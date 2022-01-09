package org.pdsr.controller;

import java.security.Principal;

import org.pdsr.repo.CaseRepository;
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

	@GetMapping("")
	public String homee(Principal principal, Model model) {

		model.addAttribute("entered_nd", caseRepo.countByCase_death(2));
		model.addAttribute("entered_sb", caseRepo.countByCase_death(1));

		model.addAttribute("submitted_ndn", caseRepo.countByCase_statusAndType(1, 2));
		model.addAttribute("submitted_sbn", caseRepo.countByCase_statusAndType(1, 1));

		model.addAttribute("submitted_ndp", caseRepo.countByCase_death(2) == 0 ? 0
				: 100 * caseRepo.countByCase_statusAndType(1, 2) / caseRepo.countByCase_death(2));
		model.addAttribute("submitted_sbp", caseRepo.countByCase_death(1) == 0 ? 0
				: 100 * caseRepo.countByCase_statusAndType(1, 1) / caseRepo.countByCase_death(1));

		model.addAttribute("selected_nd", caseRepo.countSelectedCasesByCase_death(2));
		model.addAttribute("selected_sb", caseRepo.countSelectedCasesByCase_death(1));

		model.addAttribute("reviewed_ndn", caseRepo.countReviewedCasesByCase_death(2));
		model.addAttribute("reviewed_sbn", caseRepo.countReviewedCasesByCase_death(1));

		model.addAttribute("reviewed_ndp", caseRepo.countSelectedCasesByCase_death(2) == 0 ? 0
				: 100 * caseRepo.countReviewedCasesByCase_death(2) / caseRepo.countSelectedCasesByCase_death(2));
		model.addAttribute("reviewed_sbp", caseRepo.countSelectedCasesByCase_death(1) == 0 ? 0
				: 100 * caseRepo.countReviewedCasesByCase_death(1) / caseRepo.countSelectedCasesByCase_death(1));

		return "home";
	}

}
// end class