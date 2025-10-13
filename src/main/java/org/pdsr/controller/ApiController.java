package org.pdsr.controller;

import java.util.List;
import java.util.Optional;

import org.pdsr.json.DecryptedAuditAudit;
import org.pdsr.json.DecryptedAuditRecommendation;
import org.pdsr.json.DecryptedCaseIdentifiers;
import org.pdsr.json.DecryptedWeeklyMonitoring;
import org.pdsr.json.EncryptedMessage;
import org.pdsr.master.model.facility_table;
import org.pdsr.master.repo.FacilityTableRepository;
import org.pdsr.summary.model.big_audit_audit;
import org.pdsr.summary.model.big_audit_recommendation;
import org.pdsr.summary.model.big_case_identifiers;
import org.pdsr.summary.model.big_weekly_monitoring;
import org.pdsr.summary.repo.BigAuditAuditRepository;
import org.pdsr.summary.repo.BigAuditRecommendRepository;
import org.pdsr.summary.repo.BigCaseRepository;
import org.pdsr.summary.repo.BigWeeklyMonitoringTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private BigCaseRepository bCaseRepo;
	
	@Autowired
	private BigAuditAuditRepository bAuditRepo;
	
	@Autowired
	private BigAuditRecommendRepository bRecRepo;
	
	@Autowired
	private BigWeeklyMonitoringTableRepository wMonRepo;

	@Autowired
	private FacilityTableRepository facRepo;

	@GetMapping("/facility/{code}")
	public facility_table findMyFacility(@PathVariable String code) {

		Optional<facility_table> facility = facRepo.findByFacility_code(code);

		if (facility.isPresent()) {
			return facility.get();
		}

		return new facility_table();
	}

	@PostMapping("/savecases")
	public EncryptedMessage saveCases(@RequestBody EncryptedMessage json) {

		ObjectMapper mapper = new ObjectMapper();
		DecryptedCaseIdentifiers data = mapper.convertValue(json.decrypt(), DecryptedCaseIdentifiers.class);

		List<big_case_identifiers> big = data.getData();

		bCaseRepo.saveAll(big);
		
		
		return json;

	}

	@GetMapping("/findcases")
	public EncryptedMessage findCases() {
		
		List<big_case_identifiers> big = bCaseRepo.findAll();
		
		//Encrypt the data
		EncryptedMessage data = new EncryptedMessage();
		data.setError(false);
		data.setMessage("Retrieved successfully");		
		data.setJwt(new DecryptedCaseIdentifiers(big).encryptList(data.getKEY(), data.getISS(), data.getAUD()));

		return data;
	}

	@PostMapping("/saveaudits")
	public EncryptedMessage saveAudits(@RequestBody EncryptedMessage json) {

		ObjectMapper mapper = new ObjectMapper();
		DecryptedAuditAudit data = mapper.convertValue(json.decrypt(), DecryptedAuditAudit.class);

		List<big_audit_audit> big = data.getData();

		bAuditRepo.saveAll(big);
		
		
		return json;

	}

	@GetMapping("/findaudits")
	public EncryptedMessage findAudits() {
		
		List<big_audit_audit> big = bAuditRepo.findAll();
		
		//Encrypt the data
		EncryptedMessage data = new EncryptedMessage();
		data.setError(false);
		data.setMessage("Retrieved successfully");		
		data.setJwt(new DecryptedAuditAudit(big).encryptList(data.getKEY(), data.getISS(), data.getAUD()));

		return data;
	}

	@PostMapping("/saverecs")
	public EncryptedMessage saveRecommendations(@RequestBody EncryptedMessage json) {

		ObjectMapper mapper = new ObjectMapper();
		DecryptedAuditRecommendation data = mapper.convertValue(json.decrypt(), DecryptedAuditRecommendation.class);

		List<big_audit_recommendation> big = data.getData();

		bRecRepo.saveAll(big);
		
		
		return json;

	}

	@GetMapping("/findrecs")
	public EncryptedMessage findRecommendations() {
		
		List<big_audit_recommendation> big = bRecRepo.findAll();
		
		//Encrypt the data
		EncryptedMessage data = new EncryptedMessage();
		data.setError(false);
		data.setMessage("Retrieved successfully");		
		data.setJwt(new DecryptedAuditRecommendation(big).encryptList(data.getKEY(), data.getISS(), data.getAUD()));

		return data;
	}

	@PostMapping("/savewms")
	public EncryptedMessage saveWeeklyMonitorings(@RequestBody EncryptedMessage json) {

		ObjectMapper mapper = new ObjectMapper();
		DecryptedWeeklyMonitoring data = mapper.convertValue(json.decrypt(), DecryptedWeeklyMonitoring.class);

		List<big_weekly_monitoring> big = data.getData();

		wMonRepo.saveAll(big);
		
		
		return json;

	}

	@GetMapping("/findwms")
	public EncryptedMessage findWeeklyMonitorings() {
		
		List<big_weekly_monitoring> big = wMonRepo.findAll();
		
		//Encrypt the data
		EncryptedMessage data = new EncryptedMessage();
		data.setError(false);
		data.setMessage("Retrieved successfully");		
		data.setJwt(new DecryptedWeeklyMonitoring(big).encryptList(data.getKEY(), data.getISS(), data.getAUD()));

		return data;
	}


}
