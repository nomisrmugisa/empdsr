package org.pdsr.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.pdsr.InternetAvailabilityChecker;
import org.pdsr.ServiceApi;
import org.pdsr.master.model.abnormality_table;
import org.pdsr.master.model.audit_audit;
import org.pdsr.master.model.audit_recommendation;
import org.pdsr.master.model.case_identifiers;
import org.pdsr.master.model.complication_table;
import org.pdsr.master.model.cordfault_table;
import org.pdsr.master.model.diagnoses_table;
import org.pdsr.master.model.facility_table;
import org.pdsr.master.model.icd_diagnoses;
import org.pdsr.master.model.placentacheck_table;
import org.pdsr.master.model.resuscitation_table;
import org.pdsr.master.model.risk_table;
import org.pdsr.master.model.weekly_monitoring;
import org.pdsr.master.repo.AuditAuditRepository;
import org.pdsr.master.repo.AuditRecommendRepository;
import org.pdsr.master.repo.CaseRepository;
import org.pdsr.master.repo.WeeklyMonitoringTableRepository;
import org.pdsr.summary.model.SummaryPK;
import org.pdsr.summary.model.big_audit_audit;
import org.pdsr.summary.model.big_audit_recommendation;
import org.pdsr.summary.model.big_case_identifiers;
import org.pdsr.summary.model.big_weekly_monitoring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/controls/centralmerge")
public class CentralPushController {

	@Autowired
	private ServiceApi api;

	@Autowired
	private WeeklyMonitoringTableRepository weekMRepo;

	@Autowired
	private CaseRepository caseRepo;

	@Autowired
	private AuditAuditRepository aaudRepo;

	@Autowired
	private AuditRecommendRepository recRepo;

	@GetMapping("")
	public String centralmerge(Principal principal, Model model, @RequestParam(required = false) String success,
			@RequestParam(required = false) String failure) {

		if (success != null) {
			model.addAttribute("success", "Transmitted Successfully!");

		}

		else if (failure != null) {
			if ("0".equals(failure)) {
				model.addAttribute("failure", "Could not establish connection with central server");

			} else {
				model.addAttribute("failure", "Could not locate facility code " + failure + " on central server");
			}
		}

		return "controls/merger-central";
	}

	@Transactional
	@PostMapping("")
	public String centralmerge(Principal principal, @RequestParam(required = false) Integer all) {

		try {

			if (!InternetAvailabilityChecker.isInternetAvailable()) {
				return "redirect:/controls/centralmerge?failure=0";
			}

			// pull facility data remotely
			facility_table facility = api.pullMyFacility();

			final String country = facility.getParent().getParent().getParent().getFacility_name();
			final String region = facility.getParent().getParent().getFacility_name();
			final String district = facility.getParent().getFacility_name();
			final String code = facility.getFacility_code();

			pushCaseData(code, district, region, country, (all != null && all == 1));
			pushAuditData(code, district, region, country, (all != null && all == 1));
			pushRecommendationData(code, district, region, country, (all != null && all == 1));
			pushMonitoringData(code, district, region, country, (all != null && all == 1));

			return "redirect:/controls/centralmerge?success=yes";

		} catch (IOException e) {
			return "redirect:/controls/centralmerge?failure=1";
		}

	}

	private void pushCaseData(final String code, final String district, final String region, final String country,
			boolean pushAll) {
		List<case_identifiers> cases = pushAll ? caseRepo.findAll() : caseRepo.findBySubmittedToPush();
		List<case_identifiers> sent = new ArrayList<>();

		List<big_case_identifiers> jsons = new ArrayList<>();

		for (case_identifiers elem : cases) {

			big_case_identifiers json = new big_case_identifiers();

			final String ID = elem.getCase_uuid() + country + code;
			SummaryPK pk = new SummaryPK(ID, code, country, region, district);

			json.setSummaryPk(pk);
			json.setCase_date(elem.getCase_date());
			json.setCase_death(elem.getCase_death());
			json.setCase_status(elem.getCase_status());

			if (elem.getBiodata() != null) {
				json.setBiodata_sex(elem.getBiodata().getBiodata_sex());
				json.setBiodata_mdob(elem.getBiodata().getBiodata_mdob());
				json.setBiodata_mage(elem.getBiodata().getBiodata_mage());
				json.setBiodata_medu(elem.getBiodata().getBiodata_medu());
				json.setBiodata_maddress(elem.getBiodata().getBiodata_maddress());
				json.setBiodata_location(elem.getBiodata().getBiodata_location());
				json.setBiodata_contact(elem.getBiodata().getBiodata_contact());
				json.setBiodata_work(elem.getBiodata().getBiodata_work());
				json.setBiodata_marital(elem.getBiodata().getBiodata_marital());
				json.setBiodata_religion(elem.getBiodata().getBiodata_religion());
				json.setBiodata_ethnic(elem.getBiodata().getBiodata_ethnic());

			}

			if (elem.getPregnancy() != null) {
				json.setPregnancy_weeks(elem.getPregnancy().getPregnancy_weeks());
				json.setPregnancy_days(elem.getPregnancy().getPregnancy_days());
				json.setPregnancy_type(elem.getPregnancy().getPregnancy_type());
			}

			if (elem.getDelivery() != null) {
				json.setDelivery_occured(elem.getDelivery().getDelivery_occured());
				json.setDelivery_abortion(elem.getDelivery().getDelivery_abortion());
				json.setDelivery_occured_facility(elem.getDelivery().getDelivery_occured_facility());
				json.setDelivery_date(elem.getDelivery().getDelivery_date());
				json.setDelivery_hour(elem.getDelivery().getDelivery_hour());
				json.setDelivery_minute(elem.getDelivery().getDelivery_minute());
				json.setDelivery_weight(elem.getDelivery().getDelivery_weight());
				json.setDelivery_time(elem.getDelivery().getDelivery_time());
				json.setDelivery_datetime_notstated(elem.getDelivery().getDelivery_datetime_notstated());
				json.setDelivery_period(elem.getDelivery().getDelivery_period());

			}

			if (elem.getReferral() != null) {
				json.setReferral_case(elem.getReferral().getReferral_case());
				json.setReferral_patient(elem.getReferral().getReferral_patient());
				json.setReferral_source(elem.getReferral().getReferral_source());
				json.setReferral_facility(elem.getReferral().getReferral_facility());
				json.setReferral_date(elem.getReferral().getReferral_date());
				json.setReferral_hour(elem.getReferral().getReferral_hour());
				json.setReferral_minute(elem.getReferral().getReferral_minute());
				json.setReferral_time(elem.getReferral().getReferral_time());
				json.setReferral_datetime_notstated(elem.getReferral().getReferral_datetime_notstated());
				json.setReferral_adate(elem.getReferral().getReferral_adate());
				json.setReferral_ahour(elem.getReferral().getReferral_ahour());
				json.setReferral_aminute(elem.getReferral().getReferral_aminute());
				json.setReferral_atime(elem.getReferral().getReferral_atime());
				json.setReferral_adatetime_notstated(elem.getReferral().getReferral_adatetime_notstated());
				json.setReferral_transport(elem.getReferral().getReferral_transport());
				json.setReferral_notes(elem.getReferral().getReferral_notes());
				json.setReferral_file(elem.getReferral().getReferral_file());
				json.setReferral_filetype(elem.getReferral().getReferral_filetype());
			}

			if (elem.getAntenatal() != null) {
				json.setAntenatal_gravida(elem.getAntenatal().getAntenatal_gravida());
				json.setAntenatal_para(elem.getAntenatal().getAntenatal_para());
				json.setAntenatal_attend(elem.getAntenatal().getAntenatal_attend());
				json.setAntenatal_attendno(elem.getAntenatal().getAntenatal_attendno());
				json.setAntenatal_facility(elem.getAntenatal().getAntenatal_facility());
				json.setAntenatal_weeks(elem.getAntenatal().getAntenatal_weeks());
				json.setAntenatal_days(elem.getAntenatal().getAntenatal_days());
				json.setAntenatal_hiv(elem.getAntenatal().getAntenatal_hiv());
				json.setAntenatal_art(elem.getAntenatal().getAntenatal_art());
				json.setAntenatal_alcohol(elem.getAntenatal().getAntenatal_alcohol());
				json.setAntenatal_smoker(elem.getAntenatal().getAntenatal_smoker());
				json.setAntenatal_herbal(elem.getAntenatal().getAntenatal_herbal());
				json.setAntenatal_folicacid(elem.getAntenatal().getAntenatal_folicacid());
				json.setAntenatal_folicacid3m(elem.getAntenatal().getAntenatal_folicacid3m());
				json.setAntenatal_tetanus(elem.getAntenatal().getAntenatal_tetanus());
				json.setAntenatal_malprophy(elem.getAntenatal().getAntenatal_malprophy());
				json.setAntenatal_risks(elem.getAntenatal().getAntenatal_risks());

				String list = "";
				for (risk_table r : elem.getAntenatal().getRisks()) {
					list += r.getRisk_name() + "\n";
				}
				list += elem.getAntenatal().getNew_risks();
				json.setAntenatal_risks_list(list);

			}

			if (elem.getLabour() != null) {
				json.setLabour_occured(elem.getLabour().getLabour_occured());
				json.setLabour_seedate(elem.getLabour().getLabour_seedate());
				json.setLabour_seehour(elem.getLabour().getLabour_seehour());
				json.setLabour_seeminute(elem.getLabour().getLabour_seeminute());
				json.setLabour_seetime(elem.getLabour().getLabour_seetime());
				json.setLabour_seedatetime_notstated(elem.getLabour().getLabour_seedatetime_notstated());
				json.setLabour_seeperiod(elem.getLabour().getLabour_seeperiod());
				json.setLabour_herbalind(elem.getLabour().getLabour_herbalind());
				json.setLabour_startmode(elem.getLabour().getLabour_startmode());
				json.setLabour_herbalaug(elem.getLabour().getLabour_herbalaug());
				json.setLabour_partograph(elem.getLabour().getLabour_partograph());
				json.setLabour_lasthour1(elem.getLabour().getLabour_lasthour1());
				json.setLabour_lastminute1(elem.getLabour().getLabour_lastminute1());
				json.setLabour_lasthour2(elem.getLabour().getLabour_lasthour2());
				json.setLabour_lastminute2(elem.getLabour().getLabour_lastminute2());
				json.setLabour_complications(elem.getLabour().getLabour_complications());

				String list = "";
				for (complication_table r : elem.getLabour().getComplications()) {
					list += r.getComplication_name() + "\n";
				}
				list += elem.getLabour().getNew_complications();
				json.setLabour_complications_list(list);

			}

			if (elem.getFetalheart() != null) {
				json.setFetalheart_refered(elem.getFetalheart().getFetalheart_refered());
				json.setFetalheart_arrival(elem.getFetalheart().getFetalheart_arrival());
				json.setFetalheart_lastheard(elem.getFetalheart().getFetalheart_lastheard());
			}

			if (elem.getBirth() != null) {
				json.setBirth_mode(elem.getBirth().getBirth_mode());
				json.setBirth_insistnormal(elem.getBirth().getBirth_insistnormal());
				json.setBirth_csproposedate(elem.getBirth().getBirth_csproposedate());
				json.setBirth_csproposehour(elem.getBirth().getBirth_csproposehour());
				json.setBirth_csproposeminute(elem.getBirth().getBirth_csproposeminute());
				json.setBirth_csproposetime(elem.getBirth().getBirth_csproposetime());
				json.setBirth_provider(elem.getBirth().getBirth_provider());
				json.setBirth_facility(elem.getBirth().getBirth_facility());

				json.setBirth_abnormalities(elem.getBirth().getBirth_abnormalities());
				{
					String list = "";
					for (abnormality_table r : elem.getBirth().getAbnormalities()) {
						list += r.getAbnormal_name() + "\n";
					}
					list += elem.getBirth().getNew_abnormalities();
					json.setBirth_abnormalities_list(list);
				}

				json.setBirth_cordfaults(elem.getBirth().getBirth_cordfaults());
				{
					String list = "";
					for (cordfault_table r : elem.getBirth().getCordfaults()) {
						list += r.getCordfault_name() + "\n";
					}
					list += elem.getBirth().getNew_cordfaults();
					json.setBirth_cordfaults_list(list);
				}
				json.setBirth_placentachecks(elem.getBirth().getBirth_placentachecks());
				{
					String list = "";
					for (placentacheck_table r : elem.getBirth().getPlacentachecks()) {
						list += r.getPlacentacheck_name() + "\n";
					}
					list += elem.getBirth().getNew_placentachecks();
					json.setBirth_placentachecks_list(list);
				}
				json.setBirth_liqourvolume(elem.getBirth().getBirth_liqourvolume());
				json.setBirth_liqourcolor(elem.getBirth().getBirth_liqourcolor());
				json.setBirth_liqourodour(elem.getBirth().getBirth_liqourodour());
				json.setBirth_motheroutcome(elem.getBirth().getBirth_motheroutcome());
				json.setBirth_babyoutcome(elem.getBirth().getBirth_babyoutcome());
				json.setBirth_mbabyoutcome(elem.getBirth().getBirth_mbabyoutcome());
			}

			if (elem.getBabydeath() != null) {
				json.setBaby_cry(elem.getBabydeath().getBaby_cry());
				json.setBaby_resuscitation(elem.getBabydeath().getBaby_resuscitation());
				{
					String list = "";
					for (resuscitation_table r : elem.getBabydeath().getResuscitations()) {
						list += r.getResuscitation_name() + "\n";
					}
					list += elem.getBabydeath().getNew_resuscitation();
					json.setBaby_resuscitation_list(list);
				}
				json.setBaby_apgar1(elem.getBabydeath().getBaby_apgar1());
				json.setBaby_apgar5(elem.getBabydeath().getBaby_apgar5());
				json.setBaby_admitted(elem.getBabydeath().getBaby_admitted());
				{
					String list = "";
					for (diagnoses_table r : elem.getBabydeath().getDiagnoses()) {
						list += r.getDiagnosis_name() + "\n";
					}
					list += elem.getBabydeath().getNew_diagnoses();
					json.setBaby_diagnoses(list);
				}
				{
					String list = "";
					for (icd_diagnoses r : elem.getBabydeath().getIcd_diagnoses()) {
						list += r.getIcd_code() + "\n";
					}
					json.setBaby_icd_diagnoses(list);
				}
				json.setBaby_ddate(elem.getBabydeath().getBaby_ddate());
				json.setBaby_dhour(elem.getBabydeath().getBaby_dhour());
				json.setBaby_dminute(elem.getBabydeath().getBaby_dminute());
				json.setBaby_dtime(elem.getBabydeath().getBaby_dtime());
				json.setBaby_ddatetime_notstated(elem.getBabydeath().getBaby_ddatetime_notstated());
				json.setBaby_medicalcod(elem.getBabydeath().getBaby_medicalcod());
			}

			if (elem.getMdeath() != null) {
				json.setMdeath_early_evacuation(elem.getMdeath().getMdeath_early_evacuation());
				json.setMdeath_early_antibiotic(elem.getMdeath().getMdeath_early_antibiotic());
				json.setMdeath_early_laparotomy(elem.getMdeath().getMdeath_early_laparotomy());
				json.setMdeath_early_hysterectomy(elem.getMdeath().getMdeath_early_hysterectomy());
				json.setMdeath_early_transfusion(elem.getMdeath().getMdeath_early_transfusion());
				json.setMdeath_early_antihyper(elem.getMdeath().getMdeath_early_antihyper());
				json.setMdeath_early_other(elem.getMdeath().getMdeath_early_other());
				json.setMdeath_ante_transfusion(elem.getMdeath().getMdeath_ante_transfusion());
				json.setMdeath_ante_antibiotic(elem.getMdeath().getMdeath_ante_antibiotic());
				json.setMdeath_ante_externalv(elem.getMdeath().getMdeath_ante_externalv());
				json.setMdeath_ante_magsulphate(elem.getMdeath().getMdeath_ante_magsulphate());
				json.setMdeath_ante_diazepam(elem.getMdeath().getMdeath_ante_diazepam());
				json.setMdeath_ante_antihyper(elem.getMdeath().getMdeath_ante_antihyper());
				json.setMdeath_ante_hysterotomy(elem.getMdeath().getMdeath_ante_hysterotomy());
				json.setMdeath_ante_other(elem.getMdeath().getMdeath_ante_other());
				json.setMdeath_intra_instrumental(elem.getMdeath().getMdeath_intra_instrumental());
				json.setMdeath_intra_antibiotic(elem.getMdeath().getMdeath_intra_antibiotic());
				json.setMdeath_intra_caesarian(elem.getMdeath().getMdeath_intra_caesarian());
				json.setMdeath_intra_hysterectomy(elem.getMdeath().getMdeath_intra_hysterectomy());
				json.setMdeath_intra_transfusion(elem.getMdeath().getMdeath_intra_transfusion());
				json.setMdeath_intra_magsulphate(elem.getMdeath().getMdeath_intra_magsulphate());
				json.setMdeath_intra_antihyper(elem.getMdeath().getMdeath_intra_antihyper());
				json.setMdeath_intra_diazepam(elem.getMdeath().getMdeath_intra_diazepam());
				json.setMdeath_intra_other(elem.getMdeath().getMdeath_intra_other());

				json.setMdeath_postpart_evacuation(elem.getMdeath().getMdeath_postpart_evacuation());
				json.setMdeath_postpart_antibiotic(elem.getMdeath().getMdeath_postpart_antibiotic());
				json.setMdeath_postpart_laparotomy(elem.getMdeath().getMdeath_postpart_laparotomy());
				json.setMdeath_postpart_hysterectomy(elem.getMdeath().getMdeath_postpart_hysterectomy());
				json.setMdeath_postpart_transfusion(elem.getMdeath().getMdeath_postpart_transfusion());
				json.setMdeath_postpart_magsulphate(elem.getMdeath().getMdeath_postpart_magsulphate());
				json.setMdeath_postpart_placentaremoval(elem.getMdeath().getMdeath_postpart_placentaremoval());
				json.setMdeath_postpart_tacid(elem.getMdeath().getMdeath_postpart_tacid());
				json.setMdeath_postpart_oxytocin(elem.getMdeath().getMdeath_postpart_oxytocin());
				json.setMdeath_postpart_antihyper(elem.getMdeath().getMdeath_postpart_antihyper());
				json.setMdeath_postpart_diazepam(elem.getMdeath().getMdeath_postpart_diazepam());
				json.setMdeath_postpart_other(elem.getMdeath().getMdeath_postpart_other());
				json.setMdeath_other_anaesthga(elem.getMdeath().getMdeath_other_anaesthga());
				json.setMdeath_other_epidural(elem.getMdeath().getMdeath_other_epidural());
				json.setMdeath_other_spinal(elem.getMdeath().getMdeath_other_spinal());
				json.setMdeath_other_local(elem.getMdeath().getMdeath_other_local());
				json.setMdeath_other_invasive(elem.getMdeath().getMdeath_other_invasive());
				json.setMdeath_other_antihyper(elem.getMdeath().getMdeath_other_antihyper());
				json.setMdeath_other_icuventilation(elem.getMdeath().getMdeath_other_icuventilation());
				json.setMdeath_new_intervention(elem.getMdeath().getMdeath_new_intervention());
				json.setMdeath_date(elem.getMdeath().getMdeath_date());
				json.setMdeath_hour(elem.getMdeath().getMdeath_hour());
				json.setMdeath_minute(elem.getMdeath().getMdeath_minute());
				json.setMdeath_time(elem.getMdeath().getMdeath_time());
				json.setMdeath_datetime_notstated(elem.getMdeath().getMdeath_datetime_notstated());
				json.setMdeath_autopsy(elem.getMdeath().getMdeath_autopsy());
				json.setMdeath_autopsy_date(elem.getMdeath().getMdeath_autopsy_date());
				json.setMdeath_autopsy_location(elem.getMdeath().getMdeath_autopsy_location());
				json.setMdeath_autopsy_by(elem.getMdeath().getMdeath_autopsy_by());
				json.setMdeath_autopsy_final_cod(elem.getMdeath().getMdeath_autopsy_final_cod());
				json.setMdeath_autopsy_antec_cod(elem.getMdeath().getMdeath_autopsy_antec_cod());
				json.setMdeath_autopsy_ops_cod(elem.getMdeath().getMdeath_autopsy_ops_cod());
				json.setMdeath_autopsy_icd_mm(elem.getMdeath().getMdeath_autopsy_icd_mm());

				if (elem.getNotes() != null) {
					json.setNotes_text(elem.getNotes().getNotes_text());
					json.setNotes_filetype(elem.getNotes().getNotes_filetype());
					json.setNotes_file(elem.getNotes().getNotes_file());
				}
			}

			jsons.add(json);

			elem.setData_sent(1);

			sent.add(elem);

		}

		final String msg = api.postCases(jsons);

		if ("success".equals(msg)) {
			caseRepo.saveAll(sent);
		}
	}

	private void pushAuditData(final String code, final String district, final String region, final String country,
			boolean pushAll) {
		List<audit_audit> cases = pushAll ? aaudRepo.findAll() : aaudRepo.findByAuditsToPush();
		List<audit_audit> sent = new ArrayList<>();
		List<big_audit_audit> jsons = new ArrayList<>();

		for (audit_audit elem : cases) {

			big_audit_audit json = new big_audit_audit();

			final String ID = elem.getAudit_uuid() + country + code;
			SummaryPK pk = new SummaryPK(ID, code, country, region, district);

			json.setSummaryPk(pk);
			json.setRec_complete((elem.getRec_complete() == null) ? 0 : elem.getRec_complete());
			json.setAudit_cdate(elem.getAudit_cdate());

			json.setAudit_csc(elem.getAudit_csc());
			json.setAudit_death(elem.getAudit_death());
			json.setAudit_delay1(elem.getAudit_delay1());
			json.setAudit_delay2(elem.getAudit_delay2());
			json.setAudit_delay3a(elem.getAudit_delay3a());
			json.setAudit_delay3b(elem.getAudit_delay3b());
			json.setAudit_delay3c(elem.getAudit_delay3c());
			json.setAudit_facmfs(elem.getAudit_facmfs());
			json.setAudit_icd10(elem.getAudit_icd10());
			json.setAudit_icdpm(elem.getAudit_icdpm());
			json.setAudit_ifcmfs(elem.getAudit_ifcmfs());
			json.setAudit_sysmfs(elem.getAudit_sysmfs());
			json.setAudit_hwkmfs(elem.getAudit_hwkmfs());

			jsons.add(json);

			elem.setData_sent(1);

			sent.add(elem);

		}

		final String msg = api.postAudits(jsons);

		if ("success".equals(msg)) {
			aaudRepo.saveAll(sent);
		}

	}

	private void pushRecommendationData(final String code, final String district, final String region,
			final String country, boolean pushAll) {
		List<audit_recommendation> cases = pushAll ? recRepo.findAll() : recRepo.findActionsToPush();
		List<audit_recommendation> sent = new ArrayList<>();
		List<big_audit_recommendation> jsons = new ArrayList<>();
		for (audit_recommendation elem : cases) {

			big_audit_recommendation json = new big_audit_recommendation();

			final String ID = elem.getAudit_uuid() + country + code;
			SummaryPK pk = new SummaryPK(ID, code, country, region, district);
			json.setSummaryPk(pk);

			json.setRecommendation_comments(elem.getRecommendation_comments());
			json.setRecommendation_date(elem.getRecommendation_date());
			json.setRecommendation_deadline(elem.getRecommendation_deadline());
			json.setRecommendation_leader(elem.getRecommendation_leader());
			json.setRecommendation_reporter(elem.getRecommendation_reporter());
			json.setRecommendation_resources(elem.getRecommendation_resources());
			json.setRecommendation_status(elem.getRecommendation_status());
			json.setRecommendation_task(elem.getRecommendation_task());
			json.setRecommendation_title(elem.getRecommendation_title());

			jsons.add(json);

			elem.setData_sent(1);

			sent.add(elem);

		}

		final String msg = api.postRecommendations(jsons);

		if ("success".equals(msg)) {
			recRepo.saveAll(sent);
		}

	}

	private void pushMonitoringData(final String code, final String district, final String region, final String country,
			boolean pushAll) {
		List<weekly_monitoring> cases = pushAll ? weekMRepo.findAll() : weekMRepo.findMonitoringToPush();
		List<weekly_monitoring> sent = new ArrayList<>();
		List<big_weekly_monitoring> jsons = new ArrayList<>();
		for (weekly_monitoring elem : cases) {

			big_weekly_monitoring json = new big_weekly_monitoring();

			final String ID = "" + elem.getId().getWeekly_id() + elem.getId().getMindex() + country + code;
			SummaryPK pk = new SummaryPK(ID, code, country, region, district);

			json.setSummaryPk(pk);
			json.setMindex(elem.getId().getMindex());
			json.setWeekly_mdesc(elem.getWm_grids().getWeekly_mdesc());
			json.setWeekly_month(elem.getWm_grids().getWeekly_month());
			json.setWeekly_week(elem.getWm_grids().getWeekly_week());
			json.setWeekly_year(elem.getWm_grids().getWeekly_year());
			json.setWm_subval(elem.getWm_subval());
			json.setWm_values(elem.getWm_values());

			jsons.add(json);

			elem.setData_sent(1);

			sent.add(elem);

		}

		final String msg = api.postWeeklyMonitorings(jsons);

		if ("success".equals(msg)) {
			weekMRepo.saveAll(sent);
		}

	}

}
// end class