package org.pdsr;

import java.util.ArrayList;
import java.util.List;

import org.pdsr.json.DecryptedAuditAudit;
import org.pdsr.json.DecryptedAuditRecommendation;
import org.pdsr.json.DecryptedCaseIdentifiers;
import org.pdsr.json.DecryptedWeeklyMonitoring;
import org.pdsr.json.EncryptedMessage;
import org.pdsr.json.json_audit_audit;
import org.pdsr.json.json_audit_recommendation;
import org.pdsr.json.json_case_identifiers;
import org.pdsr.json.json_weekly_monitoring;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class ServiceApi {

//	@Autowired
//	private SyncTableRepository syncRepo;
	
	private final RestTemplate restTemplate;
	private final String BASE_URL = "https://olincgroup.com/pdsr/ghana";

	public ServiceApi(final RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
		
		//BASE_URL = syncRepo.findById(CONSTANTS.FACILITY_ID).get().getSync_url();
	}

	/////////////// CASE IDENTIFIERS////////////////////////////////
	public String save(DecryptedCaseIdentifiers data) {

		final String URL = BASE_URL.concat("/savecases.php");
		EncryptedMessage json = new EncryptedMessage();

		json.setError(false);
		json.setMessage("Encrypted");
		json.setJwt(data.encrypt(json.getKEY(), json.getISS(), json.getAUD()));

		try {

			return restTemplate.postForObject(URL, json, EncryptedMessage.class).getMessage();

		} catch (Exception ex) {

			return new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage()).getMessage();
		}

	}

	public List<json_case_identifiers> findAllCases() {

		EncryptedMessage json = null;

		final String URL = BASE_URL.concat("/findcases.php");

		try {
			json = restTemplate.getForObject(URL, EncryptedMessage.class);

		} catch (RestClientException ex) {
			json = new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage());
		}

		if (json.isError()) {
			return new ArrayList<json_case_identifiers>();
		}

		if (json.equals(null)) {

			return new ArrayList<json_case_identifiers>();
		}

		ObjectMapper mapper = new ObjectMapper();
		DecryptedCaseIdentifiers data = mapper.convertValue(json.decrypt(), DecryptedCaseIdentifiers.class);

		return data.getData();
	}

	
	/////////////// CASE AUDIT AUDIT////////////////////////////////
	public String save(DecryptedAuditAudit data) {

		final String URL = BASE_URL.concat("/saveaudits.php");
		EncryptedMessage json = new EncryptedMessage();

		json.setError(false);
		json.setMessage("Encrypted");
		json.setJwt(data.encrypt(json.getKEY(), json.getISS(), json.getAUD()));

		try {

			return restTemplate.postForObject(URL, json, EncryptedMessage.class).getMessage();

		} catch (Exception ex) {

			return new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage()).getMessage();
		}

	}

	public List<json_audit_audit> findAllAudits() {

		EncryptedMessage json = null;

		final String URL = BASE_URL.concat("/findaudits.php");

		try {
			json = restTemplate.getForObject(URL, EncryptedMessage.class);

		} catch (RestClientException ex) {
			json = new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage());
		}

		if (json.isError()) {
			return new ArrayList<json_audit_audit>();
		}

		if (json.equals(null)) {

			return new ArrayList<json_audit_audit>();
		}

		ObjectMapper mapper = new ObjectMapper();
		DecryptedAuditAudit data = mapper.convertValue(json.decrypt(), DecryptedAuditAudit.class);

		return data.getData();
	}


	/////////////// CASE AUDIT RECOMMENDATIONS////////////////////////////////
	public String save(DecryptedAuditRecommendation data) {

		final String URL = BASE_URL.concat("/saveactions.php");
		EncryptedMessage json = new EncryptedMessage();

		json.setError(false);
		json.setMessage("Encrypted");
		json.setJwt(data.encrypt(json.getKEY(), json.getISS(), json.getAUD()));

		try {

			return restTemplate.postForObject(URL, json, EncryptedMessage.class).getMessage();

		} catch (Exception ex) {

			return new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage()).getMessage();
		}

	}

	public List<json_audit_recommendation> findAllRecommendations() {

		EncryptedMessage json = null;

		final String URL = BASE_URL.concat("/findactions.php");

		try {
			json = restTemplate.getForObject(URL, EncryptedMessage.class);

		} catch (RestClientException ex) {
			json = new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage());
		}

		if (json.isError()) {
			return new ArrayList<json_audit_recommendation>();
		}

		if (json.equals(null)) {

			return new ArrayList<json_audit_recommendation>();
		}

		ObjectMapper mapper = new ObjectMapper();
		DecryptedAuditRecommendation data = mapper.convertValue(json.decrypt(), DecryptedAuditRecommendation.class);

		return data.getData();
	}


	/////////////// CASE WEEKLY MONITORING////////////////////////////////
	public String save(DecryptedWeeklyMonitoring data) {

		final String URL = BASE_URL.concat("/saveweeklys.php");
		EncryptedMessage json = new EncryptedMessage();

		json.setError(false);
		json.setMessage("Encrypted");
		json.setJwt(data.encrypt(json.getKEY(), json.getISS(), json.getAUD()));

		try {

			return restTemplate.postForObject(URL, json, EncryptedMessage.class).getMessage();

		} catch (Exception ex) {

			return new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage()).getMessage();
		}

	}

	public List<json_weekly_monitoring> findAllMonitoring() {

		EncryptedMessage json = null;

		final String URL = BASE_URL.concat("/findweeklys.php");

		try {
			json = restTemplate.getForObject(URL, EncryptedMessage.class);

		} catch (RestClientException ex) {
			json = new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage());
		}

		if (json.isError()) {
			return new ArrayList<json_weekly_monitoring>();
		}

		if (json.equals(null)) {

			return new ArrayList<json_weekly_monitoring>();
		}

		ObjectMapper mapper = new ObjectMapper();
		DecryptedWeeklyMonitoring data = mapper.convertValue(json.decrypt(), DecryptedWeeklyMonitoring.class);

		return data.getData();
	}




}// end of class