package org.pdsr.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.pdsr.CONSTANTS;
import org.pdsr.InternetAvailabilityChecker;
import org.pdsr.ServiceApi;
import org.pdsr.json.DecryptedAuditAudit;
import org.pdsr.json.DecryptedAuditRecommendation;
import org.pdsr.json.DecryptedCaseIdentifiers;
import org.pdsr.json.DecryptedUserTable;
import org.pdsr.json.DecryptedWeeklyMonitoring;
import org.pdsr.json.json_audit_audit;
import org.pdsr.json.json_audit_recommendation;
import org.pdsr.json.json_case_identifiers;
import org.pdsr.json.json_user_table;
import org.pdsr.json.json_weekly_monitoring;
import org.pdsr.master.model.abnormality_table;
import org.pdsr.master.model.audit_audit;
import org.pdsr.master.model.audit_case;
import org.pdsr.master.model.audit_recommendation;
import org.pdsr.master.model.case_antenatal;
import org.pdsr.master.model.case_babydeath;
import org.pdsr.master.model.case_biodata;
import org.pdsr.master.model.case_birth;
import org.pdsr.master.model.case_delivery;
import org.pdsr.master.model.case_fetalheart;
import org.pdsr.master.model.case_identifiers;
import org.pdsr.master.model.case_labour;
import org.pdsr.master.model.case_notes;
import org.pdsr.master.model.case_pregnancy;
import org.pdsr.master.model.case_referral;
import org.pdsr.master.model.cfactor_table;
import org.pdsr.master.model.complication_table;
import org.pdsr.master.model.cordfault_table;
import org.pdsr.master.model.country_table;
import org.pdsr.master.model.diagnoses_table;
import org.pdsr.master.model.district_table;
import org.pdsr.master.model.facility_table;
import org.pdsr.master.model.icd_codes;
import org.pdsr.master.model.icd_diagnoses;
import org.pdsr.master.model.mcondition_table;
import org.pdsr.master.model.monitoring_table;
import org.pdsr.master.model.placentacheck_table;
import org.pdsr.master.model.region_table;
import org.pdsr.master.model.resuscitation_table;
import org.pdsr.master.model.risk_table;
import org.pdsr.master.model.sync_table;
import org.pdsr.master.model.user_table;
import org.pdsr.master.model.weekly_monitoring;
import org.pdsr.master.model.weekly_table;
import org.pdsr.master.model.wmPK;
import org.pdsr.master.repo.AuditAuditRepository;
import org.pdsr.master.repo.AuditCaseRepository;
import org.pdsr.master.repo.AuditRecommendRepository;
import org.pdsr.master.repo.CaseAntenatalRepository;
import org.pdsr.master.repo.CaseBabyRepository;
import org.pdsr.master.repo.CaseBiodataRepository;
import org.pdsr.master.repo.CaseBirthRepository;
import org.pdsr.master.repo.CaseDeliveryRepository;
import org.pdsr.master.repo.CaseFetalheartRepository;
import org.pdsr.master.repo.CaseLabourRepository;
import org.pdsr.master.repo.CaseNotesRepository;
import org.pdsr.master.repo.CasePregnancyRepository;
import org.pdsr.master.repo.CaseReferralRepository;
import org.pdsr.master.repo.CaseRepository;
import org.pdsr.master.repo.CountryTableRepository;
import org.pdsr.master.repo.DistrictTableRepository;
import org.pdsr.master.repo.FacilityTableRepository;
import org.pdsr.master.repo.IcdCodesRepository;
import org.pdsr.master.repo.IcdDiagnosesRepository;
import org.pdsr.master.repo.MonitoringTableRepository;
import org.pdsr.master.repo.RegionTableRepository;
import org.pdsr.master.repo.SyncTableRepository;
import org.pdsr.master.repo.UserTableRepository;
import org.pdsr.master.repo.WeeklyMonitoringTableRepository;
import org.pdsr.master.repo.WeeklyTableRepository;
import org.pdsr.pojos.casedeleter;
import org.pdsr.pojos.datamerger;
import org.pdsr.slave.repo.SlaveAuditAuditRepository;
import org.pdsr.slave.repo.SlaveAuditCaseRepository;
import org.pdsr.slave.repo.SlaveAuditRecommendRepository;
import org.pdsr.slave.repo.SlaveCaseAntenatalRepository;
import org.pdsr.slave.repo.SlaveCaseBabyRepository;
import org.pdsr.slave.repo.SlaveCaseBiodataRepository;
import org.pdsr.slave.repo.SlaveCaseBirthRepository;
import org.pdsr.slave.repo.SlaveCaseDeliveryRepository;
import org.pdsr.slave.repo.SlaveCaseFetalheartRepository;
import org.pdsr.slave.repo.SlaveCaseLabourRepository;
import org.pdsr.slave.repo.SlaveCaseNotesRepository;
import org.pdsr.slave.repo.SlaveCasePregnancyRepository;
import org.pdsr.slave.repo.SlaveCaseReferralRepository;
import org.pdsr.slave.repo.SlaveCaseRepository;
import org.pdsr.slave.repo.SlaveWeeklyMonitoringTableRepository;
import org.pdsr.slave.repo.SlaveWeeklyTableRepository;
import org.pdsr.summary.model.SummaryPK;
import org.pdsr.summary.model.big_audit_audit;
import org.pdsr.summary.model.big_audit_recommendation;
import org.pdsr.summary.model.big_case_identifiers;
import org.pdsr.summary.model.big_weekly_monitoring;
import org.pdsr.summary.repo.BigAuditAuditRepository;
import org.pdsr.summary.repo.BigAuditRecommendRepository;
import org.pdsr.summary.repo.BigCaseRepository;
import org.pdsr.summary.repo.BigWeeklyMonitoringTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Controller
@RequestMapping("/controls")
public class SetupController {

	@Autowired
	private ServiceApi api;

	@Autowired
	private SyncTableRepository syncRepo;

	@Autowired
	private IcdCodesRepository icdRepo;

	@Autowired
	private IcdDiagnosesRepository icddRepo;

	@Autowired
	private MonitoringTableRepository monRepo;

	// repositories being merged
	@Autowired
	private FacilityTableRepository facilityRepo;

//	@Autowired
//	private SlaveFacilityTableRepository sfacilityRepo;

	@Autowired
	private DistrictTableRepository districtRepo;

//	@Autowired
//	private SlaveDistrictTableRepository sdistrictRepo;

	@Autowired
	private RegionTableRepository regionRepo;

//	@Autowired
//	private SlaveRegionTableRepository sregionRepo;

	@Autowired
	private CountryTableRepository countryRepo;

//	@Autowired
//	private SlaveCountryTableRepository scounrtyRepo;

	@Autowired
	private WeeklyTableRepository weeklyRepo;

	@Autowired
	private SlaveWeeklyTableRepository sweeklyRepo;

	@Autowired
	private SlaveWeeklyMonitoringTableRepository sweekMRepo;

	@Autowired
	private WeeklyMonitoringTableRepository weekMRepo;

	@Autowired
	private BigWeeklyMonitoringTableRepository bweekMRepo;

	@Autowired
	private CaseRepository caseRepo;

	@Autowired
	private BigCaseRepository bcaseRepo;

	@Autowired
	private SlaveCaseRepository scaseRepo;

	@Autowired
	private CaseBiodataRepository bioRepo;

	@Autowired
	private SlaveCaseBiodataRepository sbioRepo;

	@Autowired
	private CasePregnancyRepository pregRepo;

	@Autowired
	private SlaveCasePregnancyRepository spregRepo;

	@Autowired
	private CaseReferralRepository refRepo;

	@Autowired
	private SlaveCaseReferralRepository srefRepo;

	@Autowired
	private CaseDeliveryRepository delRepo;

	@Autowired
	private SlaveCaseDeliveryRepository sdelRepo;

	@Autowired
	private CaseAntenatalRepository antRepo;

	@Autowired
	private SlaveCaseAntenatalRepository santRepo;

	@Autowired
	private CaseLabourRepository labRepo;

	@Autowired
	private SlaveCaseLabourRepository slabRepo;

	@Autowired
	private CaseBirthRepository birthRepo;

	@Autowired
	private SlaveCaseBirthRepository sbirthRepo;

	@Autowired
	private CaseFetalheartRepository fetRepo;

	@Autowired
	private SlaveCaseFetalheartRepository sfetRepo;

	@Autowired
	private CaseBabyRepository babyRepo;

	@Autowired
	private SlaveCaseBabyRepository sbabyRepo;

	@Autowired
	private CaseNotesRepository noteRepo;

	@Autowired
	private SlaveCaseNotesRepository snoteRepo;

	@Autowired
	private AuditCaseRepository audRepo;

	@Autowired
	private SlaveAuditCaseRepository saudRepo;

	@Autowired
	private AuditAuditRepository aaudRepo;

	@Autowired
	private BigAuditAuditRepository baaudRepo;

	@Autowired
	private SlaveAuditAuditRepository saaudRepo;

	@Autowired
	private AuditRecommendRepository recRepo;

	@Autowired
	private BigAuditRecommendRepository brecRepo;

	@Autowired
	private SlaveAuditRecommendRepository srecRepo;

	@Autowired
	private UserTableRepository userRepo;

	@GetMapping("")
	public String sync(Principal principal, Model model,
			@RequestParam(name = "success", required = false) String success) {

		Optional<sync_table> object = syncRepo.findById(CONSTANTS.FACILITY_ID);
		if (object.isPresent()) {

			model.addAttribute("selected", object.get());

		} else {
			sync_table selected = new sync_table();
			selected.setSync_id(CONSTANTS.FACILITY_ID);
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

		// do the sync operations here
		Optional<facility_table> object = facilityRepo.findByFacility_code(selected.getSync_code());

		if (!object.isPresent()) {
			results.rejectValue("sync_code", "invalid.code");
			selected.setSync_uuid("");
			selected.setSync_name("");
			selected.setSync_email("");
		}

		if (results.hasErrors()) {
			return "controls/dashboard";
		}

		facility_table facility = object.get();

		selected.setSync_name(facility.getFacility_name());
		selected.setSync_uuid(facility.getFacility_uuid());
		syncRepo.save(selected);

		try {
			List<icd_codes> icds = loadICD();
			icdRepo.saveAll(icds);
		} catch (IOException e) {
			results.rejectValue("sync_code", "invalid.icds");
			e.printStackTrace();
			return "controls/dashboard";
		}

		try {
			List<icd_diagnoses> icdds = loadICDD();
			icddRepo.saveAll(icdds);
		} catch (IOException e) {
			results.rejectValue("sync_code", "invalid.icds");
			e.printStackTrace();
			return "controls/dashboard";
		}

		return "redirect:/controls?success=yes";
	}

	@GetMapping("/regions")
	public String country(Principal principal, Model model) {
		model.addAttribute("countryList", countryRepo.findAll());
		model.addAttribute("loc", "active");
		model.addAttribute("selected", new datamerger());

		return "controls/setup-countries";
	}

	@GetMapping("/region/{id}")
	public String region(Principal principal, Model model, @PathVariable("id") String uuid,
			@RequestParam(name = "add", required = false) String add,
			@RequestParam(name = "success", required = false) String success) {

		country_table selected = countryRepo.findById(uuid).get();

		if (selected.getRegionList() == null) {
			selected.setRegionList(new ArrayList<region_table>());
		}

		if (add != null) {
			region_table region = new region_table();
			region.setRegion_uuid(UUID.randomUUID().toString().replaceAll("-", ""));
			region.setCountry(selected);
			selected.getRegionList().add(region);
			model.addAttribute("add", add);
		}
		model.addAttribute("selected", selected);
		model.addAttribute("loc", "active");

		if (success != null) {
			model.addAttribute("success", "Saved Successfully");
		}

		return "controls/setup-regions";
	}

	@Transactional
	@PostMapping("/region/{id}")
	public String add(Principal principal, Model model, @ModelAttribute("selected") country_table selected,
			@PathVariable("id") String uuid) {

		countryRepo.save(selected);

		return "redirect:/controls/region/" + selected.getCountry_uuid() + "?success=yes";
	}

	@GetMapping("/district/{id}")
	public String district(Principal principal, Model model, @PathVariable("id") String uuid,
			@RequestParam(name = "add", required = false) String add,
			@RequestParam(name = "success", required = false) String success) {

		region_table selected = regionRepo.findById(uuid).get();

		if (selected.getDistrictList() == null) {
			selected.setDistrictList(new ArrayList<district_table>());
		}

		if (add != null) {
			district_table district = new district_table();
			district.setDistrict_uuid(UUID.randomUUID().toString().replaceAll("-", ""));
			district.setRegion(selected);
			selected.getDistrictList().add(district);
			model.addAttribute("add", add);
		}
		model.addAttribute("selected", selected);
		model.addAttribute("loc", "active");

		if (success != null) {
			model.addAttribute("success", "Saved Successfully");
		}

		return "controls/setup-districts";
	}

	@Transactional
	@PostMapping("/district/{id}")
	public String district(Principal principal, Model model, @ModelAttribute("selected") region_table selected,
			@PathVariable("id") String uuid) {

		regionRepo.save(selected);

		model.addAttribute("success", "Saved Successfully");

		return "redirect:/controls/district/" + selected.getRegion_uuid() + "?success=yes";
	}

	@GetMapping("/facility/{id}")
	public String facility(Principal principal, Model model, @PathVariable("id") String uuid,
			@RequestParam(name = "add", required = false) String add,
			@RequestParam(name = "success", required = false) String success) {

		district_table selected = districtRepo.findById(uuid).get();

		if (selected.getFacilityList() == null) {
			selected.setFacilityList(new ArrayList<facility_table>());
		}

		if (add != null) {
			facility_table facility = new facility_table();
			facility.setFacility_uuid(UUID.randomUUID().toString().replaceAll("-", ""));
			facility.setDistrict(selected);
			selected.getFacilityList().add(facility);
			model.addAttribute("add", add);
		}
		model.addAttribute("selected", selected);
		model.addAttribute("loc", "active");

		if (success != null) {
			model.addAttribute("success", "Saved Successfully");
		}

		return "controls/setup-facilities";
	}

	@Transactional
	@PostMapping("/facility/{id}")
	public String facility(Principal principal, Model model, @ModelAttribute("selected") district_table selected,
			@PathVariable("id") String uuid) {

		districtRepo.save(selected);

		model.addAttribute("success", "Saved Successfully");

		return "redirect:/controls/facility/" + selected.getDistrict_uuid() + "?success=yes";
	}

	@GetMapping("/datamerge")
	public String datamerge(Principal principal, Model model,
			@RequestParam(name = "success", required = false) String success) {

		model.addAttribute("selected", new datamerger());

		if (success != null) {
			model.addAttribute("success", "Merged Successfully!");
		}

		return "controls/merger-peer";
	}

	@Transactional
	@PostMapping("/datamerge")
	public String datamerge(Principal principal, @ModelAttribute("selected") datamerger selected) {

		// merge location data from slave to master (overrides if exists)
		if (icdRepo.count() == 0) {
			try {
				List<icd_codes> icds = loadICD();
				icdRepo.saveAll(icds);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (icddRepo.count() == 0) {
			try {
				List<icd_diagnoses> icdds = loadICDD();
				icddRepo.saveAll(icdds);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

//THIS CODE SHOULD NOT BE USED SINCE WE MERGE AT THE SAME FACILITY
//		if (selected.isMerge_location()) {
//			mergeCountry();
//			mergeRegion();
//			mergeDistrict();
//			mergeFacility();
//		}

		// merge weekly reports from slave to master (overrides if exists)
		if (selected.isMerge_weekly()) {
			mergeWeeklyTable();
			mergeWeeklyMonitoring();

		}

		sync_table object = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		facility_table facility = facilityRepo.findByFacility_code(object.getSync_code()).get();
		// merge case entries from slave to master (overrides if exists)
		if (selected.isMerge_cases()) {
			mergeCaseIdentifier(facility);
		}

		// merge case audits from slave to master (overrides if exists)
		if (selected.isMerge_audit()) {
			mergeAuditCase();
		}

		return "redirect:/controls/datamerge?success=yes";
	}

	@GetMapping("/centralmerge")
	public String centralmerge(Principal principal, Model model,
			@RequestParam(name = "success", required = false) String success) {

		if (success != null) {
			if ("0".equals(success)) {
				model.addAttribute("success", "No internet connection detected!");
			} else {
				model.addAttribute("success", "Transmitted Successfully!");
			}
		}

		return "controls/merger-central";
	}

	@Transactional
	@PostMapping("/centralmerge")
	public String centralmerge(Principal principal, @RequestParam(name = "all", required = false) Integer all) {

		try {
			if (InternetAvailabilityChecker.isInternetAvailable()) {
				sync_table sync = syncRepo.findById(CONSTANTS.FACILITY_ID).get();

				facility_table facility = facilityRepo.findByFacility_code(sync.getSync_code()).get();

				final String country = facility.getDistrict().getRegion().getCountry().getCountry_name();
				final String region = facility.getDistrict().getRegion().getRegion_name();
				final String district = facility.getDistrict().getDistrict_name();
				final String code = facility.getFacility_code();

				pushuUserTable(code, district, region, country);

				if (all != null && all == 1) {

					pushCaseData_ALL(code, district, region, country);
					pushAuditData_ALL(code, district, region, country);
					pushRecommendationData_ALL(code, district, region, country);
					pushMonitoringData_ALL(code, district, region, country);

					return "redirect:/controls/centralmerge?success=" + all;
				}

				pushCaseData(code, district, region, country);
				pushAuditData(code, district, region, country);
				pushRecommendationData(code, district, region, country);
				pushMonitoringData(code, district, region, country);

				return "redirect:/controls/centralmerge?success=2";

			} else {

				return "redirect:/controls/centralmerge?success=0";

			}
		} catch (IOException e) {
			return "redirect:/controls/centralmerge?failure=1";
		}
	}

	@GetMapping("/downloadmerge")
	public String downloadmerge(Principal principal, Model model,
			@RequestParam(name = "success", required = false) String success) {

		if (success != null) {
			if ("1".equals(success)) {
				model.addAttribute("success", "Downloaded Successfully!");
			} else if ("0".equals(success)) {
				model.addAttribute("success", "No internet connection detected!");
			}
		}

		return "controls/merger-download";
	}

	@Transactional
	@PostMapping("/downloadmerge")
	public String downloadmerge(Principal principal) {
		try {
			if (InternetAvailabilityChecker.isInternetAvailable()) {

				pullUserData();
				pullCaseData();
				pullAuditData();
				pullRecommendationData();
				pullMonitoringData();

				return "redirect:/controls/downloadmerge?success=1";
			} else {
				return "redirect:/controls/downloadmerge?success=0";

			}
		} catch (IOException e) {
			return "redirect:/controls/downloadmerge?failure=1";
		}

	}

	@GetMapping("/deletecase")
	public String deletecase(Principal principal, Model model,
			@RequestParam(name = "success", required = false) String success) {

		model.addAttribute("selected", new casedeleter());

		if (success != null) {
			if ("1".equals(success)) {
				model.addAttribute("success", "Deleted Successfully!");
			} else if ("0".equals(success)) {
				model.addAttribute("success", "No internet connection detected!");
			}
		}

		return "controls/delete-case";
	}

//	@Transactional
	@PostMapping("/deletecase")
	public String deletecase(Principal principal, Model model, @ModelAttribute("selected") casedeleter selected) {

		// if confirmation is given and object is found
		if (selected.isClear_uploaded()) {

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

				sync_table sync = syncRepo.findById(CONSTANTS.FACILITY_ID).get();

				facility_table facility = facilityRepo.findByFacility_code(sync.getSync_code()).get();

				final String country = facility.getDistrict().getRegion().getCountry().getCountry_name();
				final String region = facility.getDistrict().getRegion().getRegion_name();
				final String district = facility.getDistrict().getDistrict_name();
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

		return "controls/delete-case";

	}

//	private void mergeCountry() {
//		List<org.pdsr.slave.model.country_table> scountries = scounrtyRepo.findAll();
//		if (scountries != null && scountries.size() > 0) {
//			List<country_table> countries = new ArrayList<country_table>();
//			for (org.pdsr.slave.model.country_table s : scountries) {
//				country_table country = new country_table();
//				country.setCountry_uuid(s.getCountry_uuid());
//				country.setCountry_name(s.getCountry_name());
//				countries.add(country);
//			}
//			countryRepo.saveAll(countries);
//		}
//	}
//
//	private void mergeRegion() {
//		List<org.pdsr.slave.model.region_table> sregions = sregionRepo.findAll();
//		if (sregions != null && sregions.size() > 0) {
//			List<region_table> regions = new ArrayList<region_table>();
//			for (org.pdsr.slave.model.region_table s : sregions) {
//				Optional<country_table> country = countryRepo.findById(s.getCountry().getCountry_uuid());
//				if (country.isPresent()) {
//					region_table region = new region_table();
//					region.setRegion_uuid(s.getRegion_uuid());
//					region.setRegion_name(s.getRegion_name());
//					region.setCountry(country.get());
//
//					regions.add(region);
//				}
//			}
//			regionRepo.saveAll(regions);
//		}
//	}
//
//	private void mergeDistrict() {
//		List<org.pdsr.slave.model.district_table> sdistricts = sdistrictRepo.findAll();
//		if (sdistricts != null && sdistricts.size() > 0) {
//			List<district_table> districts = new ArrayList<district_table>();
//			for (org.pdsr.slave.model.district_table s : sdistricts) {
//				Optional<region_table> region = regionRepo.findById(s.getRegion().getRegion_uuid());
//				if (region.isPresent()) {
//					district_table district = new district_table();
//					district.setDistrict_uuid(s.getDistrict_uuid());
//					district.setDistrict_name(s.getDistrict_name());
//					district.setRegion(region.get());
//
//					districts.add(district);
//				}
//			}
//
//			districtRepo.saveAll(districts);
//		}
//
//	}
//
//	private void mergeFacility() {
//		List<org.pdsr.slave.model.facility_table> sfacilities = sfacilityRepo.findAll();
//		if (sfacilities != null && sfacilities.size() > 0) {
//			List<facility_table> facilities = new ArrayList<facility_table>();
//			for (org.pdsr.slave.model.facility_table s : sfacilities) {
//				Optional<district_table> district = districtRepo.findById(s.getDistrict().getDistrict_uuid());
//				if (district.isPresent()) {
//					facility_table facility = new facility_table();
//					facility.setFacility_uuid(s.getFacility_uuid());
//					facility.setFacility_code(s.getFacility_code());
//					facility.setFacility_name(s.getFacility_name());
//					facility.setDistrict(district.get());
//
//					facilities.add(facility);
//				}
//			}
//
//			facilityRepo.saveAll(facilities);
//		}
//	}

	private void mergeWeeklyTable() {
		List<org.pdsr.slave.model.weekly_table> sweeklys = sweeklyRepo.findAll();
		if (sweeklys != null && sweeklys.size() > 0) {
			List<weekly_table> weeklys = new ArrayList<weekly_table>();
			for (org.pdsr.slave.model.weekly_table s : sweeklys) {
				weekly_table weekly = new weekly_table();
				weekly.setWeekly_id(s.getWeekly_id());
				weekly.setWeekly_date(s.getWeekly_date());
				weekly.setWeekly_year(s.getWeekly_year());
				weekly.setWeekly_month(s.getWeekly_month());
				weekly.setWeekly_mdesc(s.getWeekly_mdesc());
				weekly.setWeekly_week(s.getWeekly_week());

				weeklys.add(weekly);
			}

			weeklyRepo.saveAll(weeklys);
		}

	}

	private void mergeWeeklyMonitoring() {
		List<org.pdsr.slave.model.weekly_monitoring> smonitors = sweekMRepo.findAll();
		if (smonitors != null && smonitors.size() > 0) {
			List<weekly_monitoring> monitors = new ArrayList<weekly_monitoring>();
			for (org.pdsr.slave.model.weekly_monitoring s : smonitors) {
				Optional<weekly_table> weekly = weeklyRepo.findById(s.getWm_grids().getWeekly_id());
				Optional<monitoring_table> mont = monRepo.findById(s.getWm_indices().getMindex());
				if (weekly.isPresent() && mont.isPresent()) {
					weekly_monitoring monitor = new weekly_monitoring();
					monitor.setId(new wmPK(s.getWm_grids().getWeekly_id(), s.getWm_indices().getMindex()));
					monitor.setWm_values(s.getWm_values());
					;
					monitor.setWm_subval(s.getWm_subval());
					monitor.setWm_grids(weekly.get());
					monitor.setWm_indices(mont.get());
					monitor.setData_sent(s.getData_sent());

					monitors.add(monitor);
				}
			}

			weekMRepo.saveAll(monitors);
		}
	}

	private void mergeCaseIdentifier(facility_table facility) {
		List<org.pdsr.slave.model.case_identifiers> sdeaths = scaseRepo.findAll();
		if (sdeaths != null && sdeaths.size() > 0) {
			List<case_identifiers> deaths = new ArrayList<case_identifiers>();
			for (org.pdsr.slave.model.case_identifiers s : sdeaths) {
				case_identifiers death = new case_identifiers();
				death.setCase_uuid(s.getCase_uuid());
				death.setCase_id(s.getCase_id());
				death.setCase_date(s.getCase_date());
				death.setCase_mid(s.getCase_mid());
				death.setCase_mname(s.getCase_mname());
				death.setCase_status(s.getCase_status());
				death.setCase_death(s.getCase_death());
				death.setCase_sync(s.getCase_sync());
				death.setData_sent(s.getData_sent());

				death.setFacility(facility);

				deaths.add(death);

			}

			caseRepo.saveAll(deaths);
		}

		mergeCaseBiodata();
		mergeCasePregnancy();
		mergeCaseReferral();
		mergeCaseDelivery();
		mergeCaseAntenatal();
		mergeCaseLabour();
		mergeCaseBirth();
		mergeCaseFetalheart();
		mergeCaseBabydeath();
		mergeCaseNotes();

	}

	private void mergeCaseBiodata() {
		List<org.pdsr.slave.model.case_biodata> scases = sbioRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_biodata> mcases = new ArrayList<case_biodata>();
			for (org.pdsr.slave.model.case_biodata s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {
					case_biodata mcase = new case_biodata();
					mcase.setBiodata_uuid(s.getBiodata_uuid());
					mcase.setBiodata_sex(s.getBiodata_sex());
					mcase.setBiodata_mage(s.getBiodata_mage());
					mcase.setBiodata_medu(s.getBiodata_medu());
					mcase.setBiodata_json(s.getBiodata_json());
					mcase.setData_complete(s.getData_complete());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			bioRepo.saveAll(mcases);
		}

	}

	private void mergeCasePregnancy() {
		List<org.pdsr.slave.model.case_pregnancy> scases = spregRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_pregnancy> mcases = new ArrayList<case_pregnancy>();
			for (org.pdsr.slave.model.case_pregnancy s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {
					case_pregnancy mcase = new case_pregnancy();
					mcase.setPregnancy_uuid(s.getPregnancy_uuid());
					mcase.setPregnancy_type(s.getPregnancy_type());
					mcase.setPregnancy_days(s.getPregnancy_days());
					mcase.setPregnancy_weeks(s.getPregnancy_weeks());
					mcase.setPregnancy_json(s.getPregnancy_json());

					mcase.setData_complete(s.getData_complete());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			pregRepo.saveAll(mcases);
		}

	}

	private void mergeCaseReferral() {
		List<org.pdsr.slave.model.case_referral> scases = srefRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_referral> mcases = new ArrayList<case_referral>();
			for (org.pdsr.slave.model.case_referral s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {
					case_referral mcase = new case_referral();
					mcase.setReferral_uuid(s.getReferral_uuid());
					mcase.setReferral_adate(s.getReferral_adate());
					mcase.setReferral_ahour(s.getReferral_ahour());
					mcase.setReferral_aminute(s.getReferral_aminute());
					mcase.setReferral_atime(s.getReferral_atime());
					mcase.setReferral_case(s.getReferral_case());
					mcase.setReferral_date(s.getReferral_date());
					mcase.setReferral_facility(s.getReferral_facility());
					mcase.setReferral_file(s.getReferral_file());
					mcase.setReferral_filetype(s.getReferral_filetype());
					mcase.setReferral_hour(s.getReferral_hour());
					mcase.setReferral_minute(s.getReferral_minute());
					mcase.setReferral_patient(s.getReferral_patient());
					mcase.setReferral_source(s.getReferral_source());
					mcase.setReferral_time(s.getReferral_time());
					mcase.setReferral_transport(s.getReferral_transport());
					mcase.setReferral_notes(s.getReferral_notes());
					mcase.setReferral_json(s.getReferral_json());

					mcase.setData_complete(s.getData_complete());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			refRepo.saveAll(mcases);
		}

	}

	private void mergeCaseDelivery() {
		List<org.pdsr.slave.model.case_delivery> scases = sdelRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_delivery> mcases = new ArrayList<case_delivery>();
			for (org.pdsr.slave.model.case_delivery s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {
					case_delivery mcase = new case_delivery();
					mcase.setDelivery_uuid(s.getDelivery_uuid());
					mcase.setDelivery_date(s.getDelivery_date());
					mcase.setDelivery_hour(s.getDelivery_hour());
					mcase.setDelivery_minute(s.getDelivery_minute());
					mcase.setDelivery_period(s.getDelivery_period());
					mcase.setDelivery_time(s.getDelivery_time());
					mcase.setDelivery_weight(s.getDelivery_weight());
					mcase.setDelivery_json(s.getDelivery_json());

					mcase.setData_complete(s.getData_complete());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			delRepo.saveAll(mcases);
		}

	}

	private void mergeCaseAntenatal() {
		List<org.pdsr.slave.model.case_antenatal> scases = santRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_antenatal> mcases = new ArrayList<case_antenatal>();
			for (org.pdsr.slave.model.case_antenatal s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {
					case_antenatal mcase = new case_antenatal();
					mcase.setAntenatal_uuid(s.getAntenatal_uuid());
					mcase.setAntenatal_alcohol(s.getAntenatal_alcohol());
					mcase.setAntenatal_attend(s.getAntenatal_attend());
					mcase.setAntenatal_attendno(s.getAntenatal_attendno());
					mcase.setAntenatal_days(s.getAntenatal_days());
					mcase.setAntenatal_facility(s.getAntenatal_facility());
					mcase.setAntenatal_folicacid(s.getAntenatal_folicacid());
					mcase.setAntenatal_folicacid3m(s.getAntenatal_folicacid3m());
					mcase.setAntenatal_gravida(s.getAntenatal_gravida());
					mcase.setAntenatal_herbal(s.getAntenatal_herbal());
					mcase.setAntenatal_hiv(s.getAntenatal_hiv());
					mcase.setAntenatal_art(s.getAntenatal_art());
					mcase.setAntenatal_malprophy(s.getAntenatal_malprophy());
					mcase.setAntenatal_para(s.getAntenatal_para());
					mcase.setAntenatal_risks(s.getAntenatal_risks());

					List<risk_table> list = new ArrayList<risk_table>();
					for (org.pdsr.slave.model.risk_table r : santRepo.findRiskByAntenatalUuid(s.getAntenatal_uuid())) {
						risk_table item = new risk_table();
						item.setRisk_name(r.getRisk_name());
						item.setRisk_desc(r.getRisk_desc());

						list.add(item);
					}
					mcase.setRisks(list);
					mcase.setNew_risks(s.getNew_risks());

					mcase.setAntenatal_smoker(s.getAntenatal_smoker());
					mcase.setAntenatal_tetanus(s.getAntenatal_tetanus());
					mcase.setAntenatal_weeks(s.getAntenatal_weeks());

					mcase.setAntenatal_json(s.getAntenatal_json());

					mcase.setData_complete(s.getData_complete());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			antRepo.saveAll(mcases);
		}

	}

	private void mergeCaseLabour() {
		List<org.pdsr.slave.model.case_labour> scases = slabRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_labour> mcases = new ArrayList<case_labour>();
			for (org.pdsr.slave.model.case_labour s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {
					case_labour mcase = new case_labour();
					mcase.setLabour_uuid(s.getLabour_uuid());
					mcase.setLabour_complications(s.getLabour_complications());

					List<complication_table> list = new ArrayList<complication_table>();
					for (org.pdsr.slave.model.complication_table r : slabRepo
							.findComplicationsByUuid(s.getLabour_uuid())) {
						complication_table item = new complication_table();
						item.setComplication_name(r.getComplication_name());
						item.setComplication_desc(r.getComplication_desc());

						list.add(item);
					}

					mcase.setComplications(list);
					mcase.setNew_complications(s.getNew_complications());

					mcase.setLabour_herbalaug(s.getLabour_herbalaug());
					mcase.setLabour_lasthour1(s.getLabour_lasthour1());
					mcase.setLabour_lasthour2(s.getLabour_lasthour2());
					mcase.setLabour_lastminute1(s.getLabour_lastminute1());
					mcase.setLabour_lastminute2(s.getLabour_lastminute2());
					mcase.setLabour_partograph(s.getLabour_partograph());
					mcase.setLabour_seedate(s.getLabour_seedate());
					mcase.setLabour_seehour(s.getLabour_seehour());
					mcase.setLabour_seeminute(s.getLabour_seeminute());
					mcase.setLabour_seeperiod(s.getLabour_seeperiod());
					mcase.setLabour_startmode(s.getLabour_startmode());

					mcase.setLabour_json(s.getLabour_json());

					mcase.setData_complete(s.getData_complete());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			labRepo.saveAll(mcases);
		}

	}

	private void mergeCaseBirth() {
		List<org.pdsr.slave.model.case_birth> scases = sbirthRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_birth> mcases = new ArrayList<case_birth>();
			for (org.pdsr.slave.model.case_birth s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {
					case_birth mcase = new case_birth();
					mcase.setBirth_uuid(s.getBirth_uuid());
					mcase.setBirth_abnormalities(s.getBirth_abnormalities());

					List<abnormality_table> list = new ArrayList<abnormality_table>();
					for (org.pdsr.slave.model.abnormality_table r : sbirthRepo
							.findComplicationsByUuid(s.getBirth_uuid())) {
						abnormality_table item = new abnormality_table();
						item.setAbnormal_name(r.getAbnormal_name());
						item.setAbnormal_desc(r.getAbnormal_desc());

						list.add(item);
					}
					mcase.setAbnormalities(list);
					mcase.setNew_abnormalities(s.getNew_abnormalities());

					mcase.setBirth_babyoutcome(s.getBirth_babyoutcome());
					mcase.setBirth_cordfaults(s.getBirth_cordfaults());

					List<cordfault_table> list1 = new ArrayList<cordfault_table>();
					for (org.pdsr.slave.model.cordfault_table r : sbirthRepo.findCordfaultsByUuid(s.getBirth_uuid())) {
						cordfault_table item = new cordfault_table();
						item.setCordfault_name(r.getCordfault_name());
						item.setCordfault_desc(r.getCordfault_desc());

						list1.add(item);
					}
					mcase.setCordfaults(list1);
					mcase.setNew_cordfaults(s.getNew_cordfaults());

					mcase.setBirth_csproposedate(s.getBirth_csproposedate());
					mcase.setBirth_csproposehour(s.getBirth_csproposehour());
					mcase.setBirth_csproposeminute(s.getBirth_csproposeminute());
					mcase.setBirth_csproposetime(s.getBirth_csproposetime());
					mcase.setBirth_facility(s.getBirth_facility());
					mcase.setBirth_insistnormal(s.getBirth_insistnormal());
					mcase.setBirth_liqourcolor(s.getBirth_liqourcolor());
					mcase.setBirth_liqourodour(s.getBirth_liqourodour());
					mcase.setBirth_liqourvolume(s.getBirth_liqourvolume());
					mcase.setBirth_mode(s.getBirth_mode());
					mcase.setBirth_motheroutcome(s.getBirth_motheroutcome());
					mcase.setBirth_placentachecks(s.getBirth_placentachecks());

					List<placentacheck_table> list2 = new ArrayList<placentacheck_table>();
					for (org.pdsr.slave.model.placentacheck_table r : sbirthRepo
							.findPlacentachecksByUuid(s.getBirth_uuid())) {
						placentacheck_table item = new placentacheck_table();
						item.setPlacentacheck_name(r.getPlacentacheck_name());
						item.setPlacentacheck_desc(r.getPlacentacheck_desc());

						list2.add(item);
					}
					mcase.setPlacentachecks(list2);
					mcase.setNew_placentachecks(s.getNew_placentachecks());

					mcase.setBirth_provider(s.getBirth_provider());

					mcase.setBirth_json(s.getBirth_json());

					mcase.setData_complete(s.getData_complete());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			birthRepo.saveAll(mcases);
		}

	}

	private void mergeCaseFetalheart() {
		List<org.pdsr.slave.model.case_fetalheart> scases = sfetRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_fetalheart> mcases = new ArrayList<case_fetalheart>();
			for (org.pdsr.slave.model.case_fetalheart s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {
					case_fetalheart mcase = new case_fetalheart();
					mcase.setFetalheart_uuid(s.getFetalheart_uuid());
					mcase.setFetalheart_arrival(s.getFetalheart_arrival());
					mcase.setFetalheart_lastheard(s.getFetalheart_lastheard());
					mcase.setFetalheart_refered(s.getFetalheart_refered());

					mcase.setFetalheart_json(s.getFetalheart_json());
					mcase.setData_complete(s.getData_complete());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			fetRepo.saveAll(mcases);
		}

	}

	private void mergeCaseBabydeath() {
		List<org.pdsr.slave.model.case_babydeath> scases = sbabyRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_babydeath> mcases = new ArrayList<case_babydeath>();
			for (org.pdsr.slave.model.case_babydeath s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {
					case_babydeath mcase = new case_babydeath();
					mcase.setBaby_uuid(s.getBaby_uuid());
					mcase.setBaby_apgar1(s.getBaby_apgar1());
					mcase.setBaby_apgar5(s.getBaby_apgar5());
					mcase.setBaby_cry(s.getBaby_cry());
					mcase.setBaby_ddate(s.getBaby_ddate());
					mcase.setBaby_dhour(s.getBaby_dhour());
					mcase.setBaby_dminute(s.getBaby_dminute());
					mcase.setBaby_dtime(s.getBaby_dtime());
					mcase.setBaby_medicalcod(s.getBaby_medicalcod());

					mcase.setBaby_resuscitation(s.getBaby_resuscitation());

					List<resuscitation_table> list = new ArrayList<resuscitation_table>();
					for (org.pdsr.slave.model.resuscitation_table r : sbabyRepo
							.findResuscitationsByUuid(s.getBaby_uuid())) {
						resuscitation_table item = new resuscitation_table();
						item.setResuscitation_name(r.getResuscitation_name());
						item.setResuscitation_desc(r.getResuscitation_desc());

						list.add(item);
					}

					mcase.setResuscitations(list);
					mcase.setNew_resuscitation(s.getNew_resuscitation());

					mcase.setBaby_admitted(s.getBaby_admitted());
					List<diagnoses_table> list1 = new ArrayList<diagnoses_table>();
					for (org.pdsr.slave.model.diagnoses_table r : sbabyRepo.findDiagnosesByUuid(s.getBaby_uuid())) {
						diagnoses_table item = new diagnoses_table();
						item.setDiagnosis_name(r.getDiagnosis_name());
						item.setDiagnosis_desc(r.getDiagnosis_desc());

						list1.add(item);
					}

					mcase.setDiagnoses(list1);

					List<icd_diagnoses> list2 = new ArrayList<icd_diagnoses>();
					for (org.pdsr.slave.model.icd_diagnoses r : sbabyRepo.findICDDiagnosesByUuid(s.getBaby_uuid())) {
						icd_diagnoses item = new icd_diagnoses();
						item.setIcd_code(r.getIcd_code());
						item.setIcd_desc(r.getIcd_desc());

						list2.add(item);
					}

					mcase.setIcd_diagnoses(list2);

					mcase.setBaby_json(s.getBaby_json());
					;

					mcase.setData_complete(s.getData_complete());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			babyRepo.saveAll(mcases);
		}

	}

	private void mergeCaseNotes() {
		List<org.pdsr.slave.model.case_notes> scases = snoteRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_notes> mcases = new ArrayList<case_notes>();
			for (org.pdsr.slave.model.case_notes s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {
					case_notes mcase = new case_notes();
					mcase.setNotes_uuid(s.getNotes_uuid());
					mcase.setNotes_file(s.getNotes_file());
					mcase.setNotes_filetype(s.getNotes_filetype());
					mcase.setNotes_text(s.getNotes_text());

					mcase.setNotes_json(s.getNotes_json());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			noteRepo.saveAll(mcases);
		}

	}

	private void mergeAuditCase() {
		List<org.pdsr.slave.model.audit_case> scases = saudRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<audit_case> mcases = new ArrayList<audit_case>();
			for (org.pdsr.slave.model.audit_case s : scases) {
				audit_case mcase = new audit_case();
				mcase.setAudit_uuid(s.getAudit_uuid());
				mcase.setAudit_data(s.getAudit_data());
				mcase.setAudit_date(s.getAudit_date());
				mcase.setAudit_expired(s.getAudit_expired());
				mcase.setCase_death(s.getCase_death());

				mcases.add(mcase);

			}

			// save master
			audRepo.saveAll(mcases);
		}

		mergeAuditAudit();
	}

	private void mergeAuditAudit() {
		List<org.pdsr.slave.model.audit_audit> scases = saaudRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<audit_audit> mcases = new ArrayList<audit_audit>();
			for (org.pdsr.slave.model.audit_audit s : scases) {
				Optional<audit_case> icase = audRepo.findById(s.getAudit_case().getAudit_uuid());
				if (icase.isPresent()) {
					audit_audit mcase = new audit_audit();
					mcase.setAudit_uuid(s.getAudit_uuid());
					mcase.setAudit_cdate(s.getAudit_cdate());
					mcase.setAudit_csc(s.getAudit_csc());
					mcase.setAudit_death(s.getAudit_death());
					mcase.setAudit_delay1(s.getAudit_delay1());
					mcase.setAudit_delay2(s.getAudit_delay2());
					mcase.setAudit_delay3a(s.getAudit_delay3a());
					mcase.setAudit_delay3b(s.getAudit_delay3b());
					mcase.setAudit_delay3c(s.getAudit_delay3c());
					mcase.setAudit_facmfs(s.getAudit_facmfs());
					mcase.setAudit_hwkmfs(s.getAudit_hwkmfs());
					mcase.setAudit_icd10(s.getAudit_icd10());
					mcase.setAudit_icdpm(s.getAudit_icdpm());
					mcase.setAudit_ifcmfs(s.getAudit_ifcmfs());

					List<cfactor_table> list1 = new ArrayList<cfactor_table>();
					for (org.pdsr.slave.model.cfactor_table r : saaudRepo.findPatientFactorsByUuid(s.getAudit_uuid())) {
						cfactor_table item = new cfactor_table();
						item.setId(r.getId());
						item.setIdgroup(r.getIdgroup());
						item.setCfactor_name(r.getCfactor_name());
						list1.add(item);
					}
					mcase.setPatient_factors(list1);

					List<cfactor_table> list2 = new ArrayList<cfactor_table>();
					for (org.pdsr.slave.model.cfactor_table r : saaudRepo
							.findTransportFactorsByUuid(s.getAudit_uuid())) {
						cfactor_table item = new cfactor_table();
						item.setId(r.getId());
						item.setIdgroup(r.getIdgroup());
						item.setCfactor_name(r.getCfactor_name());
						list2.add(item);
					}
					mcase.setTransport_factors(list2);

					List<cfactor_table> list3 = new ArrayList<cfactor_table>();
					for (org.pdsr.slave.model.cfactor_table r : saaudRepo
							.findAdministrativeFactorsByUuid(s.getAudit_uuid())) {
						cfactor_table item = new cfactor_table();
						item.setId(r.getId());
						item.setIdgroup(r.getIdgroup());
						item.setCfactor_name(r.getCfactor_name());
						list3.add(item);
					}
					mcase.setAdministrative_factors(list3);

					List<cfactor_table> list4 = new ArrayList<cfactor_table>();
					for (org.pdsr.slave.model.cfactor_table r : saaudRepo
							.findHealthworkerFactorsByUuid(s.getAudit_uuid())) {
						cfactor_table item = new cfactor_table();
						item.setId(r.getId());
						item.setIdgroup(r.getIdgroup());
						item.setCfactor_name(r.getCfactor_name());
						list4.add(item);
					}
					mcase.setHealthworker_factors(list4);

					List<cfactor_table> list5 = new ArrayList<cfactor_table>();
					for (org.pdsr.slave.model.cfactor_table r : saaudRepo
							.findDocumentFactorsByUuid(s.getAudit_uuid())) {
						cfactor_table item = new cfactor_table();
						item.setId(r.getId());
						item.setIdgroup(r.getIdgroup());
						item.setCfactor_name(r.getCfactor_name());
						list5.add(item);
					}
					mcase.setDocument_factors(list5);

					List<mcondition_table> list6 = new ArrayList<mcondition_table>();
					for (org.pdsr.slave.model.mcondition_table r : saaudRepo
							.findMaternalConditionsByUuid(s.getAudit_uuid())) {
						mcondition_table item = new mcondition_table();
						item.setIcdm(r.getIcdm());
						item.setIcdmgroup(r.getIcdmgroup());
						item.setIcdm_name(r.getIcdm_name());
						list6.add(item);
					}
					mcase.setMaternal_conditions(list6);

					mcase.setData_sent(s.getData_sent());

					mcase.setAudit_json(s.getAudit_json());

					mcase.setAudit_case(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			aaudRepo.saveAll(mcases);
		}

		mergeAuditRecommendations();

	}

	private void mergeAuditRecommendations() {
		List<org.pdsr.slave.model.audit_recommendation> scases = srecRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<audit_recommendation> mcases = new ArrayList<audit_recommendation>();
			for (org.pdsr.slave.model.audit_recommendation s : scases) {
				Optional<audit_audit> icase = aaudRepo.findById(s.getAudit_uuid().getAudit_uuid());
				if (icase.isPresent()) {
					audit_recommendation mcase = new audit_recommendation();
					mcase.setRecommendation_uuid(s.getRecommendation_uuid());
					mcase.setRecommendation_comments(s.getRecommendation_comments());
					mcase.setRecommendation_date(s.getRecommendation_date());
					mcase.setRecommendation_deadline(s.getRecommendation_deadline());
					mcase.setRecommendation_leader(s.getRecommendation_leader());
					mcase.setRecommendation_reporter(s.getRecommendation_reporter());
					mcase.setRecommendation_resources(s.getRecommendation_resources());
					mcase.setRecommendation_status(s.getRecommendation_status());
					mcase.setRecommendation_task(s.getRecommendation_task());
					mcase.setRecommendation_title(s.getRecommendation_title());
					mcase.setData_sent(s.getData_sent());

					mcase.setAudit_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			recRepo.saveAll(mcases);
		}

	}

	private void pushuUserTable(final String code, final String district, final String region, final String country) {
		List<user_table> cases = userRepo.findAll();
		List<json_user_table> jsons = new ArrayList<>();
		for (user_table elem : cases) {

			json_user_table json = new json_user_table();
			json.setId(code + "_" + elem.getUsername().toLowerCase());
			json.setCode(code);
			json.setDistrict(district);
			json.setRegion(region);
			json.setCountry(country);
			json.setUsername(elem.getUsername());
			json.setPassword(elem.getPassword());
			json.setEnabled(elem.isEnabled());
			json.setUseremail(elem.getUseremail());
			json.setUserfullname(elem.getUserfullname());
			json.setUsercontact(elem.getUsercontact());
			json.setAlerted(elem.isAlerted());

			jsons.add(json);

		}

		DecryptedUserTable d = new DecryptedUserTable();
		d.setData(jsons);
		api.saveAll(d);
	}

	private void pullUserData() {
		
		Optional<sync_table> object = syncRepo.findById(CONSTANTS.FACILITY_ID);

		if (object.isPresent()) {

			final sync_table s = object.get();
			List<json_user_table> jsons = api.findAllUsers();
			List<user_table> received = new ArrayList<>();
			for (json_user_table elem : jsons) {
				

				if (elem.getCode().equals(s.getSync_code())) {

					user_table user = new user_table();
					Optional<user_table> usersearch = userRepo.findById(elem.getUsername());
					
					if(usersearch.isPresent()) {
						user = usersearch.get();
					}
					
					user.setUsername(elem.getUsername());
					user.setPassword(elem.getPassword());
					user.setEnabled(elem.isEnabled());
					user.setUseremail(elem.getUseremail());
					user.setUserfullname(elem.getUserfullname());
					user.setUsercontact(elem.getUsercontact());
					user.setAlerted(elem.isAlerted());
					
					received.add(user);

				}

			}
			
			userRepo.saveAll(received);
		}
	}

	private void pushCaseData(final String code, final String district, final String region, final String country) {
		List<case_identifiers> cases = caseRepo.findBySubmittedToPush();
		List<case_identifiers> sent = new ArrayList<>();
		List<json_case_identifiers> jsons = new ArrayList<>();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		for (case_identifiers elem : cases) {

			json_case_identifiers json = new json_case_identifiers();
			json.setId(elem.getCase_uuid() + elem.getCase_id().toLowerCase());
			json.setCode(code);
			json.setDistrict(district);
			json.setRegion(region);
			json.setCountry(country);

			json.setCase_date(f.format(elem.getCase_date()));
			json.setCase_death(elem.getCase_death());
			json.setCase_status(elem.getCase_status());

			jsons.add(json);

			elem.setData_sent(1);

			sent.add(elem);

		}

		DecryptedCaseIdentifiers d = new DecryptedCaseIdentifiers();
		d.setData(jsons);
		final String msg = api.saveAll(d);
		if ("success".equals(msg)) {
			caseRepo.saveAll(sent);
		}
	}

	private void pushCaseData_ALL(final String code, final String district, final String region, final String country) {
		List<case_identifiers> cases = caseRepo.findAll();
		List<case_identifiers> sent = new ArrayList<>();
		List<json_case_identifiers> jsons = new ArrayList<>();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		for (case_identifiers elem : cases) {

			json_case_identifiers json = new json_case_identifiers();
			json.setId(elem.getCase_uuid() + elem.getCase_id().toLowerCase());
			json.setCode(code);
			json.setDistrict(district);
			json.setRegion(region);
			json.setCountry(country);

			json.setCase_date(f.format(elem.getCase_date()));
			json.setCase_death(elem.getCase_death());
			json.setCase_status(elem.getCase_status());

			jsons.add(json);

			elem.setData_sent(1);

			sent.add(elem);

		}

		DecryptedCaseIdentifiers d = new DecryptedCaseIdentifiers();
		d.setData(jsons);
		final String msg = api.saveAll(d);
		if ("success".equals(msg)) {
			caseRepo.saveAll(sent);
		}
	}

	private void pullCaseData() {
		List<json_case_identifiers> jsons = api.findAllCases();
		List<big_case_identifiers> received = new ArrayList<>();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		for (json_case_identifiers elem : jsons) {

			big_case_identifiers json = new big_case_identifiers();

			SummaryPK pk = new SummaryPK(elem.getId(), elem.getCode(), elem.getCountry(), elem.getRegion(),
					elem.getDistrict());
			json.setSummaryPk(pk);

			json.setCase_death(elem.getCase_death());
			try {
				json.setCase_date(f.parse(elem.getCase_date()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			json.setCase_status(elem.getCase_status());

			received.add(json);

		}
		bcaseRepo.saveAll(received);
	}

	private void pushAuditData(final String code, final String district, final String region, final String country) {
		List<audit_audit> cases = aaudRepo.findByAuditsToPush();
		List<audit_audit> sent = new ArrayList<>();
		List<json_audit_audit> jsons = new ArrayList<>();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		for (audit_audit elem : cases) {

			json_audit_audit json = new json_audit_audit();
			json.setId(elem.getAudit_uuid() + country + code.toUpperCase());
			json.setCode(code);
			json.setDistrict(district);
			json.setRegion(region);
			json.setCountry(country);
			json.setRec_complete(elem.getRec_complete());
			json.setAudit_cdate(f.format(elem.getAudit_cdate()));

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

			// data about the case
			json.setCase_data(elem.getAudit_case().getAudit_data());

			// data about the audit
			json.setAudit_data(elem.getAudit_json());

			jsons.add(json);

			elem.setData_sent(1);

			sent.add(elem);

		}
		DecryptedAuditAudit d = new DecryptedAuditAudit();
		d.setData(jsons);
		final String msg = api.saveAll(d);
		if ("success".equals(msg)) {
			aaudRepo.saveAll(sent);
		}

	}

	private void pushAuditData_ALL(final String code, final String district, final String region,
			final String country) {
		List<audit_audit> cases = aaudRepo.findAll();
		List<audit_audit> sent = new ArrayList<>();
		List<json_audit_audit> jsons = new ArrayList<>();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		for (audit_audit elem : cases) {

			json_audit_audit json = new json_audit_audit();
			json.setId(elem.getAudit_uuid() + country + code.toUpperCase());
			json.setCode(code);
			json.setDistrict(district);
			json.setRegion(region);
			json.setCountry(country);
			json.setRec_complete(elem.getRec_complete());
			json.setAudit_cdate(f.format(elem.getAudit_cdate()));

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

			// data about the case
			json.setCase_data(elem.getAudit_case().getAudit_data());

			// data about the audit
			json.setAudit_data(elem.getAudit_json());

			jsons.add(json);

			elem.setData_sent(1);

			sent.add(elem);

		}
		DecryptedAuditAudit d = new DecryptedAuditAudit();
		d.setData(jsons);
		final String msg = api.saveAll(d);
		if ("success".equals(msg)) {
			aaudRepo.saveAll(sent);
		}

	}

	private void pullAuditData() {
		List<json_audit_audit> jsons = api.findAllAudits();
		List<big_audit_audit> received = new ArrayList<>();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		for (json_audit_audit elem : jsons) {

			big_audit_audit json = new big_audit_audit();

			SummaryPK pk = new SummaryPK(elem.getId(), elem.getCode(), elem.getCountry(), elem.getRegion(),
					elem.getDistrict());
			json.setSummaryPk(pk);

			json.setRec_complete(elem.getRec_complete());
			try {
				json.setAudit_cdate(f.parse(elem.getAudit_cdate()));
			} catch (ParseException e) {
			}
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

			received.add(json);

		}
		baaudRepo.saveAll(received);

	}

	private void pushRecommendationData(final String code, final String district, final String region,
			final String country) {
		List<audit_recommendation> cases = recRepo.findActionsToPush();
		List<audit_recommendation> sent = new ArrayList<>();
		List<json_audit_recommendation> jsons = new ArrayList<>();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		for (audit_recommendation elem : cases) {

			json_audit_recommendation json = new json_audit_recommendation();
			json.setId(elem.getRecommendation_uuid() + country + code.toUpperCase());
			json.setAudit_uuid(elem.getAudit_uuid().getAudit_uuid() + country + code.toUpperCase());
			json.setDistrict(district);
			json.setRegion(region);
			json.setCountry(country);
			json.setRecommendation_comments(elem.getRecommendation_comments());
			json.setRecommendation_date(f.format(elem.getRecommendation_date()));
			json.setRecommendation_deadline(f.format(elem.getRecommendation_deadline()));
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
		DecryptedAuditRecommendation d = new DecryptedAuditRecommendation();
		d.setData(jsons);
		final String msg = api.saveAll(d);
		if ("success".equals(msg)) {
			recRepo.saveAll(sent);
		}

	}

	private void pushRecommendationData_ALL(final String code, final String district, final String region,
			final String country) {
		List<audit_recommendation> cases = recRepo.findAll();
		List<audit_recommendation> sent = new ArrayList<>();
		List<json_audit_recommendation> jsons = new ArrayList<>();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		for (audit_recommendation elem : cases) {

			json_audit_recommendation json = new json_audit_recommendation();
			json.setId(elem.getRecommendation_uuid() + country + code.toUpperCase());
			json.setAudit_uuid(elem.getAudit_uuid().getAudit_uuid() + country + code.toUpperCase());
			json.setDistrict(district);
			json.setRegion(region);
			json.setCountry(country);
			json.setRecommendation_comments(elem.getRecommendation_comments());
			json.setRecommendation_date(f.format(elem.getRecommendation_date()));
			json.setRecommendation_deadline(f.format(elem.getRecommendation_deadline()));
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
		DecryptedAuditRecommendation d = new DecryptedAuditRecommendation();
		d.setData(jsons);
		final String msg = api.saveAll(d);
		if ("success".equals(msg)) {
			recRepo.saveAll(sent);
		}

	}

	private void pullRecommendationData() {
		List<json_audit_recommendation> jsons = api.findAllRecommendations();
		List<big_audit_recommendation> received = new ArrayList<>();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		for (json_audit_recommendation elem : jsons) {

			big_audit_recommendation json = new big_audit_recommendation();

			SummaryPK pk = new SummaryPK(elem.getId(), elem.getCode(), elem.getCountry(), elem.getRegion(),
					elem.getDistrict());
			json.setSummaryPk(pk);

			json.setAudit_uuid(elem.getAudit_uuid());

			json.setRecommendation_comments(elem.getRecommendation_comments());
			try {
				json.setRecommendation_date(f.parse(elem.getRecommendation_date()));
			} catch (ParseException e) {
			}
			try {
				json.setRecommendation_deadline(f.parse(elem.getRecommendation_deadline()));
			} catch (ParseException e) {
			}
			json.setRecommendation_leader(elem.getRecommendation_leader());
			json.setRecommendation_reporter(elem.getRecommendation_reporter());
			json.setRecommendation_resources(elem.getRecommendation_resources());
			json.setRecommendation_status(elem.getRecommendation_status());
			json.setRecommendation_task(elem.getRecommendation_task());
			json.setRecommendation_title(elem.getRecommendation_title());

			received.add(json);

		}
		brecRepo.saveAll(received);

	}

	private void pushMonitoringData(final String code, final String district, final String region,
			final String country) {
		List<weekly_monitoring> cases = weekMRepo.findMonitoringToPush();
		List<weekly_monitoring> sent = new ArrayList<>();
		List<json_weekly_monitoring> jsons = new ArrayList<>();
		for (weekly_monitoring elem : cases) {

			json_weekly_monitoring json = new json_weekly_monitoring();
			json.setId("" + elem.getId().getWeekly_id() + elem.getId().getMindex() + country + code.toUpperCase());
			json.setCode(code);
			json.setDistrict(district);
			json.setRegion(region);
			json.setCountry(country);
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

		DecryptedWeeklyMonitoring d = new DecryptedWeeklyMonitoring();
		d.setData(jsons);
		final String msg = api.saveAll(d);
		if ("success".equals(msg)) {
			weekMRepo.saveAll(sent);
		}

	}

	private void pushMonitoringData_ALL(final String code, final String district, final String region,
			final String country) {
		List<weekly_monitoring> cases = weekMRepo.findAll();
		List<weekly_monitoring> sent = new ArrayList<>();
		List<json_weekly_monitoring> jsons = new ArrayList<>();
		for (weekly_monitoring elem : cases) {

			json_weekly_monitoring json = new json_weekly_monitoring();
			json.setId("" + elem.getId().getWeekly_id() + elem.getId().getMindex() + country + code.toUpperCase());
			json.setCode(code);
			json.setDistrict(district);
			json.setRegion(region);
			json.setCountry(country);
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

		DecryptedWeeklyMonitoring d = new DecryptedWeeklyMonitoring();
		d.setData(jsons);
		final String msg = api.saveAll(d);
		if ("success".equals(msg)) {
			weekMRepo.saveAll(sent);
		}

	}

	private void pullMonitoringData() {

		List<json_weekly_monitoring> jsons = api.findAllMonitoring();
		List<big_weekly_monitoring> received = new ArrayList<>();
		for (json_weekly_monitoring elem : jsons) {

			big_weekly_monitoring json = new big_weekly_monitoring();

			SummaryPK pk = new SummaryPK(elem.getId(), elem.getCode(), elem.getCountry(), elem.getRegion(),
					elem.getDistrict());
			json.setSummaryPk(pk);

			json.setMindex(elem.getMindex());
			json.setWeekly_mdesc(elem.getWeekly_mdesc());
			json.setWeekly_month(elem.getWeekly_month());
			json.setWeekly_week(elem.getWeekly_week());
			json.setWeekly_year(elem.getWeekly_year());
			json.setWm_subval(elem.getWm_subval());
			json.setWm_values(elem.getWm_values());

			received.add(json);

		}

		bweekMRepo.saveAll(received);

	}

	private List<icd_codes> loadICD() throws IOException {

		byte[] bytes = new byte[0];

		bytes = CONSTANTS.readICD10("icd_code.csv");

		try (Reader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)))) {

			CsvToBean<icd_codes> csvToBean = new CsvToBeanBuilder<icd_codes>(reader).withType(icd_codes.class)
					.withIgnoreLeadingWhiteSpace(true).build();

			return csvToBean.parse();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ArrayList<>();
	}

	private List<icd_diagnoses> loadICDD() throws IOException {

		byte[] bytes = new byte[0];

		bytes = CONSTANTS.readICD10("icd_diagnoses.csv");

		try (Reader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)))) {

			CsvToBean<icd_diagnoses> csvToBean = new CsvToBeanBuilder<icd_diagnoses>(reader)
					.withType(icd_diagnoses.class).withIgnoreLeadingWhiteSpace(true).build();

			return csvToBean.parse();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ArrayList<>();
	}

}
// end class