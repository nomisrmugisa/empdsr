package org.pdsr.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.pdsr.CONSTANTS;
import org.pdsr.InternetAvailabilityChecker;
import org.pdsr.master.model.case_identifiers;
import org.pdsr.master.model.facility_table;
import org.pdsr.master.model.icd_codes;
import org.pdsr.master.model.icd_diagnoses;
import org.pdsr.master.model.sync_table;
import org.pdsr.master.repo.AuditAuditRepository;
import org.pdsr.master.repo.AuditRecommendRepository;
import org.pdsr.master.repo.CaseRepository;
import org.pdsr.master.repo.FacilityTableRepository;
import org.pdsr.master.repo.IcdCodesRepository;
import org.pdsr.master.repo.IcdDiagnosesRepository;
import org.pdsr.master.repo.SyncTableRepository;
import org.pdsr.pojos.casedeleter;
import org.pdsr.pojos.facilities;
import org.pdsr.summary.model.SummaryPK;
import org.pdsr.summary.repo.BigAuditAuditRepository;
import org.pdsr.summary.repo.BigAuditRecommendRepository;
import org.pdsr.summary.repo.BigCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Controller
@RequestMapping("/controls")
public class SetupController {

	@Autowired
	private SyncTableRepository syncRepo;

	@Autowired
	private IcdCodesRepository icdRepo;

	@Autowired
	private IcdDiagnosesRepository icddRepo;

	// repositories being merged
	@Autowired
	private FacilityTableRepository facilityRepo;

	@Autowired
	private CaseRepository caseRepo;

	@Autowired
	private BigCaseRepository bcaseRepo;

	@Autowired
	private AuditAuditRepository aaudRepo;

	@Autowired
	private BigAuditAuditRepository baaudRepo;

	@Autowired
	private AuditRecommendRepository recRepo;

	@Autowired
	private BigAuditRecommendRepository brecRepo;

	@GetMapping("")
	public String sync(Principal principal, Model model, @RequestParam(required = false) String success) {

		Optional<sync_table> object = syncRepo.findById(CONSTANTS.LICENSE_ID);
		if (object.isPresent()) {

			model.addAttribute("selected", object.get());

		} else {
			sync_table selected = new sync_table();
			selected.setSync_id(CONSTANTS.LICENSE_ID);
			model.addAttribute("selected", selected);
		}

		model.addAttribute("back", "back");

		if (success != null) {
			model.addAttribute("success", "Synced Successfully!");
		}

		return "controls/dashboard";
	}

	@PostMapping("")
	public String sync(Principal principal, @ModelAttribute("selected") sync_table selected, BindingResult results) {

		try {
			List<facilities> table = loadFromCsv("facilities.csv", facilities.class);

			for (facilities elem : table) {

				Optional<facility_table> checkFacility = facilityRepo.findByFacility_code(elem.getFacility_code());
				if (!checkFacility.isPresent()) {

					Optional<facility_table> parent = facilityRepo.findByFacility_code(elem.getParent_code());

					if (parent.isPresent()) {
						facility_table facility = new facility_table();
						facility.setFacility_uuid(UUID.randomUUID().toString().replaceAll("-", ""));
						facility.setFacility_code(elem.getFacility_code());
						facility.setFacility_name(elem.getFacility_name());
						facility.setFacility_type(elem.getFacility_type());
						facility.setParent(parent.get());
						facilityRepo.save(facility);

					}
				}
			}

		} catch (IOException e) {
			results.rejectValue("sync_code", "invalid.icds");
			e.printStackTrace();
			return "controls/dashboard";
		}

		// check if my facility is in list, then pull name of facility and replace, else
		// save stated facility name
		Optional<facility_table> checkMyFacility = facilityRepo.findByFacility_code(selected.getSync_code());
		if (checkMyFacility.isPresent()) {
			selected.setSync_name(checkMyFacility.get().getFacility_name());
		}
		syncRepo.save(selected);

		try {
			List<icd_codes> table = loadFromCsv("icd_code.csv", icd_codes.class);
			icdRepo.saveAll(table);
		} catch (IOException e) {
			results.rejectValue("sync_code", "invalid.icds");
			e.printStackTrace();
			return "controls/dashboard";
		}

		try {
			List<icd_diagnoses> table = loadFromCsv("icd_diagnoses.csv", icd_diagnoses.class);
			icddRepo.saveAll(table);
		} catch (IOException e) {
			results.rejectValue("sync_code", "invalid.icds");
			e.printStackTrace();
			return "controls/dashboard";
		}

		return "redirect:/controls?success=yes";
	}

	@GetMapping("/countries")
	public String country(Principal principal, Model model) {

		model.addAttribute("selected", facilityRepo.findById(CONSTANTS.LICENSE_ID).get());

		model.addAttribute("loc", "active");

		return "controls/setup-countries";
	}

	@GetMapping("/deletecase")
	public String deletecase(Principal principal, Model model, @RequestParam(required = false) String success,
			@RequestParam(required = false) String case_uuid) {

		casedeleter selected = new casedeleter();
		model.addAttribute("selected", selected);

		if (success != null) {
			if ("1".equals(success)) {
				model.addAttribute("success", "Deleted Successfully!");
			} else if ("0".equals(success)) {
				model.addAttribute("success", "No internet connection detected!");
			}
		}

		return "controls/delete-case";
	}

	// @Transactional
	@PostMapping("/deletecase")
	public String deletecaseconfirm(Principal principal, Model model,
			@ModelAttribute("selected") casedeleter selected) {

		// if confirmation is given and object is found
		if (selected != null && selected.isClear_uploaded()) {

			// if remote data should be deleted
			if (selected.isDelete_remotely()) {

				try {

					if (InternetAvailabilityChecker.isInternetAvailable()) {

						// TODO delete cloud data

					} else {

						model.addAttribute("failure", "Could not connect to the Internet");
						return "controls/delete-case";

					}

				} catch (IOException e) {

					model.addAttribute("failure", "Something went wrong with the internet connection");
					return "controls/delete-case";

				}
			}

			// if summary data should be deleted
			if (selected.isClear_summeries()) {

				// delete locally cached data summaries related to case data from 3 databases

				sync_table sync = syncRepo.findById(CONSTANTS.LICENSE_ID).get();

				facility_table facility = facilityRepo.findByFacility_code(sync.getSync_code()).get();

				final String country = facility.getParent().getParent().getParent().getFacility_name();
				final String region = facility.getParent().getParent().getFacility_name();
				final String district = facility.getParent().getFacility_name();
				final String code = facility.getFacility_code();
				final String id = selected.getCase_identifiers().getCase_uuid()
						+ selected.getCase_identifiers().getCase_id().toLowerCase();

				SummaryPK summaryPK = new SummaryPK(id, code, country, region, district);

				if (bcaseRepo.findById(summaryPK).isPresent())
					bcaseRepo.deleteById(summaryPK);
				if (brecRepo.findById(summaryPK).isPresent())
					brecRepo.deleteById(summaryPK);
				if (baaudRepo.findById(summaryPK).isPresent())
					baaudRepo.deleteById(summaryPK);

			}

			// then finally delete locally stored case data from 3 databases
			final String case_uuid = selected.getCase_identifiers().getCase_uuid();
			if (caseRepo.findById(case_uuid).isPresent())
				caseRepo.deleteById(case_uuid);
			if (recRepo.findById(case_uuid).isPresent())
				recRepo.deleteById(case_uuid);
			if (aaudRepo.findById(case_uuid).isPresent())
				aaudRepo.deleteById(case_uuid);

			return "redirect:/controls/deletecase?success=1";

		}

		// find the case and confirm delete action
		List<case_identifiers> cid = caseRepo.findByCaseID(selected.getCase_id());

		if (cid == null || cid.isEmpty()) {

			model.addAttribute("failure", "Record not found");
			return "controls/delete-case";
		}

		selected.setCase_identifiers(cid.get(0));
		selected.setClear_uploaded(true);
		model.addAttribute("selected", selected);

		return "controls/delete-case";

	}

	private <T> List<T> loadFromCsv(String csv, Class<T> type) throws IOException {
		byte[] bytes = CONSTANTS.read("csv", csv);

		try (Reader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)))) {

			CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader).withType(type).withIgnoreLeadingWhiteSpace(true)
					.build();

			return csvToBean.parse();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ArrayList<>();
	}

}
// end class