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

import javax.transaction.Transactional;

import org.pdsr.CONSTANTS;
import org.pdsr.master.model.country_table;
import org.pdsr.master.model.district_table;
import org.pdsr.master.model.facility_table;
import org.pdsr.master.model.icd_codes;
import org.pdsr.master.model.icd_diagnoses;
import org.pdsr.master.model.region_table;
import org.pdsr.master.model.sync_table;
import org.pdsr.master.repo.CountryTableRepository;
import org.pdsr.master.repo.DistrictTableRepository;
import org.pdsr.master.repo.FacilityTableRepository;
import org.pdsr.master.repo.IcdCodesRepository;
import org.pdsr.master.repo.IcdDiagnosesRepository;
import org.pdsr.master.repo.RegionTableRepository;
import org.pdsr.master.repo.SyncTableRepository;
import org.pdsr.pojos.datamerger;
import org.pdsr.slave.repo.SlaveCountryTableRepository;
import org.pdsr.slave.repo.SlaveDistrictTableRepository;
import org.pdsr.slave.repo.SlaveFacilityTableRepository;
import org.pdsr.slave.repo.SlaveRegionTableRepository;
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
	private SyncTableRepository syncRepo;

	@Autowired
	private FacilityTableRepository facilityRepo;

	@Autowired
	private DistrictTableRepository districtRepo;

	@Autowired
	private RegionTableRepository regionRepo;

	@Autowired
	private CountryTableRepository countryRepo;

	@Autowired
	private IcdCodesRepository icdRepo;

	@Autowired
	private IcdDiagnosesRepository icddRepo;

	@Autowired
	private SlaveFacilityTableRepository sfacilityRepo;

	@Autowired
	private SlaveDistrictTableRepository sdistrictRepo;

	@Autowired
	private SlaveRegionTableRepository sregionRepo;

	@Autowired
	private SlaveCountryTableRepository scounrtyRepo;

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

		return "controls/merger";
	}


	@Transactional
	@PostMapping("/datamerge")
	public String datamerge(Principal principal, @ModelAttribute("selected") datamerger selected) {

		// merge slave countries
		if (selected.isMerge_country()) {
			List<org.pdsr.slave.model.country_table> scountries = scounrtyRepo.findAll();
			if (scountries != null && scountries.size() > 0) {
				List<country_table> countries = new ArrayList<country_table>();
				for (org.pdsr.slave.model.country_table s : scountries) {
					country_table country = new country_table();
					country.setCountry_uuid(s.getCountry_uuid());
					country.setCountry_name(s.getCountry_name());
					countries.add(country);
				}
				countryRepo.saveAll(countries);
			}
		}

		// merge slave regions for which countries exist in master
		if (selected.isMerge_region()) {
			List<org.pdsr.slave.model.region_table> sregions = sregionRepo.findAll();
			if (sregions != null && sregions.size() > 0) {
				List<region_table> regions = new ArrayList<region_table>();
				for (org.pdsr.slave.model.region_table s : sregions) {
					Optional<country_table> country = countryRepo.findById(s.getCountry().getCountry_uuid());
					if (country.isPresent()) {
						region_table region = new region_table();
						region.setRegion_uuid(s.getRegion_uuid());
						region.setRegion_name(s.getRegion_name());
						region.setCountry(country.get());

						regions.add(region);
					}
				}
				regionRepo.saveAll(regions);
			}
		}

		// merge slave districts for which regions exist in master
		if (selected.isMerge_district()) {
			List<org.pdsr.slave.model.district_table> sdistricts = sdistrictRepo.findAll();
			if (sdistricts != null && sdistricts.size() > 0) {
				List<district_table> districts = new ArrayList<district_table>();
				for (org.pdsr.slave.model.district_table s : sdistricts) {
					Optional<region_table> region = regionRepo.findById(s.getRegion().getRegion_uuid());
					if (region.isPresent()) {
						district_table district = new district_table();
						district.setDistrict_uuid(s.getDistrict_uuid());
						district.setDistrict_name(s.getDistrict_name());
						district.setRegion(region.get());

						districts.add(district);
					}
				}

				districtRepo.saveAll(districts);
			}
		}

		// merge slave facilities for which master districts exist
		if (selected.isMerge_facility()) {
			List<org.pdsr.slave.model.facility_table> sfacilities = sfacilityRepo.findAll();
			if (sfacilities != null && sfacilities.size() > 0) {
				List<facility_table> facilities = new ArrayList<facility_table>();
				for (org.pdsr.slave.model.facility_table s : sfacilities) {
					Optional<district_table> district = districtRepo.findById(s.getDistrict().getDistrict_uuid());
					if (district.isPresent()) {
						facility_table facility = new facility_table();
						facility.setFacility_uuid(s.getFacility_uuid());
						facility.setFacility_code(s.getFacility_code());
						facility.setFacility_name(s.getFacility_name());
						facility.setDistrict(district.get());

						facilities.add(facility);
					}
				}

				facilityRepo.saveAll(facilities);
			}
		}
		return "redirect:/controls/datamerge?success=yes";
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