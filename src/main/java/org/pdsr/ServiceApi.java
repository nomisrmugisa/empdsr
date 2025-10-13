package org.pdsr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.pdsr.json.DecryptedAuditAudit;
import org.pdsr.json.DecryptedAuditRecommendation;
import org.pdsr.json.DecryptedCaseIdentifiers;
import org.pdsr.json.DecryptedWeeklyMonitoring;
import org.pdsr.json.EncryptedMessage;
import org.pdsr.json.json_redcap;
import org.pdsr.master.model.facility_table;
import org.pdsr.master.repo.SyncTableRepository;
import org.pdsr.summary.model.big_audit_audit;
import org.pdsr.summary.model.big_audit_recommendation;
import org.pdsr.summary.model.big_case_identifiers;
import org.pdsr.summary.model.big_weekly_monitoring;
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

	private final String BASE_URL, FAC_CODE, API_URL, API_TOKEN;

	private final RestTemplate restTemplate;

	public ServiceApi(final RestTemplateBuilder restTemplateBuilder, SyncTableRepository syncRepo) {
		this.restTemplate = restTemplateBuilder.build();

		BASE_URL = syncRepo.findById(CONSTANTS.LICENSE_ID).get().getSync_url();
		FAC_CODE = syncRepo.findById(CONSTANTS.LICENSE_ID).get().getSync_code();
		API_URL = "";// sync_table.get().getSync_redcap_url();
		API_TOKEN = "";// sync_table.get().getSync_redcap_token();
	}

	/////////////// PULL FROM CENTRAL SERVER ////////////////////////////////
	public facility_table pullMyFacility() {

		try {

			final String URL = BASE_URL.concat("/facility/" + FAC_CODE);
			facility_table w = restTemplate.getForObject(URL, facility_table.class);

			return w;

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		return null;
	}

	/////////////// CASE IDENTIFIERS////////////////////////////////
	public String postCases(List<big_case_identifiers> jsons) {

		DecryptedCaseIdentifiers data = new DecryptedCaseIdentifiers();
		data.setData(jsons);

		EncryptedMessage json = new EncryptedMessage();
		json.setError(false);
		json.setMessage("Encrypted");
		json.setJwt(data.encryptList(json.getKEY(), json.getISS(), json.getAUD()));

		try {

			final String URL = BASE_URL.concat("/savecases.php");
			return restTemplate.postForObject(URL, json, EncryptedMessage.class).getMessage();

		} catch (Exception ex) {

			return new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage()).getMessage();
		}

	}

	public List<big_case_identifiers> getCases() {

		EncryptedMessage json = null;


		try {
			final String URL = BASE_URL.concat("/findcases.php");
			json = restTemplate.getForObject(URL, EncryptedMessage.class);

		} catch (RestClientException ex) {
			json = new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage());
		}

		if (json.isError()) {
			return new ArrayList<big_case_identifiers>();
		}

		if (json.equals(null)) {

			return new ArrayList<big_case_identifiers>();
		}

		ObjectMapper mapper = new ObjectMapper();
		DecryptedCaseIdentifiers data = mapper.convertValue(json.decrypt(), DecryptedCaseIdentifiers.class);

		return data.getData();
	}

	/////////////// CASE AUDIT AUDIT////////////////////////////////
	public String postAudits(List<big_audit_audit> jsons) {

		DecryptedAuditAudit data = new DecryptedAuditAudit();
		data.setData(jsons);

		EncryptedMessage json = new EncryptedMessage();
		json.setError(false);
		json.setMessage("Encrypted");
		json.setJwt(data.encryptList(json.getKEY(), json.getISS(), json.getAUD()));

		try {

			final String URL = BASE_URL.concat("/saveaudits.php");
			return restTemplate.postForObject(URL, json, EncryptedMessage.class).getMessage();

		} catch (Exception ex) {

			return new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage()).getMessage();
		}

	}

	public List<big_audit_audit> getAudits() {

		EncryptedMessage json = null;


		try {
			final String URL = BASE_URL.concat("/findaudits.php");
			json = restTemplate.getForObject(URL, EncryptedMessage.class);

		} catch (RestClientException ex) {
			json = new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage());
		}

		if (json.isError()) {
			return new ArrayList<big_audit_audit>();
		}

		if (json.equals(null)) {

			return new ArrayList<big_audit_audit>();
		}

		ObjectMapper mapper = new ObjectMapper();
		DecryptedAuditAudit data = mapper.convertValue(json.decrypt(), DecryptedAuditAudit.class);

		return data.getData();
	}

	/////////////// CASE AUDIT RECOMMENDATIONS////////////////////////////////
	public String postRecommendations(List<big_audit_recommendation> jsons) {

		DecryptedAuditRecommendation data = new DecryptedAuditRecommendation();
		data.setData(jsons);

		EncryptedMessage json = new EncryptedMessage();
		json.setError(false);
		json.setMessage("Encrypted");
		json.setJwt(data.encryptList(json.getKEY(), json.getISS(), json.getAUD()));

		try {

			final String URL = BASE_URL.concat("/saverecs.php");
			return restTemplate.postForObject(URL, json, EncryptedMessage.class).getMessage();

		} catch (Exception ex) {

			return new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage()).getMessage();
		}

	}

	public List<big_audit_recommendation> getRecommendations() {

		EncryptedMessage json = null;


		try {
			final String URL = BASE_URL.concat("/findrecs.php");
			json = restTemplate.getForObject(URL, EncryptedMessage.class);

		} catch (RestClientException ex) {
			json = new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage());
		}

		if (json.isError()) {
			return new ArrayList<big_audit_recommendation>();
		}

		if (json.equals(null)) {

			return new ArrayList<big_audit_recommendation>();
		}

		ObjectMapper mapper = new ObjectMapper();
		DecryptedAuditRecommendation data = mapper.convertValue(json.decrypt(), DecryptedAuditRecommendation.class);

		return data.getData();
	}

	/////////////// CASE WEEKLY MONITORING////////////////////////////////
	public String postWeeklyMonitorings(List<big_weekly_monitoring> jsons) {

		DecryptedWeeklyMonitoring data = new DecryptedWeeklyMonitoring();
		data.setData(jsons);

		EncryptedMessage json = new EncryptedMessage();
		json.setError(false);
		json.setMessage("Encrypted");
		json.setJwt(data.encryptList(json.getKEY(), json.getISS(), json.getAUD()));

		try {

			final String URL = BASE_URL.concat("/savewms.php");
			return restTemplate.postForObject(URL, json, EncryptedMessage.class).getMessage();

		} catch (Exception ex) {

			return new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage()).getMessage();
		}

	}

	public List<big_weekly_monitoring> getWeeklyMonitorings() {

		EncryptedMessage json = null;


		try {
			final String URL = BASE_URL.concat("/findwms.php");
			json = restTemplate.getForObject(URL, EncryptedMessage.class);

		} catch (RestClientException ex) {
			json = new EncryptedMessage(Boolean.TRUE, ex.getLocalizedMessage());
		}

		if (json.isError()) {
			return new ArrayList<big_weekly_monitoring>();
		}

		if (json.equals(null)) {

			return new ArrayList<big_weekly_monitoring>();
		}

		ObjectMapper mapper = new ObjectMapper();
		DecryptedWeeklyMonitoring data = mapper.convertValue(json.decrypt(), DecryptedWeeklyMonitoring.class);

		return data.getData();
	}

	/// REDCAP DATA
	public List<json_redcap> extractRedCapIdentifiers(Date startDate, Date endDate) {

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