package org.pdsr.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.pdsr.CONSTANTS;
import org.pdsr.json.json_data;
import org.pdsr.model.abnormality_table;
import org.pdsr.model.case_antenatal;
import org.pdsr.model.case_babydeath;
import org.pdsr.model.case_biodata;
import org.pdsr.model.case_birth;
import org.pdsr.model.case_delivery;
import org.pdsr.model.case_fetalheart;
import org.pdsr.model.case_identifiers;
import org.pdsr.model.case_labour;
import org.pdsr.model.case_notes;
import org.pdsr.model.case_pregnancy;
import org.pdsr.model.case_referral;
import org.pdsr.model.complication_table;
import org.pdsr.model.cordfault_table;
import org.pdsr.model.datamap;
import org.pdsr.model.datamapPK;
import org.pdsr.model.diagnoses_table;
import org.pdsr.model.facility_table;
import org.pdsr.model.placentacheck_table;
import org.pdsr.model.resuscitation_table;
import org.pdsr.model.risk_table;
import org.pdsr.model.sync_table;
import org.pdsr.repo.AbnormalityTableRepository;
import org.pdsr.repo.CaseAntenatalRepository;
import org.pdsr.repo.CaseBabyRepository;
import org.pdsr.repo.CaseBiodataRepository;
import org.pdsr.repo.CaseBirthRepository;
import org.pdsr.repo.CaseDeliveryRepository;
import org.pdsr.repo.CaseFetalheartRepository;
import org.pdsr.repo.CaseLabourRepository;
import org.pdsr.repo.CaseNotesRepository;
import org.pdsr.repo.CasePregnancyRepository;
import org.pdsr.repo.CaseReferralRepository;
import org.pdsr.repo.CaseRepository;
import org.pdsr.repo.ComplicationTableRepository;
import org.pdsr.repo.CordfaultTableRepository;
import org.pdsr.repo.DatamapRepository;
import org.pdsr.repo.DiagnosesTableRepository;
import org.pdsr.repo.FacilityTableRepository;
import org.pdsr.repo.PlacentacheckTableRepository;
import org.pdsr.repo.ResuscitationTableRepository;
import org.pdsr.repo.RiskTableRepository;
import org.pdsr.repo.SyncTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Controller
@RequestMapping("/registry")
public class CaseEntryController {

	@Autowired
	private SyncTableRepository syncRepo;

	@Autowired
	private ComplicationTableRepository compRepo;

	@Autowired
	private ResuscitationTableRepository resusRepo;

	@Autowired
	private DiagnosesTableRepository diagRepo;

	@Autowired
	private RiskTableRepository riskRepo;

	@Autowired
	private AbnormalityTableRepository abnoRepo;

	@Autowired
	private CordfaultTableRepository cordRepo;

	@Autowired
	private PlacentacheckTableRepository placRepo;

	@Autowired
	private FacilityTableRepository facilityRepo;

	@Autowired
	private CaseRepository caseRepo;

	@Autowired
	private CaseBiodataRepository bioRepo;

	@Autowired
	private CaseReferralRepository refRepo;

	@Autowired
	private CaseAntenatalRepository antRepo;

	@Autowired
	private CasePregnancyRepository preRepo;

	@Autowired
	private CaseLabourRepository labRepo;

	@Autowired
	private CaseDeliveryRepository delRepo;

	@Autowired
	private CaseBirthRepository birRepo;

	@Autowired
	private CaseFetalheartRepository fetRepo;

	@Autowired
	private CaseBabyRepository babyRepo;

	@Autowired
	private CaseNotesRepository notRepo;

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

		model.addAttribute("items", caseRepo.findByCase_status(0));
		model.addAttribute("back", "back");

		return "registry/case-retrieve";
	}

	@GetMapping("/add")
	public String add(Principal principal, Model model) {

		if (syncRepo.findById(CONSTANTS.FACILITY_ID).isEmpty()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		case_identifiers selected = new case_identifiers();
		selected.setCase_date(new java.util.Date());

		Optional<sync_table> code = syncRepo.findById(CONSTANTS.FACILITY_ID);
		if (code.isPresent()) {
			Optional<facility_table> facility = facilityRepo.findById(code.get().getSync_uuid());
			selected.setFacility(facility.get());
		}

		model.addAttribute("selected", selected);

		return "registry/case-create";
	}

	@Transactional
	@PostMapping("/add")
	public String add(Principal principal, Model model, @ModelAttribute("selected") case_identifiers selected) {
		if (syncRepo.findById(CONSTANTS.FACILITY_ID).isEmpty()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		selected.setCase_uuid(UUID.randomUUID().toString());
		selected.setCase_id("T" + selected.getCase_death() + "C" + (new java.util.Date().getTime()));
		selected.setCase_status(0);
		selected.setCase_sync(syncRepo.findById(CONSTANTS.FACILITY_ID).get().getSync_code());

		caseRepo.save(selected);

		return "redirect:/registry/edit/" + selected.getCase_uuid() + "?page=1&success=yes";
	}

	@GetMapping("/edit/{id}")
	public String edit(Principal principal, final Model model, @PathVariable("id") String case_uuid,
			@RequestParam(name = "page", required = true) Integer page,
			@RequestParam(name = "success", required = false) String success) {

		if (syncRepo.findById(CONSTANTS.FACILITY_ID).isEmpty()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		case_identifiers selected = caseRepo.findById(case_uuid).get();

		final boolean completed = selected.getBiodata() != null && selected.getPregnancy() != null
				&& selected.getReferral() != null && selected.getDelivery() != null && selected.getAntenatal() != null
				&& selected.getLabour() != null && selected.getBirth() != null
				&& (selected.getFetalheart() != null || selected.getBabydeath() != null) && selected.getNotes() != null;

		if (completed) {
			model.addAttribute("completed", completed);
		}

		switch (page) {
		case 1: {
			if (selected.getBiodata() == null) {
				case_biodata data = new case_biodata();
				data.setBiodata_uuid(UUID.randomUUID().toString());
				data.setCase_uuid(selected);
				selected.setBiodata(data);
			}
			break;
		}

		case 2: {
			if (selected.getPregnancy() == null) {
				case_pregnancy data = new case_pregnancy();
				data.setPregnancy_uuid(UUID.randomUUID().toString());
				data.setCase_uuid(selected);
				selected.setPregnancy(data);
			}
			break;
		}

		case 3: {
			if (selected.getReferral() == null) {
				case_referral data = new case_referral();
				data.setReferral_uuid(UUID.randomUUID().toString());
				data.setCase_uuid(selected);
				selected.setReferral(data);
			}
			break;
		}
		case 4: {
			if (selected.getDelivery() == null) {
				case_delivery data = new case_delivery();
				data.setDelivery_uuid(UUID.randomUUID().toString());
				data.setCase_uuid(selected);
				selected.setDelivery(data);
			}
			break;
		}

		case 5: {
			if (selected.getAntenatal() == null) {
				case_antenatal data = new case_antenatal();
				data.setAntenatal_uuid(UUID.randomUUID().toString());
				data.setCase_uuid(selected);
				selected.setAntenatal(data);
			}
			model.addAttribute("risk_options", riskRepo.findAll());
			break;
		}

		case 6: {
			if (selected.getLabour() == null) {
				case_labour data = new case_labour();
				data.setLabour_uuid(UUID.randomUUID().toString());
				data.setCase_uuid(selected);
				selected.setLabour(data);
			}
			model.addAttribute("complication_options", compRepo.findAll());
			break;
		}

		case 7: {
			if (selected.getBirth() == null) {
				case_birth data = new case_birth();
				data.setBirth_uuid(UUID.randomUUID().toString());
				data.setCase_uuid(selected);
				selected.setBirth(data);
			}
			model.addAttribute("abnormality_options", abnoRepo.findAll());
			model.addAttribute("cordfault_options", cordRepo.findAll());
			model.addAttribute("placentacheck_options", placRepo.findAll());
			break;
		}

		case 8: {
			if (selected.getCase_death() == 1) {
				if (selected.getFetalheart() == null) {
					case_fetalheart data = new case_fetalheart();
					data.setFetalheart_uuid(UUID.randomUUID().toString());
					data.setCase_uuid(selected);
					selected.setFetalheart(data);

				}
			} else if (selected.getCase_death() == 2) {

				if (selected.getBabydeath() == null) {
					case_babydeath data = new case_babydeath();
					data.setBaby_uuid(UUID.randomUUID().toString());
					data.setCase_uuid(selected);
					selected.setBabydeath(data);
				}
				model.addAttribute("diagnoses_options", diagRepo.findAll());
				model.addAttribute("resuscitation_options", resusRepo.findAll());
			}
			break;
		}

		case 9: {
			if (selected.getNotes() == null) {
				case_notes data = new case_notes();
				data.setNotes_uuid(UUID.randomUUID().toString());
				data.setCase_uuid(selected);
				selected.setNotes(data);
			}
			break;
		}

		}

		model.addAttribute("selected", selected);
		model.addAttribute("page", page);

		if (success != null) {
			model.addAttribute("success", "Saved Successfully");
		}

		return "registry/case-update";
	}

	@SuppressWarnings("deprecation")
	@Transactional
	@PostMapping("/edit/{id}")
	public String edit(Principal principal, Model model, @ModelAttribute("selected") case_identifiers selected,
			@RequestParam(name = "page", required = true) Integer page, BindingResult results) {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		if (syncRepo.findById(CONSTANTS.FACILITY_ID).isEmpty()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		selected.setCase_status(0);

		switch (page) {
		case 1: {

			try {
				final String arrayToJson = objectMapper.writeValueAsString(processListOf(selected.getBiodata()));
				selected.getBiodata().setBiodata_json(arrayToJson);

				bioRepo.save(selected.getBiodata());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			break;
		}
		case 2: {
			try {
				final String arrayToJson = objectMapper.writeValueAsString(processListOf(selected.getPregnancy()));
				selected.getPregnancy().setPregnancy_json(arrayToJson);

				preRepo.save(selected.getPregnancy());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			break;
		}
		case 3: {
			try {
				final String arrayToJson = objectMapper.writeValueAsString(processListOf(selected.getReferral()));
				selected.getReferral().setReferral_json(arrayToJson);

				java.util.Date time = selected.getReferral().getReferral_time();
				selected.getReferral().setReferral_hour(time.getHours());
				selected.getReferral().setReferral_minute(time.getMinutes());

				java.util.Date atime = selected.getReferral().getReferral_atime();
				selected.getReferral().setReferral_ahour(atime.getHours());
				selected.getReferral().setReferral_aminute(atime.getMinutes());

				refRepo.save(selected.getReferral());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			break;
		}
		case 4: {
			try {
				final String arrayToJson = objectMapper.writeValueAsString(processListOf(selected.getDelivery()));
				selected.getDelivery().setDelivery_json(arrayToJson);

				java.util.Date time = selected.getDelivery().getDelivery_time();
				selected.getDelivery().setDelivery_hour(time.getHours());
				selected.getDelivery().setDelivery_minute(time.getMinutes());

				final Integer period = selected.getDelivery().getDelivery_period();
				final Integer hour = selected.getDelivery().getDelivery_hour();

				boolean dawn = (hour > 0 && hour < 6);
				boolean morning = (hour > 5 && hour < 12);
				boolean midday = (hour == 12);
				boolean afternoon = (hour > 12 && hour < 18);
				boolean evening = (hour > 17 && hour < 22);
				boolean midnight = (hour > 21 && hour < 1);

				if (period == 0 && !dawn) {
					results.rejectValue("delivery.delivery_period", "error.dawn");
				} else if (period == 1 && !morning) {
					results.rejectValue("delivery.delivery_period", "error.morning");
				} else if (period == 2 && !midday) {
					results.rejectValue("delivery.delivery_period", "error.midday");
				} else if (period == 3 && !afternoon) {
					results.rejectValue("delivery.delivery_period", "error.afternoon");
				} else if (period == 4 && !evening) {
					results.rejectValue("delivery.delivery_period", "error.evening");
				} else if (period == 4 && !midnight) {
					results.rejectValue("delivery.delivery_period", "error.midnight");
				}

				if (results.hasErrors()) {
					model.addAttribute("selected", selected);
					model.addAttribute("page", page);
					return "registry/case-update";
				}

				delRepo.save(selected.getDelivery());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			break;
		}
		case 5: {
			try {
				final String arrayToJson = objectMapper.writeValueAsString(processListOf(selected.getAntenatal()));
				selected.getAntenatal().setAntenatal_json(arrayToJson);

				antRepo.save(selected.getAntenatal());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			break;
		}
		case 6: {
			try {

				java.util.Date seetime = selected.getLabour().getLabour_seetime();
				selected.getLabour().setLabour_seehour(seetime.getHours());
				selected.getLabour().setLabour_seeminute(seetime.getMinutes());

				final String arrayToJson = objectMapper.writeValueAsString(processListOf(selected.getLabour()));
				selected.getLabour().setLabour_json(arrayToJson);

				final Integer period = selected.getLabour().getLabour_seeperiod();
				final Integer hour = selected.getLabour().getLabour_seehour();

				boolean dawn = (hour > 0 && hour < 6);
				boolean morning = (hour > 5 && hour < 12);
				boolean midday = (hour == 12);
				boolean afternoon = (hour > 12 && hour < 18);
				boolean evening = (hour > 17 && hour < 22);
				boolean midnight = (hour > 21 && hour < 1);

				if (period == 0 && !dawn) {
					results.rejectValue("labour.labour_seeperiod", "error.dawn");
				} else if (period == 1 && !morning) {
					results.rejectValue("labour.labour_seeperiod", "error.morning");
				} else if (period == 2 && !midday) {
					results.rejectValue("labour.labour_seeperiod", "error.midday");
				} else if (period == 3 && !afternoon) {
					results.rejectValue("labour.labour_seeperiod", "error.afternoon");
				} else if (period == 4 && !evening) {
					results.rejectValue("labour.labour_seeperiod", "error.evening");
				} else if (period == 4 && !midnight) {
					results.rejectValue("labour.labour_seeperiod", "error.midnight");
				}

				if (results.hasErrors()) {
					model.addAttribute("selected", selected);
					model.addAttribute("page", page);
					return "registry/case-update";
				}

				labRepo.save(selected.getLabour());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			break;
		}
		case 7: {
			try {
				java.util.Date cstime = selected.getBirth().getBirth_csproposetime();
				selected.getBirth().setBirth_csproposehour(cstime.getHours());
				selected.getBirth().setBirth_csproposeminute(cstime.getMinutes());

				final String arrayToJson = objectMapper.writeValueAsString(processListOf(selected.getBirth()));
				selected.getBirth().setBirth_json(arrayToJson);

				birRepo.save(selected.getBirth());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			break;
		}
		case 8: {
			if (selected.getCase_death() == 1) {
				try {
					final String arrayToJson = objectMapper.writeValueAsString(processListOf(selected.getFetalheart()));
					selected.getFetalheart().setFetalheart_json(arrayToJson);

					fetRepo.save(selected.getFetalheart());
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}

			} else if (selected.getCase_death() == 2) {
				try {
					java.util.Date time = selected.getBabydeath().getBaby_dtime();
					selected.getBabydeath().setBaby_dhour(time.getHours());
					selected.getBabydeath().setBaby_dminute(time.getMinutes());

					final String arrayToJson = objectMapper.writeValueAsString(processListOf(selected.getBabydeath()));
					selected.getBabydeath().setBaby_json(arrayToJson);

					babyRepo.save(selected.getBabydeath());
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}

			}
			break;
		}
		case 9: {
			try {
				final String arrayToJson = objectMapper.writeValueAsString(processListOf(selected.getNotes()));
				selected.getNotes().setNotes_json(arrayToJson);

				notRepo.save(selected.getNotes());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			break;
		}
		default: {
			caseRepo.save(selected);
			break;
		}
		}

		return "redirect:/registry/edit/" + selected.getCase_uuid() + "?page=" + page + "&success=yes";
	}

	@GetMapping("/submit/{id}")
	public String submit(Principal principal, Model model, @PathVariable("id") String case_uuid) {

		if (syncRepo.findById(CONSTANTS.FACILITY_ID).isEmpty()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		case_identifiers selected = caseRepo.findById(case_uuid).get();

		model.addAttribute("selected", selected);

		return "registry/case-submit";
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
		map.put(1, getQuestion("label.still.birth"));
		map.put(2, getQuestion("label.neonatal.death"));

		return map;
	}

	@ModelAttribute("sex_options")
	public Map<Integer, String> sexOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("sex_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("edu_options")
	public Map<Integer, String> eduOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("edu_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("pweeks_options")
	public Map<Integer, String> pweeksOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		for (datamap elem : mapRepo.findByMap_feature("pweeks_options")) {
			map.put(elem.getMap_value(), elem.getMap_value() + " " + elem.getMap_label());
		}

		return new TreeMap<Integer, String>(map);
	}

	@ModelAttribute("pdays_options")
	public Map<Integer, String> pdaysOptionsSelectOne() {
		final Map<Integer, String> map = new HashMap<>();

		for (datamap elem : mapRepo.findByMap_feature("pdays_options")) {
			map.put(elem.getMap_value(), elem.getMap_value() + " " + elem.getMap_label());
		}

		return new TreeMap<Integer, String>(map);
	}

	@ModelAttribute("ptype_options")
	public Map<Integer, String> ptypeOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("ptype_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("yesnodk_options")
	public Map<Integer, String> yesnodkOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("yesnodk_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("yesnodkna_options")
	public Map<Integer, String> yesnodknaOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("yesnodkna_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("hiv_options")
	public Map<Integer, String> hivOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("hiv_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("patient_options")
	public Map<Integer, String> patientOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("patient_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("source_options")
	public Map<Integer, String> sourceOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("source_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("trans_options")
	public Map<Integer, String> transOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("trans_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("period_options")
	public Map<Integer, String> periodOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("period_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("mode_options")
	public Map<Integer, String> modeOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("mode_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

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
		map.put(99, "Not Applicable");

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

	@ModelAttribute("hour_options")
	public Map<Integer, String> hourOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (int i = 0; i < 25; i++) {
			map.put(i, i + " hour(s)");
		}
		map.put(88, "Not stated");
		map.put(99, "Not Applicable");

		return map;
	}

	@ModelAttribute("minute_options")
	public Map<Integer, String> minuteOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (int i = 0; i < 60; i++) {
			map.put(i, i + " minute(s)");
		}
		map.put(88, "Not stated");
		map.put(99, "Not Applicable");

		return map;
	}

	@ModelAttribute("startmode_options")
	public Map<Integer, String> startmodeOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("startmode_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("provider_options")
	public Map<Integer, String> providerOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("provider_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("birthloc_options")
	public Map<Integer, String> birthlocOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("birthloc_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("liqourvolume_options")
	public Map<Integer, String> liqourvolumeOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("liqourvolume_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("liqourcolor_options")
	public Map<Integer, String> liqourcolorOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("liqourcolor_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("liqourodour_options")
	public Map<Integer, String> liqourodourOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("liqourodour_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("babyoutcome_options")
	public Map<Integer, String> babyoutcomeOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("babyoutcome_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("motheroutcome_options")
	public Map<Integer, String> motheroutcomeOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("motheroutcome_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("neocod_options")
	public Map<Integer, String> neocodOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("neocod_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("lastheard_options")
	public Map<Integer, String> lastheardOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("lastheard_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	@ModelAttribute("apgar_options")
	public Map<Integer, String> apgarOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("apgar_options")) {
			map.put(elem.getMap_value(), "" + elem.getMap_value());
		}

		return map;
	}

	private String getQuestion(String code) {
		return msg.getMessage(code, null, Locale.getDefault());
	}

	private String getAnswer(String feature, Integer value) {
		return mapRepo.findById(new datamapPK(feature, value)).get().getMap_label();
	}

	private List<json_data> processListOf(case_biodata o) {
		List<json_data> list = Stream.of(
				new json_data(getQuestion("label.biodata_sex"), getAnswer("sex_options", o.getBiodata_sex()), true),
				new json_data(getQuestion("label.biodata_mage"), o.getBiodata_mage() + getQuestion("txt.years"), true),
				new json_data(getQuestion("label.biodata_medu"), getAnswer("edu_options", o.getBiodata_medu()), true))
				.collect(Collectors.toList());

		return list;
	}

	private List<json_data> processListOf(case_pregnancy o) {
		List<json_data> list = Stream.of(
				new json_data(getQuestion("label.pregnancy_gest"),
						o.getPregnancy_weeks() + getQuestion("txt.weeks") + " and " + o.getPregnancy_days()
								+ getQuestion("txt.days"),
						true),
				new json_data(getQuestion("label.pregnancy_type"), getAnswer("ptype_options", o.getPregnancy_type()),
						true))
				.collect(Collectors.toList());

		return list;
	}

	private List<json_data> processListOf(case_referral o) {
		boolean isreferral = (o.getReferral_case() == 1);
		List<json_data> list = Stream.of(
				new json_data(getQuestion("label.referral_case"), getAnswer("yesnodk_options", o.getReferral_case()),
						true),
				new json_data(getQuestion("label.referral_patient"),
						getAnswer("patient_options", o.getReferral_patient()), isreferral),
				new json_data(getQuestion("label.referral_source"), getAnswer("source_options", o.getReferral_source()),
						isreferral),
				new json_data(getQuestion("label.referral_facility"), o.getReferral_facility(), isreferral),
				new json_data(getQuestion("label.referral_datetime"),
						new SimpleDateFormat("dd-MMM-yyyy").format(o.getReferral_date()) + " at "
								+ new SimpleDateFormat("HH:mm a").format(o.getReferral_time()),
						isreferral),
				new json_data(getQuestion("label.referral_adatetime"),
						new SimpleDateFormat("dd-MMM-yyyy").format(o.getReferral_adate()) + " at "
								+ new SimpleDateFormat("HH:mm a").format(o.getReferral_atime()),
						isreferral),
				new json_data(getQuestion("label.referral_transport"),
						getAnswer("trans_options", o.getReferral_transport()), isreferral),
				new json_data(getQuestion("label.referral_file"), o.getReferral_file().toString(), isreferral),
				new json_data(getQuestion("label.referral_notes"), o.getReferral_notes(), isreferral)

		).collect(Collectors.toList());

		return list;
	}

	private List<json_data> processListOf(case_delivery o) {
		List<json_data> list = Stream.of(
				new json_data(getQuestion("label.delivery_datetime"),
						new SimpleDateFormat("dd-MMM-yyyy").format(o.getDelivery_date()) + " at "
								+ new SimpleDateFormat("HH:mm a").format(o.getDelivery_time()),
						true),
				new json_data(getQuestion("label.delivery_period"), getAnswer("period_options", o.getDelivery_period()),
						true),
				new json_data(getQuestion("label.delivery_mode"), getAnswer("mode_options", o.getDelivery_mode()),
						true))
				.collect(Collectors.toList());

		return list;
	}

	private List<json_data> processListOf(case_antenatal o) {
		boolean isattend = o.getAntenatal_attend() == 1;
		boolean hasrisks = o.getAntenatal_risks() == 1;
		boolean hasfolic = o.getAntenatal_folicacid() == 1;
		String items = "";
		for (risk_table elem : o.getRisks()) {
			items += elem.getRisk_name() + "<br/>";
		}
		List<json_data> list = Stream.of(
				new json_data(getQuestion("label.antenatal_gravida"), "" + o.getAntenatal_gravida(), true),
				new json_data(getQuestion("label.antenatal_para"), "" + o.getAntenatal_para(), true),
				new json_data(getQuestion("label.antenatal_attend"),
						getAnswer("yesnodk_options", o.getAntenatal_attend()), true),
				new json_data(getQuestion("label.antenatal_attendno"), "" + o.getAntenatal_attendno(), isattend),
				new json_data(getQuestion("label.antenatal_facility"), "" + o.getAntenatal_facility(), isattend),
				new json_data(getQuestion("label.antenatal_gestage"),
						o.getAntenatal_weeks() + getQuestion("txt.weeks") + " and " + o.getAntenatal_days()
								+ getQuestion("txt.days"),
						isattend),
				new json_data(getQuestion("label.antenatal_risks"),
						getAnswer("yesnodk_options", o.getAntenatal_risks()), true),
				new json_data(getQuestion("label.risks"), items, hasrisks),
				new json_data(getQuestion("label.antenatal_hiv"), getAnswer("hiv_options", o.getAntenatal_hiv()), true),
				new json_data(getQuestion("label.antenatal_alcohol"),
						getAnswer("yesnodk_options", o.getAntenatal_alcohol()), true),
				new json_data(getQuestion("label.antenatal_smoker"),
						getAnswer("yesnodk_options", o.getAntenatal_smoker()), true),
				new json_data(getQuestion("label.antenatal_herbal"),
						getAnswer("yesnodk_options", o.getAntenatal_herbal()), true),
				new json_data(getQuestion("label.antenatal_folicacid"),
						getAnswer("yesnodk_options", o.getAntenatal_folicacid()), true),
				new json_data(getQuestion("label.antenatal_folicacid3m"),
						getAnswer("yesnodkna_options", o.getAntenatal_folicacid3m()), hasfolic),
				new json_data(getQuestion("label.antenatal_tetanus"),
						getAnswer("yesnodk_options", o.getAntenatal_tetanus()), true),
				new json_data(getQuestion("label.antenatal_malprophy"),
						getAnswer("yesnodk_options", o.getAntenatal_malprophy()), true)

		).collect(Collectors.toList());

		return list;
	}

	private List<json_data> processListOf(case_labour o) {
		boolean hascomplications = o.getLabour_complications() == 1;

		String items = "";
		for (complication_table elem : o.getComplications()) {
			items += elem.getComplication_name() + "<br/>";
		}

		List<json_data> list = Stream
				.of(new json_data(getQuestion("label.labour_datetime"),
						new SimpleDateFormat("dd-MMM-yyyy").format(o.getLabour_seedate()) + " at "
								+ new SimpleDateFormat("HH:mm a").format(o.getLabour_seetime()),
						true),
						new json_data(getQuestion("label.labour_seeperiod"),
								getAnswer("period_options", o.getLabour_seeperiod()), true),
						new json_data(getQuestion("label.labour_startmode"),
								getAnswer("startmode_options", o.getLabour_startmode()), true),
						new json_data(getQuestion("label.labour_herbalaug"),
								getAnswer("yesnodk_options", o.getLabour_herbalaug()), true),
						new json_data(getQuestion("label.labour_partograph"),
								getAnswer("yesnodk_options", o.getLabour_partograph()), true),
						new json_data(getQuestion("label.labour_lasttime1"),
								o.getLabour_lasthour1() + getQuestion("txt.hours") + " and " + o.getLabour_lastminute1()
										+ getQuestion("txt.minutes"),
								true),
						new json_data(getQuestion("label.labour_lasttime2"),
								o.getLabour_lasthour2() + getQuestion("txt.hours") + " and " + o.getLabour_lastminute2()
										+ getQuestion("txt.minutes"),
								true),
						new json_data(getQuestion("label.labour_complications"),
								getAnswer("yesnodk_options", o.getLabour_complications()), true),
						new json_data(getQuestion("label.complications"), items, hascomplications))
				.collect(Collectors.toList());

		return list;
	}

	private List<json_data> processListOf(case_birth o) {
		boolean forcenormal = o.getBirth_insistnormal() == 1;
		boolean hasabnormal = o.getBirth_abnormalities() == 1;
		boolean hascordfaults = o.getBirth_cordfaults() == 1;
		boolean hasplacentack = o.getBirth_placentachecks() == 1;

		String items = "", items1 = "", items2 = "";
		for (abnormality_table elem : o.getAbnormalities()) {
			items += elem.getAbnormal_name() + "<br/>";
		}
		items += o.getNew_abnormalities();

		for (cordfault_table elem : o.getCordfaults()) {
			items1 += elem.getCordfault_name() + "<br/>";
		}
		items1 += o.getNew_cordfaults();

		for (placentacheck_table elem : o.getPlacentachecks()) {
			items2 += elem.getPlacentacheck_name() + "<br/>";
		}
		items2 += o.getNew_placentachecks();

		List<json_data> list = Stream
				.of(new json_data(getQuestion("label.birth_mode"), getAnswer("mode_options", o.getBirth_mode()), true),
						new json_data(getQuestion("label.birth_insistnormal"),
								getAnswer("yesnodkna_options", o.getBirth_insistnormal()), true),
						new json_data(getQuestion("label.birth_csproposetime"),
								new SimpleDateFormat("HH:mm a").format(o.getBirth_csproposetime()), forcenormal),
						new json_data(getQuestion("label.birth_provider"),
								getAnswer("provider_options", o.getBirth_provider()), true),
						new json_data(getQuestion("label.birth_facility"),
								getAnswer("birthloc_options", o.getBirth_facility()), true),
						new json_data(getQuestion("label.birth_abnormalities"),
								getAnswer("yesnodk_options", o.getBirth_abnormalities()), true),
						new json_data(getQuestion("label.abnormalities"), items, hasabnormal),
						new json_data(getQuestion("label.birth_cordfaults"),
								getAnswer("yesnodk_options", o.getBirth_cordfaults()), true),
						new json_data(getQuestion("label.cordfaults"), items1, hascordfaults),
						new json_data(getQuestion("label.birth_placentachecks"),
								getAnswer("yesnodk_options", o.getBirth_placentachecks()), true),
						new json_data(getQuestion("label.placentachecks"), items2, hasplacentack),
						new json_data(getQuestion("label.birth_liqourvolume"),
								getAnswer("liqourvolume_options", o.getBirth_liqourvolume()), true),
						new json_data(getQuestion("label.birth_liqourcolor"),
								getAnswer("liqourcolor_options", o.getBirth_liqourcolor()), true),
						new json_data(getQuestion("label.birth_liqourodour"),
								getAnswer("liqourodour_options", o.getBirth_liqourodour()), true),
						new json_data(getQuestion("label.birth_babyoutcome"),
								getAnswer("babyoutcome_options", o.getBirth_babyoutcome()), true),
						new json_data(getQuestion("label.birth_motheroutcome"),
								getAnswer("motheroutcome_options", o.getBirth_motheroutcome()), true))
				.collect(Collectors.toList());

		return list;
	}

	private List<json_data> processListOf(case_fetalheart o) {
		List<json_data> list = Stream
				.of(new json_data(getQuestion("label.fetalheart_refered"),
						getAnswer("yesnodk_options", o.getFetalheart_refered()), true),
						new json_data(getQuestion("label.fetalheart_arrival"),
								getAnswer("yesnodk_options", o.getFetalheart_arrival()), true),
						new json_data(getQuestion("label.fetalheart_lastheard"),
								getAnswer("lastheard_options", o.getFetalheart_lastheard()), true))
				.collect(Collectors.toList());

		return list;
	}

	private List<json_data> processListOf(case_babydeath o) {
		boolean isresus = o.getBaby_resuscitation() == 1;
		boolean isadmit = o.getBaby_admitted() == 1;

		String items = "", items1 = "";
		for (resuscitation_table elem : o.getResuscitations()) {
			items += elem.getResuscitation_name() + "<br/>";
		}
		items += o.getNew_resuscitation();

		for (diagnoses_table elem : o.getDiagnoses()) {
			items += elem.getDiagnosis_name() + "<br/>";
		}
		items += o.getNew_diagnoses();

		List<json_data> list = Stream
				.of(new json_data(getQuestion("label.baby_cry"), getAnswer("yesnodk_options", o.getBaby_cry()), true),
						new json_data(getQuestion("label.baby_resuscitation"),
								getAnswer("yesnodk_options", o.getBaby_resuscitation()), true),
						new json_data(getQuestion("label.baby_resuscitation"), items, isresus),
						new json_data(getQuestion("label.baby_apgar1"), "" + o.getBaby_apgar1(), true),
						new json_data(getQuestion("label.baby_apgar5"), "" + o.getBaby_apgar5(), true),
						new json_data(getQuestion("label.baby_admitted"),
								getAnswer("yesnodk_options", o.getBaby_admitted()), true),
						new json_data(getQuestion("label.diagnoses"), items1, isadmit),
						new json_data(getQuestion("label.baby_ddatetime"),
								new SimpleDateFormat("dd-MMM-yyyy").format(o.getBaby_ddate()) + " at "
										+ new SimpleDateFormat("HH:mm a").format(o.getBaby_dtime()),
								isadmit)

				).collect(Collectors.toList());

		return list;
	}

	private List<json_data> processListOf(case_notes o) {
		List<json_data> list = Stream
				.of(new json_data(getQuestion("label.notes_file"), o.getNotes_file().toString(), true),
						new json_data(getQuestion("label.notes_text"), o.getNotes_text(), true))
				.collect(Collectors.toList());

		return list;
	}

}// end class