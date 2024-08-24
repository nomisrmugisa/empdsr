package org.pdsr.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.pdsr.Antenatal;
import org.pdsr.Biodata;
import org.pdsr.Birth;
import org.pdsr.CONSTANTS;
import org.pdsr.Caseid;
import org.pdsr.Delivery;
import org.pdsr.EmailService;
import org.pdsr.FetalHeart;
import org.pdsr.InternetAvailabilityChecker;
import org.pdsr.Labour;
import org.pdsr.Pregnancy;
import org.pdsr.Referral;
import org.pdsr.json.json_data;
import org.pdsr.master.model.abnormality_table;
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
import org.pdsr.master.model.complication_table;
import org.pdsr.master.model.cordfault_table;
import org.pdsr.master.model.datamap;
import org.pdsr.master.model.datamapPK;
import org.pdsr.master.model.diagnoses_table;
import org.pdsr.master.model.facility_table;
import org.pdsr.master.model.icd_diagnoses;
import org.pdsr.master.model.placentacheck_table;
import org.pdsr.master.model.resuscitation_table;
import org.pdsr.master.model.risk_table;
import org.pdsr.master.model.sync_table;
import org.pdsr.master.repo.AbnormalityTableRepository;
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
import org.pdsr.master.repo.ComplicationTableRepository;
import org.pdsr.master.repo.CordfaultTableRepository;
import org.pdsr.master.repo.DatamapRepository;
import org.pdsr.master.repo.DiagnosesTableRepository;
import org.pdsr.master.repo.FacilityTableRepository;
import org.pdsr.master.repo.IcdDiagnosesRepository;
import org.pdsr.master.repo.PlacentacheckTableRepository;
import org.pdsr.master.repo.ResuscitationTableRepository;
import org.pdsr.master.repo.RiskTableRepository;
import org.pdsr.master.repo.SyncTableRepository;
import org.pdsr.master.repo.UserTableRepository;
import org.pdsr.pojos.EntityMappings;
import org.pdsr.pojos.SheetSections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/registry")
public class CaseEntryController {

	@Autowired
	private SyncTableRepository syncRepo;

	@Autowired
	private UserTableRepository userRepo;

	@Autowired
	private ComplicationTableRepository compRepo;

	@Autowired
	private ResuscitationTableRepository resusRepo;

	@Autowired
	private DiagnosesTableRepository diagRepo;

	@Autowired
	private IcdDiagnosesRepository icddRepo;

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

	@Autowired
	private EmailService emailService;

	@GetMapping("")
	public String list(Principal principal, Model model) {

		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table synctable = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		model.addAttribute("myf", synctable.getSync_name());

		List<case_identifiers> entered_cases = caseRepo.findByDraftCases();// find cases not yet submitted
		entered_cases.addAll(caseRepo.findByPendingCase_status(1));// find cases just entered and pending review

		model.addAttribute("items", entered_cases);
		model.addAttribute("back", "back");

		return "registry/case-retrieve";
	}

	@GetMapping("/file")
	public ResponseEntity<Resource> downloadFile() {
		Resource resource = new ClassPathResource("static/PDSR_DEATH_CASE_ENTRY.xlsx");

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pdsr-sample.xlsx");

		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}

	@PostMapping("")
	public String uploadData(MultipartFile file) {
		try {
			// Read the input stream into a byte array
			byte[] bytes = file.getBytes();
			InputStream inputStream = new ByteArrayInputStream(bytes);

			List<case_identifiers> caseids = ExcelHelper.returnCaseIds(inputStream);
			caseRepo.saveAll(caseids);

			inputStream.reset();
			List<case_biodata> biodata = ExcelHelper.returnBiodata(inputStream);

			// Link Caseid and Biodata
			for (Biodata bio : biodata) {
				Caseid caseid = caseidrepository.findByMothersIdNo(bio.getMothersIdNo());
				if (caseid != null) {
					bio.setCaseid(caseid);
					caseid.setBiodata(bio);
				}
			}

			biodataRepository.saveAll(biodata);

			// Reset the input stream for reading referrals
			inputStream.reset();
			List<Referral> referrals = ExcelHelper.returnReferral(inputStream);

			// Link Caseid and Referral
			for (Referral referral : referrals) {
				Caseid caseid = caseidrepository.findByMothersIdNo(referral.getMothersIdNo());
				if (caseid != null) {
					referral.setCaseid(caseid);
					caseid.setReferral(referral);
				}
			}

			referralRepository.saveAll(referrals);

			// reset the input stream for reading pregnancies
			inputStream.reset();
			List<Pregnancy> pregnancies = ExcelHelper.returnPregnancy(inputStream);

			// Link Caseid and Pregnancy
			for (Pregnancy pregnancy : pregnancies) {
				Caseid caseid = caseidrepository.findByMothersIdNo(pregnancy.getMothersIdNo());
				if (caseid != null) {
					pregnancy.setCaseid(caseid);
					caseid.setPregnancy(pregnancy);
				}
			}

			pregnancyRepository.saveAll(pregnancies);

			// Reset the input stream for reading antenatals
			inputStream.reset();
			List<Antenatal> antenatals = ExcelHelper.returnAntenatals(inputStream);

			// Link Caseid and Antenatal
			for (Antenatal antenatal : antenatals) {
				Caseid caseid = caseidrepository.findByMothersIdNo(antenatal.getMothersIdNo());
				if (caseid != null) {
					antenatal.setCaseid(caseid);
					caseid.setAntenatal(antenatal);
				}
			}

			antenatalRepository.saveAll(antenatals);

			// Reset the input stream for reading labours
			inputStream.reset();
			List<Labour> labours = ExcelHelper.returnLabour(inputStream);

			// Link Caseid and Labour
			for (Labour labour : labours) {
				Caseid caseid = caseidrepository.findByMothersIdNo(labour.getMothersIdNo());
				if (caseid != null) {
					labour.setCaseid(caseid);
					caseid.setLabour(labour);
				}
			}
			labourRepository.saveAll(labours);

			// Reset the input stream for reading deliveries
			inputStream.reset();
			List<Delivery> deliveries = ExcelHelper.returnDelivery(inputStream);

			// Link Caseid and Delivery
			for (Delivery delivery : deliveries) {
				Caseid caseid = caseidrepository.findByMothersIdNo(delivery.getMothersIdNo());
				if (caseid != null) {
					delivery.setCaseid(caseid);
					caseid.setDelivery(delivery);
				}
			}
			deliveryRepository.saveAll(deliveries);

			// Reset the input stream for reading births
			inputStream.reset();
			List<Birth> births = ExcelHelper.returnBirth(inputStream);

			// Link Caseid and Birth
			for (Birth birth : births) {
				Caseid caseid = caseidrepository.findByMothersIdNo(birth.getMothersIdNo());
				if (caseid != null) {
					birth.setCaseid(caseid);
					caseid.setBirth(birth);
				}
			}
			birthRepository.saveAll(births);

			// Reset the input stream for reading fetal hearts
			inputStream.reset();
			List<FetalHeart> fetalHearts = ExcelHelper.returnFetalHearts(inputStream);

			// Link Caseid and FetalHeart
			for (FetalHeart fetalHeart : fetalHearts) {
				Caseid caseid = caseidrepository.findByMothersIdNo(fetalHeart.getMothersIdNo());
				if (caseid != null) {
					fetalHeart.setCaseid(caseid);
					caseid.setFetalHeart(fetalHeart);
				}
			}
			fetalHeartRepository.saveAll(fetalHearts);
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
	}

	@GetMapping("/add")
	public String add(Principal principal, Model model) {

		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table synctable = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		model.addAttribute("myf", synctable.getSync_name());

		case_identifiers selected = new case_identifiers();
		selected.setCase_date(LocalDate.now());

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
		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table synctable = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		model.addAttribute("myf", synctable.getSync_name());

		selected.setCase_uuid(UUID.randomUUID().toString());
		selected.setCase_id("T" + selected.getCase_death() + "C" + (new java.util.Date().getTime()));
		selected.setCase_status(0);
		selected.setData_sent(0);
		selected.setCase_sync(syncRepo.findById(CONSTANTS.FACILITY_ID).get().getSync_code());

		caseRepo.save(selected);

		return "redirect:/registry/edit/" + selected.getCase_uuid() + "?page=1&success=yes";
	}

	@GetMapping("/edit/{id}")
	public String edit(Principal principal, final Model model, @PathVariable("id") String case_uuid,
			@RequestParam(name = "page", required = true) Integer page,
			@RequestParam(name = "success", required = false) String success) {

		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table synctable = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		model.addAttribute("myf", synctable.getSync_name());

		case_identifiers selected = caseRepo.findById(case_uuid).get();

		boolean completed = selected.getBiodata() != null && selected.getPregnancy() != null
				&& selected.getReferral() != null && selected.getDelivery() != null && selected.getAntenatal() != null
				&& selected.getLabour() != null && selected.getBirth() != null
				&& (selected.getFetalheart() != null || selected.getBabydeath() != null) && selected.getNotes() != null;

		if (completed) {
			final boolean bio = selected.getBiodata().getData_complete() == 1;
			final boolean pre = selected.getPregnancy().getData_complete() == 1;
			final boolean ref = selected.getReferral().getData_complete() == 1;
			final boolean del = selected.getDelivery().getData_complete() == 1;
			;
			final boolean ant = selected.getAntenatal().getData_complete() == 1;
			final boolean lab = selected.getLabour().getData_complete() == 1;
			final boolean bir = selected.getBirth().getData_complete() == 1;
			final boolean fet = selected.getFetalheart() != null && selected.getFetalheart().getData_complete() == 1;
			final boolean bab = selected.getBabydeath() != null && selected.getBabydeath().getData_complete() == 1;

			completed = bio && pre && ref && del && ant && lab && bir && (fet || bab);
		}
		if (completed) {
			model.addAttribute("completed", completed);
		}

		switch (page) {
		case 1: {
			model.addAttribute("bioactive", "active");
			if (selected.getBiodata() == null) {
				case_biodata data = new case_biodata();
				data.setBiodata_uuid(UUID.randomUUID().toString());
				data.setCase_uuid(selected);
				selected.setBiodata(data);
			}
			break;
		}

		case 2: {
			model.addAttribute("refactive", "active");
			if (selected.getReferral() == null) {
				case_referral data = new case_referral();
				data.setReferral_uuid(UUID.randomUUID().toString());
				data.setCase_uuid(selected);
				selected.setReferral(data);
			}
			final String encodedImage = convertBinImageToString(selected.getReferral().getReferral_file());
			selected.getReferral().setBase64image(encodedImage);
			break;
		}

		case 3: {
			model.addAttribute("preactive", "active");
			if (selected.getPregnancy() == null) {
				case_pregnancy data = new case_pregnancy();
				data.setPregnancy_uuid(UUID.randomUUID().toString());
				data.setCase_uuid(selected);
				selected.setPregnancy(data);
			}
			break;
		}

		case 4: {
			model.addAttribute("antactive", "active");
			if (selected.getAntenatal() == null) {
				case_antenatal data = new case_antenatal();
				data.setAntenatal_uuid(UUID.randomUUID().toString());
				data.setCase_uuid(selected);
				selected.setAntenatal(data);
			}
			model.addAttribute("risk_options", riskRepo.findAll());
			break;
		}

		case 5: {
			model.addAttribute("labactive", "active");
			if (selected.getLabour() == null) {
				case_labour data = new case_labour();
				data.setLabour_uuid(UUID.randomUUID().toString());
				data.setCase_uuid(selected);
				selected.setLabour(data);
			}
			model.addAttribute("complication_options", compRepo.findAll());
			break;
		}

		case 6: {
			model.addAttribute("delactive", "active");
			if (selected.getDelivery() == null) {
				case_delivery data = new case_delivery();
				data.setDelivery_uuid(UUID.randomUUID().toString());
				data.setCase_uuid(selected);
				selected.setDelivery(data);
			}
			break;
		}

		case 7: {
			model.addAttribute("biractive", "active");
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
				model.addAttribute("fetactive", "active");
				if (selected.getFetalheart() == null) {
					case_fetalheart data = new case_fetalheart();
					data.setFetalheart_uuid(UUID.randomUUID().toString());
					data.setCase_uuid(selected);
					selected.setFetalheart(data);

				}
			} else if (selected.getCase_death() == 2) {

				model.addAttribute("babactive", "active");
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
			model.addAttribute("notactive", "active");
			if (selected.getNotes() == null) {
				case_notes data = new case_notes();
				data.setNotes_uuid(UUID.randomUUID().toString());
				data.setCase_uuid(selected);
				selected.setNotes(data);
			}

			final String encodedImage = convertBinImageToString(selected.getNotes().getNotes_file());
			selected.getNotes().setBase64image(encodedImage);

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
			@RequestParam(name = "page", required = true) Integer page,
			@RequestParam(name = "go", required = false) Integer go, BindingResult results) {

		ObjectMapper objectMapper = new ObjectMapper();
		// objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table synctable = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		model.addAttribute("myf", synctable.getSync_name());

		case_identifiers existing = caseRepo.findById(selected.getCase_uuid()).get();
		selected.setData_sent(0);// reset the data sending indicator when any record is edited
		selected.setCase_status(0);// reset the submission status to not submitted (incomplete)

		switch (page) {
		case 1: {

			try {
				case_biodata o = selected.getBiodata();
				if (o.getBiodata_mage() == null || o.getBiodata_medu() == null || o.getBiodata_sex() == null) {
					o.setData_complete(0);
				} else {
					o.setData_complete(1);
					final String arrayToJson = objectMapper.writeValueAsString(processListOf(selected.getBiodata()));
					selected.getBiodata().setBiodata_json(arrayToJson);

				}
				bioRepo.save(o);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			break;
		}
		case 2: {
			try {

				java.util.Date time = selected.getReferral().getReferral_time();
				if (time != null) {
					selected.getReferral().setReferral_hour(time.getHours());
					selected.getReferral().setReferral_minute(time.getMinutes());
				}

				java.util.Date atime = selected.getReferral().getReferral_atime();
				if (atime != null) {
					selected.getReferral().setReferral_ahour(atime.getHours());
					selected.getReferral().setReferral_aminute(atime.getMinutes());
				}

				MultipartFile file = selected.getReferral().getFile();
				selected.getReferral().setBase64image(null);

				try {
					if (file != null && file.getBytes() != null && file.getBytes().length > 0) {
						selected.getReferral().setReferral_file(file.getBytes());
						selected.getReferral().setReferral_filetype(file.getContentType());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (selected.getReferral().getReferral_case() != null
						&& selected.getReferral().getReferral_case() == 1) {

					validateTheTimesOnReferralPage(model, results, selected, existing);

					if (results.hasErrors()) {
						model.addAttribute("selected", selected);
						model.addAttribute("page", page);
						return "registry/case-update";
					}

				}

				case_referral o = selected.getReferral();

				boolean referral_adatetime_expected = (o.getReferral_adatetime_notstated() == null
						|| o.getReferral_adatetime_notstated() == 0);

				boolean referral_adatetime_any_specified = o.getReferral_ahour() != null
						|| o.getReferral_adate() != null || o.getReferral_aminute() != null
						|| o.getReferral_atime() != null;

				boolean referral_adatetime_any_missing = o.getReferral_ahour() == null || o.getReferral_adate() == null
						|| o.getReferral_aminute() == null || o.getReferral_atime() == null;

				boolean referral_datetime_expected = (o.getReferral_datetime_notstated() == null
						|| o.getReferral_datetime_notstated() == 0);

				boolean referral_datetime_any_specified = o.getReferral_hour() != null || o.getReferral_date() != null
						|| o.getReferral_minute() != null || o.getReferral_time() != null;

				boolean referral_datetime_any_missing = o.getReferral_hour() == null || o.getReferral_date() == null
						|| o.getReferral_minute() == null || o.getReferral_time() == null;

				if ((referral_adatetime_expected && referral_adatetime_any_missing)
						|| (!referral_adatetime_expected && referral_adatetime_any_specified)
						|| o.getReferral_case() == null || (referral_datetime_expected && referral_datetime_any_missing)
						|| (!referral_datetime_expected && referral_datetime_any_specified)
						|| o.getReferral_facility() == null || o.getReferral_facility().trim() == ""
						|| o.getReferral_patient() == null || o.getReferral_source() == null
						|| o.getReferral_transport() == null) {

					o.setData_complete(0);
				} else {
					o.setData_complete(1);
					final String arrayToJson = objectMapper.writeValueAsString(processListOf(selected.getReferral()));
					selected.getReferral().setReferral_json(arrayToJson);
				}

				refRepo.save(selected.getReferral());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			break;
		}
		case 3: {
			try {

				case_pregnancy o = selected.getPregnancy();
				if (o.getPregnancy_days() == null || o.getPregnancy_type() == null || o.getPregnancy_weeks() == null) {
					o.setData_complete(0);
				} else {
					o.setData_complete(1);
					final String arrayToJson = objectMapper.writeValueAsString(processListOf(selected.getPregnancy()));
					selected.getPregnancy().setPregnancy_json(arrayToJson);
				}
				preRepo.save(o);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			break;
		}
		case 4: {
			try {

				case_antenatal o = selected.getAntenatal();
				if (o.getAntenatal_alcohol() == null || o.getAntenatal_attend() == null
						|| o.getAntenatal_attendno() == null || o.getAntenatal_days() == null
						|| o.getAntenatal_facility() == null || o.getAntenatal_facility().trim() == ""
						|| o.getAntenatal_folicacid() == null || o.getAntenatal_folicacid3m() == null
						|| o.getAntenatal_gravida() == null || o.getAntenatal_herbal() == null
						|| o.getAntenatal_hiv() == null || o.getAntenatal_malprophy() == null
						|| o.getAntenatal_para() == null || o.getAntenatal_risks() == null
						|| o.getAntenatal_smoker() == null || o.getAntenatal_tetanus() == null
						|| o.getAntenatal_weeks() == null) {
					o.setData_complete(0);
				} else {
					o.setData_complete(1);
					final String arrayToJson = objectMapper.writeValueAsString(processListOf(selected.getAntenatal()));
					selected.getAntenatal().setAntenatal_json(arrayToJson);
				}

				antRepo.save(selected.getAntenatal());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			break;
		}
		case 5: {
			try {

				java.util.Date seetime = selected.getLabour().getLabour_seetime();
				if (seetime != null) {
					selected.getLabour().setLabour_seehour(seetime.getHours());
					selected.getLabour().setLabour_seeminute(seetime.getMinutes());
				}

				final Integer period = selected.getLabour().getLabour_seeperiod();
				final Integer hour = selected.getLabour().getLabour_seehour();

				if (period != null && hour != null) {
					// boolean dawn = (hour > 0 && hour < 6);
					boolean morning = (hour >= 8 && hour < 14);
					// boolean midday = (hour == 12);
					boolean afternoon = (hour >= 14 && hour < 20);
					boolean night = (hour >= 20 || hour < 8);
					// boolean midnight = (hour > 21 || hour < 1);

					if (period == 1 && !morning) {
						results.rejectValue("labour.labour_seeperiod", "error.morning");
					} else if (period == 3 && !afternoon) {
						results.rejectValue("labour.labour_seeperiod", "error.afternoon");
					} else if (period == 6 && !night) {
						results.rejectValue("labour.labour_seeperiod", "error.night");
					}

					if (results.hasErrors()) {
						model.addAttribute("selected", selected);
						model.addAttribute("page", page);
						return "registry/case-update";
					}
				}

				if (existing.getReferral() != null && existing.getReferral().getReferral_case() == 1) {

					validateTheTimesOnLabourPage(model, results, selected, existing);

					if (results.hasErrors()) {
						model.addAttribute("selected", selected);
						model.addAttribute("page", page);
						return "registry/case-update";
					}

				}

				case_labour o = selected.getLabour();

				boolean labour_seedatetime_expected = (o.getLabour_seedatetime_notstated() == null
						|| o.getLabour_seedatetime_notstated() == 0);

				boolean labour_seedatetime_any_specified = o.getLabour_seehour() != null
						|| o.getLabour_seedate() != null || o.getLabour_seeminute() != null
						|| o.getLabour_seetime() != null;

				boolean labour_seedatetime_any_missing = o.getLabour_seehour() == null || o.getLabour_seedate() == null
						|| o.getLabour_seeminute() == null || o.getLabour_seetime() == null;

				if (o.getLabour_complications() == null || o.getLabour_herbalaug() == null
						|| o.getLabour_lasthour1() == null || o.getLabour_lasthour2() == null
						|| o.getLabour_lastminute1() == null || o.getLabour_lastminute2() == null
						|| o.getLabour_partograph() == null
						|| (labour_seedatetime_expected && labour_seedatetime_any_missing)
						|| (!labour_seedatetime_expected && labour_seedatetime_any_specified)
						|| o.getLabour_seeperiod() == null || o.getLabour_startmode() == null) {
					o.setData_complete(0);
				} else {
					final String arrayToJson = objectMapper.writeValueAsString(processListOf(selected.getLabour()));
					selected.getLabour().setLabour_json(arrayToJson);
					o.setData_complete(1);
				}

				labRepo.save(selected.getLabour());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			break;
		}
		case 6: {
			try {

				java.util.Date time = selected.getDelivery().getDelivery_time();
				if (time != null) {
					selected.getDelivery().setDelivery_hour(time.getHours());
					selected.getDelivery().setDelivery_minute(time.getMinutes());
				}
				final Integer period = selected.getDelivery().getDelivery_period();
				final Integer hour = selected.getDelivery().getDelivery_hour();

				if (period != null && hour != null) {
					// boolean dawn = (hour > 0 && hour < 6);
					boolean morning = (hour >= 8 && hour < 14);
					// boolean midday = (hour == 12);
					boolean afternoon = (hour >= 14 && hour < 20);
					boolean night = (hour >= 20 || hour < 8);
					// boolean midnight = (hour > 21 || hour < 1);

					if (period == 1 && !morning) {
						results.rejectValue("delivery.delivery_period", "error.morning");
//					} else if (period == 2 && !midday) {
//						results.rejectValue("delivery.delivery_period", "error.midday");
					} else if (period == 3 && !afternoon) {
						results.rejectValue("delivery.delivery_period", "error.afternoon");
//					} else if (period == 4 && !evening) {
//						results.rejectValue("delivery.delivery_period", "error.evening");
//					} else if (period == 5 && !midnight) {
//						results.rejectValue("delivery.delivery_period", "error.midnight");
					} else if (period == 6 && !night) {
						results.rejectValue("delivery.delivery_period", "error.night");
					}

					if (results.hasErrors()) {
						model.addAttribute("selected", selected);
						model.addAttribute("page", page);
						return "registry/case-update";
					}
				}
				if (existing.getBirth() != null) {

					validateTheTimesOnDeliveryPage(model, results, selected, existing);

					if (results.hasErrors()) {
						model.addAttribute("selected", selected);
						model.addAttribute("page", page);
						return "registry/case-update";
					}

				}

				case_delivery o = selected.getDelivery();
				boolean delivery_datetime_expected = (o.getDelivery_datetime_notstated() == null
						|| o.getDelivery_datetime_notstated() == 0);

				boolean delivery_datetime_any_specified = o.getDelivery_hour() != null || o.getDelivery_date() != null
						|| o.getDelivery_minute() != null || o.getDelivery_time() != null;

				boolean delivery_datetime_any_missing = o.getDelivery_hour() == null || o.getDelivery_date() == null
						|| o.getDelivery_minute() == null || o.getDelivery_time() == null;

				if ((delivery_datetime_expected && delivery_datetime_any_missing)
						|| (!delivery_datetime_expected && delivery_datetime_any_specified)
						|| o.getDelivery_period() == null || o.getDelivery_weight() == null) {
					o.setData_complete(0);
				} else {

					o.setData_complete(1);
					final String arrayToJson = objectMapper.writeValueAsString(processListOf(selected.getDelivery()));
					selected.getDelivery().setDelivery_json(arrayToJson);
				}

				delRepo.save(selected.getDelivery());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			break;
		}
		case 7: {
			try {
				java.util.Date cstime = selected.getBirth().getBirth_csproposetime();

				if (cstime != null) {
					selected.getBirth().setBirth_csproposehour(cstime.getHours());
					selected.getBirth().setBirth_csproposeminute(cstime.getMinutes());
				}

				if (existing.getDelivery() != null) {

					validateTheTimesOnBirthPage(model, results, selected, existing);

					if (results.hasErrors()) {
						model.addAttribute("selected", selected);
						model.addAttribute("page", page);
						return "registry/case-update";
					}

				}

				case_birth o = selected.getBirth();

				boolean insistnormal_notselected = (o.getBirth_mode() != null
						&& (o.getBirth_mode() == 0 || o.getBirth_mode() == 1)) && o.getBirth_insistnormal() == null;

				boolean insistnormal_selected_but_any_cs_datetime_missing = (o.getBirth_mode() != null
						&& (o.getBirth_mode() == 0 || o.getBirth_mode() == 1)) && o.getBirth_insistnormal() != null
						&& o.getBirth_insistnormal() == 1
						&& (o.getBirth_csproposehour() == null || o.getBirth_csproposedate() == null
								|| o.getBirth_csproposeminute() == null || o.getBirth_csproposetime() == null);

				if (o.getBirth_abnormalities() == null || o.getBirth_babyoutcome() == null
						|| o.getBirth_cordfaults() == null || o.getBirth_mode() == null || insistnormal_notselected
						|| insistnormal_selected_but_any_cs_datetime_missing || o.getBirth_facility() == null
						|| o.getBirth_insistnormal() == null || o.getBirth_liqourcolor() == null
						|| o.getBirth_liqourodour() == null || o.getBirth_liqourvolume() == null
						|| o.getBirth_motheroutcome() == null || o.getBirth_placentachecks() == null
						|| o.getBirth_provider() == null) {

					System.out.println();
					o.setData_complete(0);
				} else {
					o.setData_complete(1);
					final String arrayToJson = objectMapper.writeValueAsString(processListOf(selected.getBirth()));
					selected.getBirth().setBirth_json(arrayToJson);
				}

				birRepo.save(selected.getBirth());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			break;
		}
		case 8: {
			if (selected.getCase_death() == 1) {
				try {

					case_fetalheart o = selected.getFetalheart();
					if (o.getFetalheart_arrival() == null || o.getFetalheart_lastheard() == null
							|| o.getFetalheart_refered() == null) {
						o.setData_complete(0);
					} else {
						o.setData_complete(1);
						final String arrayToJson = objectMapper
								.writeValueAsString(processListOf(selected.getFetalheart()));
						selected.getFetalheart().setFetalheart_json(arrayToJson);
					}

					fetRepo.save(selected.getFetalheart());
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}

			} else if (selected.getCase_death() == 2) {
				try {
					java.util.Date time = selected.getBabydeath().getBaby_dtime();
					if (time != null) {
						selected.getBabydeath().setBaby_dhour(time.getHours());
						selected.getBabydeath().setBaby_dminute(time.getMinutes());
					}

					if (existing.getDelivery() != null) {

						validateTheTimesOnBabydeathPage(model, results, selected, existing);

						if (results.hasErrors()) {
							model.addAttribute("selected", selected);
							model.addAttribute("page", page);
							return "registry/case-update";
						}

					}

					case_babydeath o = selected.getBabydeath();
					boolean baby_ddatetime_expected = (o.getBaby_ddatetime_notstated() == null
							|| o.getBaby_ddatetime_notstated() == 0);

					boolean baby_ddatetime_any_specified = o.getBaby_dhour() != null || o.getBaby_ddate() != null
							|| o.getBaby_dminute() != null || o.getBaby_dtime() != null;

					boolean baby_ddatetime_any_missing = o.getBaby_dhour() == null || o.getBaby_ddate() == null
							|| o.getBaby_dminute() == null || o.getBaby_dtime() == null;

					if (o.getBaby_admitted() == null || o.getBaby_apgar1() == null || o.getBaby_apgar5() == null
							|| o.getBaby_cry() == null || (baby_ddatetime_expected && baby_ddatetime_any_missing)
							|| (!baby_ddatetime_expected && baby_ddatetime_any_specified)
							|| o.getBaby_medicalcod() == null || o.getBaby_resuscitation() == null) {
						o.setData_complete(0);
					} else {
						o.setData_complete(1);
						final String arrayToJson = objectMapper
								.writeValueAsString(processListOf(selected.getBabydeath()));
						selected.getBabydeath().setBaby_json(arrayToJson);
					}

					babyRepo.save(selected.getBabydeath());
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}

			}
			break;
		}
		case 9: {
			try {
				MultipartFile file = selected.getNotes().getFile();
				selected.getNotes().setBase64image(null);

				try {
					if (file != null && file.getBytes() != null && file.getBytes().length > 0) {
						selected.getNotes().setNotes_file(file.getBytes());
						selected.getNotes().setNotes_filetype(file.getContentType());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				final String arrayToJson = objectMapper.writeValueAsString(processListOf(selected.getNotes()));
				selected.getNotes().setNotes_json(arrayToJson);

				notRepo.save(selected.getNotes());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			break;
		}
		default: {
			selected.setCase_status(0);
			caseRepo.save(selected);
			break;
		}
		}

		return "redirect:/registry/edit/" + selected.getCase_uuid() + "?page="
				+ ((go != null && page < 9) ? ++page : page) + "&success=yes";
	}

	@GetMapping("/submit/{id}")
	public String submit(Principal principal, Model model, @PathVariable("id") String case_uuid) {

		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table synctable = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		model.addAttribute("myf", synctable.getSync_name());

		case_identifiers selected = caseRepo.findById(case_uuid).get();

		model.addAttribute("selected", selected);

		return "registry/case-submit";
	}

	@PostMapping("/submit/{id}")
	public String submit(Principal principal, @PathVariable("id") String case_uuid) {
		case_identifiers selected = caseRepo.findById(case_uuid).get();
		selected.setCase_status(1);

		caseRepo.save(selected);

		try {
			if (InternetAvailabilityChecker.isInternetAvailable()) {

				sync_table sync = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
				emailService.sendSimpleMessage(getRecipients(), "TEST MESSAGE- PDSR DEATH NOTIFICATION!",
						"Hello, \nThis is is to notify you of a " + getAnswer("death_options", selected.getCase_death())
								+ "\nMother's age: " + selected.getBiodata().getBiodata_mage() + "\nChild's sex: "
								+ getAnswer("sex_options", selected.getBiodata().getBiodata_sex()) + "\nDate of death: "
								+ (selected.getCase_death() == 1
										? (selected.getDelivery().getDelivery_date() == null ? "Not stated"
												: new SimpleDateFormat("dd/MMM/yyyy")
														.format(selected.getDelivery().getDelivery_date()))
										: (selected.getBabydeath().getBaby_ddate() == null ? "Not stated"
												: new SimpleDateFormat("dd/MMM/yyyy")
														.format(selected.getBabydeath().getBaby_ddate())))
								+ "\nHealth Facility: " + sync.getSync_name() + " - " + sync.getSync_code()
								+ "\nThis is a PILOT IMPLEMENTATION of the Enhanced Automated PDSR tool developed by Alex and Eliezer");
			}
		} catch (IOException e) {
		}

		return "redirect:/registry?page=1&success=yes";
	}

	private Integer validateTheTimesOnReferralPage(Model model, BindingResult results, case_identifiers selected,
			case_identifiers existing) {
		// validate
		final Date referralDate = selected.getReferral().getReferral_date();
		final Date arrivalDate = selected.getReferral().getReferral_adate();
		if (referralDate != null && arrivalDate != null) {

			if (arrivalDate.before(referralDate)) {
				results.rejectValue("referral.referral_adate", "error.date.arrived.before.referral");
				return 1;

			} else if (arrivalDate.compareTo(referralDate) == 0) {
				final Date referralTime = selected.getReferral().getReferral_date();
				final Date arrivalTime = selected.getReferral().getReferral_adate();

				if (referralTime != null && arrivalTime != null) {
					final Integer referralHour = selected.getReferral().getReferral_hour();
					final Integer referralMins = selected.getReferral().getReferral_minute();

					final Integer arrivalHour = selected.getReferral().getReferral_ahour();
					final Integer arrivalMins = selected.getReferral().getReferral_aminute();

					if (((arrivalHour * 60) + arrivalMins) <= ((referralHour * 60) + referralMins)) {
						results.rejectValue("referral.referral_atime", "error.time.arrived.before.referral");
						return 2;
					}
				}
			}

		}

		if (existing.getLabour() != null) {
			final Date seenDate = existing.getLabour().getLabour_seedate();

			if (arrivalDate != null && seenDate != null) {

				if (seenDate.before(arrivalDate)) {
					results.rejectValue("referral.referral_adate", "error.date.seen.before.arrival");
					return 3;
				} else if (arrivalDate.compareTo(seenDate) == 0) {
					final Date arrivalTime = selected.getReferral().getReferral_date();
					final Date seenTime = existing.getLabour().getLabour_seetime();

					if (arrivalTime != null && seenTime != null) {
						final Integer arrivalHour = selected.getReferral().getReferral_hour();
						final Integer arrivalMins = selected.getReferral().getReferral_minute();

						final Integer seenHour = existing.getLabour().getLabour_seehour();
						final Integer seenMins = existing.getLabour().getLabour_seeminute();

						if (((seenHour * 60) + seenMins) <= ((arrivalHour * 60) + arrivalMins)) {
							results.rejectValue("referral.referral_atime", "error.time.seen.before.arrival");
							return 4;
						}
					}
				}

			}
		}

		return 0;

	}

	private Integer validateTheTimesOnLabourPage(Model model, BindingResult results, case_identifiers selected,
			case_identifiers existing) {
		// validate
		final Date arrivalDate = existing.getReferral().getReferral_adate();
		final Date seenDate = selected.getLabour().getLabour_seedate();

		if (arrivalDate != null && seenDate != null) {

			if (seenDate.before(arrivalDate)) {
				results.rejectValue("labour.labour_seedate", "error.date.seen.before.arrival");
				return 1;
			} else if (seenDate.compareTo(arrivalDate) == 0) {
				final Date arrivalTime = existing.getReferral().getReferral_date();
				final Date seenTime = selected.getLabour().getLabour_seetime();

				if (arrivalTime != null && seenTime != null) {
					final Integer arrivalHour = existing.getReferral().getReferral_ahour();
					final Integer arrivalMins = existing.getReferral().getReferral_aminute();

					final Integer seenHour = selected.getLabour().getLabour_seehour();
					final Integer seenMins = selected.getLabour().getLabour_seeminute();

					if (((seenHour * 60) + seenMins) <= ((arrivalHour * 60) + arrivalMins)) {
						results.rejectValue("labour.labour_seetime", "error.time.seen.before.arrival");
						return 2;
					}
				}
			}

		}

		return 0;

	}

	private Integer validateTheTimesOnDeliveryPage(Model model, BindingResult results, case_identifiers selected,
			case_identifiers existing) {
		// validate
		final Date csdecisionDate = existing.getBirth().getBirth_csproposedate();
		final Date deliveryDate = selected.getDelivery().getDelivery_date();

		if (deliveryDate != null && csdecisionDate != null) {

			if (deliveryDate.before(csdecisionDate)) {
				results.rejectValue("delivery.delivery_date", "error.date.delivery.before.csdecision");
				return 1;
			} else if (deliveryDate.compareTo(csdecisionDate) == 0) {
				final Date csdecisionTime = existing.getBirth().getBirth_csproposedate();
				final Date deliveryTime = selected.getDelivery().getDelivery_time();

				if (csdecisionTime != null && deliveryTime != null) {
					final Integer csdecisionHour = existing.getBirth().getBirth_csproposehour();
					final Integer csdecisionMins = existing.getBirth().getBirth_csproposeminute();

					final Integer deliveryHour = selected.getDelivery().getDelivery_hour();
					final Integer deliveryMins = selected.getDelivery().getDelivery_minute();

					if (((deliveryHour * 60) + deliveryMins) <= ((csdecisionHour * 60) + csdecisionMins)) {
						results.rejectValue("delivery.delivery_time", "error.time.delivery.before.csdecision");
						return 2;
					}
				}
			}

		}

		if (existing.getBabydeath() != null) {

			final Date deathDate = existing.getBabydeath().getBaby_ddate();

			if (deathDate != null && deliveryDate != null) {

				if (deathDate.before(deliveryDate)) {
					results.rejectValue("delivery.delivery_date", "error.date.death.before.delivery");
					return 3;
				} else if (deathDate.compareTo(deliveryDate) == 0) {
					final Date deliveryTime = selected.getDelivery().getDelivery_time();
					final Date deathTime = existing.getBabydeath().getBaby_dtime();

					if (deliveryTime != null && deathTime != null) {
						final Integer deliveryHour = selected.getDelivery().getDelivery_hour();
						final Integer deliveryMins = selected.getDelivery().getDelivery_minute();

						final Integer deathHour = existing.getBabydeath().getBaby_dhour();
						final Integer deathMins = existing.getBabydeath().getBaby_dminute();

						if (((deathHour * 60) + deathMins) <= ((deliveryHour * 60) + deliveryMins)) {
							results.rejectValue("delivery.delivery_time", "error.time.death.before.delivery");
							return 4;
						}
					}
				}
			}
		}

		return 0;

	}

	private Integer validateTheTimesOnBirthPage(Model model, BindingResult results, case_identifiers selected,
			case_identifiers existing) {
		// validate
		final Date csdecisionDate = selected.getBirth().getBirth_csproposedate();
		final Date deliveryDate = existing.getDelivery().getDelivery_date();

		if (deliveryDate != null && csdecisionDate != null) {
			if (deliveryDate.before(csdecisionDate)) {
				results.rejectValue("birth.birth_csproposedate", "error.date.delivery.before.csdecision");
				return 1;
			} else if (deliveryDate.compareTo(csdecisionDate) == 0) {
				final Date csdecisionTime = selected.getBirth().getBirth_csproposetime();
				final Date deliveryTime = existing.getDelivery().getDelivery_time();

				if (csdecisionTime != null && deliveryTime != null) {
					final Integer csdecisionHour = selected.getBirth().getBirth_csproposehour();
					final Integer csdecisionMins = selected.getBirth().getBirth_csproposeminute();

					final Integer deliveryHour = existing.getDelivery().getDelivery_hour();
					final Integer deliveryMins = existing.getDelivery().getDelivery_minute();

					if (((deliveryHour * 60) + deliveryMins) < ((csdecisionHour * 60) + csdecisionMins)) {
						results.rejectValue("birth.birth_csproposetime", "error.time.delivery.before.csdecision");
						return 2;
					}
				}
			}

		}

		return 0;

	}

	private Integer validateTheTimesOnBabydeathPage(Model model, BindingResult results, case_identifiers selected,
			case_identifiers existing) {
		// validate
		final Date deliveryDate = existing.getDelivery().getDelivery_date();
		final Date deathDate = selected.getBabydeath().getBaby_ddate();

		if (deathDate != null && deliveryDate != null) {

			if (deathDate.before(deliveryDate)) {
				results.rejectValue("babydeath.baby_ddate", "error.date.death.before.delivery");
				return 1;
			} else if (deathDate.compareTo(deliveryDate) == 0) {
				final Date deliveryTime = existing.getDelivery().getDelivery_time();
				final Date deathTime = selected.getBabydeath().getBaby_dtime();

				if (deliveryTime != null && deathTime != null) {
					final Integer deliveryHour = existing.getDelivery().getDelivery_hour();
					final Integer deliveryMins = existing.getDelivery().getDelivery_minute();

					final Integer deathHour = selected.getBabydeath().getBaby_dhour();
					final Integer deathMins = selected.getBabydeath().getBaby_dminute();

					if (((deathHour * 60) + deathMins) <= ((deliveryHour * 60) + deliveryMins)) {
						results.rejectValue("babydeath.baby_dtime", "error.time.death.before.delivery");
						return 2;
					}
				}
			}

		}

		return 0;
	}

	@GetMapping(value = "/icdselect")
	public @ResponseBody Set<icd_diagnoses> findByDiagnosis(@RequestParam(value = "q", required = true) String search) {

		Set<icd_diagnoses> diaglist = icddRepo.findByDiagnosese("%" + search + "%");

		return diaglist;
	}

	private String[] getRecipients() {
		List<String> recipientList = userRepo.findByUser_alerted(true);
		if (recipientList == null) {
			recipientList = new ArrayList<>();
		}
		recipientList.add("makmanu128@gmail.com");
		recipientList.add("elelart@gmail.com");
		// , "thailegebriel@unicef.org",
		// "pwobil@unicef.org", "mkim@unicef.org" };

		return recipientList.toArray(new String[recipientList.size()]);

	}

	@ModelAttribute("death_options")
	public Map<Integer, String> deathOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("death_options")) {
			map.put(elem.getMap_value(), elem.getMap_label());
		}

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

	@ModelAttribute("mage_options")
	public Map<Integer, String> mageOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Select one");
		for (datamap elem : mapRepo.findByMap_feature("mage_options")) {
			map.put(elem.getMap_value(), elem.getMap_value() + " " + elem.getMap_label());
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
		map.put(1, getQuestion("shift.morning"));
		map.put(3, getQuestion("shift.afternoon"));
		map.put(6, getQuestion("shift.night"));

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
			map.put(elem.getMap_value(), elem.getMap_label());
		}

		return map;
	}

	private String getQuestion(String code) {
		return msg.getMessage(code, null, Locale.getDefault());
	}

	private String getAnswer(String feature, Integer value) {
		if (value == null) {
			return "";
		}
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
		boolean isreferral = (o.getReferral_case() != null && o.getReferral_case() == 1);
		List<json_data> list = Stream.of(
				new json_data(getQuestion("label.referral_case"), getAnswer("yesnodk_options", o.getReferral_case()),
						true),
				new json_data(getQuestion("label.referral_patient"),
						getAnswer("patient_options", o.getReferral_patient()), isreferral),
				new json_data(getQuestion("label.referral_source"), getAnswer("source_options", o.getReferral_source()),
						isreferral),
				new json_data(getQuestion("label.referral_facility"), o.getReferral_facility(), isreferral),
				new json_data(getQuestion("label.referral_datetime"),
						(o.getReferral_datetime_notstated() != null && o.getReferral_datetime_notstated() == 1)
								? "Not stated"
								: new SimpleDateFormat("dd-MMM-yyyy").format(o.getReferral_date()) + " at "
										+ new SimpleDateFormat("HH:mm a").format(o.getReferral_time()),
						isreferral),
				new json_data(getQuestion("label.referral_adatetime"),
						(o.getReferral_adatetime_notstated() != null && o.getReferral_adatetime_notstated() == 1)
								? "Not stated"
								: new SimpleDateFormat("dd-MMM-yyyy").format(o.getReferral_adate()) + " at "
										+ new SimpleDateFormat("HH:mm a").format(o.getReferral_atime()),
						isreferral),
				new json_data(getQuestion("label.referral_transport"),
						getAnswer("trans_options", o.getReferral_transport()), isreferral),
				new json_data(getQuestion("label.referral_notes"), o.getReferral_notes(), isreferral),
				new json_data(getQuestion("label.referral_file"),
						CONSTANTS.IMAGE_TAG + convertBinImageToString(o.getReferral_file()), isreferral)

		).collect(Collectors.toList());

		return list;
	}

	private List<json_data> processListOf(case_delivery o) {
		List<json_data> list = Stream.of(
				new json_data(getQuestion("label.delivery_datetime"),
						(o.getDelivery_datetime_notstated() != null && o.getDelivery_datetime_notstated() == 1)
								? "Not stated"
								: new SimpleDateFormat("dd-MMM-yyyy").format(o.getDelivery_date()) + " at "
										+ new SimpleDateFormat("HH:mm a").format(o.getDelivery_time()),
						true),
				new json_data(getQuestion("label.delivery_period"), getAnswer("period_options", o.getDelivery_period()),
						true))
				.collect(Collectors.toList());

		return list;
	}

	private List<json_data> processListOf(case_antenatal o) {
		boolean isattend = o.getAntenatal_attend() == 1;
		boolean hasrisks = o.getAntenatal_risks() == 1;
		boolean hasfolic = o.getAntenatal_folicacid() == 1;
		String items = "";
		if (o.getRisks() != null && !o.getRisks().isEmpty())
			for (risk_table elem : o.getRisks()) {
				items += elem.getRisk_name() + "<br/>";
			}
		if (o.getNew_risks() != null && o.getNew_risks().trim() != "")
			items += o.getNew_risks();

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
		if (o.getComplications() != null && !o.getComplications().isEmpty())
			for (complication_table elem : o.getComplications()) {
				items += elem.getComplication_name() + "<br/>";
			}
		if (o.getNew_complications() != null && o.getNew_complications().trim() != "")
			items += o.getNew_complications();

		List<json_data> list = Stream
				.of(new json_data(getQuestion("label.labour_datetime"),
						(o.getLabour_seedatetime_notstated() != null && o.getLabour_seedatetime_notstated() == 1)
								? "Not stated"
								: new SimpleDateFormat("dd-MMM-yyyy").format(o.getLabour_seedate()) + " at "
										+ new SimpleDateFormat("HH:mm a").format(o.getLabour_seetime()),
						true),
						new json_data(getQuestion("label.labour_seeperiod"),
								getAnswer("period_options", o.getLabour_seeperiod()), true),
						new json_data(getQuestion("label.labour_startmode"),
								getAnswer("startmode_options", o.getLabour_startmode()), true),
						new json_data(getQuestion("label.labour_herbalaug"),
								getAnswer("yesnodk_options", o.getLabour_herbalaug()), true),
						new json_data(getQuestion("label.labour_partograph"),
								getAnswer("yesnodkna_options", o.getLabour_partograph()), true),
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
		if (o.getAbnormalities() != null && !o.getAbnormalities().isEmpty())
			for (abnormality_table elem : o.getAbnormalities()) {
				items += elem.getAbnormal_name() + "<br/>";
			}
		if (o.getNew_abnormalities() != null && o.getNew_abnormalities().trim() != "")
			items += o.getNew_abnormalities();

		if (o.getCordfaults() != null && !o.getCordfaults().isEmpty())
			for (cordfault_table elem : o.getCordfaults()) {
				items1 += elem.getCordfault_name() + "<br/>";
			}
		if (o.getNew_cordfaults() != null && o.getNew_cordfaults().trim() != "")
			items1 += o.getNew_cordfaults();

		if (o.getPlacentachecks() != null && !o.getPlacentachecks().isEmpty())
			for (placentacheck_table elem : o.getPlacentachecks()) {
				items2 += elem.getPlacentacheck_name() + "<br/>";
			}
		if (o.getNew_placentachecks() != null && o.getNew_placentachecks().trim() != "")
			items2 += o.getNew_placentachecks();

		List<json_data> list = Stream
				.of(new json_data(getQuestion("label.birth_mode"), getAnswer("mode_options", o.getBirth_mode()), true),
						new json_data(getQuestion("label.birth_insistnormal"),
								getAnswer("yesnodkna_options", o.getBirth_insistnormal()), true),
						new json_data(getQuestion("label.birth_csproposedatetime"),
								(o.getBirth_csproposetime() == null) ? "Not stated"
										: new SimpleDateFormat("HH:mm a").format(o.getBirth_csproposetime()),
								forcenormal),
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
		boolean isresus = o.getBaby_resuscitation() != null && o.getBaby_resuscitation() == 1;
		boolean isadmit = o.getBaby_admitted() != null && o.getBaby_admitted() == 1;

		String items = "", items1 = "";
		if (o.getResuscitations() != null && !o.getResuscitations().isEmpty())
			for (resuscitation_table elem : o.getResuscitations()) {
				items += elem.getResuscitation_name() + "<br/>";
			}
		if (o.getNew_resuscitation() != null && o.getNew_resuscitation().trim() != "")
			items += o.getNew_resuscitation();

		if (o.getDiagnoses() != null && !o.getDiagnoses().isEmpty())
			for (diagnoses_table elem : o.getDiagnoses()) {
				items1 += elem.getDiagnosis_name() + "<br/>";
			}
		if (o.getNew_diagnoses() != null && o.getNew_diagnoses().trim() != "")
			items1 += o.getNew_diagnoses();

		List<json_data> list = Stream
				.of(new json_data(getQuestion("label.baby_cry"), getAnswer("yesnodk_options", o.getBaby_cry()), true),
						new json_data(getQuestion("label.baby_resuscitation"),
								getAnswer("yesnodk_options", o.getBaby_resuscitation()), true),
						new json_data(getQuestion("label.baby_resuscitation"), items, isresus),
						new json_data(getQuestion("label.baby_apgar1"), "" + o.getBaby_apgar1(), true),
						new json_data(getQuestion("label.baby_apgar5"), "" + o.getBaby_apgar5(), true),
						new json_data(getQuestion("label.baby_admitted"),
								getAnswer("yesnodk_options", o.getBaby_admitted()), true),
						new json_data(getQuestion(
								"label.diagnoses"), items1, isadmit),
						new json_data(getQuestion("label.baby_ddatetime"),
								(o.getBaby_ddate() == null) ? ""
										: new SimpleDateFormat("dd-MMM-yyyy").format(o.getBaby_ddate()) + " at "
												+ new SimpleDateFormat("HH:mm a").format(o.getBaby_dtime()),
								isadmit)

				).collect(Collectors.toList());

		return list;
	}

	private List<json_data> processListOf(case_notes o) {
		List<json_data> list = Stream.of(new json_data(getQuestion("label.notes_text"), o.getNotes_text(), true),
				new json_data(getQuestion("label.notes_file"),
						CONSTANTS.IMAGE_TAG + convertBinImageToString(o.getNotes_file()), true)

		).collect(Collectors.toList());

		return list;
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

	private String convertBinImageToString(byte[] binImage) {
		if (binImage != null && binImage.length > 0) {
			return Base64.getEncoder().encodeToString(binImage);
		} else
			return "";
	}

	public Map<String, case_identifiers> returnCaseIdentifiers(InputStream is) {
		Map<String, case_identifiers> caseids = new HashMap<>();
		try (Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheet(SheetSections.CASEIDSHEET.getDescription());
			Iterator<Row> rows = sheet.iterator();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				case_identifiers caseid = new case_identifiers();
				caseid.setCase_uuid(UUID.randomUUID().toString());
				Optional<sync_table> code = syncRepo.findById(CONSTANTS.FACILITY_ID);
				if (code.isPresent()) {
					Optional<facility_table> facility = facilityRepo.findById(code.get().getSync_uuid());
					caseid.setFacility(facility.get());
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							caseid.setCase_date(currentCell.getLocalDateTimeCellValue().toLocalDate());
						}
						break;
					case 1:
						caseid.setCase_id(currentCell.toString());
						break;
					case 2:
						caseid.setCase_death((int) (currentCell.getNumericCellValue()));
						break;
					case 3:
						caseid.setCase_mid(currentCell.toString());
						break;
					case 4:
						caseid.setCase_mname(currentCell.toString());
						break;
					default:
						break;
					}
					cellIdx++;
				}
				caseids.put(caseid.getCase_id(), caseid);
			}
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
		return caseids;
	}

	public List<case_biodata> returnBiodata(InputStream is, Map<String, case_identifiers> caseMap) {
		List<case_biodata> caseBiodata = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheet(SheetSections.CASEBIODATASHEET.getDescription());
			Iterator<Row> rows = sheet.iterator();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				case_biodata biodata = new case_biodata();

				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						final String case_id_num = (currentCell.toString());
						biodata.setBiodata_uuid(caseMap.get(case_id_num).getCase_uuid());
						break;
					case 2:
						final String childSex = currentCell.toString();
						final int childSexCode = EntityMappings.BIODATA_CHILD_SEX.getOrDefault(childSex, null);
						biodata.setBiodata_sex(childSexCode);
						break;
					case 3:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							biodata.setBiodata_mage((int) currentCell.getNumericCellValue());
						}
						break;
					case 4:
						final String mothersEducation = currentCell.toString();
						final int mothersEducationCode = EntityMappings.BIODATA_MOTHER_EDUCATION
								.getOrDefault(mothersEducation, null);
						biodata.setBiodata_medu(mothersEducationCode);
						break;
					default:
						break;
					}
					cellIdx++;
				}
				caseBiodata.add(biodata);
			}
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
		return caseBiodata;
	}

	public static List<case_referral> returnReferral(InputStream is, Map<String, case_identifiers> caseMap) {
		List<case_referral> caseReferrals = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheet(SheetSections.REFERRALSHEET.getDescription());
			Iterator<Row> rows = sheet.iterator();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();
				case_referral referral = new case_referral();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						final String case_id_num = (currentCell.toString());
						referral.setReferral_uuid(caseMap.get(case_id_num).getCase_uuid());
						break;
					case 2:
						final String wasReferred = currentCell.toString();
						final int wasReferredCode = EntityMappings.REFERRAL_WAS_REFERRED.getOrDefault(wasReferred,
								null);
						referral.setReferral_case(wasReferredCode);
						break;
					default:
						break;
					}
					cellIdx++;
				}
				caseReferrals.add(referral);
			}
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
		return caseReferrals;
	}

	public static List<Pregnancy> returnPregnancy(InputStream is) {
		List<Pregnancy> casePregnancies = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheet(PREGNANCYSHEET);
			Iterator<Row> rows = sheet.iterator();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				Pregnancy pregnancy = new Pregnancy();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						pregnancy.setMothersIdNo(currentCell.toString());
						break;
					case 1:
						pregnancy.setMothersName(currentCell.toString());
						break;
					case 2:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							pregnancy.setPregnancyWeeks((int) currentCell.getNumericCellValue());
						}
						break;
					case 3:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							pregnancy.setPregnancyDays((int) currentCell.getNumericCellValue());
						}
						break;
					case 4:
						String pregnancyType = currentCell.toString();
						int pregnancyTypeCode = EntityMappings.PREGNANCY_TYPE.getOrDefault(pregnancyType, null);
						pregnancy.setPregnancyType(pregnancyTypeCode);
						break;
					default:
						break;
					}
					cellIdx++;
				}
				casePregnancies.add(pregnancy);
			}
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
		return casePregnancies;
	}

	public static List<Antenatal> returnAntenatals(InputStream is) {
		List<Antenatal> antenatals = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheet(ANTENATALSHEET);
			Iterator<Row> rows = sheet.iterator();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				Antenatal antenatal = new Antenatal();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						antenatal.setMothersIdNo(currentCell.toString());
						break;
					case 1:
						antenatal.setMothersName(currentCell.toString());
						break;
					case 2:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							antenatal.setAntenatalGravida((int) currentCell.getNumericCellValue());
						}
						break;
					case 3:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							antenatal.setAntenatalParity((int) currentCell.getNumericCellValue());
						}
						break;
					case 4:
						String antenatalAnc = currentCell.toString();
						int antenatalAncCode = EntityMappings.ANTENATAL_ANC.getOrDefault(antenatalAnc, null);
						antenatal.setAntenatalAnc(antenatalAncCode);
						break;
					case 5:
						String antenatalRisk = currentCell.toString();
						int antenatalRiskCode = EntityMappings.ANTENATAL_RISK.getOrDefault(antenatalRisk, null);
						antenatal.setAntenatalRisk(antenatalRiskCode);
						break;
					case 6:
						String antenatalMotherHiv = currentCell.toString();
						int antenatalMotherHivCode = EntityMappings.ANTENATAL_MOTHER_HIV
								.getOrDefault(antenatalMotherHiv, null);
						antenatal.setAntenatalMotherHiv(antenatalMotherHivCode);
						break;
					case 7:
						String antenatalUseOfAlcohol = currentCell.toString();
						int antenatalUseOfAlcoholCode = EntityMappings.ANTENATAL_USE_OF_ALCOHOL
								.getOrDefault(antenatalUseOfAlcohol, null);
						antenatal.setAntenatalUseOfAlcohol(antenatalUseOfAlcoholCode);
						break;
					case 8:
						String antenatalExposureCigarette = currentCell.toString();
						int antenatalExposureCigaretteCode = EntityMappings.ANTENATAL_EXPOSURE_CIGARETTES
								.getOrDefault(antenatalExposureCigarette, null);
						antenatal.setAntenatalExposureCigarette(antenatalExposureCigaretteCode);
						break;
					case 9:
						String antenatalUseOfHerbs = currentCell.toString();
						int antenatalUseOfHerbsCode = EntityMappings.ANTENATAL_USE_OF_HERBS
								.getOrDefault(antenatalUseOfHerbs, null);
						antenatal.setAntenatalUseOfHerbs(antenatalUseOfHerbsCode);
						break;
					case 10:
						String antenatalIntakeFolicAcid = currentCell.toString();
						int antenatalIntakeFolicAcidCode = EntityMappings.ANTENATAL_USE_OF_HERBS
								.getOrDefault(antenatalIntakeFolicAcid, null);
						antenatal.setAntenatalIntakeFolicAcid(antenatalIntakeFolicAcidCode);
						break;
					case 11:
						String antenatalTetanus = currentCell.toString();
						int antenatalTetanusCode = EntityMappings.ANTENATAL_TETANUS.getOrDefault(antenatalTetanus,
								null);
						antenatal.setAntenatalTetanus(antenatalTetanusCode);
						break;
					case 12:
						String antenatalMalaria = currentCell.toString();
						int antenatalMalariaCode = EntityMappings.ANTENATAL_MALARIA.getOrDefault(antenatalMalaria,
								null);
						antenatal.setAntenatalMalaria(antenatalMalariaCode);
						break;
					default:
						break;
					}
					cellIdx++;
				}
				antenatals.add(antenatal);
			}
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
		return antenatals;
	}

	public static List<Labour> returnLabour(InputStream is) {
		List<Labour> caseLabours = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheet(LABOURSHEET);
			Iterator<Row> rows = sheet.iterator();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				Labour labour = new Labour();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						labour.setMothersIdNo(currentCell.toString());
						break;
					case 1:
						labour.setMothersName(currentCell.toString());
						break;
					case 2:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							labour.setLabourSeeDate(currentCell.getLocalDateTimeCellValue().toLocalDate());
						}
						break;
					case 3:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							labour.setLabourSeeTime(currentCell.getLocalDateTimeCellValue().toLocalTime());
						}
						break;
					case 4:
						String labourPeriod = currentCell.toString();
						int labourPeriodCode = EntityMappings.LABOUR_STAFF_PERIOD.getOrDefault(labourPeriod, null);
						labour.setLabourStaffPeriod(labourPeriodCode);
						break;
					case 5:
						String motherHerbalSubstance = currentCell.toString();
						int motherHerbalSubstanceCode = EntityMappings.MOTHER_HERBAL_SUBSTANCE
								.getOrDefault(motherHerbalSubstance, null);
						labour.setMotherHerbalSubstance(motherHerbalSubstanceCode);
						break;
					case 6:
						String labourStart = currentCell.toString();
						int labourStartCode = EntityMappings.LABOUR_START.getOrDefault(labourStart, null);
						labour.setLabourStart(labourStartCode);
						break;
					case 7:
						String labourComplications = currentCell.toString();
						int labourComplicationsCode = EntityMappings.LABOUR_COMPLICATIONS
								.getOrDefault(labourComplications, null);
						labour.setLabourComplications(labourComplicationsCode);
						break;
					default:
						break;
					}
					cellIdx++;
				}
				caseLabours.add(labour);
			}
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
		return caseLabours;
	}

	public static List<Delivery> returnDelivery(InputStream is) {
		List<Delivery> deliveries = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheet(DELIVERYSHEET);
			Iterator<Row> rows = sheet.iterator();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				Delivery delivery = new Delivery();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						delivery.setMothersIdNo(currentCell.toString());
						break;
					case 1:
						delivery.setMothersName(currentCell.toString());
						break;
					case 2:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							delivery.setDeliveryDate(currentCell.getLocalDateTimeCellValue().toLocalDate());
						}
						break;
					case 3:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							delivery.setDeliveryTime(currentCell.getLocalDateTimeCellValue().toLocalTime());
						}
						break;
					case 4:
						String deliveryPeriod = currentCell.toString();
						int deliveryPeriodCode = EntityMappings.DELIVERY_PERIOD.getOrDefault(deliveryPeriod, null);
						delivery.setDeliveryPeriod(deliveryPeriodCode);
						break;
					case 5:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							delivery.setBabyWeight(currentCell.getNumericCellValue());
						}
						break;
					default:
						break;
					}
					cellIdx++;
				}
				deliveries.add(delivery);
			}
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
		return deliveries;
	}

	public static List<Birth> returnBirth(InputStream is) {
		List<Birth> births = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheet(BIRTH_SHEET);
			Iterator<Row> rows = sheet.iterator();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				Birth birth = new Birth();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						birth.setMothersIdNo(currentCell.toString());
						break;
					case 1:
						birth.setMothersName(currentCell.toString());
						break;
					case 2:
						String modeOfDelivery = currentCell.toString();
						int modeOfDeliveryCode = EntityMappings.MODE_OF_DELIVERY.getOrDefault(modeOfDelivery, null);
						birth.setModeOfDelivery(modeOfDeliveryCode);
						break;
					case 3:
						String vaginalDeliveryOccur = currentCell.toString();
						int vaginalDeliveryOccurCode = EntityMappings.VAGINAL_DELIVERY_OCCUR
								.getOrDefault(vaginalDeliveryOccur, null);
						birth.setVaginalDeliveryOccur(vaginalDeliveryOccurCode);
						break;
					case 4:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							birth.setCsDate(currentCell.getLocalDateTimeCellValue().toLocalDate());
						}
						break;
					case 5:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							birth.setCsTime(currentCell.getLocalDateTimeCellValue().toLocalTime());
						}
						break;
					case 6:
						String deliveredBy = currentCell.toString();
						int deliveredByCode = EntityMappings.DELIVERED_BY.getOrDefault(deliveredBy, null);
						birth.setDeliveredBy(deliveredByCode);
						break;
					case 7:
						String deliveredIn = currentCell.toString();
						int deliveredInCode = EntityMappings.DELIVERED_IN.getOrDefault(deliveredIn, null);
						birth.setDeliveredBy(deliveredInCode);
						break;
					case 8:
						String babyAbnormalities = currentCell.toString();
						int babyAbnormalitiesCode = EntityMappings.BABY_ABNORMALITIES.getOrDefault(babyAbnormalities,
								null);
						birth.setBabyAbnormalities(babyAbnormalitiesCode);
						break;
					case 9:
						String cordProblems = currentCell.toString();
						int cordProblemsCode = EntityMappings.CORD_PROBLEMS.getOrDefault(cordProblems, null);
						birth.setBabyAbnormalities(cordProblemsCode);
						break;
					case 10:
						String placentaProblems = currentCell.toString();
						int placentaProblemsCode = EntityMappings.PLACENTA_PROBLEMS.getOrDefault(placentaProblems,
								null);
						birth.setPlacentaProblems(placentaProblemsCode);
						break;
					case 11:
						String liquorVolume = currentCell.toString();
						int liquorVolumeCode = EntityMappings.LIQUOR_VOLUME.getOrDefault(liquorVolume, null);
						birth.setLiquorVolume(liquorVolumeCode);
						break;
					case 12:
						String liquorColor = currentCell.toString();
						int liquorColorCode = EntityMappings.LIQUOR_COLOR.getOrDefault(liquorColor, null);
						birth.setLiquorColor(liquorColorCode);
						break;
					case 13:
						String liquorOdour = currentCell.toString();
						int liquorOdourCode = EntityMappings.LIQUOR_ODOUR.getOrDefault(liquorOdour, null);
						birth.setLiquorOdour(liquorOdourCode);
						break;
					case 14:
						String stateOfBaby = currentCell.toString();
						int stateOfBabyCode = EntityMappings.STATE_OF_BABY.getOrDefault(stateOfBaby, null);
						birth.setStateOfBaby(stateOfBabyCode);
						break;
					case 15:
						String maternalOutcome = currentCell.toString();
						int maternalOutcomeCode = EntityMappings.MATERNAL_OUTCOME.getOrDefault(maternalOutcome, null);
						birth.setMaternalOutcome(maternalOutcomeCode);
						break;
					default:
						break;
					}
					cellIdx++;
				}
				births.add(birth);
			}
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
		return births;
	}

	public static List<FetalHeart> returnFetalHearts(InputStream is) {
		List<FetalHeart> fetalHearts = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheet(FETALHEART_SHEET);
			Iterator<Row> rows = sheet.iterator();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				FetalHeart fetalHeart = new FetalHeart();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						fetalHeart.setMothersIdNo(currentCell.toString());
						break;
					case 1:
						fetalHeart.setMothersName(currentCell.toString());
						break;
					case 2:
						String fetalSoundFacility = currentCell.toString();
						int fetalSoundFacilityCode = EntityMappings.FETAL_SOUND_FACILITY
								.getOrDefault(fetalSoundFacility, null);
						fetalHeart.setFetalHeartSoundPresentFromReferringFacility(fetalSoundFacilityCode);
						break;
					case 3:
						String fetalSoundArrival = currentCell.toString();
						int fetalSoundArrivalCode = EntityMappings.FETAL_SOUND_ARRIVAL.getOrDefault(fetalSoundArrival,
								null);
						fetalHeart.setFetalHeartSoundPresentOnArrival(fetalSoundArrivalCode);
						break;
					case 4:
						String fetalSoundPeriod = currentCell.toString();
						int fetalSoundPeriodCode = EntityMappings.FETAL_SOUND_PERIOD.getOrDefault(fetalSoundPeriod,
								null);
						fetalHeart.setFetalHeartSoundPeriod(fetalSoundPeriodCode);
						break;
					default:
						break;
					}
					cellIdx++;
				}
				fetalHearts.add(fetalHeart);
			}
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
		return fetalHearts;
	}

//    public static List<Notes> returnNotes(InputStream is) {
//        List<Notes> notesList = new ArrayList<>();
//        try (Workbook workbook = new XSSFWorkbook(is)) {
//            Sheet sheet = workbook.getSheet(NOTES_SHEET);
//            Iterator<Row> rows = sheet.iterator();
//            int rowNumber = 0;
//            while (rows.hasNext()) {
//                Row currentRow = rows.next();
//                if (rowNumber == 0) {
//                    rowNumber++;
//                    continue;
//                }
//                Iterator<Cell> cellsInRow = currentRow.iterator();
//                Notes notes = new Notes();
//                int cellIdx = 0;
//                while (cellsInRow.hasNext()) {
//                    Cell currentCell = cellsInRow.next();
//                    switch (cellIdx) {
//                        case 0:
//                            notes.setAdditionalNotes(currentCell.toString());
//                            break;
//                        default:
//                            break;
//                    }
//                    cellIdx++;
//                }
//                notesList.add(notes);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
//        }
//        return notesList;
//    }

}// end class