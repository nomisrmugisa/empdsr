package org.pdsr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.pdsr.json.DecryptedAuditAudit;
import org.pdsr.json.DecryptedAuditRecommendation;
import org.pdsr.json.DecryptedCaseIdentifiers;
import org.pdsr.json.DecryptedUserTable;
import org.pdsr.json.DecryptedWeeklyMonitoring;
import org.pdsr.json.EncryptedMessage;
import org.pdsr.json.json_audit_audit;
import org.pdsr.json.json_audit_recommendation;
import org.pdsr.json.json_case_identifiers;
import org.pdsr.json.json_redcap;
import org.pdsr.json.json_user_table;
import org.pdsr.json.json_weekly_monitoring;
import org.pdsr.master.model.sync_table;
import org.pdsr.master.repo.SyncTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ServiceApi {

	@Autowired
	private SyncTableRepository syncRepo;
	private String BASE_URL, API_URL, API_TOKEN;// by eliezer

	private final RestTemplate restTemplate;

	public ServiceApi(final RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	/////////////// USER TABLE////////////////////////////////
	public String saveAll(DecryptedUserTable data) {

		Optional<sync_table> sync_table = syncRepo.findById(CONSTANTS.FACILITY_ID);

		if (sync_table.isPresent()) {

			BASE_URL = sync_table.get().getSync_url();

		}

		final String URL = BASE_URL.concat("/saveusers.php");
		EncryptedMessage json = new EncryptedMessage();

		json.setError(false);
		json.setMessage("Encrypted");
		json.setJwt(data.encryptList(json.getKEY(), json.getISS(), json.getAUD()));

		try {

			return restTemplate.postForObject(URL, json, EncryptedMessage.class).getMessage();

		} catch (Exception ex) {

			return new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage()).getMessage();
		}

	}

	public List<json_user_table> findAllUsers() {

		Optional<sync_table> sync_table = syncRepo.findById(CONSTANTS.FACILITY_ID);

		if (sync_table.isPresent()) {

			BASE_URL = sync_table.get().getSync_url();

		}

		EncryptedMessage json = null;

		final String URL = BASE_URL.concat("/findusers.php");

		try {
			json = restTemplate.getForObject(URL, EncryptedMessage.class);

		} catch (RestClientException ex) {
			json = new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage());
		}

		if (json.isError()) {
			return new ArrayList<json_user_table>();
		}

		if (json.equals(null)) {

			return new ArrayList<json_user_table>();
		}

		ObjectMapper mapper = new ObjectMapper();
		DecryptedUserTable data = mapper.convertValue(json.decrypt(), DecryptedUserTable.class);

		return data.getData();
	}

	/////////////// CASE IDENTIFIERS////////////////////////////////
	public String saveAll(DecryptedCaseIdentifiers data) {
		Optional<sync_table> sync_table = syncRepo.findById(CONSTANTS.FACILITY_ID);

		if (sync_table.isPresent()) {

			BASE_URL = sync_table.get().getSync_url();

		}

		final String URL = BASE_URL.concat("/savecases.php");
		EncryptedMessage json = new EncryptedMessage();

		json.setError(false);
		json.setMessage("Encrypted");
		json.setJwt(data.encryptList(json.getKEY(), json.getISS(), json.getAUD()));

		try {

			return restTemplate.postForObject(URL, json, EncryptedMessage.class).getMessage();

		} catch (Exception ex) {

			return new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage()).getMessage();
		}

	}

	public List<json_case_identifiers> findAllCases() {

		Optional<sync_table> sync_table = syncRepo.findById(CONSTANTS.FACILITY_ID);

		if (sync_table.isPresent()) {

			BASE_URL = sync_table.get().getSync_url();

		}

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

		Optional<sync_table> sync_table = syncRepo.findById(CONSTANTS.FACILITY_ID);

		if (sync_table.isPresent()) {

			BASE_URL = sync_table.get().getSync_url();

		}

		final String URL = BASE_URL.concat("/saveaudit.php");
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

	public String saveAll(DecryptedAuditAudit data) {

		final String URL = BASE_URL.concat("/saveaudits.php");
		EncryptedMessage json = new EncryptedMessage();

		json.setError(false);
		json.setMessage("Encrypted");
		json.setJwt(data.encryptList(json.getKEY(), json.getISS(), json.getAUD()));

		try {

			return restTemplate.postForObject(URL, json, EncryptedMessage.class).getMessage();

		} catch (Exception ex) {

			return new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage()).getMessage();
		}

	}

	public List<json_audit_audit> findAllAudits() {

		Optional<sync_table> sync_table = syncRepo.findById(CONSTANTS.FACILITY_ID);

		if (sync_table.isPresent()) {

			BASE_URL = sync_table.get().getSync_url();

		}

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

		Optional<sync_table> sync_table = syncRepo.findById(CONSTANTS.FACILITY_ID);

		if (sync_table.isPresent()) {

			BASE_URL = sync_table.get().getSync_url();

		}

		final String URL = BASE_URL.concat("/saveaction.php");
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

	public String saveAll(DecryptedAuditRecommendation data) {

		Optional<sync_table> sync_table = syncRepo.findById(CONSTANTS.FACILITY_ID);

		if (sync_table.isPresent()) {

			BASE_URL = sync_table.get().getSync_url();

		}

		final String URL = BASE_URL.concat("/saveactions.php");
		EncryptedMessage json = new EncryptedMessage();

		json.setError(false);
		json.setMessage("Encrypted");
		json.setJwt(data.encryptList(json.getKEY(), json.getISS(), json.getAUD()));

		try {

			return restTemplate.postForObject(URL, json, EncryptedMessage.class).getMessage();

		} catch (Exception ex) {

			return new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage()).getMessage();
		}

	}

	public List<json_audit_recommendation> findAllRecommendations() {

		Optional<sync_table> sync_table = syncRepo.findById(CONSTANTS.FACILITY_ID);

		if (sync_table.isPresent()) {

			BASE_URL = sync_table.get().getSync_url();

		}

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

		Optional<sync_table> sync_table = syncRepo.findById(CONSTANTS.FACILITY_ID);

		if (sync_table.isPresent()) {

			BASE_URL = sync_table.get().getSync_url();

		}

		final String URL = BASE_URL.concat("/saveweekly.php");
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

	public String saveAll(DecryptedWeeklyMonitoring data) {

		Optional<sync_table> sync_table = syncRepo.findById(CONSTANTS.FACILITY_ID);

		if (sync_table.isPresent()) {

			BASE_URL = sync_table.get().getSync_url();

		}

		final String URL = BASE_URL.concat("/saveweeklys.php");
		EncryptedMessage json = new EncryptedMessage();

		json.setError(false);
		json.setMessage("Encrypted");
		json.setJwt(data.encryptList(json.getKEY(), json.getISS(), json.getAUD()));

		try {

			return restTemplate.postForObject(URL, json, EncryptedMessage.class).getMessage();

		} catch (Exception ex) {

			return new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage()).getMessage();
		}

	}

	public List<json_weekly_monitoring> findAllMonitoring() {

		Optional<sync_table> sync_table = syncRepo.findById(CONSTANTS.FACILITY_ID);

		if (sync_table.isPresent()) {

			BASE_URL = sync_table.get().getSync_url();

		}

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

	/// REDCAP DATA
	public List<json_redcap> extractRedCapIdentifiers(Date startDate, Date endDate) {

		Optional<sync_table> sync_table = syncRepo.findById(CONSTANTS.FACILITY_ID);

		if (sync_table.isPresent()) {

			API_URL = sync_table.get().getSync_redcap_url();
			API_TOKEN = sync_table.get().getSync_redcap_token();

		}

		try {

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			MultiValueMap<String, String> vars = new LinkedMultiValueMap<>();
			vars.add("token", API_TOKEN);
			vars.add("content", "record");
			vars.add("format", "json");
			vars.add("type", "flat");
			vars.add("dateRangeBegin", dateformat.format(startDate));
			vars.add("dateRangeEnd", dateformat.format(endDate));

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(vars, headers);

			ResponseEntity<List<json_redcap>> response = restTemplate.exchange(API_URL, HttpMethod.POST, request,
					new ParameterizedTypeReference<List<json_redcap>>() {
					});

			System.out.println("Size of data is " + dateformat.format(startDate) + " " + dateformat.format(endDate)
					+ " is " + response.getBody().size());

			return response.getBody();

		} catch (RestClientException ex) {
			System.out.println("Error is " + ex.getLocalizedMessage());
		}

		return new ArrayList<>();
	}

}// end of class