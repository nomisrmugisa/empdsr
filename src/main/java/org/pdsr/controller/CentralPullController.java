package org.pdsr.controller;

import java.io.IOException;
import java.security.Principal;

import javax.transaction.Transactional;

import org.pdsr.InternetAvailabilityChecker;
import org.pdsr.ServiceApi;
import org.pdsr.summary.repo.BigAuditAuditRepository;
import org.pdsr.summary.repo.BigAuditRecommendRepository;
import org.pdsr.summary.repo.BigCaseRepository;
import org.pdsr.summary.repo.BigWeeklyMonitoringTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/controls/downloadmerge")
public class CentralPullController {

	@Autowired
	private ServiceApi api;

	@Autowired
	private BigWeeklyMonitoringTableRepository bweekMRepo;

	@Autowired
	private BigCaseRepository bcaseRepo;

	@Autowired
	private BigAuditAuditRepository baaudRepo;
	@Autowired
	private BigAuditRecommendRepository brecRepo;



	@GetMapping("")
	public String downloadmerge(Principal principal, Model model, @RequestParam(required = false) String success) {

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
	@PostMapping("")
	public String downloadmerge(Principal principal) {
		try {
			if (InternetAvailabilityChecker.isInternetAvailable()) {

				bcaseRepo.saveAll(api.getCases());
				baaudRepo.saveAll(api.getAudits());
				brecRepo.saveAll(api.getRecommendations());
				bweekMRepo.saveAll(api.getWeeklyMonitorings());

				return "redirect:/controls/downloadmerge?success=1";
			} else {
				return "redirect:/controls/downloadmerge?success=0";

			}
		} catch (IOException e) {
			return "redirect:/controls/downloadmerge?failure=1";
		}

	}
}
// end class