package org.pdsr.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.pdsr.pojos.icdpm;
import org.pdsr.pojos.wmoindicators;
import org.pdsr.repo.AuditAuditRepository;
import org.pdsr.repo.AuditRecommendRepository;
import org.pdsr.repo.CaseRepository;
import org.pdsr.repo.IcdCodesRepository;
import org.pdsr.repo.WeeklyMonitoringTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

	@Autowired
	private AuditRecommendRepository recRepo;

	@Autowired
	private AuditAuditRepository audRepo;

	@Autowired
	private IcdCodesRepository icdRepo;

	@GetMapping("")
	public String homee(Principal principal, Model model) {
		final int year = Calendar.getInstance().get(Calendar.YEAR);
		final java.util.Date date = new Date();

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

		// SUMMARY STATISTICS - CURRENT YEAR
		final String[] cdata = wmRepo.findFrontPageRates(year).get(0);

		Integer ctotaldeliveries = Integer.valueOf(cdata[0]), ctotaldelvaginal = Integer.valueOf(cdata[1]),
				ctotaldelassisted = Integer.valueOf(cdata[2]), ctotaldelcaesarean = Integer.valueOf(cdata[3]),

				ctotalbirths = Integer.valueOf(cdata[4]),

				ctotalstillbirth = Integer.valueOf(cdata[5]), ctotalintrapartum = Integer.valueOf(cdata[6]),
				// ctotalantepartum = Integer.valueOf(cdata[7]),

				ctotallivebirths = Integer.valueOf(cdata[8]),

				ctotalpretermbirths = Integer.valueOf(cdata[9]), ctotallowbirthwgt = Integer.valueOf(cdata[10]),

				ctotalneondeaths = Integer.valueOf(cdata[11]),

				ctotalneondeaths_e = Integer.valueOf(cdata[12]), ctotalneondeaths_l = Integer.valueOf(cdata[13]),

				ctotalmaternaldeaths = Integer.valueOf(cdata[14]);

		wmoindicators cindicators = new wmoindicators();
		cindicators.setWmdesc("Averages for " + year);

		cindicators.setTotalbirths(ctotalbirths);
		cindicators.setTotallivebirths(ctotallivebirths);
		cindicators.setTotalperinatals(ctotalneondeaths_e + ctotalstillbirth);
		cindicators.setTotalneonatals(ctotalneondeaths);
		cindicators.setTotalstillbirths(ctotalstillbirth);

		cindicators.setIsbr_oavg(
				ctotalbirths == 0 ? 0 : Math.round(((ctotalstillbirth / ctotalbirths) * 1000) * 10.0) / 10.0);

		cindicators.setIisbr_oavg(
				ctotalbirths == 0 ? 0 : Math.round(((ctotalintrapartum / ctotalbirths) * 1000) * 10.0) / 10.0);

		cindicators.setAisbr_oavg(cindicators.getIsbr_oavg() - cindicators.getIisbr_oavg());

		cindicators.setPiisbr_oavg(
				Math.round(((cindicators.getIisbr_oavg() / cindicators.getIsbr_oavg()) * 100) * 10.0) / 10.0);

		cindicators.setInmr_oavg(
				ctotallivebirths == 0 ? 0 : Math.round(((ctotalneondeaths / ctotallivebirths) * 1000) * 10.0) / 10.0);

		cindicators.setIndwk1_oavg(
				ctotalneondeaths == 0 ? 0 : Math.round(((ctotalneondeaths_e / ctotalneondeaths) * 100) * 10.0) / 10.0);

		cindicators.setEinmr_oavg(
				ctotallivebirths == 0 ? 0 : Math.round(((ctotalneondeaths_e / ctotallivebirths) * 1000) * 10.0) / 10.0);

		cindicators.setLinmr_oavg(
				ctotallivebirths == 0 ? 0 : Math.round(((ctotalneondeaths_l / ctotallivebirths) * 1000) * 10.0) / 10.0);

		cindicators.setIpmr_oavg(ctotalbirths == 0 ? 0
				: Math.round((((ctotalneondeaths_e + ctotalstillbirth) / ctotalbirths) * 1000) * 10.0) / 10.0);

		cindicators.setImmr_oavg(ctotallivebirths == 0 ? 0
				: Math.round(((ctotalmaternaldeaths / ctotallivebirths) * 100000) * 10.0) / 10.0);

		cindicators.setIcsr_oavg(
				ctotaldeliveries == 0 ? 0 : Math.round(((ctotaldelcaesarean / ctotaldeliveries) * 100) * 10.0) / 10.0);

		cindicators.setIadr_oavg(
				ctotaldeliveries == 0 ? 0 : Math.round(((ctotaldelassisted / ctotaldeliveries) * 100) * 10.0) / 10.0);

		cindicators.setIvdr_oavg(
				ctotaldeliveries == 0 ? 0 : Math.round(((ctotaldelvaginal / ctotaldeliveries) * 100) * 10.0) / 10.0);

		cindicators.setIlbwr_oavg(
				ctotallivebirths == 0 ? 0 : Math.round(((ctotallowbirthwgt / ctotallivebirths) * 100) * 10.0) / 10.0);

		cindicators.setIptbr_oavg(
				ctotallivebirths == 0 ? 0 : Math.round(((ctotalpretermbirths / ctotallivebirths) * 100) * 10.0) / 10.0);

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
		for (String[] elem : audRepo.findByTopPMCodes(2, PageRequest.of(0, 5))) {

			icdpm neonatal = new icdpm();
			neonatal.setPm_code(elem[0]);
			neonatal.setPm_desc(icdRepo.findDescriptionOfICDPMNeonatal(elem[0]).get());
			neonatal.setPm_tsum(elem[1]);

			oneonatal.add(neonatal);
		}
		model.addAttribute("top_oneonatal", oneonatal);

		// top causes of death for neonatal for current year
		List<icdpm> cneonatal = new ArrayList<icdpm>();
		for (String[] elem : audRepo.findByTopPMCodes(2, year, PageRequest.of(0, 5))) {

			icdpm neonatal = new icdpm();
			neonatal.setPm_code(elem[0]);
			neonatal.setPm_desc(icdRepo.findDescriptionOfICDPMNeonatal(elem[0]).get());
			neonatal.setPm_tsum(elem[1]);

			cneonatal.add(neonatal);
		}
		model.addAttribute("top_cneonatal", cneonatal);

		
		
		
		// top causes of death overall for stillbirths
		List<icdpm> ostillbirth = new ArrayList<icdpm>();
		for (String[] elem : audRepo.findByTopPMCodes(1, PageRequest.of(0, 5))) {

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
		for (String[] elem : audRepo.findByTopPMCodes(1, year, PageRequest.of(0, 5))) {

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

		return "home";
	}

}
// end class