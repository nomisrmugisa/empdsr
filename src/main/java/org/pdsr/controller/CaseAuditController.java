package org.pdsr.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.pdsr.CONSTANTS;
import org.pdsr.json.json_data;
import org.pdsr.json.json_list;
import org.pdsr.model.audit_audit;
import org.pdsr.model.audit_case;
import org.pdsr.model.audit_recommendation;
import org.pdsr.model.case_identifiers;
import org.pdsr.model.datamapPK;
import org.pdsr.model.icd_codes;
import org.pdsr.pojos.icdpm;
import org.pdsr.repo.AuditAuditRepository;
import org.pdsr.repo.AuditCaseRepository;
import org.pdsr.repo.AuditRecommendRepository;
import org.pdsr.repo.CaseRepository;
import org.pdsr.repo.DatamapRepository;
import org.pdsr.repo.IcdCodesRepository;
import org.pdsr.repo.SyncTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Controller
@RequestMapping("/auditing")
public class CaseAuditController {

	@Autowired
	private SyncTableRepository syncRepo;

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

	@GetMapping("")
	public String list(Principal principal, Model model) {

		if (syncRepo.findById(CONSTANTS.FACILITY_ID).isEmpty()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		model.addAttribute("items", acaseRepo.findByPendingAudit());
		model.addAttribute("items1", tcaseRepo.findByPendingRecommendation());

		List<audit_recommendation> recommendations = new ArrayList<>();
		for (audit_recommendation elem : rcaseRepo.findAll()) {

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
				elem.setBg_color("fw-bold text-danger");
			}

			recommendations.add(elem);
		}

		model.addAttribute("items2", recommendations);

		model.addAttribute("back", "back");

		return "auditing/audit-retrieve";
	}

	@PostMapping("")
	public String list(Principal principal) {
		// prepare a mapping reference type for converting the JSON strings to objects
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		TypeReference<List<json_data>> mapType = new TypeReference<List<json_data>>() {
		};

		List<case_identifiers> pendingAudit = caseRepo.findByPendingCase_status(1);// find all submitted cases but not
																					// audited.
		// the DB will apply the algorithm before selecting the pending cases. so the
		// pending cases will always be audit size limit

		// create a bucket for the selected cases for auditing
		List<audit_case> selectedForAuditing = new ArrayList<>();

		try {
			for (case_identifiers scase : pendingAudit) {

				// create a new audit for the case
				audit_case acase = new audit_case();
				acase.setAudit_date(new java.util.Date());
				acase.setAudit_uuid(scase.getCase_uuid());
				acase.setCase_death(scase.getCase_death());

				// extract the json array from the case into a one big list object
				json_list fulldata = new json_list();

				List<json_data> biodata = objectMapper.readValue(scase.getBiodata().getBiodata_json(), mapType);
				fulldata.setBiodata(biodata);

				List<json_data> pregdata = objectMapper.readValue(scase.getPregnancy().getPregnancy_json(), mapType);
				fulldata.setPregnancy(pregdata);

				List<json_data> refdata = objectMapper.readValue(scase.getReferral().getReferral_json(), mapType);
				fulldata.setReferral(refdata);

				List<json_data> deldata = objectMapper.readValue(scase.getDelivery().getDelivery_json(), mapType);
				fulldata.setDelivery(deldata);

				List<json_data> antedata = objectMapper.readValue(scase.getAntenatal().getAntenatal_json(), mapType);
				fulldata.setAntenatal(antedata);

				List<json_data> labdata = objectMapper.readValue(scase.getLabour().getLabour_json(), mapType);
				fulldata.setLabour(labdata);

				List<json_data> birdata = objectMapper.readValue(scase.getBirth().getBirth_json(), mapType);
				fulldata.setBirth(birdata);

				List<json_data> fetdata = objectMapper.readValue(scase.getFetalheart().getFetalheart_json(), mapType);
				fulldata.setFetalheart(fetdata);

				List<json_data> notedata = objectMapper.readValue(scase.getNotes().getNotes_json(), mapType);
				fulldata.setNotes(notedata);

				// convert the big list back to JSON data String
				final String arrayToJson = objectMapper.writeValueAsString(fulldata);

				// add the combined JSON data to the new audit for the case
				acase.setAudit_data(arrayToJson);

				// add the new audit for case into the bucket of selected cases for auditing
				selectedForAuditing.add(acase);

			}

			// save them to the audit_case
			acaseRepo.saveAll(selectedForAuditing);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/auditing";
	}

	@GetMapping("/edit/{id}")
	public String submit(Principal principal, Model model, @PathVariable("id") String case_uuid,
			@RequestParam(name = "success", required = false) String success) {

		if (syncRepo.findById(CONSTANTS.FACILITY_ID).isEmpty()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		if (success != null) {
			model.addAttribute("success", "Saved Successfully");
		}
		// load the ICD 10 codes

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
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
			model.addAttribute("casenotes", dataset.getNotes());

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		model.addAttribute("facility_code", caseRepo.findById(case_uuid).get().getFacility().getFacility_code());

		return "auditing/audit-create";
	}

	@PostMapping("/edit/{id}")
	public String submit(Principal principal, @ModelAttribute("selected") audit_audit selected,
			@PathVariable("id") String case_uuid) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		try {
			selected.setAudit_uuid(case_uuid);
			selected.setAudit_cdate(new java.util.Date());
			selected.setAudit_case(acaseRepo.findById(case_uuid).get());

			String arrayToJson;
			arrayToJson = objectMapper.writeValueAsString(processListOf(selected));
			selected.setAudit_json(arrayToJson);
			tcaseRepo.save(selected);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "auditing/audit-create";
		}

		return "redirect:/auditing?success=yes";
	}

	@GetMapping("/recommend/{id}")
	public String recommendation(Principal principal, Model model, @PathVariable("id") String case_uuid,
			@RequestParam(name = "success", required = false) String success) {

		if (syncRepo.findById(CONSTANTS.FACILITY_ID).isEmpty()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		if (success != null) {
			model.addAttribute("success", "Saved Successfully");
		}
		// load the ICD 10 codes

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
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
		selected.setAudit_uuid(audit);

		audit.getRecommendations().add(selected);

		tcaseRepo.save(audit);

		return "redirect:/auditing/recommend/" + case_uuid + "?success=yes";
	}

	@GetMapping("/recommend/started/{id}")
	public String recommendStarted(Principal principal, @PathVariable("id") String recommendation_uuid) {

		audit_recommendation recommend = rcaseRepo.findById(recommendation_uuid).get();
		recommend.setRecommendation_status(1);
		rcaseRepo.save(recommend);

		return "redirect:/auditing";
	}

	@GetMapping("/recommend/completed/{id}")
	public String recommendCompleted(Principal principal, @PathVariable("id") String recommendation_uuid) {

		audit_recommendation recommend = rcaseRepo.findById(recommendation_uuid).get();
		recommend.setRecommendation_status(2);
		rcaseRepo.save(recommend);

		return "redirect:/auditing";
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

	private String getQuestion(String code) {
		return msg.getMessage(code, null, Locale.getDefault());
	}

	private String getAnswer(String feature, Integer value) {
		if (mapRepo.findById(new datamapPK(feature, value)).isPresent()) {
			return mapRepo.findById(new datamapPK(feature, value)).get().getMap_label();
		}

		return "NA";
	}

	private String getIcdDesc(String icd) {
		if (icd != null) {
			return icd + " : " + icdRepo.findById(icd).get().getIcd_desc();
		} else {
			return "NA";
		}
	}

	private String getPMDesc(final Integer death, final String pm) {
		switch (death) {
		case 1: {
			if (!icdRepo.findIntrapartumPMByICD(pm).isEmpty()) {
				icd_codes icd = icdRepo.findIntrapartumPMByICD(pm).get(0);
				return pm + " : " + icd.getIcd_pmi_desc();
			}
		}
		case 2: {
			if (!icdRepo.findAntepartumPMByICD(pm).isEmpty()) {
				icd_codes icd = icdRepo.findAntepartumPMByICD(pm).get(0);
				return pm + " : " + icd.getIcd_pma_desc();
			}
		}
		case 3: {
			if (!icdRepo.findNeonatalPMByICD(pm).isEmpty()) {
				icd_codes icd = icdRepo.findNeonatalPMByICD(pm).get(0);
				return pm + " : " + icd.getIcd_pmi_desc();
			}
		}
		default: {
			return "NA";
		}
		}

	}

	private List<json_data> processListOf(audit_audit o) {
		List<json_data> list = Stream.of(
//				new json_data(getQuestion("label.audit_death"), getAnswer("adeath_options", o.getAudit_death())),
//				new json_data(getQuestion("label.audit_icd10"), getIcdDesc(o.getAudit_icd10())),
//				new json_data(getQuestion("label.audit_icdpm"), getPMDesc(o.getAudit_death(), o.getAudit_icdpm())),
//				new json_data(getQuestion("label.audit_csc"), o.getAudit_csc()),
				new json_data(getQuestion("label.audit_delay1"), getAnswer("yesnodk_options", o.getAudit_delay1())),
				new json_data(getQuestion("label.audit_delay2"), getAnswer("yesnodk_options", o.getAudit_delay2())),
				new json_data(getQuestion("label.audit_delay3a"), getAnswer("yesnodk_options", o.getAudit_delay3a())),
				new json_data(getQuestion("label.audit_delay3b"), getAnswer("yesnodk_options", o.getAudit_delay3b())),
				new json_data(getQuestion("label.audit_delay3b"), getAnswer("yesnodk_options", o.getAudit_delay3c())),
				new json_data(getQuestion("label.audit_ifcmfs"), o.getAudit_ifcmfs()),
				new json_data(getQuestion("label.audit_sysmfs"), o.getAudit_sysmfs()),
				new json_data(getQuestion("label.audit_facmfs"), o.getAudit_facmfs()),
				new json_data(getQuestion("label.audit_hwkmfs"), o.getAudit_hwkmfs()),
				new json_data(getQuestion("label.audit_cdate"),
						new SimpleDateFormat("dd-MMM-yyyy").format(o.getAudit_cdate())))
				.collect(Collectors.toList());

		return list;
	}

}// end class