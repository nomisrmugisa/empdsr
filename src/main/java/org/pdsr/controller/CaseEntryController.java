package org.pdsr.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.pdsr.CONSTANTS;
import org.pdsr.json.json_data;
import org.pdsr.model.abnormality_table;
import org.pdsr.model.case_antenatal;
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
import org.pdsr.model.facility_table;
import org.pdsr.model.placentacheck_table;
import org.pdsr.model.risk_table;
import org.pdsr.model.sync_table;
import org.pdsr.repo.AbnormalityTableRepository;
import org.pdsr.repo.CaseAntenatalRepository;
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
import org.pdsr.repo.FacilityTableRepository;
import org.pdsr.repo.PlacentacheckTableRepository;
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
				&& selected.getLabour() != null && selected.getBirth() != null && selected.getFetalheart() != null
				&& selected.getNotes() != null;

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
			if (selected.getFetalheart() == null) {
				case_fetalheart data = new case_fetalheart();
				data.setFetalheart_uuid(UUID.randomUUID().toString());
				data.setCase_uuid(selected);
				selected.setFetalheart(data);
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
			java.util.Date time = selected.getReferral().getReferral_time();
			selected.getReferral().setReferral_hour(time.getHours());
			selected.getReferral().setReferral_minute(time.getMinutes());

			java.util.Date atime = selected.getReferral().getReferral_atime();
			selected.getReferral().setReferral_ahour(atime.getHours());
			selected.getReferral().setReferral_aminute(atime.getMinutes());

			refRepo.save(selected.getReferral());
			break;
		}
		case 4: {
			java.util.Date time = selected.getDelivery().getDelivery_time();
			selected.getDelivery().setDelivery_hour(time.getHours());
			selected.getDelivery().setDelivery_minute(time.getMinutes());

			delRepo.save(selected.getDelivery());
			break;
		}
		case 5: {

			String data = selected.getAntenatal().getNew_risks();
			List<risk_table> items = selected.getAntenatal().getRisks();
			if (data != null && !data.isBlank()) {
				String[] tokens = data.split("\n\r");

				List<risk_table> table = new ArrayList<>();
				for (String elem : tokens) {
					risk_table item = new risk_table();
					item.setRisk_name(elem);
					item.setRisk_desc(elem);
					table.add(item);
					items.add(item);
				}
				riskRepo.saveAll(table);

			}
			selected.getAntenatal().setRisks(items);
			antRepo.save(selected.getAntenatal());
			break;
		}
		case 6: {
			java.util.Date seetime = selected.getLabour().getLabour_seetime();
			selected.getLabour().setLabour_seehour(seetime.getHours());
			selected.getLabour().setLabour_seeminute(seetime.getMinutes());

			java.util.Date lasttime1 = selected.getLabour().getLabour_lasttime1();
			selected.getLabour().setLabour_lasthour1(lasttime1.getHours());
			selected.getLabour().setLabour_lastminute1(lasttime1.getMinutes());

			java.util.Date lasttime2 = selected.getLabour().getLabour_lasttime2();
			selected.getLabour().setLabour_lasthour2(lasttime2.getHours());
			selected.getLabour().setLabour_lastminute2(lasttime2.getMinutes());

			String data = selected.getLabour().getNew_complications();
			List<complication_table> items = selected.getLabour().getComplications();
			if (data != null && !data.isBlank()) {
				String[] tokens = data.split("\n\r");

				List<complication_table> table = new ArrayList<>();
				for (String elem : tokens) {
					complication_table item = new complication_table();
					item.setComplication_name(elem);
					item.setComplication_desc(elem);
					table.add(item);
					items.add(item);
				}
				compRepo.saveAll(table);

			}
			selected.getLabour().setComplications(items);

			labRepo.save(selected.getLabour());
			break;
		}
		case 7: {
			java.util.Date cstime = selected.getBirth().getBirth_csproposetime();
			selected.getBirth().setBirth_csproposehour(cstime.getHours());
			selected.getBirth().setBirth_csproposeminute(cstime.getMinutes());

			String data1 = selected.getBirth().getNew_abnormalities();
			List<abnormality_table> items1 = selected.getBirth().getAbnormalities();
			if (data1 != null && !data1.isBlank()) {
				String[] tokens = data1.split("\n\r");

				List<abnormality_table> table = new ArrayList<>();
				for (String elem : tokens) {
					abnormality_table item = new abnormality_table();
					item.setAbnormal_name(elem);
					item.setAbnormal_desc(elem);
					table.add(item);
					items1.add(item);
				}
				abnoRepo.saveAll(table);
			}

			String data2 = selected.getBirth().getNew_cordfaults();
			List<cordfault_table> items2 = selected.getBirth().getCordfaults();
			if (data2 != null && !data2.isBlank()) {
				String[] tokens = data2.split("\n\r");

				List<cordfault_table> table = new ArrayList<>();
				for (String elem : tokens) {
					cordfault_table item = new cordfault_table();
					item.setCordfault_name(elem);
					item.setCordfault_desc(elem);
					table.add(item);
					items2.add(item);
				}
				cordRepo.saveAll(table);
			}

			String data3 = selected.getBirth().getNew_placentachecks();
			List<placentacheck_table> items3 = selected.getBirth().getPlacentachecks();
			if (data3 != null && !data3.isBlank()) {
				String[] tokens = data3.split("\n\r");

				List<placentacheck_table> table = new ArrayList<>();
				for (String elem : tokens) {
					placentacheck_table item = new placentacheck_table();
					item.setPlacentacheck_name(elem);
					item.setPlacentacheck_desc(elem);
					table.add(item);
					items3.add(item);
				}
				placRepo.saveAll(table);
			}

			selected.getBirth().setAbnormalities(items1);
			selected.getBirth().setCordfaults(items2);
			selected.getBirth().setPlacentachecks(items3);

			birRepo.save(selected.getBirth());
			break;
		}
		case 8: {
			fetRepo.save(selected.getFetalheart());
			break;
		}
		case 9: {
			notRepo.save(selected.getNotes());
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
		for (datamap elem : mapRepo.findByMap_feature("edu_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

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

	@ModelAttribute("lastheard_options")
	public Map<Integer, String> lastheardOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("lastheard_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
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
		List<json_data> list = Stream
				.of(new json_data(getQuestion("label.biodata_sex"), getAnswer("sex_options", o.getBiodata_sex())),
						new json_data(getQuestion("label.biodata_mage"), o.getBiodata_mage() + getQuestion("txt.years")),
						new json_data(getQuestion("label.biodata_medu"), getAnswer("edu_options", o.getBiodata_medu())))
				.collect(Collectors.toList());

		return list;
	}

	private List<json_data> processListOf(case_pregnancy o) {
		List<json_data> list = Stream
				.of(new json_data(getQuestion("label.pregnancy_gest")
						, o.getPregnancy_weeks() + getQuestion("txt.weeks") + "/" + o.getPregnancy_days() + getQuestion("txt.days")),
						new json_data(getQuestion("label.pregnancy_type"), getAnswer("ptype_options", o.getPregnancy_type())))
				.collect(Collectors.toList());

		return list;
	}

}// end class