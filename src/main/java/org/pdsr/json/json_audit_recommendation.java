package org.pdsr.json;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class json_audit_recommendation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String code;
	private String country;
	private String region;
	private String district;
	private java.util.Date recommendation_date;
	private String recommendation_title;
	private String recommendation_task;
	private String recommendation_leader;
	private String recommendation_reporter;
	private java.util.Date recommendation_deadline;
	private String recommendation_resources;
	private Integer recommendation_status;//0 not started 1 started 2 completed
	private String recommendation_comments;

	public json_audit_recommendation() {
		super();
	}


	public json_audit_recommendation(String id, String code, String country, String region, String district,
			Date recommendation_date, String recommendation_title, String recommendation_task,
			String recommendation_leader, String recommendation_reporter, Date recommendation_deadline,
			String recommendation_resources, Integer recommendation_status, String recommendation_comments) {
		super();
		this.id = id;
		this.code = code;
		this.country = country;
		this.region = region;
		this.district = district;
		this.recommendation_date = recommendation_date;
		this.recommendation_title = recommendation_title;
		this.recommendation_task = recommendation_task;
		this.recommendation_leader = recommendation_leader;
		this.recommendation_reporter = recommendation_reporter;
		this.recommendation_deadline = recommendation_deadline;
		this.recommendation_resources = recommendation_resources;
		this.recommendation_status = recommendation_status;
		this.recommendation_comments = recommendation_comments;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public java.util.Date getRecommendation_date() {
		return recommendation_date;
	}

	public void setRecommendation_date(java.util.Date recommendation_date) {
		this.recommendation_date = recommendation_date;
	}

	public String getRecommendation_title() {
		return recommendation_title;
	}

	public void setRecommendation_title(String recommendation_title) {
		this.recommendation_title = recommendation_title;
	}

	public String getRecommendation_task() {
		return recommendation_task;
	}

	public void setRecommendation_task(String recommendation_task) {
		this.recommendation_task = recommendation_task;
	}

	public String getRecommendation_leader() {
		return recommendation_leader;
	}

	public void setRecommendation_leader(String recommendation_leader) {
		this.recommendation_leader = recommendation_leader;
	}

	public String getRecommendation_reporter() {
		return recommendation_reporter;
	}

	public void setRecommendation_reporter(String recommendation_reporter) {
		this.recommendation_reporter = recommendation_reporter;
	}

	public java.util.Date getRecommendation_deadline() {
		return recommendation_deadline;
	}

	public void setRecommendation_deadline(java.util.Date recommendation_deadline) {
		this.recommendation_deadline = recommendation_deadline;
	}

	public String getRecommendation_resources() {
		return recommendation_resources;
	}

	public void setRecommendation_resources(String recommendation_resources) {
		this.recommendation_resources = recommendation_resources;
	}

	public Integer getRecommendation_status() {
		return recommendation_status;
	}

	public void setRecommendation_status(Integer recommendation_status) {
		this.recommendation_status = recommendation_status;
	}

	public String getRecommendation_comments() {
		return recommendation_comments;
	}

	public void setRecommendation_comments(String recommendation_comments) {
		this.recommendation_comments = recommendation_comments;
	}

}
