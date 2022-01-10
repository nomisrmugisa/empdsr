package org.pdsr.controller;

import java.security.Principal;
import java.util.Calendar;

import org.pdsr.pojos.wmoindicators;
import org.pdsr.repo.CaseRepository;
import org.pdsr.repo.WeeklyMonitoringTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({ "/", "/home" })
public class HomeController {

	@Autowired
	private CaseRepository caseRepo;

	@Autowired
	private WeeklyMonitoringTableRepository wmRepo;

	@GetMapping("")
	public String homee(Principal principal, Model model) {
		final int year = Calendar.getInstance().get(Calendar.YEAR);

		model.addAttribute("cyear", year);

		model.addAttribute("entered_nd", caseRepo.countByCase_death(2));
		model.addAttribute("entered_sb", caseRepo.countByCase_death(1));

		model.addAttribute("c_entered_nd", caseRepo.countByCase_death(2, year));
		model.addAttribute("c_entered_sb", caseRepo.countByCase_death(1, year));

		model.addAttribute("submitted_ndn", caseRepo.countByCase_statusAndType(1, 2));
		model.addAttribute("submitted_sbn", caseRepo.countByCase_statusAndType(1, 1));

		model.addAttribute("c_submitted_ndn", caseRepo.countByCase_statusAndType(1, 2, year));
		model.addAttribute("c_submitted_sbn", caseRepo.countByCase_statusAndType(1, 1, year));

		model.addAttribute("submitted_ndp", caseRepo.countByCase_death(2) == 0 ? 0
				: 100 * caseRepo.countByCase_statusAndType(1, 2) / caseRepo.countByCase_death(2));
		model.addAttribute("submitted_sbp", caseRepo.countByCase_death(1) == 0 ? 0
				: 100 * caseRepo.countByCase_statusAndType(1, 1) / caseRepo.countByCase_death(1));

		model.addAttribute("c_submitted_ndp", caseRepo.countByCase_death(2, year) == 0 ? 0
				: 100 * caseRepo.countByCase_statusAndType(1, 2, year) / caseRepo.countByCase_death(2, year));
		model.addAttribute("c_submitted_sbp", caseRepo.countByCase_death(1, year) == 0 ? 0
				: 100 * caseRepo.countByCase_statusAndType(1, 1, year) / caseRepo.countByCase_death(1, year));

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

		Integer totaldeliveries = Integer.valueOf(adata[0]), totaldelvaginal = Integer.valueOf(adata[1]),
				totaldelassisted = Integer.valueOf(adata[2]), totaldelcaesarean = Integer.valueOf(adata[3]),

				totalbirths = Integer.valueOf(adata[4]),

				totalstillbirth = Integer.valueOf(adata[5]), totalintrapartum = Integer.valueOf(adata[6]),
				// totalantepartum = Integer.valueOf(adata[7]),

				totallivebirths = Integer.valueOf(adata[8]),

				totalpretermbirths = Integer.valueOf(adata[9]), totallowbirthwgt = Integer.valueOf(adata[10]),

				totalneondeaths = Integer.valueOf(adata[11]),

				totalneondeaths_e = Integer.valueOf(adata[12]), totalneondeaths_l = Integer.valueOf(adata[13]),

				totalmaternaldeaths = Integer.valueOf(adata[14]);

		wmoindicators oindicators = new wmoindicators();
		oindicators.setWmdesc("Overall Averages");

		oindicators.setTotalbirths(totalbirths);
		oindicators.setTotallivebirths(totallivebirths);
		oindicators.setTotalperinatals(totalneondeaths_e + totalstillbirth);
		oindicators.setTotalneonatals(totalneondeaths);
		oindicators.setTotalstillbirths(totalstillbirth);

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

		return "home";
	}

}
// end class