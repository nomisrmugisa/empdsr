package org.pdsr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.pdsr.master.model.audit_audit;
import org.pdsr.master.model.audit_case;
import org.pdsr.master.model.audit_recommendation;
import org.pdsr.master.model.sync_table;
import org.pdsr.master.repo.AuditAuditRepository;
import org.pdsr.master.repo.AuditCaseRepository;
import org.pdsr.master.repo.AuditRecommendRepository;
import org.pdsr.master.repo.SyncTableRepository;
import org.pdsr.master.repo.UserTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	@Autowired
	private SyncTableRepository syncRepo;
	
	@Autowired
	private UserTableRepository userRepo;

	@Autowired
	private AuditCaseRepository acaseRepo;
	
	@Autowired
	private AuditAuditRepository tcaseRepo;

	@Autowired
	private AuditRecommendRepository rcaseRepo;


	@Autowired
	private EmailService emailService;

	@Scheduled(cron = "0 0 9 * * 1,3") // (9pm) of every tuesday, thursday within each week
	public void ScheduleDataExtraction() {

		autoCheckPendingReviews();

	}

	private void autoCheckPendingReviews() {
		try {
			if (InternetAvailabilityChecker.isInternetAvailable()) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, -7);
				sync_table sync = syncRepo.findById(CONSTANTS.LICENSE_ID).get();

				// alert for pending reviews
				List<audit_case> auditsPending = acaseRepo.findActivePendingAudit(cal.getTime());
				if (auditsPending.size() > 0) {
					emailService.sendSimpleMessage(getRecipients(), "MPDSR PENDING REVIEW NOTIFICATION!",
							"Hello Reviewers,\n" + "\nThere are " + auditsPending.size()
									+ " deaths yet to be reviewed for this week" + "\nHealth Facility: "
									+ sync.getSync_name() + " - " + sync.getSync_code()
									+ "\nThis is a PILOT IMPLEMENTATION of the Enhanced Automated MPDSR tool developed by Alex and Eliezer");
				}

				// alert for pending recommendations
				List<audit_audit> recsPending = tcaseRepo.findByPendingRecommendation();
				if (recsPending.size() > 0) {
					emailService.sendSimpleMessage(getRecipients(), "MPDSR PENDING RECOMMENDATIONS NOTIFICATION!",
							"Hello Reviewers,\n" + "\nThere are " + recsPending.size() + " recommendations to work on"
									+ "\nHealth Facility: " + sync.getSync_name() + " - " + sync.getSync_code()
									+ "\nThis is a PILOT IMPLEMENTATION of the Enhanced Automated MPDSR tool developed by Alex and Eliezer");
				}

				// alerts for overdue actions
				List<audit_recommendation> overdue = new ArrayList<>();
				for (audit_recommendation elem : rcaseRepo.findByPendingAction()) {

					if (new java.util.Date().after(elem.getRecommendation_deadline())
							&& elem.getRecommendation_status() != 2) {
					}

					overdue.add(elem);
				}
				if (overdue.size() > 0) {
					emailService.sendSimpleMessage(getRecipients(), "MPDSR OVERDUE ACTIONS NOTIFICATION!",
							"Hello Reviewers,\n" + "\nThere are " + overdue.size()
									+ " incomplete actions that have passed the deadline" + "\nHealth Facility: "
									+ sync.getSync_name() + " - " + sync.getSync_code()
									+ "\nThis is a PILOT IMPLEMENTATION of the Enhanced Automated MPDSR tool developed by Alex and Eliezer");
				}

			}

		} catch (IOException e) {
		}

	}

	private String[] getRecipients() {
		List<String> recipientList = userRepo.findByUser_alerted(true);
		if (recipientList == null) {
			recipientList = new ArrayList<>();
		}
		recipientList.add("makmanu128@gmail.com");
		recipientList.add("elelart@gmail.com");

		return recipientList.toArray(new String[recipientList.size()]);

	}

}
