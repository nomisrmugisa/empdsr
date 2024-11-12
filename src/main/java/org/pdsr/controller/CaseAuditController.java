package org.pdsr.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.pdsr.CONSTANTS;
import org.pdsr.EmailService;
import org.pdsr.InternetAvailabilityChecker;
import org.pdsr.Utils;
import org.pdsr.json.json_algorithm;
import org.pdsr.json.json_data;
import org.pdsr.json.json_list;
import org.pdsr.master.model.audit_audit;
import org.pdsr.master.model.audit_case;
import org.pdsr.master.model.audit_recommendation;
import org.pdsr.master.model.case_identifiers;
import org.pdsr.master.model.datamap;
import org.pdsr.master.model.datamapPK;
import org.pdsr.master.model.icd_codes;
import org.pdsr.master.model.mcgroup_table;
import org.pdsr.master.model.mcondition_table;
import org.pdsr.master.model.sync_table;
import org.pdsr.master.repo.AuditAuditRepository;
import org.pdsr.master.repo.AuditCaseRepository;
import org.pdsr.master.repo.AuditRecommendRepository;
import org.pdsr.master.repo.CFactorsRepository;
import org.pdsr.master.repo.CaseRepository;
import org.pdsr.master.repo.DatamapRepository;
import org.pdsr.master.repo.IcdCodesRepository;
import org.pdsr.master.repo.McgroupRepository;
import org.pdsr.master.repo.MconditionsRepository;
import org.pdsr.master.repo.SyncTableRepository;
import org.pdsr.master.repo.UserTableRepository;
import org.pdsr.pojos.CaseWrapper;
import org.pdsr.pojos.icdpm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/auditing")
public class CaseAuditController {

	@Autowired
	private SyncTableRepository syncRepo;

	@Autowired
	private UserTableRepository userRepo;

	@Autowired
	private CaseRepository caseRepo;

	@Autowired
	private AuditCaseRepository acaseRepo;

	@Autowired
	private AuditAuditRepository tcaseRepo;

	@Autowired
	private AuditRecommendRepository rcaseRepo;

	@Autowired
	private IcdCodesRepository icdRepo;

	@Autowired
	private DatamapRepository mapRepo;

	@Autowired
	private MessageSource msg;

	@Autowired
	private CFactorsRepository cfactRepo;

	@Autowired
	private MconditionsRepository mcondRepo;

	@Autowired
	private McgroupRepository mcgrpRepo;

	@Autowired
	private EmailService emailService;

	private String[] getRecipients() {
		List<String> recipientList = userRepo.findByUser_alerted(true);
		if (recipientList == null) {
			recipientList = new ArrayList<>();
		}
		recipientList.add("makmanu128@gmail.com");
		recipientList.add("elelart@gmail.com");

		return recipientList.toArray(new String[recipientList.size()]);

	}

	@Scheduled(cron = "0 0 9 * * 1,3") // (9pm) of every tuesday, thursday within each week
	public void autoCheckPendingReviews() {
		try {
			if (InternetAvailabilityChecker.isInternetAvailable()) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, -7);
				sync_table sync = syncRepo.findById(CONSTANTS.FACILITY_ID).get();

				// alert for pending reviews
				List<audit_case> auditsPending = acaseRepo.findActivePendingAudit(cal.getTime());
				if (auditsPending.size() > 0) {
					emailService.sendSimpleMessage(getRecipients(), "TEST MESSAGE - PDSR PENDING REVIEW NOTIFICATION!",
							"Hello Reviewers,\n" + "\nThere are " + auditsPending.size()
									+ " deaths yet to be reviewed for this week" + "\nHealth Facility: "
									+ sync.getSync_name() + " - " + sync.getSync_code()
									+ "\nThis is a PILOT IMPLEMENTATION of the Enhanced Automated PDSR tool developed by Alex and Eliezer");
				}

				// alert for pending recommendations
				List<audit_audit> recsPending = tcaseRepo.findByPendingRecommendation();
				if (recsPending.size() > 0) {
					emailService.sendSimpleMessage(getRecipients(),
							"TEST MESSAGE - PDSR PENDING RECOMMENDATIONS NOTIFICATION!",
							"Hello Reviewers,\n" + "\nThere are " + recsPending.size() + " recommendations to work on"
									+ "\nHealth Facility: " + sync.getSync_name() + " - " + sync.getSync_code()
									+ "\nThis is a PILOT IMPLEMENTATION of the Enhanced Automated PDSR tool developed by Alex and Eliezer");
				}

				// alerts for overdue actions
				List<audit_recommendation> overdue = new ArrayList<>();
				for (audit_recommendation elem : rcaseRepo.findByPendingAction()) {

					if (new java.util.Date().after(elem.getRecommendation_deadline())
							&& elem.getRecommendation_status() != 2) {
					}

					overdue.add(elem);
				}
				if (overdue.size() > 0) {
					emailService.sendSimpleMessage(getRecipients(), "TEST MESSAGE - PDSR OVERDUE ACTIONS NOTIFICATION!",
							"Hello Reviewers,\n" + "\nThere are " + overdue.size()
									+ " incomplete actions that have passed the deadline" + "\nHealth Facility: "
									+ sync.getSync_name() + " - " + sync.getSync_code()
									+ "\nThis is a PILOT IMPLEMENTATION of the Enhanced Automated PDSR tool developed by Alex and Eliezer");
				}

			}

		} catch (IOException e) {
		}

	}

	@GetMapping("")
	public String list(Principal principal, Model model) {

		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table synctable = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		model.addAttribute("myf", synctable.getSync_name());

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		model.addAttribute("items", acaseRepo.findActivePendingAudit(cal.getTime()));//// pick data selected at least
																						//// seven days ago

		model.addAttribute("items1", tcaseRepo.findByPendingRecommendation());

		List<audit_recommendation> recommendations = new ArrayList<>();
		for (audit_recommendation elem : rcaseRepo.findByPendingAction()) {

			if (elem.getRecommendation_status() == 2) {
				elem.setRec_color("bg-success text-white");

			} else if (new java.util.Date().before(elem.getRecommendation_deadline())) {// date passed but not
																						// completed

				if (elem.getRecommendation_status() == 1) {
					elem.setRec_color("table-warning text-dark");
				} else {
					elem.setRec_color("table-light");
				}

			} else {
				elem.setRec_color("bg-danger text-white");
				elem.setBg_color("fw-bold");
			}

			recommendations.add(elem);
		}

		model.addAttribute("items2", recommendations);

		model.addAttribute("back", "back");

		ObjectMapper objectMapper = new ObjectMapper();
		// objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		TypeReference<json_algorithm> mapType1 = new TypeReference<json_algorithm>() {
		};
		json_algorithm algorithm = new json_algorithm();

		if (synctable.getSync_json() != null && synctable.getSync_json().trim() != "") {
			try {
				algorithm = objectMapper.readValue(synctable.getSync_json(), mapType1);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			// if audits have been done for the given week, then don't run the algorithm
			final boolean isyear = Calendar.getInstance().get(Calendar.YEAR) == algorithm.getAlg_year();
			final boolean ismnth = Calendar.getInstance().get(Calendar.MONTH) == algorithm.getAlg_month();
			final boolean isweek = Calendar.getInstance().get(Calendar.WEEK_OF_MONTH) == algorithm.getAlg_week();
			final boolean isdone = algorithm.getAlg_totalcases() == Utils.EXPECTED_CASES_PER_WEEK;

			if (isyear && ismnth && isweek && isdone) {
				model.addAttribute("done", "done");
			}
		}

		return "auditing/audit-retrieve";
	}

	@PostMapping("")
	public String selectSpecialCases(Principal principal, Model model) {
		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			return "home";
		}

		sync_table synctable = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		model.addAttribute("myf", synctable.getSync_name());

		autoSelectCases();

		return "redirect:/auditing";
	}

	@GetMapping("/mycases")
	public String manualSelectCases(Principal principal, Model model) {
		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			return "home";
		}

		sync_table synctable = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		model.addAttribute("myf", synctable.getSync_name());

		CaseWrapper selected = new CaseWrapper();
		selected.setId("casewrapper");
		model.addAttribute("selected", selected);

		List<case_identifiers> active_cases = caseRepo.findByPendingCase_status(1);// find cases pending review
		active_cases.addAll(caseRepo.findByPendingCase_status(9));// add archived cases that were not reviewed

		model.addAttribute("active_cases", active_cases);

		return "auditing/audit-list";
	}

	@PostMapping("/mycases")
	public String manualSelectCases(Principal principal, @ModelAttribute("selected") CaseWrapper selected) {
		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			return "home";
		}

		// prepare a mapping reference type for converting the JSON strings to objects
		ObjectMapper objectMapper = new ObjectMapper();
		// objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		TypeReference<List<json_data>> mapType = new TypeReference<List<json_data>>() {
		};
//		TypeReference<json_algorithm> mapType1 = new TypeReference<json_algorithm>() {
//		};

		// create a bucket for the selected cases for auditing
		List<case_identifiers> pendingAudit = selected.getRcases();
//		System.out.println("Id : " + selected.getId());
//		System.out.println("cases size: " + selected.getRcases().size());
//		System.out.println("cases: " + selected.getRcases().get(0));

		List<audit_case> selectedForAuditing = new ArrayList<>();

		for (case_identifiers scase : pendingAudit) {

//			System.out.print("case is: " + scase.getCase_id());
			scase.setCase_status(2);
			caseRepo.save(scase);

			// create a new audit for the case
			audit_case acase = new audit_case();
			acase.setAudit_date(new java.util.Date());
			acase.setAudit_uuid(scase.getCase_uuid());
			acase.setCase_death(scase.getCase_death());

			// extract the json array from the case into a one big list object
			json_list fulldata = new json_list();

			List<json_data> biodata;
			try {
				biodata = objectMapper.readValue(scase.getBiodata().getBiodata_json(), mapType);
				fulldata.setBiodata(biodata);
			} catch (JsonProcessingException e) {

				e.printStackTrace();
			}

			List<json_data> pregdata;
			try {
				pregdata = objectMapper.readValue(scase.getPregnancy().getPregnancy_json(), mapType);
				fulldata.setPregnancy(pregdata);
			} catch (JsonProcessingException e) {

				e.printStackTrace();
			}

			List<json_data> refdata;
			try {
				refdata = objectMapper.readValue(scase.getReferral().getReferral_json(), mapType);
				fulldata.setReferral(refdata);
			} catch (JsonProcessingException e) {

				e.printStackTrace();
			}

			List<json_data> deldata;
			try {
				deldata = objectMapper.readValue(scase.getDelivery().getDelivery_json(), mapType);
				fulldata.setDelivery(deldata);
			} catch (JsonProcessingException e) {

				e.printStackTrace();
			}

			List<json_data> antedata;
			try {
				antedata = objectMapper.readValue(scase.getAntenatal().getAntenatal_json(), mapType);
				fulldata.setAntenatal(antedata);
			} catch (JsonProcessingException e) {

				e.printStackTrace();
			}

			List<json_data> labdata;
			try {
				labdata = objectMapper.readValue(scase.getLabour().getLabour_json(), mapType);
				fulldata.setLabour(labdata);
			} catch (JsonProcessingException e) {

				e.printStackTrace();
			}

			List<json_data> birdata;
			try {
				birdata = objectMapper.readValue(scase.getBirth().getBirth_json(), mapType);
				fulldata.setBirth(birdata);
			} catch (JsonProcessingException e) {

				e.printStackTrace();
			}

			if (scase.getCase_death() == 1 && scase.getFetalheart() != null) {
				List<json_data> fetdata;
				try {
					fetdata = objectMapper.readValue(scase.getFetalheart().getFetalheart_json(), mapType);
					fulldata.setFetalheart(fetdata);
				} catch (JsonProcessingException e) {

					e.printStackTrace();
				}
			}

			if (scase.getCase_death() == 2 && scase.getBabydeath() != null) {
				List<json_data> bdtdata;
				try {
					bdtdata = objectMapper.readValue(scase.getBabydeath().getBaby_json(), mapType);
					fulldata.setBabydeath(bdtdata);
				} catch (JsonProcessingException e) {

					e.printStackTrace();
				}
			}

			List<json_data> notedata;
			try {
				notedata = objectMapper.readValue(scase.getNotes().getNotes_json(), mapType);
				fulldata.setNotes(notedata);
			} catch (JsonProcessingException e) {

				e.printStackTrace();
			}

			// convert the big list back to JSON data String
			String arrayToJson;
			try {
				arrayToJson = objectMapper.writeValueAsString(fulldata);
				// add the combined JSON data to the new audit for the case
				acase.setAudit_data(arrayToJson);
			} catch (JsonProcessingException e) {

				e.printStackTrace();
			}

			// add the new audit for case into the bucket of selected cases for auditing
			selectedForAuditing.add(acase);

		}

		if (selectedForAuditing.size() > 0) {
			try {
				if (InternetAvailabilityChecker.isInternetAvailable()) {

					sync_table sync = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
					emailService.sendSimpleMessage(getRecipients(), "TEST MESSAGE - PDSR NEW REVIEWS NOTIFICATION!",
							"Hello Reviewers,\n" + "\nThere are " + selectedForAuditing.size()
									+ " deaths ready to be reviewed this week" + "\nHealth Facility: "
									+ sync.getSync_name() + " - " + sync.getSync_code()
									+ "\nThis is a PILOT IMPLEMENTATION of the Enhanced Automated PDSR tool developed by Alex and Eliezer");
				}
			} catch (IOException e) {
			}

			acaseRepo.saveAll(selectedForAuditing);
		}

		return "redirect:/auditing";

	}

	// select cases for review
	@Scheduled(cron = "0 0 9 * * 0") // once a week on mondays at 9am
	public void autoSelectCases() {

		// prepare a mapping reference type for converting the JSON strings to objects
		ObjectMapper objectMapper = new ObjectMapper();
		// objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		TypeReference<List<json_data>> mapType = new TypeReference<List<json_data>>() {
		};
		TypeReference<json_algorithm> mapType1 = new TypeReference<json_algorithm>() {
		};

		// find all submitted cases but not audited
		List<case_identifiers> pendingAudit = caseRepo.findByPendingCase_status(1);

		// audited
		// Randomly shuffle the list to be selected from to ensure that each pending
		// case has a fair chance of being selected
		Collections.shuffle(pendingAudit, new Random());
		// audited
		// create a bucket for the selected cases for auditing
		List<audit_case> selectedForAuditing = new ArrayList<>();

		try {
			// Create the temporary deque
			Deque<Integer> tempDeque = new ArrayDeque<>();
			Deque<Integer> persDeque = new ArrayDeque<>();
			persDeque.push(1);
			persDeque.push(2);
			persDeque.push(3);
			persDeque.push(4);

			// Fetch the persistent deque
			sync_table synctable = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
			json_algorithm algorithm = (synctable.getSync_json() != null && synctable.getSync_json().trim() != "")
					? objectMapper.readValue(synctable.getSync_json(), mapType1)
					: new json_algorithm();

			if (algorithm.getAlg_deque() != null) {

				persDeque = algorithm.getAlg_deque();
			}

			// get the week of auditing
			int auditweek = (Calendar.getInstance().get(Calendar.WEEK_OF_MONTH) % 4);

			// get the number of neonatal audits to be done for that week
			int neonatalCount = Utils.PRIORITY_MATRIX[auditweek][1];
			int totalneonatal = algorithm.getAlg_neonatal() == null ? 0 : algorithm.getAlg_neonatal();

			for (int counter = totalneonatal; counter < neonatalCount;) {

				Integer nextPriority = persDeque.pollLast();

				a: for (case_identifiers scase : pendingAudit) {

					if (scase.getCase_death() != 2 || scase.getBabydeath() == null) {
						continue a;
					}

					if (scase.getBabydeath().getBaby_ddate() == null) {
						continue a;
					}

					Calendar reviewDate = Calendar.getInstance();
					final Date dth = scase.getBabydeath().getBaby_ddate();
					Calendar deathDate = Calendar.getInstance();
					deathDate.setTime(dth);

//					final boolean isyear1 = Calendar.getInstance().get(Calendar.YEAR) == cal.get(Calendar.YEAR);
//					final boolean isweek1 = (Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)
//							- cal.get(Calendar.WEEK_OF_YEAR)) == 1;
//
//					final boolean isyear2 = Calendar.getInstance().get(Calendar.YEAR) - cal.get(Calendar.YEAR) == 1;
//					final boolean isweek2 = cal.get(Calendar.WEEK_OF_YEAR)
//							- (Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)) == 51;

					// check whether the death is a recent one that falls within the previous week
					// before the review
					Calendar minus2Weeks = (Calendar) reviewDate.clone();
					minus2Weeks.add(Calendar.WEEK_OF_MONTH, -2);
					Calendar minus1Weeks = (Calendar) reviewDate.clone();
					minus1Weeks.add(Calendar.WEEK_OF_MONTH, -1);
					final boolean isvalid = deathDate.after(minus2Weeks.getTime())
							&& deathDate.before(minus1Weeks.getTime());
					// (isyear1 && isweek1) || (isyear2 && isweek2);

					if (!isvalid) {
						continue a;
					}

					Integer medicalcode = scase.getBabydeath().getBaby_medicalcod();

					if (nextPriority != medicalcode) {
						continue a;
					}

					// mark as selected case for review
					scase.setCase_status(2);
					caseRepo.save(scase);

					// create a new audit for the case
					audit_case acase = new audit_case();
					acase.setAudit_date(new java.util.Date());
					acase.setAudit_uuid(scase.getCase_uuid());
					acase.setCase_death(scase.getCase_death());

					// extract the json array from the case into a one big list object
					json_list fulldata = new json_list();

					List<json_data> biodata = objectMapper.readValue(scase.getBiodata().getBiodata_json(), mapType);
					fulldata.setBiodata(biodata);

					List<json_data> pregdata = objectMapper.readValue(scase.getPregnancy().getPregnancy_json(),
							mapType);
					fulldata.setPregnancy(pregdata);

					List<json_data> refdata = objectMapper.readValue(scase.getReferral().getReferral_json(), mapType);
					fulldata.setReferral(refdata);

					List<json_data> deldata = objectMapper.readValue(scase.getDelivery().getDelivery_json(), mapType);
					fulldata.setDelivery(deldata);

					List<json_data> antedata = objectMapper.readValue(scase.getAntenatal().getAntenatal_json(),
							mapType);
					fulldata.setAntenatal(antedata);

					List<json_data> labdata = objectMapper.readValue(scase.getLabour().getLabour_json(), mapType);
					fulldata.setLabour(labdata);

					List<json_data> birdata = objectMapper.readValue(scase.getBirth().getBirth_json(), mapType);
					fulldata.setBirth(birdata);

					if (scase.getCase_death() == 1) {
						List<json_data> fetdata = objectMapper.readValue(scase.getFetalheart().getFetalheart_json(),
								mapType);
						fulldata.setFetalheart(fetdata);
					}

					if (scase.getCase_death() == 2) {
						List<json_data> bdtdata = objectMapper.readValue(scase.getBabydeath().getBaby_json(), mapType);
						fulldata.setBabydeath(bdtdata);
					}

					List<json_data> notedata = objectMapper.readValue(scase.getNotes().getNotes_json(), mapType);
					fulldata.setNotes(notedata);

					// convert the big list back to JSON data String
					final String arrayToJson = objectMapper.writeValueAsString(fulldata);

					// add the combined JSON data to the new audit for the case
					acase.setAudit_data(arrayToJson);

					// add the new audit for case into the bucket of selected cases for auditing
					selectedForAuditing.add(acase);

					totalneonatal = selectedForAuditing.size();

					pendingAudit.remove(scase);

					persDeque.addFirst(nextPriority);
					counter++;

					break;
				}

				if (nextPriority != null && !persDeque.contains(nextPriority)) {
					tempDeque.addFirst(nextPriority);
				}

				if (persDeque.isEmpty()) {
					counter++;
				}

			}

			if (!tempDeque.isEmpty()) {
				persDeque.addAll(tempDeque);
			}

			/// still birth
			// get the number of neonatal audits to be done for that week
			int stillCount = Utils.PRIORITY_MATRIX[auditweek][0];

			int totalStill = algorithm.getAlg_stillbirth() == null ? 0 : algorithm.getAlg_stillbirth();

			for (int counter = totalStill; counter < stillCount; counter++) {

				case_identifiers intra = null;
				case_identifiers antep = null;

				a: for (case_identifiers tcase : pendingAudit) {

					if (tcase.getCase_death() != 1 || tcase.getFetalheart() == null) {
						continue a;
					}

					if (tcase.getDelivery().getDelivery_date() == null) {
						continue a;
					}

					Calendar reviewDate = Calendar.getInstance();
					final Date dth = tcase.getDelivery().getDelivery_date();
					Calendar deathDate = Calendar.getInstance();
					deathDate.setTime(dth);

					// check whether the death is a recent one that falls within the previous week
					// before the review
//					final boolean isyear1 = Calendar.getInstance().get(Calendar.YEAR) == cal.get(Calendar.YEAR);
//					final boolean isweek1 = (Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)
//							- cal.get(Calendar.WEEK_OF_YEAR)) == 1;
//
//					final boolean isyear2 = Calendar.getInstance().get(Calendar.YEAR) - cal.get(Calendar.YEAR) == 1;
//					final boolean isweek2 = cal.get(Calendar.WEEK_OF_YEAR)
//							- (Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)) == 51;
					Calendar minus2Weeks = (Calendar) reviewDate.clone();
					minus2Weeks.add(Calendar.WEEK_OF_MONTH, -2);
					Calendar minus1Weeks = (Calendar) reviewDate.clone();
					minus1Weeks.add(Calendar.WEEK_OF_MONTH, -1);
					final boolean isvalid = deathDate.after(minus2Weeks.getTime())
							&& deathDate.before(minus1Weeks.getTime());
					// (isyear1 && isweek1) || (isyear2 && isweek2);

					if (!isvalid) {
						continue a;
					}

					final Integer stillcase = tcase.getFetalheart().getFetalheart_lastheard();
					if (stillcase > 2) {// if it is not intrapartum then store it aside and skip
						antep = tcase;
					} else {// once an intrapartum case is detected, break and register it
						intra = tcase;
						break;
					}

				}

				case_identifiers scase = null;
				// add the new audit for case into the bucket of selected cases for auditing
				if (intra != null) {
					scase = intra;
				} else if (antep != null) {
					scase = antep;
				}

				if (scase != null) {

					// mark as selected case for review
					scase.setCase_status(2);
					caseRepo.save(scase);

					// create a new audit for the case
					audit_case acase = new audit_case();
					acase.setAudit_date(new java.util.Date());
					acase.setAudit_uuid(scase.getCase_uuid());
					acase.setCase_death(scase.getCase_death());

					// extract the json array from the case into a one big list object
					json_list fulldata = new json_list();

					List<json_data> biodata = objectMapper.readValue(scase.getBiodata().getBiodata_json(), mapType);
					fulldata.setBiodata(biodata);

					List<json_data> pregdata = objectMapper.readValue(scase.getPregnancy().getPregnancy_json(),
							mapType);
					fulldata.setPregnancy(pregdata);

					List<json_data> refdata = objectMapper.readValue(scase.getReferral().getReferral_json(), mapType);
					fulldata.setReferral(refdata);

					List<json_data> deldata = objectMapper.readValue(scase.getDelivery().getDelivery_json(), mapType);
					fulldata.setDelivery(deldata);

					List<json_data> antedata = objectMapper.readValue(scase.getAntenatal().getAntenatal_json(),
							mapType);
					fulldata.setAntenatal(antedata);

					List<json_data> labdata = objectMapper.readValue(scase.getLabour().getLabour_json(), mapType);
					fulldata.setLabour(labdata);

					List<json_data> birdata = objectMapper.readValue(scase.getBirth().getBirth_json(), mapType);
					fulldata.setBirth(birdata);

					if (scase.getCase_death() == 1) {
						List<json_data> fetdata = objectMapper.readValue(scase.getFetalheart().getFetalheart_json(),
								mapType);
						fulldata.setFetalheart(fetdata);
					}

					if (scase.getCase_death() == 2) {
						List<json_data> bdtdata = objectMapper.readValue(scase.getBabydeath().getBaby_json(), mapType);
						fulldata.setBabydeath(bdtdata);
					}

					List<json_data> notedata = objectMapper.readValue(scase.getNotes().getNotes_json(), mapType);
					fulldata.setNotes(notedata);

					// convert the big list back to JSON data String
					final String arrayToJson = objectMapper.writeValueAsString(fulldata);

					// add the combined JSON data to the new audit for the case
					acase.setAudit_data(arrayToJson);
					totalStill++;

					selectedForAuditing.add(acase);

					pendingAudit.remove(scase);// exclude the selected case from the next search
				}

			}

			// save the new state of the persistent deque
			algorithm.setAlg_date(new java.util.Date());
			algorithm.setAlg_year(Calendar.getInstance().get(Calendar.YEAR));
			algorithm.setAlg_month(Calendar.getInstance().get(Calendar.MONTH));
			algorithm.setAlg_week(Calendar.getInstance().get(Calendar.WEEK_OF_MONTH));
			algorithm.setAlg_modulo(auditweek);
			algorithm.setAlg_neonatal(totalneonatal);
			algorithm.setAlg_stillbirth(totalStill);
			algorithm.setAlg_totalcases(totalneonatal + totalStill);

			algorithm.setAlg_deque(persDeque);
			final String arrayToJson = objectMapper.writeValueAsString(algorithm);
			synctable.setSync_json(arrayToJson);
			syncRepo.save(synctable);

			if (selectedForAuditing.size() > 0) {
				try {
					if (InternetAvailabilityChecker.isInternetAvailable()) {

						sync_table sync = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
						emailService.sendSimpleMessage(getRecipients(), "TEST MESSAGE - PDSR NEW REVIEWS NOTIFICATION!",
								"Hello Reviewers,\n" + "\nThere are " + selectedForAuditing.size()
										+ " deaths ready to be reviewed this week" + "\nHealth Facility: "
										+ sync.getSync_name() + " - " + sync.getSync_code()
										+ "\nThis is a PILOT IMPLEMENTATION of the Enhanced Automated PDSR tool developed by Alex and Eliezer");
					}
				} catch (IOException e) {
				}

				acaseRepo.saveAll(selectedForAuditing);
			}

			// mark all unselected cases for archiving
			if (pendingAudit != null && !pendingAudit.isEmpty()) {
				pendingAudit.forEach(v -> {
					v.setCase_status(CONSTANTS.ARCHIVED_CASE);
				});

				caseRepo.saveAll(pendingAudit);
			}

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/edit/{id}")
	public String submit(Principal principal, Model model, @PathVariable("id") String case_uuid,
			@RequestParam(name = "success", required = false) String success,
			@RequestParam(name = "error", required = false) String error) {

		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table synctable = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		model.addAttribute("myf", synctable.getSync_name());

		if (success != null) {
			model.addAttribute("success", "Saved Successfully");
		} else if (error != null) {
			model.addAttribute("error", "No maternal condition has been selected from the (2.M.Cond) section");
		}
		// load the ICD 10 codes

		ObjectMapper objectMapper = new ObjectMapper();
		// objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		TypeReference<json_list> mapType = new TypeReference<json_list>() {
		};

		audit_case acase = acaseRepo.findById(case_uuid).get();
		Optional<audit_audit> selected = tcaseRepo.findById(case_uuid);

		if (selected.isPresent()) {
			audit_audit tcase = selected.get();

			model.addAttribute("selected", tcase);

			if (icdRepo.findById(tcase.getAudit_icd10()).isPresent()) {
				model.addAttribute("icdsel", icdRepo.findById(tcase.getAudit_icd10()).get());
			}

			final Integer death = tcase.getAudit_death();
			switch (death) {
			case 1: {
				if (!icdRepo.findIntrapartumPMByICD(tcase.getAudit_icdpm()).isEmpty()) {
					icd_codes icd = icdRepo.findIntrapartumPMByICD(tcase.getAudit_icdpm()).get(0);
					model.addAttribute("pmsel", new icdpm(icd.getIcd_pmi(), icd.getIcd_pmi_desc()));
				}
				break;
			}
			case 2: {
				if (!icdRepo.findAntepartumPMByICD(tcase.getAudit_icdpm()).isEmpty()) {
					icd_codes icd = icdRepo.findAntepartumPMByICD(tcase.getAudit_icdpm()).get(0);
					model.addAttribute("pmsel", new icdpm(icd.getIcd_pma(), icd.getIcd_pma_desc()));
				}
				break;
			}
			case 3: {
				if (!icdRepo.findNeonatalPMByICD(tcase.getAudit_icdpm()).isEmpty()) {
					icd_codes icd = icdRepo.findNeonatalPMByICD(tcase.getAudit_icdpm()).get(0);
					model.addAttribute("pmsel", new icdpm(icd.getIcd_pmn(), icd.getIcd_pmn_desc()));
				}
				break;
			}
			}

		} else {
			audit_audit tcase = new audit_audit();
			tcase.setAudit_uuid(case_uuid);
			tcase.setAudit_case(acase);
			model.addAttribute("selected", tcase);

		}

		try {

			json_list dataset = objectMapper.readValue(acase.getAudit_data(), mapType);

			model.addAttribute("casebiodata", dataset.getBiodata());
			model.addAttribute("casepregnancy", dataset.getPregnancy());
			model.addAttribute("casereferral", dataset.getReferral());
			model.addAttribute("casedelivery", dataset.getDelivery());
			model.addAttribute("caseantenatal", dataset.getAntenatal());
			model.addAttribute("caselabour", dataset.getLabour());
			model.addAttribute("casebirth", dataset.getBirth());
			model.addAttribute("casefetalheart", dataset.getFetalheart());
			model.addAttribute("casebabydeath", dataset.getBabydeath());
			model.addAttribute("casenotes", dataset.getNotes());

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		model.addAttribute("factor_patient", cfactRepo.findByIdgroup(100));
		model.addAttribute("factor_transport", cfactRepo.findByIdgroup(200));
		model.addAttribute("factor_administrative", cfactRepo.findByIdgroup(300));
		model.addAttribute("factor_healthworker", cfactRepo.findByIdgroup(400));
		model.addAttribute("factor_document", cfactRepo.findByIdgroup(500));

		Map<mcgroup_table, List<mcondition_table>> map = new LinkedHashMap<>();
		map.put(mcgrpRepo.findById("M1").get(), mcondRepo.findByIcdmgroup("M1"));
		map.put(mcgrpRepo.findById("M2").get(), mcondRepo.findByIcdmgroup("M2"));
		map.put(mcgrpRepo.findById("M3").get(), mcondRepo.findByIcdmgroup("M3"));
		map.put(mcgrpRepo.findById("M4").get(), mcondRepo.findByIcdmgroup("M4"));
		// map.put(mcgrpRepo.findById("M5").get(), mcondRepo.findByIcdmgroup("M5"));

		model.addAttribute("mcond_options", map);

		model.addAttribute("facility_code", caseRepo.findById(case_uuid).get().getFacility().getFacility_code());

		return "auditing/audit-create";
	}

	@Transactional
	@PostMapping("/edit/{id}")
	public String submit(Principal principal, @ModelAttribute("selected") audit_audit selected,
			@PathVariable("id") String case_uuid) {

		ObjectMapper objectMapper = new ObjectMapper();
		// objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {
			selected.setAudit_uuid(case_uuid);
			selected.setAudit_cdate(new java.util.Date());
			selected.setAudit_case(acaseRepo.findById(case_uuid).get());
			selected.setAudit_csc("None");
			selected.setData_sent(0);

			String arrayToJson;
			arrayToJson = objectMapper.writeValueAsString(processListOf(selected));
			selected.setAudit_json(arrayToJson);
			tcaseRepo.save(selected);

			// mark as reviewed case for data transfer purposes
			case_identifiers c = caseRepo.findById(case_uuid).get();
			c.setCase_status(3);
			caseRepo.save(c);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "auditing/audit-create";
		}

		if (selected.getMaternal_condition() == 1
				&& (selected.getMaternal_conditions() == null || selected.getMaternal_conditions().isEmpty())) {
			return "redirect:/auditing/edit/" + case_uuid + "?error=yes";
		}

		return "redirect:/auditing/edit/" + case_uuid + "?success=yes";
	}

	@GetMapping("/recommend/{id}")
	public String recommendation(Principal principal, Model model, @PathVariable("id") String case_uuid,
			@RequestParam(required = false) String success) {

		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table synctable = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		model.addAttribute("myf", synctable.getSync_name());

		if (success != null) {
			model.addAttribute("success", "Saved Successfully");
		}
		// load the ICD 10 codes

		ObjectMapper objectMapper = new ObjectMapper();
		// objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		TypeReference<List<json_data>> mapType = new TypeReference<List<json_data>>() {
		};

		Optional<audit_audit> audit = tcaseRepo.findById(case_uuid);

		List<json_data> dataset;
		try {
			dataset = objectMapper.readValue(audit.get().getAudit_json(), mapType);
		} catch (JsonProcessingException e) {
			dataset = new ArrayList<>();
			e.printStackTrace();
		}
		model.addAttribute("caseaudited", dataset);
		List<audit_recommendation> recommendations = new ArrayList<>();
		for (audit_recommendation elem : audit.get().getRecommendations()) {

			if (elem.getRecommendation_status() == 2) {
				elem.setRec_color("table-success");

			} else if (elem.getRecommendation_date().before(elem.getRecommendation_deadline())) {// date passed but not
																									// completed

				if (elem.getRecommendation_status() == 1) {
					elem.setRec_color("table-warning");
				} else {
					elem.setRec_color("table-light");
				}

			} else {
				elem.setRec_color("table-danger");
			}

			recommendations.add(elem);
		}

		model.addAttribute("recommendations", recommendations);

		audit_recommendation selected = new audit_recommendation();
		selected.setRecommendation_uuid(UUID.randomUUID().toString());
		selected.setAudit_uuid(audit.get());

		model.addAttribute("selected", selected);

		return "auditing/audit-recommend";

	}

	@Transactional
	@PostMapping("/recommend/{id}")
	public String recommendation(Principal principal, @ModelAttribute("selected") audit_recommendation selected,
			@PathVariable("id") String case_uuid) {

		audit_audit audit = tcaseRepo.findById(case_uuid).get();
		if (audit.getRecommendations() == null) {
			audit.setRecommendations(new ArrayList<>());
		}

		selected.setRecommendation_uuid(UUID.randomUUID().toString());
		selected.setRecommendation_date(new java.util.Date());
		selected.setRecommendation_status(0);
		selected.setData_sent(0);
		selected.setRecommendation_comments("comments");
		selected.setAudit_uuid(audit);

		audit.getRecommendations().add(selected);

		tcaseRepo.save(audit);

		return "redirect:/auditing/recommend/" + case_uuid + "?success=yes";
	}

	@GetMapping("/recommend/complete/{id}")
	public String recommendation(Principal principal, @PathVariable("id") String case_uuid) {

		audit_audit audit = tcaseRepo.findById(case_uuid).get();
		audit.setRec_complete(1);

		tcaseRepo.save(audit);

		return "redirect:/auditing?success=yes";
	}

	@GetMapping("/cstatus/{id}")
	public String recommendStarted(Principal principal, Model model, @PathVariable("id") String recommendation_uuid,
			@RequestParam(name = "success", required = false) String success) {

		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table synctable = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		model.addAttribute("myf", synctable.getSync_name());

		audit_recommendation selected = rcaseRepo.findById(recommendation_uuid).get();
		if (selected.getRecommendation_status() == 2) {
			selected.setRec_color("alert alert-success");

		} else if (new java.util.Date().before(selected.getRecommendation_deadline())) {// date passed but
																						// not
			// completed

			if (selected.getRecommendation_status() == 1) {
				selected.setRec_color("alert alert-warning");
			} else {
				selected.setRec_color("alert alert-light");
			}

		} else {
			selected.setRec_color("alert alert-danger");
		}

		model.addAttribute("selected", selected);

		if (success != null) {
			model.addAttribute("success", "Saved Successfully");
		}

		return "auditing/audit-status";
	}

	@PostMapping("/cstatus/{id}")
	public String recommendStarted(Principal principal, @ModelAttribute("selected") audit_recommendation selected,
			@PathVariable("id") String recommendation_uuid) {

		audit_recommendation recommend = rcaseRepo.findById(recommendation_uuid).get();
		recommend.setRecommendation_status(selected.getRecommendation_status());
		recommend.setRecommendation_comments(selected.getRecommendation_comments());
		selected.setData_sent(0);

		rcaseRepo.save(recommend);

		return "redirect:/auditing/cstatus/" + recommendation_uuid + "?success=yes";
	}

	@GetMapping(value = "/icdcodes")
	public @ResponseBody List<icd_codes> findICDCodes(
			@RequestParam(value = "audit_death", required = true) Integer audit_death) {

		if (audit_death == 1) {

			return icdRepo.findIntrapartumICD("xx");

		} else if (audit_death == 2) {

			return icdRepo.findAntepartumICD("xx");

		} else if (audit_death == 3) {

			return icdRepo.findNeonatalICD("xx");
		}

		return new ArrayList<>();
	}

	@GetMapping(value = "/pmcodes")
	public @ResponseBody Set<icdpm> findPMCodes(
			@RequestParam(value = "audit_death", required = true) Integer audit_death) {

		if (audit_death == 1) {

			Set<icdpm> pmset = new LinkedHashSet<>();

			for (icd_codes elem : icdRepo.findIntrapartumICD("xx")) {
				pmset.add(new icdpm(elem.getIcd_pmi(), elem.getIcd_pmi_desc()));
			}

			return pmset;

		} else if (audit_death == 2) {

			Set<icdpm> pmset = new LinkedHashSet<>();

			for (icd_codes elem : icdRepo.findAntepartumICD("xx")) {
				pmset.add(new icdpm(elem.getIcd_pma(), elem.getIcd_pma_desc()));
			}

			return pmset;

		} else if (audit_death == 3) {

			Set<icdpm> pmset = new LinkedHashSet<>();

			for (icd_codes elem : icdRepo.findNeonatalICD("xx")) {
				pmset.add(new icdpm(elem.getIcd_pmn(), elem.getIcd_pmn_desc()));
			}

			return pmset;

		}

		return new LinkedHashSet<icdpm>();
	}

	@GetMapping(value = "/pmselect")
	public @ResponseBody icdpm findPMCode(@RequestParam(value = "audit_death", required = true) Integer audit_death,
			@RequestParam(value = "audit_icd10", required = true) String audit_icd10) {

		if (!"".equals(audit_icd10)) {
			icd_codes icd = icdRepo.findPMByICD(audit_icd10).get();

			if (audit_death == 1) {
				return new icdpm(icd.getIcd_pmi(), icd.getIcd_pmi_desc());

			} else if (audit_death == 2) {
				return new icdpm(icd.getIcd_pma(), icd.getIcd_pma_desc());

			} else if (audit_death == 3) {
				return new icdpm(icd.getIcd_pmn(), icd.getIcd_pmn_desc());

			}
		}
		return new icdpm(null, null);
	}

	@ModelAttribute("cstatus_options")
	public Map<Integer, String> cstatusOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("cstatus_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("death_options")
	public Map<Integer, String> deathOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(0, "Select one");
		map.put(1, getQuestion("label.still.birth.intra"));
		map.put(2, getQuestion("label.still.birth.antep"));
		map.put(3, getQuestion("label.neonatal.death"));

		return map;
	}

	@ModelAttribute("yesnodk_options")
	public Map<Integer, String> yesnodkOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(1, getQuestion("label.yes"));
		map.put(2, getQuestion("label.no"));

		return map;
	}

	@ModelAttribute("yesnomc_options")
	public Map<Integer, String> yesnmcOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(1, getQuestion("label.yes"));
		map.put(2, "M5: No maternal condition identified (healthy mother)");

		return map;
	}

	private String getQuestion(String code) {
		return msg.getMessage(code, null, Locale.getDefault());
	}

	private String getAnswer(String feature, Integer value) {
		if (mapRepo.findById(new datamapPK(feature, value)).isPresent()) {
			return mapRepo.findById(new datamapPK(feature, value)).get().getMap_label();
		}

		return "NA";
	}

//	private String getIcdDesc(String icd) {
//		if (icd != null) {
//			return icd + " : " + icdRepo.findById(icd).get().getIcd_desc();
//		} else {
//			return "NA";
//		}
//	}
//
//	private String getPMDesc(final Integer death, final String pm) {
//		switch (death) {
//		case 1: {
//			if (!icdRepo.findIntrapartumPMByICD(pm).isEmpty()) {
//				icd_codes icd = icdRepo.findIntrapartumPMByICD(pm).get(0);
//				return pm + " : " + icd.getIcd_pmi_desc();
//			}
//		}
//		case 2: {
//			if (!icdRepo.findAntepartumPMByICD(pm).isEmpty()) {
//				icd_codes icd = icdRepo.findAntepartumPMByICD(pm).get(0);
//				return pm + " : " + icd.getIcd_pma_desc();
//			}
//		}
//		case 3: {
//			if (!icdRepo.findNeonatalPMByICD(pm).isEmpty()) {
//				icd_codes icd = icdRepo.findNeonatalPMByICD(pm).get(0);
//				return pm + " : " + icd.getIcd_pmi_desc();
//			}
//		}
//		default: {
//			return "NA";
//		}
//		}
//
//	}

	private List<json_data> processListOf(audit_audit o) {

		final String[] patientFactors = new String[1];
		patientFactors[0] = "";
		o.getPatient_factors().forEach(v -> {
			patientFactors[0] += (v.getCfactor_name() != null) ? (v.getCfactor_name() + "; ") : "";
		});

		final String[] maternalConditions = new String[1];
		maternalConditions[0] = "";
		o.getMaternal_conditions().forEach(v -> {
			maternalConditions[0] += (v.getIcdm_name() != null) ? (v.getIcdm_name() + "; ") : "";
		});

		final String[] transportFactors = new String[1];
		transportFactors[0] = "";
		o.getTransport_factors().forEach(v -> {
			transportFactors[0] += (v.getCfactor_name() != null) ? (v.getCfactor_name() + "; ") : "";
		});

		final String[] administrativeFactors = new String[1];
		administrativeFactors[0] = "";
		o.getAdministrative_factors().forEach(v -> {
			administrativeFactors[0] += (v.getCfactor_name() != null) ? (v.getCfactor_name() + "; ") : "";
		});

		final String[] healthworkerFactors = new String[1];
		healthworkerFactors[0] = "";
		o.getHealthworker_factors().forEach(v -> {
			healthworkerFactors[0] += (v.getCfactor_name() != null) ? (v.getCfactor_name() + "; ") : "";
		});

		final String[] documentFactors = new String[1];
		documentFactors[0] = "";
		o.getDocument_factors().forEach(v -> {
			documentFactors[0] += (v.getCfactor_name() != null) ? (v.getCfactor_name() + "; ") : "";
		});

		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		List<json_data> list = Stream.of(
//				new json_data(getQuestion("label.audit_death"), getAnswer("adeath_options", o.getAudit_death())),
//				new json_data(getQuestion("label.audit_icd10"), getIcdDesc(o.getAudit_icd10())),
//				new json_data(getQuestion("label.audit_icdpm"), getPMDesc(o.getAudit_death(), o.getAudit_icdpm())),
//				new json_data(getQuestion("label.audit_csc"), o.getAudit_csc()),
//				new json_data(getQuestion("label.audit_maternal"), maternalConditions[0], true),
				new json_data(getQuestion("label.audit_delay1"), getAnswer("yesnodk_options", o.getAudit_delay1()),
						true),
				new json_data(getQuestion("label.audit_delay2"), getAnswer("yesnodk_options", o.getAudit_delay2()),
						true),
				new json_data(getQuestion("label.audit_delay3a"), getAnswer("yesnodk_options", o.getAudit_delay3a()),
						true),
				new json_data(getQuestion("label.audit_delay3b"), getAnswer("yesnodk_options", o.getAudit_delay3b()),
						true),
				new json_data(getQuestion("label.audit_delay3b"), getAnswer("yesnodk_options", o.getAudit_delay3c()),
						true),
				new json_data(getQuestion("label.audit_ifcmfs"), o.getAudit_ifcmfs(), true),
				new json_data(getQuestion("label.audit_patient"), patientFactors[0], true),
				new json_data(getQuestion("label.audit_sysmfs"), o.getAudit_sysmfs(), true),
				new json_data(getQuestion("label.audit_transport"), transportFactors[0], true),
				new json_data(getQuestion("label.audit_facmfs"), o.getAudit_facmfs(), true),
				new json_data(getQuestion("label.audit_administrative"), administrativeFactors[0], true),
				new json_data(getQuestion("label.audit_hwkmfs"), o.getAudit_hwkmfs(), true),
				new json_data(getQuestion("label.audit_healthworker"), healthworkerFactors[0], true),
				new json_data(getQuestion("label.audit_document"), documentFactors[0], true),
				new json_data(getQuestion("label.audit_cdate"), df.format(o.getAudit_cdate()), true))
				.collect(Collectors.toList());

		return list;
	}

	@GetMapping("/file/notes/{id}")
	@ResponseBody
	public void notesFile(@PathVariable("id") String id, HttpServletResponse response) {
		case_identifiers selected = caseRepo.findById(id).get();

		try {
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
					(new StringBuilder()).append("inline;filename=\"").append("case_summray").append("\"").toString());

			response.setContentType(selected.getNotes().getNotes_filetype());

			java.io.OutputStream out = response.getOutputStream();
			IOUtils.copy(new ByteArrayInputStream(selected.getNotes().getNotes_file()), out);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@GetMapping("/file/referral/{id}")
	@ResponseBody
	public void referralFile(@PathVariable("id") String id, HttpServletResponse response) {
		case_identifiers selected = caseRepo.findById(id).get();

		try {
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, (new StringBuilder()).append("inline;filename=\"")
					.append("referral_notes").append("\"").toString());

			response.setContentType(selected.getReferral().getReferral_filetype());

			java.io.OutputStream out = response.getOutputStream();
			IOUtils.copy(new ByteArrayInputStream(selected.getReferral().getReferral_file()), out);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}// end class