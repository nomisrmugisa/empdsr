package org.pdsr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.pdsr.dhis2.Dhis2ProgramStagesResponse;
import org.pdsr.dhis2.Dhis2StageDetailResponse;
import org.pdsr.json.json_dhis2_dataValues;
import org.pdsr.json.json_dhis2_form;
import org.pdsr.master.model.case_identifiers;
import org.pdsr.master.model.sync_table;
import org.pdsr.pojos.Dhis2Authorisation;
import org.springframework.stereotype.Service;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Orchestrates the 3-step DHIS2 sync workflow:
 * 1. Validate all required fields (pre-flight check)
 * 2. GET program stages / data elements from DHIS2 API
 * 3. Build json_dhis2_form and POST via ServiceApi
 *
 * Errors are returned as List<Object[]> = { message, caseUuid, tabIndex }
 * matching the format expected by registry/case-dhis2.html.
 */
@Service
public class Dhis2SyncService {

    // Tab index constants (match ?page= parameter in edit URLs)
    private static final int TAB_CASE_ENTRY = 0;
    private static final int TAB_PROFILE = 1;
    private static final int TAB_PREGNANCY = 3;
    private static final int TAB_LABOUR = 5;
    private static final int TAB_DELIVERY = 6;
    private static final int TAB_BIRTH = 7;
    private static final int TAB_DEATH = 8;
    private static final int TAB_NOTES = 9;

    private final RestTemplateBuilder builder;
    private final ServiceApi serviceApi;

    public Dhis2SyncService(RestTemplateBuilder builder, ServiceApi serviceApi) {
        this.builder = builder;
        this.serviceApi = serviceApi;
    }

    // ---------------------------------------------------------------
    // Main entry point — called by CaseEntryController on Sync button
    // Returns list of Object[] errors; empty = all synced successfully
    // ---------------------------------------------------------------
    public List<Object[]> syncCases(List<case_identifiers> cases,
            Dhis2Authorisation dhis2,
            sync_table synctable) {

        List<Object[]> errors = new ArrayList<>();
        String dhis2Url = dhis2.getDhis2_url();
        String username = dhis2.getDhis2_username();
        String password = dhis2.getDhis2_password();

        for (case_identifiers item : cases) {

            boolean isMaternalDeath = item.getCase_death() != null
                    && item.getCase_death() == CONSTANTS.MATERNAL_DEATH;
            boolean isPerinatalDeath = item.getCase_death() != null
                    && (item.getCase_death() == CONSTANTS.STILL_BIRTH
                            || item.getCase_death() == CONSTANTS.NEONATAL_DEATH);
            boolean isNeonatalDeath = item.getCase_death() != null
                    && item.getCase_death() == CONSTANTS.NEONATAL_DEATH;

            // -------------------------------------------------------
            // PRE-FLIGHT VALIDATION
            // -------------------------------------------------------
            List<Object[]> caseErrors = validateCase(item,
                    isMaternalDeath, isPerinatalDeath, isNeonatalDeath);

            if (!caseErrors.isEmpty()) {
                errors.addAll(caseErrors);
                continue; // skip this case — do NOT POST to DHIS2
            }

            // Determine program UID
            String programUID;
            if (isMaternalDeath) {
                programUID = CONSTANTS.DHIS2_MATERNAL_NOTIFICATION_PROGRAM;
            } else if (isPerinatalDeath) {
                programUID = CONSTANTS.DHIS2_PERINATAL_NOTIFICATION_PROGRAM;
            } else {
                errors.add(new Object[] {
                        "Type of death not selected — case cannot be synced.",
                        item.getCase_uuid(), TAB_CASE_ENTRY });
                continue;
            }

            // Step 1 — resolve stage ID
            String stageId = getProgramStageId(programUID, dhis2Url, username, password);
            if (stageId == null) {
                errors.add(new Object[] {
                        "Could not resolve program stage for program " + programUID
                                + " — check DHIS2 connectivity.",
                        item.getCase_uuid(), TAB_CASE_ENTRY });
                continue;
            }

            // Step 2 — get live data element IDs (validates our mapping)
            Set<String> liveIds = getLiveDataElementIds(stageId, dhis2Url, username, password);
            if (liveIds.isEmpty()) {
                errors.add(new Object[] {
                        "Could not retrieve data elements for stage " + stageId + ".",
                        item.getCase_uuid(), TAB_CASE_ENTRY });
                continue;
            }

            // Step 3 — build event payload and POST
            json_dhis2_form d = buildEvent(item, synctable, dhis2, programUID,
                    isMaternalDeath, isPerinatalDeath, isNeonatalDeath, liveIds);

            String eventsUrl = dhis2Url + "/api/events";
            serviceApi.saveForm(d, eventsUrl, username, password);
        }

        return errors;
    }

    // ---------------------------------------------------------------
    // PRE-FLIGHT VALIDATION
    // Three blocks: A = notification, B = maternal review, C = perinatal review
    // ---------------------------------------------------------------
    private List<Object[]> validateCase(case_identifiers item,
            boolean isMaternalDeath,
            boolean isPerinatalDeath,
            boolean isNeonatalDeath) {

        List<Object[]> e = new ArrayList<>();
        String uuid = item.getCase_uuid();

        // ============================================================
        // BLOCK A — Notification program mandatory fields
        // (applies to ALL cases, both maternal and perinatal)
        // ============================================================

        // Tab 0 — Case Entry
        if (blank(item.getCase_mname()))
            e.add(err("Mother's name is missing (Case Entry tab).", uuid, TAB_CASE_ENTRY));

        if (blank(item.getCase_mid()))
            e.add(err("Inpatient number is missing (Case Entry tab).", uuid, TAB_CASE_ENTRY));

        if (item.getCase_death() == null)
            e.add(err("Type of death has not been selected (Case Entry tab).", uuid, TAB_CASE_ENTRY));

        if (item.getCase_nationality() == null)
            e.add(err("Nationality has not been selected (Case Entry tab).", uuid, TAB_CASE_ENTRY));

        // Tab 1 — Profile / Biodata
        if (item.getBiodata() == null) {
            e.add(err("Profile (biodata) section is missing entirely.", uuid, TAB_PROFILE));
        } else {
            if (blank(item.getBiodata().getBiodata_village()))
                e.add(err("Village of residence is missing (Profile tab).", uuid, TAB_PROFILE));
            if (blank(item.getBiodata().getBiodata_location()))
                e.add(err("Sub-county (LC III) is missing (Profile tab).", uuid, TAB_PROFILE));
            if (blank(item.getBiodata().getBiodata_nok()))
                e.add(err("Next of kin is missing (Profile tab).", uuid, TAB_PROFILE));
        }

        // Tab 3 — Pregnancy
        if (item.getPregnancy() == null || item.getPregnancy().getPregnancy_weeks() == null)
            e.add(err("Gestational age (weeks) is missing (Pregnancy tab).", uuid, TAB_PREGNANCY));

        // Tab 9 — Notes
        if (item.getNotes() == null) {
            e.add(err("Notes section is missing entirely.", uuid, TAB_NOTES));
        } else {
            if (item.getNotes().getNotes_dispdate() == null)
                e.add(err("Dispatch date is missing (Notes tab).", uuid, TAB_NOTES));
            if (blank(item.getNotes().getNotes_dlvby()))
                e.add(err("'Delivered/sent by' is missing (Notes tab).", uuid, TAB_NOTES));
            if (item.getNotes().getNotes_dlvdate() == null)
                e.add(err("Delivery date of form is missing (Notes tab).", uuid, TAB_NOTES));
            if (blank(item.getNotes().getNotes_rcvby()))
                e.add(err("'Form received by' is missing (Notes tab).", uuid, TAB_NOTES));
            if (item.getNotes().getNotes_rcvdate() == null)
                e.add(err("Form received date is missing (Notes tab).", uuid, TAB_NOTES));
            if (blank(item.getNotes().getNotes_ntfby()))
                e.add(err("'Notified by' (filled by) is missing (Notes tab).", uuid, TAB_NOTES));
        }

        // ============================================================
        // BLOCK B — Maternal Death REVIEW program mandatory fields
        // (only when case_death == MATERNAL_DEATH)
        // ============================================================
        if (isMaternalDeath) {

            // Tab 5 — Labour (admission date/time required for maternal review)
            if (item.getLabour() == null || item.getLabour().getLabour_seedate() == null)
                e.add(err("Date first seen / admission date is missing (Labour tab).", uuid, TAB_LABOUR));

            // Tab 8 — Maternal Death details
            if (item.getMdeath() == null) {
                e.add(err("Maternal Death section is missing entirely (Death tab).", uuid, TAB_DEATH));
            } else {
                if (item.getMdeath().getMdeath_date() == null)
                    e.add(err("Date of maternal death is missing (Death tab).", uuid, TAB_DEATH));

                if (blank(item.getMdeath().getMdeath_possible_cause()))
                    e.add(err("Possible cause of maternal death is missing (Death tab).", uuid, TAB_DEATH));

                if (item.getMdeath().getMdeath_place() == null)
                    e.add(err("Place of death has not been selected (Death tab).", uuid, TAB_DEATH));

                if (item.getMdeath().getMdeath_condition_admission() == null)
                    e.add(err("Condition on admission has not been selected (Death tab).", uuid, TAB_DEATH));
            }
        }

        // ============================================================
        // BLOCK C — Perinatal Death REVIEW program mandatory fields
        // (only when case_death == STILL_BIRTH or NEONATAL_DEATH)
        // ============================================================
        if (isPerinatalDeath) {

            // Tab 6 — Delivery
            if (item.getDelivery() == null) {
                e.add(err("Delivery section is missing entirely (Delivery tab).", uuid, TAB_DELIVERY));
            } else {
                if (item.getDelivery().getDelivery_date() == null)
                    e.add(err("Date of delivery is missing (Delivery tab).", uuid, TAB_DELIVERY));

                if (item.getDelivery().getDelivery_weight() == null)
                    e.add(err("Birth weight is missing (Delivery tab).", uuid, TAB_DELIVERY));
            }

            // Tab 7 — Birth outcome
            if (item.getBirth() == null || item.getBirth().getBirth_babyoutcome() == null)
                e.add(err("Baby outcome (fresh stillbirth / macerated / neonatal) "
                        + "has not been selected (Birth tab).", uuid, TAB_BIRTH));

            // Tab 8 — Baby / Neonatal death details (neonatal only)
            if (isNeonatalDeath) {
                if (item.getBabydeath() == null) {
                    e.add(err("Neonatal death section is missing entirely (Death tab).", uuid, TAB_DEATH));
                } else {
                    if (item.getBabydeath().getBaby_ddate() == null)
                        e.add(err("Date of neonatal death is missing (Death tab).", uuid, TAB_DEATH));
                    if (blank(item.getBabydeath().getBaby_possible_cause()))
                        e.add(err("Possible cause of neonatal death is missing (Death tab).", uuid, TAB_DEATH));
                }
            }
        }

        return e;
    }

    // ---------------------------------------------------------------
    // Build json_dhis2_form payload
    // Only adds a dataValue if the DE id exists in the live set.
    // ---------------------------------------------------------------
    private json_dhis2_form buildEvent(case_identifiers item,
            sync_table synctable,
            Dhis2Authorisation dhis2,
            String programUID,
            boolean isMaternalDeath,
            boolean isPerinatalDeath,
            boolean isNeonatalDeath,
            Set<String> liveIds) {

        SimpleDateFormat utcFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");

        json_dhis2_form d = new json_dhis2_form();

        // Event metadata
        int uuidLength = item.getCase_uuid().length();
        d.setEvent(item.getCase_uuid().substring(uuidLength - 11));
        d.setProgram(programUID);
        d.setOrgUnit(synctable.getSync_code());
        d.setEventDate(dateFmt.format(item.getCase_date()));
        d.setStatus(CONSTANTS.DHIS2_FORM_STATUS);
        d.setStoredBy(dhis2.getDhis2_username());
        d.getCoordinate().setLongitude(50.7);
        d.getCoordinate().setLatitude(10.5);

        // Attribute option combo (nationality)
        if (item.getCase_nationality() != null) {
            if (item.getCase_nationality() == CONSTANTS.DHIS2_NATIONAL)
                d.setAttributeOptionCombo(CONSTANTS.DHIS2_ATTRIBUTECOMBO_NATIONAL);
            else if (item.getCase_nationality() == CONSTANTS.DHIS2_REFUGEE)
                d.setAttributeOptionCombo(CONSTANTS.DHIS2_ATTRIBUTECOMBO_REFUGEE);
            else if (item.getCase_nationality() == CONSTANTS.DHIS2_FOREIGNER)
                d.setAttributeOptionCombo(CONSTANTS.DHIS2_ATTRIBUTECOMBO_FOREIGNER);
        }

        // ---- Tab 0: Case Entry ----
        dv(d, liveIds, "ZKBE8Xm9DJG", item.getCase_id());

        String mnameDe = isPerinatalDeath ? "ARHfUa6Z9qs" : "HCbRydAAt1T";
        dv(d, liveIds, mnameDe, item.getCase_mname());

        if (isMaternalDeath)
            dv(d, liveIds, "j9euMjl5QGc", item.getCase_nin());

        String midDe = isPerinatalDeath ? "XYcqirdNu2m" : "YNXzLwM2R6p";
        dv(d, liveIds, midDe, item.getCase_mid());

        dv(d, liveIds, "BMGZD6w3tid", dateFmt.format(item.getCase_date()));

        // ---- Tab 1: Profile ----
        if (item.getBiodata() != null) {
            String villageDe = isPerinatalDeath ? "WH6mqVqUyjY" : "WFSWhrHOUFl";
            String locationDe = isPerinatalDeath ? "VRVt0LjRQvb" : "xQoLDOeMANl";
            String districtDe = isPerinatalDeath ? "ro2ycn9f2UP" : "MwBfsz2pc0j";
            String nokDe = isPerinatalDeath ? "yy6fkRMHXuv" : "IBlMJxS9Qe7";

            dv(d, liveIds, villageDe, item.getBiodata().getBiodata_village());
            dv(d, liveIds, locationDe, item.getBiodata().getBiodata_location());
            dv(d, liveIds, districtDe, synctable.getSync_name());
            dv(d, liveIds, nokDe, item.getBiodata().getBiodata_nok());

            if (isMaternalDeath)
                dv(d, liveIds, "rXcIphzS2GV", item.getBiodata().getBiodata_mage());
            if (isPerinatalDeath)
                dv(d, liveIds, "Svg1CaephM3", item.getBiodata().getBiodata_contact());
        }

        // ---- Tab 3: Pregnancy ----
        if (item.getPregnancy() != null) {
            String gestDe = isPerinatalDeath ? "h9MKKyiH6Ar" : "jhisgRfr13C";
            dv(d, liveIds, gestDe, item.getPregnancy().getPregnancy_weeks());
        }

        // ---- Tab 5: Labour ----
        if (item.getLabour() != null && isMaternalDeath
                && item.getLabour().getLabour_seedate() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(item.getLabour().getLabour_seedate());
            safeSetHour(cal, item.getLabour().getLabour_seehour());
            safeSetMinute(cal, item.getLabour().getLabour_seeminute());
            dv(d, liveIds, "r6tDKOx4iVL", utcFmt.format(cal.getTime()));
        }

        // ---- Tab 6: Delivery ----
        if (item.getDelivery() != null && isPerinatalDeath
                && item.getDelivery().getDelivery_date() != null) {

            Calendar cal = Calendar.getInstance();
            cal.setTime(item.getDelivery().getDelivery_date());
            safeSetHour(cal, item.getDelivery().getDelivery_hour());
            safeSetMinute(cal, item.getDelivery().getDelivery_minute());
            dv(d, liveIds, "f4iqSVSIif1", utcFmt.format(cal.getTime()));

            if (item.getDelivery().getDelivery_weight() != null)
                dv(d, liveIds, "x8dzSqL6DfQ",
                        (int) (1000.0 * item.getDelivery().getDelivery_weight()));
        }

        // ---- Tab 7: Birth ----
        if (item.getBirth() != null && isPerinatalDeath) {
            String typeDeath = "";
            Integer babyOutcome = item.getBirth().getBirth_babyoutcome();
            if (babyOutcome != null) {
                if (babyOutcome == 0)
                    typeDeath = CONSTANTS.DHIS2_FSB;
                else if (babyOutcome == 1)
                    typeDeath = CONSTANTS.DHIS2_MSB;
                else if (babyOutcome == 2)
                    typeDeath = CONSTANTS.DHIS2_END;
            }
            dv(d, liveIds, "gBhV2LXen0l", typeDeath);
        }

        // ---- Tab 8a: Perinatal — neonatal or still birth ----
        if (isPerinatalDeath && item.getDelivery() != null
                && item.getDelivery().getDelivery_date() != null) {

            Date dateDelv = item.getDelivery().getDelivery_date();
            Integer hourDelv = item.getDelivery().getDelivery_hour();

            if (isNeonatalDeath && item.getBabydeath() != null) {
                Date dateDeath = item.getBabydeath().getBaby_ddate();
                Integer hourDeath = item.getBabydeath().getBaby_dhour();
                Integer minDeath = item.getBabydeath().getBaby_dminute();

                if (dateDeath != null) {
                    int ageDays = (int) CONSTANTS.calculateAgeInDays(dateDelv, dateDeath);
                    int ageHours = (ageDays == 0 && hourDeath != null && hourDelv != null)
                            ? (hourDeath - hourDelv)
                            : (hourDeath != null ? hourDeath : 0);

                    dv(d, liveIds, "tVHwoNnaTmj", ageDays);
                    dv(d, liveIds, "LRRZJWsh5p0", ageHours);
                    dv(d, liveIds, "lN4hzVULEdF", dateFmt.format(dateDeath));
                    if (minDeath != null)
                        dv(d, liveIds, "LfoSGxsRASi", minDeath);

                    if (item.getLabour() != null && item.getLabour().getLabour_seedate() != null) {
                        int durDays = (int) CONSTANTS.calculateAgeInDays(
                                item.getLabour().getLabour_seedate(), dateDeath);
                        int durHours = (durDays == 0 && hourDeath != null
                                && item.getLabour().getLabour_seehour() != null)
                                        ? (hourDeath - item.getLabour().getLabour_seehour())
                                        : (hourDeath != null ? hourDeath : 0);
                        dv(d, liveIds, "Gz95Yv0DaHm", durDays);
                        dv(d, liveIds, "oeaDOmhm0CS", durHours);
                    }

                    dv(d, liveIds, "ikWjwp2LnoN", item.getBabydeath().getBaby_possible_cause());
                }

            } else {
                // Fresh or Macerated Stillbirth
                dv(d, liveIds, "lN4hzVULEdF", dateFmt.format(dateDelv));
                if (item.getDelivery().getDelivery_minute() != null)
                    dv(d, liveIds, "LfoSGxsRASi", item.getDelivery().getDelivery_minute());
                dv(d, liveIds, "tVHwoNnaTmj", 0);
                dv(d, liveIds, "LRRZJWsh5p0", 0);
                dv(d, liveIds, "ikWjwp2LnoN", "Still Birth");

                if (item.getLabour() != null && item.getLabour().getLabour_seedate() != null) {
                    int durDays = (int) CONSTANTS.calculateAgeInDays(
                            item.getLabour().getLabour_seedate(), dateDelv);
                    int durHours = (hourDelv != null && item.getLabour().getLabour_seehour() != null)
                            ? (durDays == 0 ? hourDelv - item.getLabour().getLabour_seehour() : hourDelv)
                            : 0;
                    dv(d, liveIds, "Gz95Yv0DaHm", durDays);
                    dv(d, liveIds, "oeaDOmhm0CS", durHours);
                }
            }
        }

        // ---- Tab 8b: Maternal death ----
        if (isMaternalDeath && item.getMdeath() != null
                && item.getMdeath().getMdeath_date() != null) {

            Calendar cal = Calendar.getInstance();
            cal.setTime(item.getMdeath().getMdeath_date());
            safeSetHour(cal, item.getMdeath().getMdeath_hour());
            safeSetMinute(cal, item.getMdeath().getMdeath_minute());
            dv(d, liveIds, "qWAqTjmR6Dj", utcFmt.format(cal.getTime()));
            dv(d, liveIds, "cdAirZ9dQKj", item.getMdeath().getMdeath_possible_cause());

            buildWhereDied(d, liveIds, item);
        }

        // ---- Tab 9: Notes ----
        if (item.getNotes() != null) {
            String dlvbyDe = isPerinatalDeath ? "m4RxzRUvdG2" : "ZmbVtTXyG29";
            String dlvcDe = isPerinatalDeath ? "UX4BsvcuDUS" : "jVfTrVzuRoP";
            String rcvbyDe = isPerinatalDeath ? "HKsuTxhq0TP" : "YP7LbXgv1c8";
            String ntfbyDe = isPerinatalDeath ? "D0IBmvQLA2Q" : "v7mt2RBEWEq";
            String ntfcDe = isPerinatalDeath ? "wWZ4b4m281F" : "HRWIjaS9i4M";

            if (item.getNotes().getNotes_dispdate() != null)
                dv(d, liveIds, "MTfPUIoVmKT", dateFmt.format(item.getNotes().getNotes_dispdate()));

            dv(d, liveIds, dlvbyDe, item.getNotes().getNotes_dlvby());
            dv(d, liveIds, dlvcDe, item.getNotes().getNotes_dlvcontact());

            if (item.getNotes().getNotes_dlvdate() != null)
                dv(d, liveIds, "yow1Falsvhe", dateFmt.format(item.getNotes().getNotes_dlvdate()));

            dv(d, liveIds, rcvbyDe, item.getNotes().getNotes_rcvby());

            if (item.getNotes().getNotes_rcvdate() != null)
                dv(d, liveIds, "x3RuFJLnC37", dateFmt.format(item.getNotes().getNotes_rcvdate()));

            dv(d, liveIds, ntfbyDe, item.getNotes().getNotes_ntfby());
            dv(d, liveIds, ntfcDe, item.getNotes().getNotes_ntfcontact());
        }

        return d;
    }

    // ---------------------------------------------------------------
    // Builds the "Where Died" (FvHDEzD1M0A) data value (Maternal)
    // ---------------------------------------------------------------
    private void buildWhereDied(json_dhis2_form d, Set<String> liveIds,
            case_identifiers item) {
        String died = "";
        if (item.getLabour() != null && item.getLabour().getLabour_occured() != null) {
            if (item.getLabour().getLabour_occured() == 0) {
                died = CONSTANTS.DHIS2_DIED_UNDELIVERED;
            } else if (item.getLabour().getLabour_occured() == 1) {
                died = CONSTANTS.DHIS2_DIED_INLABOUR;
                if (item.getDelivery() != null
                        && item.getDelivery().getDelivery_occured() != null
                        && item.getDelivery().getDelivery_occured() == 1) {
                    died = CONSTANTS.DHIS2_DIED_AFTERDELIVERY;
                }
            }
        }
        if (item.getBirth() != null && item.getBirth().getBirth_mode() != null) {
            int m = item.getBirth().getBirth_mode();
            if (m == 1 || m == 2)
                died = CONSTANTS.DHIS2_DIED_INTHEATRE;
        }
        dv(d, liveIds, "FvHDEzD1M0A", died);
    }

    // ---------------------------------------------------------------
    // Step 1 — get the programStage ID for a program UID
    // ---------------------------------------------------------------
    public String getProgramStageId(String programUID, String dhis2Url,
            String username, String password) {
        try {
            String url = dhis2Url + "/api/programs/" + programUID
                    + ".json?fields=programStages[id,name]";
            RestTemplate rt = builder.basicAuthentication(username, password).build();
            Dhis2ProgramStagesResponse resp = rt.getForObject(url, Dhis2ProgramStagesResponse.class);
            if (resp != null && resp.getProgramStages() != null
                    && !resp.getProgramStages().isEmpty()) {
                return resp.getProgramStages().get(0).getId();
            }
        } catch (RestClientException ex) {
            System.err.println("[DHIS2] getProgramStageId error: " + ex.getMessage());
        }
        return null;
    }

    // ---------------------------------------------------------------
    // Step 2 — get the set of live data element IDs for a stage
    // ---------------------------------------------------------------
    public Set<String> getLiveDataElementIds(String stageId, String dhis2Url,
            String username, String password) {
        try {
            String url = dhis2Url + "/api/programStages/" + stageId
                    + ".json?fields=name,id,programStageDataElements[dataElement[id,name]]";
            RestTemplate rt = builder.basicAuthentication(username, password).build();
            Dhis2StageDetailResponse resp = rt.getForObject(url, Dhis2StageDetailResponse.class);
            if (resp != null && resp.getProgramStageDataElements() != null) {
                return resp.getProgramStageDataElements().stream()
                        .filter(pde -> pde.getDataElement() != null)
                        .map(pde -> pde.getDataElement().getId())
                        .collect(Collectors.toSet());
            }
        } catch (RestClientException ex) {
            System.err.println("[DHIS2] getLiveDataElementIds error: " + ex.getMessage());
        }
        return Set.of();
    }

    // ---------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------

    /** Convenience: build an Object[] error entry */
    private Object[] err(String message, String caseUuid, int tabIndex) {
        return new Object[] { message, caseUuid, tabIndex };
    }

    /** True if string is null or blank */
    private boolean blank(String s) {
        return s == null || s.trim().isEmpty();
    }

    /** Only adds a dataValue if deId is in the live set and value is not null */
    private void dv(json_dhis2_form d, Set<String> liveIds, String deId, Object value) {
        if (value == null)
            return;
        if (!liveIds.isEmpty() && !liveIds.contains(deId)) {
            System.err.println("[DHIS2] DE " + deId + " not found in program stage — skipping.");
            return;
        }
        // All DHIS2 values are ultimately strings over the wire
        d.getDataValues().add(new json_dhis2_dataValues(deId, value.toString()));
    }

    private void safeSetHour(Calendar cal, Integer hour) {
        if (hour != null)
            cal.set(Calendar.HOUR_OF_DAY, hour);
    }

    private void safeSetMinute(Calendar cal, Integer minute) {
        if (minute != null)
            cal.set(Calendar.MINUTE, minute);
    }
}
