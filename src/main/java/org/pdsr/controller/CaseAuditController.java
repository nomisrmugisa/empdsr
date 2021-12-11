package org.pdsr.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.pdsr.CONSTANTS;
import org.pdsr.json.json_data;
import org.pdsr.json.json_list;
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

		model.addAttribute("items", acaseRepo.findBySelectedCases());
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
	public String submit(Principal principal, Model model, @PathVariable("id") String case_uuid) {

		if (syncRepo.findById(CONSTANTS.FACILITY_ID).isEmpty()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		TypeReference<json_list> mapType = new TypeReference<json_list>() {
		};

		audit_case acase = acaseRepo.findById(case_uuid).get();
		audit_audit selected = new audit_audit();
		selected.setAudit_uuid(case_uuid);
		selected.setAudit_case(acase);

		model.addAttribute("selected", selected);

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

	@PostMapping("/submit/{id}")
	public String submit(Principal principal, @PathVariable("id") String case_uuid) {
		case_identifiers selected = caseRepo.findById(case_uuid).get();
		selected.setCase_status(1);

		caseRepo.save(selected);

		return "redirect:/registry?page=1&success=yes";
	}

}// end class