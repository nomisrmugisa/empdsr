package org.pdsr.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.pdsr.CONSTANTS;
import org.pdsr.master.model.facility_table;
import org.pdsr.master.model.sync_table;
import org.pdsr.master.repo.AuditAuditRepository;
import org.pdsr.master.repo.AuditRecommendRepository;
import org.pdsr.master.repo.CaseRepository;
import org.pdsr.master.repo.FacilityTableRepository;
import org.pdsr.master.repo.IcdCodesRepository;
import org.pdsr.master.repo.SyncTableRepository;
import org.pdsr.master.repo.WeeklyMonitoringTableRepository;
import org.pdsr.pojos.icdpm;
import org.pdsr.pojos.wmindicators;
import org.pdsr.pojos.wmoindicators;
import org.pdsr.pojos.wmsearch;
import org.pdsr.summary.repo.BigAuditAuditRepository;
import org.pdsr.summary.repo.BigAuditRecommendRepository;
import org.pdsr.summary.repo.BigCaseRepository;
import org.pdsr.summary.repo.BigWeeklyMonitoringTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({ "/", "/home" })
public class HomeController {

	@Autowired
	private SyncTableRepository syncRepo;

	@Autowired
	private FacilityTableRepository facRepo;

	@Autowired
	private CaseRepository caseRepo;

	@Autowired
	private WeeklyMonitoringTableRepository wmRepo;

	@Autowired
	private AuditRecommendRepository recRepo;

	@Autowired
	private AuditAuditRepository audRepo;

	@Autowired
	private IcdCodesRepository icdRepo;

	@Autowired
	private BigCaseRepository bcaseRepo;

	@Autowired
	private BigWeeklyMonitoringTableRepository bwmRepo;

	@Autowired
	private BigAuditRecommendRepository brecRepo;

	@Autowired
	private BigAuditAuditRepository baudRepo;

	@GetMapping("")
	public String home(Principal principal, Model model) {
		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table sync = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		facility_table facility = facRepo.findByFacility_code(sync.getSync_code()).get();
		facilityLevel(model, facility);
		return "home";
	}

	@GetMapping("/district")
	public String district(Principal principal, Model model) {
		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table sync = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		facility_table facility = facRepo.findByFacility_code(sync.getSync_code()).get();
		districtLevel(model, facility);
		return "home";

	}

	@GetMapping("/district/search")
	public String districtSearch(Principal principal, Model model) {
		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table sync = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		facility_table facility = facRepo.findByFacility_code(sync.getSync_code()).get();
		model.addAttribute("district", "active");
		model.addAttribute("page", "active");
		model.addAttribute("country_name", facility.getDistrict().getDistrict_name());
		model.addAttribute("selected", new wmsearch());
		model.addAttribute("oavg", new wmoindicators());

		return "home";

	}

	@PostMapping("/district/search")
	public String districtSearch(Principal principal, Model model, @ModelAttribute("selected") wmsearch search) {
		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table sync = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		facility_table facility = facRepo.findByFacility_code(sync.getSync_code()).get();
		districtIndicators(model, search, facility);

		return "home";

	}

	@GetMapping("/regional")
	public String regional(Principal principal, Model model) {

		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table sync = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		facility_table facility = facRepo.findByFacility_code(sync.getSync_code()).get();
		regionalLevel(model, facility);
		return "home";
	}

	@GetMapping("/regional/search")
	public String regionalSearch(Principal principal, Model model) {
		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table sync = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		facility_table facility = facRepo.findByFacility_code(sync.getSync_code()).get();
		model.addAttribute("country_name", facility.getDistrict().getRegion().getCountry().getCountry_name());
		model.addAttribute("regional", "active");
		model.addAttribute("page", "active");
		model.addAttribute("region_name", facility.getDistrict().getRegion().getRegion_name());
		model.addAttribute("selected", new wmsearch());
		model.addAttribute("oavg", new wmoindicators());
		return "home";
	}

	@PostMapping("/regional/search")
	public String regionalSearch(Principal principal, Model model, @ModelAttribute("selected") wmsearch search) {
		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table sync = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		facility_table facility = facRepo.findByFacility_code(sync.getSync_code()).get();
		regionalIndicators(model, search, facility, null);

		return "home";
	}

	@GetMapping("/national")
	public String national(Principal principal, Model model) {
		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table sync = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		facility_table facility = facRepo.findByFacility_code(sync.getSync_code()).get();
		nationalLevel(model, facility);

		return "home";
	}

	@GetMapping("/national/search")
	public String nationalSearch(Principal principal, Model model) {
		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table sync = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		facility_table facility = facRepo.findByFacility_code(sync.getSync_code()).get();
		model.addAttribute("national", "active");
		model.addAttribute("national_level", facility.getDistrict().getRegion().getCountry().getCountry_name());
		model.addAttribute("page", "active");
		model.addAttribute("country_name", facility.getDistrict().getRegion().getCountry().getCountry_name());
		model.addAttribute("selected", new wmsearch());
		model.addAttribute("oavg", new wmoindicators());

		return "home";
	}

	@PostMapping("/national/search")
	public String nationalSearch(Principal principal, Model model, @ModelAttribute("selected") wmsearch search) {
		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table sync = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		facility_table facility = facRepo.findByFacility_code(sync.getSync_code()).get();
		nationalIndicators(model, search, facility);

		return "home";
	}

	@GetMapping("/national/search/{region}")
	public String nationalSearch(Principal principal, Model model, @PathVariable("region") String region_name) {
		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table sync = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		facility_table facility = facRepo.findByFacility_code(sync.getSync_code()).get();
		model.addAttribute("national", "active");
		model.addAttribute("national_regional", region_name);
		model.addAttribute("country_name", facility.getDistrict().getRegion().getCountry().getCountry_name());
		model.addAttribute("page", "active");
		model.addAttribute("region_name", region_name);
		model.addAttribute("selected", new wmsearch());
		model.addAttribute("oavg", new wmoindicators());

		return "home";
	}

	@PostMapping("/national/search/{region}")
	public String nationalSearch(Principal principal, Model model, @PathVariable("region") String region_name,
			@ModelAttribute("selected") wmsearch search) {
		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table sync = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		facility_table facility = facRepo.findByFacility_code(sync.getSync_code()).get();
		regionalIndicators(model, search, facility, region_name);

		return "home";
	}

	private void facilityLevel(Model model, facility_table facility) {

		final int year = Calendar.getInstance().get(Calendar.YEAR);
		final java.util.Date date = new Date();

		model.addAttribute("cyear", year);

		model.addAttribute("entered_nd", caseRepo.countByCase_death(2));
		model.addAttribute("entered_sb", caseRepo.countByCase_death(1));

		model.addAttribute("c_entered_nd", caseRepo.countByCase_death(2, year));
		model.addAttribute("c_entered_sb", caseRepo.countByCase_death(1, year));

		model.addAttribute("submitted_ndn", caseRepo.countBySubmittedAndType(2));
		model.addAttribute("submitted_sbn", caseRepo.countBySubmittedAndType(1));

		model.addAttribute("c_submitted_ndn", caseRepo.countBySubmittedAndType(2, year));
		model.addAttribute("c_submitted_sbn", caseRepo.countBySubmittedAndType(1, year));

		model.addAttribute("submitted_ndp", caseRepo.countByCase_death(2) == 0 ? 0
				: 100 * caseRepo.countBySubmittedAndType(2) / caseRepo.countByCase_death(2));
		model.addAttribute("submitted_sbp", caseRepo.countByCase_death(1) == 0 ? 0
				: 100 * caseRepo.countBySubmittedAndType(1) / caseRepo.countByCase_death(1));

		model.addAttribute("c_submitted_ndp", caseRepo.countByCase_death(2, year) == 0 ? 0
				: 100 * caseRepo.countBySubmittedAndType(2, year) / caseRepo.countByCase_death(2, year));
		model.addAttribute("c_submitted_sbp", caseRepo.countByCase_death(1, year) == 0 ? 0
				: 100 * caseRepo.countBySubmittedAndType(1, year) / caseRepo.countByCase_death(1, year));

		model.addAttribute("selected_nd", caseRepo.countSelectedCasesByCase_death(2));
		model.addAttribute("selected_sb", caseRepo.countSelectedCasesByCase_death(1));

		model.addAttribute("c_selected_nd", caseRepo.countSelectedCasesByCase_death(2, year));
		model.addAttribute("c_selected_sb", caseRepo.countSelectedCasesByCase_death(1, year));

		model.addAttribute("reviewed_ndn", caseRepo.countReviewedCasesByCase_death(2));
		model.addAttribute("reviewed_sbn", caseRepo.countReviewedCasesByCase_death(1));

		model.addAttribute("c_reviewed_ndn", caseRepo.countReviewedCasesByCase_death(2, year));
		model.addAttribute("c_reviewed_sbn", caseRepo.countReviewedCasesByCase_death(1, year));

		model.addAttribute("reviewed_ndp", caseRepo.countSelectedCasesByCase_death(2) == 0 ? 0
				: 100 * caseRepo.countReviewedCasesByCase_death(2) / caseRepo.countSelectedCasesByCase_death(2));
		model.addAttribute("reviewed_sbp", caseRepo.countSelectedCasesByCase_death(1) == 0 ? 0
				: 100 * caseRepo.countReviewedCasesByCase_death(1) / caseRepo.countSelectedCasesByCase_death(1));

		model.addAttribute("reviewed_ndp",
				caseRepo.countSelectedCasesByCase_death(2, year) == 0 ? 0
						: 100 * caseRepo.countReviewedCasesByCase_death(2, year)
								/ caseRepo.countSelectedCasesByCase_death(2, year));
		model.addAttribute("reviewed_sbp",
				caseRepo.countSelectedCasesByCase_death(1, year) == 0 ? 0
						: 100 * caseRepo.countReviewedCasesByCase_death(1, year)
								/ caseRepo.countSelectedCasesByCase_death(1, year));

		// SUMMARY STATISTICS
		final String[] adata = wmRepo.findFrontPageRates().get(0);

		Integer totaldeliveries = (adata[0] == null) ? 0 : Integer.valueOf(adata[0]),
				totaldelvaginal = (adata[1] == null) ? 0 : Integer.valueOf(adata[1]),
				totaldelassisted = (adata[2] == null) ? 0 : Integer.valueOf(adata[2]),
				totaldelcaesarean = (adata[3] == null) ? 0 : Integer.valueOf(adata[3]),

				totalbirths = (adata[4] == null) ? 0 : Integer.valueOf(adata[4]),

				totalstillbirth = (adata[5] == null) ? 0 : Integer.valueOf(adata[5]),
				totalintrapartum = (adata[6] == null) ? 0 : Integer.valueOf(adata[6]),
				// totalantepartum = Integer.valueOf(adata[7]),

				totallivebirths = (adata[8] == null) ? 0 : Integer.valueOf(adata[8]),

				totalpretermbirths = (adata[9] == null) ? 0 : Integer.valueOf(adata[9]),
				totallowbirthwgt = (adata[10] == null) ? 0 : Integer.valueOf(adata[10]),

				totalneondeaths = (adata[11] == null) ? 0 : Integer.valueOf(adata[11]),

				totalneondeaths_e = (adata[12] == null) ? 0 : Integer.valueOf(adata[12]),
				totalneondeaths_l = (adata[13] == null) ? 0 : Integer.valueOf(adata[13]),

				totalmaternaldeaths = (adata[13] == null) ? 0 : Integer.valueOf(adata[14]);

		wmoindicators oindicators = new wmoindicators();
		oindicators.setWmdesc("Overall Averages");

		oindicators.setTotalbirths(totalbirths);
		oindicators.setTotallivebirths(totallivebirths);
		oindicators.setTotalperinatals(totalneondeaths_e + totalstillbirth);
		oindicators.setTotalneonatals(totalneondeaths);
		oindicators.setTotalstillbirths(totalstillbirth);

		oindicators.setIsbr_oavg(
				totalbirths == 0 ? 0 : Math.round(((1000.0 * totalstillbirth / totalbirths)) * 10.0) / 10.0);

		oindicators.setIisbr_oavg(
				totalbirths == 0 ? 0 : Math.round(((1000.0 * totalintrapartum / totalbirths)) * 10.0) / 10.0);

		oindicators.setAisbr_oavg(oindicators.getIsbr_oavg() - oindicators.getIisbr_oavg());

		oindicators.setPiisbr_oavg(
				Math.round(((100.0 * oindicators.getIisbr_oavg() / oindicators.getIsbr_oavg())) * 10.0) / 10.0);

		oindicators.setInmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((1000.0 * totalneondeaths / totallivebirths)) * 10.0) / 10.0);

		oindicators.setIndwk1_oavg(
				totalneondeaths == 0 ? 0 : Math.round(((100.0 * totalneondeaths_e / totalneondeaths)) * 10.0) / 10.0);

		oindicators.setEinmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((1000.0 * totalneondeaths_e / totallivebirths)) * 10.0) / 10.0);

		oindicators.setLinmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((1000.0 * totalneondeaths_l / totallivebirths)) * 10.0) / 10.0);

		oindicators.setIpmr_oavg(totalbirths == 0 ? 0
				: Math.round(((1000.0 * (totalneondeaths_e + totalstillbirth) / totalbirths)) * 10.0) / 10.0);

		oindicators.setImmr_oavg(totallivebirths == 0 ? 0
				: Math.round(((100000.0 * totalmaternaldeaths / totallivebirths)) * 10.0) / 10.0);

		oindicators.setIcsr_oavg(
				totaldeliveries == 0 ? 0 : Math.round(((100.0 * totaldelcaesarean / totaldeliveries)) * 10.0) / 10.0);

		oindicators.setIadr_oavg(
				totaldeliveries == 0 ? 0 : Math.round(((100.0 * totaldelassisted / totaldeliveries)) * 10.0) / 10.0);

		oindicators.setIvdr_oavg(totaldeliveries == 0 ? 0
				: Math.round(((100.0 * totaldelvaginal / totaldeliveries) * 100) * 10.0) / 10.0);

		oindicators.setIlbwr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((100.0 * totallowbirthwgt / totallivebirths)) * 10.0) / 10.0);

		oindicators.setIptbr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((100.0 * totalpretermbirths / totallivebirths)) * 10.0) / 10.0);

		oindicators.setMdeath_osum(totalmaternaldeaths);

		model.addAttribute("oavg", oindicators);
		// SUMMARY STATISTICS - CURRENT YEAR
		final String[] cdata = wmRepo.findFrontPageRates(year).get(0);

		Integer ctotaldeliveries = Integer.valueOf(cdata[0] == null ? "0" : cdata[0]);
		Integer ctotaldelvaginal = Integer.valueOf(cdata[1] == null ? "0" : cdata[1]);
		Integer ctotaldelassisted = Integer.valueOf(cdata[2] == null ? "0" : cdata[2]);
		Integer ctotaldelcaesarean = Integer.valueOf(cdata[3] == null ? "0" : cdata[3]);
		Integer ctotalbirths = Integer.valueOf(cdata[4] == null ? "0" : cdata[4]);
		Integer ctotalstillbirth = Integer.valueOf(cdata[5] == null ? "0" : cdata[5]);
		Integer ctotalintrapartum = Integer.valueOf(cdata[6] == null ? "0" : cdata[6]);
		// Integer ctotalantepartum = Integer.valueOf(cdata[7] == null ? "0" :
		// cdata[7]);
		Integer ctotallivebirths = Integer.valueOf(cdata[8] == null ? "0" : cdata[8]);
		Integer ctotalpretermbirths = Integer.valueOf(cdata[9] == null ? "0" : cdata[9]);
		Integer ctotallowbirthwgt = Integer.valueOf(cdata[10] == null ? "0" : cdata[10]);
		Integer ctotalneondeaths = Integer.valueOf(cdata[11] == null ? "0" : cdata[11]);
		Integer ctotalneondeaths_e = Integer.valueOf(cdata[12] == null ? "0" : cdata[12]);
		Integer ctotalneondeaths_l = Integer.valueOf(cdata[13] == null ? "0" : cdata[13]);
		Integer ctotalmaternaldeaths = Integer.valueOf(cdata[14] == null ? "0" : cdata[14]);

		wmoindicators cindicators = new wmoindicators();
		cindicators.setWmdesc("Averages for " + year);

		cindicators.setTotalbirths(ctotalbirths);
		cindicators.setTotallivebirths(ctotallivebirths);
		cindicators.setTotalperinatals(ctotalneondeaths_e + ctotalstillbirth);
		cindicators.setTotalneonatals(ctotalneondeaths);
		cindicators.setTotalstillbirths(ctotalstillbirth);

		cindicators.setIsbr_oavg(
				ctotalbirths == 0 ? 0 : Math.round(((1000.0 * ctotalstillbirth / ctotalbirths)) * 10.0) / 10.0);

		cindicators.setIisbr_oavg(
				ctotalbirths == 0 ? 0 : Math.round(((1000.0 * ctotalintrapartum / ctotalbirths)) * 10.0) / 10.0);

		cindicators.setAisbr_oavg(cindicators.getIsbr_oavg() - cindicators.getIisbr_oavg());

		cindicators.setPiisbr_oavg(
				Math.round(((100.0 * cindicators.getIisbr_oavg() / cindicators.getIsbr_oavg())) * 10.0) / 10.0);

		cindicators.setInmr_oavg(
				ctotallivebirths == 0 ? 0 : Math.round(((1000.0 * ctotalneondeaths / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setIndwk1_oavg(ctotalneondeaths == 0 ? 0
				: Math.round(((100.0 * ctotalneondeaths_e / ctotalneondeaths)) * 10.0) / 10.0);

		cindicators.setEinmr_oavg(ctotallivebirths == 0 ? 0
				: Math.round(((1000.0 * ctotalneondeaths_e / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setLinmr_oavg(ctotallivebirths == 0 ? 0
				: Math.round(((1000.0 * ctotalneondeaths_l / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setIpmr_oavg(ctotalbirths == 0 ? 0
				: Math.round(((1000.0 * (ctotalneondeaths_e + ctotalstillbirth) / ctotalbirths)) * 10.0) / 10.0);

		cindicators.setImmr_oavg(ctotallivebirths == 0 ? 0
				: Math.round(((100000.0 * ctotalmaternaldeaths / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setIcsr_oavg(ctotaldeliveries == 0 ? 0
				: Math.round(((100.0 * ctotaldelcaesarean / ctotaldeliveries)) * 10.0) / 10.0);

		cindicators.setIadr_oavg(
				ctotaldeliveries == 0 ? 0 : Math.round(((100.0 * ctotaldelassisted / ctotaldeliveries)) * 10.0) / 10.0);

		cindicators.setIvdr_oavg(
				ctotaldeliveries == 0 ? 0 : Math.round(((100.0 * ctotaldelvaginal / ctotaldeliveries)) * 10.0) / 10.0);

		cindicators.setIlbwr_oavg(
				ctotallivebirths == 0 ? 0 : Math.round(((100.0 * ctotallowbirthwgt / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setIptbr_oavg(ctotallivebirths == 0 ? 0
				: Math.round(((100.0 * ctotalpretermbirths / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setMdeath_osum(ctotalmaternaldeaths);

		model.addAttribute("cavg", cindicators);

		// RECOMMENDATIONS WITH ACTIONS
		// overall
		model.addAttribute("atotal_actions", recRepo.count());
		model.addAttribute("acompleted_actions", recRepo.countByCompleted());
		model.addAttribute("acompleted_actions_r",
				recRepo.count() == 0 ? 0 : 100 * recRepo.countByCompleted() / recRepo.count());
		model.addAttribute("apending_actions", recRepo.countByPending(date));
		model.addAttribute("apending_actions_r",
				recRepo.count() == 0 ? 0 : 100 * recRepo.countByPending(date) / recRepo.count());
		model.addAttribute("aoverdue_actions", recRepo.countByOverdue(date));
		model.addAttribute("aoverdue_actions_r",
				recRepo.count() == 0 ? 0 : 100 * recRepo.countByOverdue(date) / recRepo.count());

		/// current year
		model.addAttribute("ctotal_actions", recRepo.count(year));
		model.addAttribute("ccompleted_actions", recRepo.countByCompleted(year));
		model.addAttribute("ccompleted_actions_r",
				recRepo.count(year) == 0 ? 0 : 100 * recRepo.countByCompleted(year) / recRepo.count(year));
		model.addAttribute("cpending_actions", recRepo.countByPending(date, year));
		model.addAttribute("cpending_actions_r",
				recRepo.count(year) == 0 ? 0 : 100 * recRepo.countByPending(date, year) / recRepo.count(year));
		model.addAttribute("coverdue_actions", recRepo.countByOverdue(date, year));
		model.addAttribute("coverdue_actions_r",
				recRepo.count(year) == 0 ? 0 : 100 * recRepo.countByOverdue(date, year) / recRepo.count(year));

		// top causes of death overall for neonatal
		List<icdpm> oneonatal = new ArrayList<icdpm>();
		for (String[] elem : audRepo.findByTopPMCodesNeonatal(PageRequest.of(0, 5))) {

			icdpm neonatal = new icdpm();
			neonatal.setPm_code(elem[0]);
			neonatal.setPm_desc(icdRepo.findDescriptionOfICDPMNeonatal(elem[0]).get());
			neonatal.setPm_tsum(elem[1]);

			oneonatal.add(neonatal);
		}
		model.addAttribute("top_oneonatal", oneonatal);

		// top causes of death for neonatal for current year
		List<icdpm> cneonatal = new ArrayList<icdpm>();
		for (String[] elem : audRepo.findByTopPMCodesNeonatal(year, PageRequest.of(0, 5))) {

			icdpm neonatal = new icdpm();
			neonatal.setPm_code(elem[0]);
			neonatal.setPm_desc(icdRepo.findDescriptionOfICDPMNeonatal(elem[0]).get());
			neonatal.setPm_tsum(elem[1]);

			cneonatal.add(neonatal);
		}
		model.addAttribute("top_cneonatal", cneonatal);

		// top causes of death overall for stillbirths
		List<icdpm> ostillbirth = new ArrayList<icdpm>();
		for (String[] elem : audRepo.findByTopPMCodesStillBirth(PageRequest.of(0, 5))) {

			icdpm stillbirth = new icdpm();
			stillbirth.setPm_code(elem[0]);
			final String pm_desc = icdRepo.findDescriptionOfICDPMIntrapartum(elem[0]).isPresent()
					? icdRepo.findDescriptionOfICDPMIntrapartum(elem[0]).get()
					: icdRepo.findDescriptionOfICDPMAntepartum(elem[0]).get();
			stillbirth.setPm_desc(pm_desc);
			stillbirth.setPm_tsum(elem[1]);

			ostillbirth.add(stillbirth);
		}
		model.addAttribute("top_ostillbirth", ostillbirth);

		// top causes of death for stillbirths for current year
		List<icdpm> cstillbirth = new ArrayList<icdpm>();
		for (String[] elem : audRepo.findByTopPMCodesStillBirth(year, PageRequest.of(0, 5))) {

			icdpm stillbirth = new icdpm();
			stillbirth.setPm_code(elem[0]);
			final String pm_desc = icdRepo.findDescriptionOfICDPMIntrapartum(elem[0]).isPresent()
					? icdRepo.findDescriptionOfICDPMIntrapartum(elem[0]).get()
					: icdRepo.findDescriptionOfICDPMAntepartum(elem[0]).get();
			stillbirth.setPm_desc(pm_desc);
			stillbirth.setPm_tsum(elem[1]);

			cstillbirth.add(stillbirth);
		}
		model.addAttribute("top_cstillbirth", cstillbirth);

	}

	private void districtLevel(Model model, facility_table facility) {

		final String COUNTRY_NAME = facility.getDistrict().getRegion().getCountry().getCountry_name();
		final String REGION_NAME = facility.getDistrict().getRegion().getRegion_name();
		final String DISTRICT_NAME = facility.getDistrict().getDistrict_name();

		model.addAttribute("district", "active");

		final int year = Calendar.getInstance().get(Calendar.YEAR);
		final java.util.Date date = new Date();

		model.addAttribute("cyear", year);

		model.addAttribute("entered_nd", bcaseRepo.countByCase_death(2, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("entered_sb", bcaseRepo.countByCase_death(1, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));

		model.addAttribute("c_entered_nd",
				bcaseRepo.countByCase_death(2, year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("c_entered_sb",
				bcaseRepo.countByCase_death(1, year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));

		model.addAttribute("submitted_ndn",
				bcaseRepo.countByCase_statusAndType(1, 2, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("submitted_sbn",
				bcaseRepo.countByCase_statusAndType(1, 1, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));

		model.addAttribute("c_submitted_ndn",
				bcaseRepo.countByCase_statusAndType(1, 2, year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("c_submitted_sbn",
				bcaseRepo.countByCase_statusAndType(1, 1, year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));

		model.addAttribute("submitted_ndp",
				bcaseRepo.countByCase_death(2, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(1, 2, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME)
								/ bcaseRepo.countByCase_death(2, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("submitted_sbp",
				bcaseRepo.countByCase_death(1, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(1, 1, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME)
								/ bcaseRepo.countByCase_death(1, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));

		model.addAttribute("c_submitted_ndp",
				bcaseRepo.countByCase_death(2, year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(1, 2, year, COUNTRY_NAME, REGION_NAME,
								DISTRICT_NAME)
								/ bcaseRepo.countByCase_death(2, year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("c_submitted_sbp",
				bcaseRepo.countByCase_death(1, year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(1, 1, year, COUNTRY_NAME, REGION_NAME,
								DISTRICT_NAME)
								/ bcaseRepo.countByCase_death(1, year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));

		// selected status=2
		model.addAttribute("selected_nd",
				bcaseRepo.countByCase_statusAndType(2, 2, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("selected_sb",
				bcaseRepo.countByCase_statusAndType(2, 1, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));

		model.addAttribute("c_selected_nd",
				bcaseRepo.countByCase_statusAndType(2, 2, year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("c_selected_sb",
				bcaseRepo.countByCase_statusAndType(2, 1, year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));

		model.addAttribute("reviewed_ndn",
				bcaseRepo.countByCase_statusAndType(3, 2, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("reviewed_sbn",
				bcaseRepo.countByCase_statusAndType(3, 1, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));

		model.addAttribute("c_reviewed_ndn",
				bcaseRepo.countByCase_statusAndType(3, 2, year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("c_reviewed_sbn",
				bcaseRepo.countByCase_statusAndType(3, 1, year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));

		model.addAttribute("reviewed_ndp",
				bcaseRepo.countByCase_statusAndType(3, 2, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(3, 2, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME)
								/ bcaseRepo.countByCase_statusAndType(2, 2, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("reviewed_sbp",
				bcaseRepo.countByCase_statusAndType(2, 1, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(3, 1, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME)
								/ bcaseRepo.countByCase_statusAndType(2, 1, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));

		model.addAttribute("reviewed_ndp",
				bcaseRepo.countByCase_statusAndType(2, 2, year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(3, 2, year, COUNTRY_NAME, REGION_NAME,
								DISTRICT_NAME)
								/ bcaseRepo.countByCase_statusAndType(2, 2, year, COUNTRY_NAME, REGION_NAME,
										DISTRICT_NAME));
		model.addAttribute("reviewed_sbp",
				bcaseRepo.countByCase_statusAndType(2, 1, year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(3, 1, year, COUNTRY_NAME, REGION_NAME,
								DISTRICT_NAME)
								/ bcaseRepo.countByCase_statusAndType(2, 1, year, COUNTRY_NAME, REGION_NAME,
										DISTRICT_NAME));

		// SUMMARY STATISTICS
		System.out.println(bwmRepo.findFrontPageRates(COUNTRY_NAME, REGION_NAME, DISTRICT_NAME).size());
		
		
		final String[] adata = bwmRepo.findFrontPageRates(COUNTRY_NAME, REGION_NAME, DISTRICT_NAME).get(0);

		Integer totaldeliveries = (adata[0] == null) ? 0 : Integer.valueOf(adata[0]),
				totaldelvaginal = (adata[1] == null) ? 0 : Integer.valueOf(adata[1]),
				totaldelassisted = (adata[2] == null) ? 0 : Integer.valueOf(adata[2]),
				totaldelcaesarean = (adata[3] == null) ? 0 : Integer.valueOf(adata[3]),

				totalbirths = (adata[4] == null) ? 0 : Integer.valueOf(adata[4]),

				totalstillbirth = (adata[5] == null) ? 0 : Integer.valueOf(adata[5]),
				totalintrapartum = (adata[6] == null) ? 0 : Integer.valueOf(adata[6]),
				// totalantepartum = Integer.valueOf(adata[7]),

				totallivebirths = (adata[8] == null) ? 0 : Integer.valueOf(adata[8]),

				totalpretermbirths = (adata[9] == null) ? 0 : Integer.valueOf(adata[9]),
				totallowbirthwgt = (adata[10] == null) ? 0 : Integer.valueOf(adata[10]),

				totalneondeaths = (adata[11] == null) ? 0 : Integer.valueOf(adata[11]),

				totalneondeaths_e = (adata[12] == null) ? 0 : Integer.valueOf(adata[12]),
				totalneondeaths_l = (adata[13] == null) ? 0 : Integer.valueOf(adata[13]),

				totalmaternaldeaths = (adata[13] == null) ? 0 : Integer.valueOf(adata[14]);

		wmoindicators oindicators = new wmoindicators();
		oindicators.setWmdesc("Overall District/Sub-province Averages");

		oindicators.setTotalbirths(totalbirths);
		oindicators.setTotallivebirths(totallivebirths);
		oindicators.setTotalperinatals(totalneondeaths_e + totalstillbirth);
		oindicators.setTotalneonatals(totalneondeaths);
		oindicators.setTotalstillbirths(totalstillbirth);

		oindicators.setIsbr_oavg(
				totalbirths == 0 ? 0 : Math.round(((1000.0 * totalstillbirth / totalbirths)) * 10.0) / 10.0);

		oindicators.setIisbr_oavg(
				totalbirths == 0 ? 0 : Math.round(((1000.0 * totalintrapartum / totalbirths)) * 10.0) / 10.0);

		oindicators.setAisbr_oavg(oindicators.getIsbr_oavg() - oindicators.getIisbr_oavg());

		oindicators.setPiisbr_oavg(
				Math.round(((100.0 * oindicators.getIisbr_oavg() / oindicators.getIsbr_oavg())) * 10.0) / 10.0);

		oindicators.setInmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((1000.0 * totalneondeaths / totallivebirths)) * 10.0) / 10.0);

		oindicators.setIndwk1_oavg(
				totalneondeaths == 0 ? 0 : Math.round(((100.0 * totalneondeaths_e / totalneondeaths)) * 10.0) / 10.0);

		oindicators.setEinmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((1000.0 * totalneondeaths_e / totallivebirths)) * 10.0) / 10.0);

		oindicators.setLinmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((1000.0 * totalneondeaths_l / totallivebirths)) * 10.0) / 10.0);

		oindicators.setIpmr_oavg(totalbirths == 0 ? 0
				: Math.round(((1000.0 * (totalneondeaths_e + totalstillbirth) / totalbirths)) * 10.0) / 10.0);

		oindicators.setImmr_oavg(totallivebirths == 0 ? 0
				: Math.round(((100000.0 * totalmaternaldeaths / totallivebirths)) * 10.0) / 10.0);

		oindicators.setIcsr_oavg(
				totaldeliveries == 0 ? 0 : Math.round(((100.0 * totaldelcaesarean / totaldeliveries)) * 10.0) / 10.0);

		oindicators.setIadr_oavg(
				totaldeliveries == 0 ? 0 : Math.round(((100.0 * totaldelassisted / totaldeliveries)) * 10.0) / 10.0);

		oindicators.setIvdr_oavg(totaldeliveries == 0 ? 0
				: Math.round(((100.0 * totaldelvaginal / totaldeliveries) * 100) * 10.0) / 10.0);

		oindicators.setIlbwr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((100.0 * totallowbirthwgt / totallivebirths)) * 10.0) / 10.0);

		oindicators.setIptbr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((100.0 * totalpretermbirths / totallivebirths)) * 10.0) / 10.0);

		oindicators.setMdeath_osum(totalmaternaldeaths);

		model.addAttribute("oavg", oindicators);
		// SUMMARY STATISTICS - CURRENT YEAR
		final String[] cdata = bwmRepo.findFrontPageRates(year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME).get(0);

		Integer ctotaldeliveries = Integer.valueOf(cdata[0] == null ? "0" : cdata[0]);
		Integer ctotaldelvaginal = Integer.valueOf(cdata[1] == null ? "0" : cdata[1]);
		Integer ctotaldelassisted = Integer.valueOf(cdata[2] == null ? "0" : cdata[2]);
		Integer ctotaldelcaesarean = Integer.valueOf(cdata[3] == null ? "0" : cdata[3]);
		Integer ctotalbirths = Integer.valueOf(cdata[4] == null ? "0" : cdata[4]);
		Integer ctotalstillbirth = Integer.valueOf(cdata[5] == null ? "0" : cdata[5]);
		Integer ctotalintrapartum = Integer.valueOf(cdata[6] == null ? "0" : cdata[6]);
		// Integer ctotalantepartum = Integer.valueOf(cdata[7] == null ? "0" :
		// cdata[7]);
		Integer ctotallivebirths = Integer.valueOf(cdata[8] == null ? "0" : cdata[8]);
		Integer ctotalpretermbirths = Integer.valueOf(cdata[9] == null ? "0" : cdata[9]);
		Integer ctotallowbirthwgt = Integer.valueOf(cdata[10] == null ? "0" : cdata[10]);
		Integer ctotalneondeaths = Integer.valueOf(cdata[11] == null ? "0" : cdata[11]);
		Integer ctotalneondeaths_e = Integer.valueOf(cdata[12] == null ? "0" : cdata[12]);
		Integer ctotalneondeaths_l = Integer.valueOf(cdata[13] == null ? "0" : cdata[13]);
		Integer ctotalmaternaldeaths = Integer.valueOf(cdata[14] == null ? "0" : cdata[14]);

		wmoindicators cindicators = new wmoindicators();
		cindicators.setWmdesc("District/Sub-provice Averages for " + year);

		cindicators.setTotalbirths(ctotalbirths);
		cindicators.setTotallivebirths(ctotallivebirths);
		cindicators.setTotalperinatals(ctotalneondeaths_e + ctotalstillbirth);
		cindicators.setTotalneonatals(ctotalneondeaths);
		cindicators.setTotalstillbirths(ctotalstillbirth);

		cindicators.setIsbr_oavg(
				ctotalbirths == 0 ? 0 : Math.round(((1000.0 * ctotalstillbirth / ctotalbirths)) * 10.0) / 10.0);

		cindicators.setIisbr_oavg(
				ctotalbirths == 0 ? 0 : Math.round(((1000.0 * ctotalintrapartum / ctotalbirths)) * 10.0) / 10.0);

		cindicators.setAisbr_oavg(cindicators.getIsbr_oavg() - cindicators.getIisbr_oavg());

		cindicators.setPiisbr_oavg(
				Math.round(((100.0 * cindicators.getIisbr_oavg() / cindicators.getIsbr_oavg())) * 10.0) / 10.0);

		cindicators.setInmr_oavg(
				ctotallivebirths == 0 ? 0 : Math.round(((1000.0 * ctotalneondeaths / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setIndwk1_oavg(ctotalneondeaths == 0 ? 0
				: Math.round(((100.0 * ctotalneondeaths_e / ctotalneondeaths)) * 10.0) / 10.0);

		cindicators.setEinmr_oavg(ctotallivebirths == 0 ? 0
				: Math.round(((1000.0 * ctotalneondeaths_e / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setLinmr_oavg(ctotallivebirths == 0 ? 0
				: Math.round(((1000.0 * ctotalneondeaths_l / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setIpmr_oavg(ctotalbirths == 0 ? 0
				: Math.round(((1000.0 * (ctotalneondeaths_e + ctotalstillbirth) / ctotalbirths)) * 10.0) / 10.0);

		cindicators.setImmr_oavg(ctotallivebirths == 0 ? 0
				: Math.round(((100000.0 * ctotalmaternaldeaths / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setIcsr_oavg(ctotaldeliveries == 0 ? 0
				: Math.round(((100.0 * ctotaldelcaesarean / ctotaldeliveries)) * 10.0) / 10.0);

		cindicators.setIadr_oavg(
				ctotaldeliveries == 0 ? 0 : Math.round(((100.0 * ctotaldelassisted / ctotaldeliveries)) * 10.0) / 10.0);

		cindicators.setIvdr_oavg(
				ctotaldeliveries == 0 ? 0 : Math.round(((100.0 * ctotaldelvaginal / ctotaldeliveries)) * 10.0) / 10.0);

		cindicators.setIlbwr_oavg(
				ctotallivebirths == 0 ? 0 : Math.round(((100.0 * ctotallowbirthwgt / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setIptbr_oavg(ctotallivebirths == 0 ? 0
				: Math.round(((100.0 * ctotalpretermbirths / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setMdeath_osum(ctotalmaternaldeaths);

		model.addAttribute("cavg", cindicators);

		// RECOMMENDATIONS WITH ACTIONS
		// overall
		model.addAttribute("atotal_actions", brecRepo.count(COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("acompleted_actions", brecRepo.countByCompleted(COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("acompleted_actions_r",
				brecRepo.count() == 0 ? 0
						: 100 * brecRepo.countByCompleted(COUNTRY_NAME, REGION_NAME, DISTRICT_NAME)
								/ brecRepo.count(COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("apending_actions", brecRepo.countByPending(date, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("apending_actions_r",
				brecRepo.count() == 0 ? 0
						: 100 * brecRepo.countByPending(date, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME)
								/ brecRepo.count(COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("aoverdue_actions", brecRepo.countByOverdue(date, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("aoverdue_actions_r",
				brecRepo.count() == 0 ? 0
						: 100 * brecRepo.countByOverdue(date, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME)
								/ brecRepo.count(COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));

		/// current year
		model.addAttribute("ctotal_actions", brecRepo.count(year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("ccompleted_actions",
				brecRepo.countByCompleted(year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("ccompleted_actions_r",
				brecRepo.count(year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME) == 0 ? 0
						: 100 * brecRepo.countByCompleted(year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME)
								/ brecRepo.count(year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("cpending_actions",
				brecRepo.countByPending(date, year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("cpending_actions_r",
				brecRepo.count(year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME) == 0 ? 0
						: 100 * brecRepo.countByPending(date, year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME)
								/ brecRepo.count(year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("coverdue_actions",
				brecRepo.countByOverdue(date, year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));
		model.addAttribute("coverdue_actions_r",
				brecRepo.count(year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME) == 0 ? 0
						: 100 * brecRepo.countByOverdue(date, year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME)
								/ brecRepo.count(year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME));

		// top causes of death overall for neonatal
		List<icdpm> oneonatal = new ArrayList<icdpm>();
		for (String[] elem : baudRepo.findByTopPMCodesNeonatal(COUNTRY_NAME, REGION_NAME, DISTRICT_NAME,
				PageRequest.of(0, 5))) {

			icdpm neonatal = new icdpm();
			neonatal.setPm_code(elem[0]);
			neonatal.setPm_desc(icdRepo.findDescriptionOfICDPMNeonatal(elem[0]).get());
			neonatal.setPm_tsum(elem[1]);

			oneonatal.add(neonatal);
		}
		model.addAttribute("top_oneonatal", oneonatal);

		// top causes of death for neonatal for current year
		List<icdpm> cneonatal = new ArrayList<icdpm>();
		for (String[] elem : baudRepo.findByTopPMCodesNeonatal(year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME,
				PageRequest.of(0, 5))) {

			icdpm neonatal = new icdpm();
			neonatal.setPm_code(elem[0]);
			neonatal.setPm_desc(icdRepo.findDescriptionOfICDPMNeonatal(elem[0]).get());
			neonatal.setPm_tsum(elem[1]);

			cneonatal.add(neonatal);
		}
		model.addAttribute("top_cneonatal", cneonatal);

		// top causes of death overall for stillbirths
		List<icdpm> ostillbirth = new ArrayList<icdpm>();
		for (String[] elem : baudRepo.findByTopPMCodesStillBirth(COUNTRY_NAME, REGION_NAME, DISTRICT_NAME,
				PageRequest.of(0, 5))) {

			icdpm stillbirth = new icdpm();
			stillbirth.setPm_code(elem[0]);
			final String pm_desc = icdRepo.findDescriptionOfICDPMIntrapartum(elem[0]).isPresent()
					? icdRepo.findDescriptionOfICDPMIntrapartum(elem[0]).get()
					: icdRepo.findDescriptionOfICDPMAntepartum(elem[0]).get();
			stillbirth.setPm_desc(pm_desc);
			stillbirth.setPm_tsum(elem[1]);

			ostillbirth.add(stillbirth);
		}
		model.addAttribute("top_ostillbirth", ostillbirth);

		// top causes of death for stillbirths for current year
		List<icdpm> cstillbirth = new ArrayList<icdpm>();
		for (String[] elem : baudRepo.findByTopPMCodesStillBirth(year, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME,
				PageRequest.of(0, 5))) {

			icdpm stillbirth = new icdpm();
			stillbirth.setPm_code(elem[0]);
			final String pm_desc = icdRepo.findDescriptionOfICDPMIntrapartum(elem[0]).isPresent()
					? icdRepo.findDescriptionOfICDPMIntrapartum(elem[0]).get()
					: icdRepo.findDescriptionOfICDPMAntepartum(elem[0]).get();
			stillbirth.setPm_desc(pm_desc);
			stillbirth.setPm_tsum(elem[1]);

			cstillbirth.add(stillbirth);
		}
		model.addAttribute("top_cstillbirth", cstillbirth);

	}

	private void regionalLevel(Model model, facility_table facility) {

		final String COUNTRY_NAME = facility.getDistrict().getRegion().getCountry().getCountry_name();
		final String REGION_NAME = facility.getDistrict().getRegion().getRegion_name();

		model.addAttribute("regional", "active");

		final int year = Calendar.getInstance().get(Calendar.YEAR);
		final java.util.Date date = new Date();

		model.addAttribute("cyear", year);

		model.addAttribute("entered_nd", bcaseRepo.countByCase_death(2, COUNTRY_NAME, REGION_NAME));
		model.addAttribute("entered_sb", bcaseRepo.countByCase_death(1, COUNTRY_NAME, REGION_NAME));

		model.addAttribute("c_entered_nd", bcaseRepo.countByCase_death(2, year, COUNTRY_NAME, REGION_NAME));
		model.addAttribute("c_entered_sb", bcaseRepo.countByCase_death(1, year, COUNTRY_NAME, REGION_NAME));

		model.addAttribute("submitted_ndn", bcaseRepo.countByCase_statusAndType(1, 2, COUNTRY_NAME, REGION_NAME));
		model.addAttribute("submitted_sbn", bcaseRepo.countByCase_statusAndType(1, 1, COUNTRY_NAME, REGION_NAME));

		model.addAttribute("c_submitted_ndn",
				bcaseRepo.countByCase_statusAndType(1, 2, year, COUNTRY_NAME, REGION_NAME));
		model.addAttribute("c_submitted_sbn",
				bcaseRepo.countByCase_statusAndType(1, 1, year, COUNTRY_NAME, REGION_NAME));

		model.addAttribute("submitted_ndp",
				bcaseRepo.countByCase_death(2, COUNTRY_NAME, REGION_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(1, 2, COUNTRY_NAME, REGION_NAME)
								/ bcaseRepo.countByCase_death(2, COUNTRY_NAME, REGION_NAME));
		model.addAttribute("submitted_sbp",
				bcaseRepo.countByCase_death(1, COUNTRY_NAME, REGION_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(1, 1, COUNTRY_NAME, REGION_NAME)
								/ bcaseRepo.countByCase_death(1, COUNTRY_NAME, REGION_NAME));

		model.addAttribute("c_submitted_ndp",
				bcaseRepo.countByCase_death(2, year, COUNTRY_NAME, REGION_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(1, 2, year, COUNTRY_NAME, REGION_NAME)
								/ bcaseRepo.countByCase_death(2, year, COUNTRY_NAME, REGION_NAME));
		model.addAttribute("c_submitted_sbp",
				bcaseRepo.countByCase_death(1, year, COUNTRY_NAME, REGION_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(1, 1, year, COUNTRY_NAME, REGION_NAME)
								/ bcaseRepo.countByCase_death(1, year, COUNTRY_NAME, REGION_NAME));

		// selected status=2
		model.addAttribute("selected_nd", bcaseRepo.countByCase_statusAndType(2, 2, COUNTRY_NAME, REGION_NAME));
		model.addAttribute("selected_sb", bcaseRepo.countByCase_statusAndType(2, 1, COUNTRY_NAME, REGION_NAME));

		model.addAttribute("c_selected_nd", bcaseRepo.countByCase_statusAndType(2, 2, year, COUNTRY_NAME, REGION_NAME));
		model.addAttribute("c_selected_sb", bcaseRepo.countByCase_statusAndType(2, 1, year, COUNTRY_NAME, REGION_NAME));

		model.addAttribute("reviewed_ndn", bcaseRepo.countByCase_statusAndType(3, 2, COUNTRY_NAME, REGION_NAME));
		model.addAttribute("reviewed_sbn", bcaseRepo.countByCase_statusAndType(3, 1, COUNTRY_NAME, REGION_NAME));

		model.addAttribute("c_reviewed_ndn",
				bcaseRepo.countByCase_statusAndType(3, 2, year, COUNTRY_NAME, REGION_NAME));
		model.addAttribute("c_reviewed_sbn",
				bcaseRepo.countByCase_statusAndType(3, 1, year, COUNTRY_NAME, REGION_NAME));

		model.addAttribute("reviewed_ndp",
				bcaseRepo.countByCase_statusAndType(3, 2, COUNTRY_NAME, REGION_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(3, 2, COUNTRY_NAME, REGION_NAME)
								/ bcaseRepo.countByCase_statusAndType(2, 2, COUNTRY_NAME, REGION_NAME));
		model.addAttribute("reviewed_sbp",
				bcaseRepo.countByCase_statusAndType(2, 1, COUNTRY_NAME, REGION_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(3, 1, COUNTRY_NAME, REGION_NAME)
								/ bcaseRepo.countByCase_statusAndType(2, 1, COUNTRY_NAME, REGION_NAME));

		model.addAttribute("reviewed_ndp",
				bcaseRepo.countByCase_statusAndType(2, 2, year, COUNTRY_NAME, REGION_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(3, 2, year, COUNTRY_NAME, REGION_NAME)
								/ bcaseRepo.countByCase_statusAndType(2, 2, year, COUNTRY_NAME, REGION_NAME));
		model.addAttribute("reviewed_sbp",
				bcaseRepo.countByCase_statusAndType(2, 1, year, COUNTRY_NAME, REGION_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(3, 1, year, COUNTRY_NAME, REGION_NAME)
								/ bcaseRepo.countByCase_statusAndType(2, 1, year, COUNTRY_NAME, REGION_NAME));

		// SUMMARY STATISTICS
		final String[] adata = bwmRepo.findFrontPageRates(COUNTRY_NAME, REGION_NAME).get(0);

		Integer totaldeliveries = (adata[0] == null) ? 0 : Integer.valueOf(adata[0]),
				totaldelvaginal = (adata[1] == null) ? 0 : Integer.valueOf(adata[1]),
				totaldelassisted = (adata[2] == null) ? 0 : Integer.valueOf(adata[2]),
				totaldelcaesarean = (adata[3] == null) ? 0 : Integer.valueOf(adata[3]),

				totalbirths = (adata[4] == null) ? 0 : Integer.valueOf(adata[4]),

				totalstillbirth = (adata[5] == null) ? 0 : Integer.valueOf(adata[5]),
				totalintrapartum = (adata[6] == null) ? 0 : Integer.valueOf(adata[6]),
				// totalantepartum = Integer.valueOf(adata[7]),

				totallivebirths = (adata[8] == null) ? 0 : Integer.valueOf(adata[8]),

				totalpretermbirths = (adata[9] == null) ? 0 : Integer.valueOf(adata[9]),
				totallowbirthwgt = (adata[10] == null) ? 0 : Integer.valueOf(adata[10]),

				totalneondeaths = (adata[11] == null) ? 0 : Integer.valueOf(adata[11]),

				totalneondeaths_e = (adata[12] == null) ? 0 : Integer.valueOf(adata[12]),
				totalneondeaths_l = (adata[13] == null) ? 0 : Integer.valueOf(adata[13]),

				totalmaternaldeaths = (adata[13] == null) ? 0 : Integer.valueOf(adata[14]);

		wmoindicators oindicators = new wmoindicators();
		oindicators.setWmdesc("Overall Regional/Province Averages");

		oindicators.setTotalbirths(totalbirths);
		oindicators.setTotallivebirths(totallivebirths);
		oindicators.setTotalperinatals(totalneondeaths_e + totalstillbirth);
		oindicators.setTotalneonatals(totalneondeaths);
		oindicators.setTotalstillbirths(totalstillbirth);

		oindicators.setIsbr_oavg(
				totalbirths == 0 ? 0 : Math.round(((1000.0 * totalstillbirth / totalbirths)) * 10.0) / 10.0);

		oindicators.setIisbr_oavg(
				totalbirths == 0 ? 0 : Math.round(((1000.0 * totalintrapartum / totalbirths)) * 10.0) / 10.0);

		oindicators.setAisbr_oavg(oindicators.getIsbr_oavg() - oindicators.getIisbr_oavg());

		oindicators.setPiisbr_oavg(
				Math.round(((100.0 * oindicators.getIisbr_oavg() / oindicators.getIsbr_oavg())) * 10.0) / 10.0);

		oindicators.setInmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((1000.0 * totalneondeaths / totallivebirths)) * 10.0) / 10.0);

		oindicators.setIndwk1_oavg(
				totalneondeaths == 0 ? 0 : Math.round(((100.0 * totalneondeaths_e / totalneondeaths)) * 10.0) / 10.0);

		oindicators.setEinmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((1000.0 * totalneondeaths_e / totallivebirths)) * 10.0) / 10.0);

		oindicators.setLinmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((1000.0 * totalneondeaths_l / totallivebirths)) * 10.0) / 10.0);

		oindicators.setIpmr_oavg(totalbirths == 0 ? 0
				: Math.round(((1000.0 * (totalneondeaths_e + totalstillbirth) / totalbirths)) * 10.0) / 10.0);

		oindicators.setImmr_oavg(totallivebirths == 0 ? 0
				: Math.round(((100000.0 * totalmaternaldeaths / totallivebirths)) * 10.0) / 10.0);

		oindicators.setIcsr_oavg(
				totaldeliveries == 0 ? 0 : Math.round(((100.0 * totaldelcaesarean / totaldeliveries)) * 10.0) / 10.0);

		oindicators.setIadr_oavg(
				totaldeliveries == 0 ? 0 : Math.round(((100.0 * totaldelassisted / totaldeliveries)) * 10.0) / 10.0);

		oindicators.setIvdr_oavg(totaldeliveries == 0 ? 0
				: Math.round(((100.0 * totaldelvaginal / totaldeliveries) * 100) * 10.0) / 10.0);

		oindicators.setIlbwr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((100.0 * totallowbirthwgt / totallivebirths)) * 10.0) / 10.0);

		oindicators.setIptbr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((100.0 * totalpretermbirths / totallivebirths)) * 10.0) / 10.0);

		oindicators.setMdeath_osum(totalmaternaldeaths);

		model.addAttribute("oavg", oindicators);
		// SUMMARY STATISTICS - CURRENT YEAR
		final String[] cdata = bwmRepo.findFrontPageRates(year, COUNTRY_NAME, REGION_NAME).get(0);

		Integer ctotaldeliveries = Integer.valueOf(cdata[0] == null ? "0" : cdata[0]);
		Integer ctotaldelvaginal = Integer.valueOf(cdata[1] == null ? "0" : cdata[1]);
		Integer ctotaldelassisted = Integer.valueOf(cdata[2] == null ? "0" : cdata[2]);
		Integer ctotaldelcaesarean = Integer.valueOf(cdata[3] == null ? "0" : cdata[3]);
		Integer ctotalbirths = Integer.valueOf(cdata[4] == null ? "0" : cdata[4]);
		Integer ctotalstillbirth = Integer.valueOf(cdata[5] == null ? "0" : cdata[5]);
		Integer ctotalintrapartum = Integer.valueOf(cdata[6] == null ? "0" : cdata[6]);
		// Integer ctotalantepartum = Integer.valueOf(cdata[7] == null ? "0" :
		// cdata[7]);
		Integer ctotallivebirths = Integer.valueOf(cdata[8] == null ? "0" : cdata[8]);
		Integer ctotalpretermbirths = Integer.valueOf(cdata[9] == null ? "0" : cdata[9]);
		Integer ctotallowbirthwgt = Integer.valueOf(cdata[10] == null ? "0" : cdata[10]);
		Integer ctotalneondeaths = Integer.valueOf(cdata[11] == null ? "0" : cdata[11]);
		Integer ctotalneondeaths_e = Integer.valueOf(cdata[12] == null ? "0" : cdata[12]);
		Integer ctotalneondeaths_l = Integer.valueOf(cdata[13] == null ? "0" : cdata[13]);
		Integer ctotalmaternaldeaths = Integer.valueOf(cdata[14] == null ? "0" : cdata[14]);

		wmoindicators cindicators = new wmoindicators();
		cindicators.setWmdesc("Regional/Province Averages for " + year);

		cindicators.setTotalbirths(ctotalbirths);
		cindicators.setTotallivebirths(ctotallivebirths);
		cindicators.setTotalperinatals(ctotalneondeaths_e + ctotalstillbirth);
		cindicators.setTotalneonatals(ctotalneondeaths);
		cindicators.setTotalstillbirths(ctotalstillbirth);

		cindicators.setIsbr_oavg(
				ctotalbirths == 0 ? 0 : Math.round(((1000.0 * ctotalstillbirth / ctotalbirths)) * 10.0) / 10.0);

		cindicators.setIisbr_oavg(
				ctotalbirths == 0 ? 0 : Math.round(((1000.0 * ctotalintrapartum / ctotalbirths)) * 10.0) / 10.0);

		cindicators.setAisbr_oavg(cindicators.getIsbr_oavg() - cindicators.getIisbr_oavg());

		cindicators.setPiisbr_oavg(
				Math.round(((100.0 * cindicators.getIisbr_oavg() / cindicators.getIsbr_oavg())) * 10.0) / 10.0);

		cindicators.setInmr_oavg(
				ctotallivebirths == 0 ? 0 : Math.round(((1000.0 * ctotalneondeaths / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setIndwk1_oavg(ctotalneondeaths == 0 ? 0
				: Math.round(((100.0 * ctotalneondeaths_e / ctotalneondeaths)) * 10.0) / 10.0);

		cindicators.setEinmr_oavg(ctotallivebirths == 0 ? 0
				: Math.round(((1000.0 * ctotalneondeaths_e / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setLinmr_oavg(ctotallivebirths == 0 ? 0
				: Math.round(((1000.0 * ctotalneondeaths_l / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setIpmr_oavg(ctotalbirths == 0 ? 0
				: Math.round(((1000.0 * (ctotalneondeaths_e + ctotalstillbirth) / ctotalbirths)) * 10.0) / 10.0);

		cindicators.setImmr_oavg(ctotallivebirths == 0 ? 0
				: Math.round(((100000.0 * ctotalmaternaldeaths / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setIcsr_oavg(ctotaldeliveries == 0 ? 0
				: Math.round(((100.0 * ctotaldelcaesarean / ctotaldeliveries)) * 10.0) / 10.0);

		cindicators.setIadr_oavg(
				ctotaldeliveries == 0 ? 0 : Math.round(((100.0 * ctotaldelassisted / ctotaldeliveries)) * 10.0) / 10.0);

		cindicators.setIvdr_oavg(
				ctotaldeliveries == 0 ? 0 : Math.round(((100.0 * ctotaldelvaginal / ctotaldeliveries)) * 10.0) / 10.0);

		cindicators.setIlbwr_oavg(
				ctotallivebirths == 0 ? 0 : Math.round(((100.0 * ctotallowbirthwgt / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setIptbr_oavg(ctotallivebirths == 0 ? 0
				: Math.round(((100.0 * ctotalpretermbirths / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setMdeath_osum(ctotalmaternaldeaths);

		model.addAttribute("cavg", cindicators);

		// RECOMMENDATIONS WITH ACTIONS
		// overall
		model.addAttribute("atotal_actions", brecRepo.count(COUNTRY_NAME, REGION_NAME));
		model.addAttribute("acompleted_actions", brecRepo.countByCompleted(COUNTRY_NAME, REGION_NAME));
		model.addAttribute("acompleted_actions_r",
				brecRepo.count() == 0 ? 0
						: 100 * brecRepo.countByCompleted(COUNTRY_NAME, REGION_NAME)
								/ brecRepo.count(COUNTRY_NAME, REGION_NAME));
		model.addAttribute("apending_actions", brecRepo.countByPending(date, COUNTRY_NAME, REGION_NAME));
		model.addAttribute("apending_actions_r",
				brecRepo.count() == 0 ? 0
						: 100 * brecRepo.countByPending(date, COUNTRY_NAME, REGION_NAME)
								/ brecRepo.count(COUNTRY_NAME, REGION_NAME));
		model.addAttribute("aoverdue_actions", brecRepo.countByOverdue(date, COUNTRY_NAME, REGION_NAME));
		model.addAttribute("aoverdue_actions_r",
				brecRepo.count() == 0 ? 0
						: 100 * brecRepo.countByOverdue(date, COUNTRY_NAME, REGION_NAME)
								/ brecRepo.count(COUNTRY_NAME, REGION_NAME));

		/// current year
		model.addAttribute("ctotal_actions", brecRepo.count(year, COUNTRY_NAME, REGION_NAME));
		model.addAttribute("ccompleted_actions", brecRepo.countByCompleted(year, COUNTRY_NAME, REGION_NAME));
		model.addAttribute("ccompleted_actions_r",
				brecRepo.count(year, COUNTRY_NAME, REGION_NAME) == 0 ? 0
						: 100 * brecRepo.countByCompleted(year, COUNTRY_NAME, REGION_NAME)
								/ brecRepo.count(year, COUNTRY_NAME, REGION_NAME));
		model.addAttribute("cpending_actions", brecRepo.countByPending(date, year, COUNTRY_NAME, REGION_NAME));
		model.addAttribute("cpending_actions_r",
				brecRepo.count(year, COUNTRY_NAME, REGION_NAME) == 0 ? 0
						: 100 * brecRepo.countByPending(date, year, COUNTRY_NAME, REGION_NAME)
								/ brecRepo.count(year, COUNTRY_NAME, REGION_NAME));
		model.addAttribute("coverdue_actions", brecRepo.countByOverdue(date, year, COUNTRY_NAME, REGION_NAME));
		model.addAttribute("coverdue_actions_r",
				brecRepo.count(year, COUNTRY_NAME, REGION_NAME) == 0 ? 0
						: 100 * brecRepo.countByOverdue(date, year, COUNTRY_NAME, REGION_NAME)
								/ brecRepo.count(year, COUNTRY_NAME, REGION_NAME));

		// top causes of death overall for neonatal
		List<icdpm> oneonatal = new ArrayList<icdpm>();
		for (String[] elem : baudRepo.findByTopPMCodesNeonatal(COUNTRY_NAME, REGION_NAME, PageRequest.of(0, 5))) {

			icdpm neonatal = new icdpm();
			neonatal.setPm_code(elem[0]);
			neonatal.setPm_desc(icdRepo.findDescriptionOfICDPMNeonatal(elem[0]).get());
			neonatal.setPm_tsum(elem[1]);

			oneonatal.add(neonatal);
		}
		model.addAttribute("top_oneonatal", oneonatal);

		// top causes of death for neonatal for current year
		List<icdpm> cneonatal = new ArrayList<icdpm>();
		for (String[] elem : baudRepo.findByTopPMCodesNeonatal(year, COUNTRY_NAME, REGION_NAME, PageRequest.of(0, 5))) {

			icdpm neonatal = new icdpm();
			neonatal.setPm_code(elem[0]);
			neonatal.setPm_desc(icdRepo.findDescriptionOfICDPMNeonatal(elem[0]).get());
			neonatal.setPm_tsum(elem[1]);

			cneonatal.add(neonatal);
		}
		model.addAttribute("top_cneonatal", cneonatal);

		// top causes of death overall for stillbirths
		List<icdpm> ostillbirth = new ArrayList<icdpm>();
		for (String[] elem : baudRepo.findByTopPMCodesStillBirth(COUNTRY_NAME, REGION_NAME, PageRequest.of(0, 5))) {

			icdpm stillbirth = new icdpm();
			stillbirth.setPm_code(elem[0]);
			final String pm_desc = icdRepo.findDescriptionOfICDPMIntrapartum(elem[0]).isPresent()
					? icdRepo.findDescriptionOfICDPMIntrapartum(elem[0]).get()
					: icdRepo.findDescriptionOfICDPMAntepartum(elem[0]).get();
			stillbirth.setPm_desc(pm_desc);
			stillbirth.setPm_tsum(elem[1]);

			ostillbirth.add(stillbirth);
		}
		model.addAttribute("top_ostillbirth", ostillbirth);

		// top causes of death for stillbirths for current year
		List<icdpm> cstillbirth = new ArrayList<icdpm>();
		for (String[] elem : baudRepo.findByTopPMCodesStillBirth(year, COUNTRY_NAME, REGION_NAME,
				PageRequest.of(0, 5))) {

			icdpm stillbirth = new icdpm();
			stillbirth.setPm_code(elem[0]);
			final String pm_desc = icdRepo.findDescriptionOfICDPMIntrapartum(elem[0]).isPresent()
					? icdRepo.findDescriptionOfICDPMIntrapartum(elem[0]).get()
					: icdRepo.findDescriptionOfICDPMAntepartum(elem[0]).get();
			stillbirth.setPm_desc(pm_desc);
			stillbirth.setPm_tsum(elem[1]);

			cstillbirth.add(stillbirth);
		}
		model.addAttribute("top_cstillbirth", cstillbirth);

	}

	private void nationalLevel(Model model, facility_table facility) {

		final String COUNTRY_NAME = facility.getDistrict().getRegion().getCountry().getCountry_name();

		model.addAttribute("national", "active");

		final int year = Calendar.getInstance().get(Calendar.YEAR);
		final java.util.Date date = new Date();

		model.addAttribute("cyear", year);

		model.addAttribute("entered_nd", bcaseRepo.countByCase_death(2, COUNTRY_NAME));
		model.addAttribute("entered_sb", bcaseRepo.countByCase_death(1, COUNTRY_NAME));

		model.addAttribute("c_entered_nd", bcaseRepo.countByCase_death(2, year, COUNTRY_NAME));
		model.addAttribute("c_entered_sb", bcaseRepo.countByCase_death(1, year, COUNTRY_NAME));

		model.addAttribute("submitted_ndn", bcaseRepo.countByCase_statusAndType(1, 2, COUNTRY_NAME));
		model.addAttribute("submitted_sbn", bcaseRepo.countByCase_statusAndType(1, 1, COUNTRY_NAME));

		model.addAttribute("c_submitted_ndn", bcaseRepo.countByCase_statusAndType(1, 2, year, COUNTRY_NAME));
		model.addAttribute("c_submitted_sbn", bcaseRepo.countByCase_statusAndType(1, 1, year, COUNTRY_NAME));

		model.addAttribute("submitted_ndp",
				bcaseRepo.countByCase_death(2, COUNTRY_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(1, 2, COUNTRY_NAME)
								/ bcaseRepo.countByCase_death(2, COUNTRY_NAME));
		model.addAttribute("submitted_sbp",
				bcaseRepo.countByCase_death(1, COUNTRY_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(1, 1, COUNTRY_NAME)
								/ bcaseRepo.countByCase_death(1, COUNTRY_NAME));

		model.addAttribute("c_submitted_ndp",
				bcaseRepo.countByCase_death(2, year, COUNTRY_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(1, 2, year, COUNTRY_NAME)
								/ bcaseRepo.countByCase_death(2, year, COUNTRY_NAME));
		model.addAttribute("c_submitted_sbp",
				bcaseRepo.countByCase_death(1, year, COUNTRY_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(1, 1, year, COUNTRY_NAME)
								/ bcaseRepo.countByCase_death(1, year, COUNTRY_NAME));

		// selected status=2
		model.addAttribute("selected_nd", bcaseRepo.countByCase_statusAndType(2, 2, COUNTRY_NAME));
		model.addAttribute("selected_sb", bcaseRepo.countByCase_statusAndType(2, 1, COUNTRY_NAME));

		model.addAttribute("c_selected_nd", bcaseRepo.countByCase_statusAndType(2, 2, year, COUNTRY_NAME));
		model.addAttribute("c_selected_sb", bcaseRepo.countByCase_statusAndType(2, 1, year, COUNTRY_NAME));

		model.addAttribute("reviewed_ndn", bcaseRepo.countByCase_statusAndType(3, 2, COUNTRY_NAME));
		model.addAttribute("reviewed_sbn", bcaseRepo.countByCase_statusAndType(3, 1, COUNTRY_NAME));

		model.addAttribute("c_reviewed_ndn", bcaseRepo.countByCase_statusAndType(3, 2, year, COUNTRY_NAME));
		model.addAttribute("c_reviewed_sbn", bcaseRepo.countByCase_statusAndType(3, 1, year, COUNTRY_NAME));

		model.addAttribute("reviewed_ndp",
				bcaseRepo.countByCase_statusAndType(3, 2, COUNTRY_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(3, 2, COUNTRY_NAME)
								/ bcaseRepo.countByCase_statusAndType(2, 2, COUNTRY_NAME));
		model.addAttribute("reviewed_sbp",
				bcaseRepo.countByCase_statusAndType(2, 1, COUNTRY_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(3, 1, COUNTRY_NAME)
								/ bcaseRepo.countByCase_statusAndType(2, 1, COUNTRY_NAME));

		model.addAttribute("reviewed_ndp",
				bcaseRepo.countByCase_statusAndType(2, 2, year, COUNTRY_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(3, 2, year, COUNTRY_NAME)
								/ bcaseRepo.countByCase_statusAndType(2, 2, year, COUNTRY_NAME));
		model.addAttribute("reviewed_sbp",
				bcaseRepo.countByCase_statusAndType(2, 1, year, COUNTRY_NAME) == 0 ? 0
						: 100 * bcaseRepo.countByCase_statusAndType(3, 1, year, COUNTRY_NAME)
								/ bcaseRepo.countByCase_statusAndType(2, 1, year, COUNTRY_NAME));

		// SUMMARY STATISTICS
		final String[] adata = bwmRepo.findFrontPageRates(COUNTRY_NAME).get(0);

		Integer totaldeliveries = (adata[0] == null) ? 0 : Integer.valueOf(adata[0]),
				totaldelvaginal = (adata[1] == null) ? 0 : Integer.valueOf(adata[1]),
				totaldelassisted = (adata[2] == null) ? 0 : Integer.valueOf(adata[2]),
				totaldelcaesarean = (adata[3] == null) ? 0 : Integer.valueOf(adata[3]),

				totalbirths = (adata[4] == null) ? 0 : Integer.valueOf(adata[4]),

				totalstillbirth = (adata[5] == null) ? 0 : Integer.valueOf(adata[5]),
				totalintrapartum = (adata[6] == null) ? 0 : Integer.valueOf(adata[6]),
				// totalantepartum = Integer.valueOf(adata[7]),

				totallivebirths = (adata[8] == null) ? 0 : Integer.valueOf(adata[8]),

				totalpretermbirths = (adata[9] == null) ? 0 : Integer.valueOf(adata[9]),
				totallowbirthwgt = (adata[10] == null) ? 0 : Integer.valueOf(adata[10]),

				totalneondeaths = (adata[11] == null) ? 0 : Integer.valueOf(adata[11]),

				totalneondeaths_e = (adata[12] == null) ? 0 : Integer.valueOf(adata[12]),
				totalneondeaths_l = (adata[13] == null) ? 0 : Integer.valueOf(adata[13]),

				totalmaternaldeaths = (adata[13] == null) ? 0 : Integer.valueOf(adata[14]);

		wmoindicators oindicators = new wmoindicators();
		oindicators.setWmdesc("Overall National Averages");

		oindicators.setTotalbirths(totalbirths);
		oindicators.setTotallivebirths(totallivebirths);
		oindicators.setTotalperinatals(totalneondeaths_e + totalstillbirth);
		oindicators.setTotalneonatals(totalneondeaths);
		oindicators.setTotalstillbirths(totalstillbirth);

		oindicators.setIsbr_oavg(
				totalbirths == 0 ? 0 : Math.round(((1000.0 * totalstillbirth / totalbirths)) * 10.0) / 10.0);

		oindicators.setIisbr_oavg(
				totalbirths == 0 ? 0 : Math.round(((1000.0 * totalintrapartum / totalbirths)) * 10.0) / 10.0);

		oindicators.setAisbr_oavg(oindicators.getIsbr_oavg() - oindicators.getIisbr_oavg());

		oindicators.setPiisbr_oavg(
				Math.round(((100.0 * oindicators.getIisbr_oavg() / oindicators.getIsbr_oavg())) * 10.0) / 10.0);

		oindicators.setInmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((1000.0 * totalneondeaths / totallivebirths)) * 10.0) / 10.0);

		oindicators.setIndwk1_oavg(
				totalneondeaths == 0 ? 0 : Math.round(((100.0 * totalneondeaths_e / totalneondeaths)) * 10.0) / 10.0);

		oindicators.setEinmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((1000.0 * totalneondeaths_e / totallivebirths)) * 10.0) / 10.0);

		oindicators.setLinmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((1000.0 * totalneondeaths_l / totallivebirths)) * 10.0) / 10.0);

		oindicators.setIpmr_oavg(totalbirths == 0 ? 0
				: Math.round(((1000.0 * (totalneondeaths_e + totalstillbirth) / totalbirths)) * 10.0) / 10.0);

		oindicators.setImmr_oavg(totallivebirths == 0 ? 0
				: Math.round(((100000.0 * totalmaternaldeaths / totallivebirths)) * 10.0) / 10.0);

		oindicators.setIcsr_oavg(
				totaldeliveries == 0 ? 0 : Math.round(((100.0 * totaldelcaesarean / totaldeliveries)) * 10.0) / 10.0);

		oindicators.setIadr_oavg(
				totaldeliveries == 0 ? 0 : Math.round(((100.0 * totaldelassisted / totaldeliveries)) * 10.0) / 10.0);

		oindicators.setIvdr_oavg(totaldeliveries == 0 ? 0
				: Math.round(((100.0 * totaldelvaginal / totaldeliveries) * 100) * 10.0) / 10.0);

		oindicators.setIlbwr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((100.0 * totallowbirthwgt / totallivebirths)) * 10.0) / 10.0);

		oindicators.setIptbr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((100.0 * totalpretermbirths / totallivebirths)) * 10.0) / 10.0);

		oindicators.setMdeath_osum(totalmaternaldeaths);

		model.addAttribute("oavg", oindicators);
		// SUMMARY STATISTICS - CURRENT YEAR
		final String[] cdata = bwmRepo.findFrontPageRates(year, COUNTRY_NAME).get(0);

		Integer ctotaldeliveries = Integer.valueOf(cdata[0] == null ? "0" : cdata[0]);
		Integer ctotaldelvaginal = Integer.valueOf(cdata[1] == null ? "0" : cdata[1]);
		Integer ctotaldelassisted = Integer.valueOf(cdata[2] == null ? "0" : cdata[2]);
		Integer ctotaldelcaesarean = Integer.valueOf(cdata[3] == null ? "0" : cdata[3]);
		Integer ctotalbirths = Integer.valueOf(cdata[4] == null ? "0" : cdata[4]);
		Integer ctotalstillbirth = Integer.valueOf(cdata[5] == null ? "0" : cdata[5]);
		Integer ctotalintrapartum = Integer.valueOf(cdata[6] == null ? "0" : cdata[6]);
		// Integer ctotalantepartum = Integer.valueOf(cdata[7] == null ? "0" :
		// cdata[7]);
		Integer ctotallivebirths = Integer.valueOf(cdata[8] == null ? "0" : cdata[8]);
		Integer ctotalpretermbirths = Integer.valueOf(cdata[9] == null ? "0" : cdata[9]);
		Integer ctotallowbirthwgt = Integer.valueOf(cdata[10] == null ? "0" : cdata[10]);
		Integer ctotalneondeaths = Integer.valueOf(cdata[11] == null ? "0" : cdata[11]);
		Integer ctotalneondeaths_e = Integer.valueOf(cdata[12] == null ? "0" : cdata[12]);
		Integer ctotalneondeaths_l = Integer.valueOf(cdata[13] == null ? "0" : cdata[13]);
		Integer ctotalmaternaldeaths = Integer.valueOf(cdata[14] == null ? "0" : cdata[14]);

		wmoindicators cindicators = new wmoindicators();
		cindicators.setWmdesc("National Averages for " + year);

		cindicators.setTotalbirths(ctotalbirths);
		cindicators.setTotallivebirths(ctotallivebirths);
		cindicators.setTotalperinatals(ctotalneondeaths_e + ctotalstillbirth);
		cindicators.setTotalneonatals(ctotalneondeaths);
		cindicators.setTotalstillbirths(ctotalstillbirth);

		cindicators.setIsbr_oavg(
				ctotalbirths == 0 ? 0 : Math.round(((1000.0 * ctotalstillbirth / ctotalbirths)) * 10.0) / 10.0);

		cindicators.setIisbr_oavg(
				ctotalbirths == 0 ? 0 : Math.round(((1000.0 * ctotalintrapartum / ctotalbirths)) * 10.0) / 10.0);

		cindicators.setAisbr_oavg(cindicators.getIsbr_oavg() - cindicators.getIisbr_oavg());

		cindicators.setPiisbr_oavg(
				Math.round(((100.0 * cindicators.getIisbr_oavg() / cindicators.getIsbr_oavg())) * 10.0) / 10.0);

		cindicators.setInmr_oavg(
				ctotallivebirths == 0 ? 0 : Math.round(((1000.0 * ctotalneondeaths / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setIndwk1_oavg(ctotalneondeaths == 0 ? 0
				: Math.round(((100.0 * ctotalneondeaths_e / ctotalneondeaths)) * 10.0) / 10.0);

		cindicators.setEinmr_oavg(ctotallivebirths == 0 ? 0
				: Math.round(((1000.0 * ctotalneondeaths_e / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setLinmr_oavg(ctotallivebirths == 0 ? 0
				: Math.round(((1000.0 * ctotalneondeaths_l / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setIpmr_oavg(ctotalbirths == 0 ? 0
				: Math.round(((1000.0 * (ctotalneondeaths_e + ctotalstillbirth) / ctotalbirths)) * 10.0) / 10.0);

		cindicators.setImmr_oavg(ctotallivebirths == 0 ? 0
				: Math.round(((100000.0 * ctotalmaternaldeaths / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setIcsr_oavg(ctotaldeliveries == 0 ? 0
				: Math.round(((100.0 * ctotaldelcaesarean / ctotaldeliveries)) * 10.0) / 10.0);

		cindicators.setIadr_oavg(
				ctotaldeliveries == 0 ? 0 : Math.round(((100.0 * ctotaldelassisted / ctotaldeliveries)) * 10.0) / 10.0);

		cindicators.setIvdr_oavg(
				ctotaldeliveries == 0 ? 0 : Math.round(((100.0 * ctotaldelvaginal / ctotaldeliveries)) * 10.0) / 10.0);

		cindicators.setIlbwr_oavg(
				ctotallivebirths == 0 ? 0 : Math.round(((100.0 * ctotallowbirthwgt / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setIptbr_oavg(ctotallivebirths == 0 ? 0
				: Math.round(((100.0 * ctotalpretermbirths / ctotallivebirths)) * 10.0) / 10.0);

		cindicators.setMdeath_osum(ctotalmaternaldeaths);

		model.addAttribute("cavg", cindicators);

		// RECOMMENDATIONS WITH ACTIONS
		// overall
		model.addAttribute("atotal_actions", brecRepo.count(COUNTRY_NAME));
		model.addAttribute("acompleted_actions", brecRepo.countByCompleted(COUNTRY_NAME));
		model.addAttribute("acompleted_actions_r", brecRepo.count() == 0 ? 0
				: 100 * brecRepo.countByCompleted(COUNTRY_NAME) / brecRepo.count(COUNTRY_NAME));
		model.addAttribute("apending_actions", brecRepo.countByPending(date, COUNTRY_NAME));
		model.addAttribute("apending_actions_r", brecRepo.count() == 0 ? 0
				: 100 * brecRepo.countByPending(date, COUNTRY_NAME) / brecRepo.count(COUNTRY_NAME));
		model.addAttribute("aoverdue_actions", brecRepo.countByOverdue(date, COUNTRY_NAME));
		model.addAttribute("aoverdue_actions_r", brecRepo.count() == 0 ? 0
				: 100 * brecRepo.countByOverdue(date, COUNTRY_NAME) / brecRepo.count(COUNTRY_NAME));

		/// current year
		model.addAttribute("ctotal_actions", brecRepo.count(year, COUNTRY_NAME));
		model.addAttribute("ccompleted_actions", brecRepo.countByCompleted(year, COUNTRY_NAME));
		model.addAttribute("ccompleted_actions_r", brecRepo.count(year, COUNTRY_NAME) == 0 ? 0
				: 100 * brecRepo.countByCompleted(year, COUNTRY_NAME) / brecRepo.count(year, COUNTRY_NAME));
		model.addAttribute("cpending_actions", brecRepo.countByPending(date, year, COUNTRY_NAME));
		model.addAttribute("cpending_actions_r", brecRepo.count(year, COUNTRY_NAME) == 0 ? 0
				: 100 * brecRepo.countByPending(date, year, COUNTRY_NAME) / brecRepo.count(year, COUNTRY_NAME));
		model.addAttribute("coverdue_actions", brecRepo.countByOverdue(date, year, COUNTRY_NAME));
		model.addAttribute("coverdue_actions_r", brecRepo.count(year, COUNTRY_NAME) == 0 ? 0
				: 100 * brecRepo.countByOverdue(date, year, COUNTRY_NAME) / brecRepo.count(year, COUNTRY_NAME));

		// top causes of death overall for neonatal
		List<icdpm> oneonatal = new ArrayList<icdpm>();
		for (String[] elem : baudRepo.findByTopPMCodesNeonatal(COUNTRY_NAME, PageRequest.of(0, 5))) {

			icdpm neonatal = new icdpm();
			neonatal.setPm_code(elem[0]);
			neonatal.setPm_desc(icdRepo.findDescriptionOfICDPMNeonatal(elem[0]).get());
			neonatal.setPm_tsum(elem[1]);

			oneonatal.add(neonatal);
		}
		model.addAttribute("top_oneonatal", oneonatal);

		// top causes of death for neonatal for current year
		List<icdpm> cneonatal = new ArrayList<icdpm>();
		for (String[] elem : baudRepo.findByTopPMCodesNeonatal(year, COUNTRY_NAME, PageRequest.of(0, 5))) {

			icdpm neonatal = new icdpm();
			neonatal.setPm_code(elem[0]);
			neonatal.setPm_desc(icdRepo.findDescriptionOfICDPMNeonatal(elem[0]).get());
			neonatal.setPm_tsum(elem[1]);

			cneonatal.add(neonatal);
		}
		model.addAttribute("top_cneonatal", cneonatal);

		// top causes of death overall for stillbirths
		List<icdpm> ostillbirth = new ArrayList<icdpm>();
		for (String[] elem : baudRepo.findByTopPMCodesStillBirth(COUNTRY_NAME, PageRequest.of(0, 5))) {

			icdpm stillbirth = new icdpm();
			stillbirth.setPm_code(elem[0]);
			final String pm_desc = icdRepo.findDescriptionOfICDPMIntrapartum(elem[0]).isPresent()
					? icdRepo.findDescriptionOfICDPMIntrapartum(elem[0]).get()
					: icdRepo.findDescriptionOfICDPMAntepartum(elem[0]).get();
			stillbirth.setPm_desc(pm_desc);
			stillbirth.setPm_tsum(elem[1]);

			ostillbirth.add(stillbirth);
		}
		model.addAttribute("top_ostillbirth", ostillbirth);

		// top causes of death for stillbirths for current year
		List<icdpm> cstillbirth = new ArrayList<icdpm>();
		for (String[] elem : baudRepo.findByTopPMCodesStillBirth(year, COUNTRY_NAME, PageRequest.of(0, 5))) {

			icdpm stillbirth = new icdpm();
			stillbirth.setPm_code(elem[0]);
			final String pm_desc = icdRepo.findDescriptionOfICDPMIntrapartum(elem[0]).isPresent()
					? icdRepo.findDescriptionOfICDPMIntrapartum(elem[0]).get()
					: icdRepo.findDescriptionOfICDPMAntepartum(elem[0]).get();
			stillbirth.setPm_desc(pm_desc);
			stillbirth.setPm_tsum(elem[1]);

			cstillbirth.add(stillbirth);
		}
		model.addAttribute("top_cstillbirth", cstillbirth);

	}

	private void nationalIndicators(Model model, wmsearch search, facility_table facility) {

		final String COUNTRY_NAME = facility.getDistrict().getRegion().getCountry().getCountry_name();

		model.addAttribute("national", "active");
		model.addAttribute("national_level", COUNTRY_NAME);
		model.addAttribute("page", "active");
		model.addAttribute("country_name", COUNTRY_NAME);

		Integer startYM = ((search.getWm_startyear() % 2000) * 12) + search.getWm_startmonth();
		Integer endYM = ((search.getWm_endyear() % 2000) * 12) + search.getWm_endmonth();

		Double totaldeliveries = 0.0, totaldelvaginal = 0.0, totaldelassisted = 0.0, totaldelcaesarean = 0.0,

				totalbirths = 0.0, totalstillbirth = 0.0, totalintrapartum = 0.0, totalantepartum = 0.0,

				totallivebirths = 0.0, totalpretermbirths = 0.0, totallowbirthwgt = 0.0,

				totalneondeaths = 0.0, totalneondeaths_e = 0.0, totalneondeaths_l = 0.0;

		Integer totalmaternaldeaths = 0;

		List<String[]> data = bwmRepo.findAllRates(startYM, endYM, COUNTRY_NAME);

		final String[] yearmonth = new String[data.size()];
		final Double[] isbr_array = new Double[data.size()];
		final Double[] iisbr_array = new Double[data.size()];
		final Double[] aisbr_array = new Double[data.size()];
		final Double[] piisbr_array = new Double[data.size()];
		final Double[] einmr_array = new Double[data.size()];
		final Double[] linmr_array = new Double[data.size()];
		final Double[] ipmr_array = new Double[data.size()];
		final Double[] inmr_array = new Double[data.size()];
		final Double[] immr_array = new Double[data.size()];
		final Double[] icsr_array = new Double[data.size()];
		final Double[] iadr_array = new Double[data.size()];
		final Double[] ivdr_array = new Double[data.size()];
		final Double[] ilbwr_array = new Double[data.size()];
		final Double[] iptbr_array = new Double[data.size()];
		final Double[] indwk1_array = new Double[data.size()];
		final Integer[] mdeath_array = new Integer[data.size()];

		List<wmindicators> indicators = new ArrayList<>();
		int arrayIndex = 0;
		for (String[] elem : data)// startYM, endYM
		{
			wmindicators i = new wmindicators();
			i.setWmdesc(elem[2]);
			yearmonth[arrayIndex] = i.getWmdesc();

			i.setIsbr(
					(Double.valueOf(elem[7]) == 0.0) ? 0 : (Double.valueOf(elem[8]) / Double.valueOf(elem[7])) * 1000);
			isbr_array[arrayIndex] = Math.round(i.getIsbr() * 10.0) / 10.0;

			i.setIisbr(
					(Double.valueOf(elem[7]) == 0.0) ? 0 : (Double.valueOf(elem[9]) / Double.valueOf(elem[7])) * 1000);
			iisbr_array[arrayIndex] = Math.round(i.getIisbr() * 10.0) / 10.0;

			i.setAisbr(i.getIsbr() - i.getIisbr());
			aisbr_array[arrayIndex] = Math.round(i.getAisbr() * 10.0) / 10.0;

			i.setPiisbr((i.getIsbr() == 0.0) ? 0 : (i.getIisbr() / i.getIsbr()) * 100);
			piisbr_array[arrayIndex] = Math.round(i.getPiisbr() * 10.0) / 10.0;

			i.setInmr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[14]) / Double.valueOf(elem[11])) * 1000);
			inmr_array[arrayIndex] = Math.round(i.getInmr() * 10.0) / 10.0;

			i.setIndwk1((Double.valueOf(elem[14]) == 0.0) ? 0
					: (Double.valueOf(elem[15]) / Double.valueOf(elem[14])) * 100);
			indwk1_array[arrayIndex] = Math.round(i.getIndwk1() * 10.0) / 10.0;

			i.setEinmr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[15]) / Double.valueOf(elem[11])) * 1000);
			einmr_array[arrayIndex] = Math.round(i.getEinmr() * 10.0) / 10.0;

			i.setLinmr(i.getInmr() - i.getEinmr());
			linmr_array[arrayIndex] = Math.round(i.getLinmr() * 10.0) / 10.0;

			i.setIpmr((Double.valueOf(elem[11]) == 0.0) ? 0
					: ((Double.valueOf(elem[15]) + Double.valueOf(elem[8])) / Double.valueOf(elem[11])) * 1000);
			ipmr_array[arrayIndex] = Math.round(i.getIpmr() * 10.0) / 10.0;

			i.setImmr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[17]) / Double.valueOf(elem[11])) * 100000);
			immr_array[arrayIndex] = Math.round(i.getImmr() * 10.0) / 10.0;

			i.setIcsr((Double.valueOf(elem[3]) == 0.0) ? 0 : (Double.valueOf(elem[6]) / Double.valueOf(elem[3])) * 100);
			icsr_array[arrayIndex] = Math.round(i.getIcsr() * 10.0) / 10.0;

			i.setIadr((Double.valueOf(elem[3]) == 0.0) ? 0 : (Double.valueOf(elem[5]) / Double.valueOf(elem[3])) * 100);
			iadr_array[arrayIndex] = Math.round(i.getIadr() * 10.0) / 10.0;

			i.setIvdr((Double.valueOf(elem[3]) == 0.0) ? 0 : (Double.valueOf(elem[4]) / Double.valueOf(elem[3])) * 100);
			ivdr_array[arrayIndex] = Math.round(i.getIvdr() * 10.0) / 10.0;

			i.setIlbwr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[13]) / Double.valueOf(elem[11])) * 100);
			ilbwr_array[arrayIndex] = Math.round(i.getIlbwr() * 10.0) / 10.0;

			i.setIptbr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[12]) / Double.valueOf(elem[11])) * 100);
			iptbr_array[arrayIndex] = Math.round(i.getIptbr() * 10.0) / 10.0;

			i.setMdeath(Integer.valueOf(elem[17]));
			mdeath_array[arrayIndex] = i.getMdeath();

			totaldeliveries += Double.valueOf(elem[3]);
			totaldelvaginal += Double.valueOf(elem[4]);
			totaldelassisted += Double.valueOf(elem[5]);
			totaldelcaesarean += Double.valueOf(elem[6]);

			totalbirths += Double.valueOf(elem[7]);
			totalstillbirth += Double.valueOf(elem[8]);
			totalintrapartum += Double.valueOf(elem[9]);
			totalantepartum += Double.valueOf(elem[10]);

			totallivebirths += Double.valueOf(elem[11]);
			totalpretermbirths += Double.valueOf(elem[12]);
			totallowbirthwgt += Double.valueOf(elem[13]);

			totalneondeaths += Double.valueOf(elem[14]);
			totalneondeaths_e += Double.valueOf(elem[15]);
			totalneondeaths_l += Double.valueOf(elem[16]);

			totalmaternaldeaths += Integer.valueOf(elem[17]);

			indicators.add(i);

			arrayIndex++;

		}
		model.addAttribute("items", indicators);

		// overall averages of data that is pulled
		wmoindicators oindicators = new wmoindicators();
		oindicators.setWmdesc("Overall");

		oindicators.setIsbr_oavg(
				totalbirths == 0 ? 0 : Math.round(((totalstillbirth / totalbirths) * 1000) * 10.0) / 10.0);

		oindicators.setIisbr_oavg(
				totalbirths == 0 ? 0 : Math.round(((totalintrapartum / totalbirths) * 1000) * 10.0) / 10.0);

		oindicators.setAisbr_oavg(oindicators.getIsbr_oavg() - oindicators.getIisbr_oavg());

		oindicators.setPiisbr_oavg(
				Math.round(((oindicators.getIisbr_oavg() / oindicators.getIsbr_oavg()) * 100) * 10.0) / 10.0);

		oindicators.setInmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((totalneondeaths / totallivebirths) * 1000) * 10.0) / 10.0);

		oindicators.setIndwk1_oavg(
				totalneondeaths == 0 ? 0 : Math.round(((totalneondeaths_e / totalneondeaths) * 100) * 10.0) / 10.0);

		oindicators.setEinmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((totalneondeaths_e / totallivebirths) * 1000) * 10.0) / 10.0);

		oindicators.setLinmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((totalneondeaths_l / totallivebirths) * 1000) * 10.0) / 10.0);

		oindicators.setIpmr_oavg(totalbirths == 0 ? 0
				: Math.round((((totalneondeaths_e + totalstillbirth) / totalbirths) * 1000) * 10.0) / 10.0);

		oindicators.setImmr_oavg(totallivebirths == 0 ? 0
				: Math.round(((totalmaternaldeaths / totallivebirths) * 100000) * 10.0) / 10.0);

		oindicators.setIcsr_oavg(
				totaldeliveries == 0 ? 0 : Math.round(((totaldelcaesarean / totaldeliveries) * 100) * 10.0) / 10.0);

		oindicators.setIadr_oavg(
				totaldeliveries == 0 ? 0 : Math.round(((totaldelassisted / totaldeliveries) * 100) * 10.0) / 10.0);

		oindicators.setIvdr_oavg(
				totaldeliveries == 0 ? 0 : Math.round(((totaldelvaginal / totaldeliveries) * 100) * 10.0) / 10.0);

		oindicators.setIlbwr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((totallowbirthwgt / totallivebirths) * 100) * 10.0) / 10.0);

		oindicators.setIptbr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((totalpretermbirths / totallivebirths) * 100) * 10.0) / 10.0);

		oindicators.setMdeath_osum(totalmaternaldeaths);

		model.addAttribute("oavg", oindicators);

		model.addAttribute("yearmonth_array", yearmonth);
		model.addAttribute("isbr_array", isbr_array);
		model.addAttribute("iisbr_array", iisbr_array);
		model.addAttribute("aisbr_array", aisbr_array);
		model.addAttribute("piisbr_array", piisbr_array);
		model.addAttribute("einmr_array", einmr_array);
		model.addAttribute("linmr_array", linmr_array);
		model.addAttribute("ipmr_array", ipmr_array);
		model.addAttribute("inmr_array", inmr_array);
		model.addAttribute("immr_array", immr_array);
		model.addAttribute("icsr_array", icsr_array);
		model.addAttribute("iadr_array", iadr_array);
		model.addAttribute("ivdr_array", ivdr_array);
		model.addAttribute("ilbwr_array", ilbwr_array);
		model.addAttribute("iptbr_array", iptbr_array);
		model.addAttribute("indwk1_array", indwk1_array);
		model.addAttribute("mdeath_array", mdeath_array);

	}

	private void regionalIndicators(Model model, wmsearch search, facility_table facility, String region_name) {

		final String COUNTRY_NAME = facility.getDistrict().getRegion().getCountry().getCountry_name();
		final String REGION_NAME = (region_name != null) ? region_name
				: facility.getDistrict().getRegion().getRegion_name();

		model.addAttribute((region_name != null) ? "national" : "regional", "active");
		if (region_name != null) {
			model.addAttribute("national_regional", region_name);
		}
		model.addAttribute("page", "active");
		model.addAttribute("country_name", COUNTRY_NAME);
		model.addAttribute("region_name", REGION_NAME);

		Integer startYM = ((search.getWm_startyear() % 2000) * 12) + search.getWm_startmonth();
		Integer endYM = ((search.getWm_endyear() % 2000) * 12) + search.getWm_endmonth();

		Double totaldeliveries = 0.0, totaldelvaginal = 0.0, totaldelassisted = 0.0, totaldelcaesarean = 0.0,

				totalbirths = 0.0, totalstillbirth = 0.0, totalintrapartum = 0.0, totalantepartum = 0.0,

				totallivebirths = 0.0, totalpretermbirths = 0.0, totallowbirthwgt = 0.0,

				totalneondeaths = 0.0, totalneondeaths_e = 0.0, totalneondeaths_l = 0.0;

		Integer totalmaternaldeaths = 0;

		List<String[]> data = bwmRepo.findAllRates(startYM, endYM, COUNTRY_NAME, REGION_NAME);

		final String[] yearmonth = new String[data.size()];
		final Double[] isbr_array = new Double[data.size()];
		final Double[] iisbr_array = new Double[data.size()];
		final Double[] aisbr_array = new Double[data.size()];
		final Double[] piisbr_array = new Double[data.size()];
		final Double[] einmr_array = new Double[data.size()];
		final Double[] linmr_array = new Double[data.size()];
		final Double[] ipmr_array = new Double[data.size()];
		final Double[] inmr_array = new Double[data.size()];
		final Double[] immr_array = new Double[data.size()];
		final Double[] icsr_array = new Double[data.size()];
		final Double[] iadr_array = new Double[data.size()];
		final Double[] ivdr_array = new Double[data.size()];
		final Double[] ilbwr_array = new Double[data.size()];
		final Double[] iptbr_array = new Double[data.size()];
		final Double[] indwk1_array = new Double[data.size()];
		final Integer[] mdeath_array = new Integer[data.size()];

		List<wmindicators> indicators = new ArrayList<>();
		int arrayIndex = 0;
		for (String[] elem : data)// startYM, endYM
		{
			wmindicators i = new wmindicators();
			i.setWmdesc(elem[2]);
			yearmonth[arrayIndex] = i.getWmdesc();

			i.setIsbr(
					(Double.valueOf(elem[7]) == 0.0) ? 0 : (Double.valueOf(elem[8]) / Double.valueOf(elem[7])) * 1000);
			isbr_array[arrayIndex] = Math.round(i.getIsbr() * 10.0) / 10.0;

			i.setIisbr(
					(Double.valueOf(elem[7]) == 0.0) ? 0 : (Double.valueOf(elem[9]) / Double.valueOf(elem[7])) * 1000);
			iisbr_array[arrayIndex] = Math.round(i.getIisbr() * 10.0) / 10.0;

			i.setAisbr(i.getIsbr() - i.getIisbr());
			aisbr_array[arrayIndex] = Math.round(i.getAisbr() * 10.0) / 10.0;

			i.setPiisbr((i.getIsbr() == 0.0) ? 0 : (i.getIisbr() / i.getIsbr()) * 100);
			piisbr_array[arrayIndex] = Math.round(i.getPiisbr() * 10.0) / 10.0;

			i.setInmr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[14]) / Double.valueOf(elem[11])) * 1000);
			inmr_array[arrayIndex] = Math.round(i.getInmr() * 10.0) / 10.0;

			i.setIndwk1((Double.valueOf(elem[14]) == 0.0) ? 0
					: (Double.valueOf(elem[15]) / Double.valueOf(elem[14])) * 100);
			indwk1_array[arrayIndex] = Math.round(i.getIndwk1() * 10.0) / 10.0;

			i.setEinmr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[15]) / Double.valueOf(elem[11])) * 1000);
			einmr_array[arrayIndex] = Math.round(i.getEinmr() * 10.0) / 10.0;

			i.setLinmr(i.getInmr() - i.getEinmr());
			linmr_array[arrayIndex] = Math.round(i.getLinmr() * 10.0) / 10.0;

			i.setIpmr((Double.valueOf(elem[11]) == 0.0) ? 0
					: ((Double.valueOf(elem[15]) + Double.valueOf(elem[8])) / Double.valueOf(elem[11])) * 1000);
			ipmr_array[arrayIndex] = Math.round(i.getIpmr() * 10.0) / 10.0;

			i.setImmr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[17]) / Double.valueOf(elem[11])) * 100000);
			immr_array[arrayIndex] = Math.round(i.getImmr() * 10.0) / 10.0;

			i.setIcsr((Double.valueOf(elem[3]) == 0.0) ? 0 : (Double.valueOf(elem[6]) / Double.valueOf(elem[3])) * 100);
			icsr_array[arrayIndex] = Math.round(i.getIcsr() * 10.0) / 10.0;

			i.setIadr((Double.valueOf(elem[3]) == 0.0) ? 0 : (Double.valueOf(elem[5]) / Double.valueOf(elem[3])) * 100);
			iadr_array[arrayIndex] = Math.round(i.getIadr() * 10.0) / 10.0;

			i.setIvdr((Double.valueOf(elem[3]) == 0.0) ? 0 : (Double.valueOf(elem[4]) / Double.valueOf(elem[3])) * 100);
			ivdr_array[arrayIndex] = Math.round(i.getIvdr() * 10.0) / 10.0;

			i.setIlbwr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[13]) / Double.valueOf(elem[11])) * 100);
			ilbwr_array[arrayIndex] = Math.round(i.getIlbwr() * 10.0) / 10.0;

			i.setIptbr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[12]) / Double.valueOf(elem[11])) * 100);
			iptbr_array[arrayIndex] = Math.round(i.getIptbr() * 10.0) / 10.0;

			i.setMdeath(Integer.valueOf(elem[17]));
			mdeath_array[arrayIndex] = i.getMdeath();

			totaldeliveries += Double.valueOf(elem[3]);
			totaldelvaginal += Double.valueOf(elem[4]);
			totaldelassisted += Double.valueOf(elem[5]);
			totaldelcaesarean += Double.valueOf(elem[6]);

			totalbirths += Double.valueOf(elem[7]);
			totalstillbirth += Double.valueOf(elem[8]);
			totalintrapartum += Double.valueOf(elem[9]);
			totalantepartum += Double.valueOf(elem[10]);

			totallivebirths += Double.valueOf(elem[11]);
			totalpretermbirths += Double.valueOf(elem[12]);
			totallowbirthwgt += Double.valueOf(elem[13]);

			totalneondeaths += Double.valueOf(elem[14]);
			totalneondeaths_e += Double.valueOf(elem[15]);
			totalneondeaths_l += Double.valueOf(elem[16]);

			totalmaternaldeaths += Integer.valueOf(elem[17]);

			indicators.add(i);

			arrayIndex++;

		}
		model.addAttribute("items", indicators);

		// overall averages of data that is pulled
		wmoindicators oindicators = new wmoindicators();
		oindicators.setWmdesc("Overall");

		oindicators.setIsbr_oavg(
				totalbirths == 0 ? 0 : Math.round(((totalstillbirth / totalbirths) * 1000) * 10.0) / 10.0);

		oindicators.setIisbr_oavg(
				totalbirths == 0 ? 0 : Math.round(((totalintrapartum / totalbirths) * 1000) * 10.0) / 10.0);

		oindicators.setAisbr_oavg(oindicators.getIsbr_oavg() - oindicators.getIisbr_oavg());

		oindicators.setPiisbr_oavg(
				Math.round(((oindicators.getIisbr_oavg() / oindicators.getIsbr_oavg()) * 100) * 10.0) / 10.0);

		oindicators.setInmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((totalneondeaths / totallivebirths) * 1000) * 10.0) / 10.0);

		oindicators.setIndwk1_oavg(
				totalneondeaths == 0 ? 0 : Math.round(((totalneondeaths_e / totalneondeaths) * 100) * 10.0) / 10.0);

		oindicators.setEinmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((totalneondeaths_e / totallivebirths) * 1000) * 10.0) / 10.0);

		oindicators.setLinmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((totalneondeaths_l / totallivebirths) * 1000) * 10.0) / 10.0);

		oindicators.setIpmr_oavg(totalbirths == 0 ? 0
				: Math.round((((totalneondeaths_e + totalstillbirth) / totalbirths) * 1000) * 10.0) / 10.0);

		oindicators.setImmr_oavg(totallivebirths == 0 ? 0
				: Math.round(((totalmaternaldeaths / totallivebirths) * 100000) * 10.0) / 10.0);

		oindicators.setIcsr_oavg(
				totaldeliveries == 0 ? 0 : Math.round(((totaldelcaesarean / totaldeliveries) * 100) * 10.0) / 10.0);

		oindicators.setIadr_oavg(
				totaldeliveries == 0 ? 0 : Math.round(((totaldelassisted / totaldeliveries) * 100) * 10.0) / 10.0);

		oindicators.setIvdr_oavg(
				totaldeliveries == 0 ? 0 : Math.round(((totaldelvaginal / totaldeliveries) * 100) * 10.0) / 10.0);

		oindicators.setIlbwr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((totallowbirthwgt / totallivebirths) * 100) * 10.0) / 10.0);

		oindicators.setIptbr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((totalpretermbirths / totallivebirths) * 100) * 10.0) / 10.0);

		oindicators.setMdeath_osum(totalmaternaldeaths);

		model.addAttribute("oavg", oindicators);

		model.addAttribute("yearmonth_array", yearmonth);
		model.addAttribute("isbr_array", isbr_array);
		model.addAttribute("iisbr_array", iisbr_array);
		model.addAttribute("aisbr_array", aisbr_array);
		model.addAttribute("piisbr_array", piisbr_array);
		model.addAttribute("einmr_array", einmr_array);
		model.addAttribute("linmr_array", linmr_array);
		model.addAttribute("ipmr_array", ipmr_array);
		model.addAttribute("inmr_array", inmr_array);
		model.addAttribute("immr_array", immr_array);
		model.addAttribute("icsr_array", icsr_array);
		model.addAttribute("iadr_array", iadr_array);
		model.addAttribute("ivdr_array", ivdr_array);
		model.addAttribute("ilbwr_array", ilbwr_array);
		model.addAttribute("iptbr_array", iptbr_array);
		model.addAttribute("indwk1_array", indwk1_array);
		model.addAttribute("mdeath_array", mdeath_array);

	}

	private void districtIndicators(Model model, wmsearch search, facility_table facility) {

		final String COUNTRY_NAME = facility.getDistrict().getRegion().getCountry().getCountry_name();
		final String REGION_NAME = facility.getDistrict().getRegion().getRegion_name();
		final String DISTRICT_NAME = facility.getDistrict().getDistrict_name();

		model.addAttribute("district", "active");
		model.addAttribute("page", "active");
		model.addAttribute("district_name", DISTRICT_NAME);

		Integer startYM = ((search.getWm_startyear() % 2000) * 12) + search.getWm_startmonth();
		Integer endYM = ((search.getWm_endyear() % 2000) * 12) + search.getWm_endmonth();

		Double totaldeliveries = 0.0, totaldelvaginal = 0.0, totaldelassisted = 0.0, totaldelcaesarean = 0.0,

				totalbirths = 0.0, totalstillbirth = 0.0, totalintrapartum = 0.0, totalantepartum = 0.0,

				totallivebirths = 0.0, totalpretermbirths = 0.0, totallowbirthwgt = 0.0,

				totalneondeaths = 0.0, totalneondeaths_e = 0.0, totalneondeaths_l = 0.0;

		Integer totalmaternaldeaths = 0;

		List<String[]> data = bwmRepo.findAllRates(startYM, endYM, COUNTRY_NAME, REGION_NAME, DISTRICT_NAME);

		final String[] yearmonth = new String[data.size()];
		final Double[] isbr_array = new Double[data.size()];
		final Double[] iisbr_array = new Double[data.size()];
		final Double[] aisbr_array = new Double[data.size()];
		final Double[] piisbr_array = new Double[data.size()];
		final Double[] einmr_array = new Double[data.size()];
		final Double[] linmr_array = new Double[data.size()];
		final Double[] ipmr_array = new Double[data.size()];
		final Double[] inmr_array = new Double[data.size()];
		final Double[] immr_array = new Double[data.size()];
		final Double[] icsr_array = new Double[data.size()];
		final Double[] iadr_array = new Double[data.size()];
		final Double[] ivdr_array = new Double[data.size()];
		final Double[] ilbwr_array = new Double[data.size()];
		final Double[] iptbr_array = new Double[data.size()];
		final Double[] indwk1_array = new Double[data.size()];
		final Integer[] mdeath_array = new Integer[data.size()];

		List<wmindicators> indicators = new ArrayList<>();
		int arrayIndex = 0;
		for (String[] elem : data)// startYM, endYM
		{
			wmindicators i = new wmindicators();
			i.setWmdesc(elem[2]);
			yearmonth[arrayIndex] = i.getWmdesc();

			i.setIsbr(
					(Double.valueOf(elem[7]) == 0.0) ? 0 : (Double.valueOf(elem[8]) / Double.valueOf(elem[7])) * 1000);
			isbr_array[arrayIndex] = Math.round(i.getIsbr() * 10.0) / 10.0;

			i.setIisbr(
					(Double.valueOf(elem[7]) == 0.0) ? 0 : (Double.valueOf(elem[9]) / Double.valueOf(elem[7])) * 1000);
			iisbr_array[arrayIndex] = Math.round(i.getIisbr() * 10.0) / 10.0;

			i.setAisbr(i.getIsbr() - i.getIisbr());
			aisbr_array[arrayIndex] = Math.round(i.getAisbr() * 10.0) / 10.0;

			i.setPiisbr((i.getIsbr() == 0.0) ? 0 : (i.getIisbr() / i.getIsbr()) * 100);
			piisbr_array[arrayIndex] = Math.round(i.getPiisbr() * 10.0) / 10.0;

			i.setInmr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[14]) / Double.valueOf(elem[11])) * 1000);
			inmr_array[arrayIndex] = Math.round(i.getInmr() * 10.0) / 10.0;

			i.setIndwk1((Double.valueOf(elem[14]) == 0.0) ? 0
					: (Double.valueOf(elem[15]) / Double.valueOf(elem[14])) * 100);
			indwk1_array[arrayIndex] = Math.round(i.getIndwk1() * 10.0) / 10.0;

			i.setEinmr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[15]) / Double.valueOf(elem[11])) * 1000);
			einmr_array[arrayIndex] = Math.round(i.getEinmr() * 10.0) / 10.0;

			i.setLinmr(i.getInmr() - i.getEinmr());
			linmr_array[arrayIndex] = Math.round(i.getLinmr() * 10.0) / 10.0;

			i.setIpmr((Double.valueOf(elem[11]) == 0.0) ? 0
					: ((Double.valueOf(elem[15]) + Double.valueOf(elem[8])) / Double.valueOf(elem[11])) * 1000);
			ipmr_array[arrayIndex] = Math.round(i.getIpmr() * 10.0) / 10.0;

			i.setImmr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[17]) / Double.valueOf(elem[11])) * 100000);
			immr_array[arrayIndex] = Math.round(i.getImmr() * 10.0) / 10.0;

			i.setIcsr((Double.valueOf(elem[3]) == 0.0) ? 0 : (Double.valueOf(elem[6]) / Double.valueOf(elem[3])) * 100);
			icsr_array[arrayIndex] = Math.round(i.getIcsr() * 10.0) / 10.0;

			i.setIadr((Double.valueOf(elem[3]) == 0.0) ? 0 : (Double.valueOf(elem[5]) / Double.valueOf(elem[3])) * 100);
			iadr_array[arrayIndex] = Math.round(i.getIadr() * 10.0) / 10.0;

			i.setIvdr((Double.valueOf(elem[3]) == 0.0) ? 0 : (Double.valueOf(elem[4]) / Double.valueOf(elem[3])) * 100);
			ivdr_array[arrayIndex] = Math.round(i.getIvdr() * 10.0) / 10.0;

			i.setIlbwr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[13]) / Double.valueOf(elem[11])) * 100);
			ilbwr_array[arrayIndex] = Math.round(i.getIlbwr() * 10.0) / 10.0;

			i.setIptbr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[12]) / Double.valueOf(elem[11])) * 100);
			iptbr_array[arrayIndex] = Math.round(i.getIptbr() * 10.0) / 10.0;

			i.setMdeath(Integer.valueOf(elem[17]));
			mdeath_array[arrayIndex] = i.getMdeath();

			totaldeliveries += Double.valueOf(elem[3]);
			totaldelvaginal += Double.valueOf(elem[4]);
			totaldelassisted += Double.valueOf(elem[5]);
			totaldelcaesarean += Double.valueOf(elem[6]);

			totalbirths += Double.valueOf(elem[7]);
			totalstillbirth += Double.valueOf(elem[8]);
			totalintrapartum += Double.valueOf(elem[9]);
			totalantepartum += Double.valueOf(elem[10]);

			totallivebirths += Double.valueOf(elem[11]);
			totalpretermbirths += Double.valueOf(elem[12]);
			totallowbirthwgt += Double.valueOf(elem[13]);

			totalneondeaths += Double.valueOf(elem[14]);
			totalneondeaths_e += Double.valueOf(elem[15]);
			totalneondeaths_l += Double.valueOf(elem[16]);

			totalmaternaldeaths += Integer.valueOf(elem[17]);

			indicators.add(i);

			arrayIndex++;

		}
		model.addAttribute("items", indicators);

		// overall averages of data that is pulled
		wmoindicators oindicators = new wmoindicators();
		oindicators.setWmdesc("Overall");

		oindicators.setIsbr_oavg(
				totalbirths == 0 ? 0 : Math.round(((totalstillbirth / totalbirths) * 1000) * 10.0) / 10.0);

		oindicators.setIisbr_oavg(
				totalbirths == 0 ? 0 : Math.round(((totalintrapartum / totalbirths) * 1000) * 10.0) / 10.0);

		oindicators.setAisbr_oavg(oindicators.getIsbr_oavg() - oindicators.getIisbr_oavg());

		oindicators.setPiisbr_oavg(
				Math.round(((oindicators.getIisbr_oavg() / oindicators.getIsbr_oavg()) * 100) * 10.0) / 10.0);

		oindicators.setInmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((totalneondeaths / totallivebirths) * 1000) * 10.0) / 10.0);

		oindicators.setIndwk1_oavg(
				totalneondeaths == 0 ? 0 : Math.round(((totalneondeaths_e / totalneondeaths) * 100) * 10.0) / 10.0);

		oindicators.setEinmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((totalneondeaths_e / totallivebirths) * 1000) * 10.0) / 10.0);

		oindicators.setLinmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((totalneondeaths_l / totallivebirths) * 1000) * 10.0) / 10.0);

		oindicators.setIpmr_oavg(totalbirths == 0 ? 0
				: Math.round((((totalneondeaths_e + totalstillbirth) / totalbirths) * 1000) * 10.0) / 10.0);

		oindicators.setImmr_oavg(totallivebirths == 0 ? 0
				: Math.round(((totalmaternaldeaths / totallivebirths) * 100000) * 10.0) / 10.0);

		oindicators.setIcsr_oavg(
				totaldeliveries == 0 ? 0 : Math.round(((totaldelcaesarean / totaldeliveries) * 100) * 10.0) / 10.0);

		oindicators.setIadr_oavg(
				totaldeliveries == 0 ? 0 : Math.round(((totaldelassisted / totaldeliveries) * 100) * 10.0) / 10.0);

		oindicators.setIvdr_oavg(
				totaldeliveries == 0 ? 0 : Math.round(((totaldelvaginal / totaldeliveries) * 100) * 10.0) / 10.0);

		oindicators.setIlbwr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((totallowbirthwgt / totallivebirths) * 100) * 10.0) / 10.0);

		oindicators.setIptbr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((totalpretermbirths / totallivebirths) * 100) * 10.0) / 10.0);

		oindicators.setMdeath_osum(totalmaternaldeaths);

		model.addAttribute("oavg", oindicators);

		model.addAttribute("yearmonth_array", yearmonth);
		model.addAttribute("isbr_array", isbr_array);
		model.addAttribute("iisbr_array", iisbr_array);
		model.addAttribute("aisbr_array", aisbr_array);
		model.addAttribute("piisbr_array", piisbr_array);
		model.addAttribute("einmr_array", einmr_array);
		model.addAttribute("linmr_array", linmr_array);
		model.addAttribute("ipmr_array", ipmr_array);
		model.addAttribute("inmr_array", inmr_array);
		model.addAttribute("immr_array", immr_array);
		model.addAttribute("icsr_array", icsr_array);
		model.addAttribute("iadr_array", iadr_array);
		model.addAttribute("ivdr_array", ivdr_array);
		model.addAttribute("ilbwr_array", ilbwr_array);
		model.addAttribute("iptbr_array", iptbr_array);
		model.addAttribute("indwk1_array", indwk1_array);
		model.addAttribute("mdeath_array", mdeath_array);

	}

	@ModelAttribute("wmyear_options")
	public Map<Integer, String> wmyearOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Year");
		for (Integer elem : bwmRepo.findYears()) {
			map.put(elem, "" + elem);
		}

		return map;
	}

	@ModelAttribute("wmmonth_options")
	public Map<Integer, String> wmmonthOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Month");
		for (Object[] elem : bwmRepo.findMonths()) {
			map.put((Integer) elem[0], "" + elem[1]);
		}

		return map;
	}

}
// end class