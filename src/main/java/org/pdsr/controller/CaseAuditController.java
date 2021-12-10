package org.pdsr.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.pdsr.CONSTANTS;
import org.pdsr.json.json_data;
import org.pdsr.model.audit_audit;
import org.pdsr.model.audit_case;
import org.pdsr.model.case_identifiers;
import org.pdsr.repo.AuditCaseRepository;
import org.pdsr.repo.CaseRepository;
import org.pdsr.repo.SyncTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@GetMapping("")
	public String list(Principal principal, Model model) {

		if (syncRepo.findById(CONSTANTS.FACILITY_ID).isEmpty()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		model.addAttribute("items", caseRepo.findByPendingCase_status(1));
		model.addAttribute("back", "back");

		return "auditing/audit-retrieve";
	}

	@PostMapping("")
	public String list(Principal principal) {
		//prepare a mapping reference type for converting the json strings to objects
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		TypeReference<List<json_data>> mapType = new TypeReference<List<json_data>>(){};

		List<case_identifiers> pendingAudit = caseRepo.findByPendingCase_status(1);// find all submitted cases but not
										
		// create a bucket for the selected cases for auditing
		List<audit_case> selectedForAuditing = new ArrayList<>();
		int selectionCounter = 0;
		try {
			for (case_identifiers scase : pendingAudit) {
				
				
				//check each case against the algorithm
				
				//create a new audit for the case
				audit_case acase = new audit_case();
				acase.setAudit_date(new java.util.Date());
				acase.setAudit_uuid(scase.getCase_uuid());
				
				List<json_data> fulldata = new ArrayList<>();
				//extract the json array from the case into a list object
				List<json_data> biodata = objectMapper.readValue(scase.getBiodata().getBiodata_json(), mapType);
				fulldata.addAll(biodata);
				List<json_data> pregdata = objectMapper.readValue(scase.getPregnancy().getPregnancy_json(), mapType);
				fulldata.add
				
				//combine the lists into one and convert back to JSON data
				//add the combined JSON data to the new audit for the case
				
				//add the new audit for case into the bucket of selected cases for auditing
				selectedForAuditing.add(acase);
				
				//check the size of the bucket against the number of cases needed for auditing 
				if(selectedForAuditing.size()==3) {break;}
				
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// save them to the audit_case

		return "redirect:/auditing";
	}

	@GetMapping("/edit/{id}")
	public String submit(Principal principal, Model model, @PathVariable("id") String case_uuid) {

		if (syncRepo.findById(CONSTANTS.FACILITY_ID).isEmpty()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		audit_case acase = acaseRepo.findById(case_uuid).get();
		audit_audit selected = new audit_audit();
		selected.setAudit_uuid(case_uuid);
		selected.setAudit_case(acase);

		model.addAttribute("selected", selected);

		return "auditing/audit-create";
	}

	@PostMapping("/submit/{id}")
	public String submit(Principal principal, @PathVariable("id") String case_uuid) {
		case_identifiers selected = caseRepo.findById(case_uuid).get();
		selected.setCase_status(1);

		caseRepo.save(selected);

		return "redirect:/registry?page=1&success=yes";
	}

	@ModelAttribute("death_options")
	public Map<Integer, String> deathOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(1, "Stillbirth");
		map.put(2, "Early Neonatal Death");

		return map;
	}

	@ModelAttribute("sex_options")
	public Map<Integer, String> sexOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(1, "Male");
		map.put(2, "Female");

		return map;
	}

	@ModelAttribute("edu_options")
	public Map<Integer, String> eduOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(0, "No Education");
		map.put(1, "Non-formal");
		map.put(2, "Basic Level");
		map.put(3, "Secondary Level Education");
		map.put(4, "Tertiary Level Education");
		map.put(88, "Not Stated");

		return map;
	}

	@ModelAttribute("pweeks_options")
	public Map<Integer, String> pweeksOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "In Weeks");
		for (int i = 4; i < 45; i++) {
			map.put(i, i + " Weeks");
		}
		map.put(88, "Not stated");

		return map;
	}

	@ModelAttribute("pdays_options")
	public Map<Integer, String> pdaysOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "In days");
		map.put(0, "0 Days");
		map.put(1, "1 Day");
		map.put(2, "2 Days");
		map.put(3, "3 Days");
		map.put(4, "4 Days");
		map.put(5, "5 Days");
		map.put(6, "6 Days");
		map.put(88, "Not stated");

		return map;
	}

	@ModelAttribute("ptype_options")
	public Map<Integer, String> ptypeOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(1, "Singleton");
		map.put(2, "Twins");
		map.put(3, "Triplets");
		map.put(66, "Other");
		map.put(88, "Not stated");

		return map;
	}

	@ModelAttribute("yesnodk_options")
	public Map<Integer, String> yesnodkOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(0, "No");
		map.put(1, "Yes");
		map.put(77, "Unknown");
		map.put(88, "Not stated");

		return map;
	}

	@ModelAttribute("hiv_options")
	public Map<Integer, String> hivOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(0, "Negative(-ve)");
		map.put(1, "Positive(+ve)");
		map.put(77, "Unknown");
		map.put(88, "Not stated");

		return map;
	}

	@ModelAttribute("patient_options")
	public Map<Integer, String> patientOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(0, "Mother");
		map.put(1, "Baby");
		map.put(88, "Not stated");
		map.put(99, "Not applicable");

		return map;
	}

	@ModelAttribute("source_options")
	public Map<Integer, String> sourceOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(0, "Health Facility");
		map.put(1, "Home");
		map.put(66, "Other");
		map.put(88, "Not stated");
		map.put(99, "Not applicable");

		return map;
	}

	@ModelAttribute("trans_options")
	public Map<Integer, String> transOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(0, "On foot");
		map.put(1, "Ambulance");
		map.put(2, "Tricycle");
		map.put(3, "Motobike");
		map.put(4, "Vehicle (commercial)");
		map.put(5, "Vehicle (Private)");
		map.put(88, "Not stated");
		map.put(99, "Not applicable");

		return map;
	}

	@ModelAttribute("period_options")
	public Map<Integer, String> periodOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(0, "Dawn (early morning)");
		map.put(1, "Morning");
		map.put(2, "Midday");
		map.put(3, "Afternoon");
		map.put(4, "Evening");
		map.put(5, "Midnight");
		map.put(88, "Not stated");

		return map;
	}

	@ModelAttribute("mode_options")
	public Map<Integer, String> modeOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(0, "Spontaneous vaginal delivery");
		map.put(1, "Breech delivery (vaginal)");
		map.put(2, "Vacuum/forceps delivery");
		map.put(3, "Elective C/S");
		map.put(4, "Emergency C/S");
		map.put(88, "Not stated");

		return map;
	}

	@ModelAttribute("attendno_options")
	public Map<Integer, String> attendnoOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (int i = 1; i < 25; i++) {
			map.put(i, i + "");
		}
		map.put(88, "Not stated");

		return map;
	}

	@ModelAttribute("gravipara_options")
	public Map<Integer, String> graviparaOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (int i = 1; i < 25; i++) {
			map.put(i, i + "");
		}
		map.put(88, "Not stated");

		return map;
	}

	@ModelAttribute("startmode_options")
	public Map<Integer, String> startmodeOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(0, "No labour");
		map.put(1, "Spontaneous");
		map.put(2, "Induced");
		map.put(77, "Unknown");
		map.put(88, "Not stated");

		return map;
	}

	@ModelAttribute("provider_options")
	public Map<Integer, String> providerOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(0, "Specialist");
		map.put(1, "Resident");
		map.put(2, "Medical Officer");
		map.put(3, "Midwife");
		map.put(4, "Nurse");
		map.put(5, "Traditional Birth Attendant (TBA)");
		map.put(77, "Unknown");
		map.put(88, "Not stated");

		return map;
	}

	@ModelAttribute("birthloc_options")
	public Map<Integer, String> birthlocOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(0, "Health facility");
		map.put(1, "Home");
		map.put(2, "On the way to facility");
		map.put(66, "Other");
		map.put(77, "Unknown");
		map.put(88, "Not stated");

		return map;
	}

	@ModelAttribute("liqourvolume_options")
	public Map<Integer, String> liqourvolumeOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(0, "Adequate");
		map.put(1, "Reduced (Oligohydramnious)");
		map.put(2, "No fluid (Anhydramnious)");
		map.put(3, "Too much fluid (Polyhydramnious)");
		map.put(88, "Not stated");

		return map;
	}

	@ModelAttribute("liqourcolor_options")
	public Map<Integer, String> liqourcolorOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(0, "Clear");
		map.put(1, "Meconium+");
		map.put(2, "Meconium++");
		map.put(3, "Meconium+++");
		map.put(4, "Blood stained");
		map.put(66, "Other");
		map.put(88, "Not stated");

		return map;
	}

	@ModelAttribute("liqourodour_options")
	public Map<Integer, String> liqourodourOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(0, "Normal");
		map.put(1, "Foul smell");
		map.put(88, "Not stated");

		return map;
	}

	@ModelAttribute("babyoutcome_options")
	public Map<Integer, String> babyoutcomeOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(0, "Fresh stillbirth");
		map.put(1, "Macerated stillbirth");
		map.put(77, "Unknown");
		map.put(88, "Not stated");

		return map;
	}

	@ModelAttribute("motheroutcome_options")
	public Map<Integer, String> motheroutcomeOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(0, "Alive");
		map.put(1, "Dead");
		map.put(77, "Unknown");
		map.put(88, "Not stated");

		return map;
	}

	@ModelAttribute("lastheard_options")
	public Map<Integer, String> lastheardOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		map.put(0, "Antepartum");
		map.put(1, "Intrapartum (first stage)");
		map.put(2, "Intrapartum (second stage)");
		map.put(77, "Unknown");
		map.put(88, "Not stated");

		return map;
	}

}// end class