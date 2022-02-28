package org.pdsr.json;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_case_identifiers implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String code;
	private String country;
	private String region;
	private String district;
	private java.util.Date case_date;
	private Integer case_death;//1 stillbirth or 2 early neonatal
	private Integer case_status;//0 entry, 1 submitted, 2 selected, 3 reviewed
	
	public json_case_identifiers() {
		super();
	}

	public json_case_identifiers(String id, String code, String country, String region, String district, Date case_date,
			Integer case_death, Integer case_status) {
		super();
		this.id = id;
		this.code = code;
		this.country = country;
		this.region = region;
		this.district = district;
		this.case_date = case_date;
		this.case_death = case_death;
		this.case_status = case_status;
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

	public java.util.Date getCase_date() {
		return case_date;
	}

	public void setCase_date(java.util.Date case_date) {
		this.case_date = case_date;
	}

	public Integer getCase_death() {
		return case_death;
	}

	public void setCase_death(Integer case_death) {
		this.case_death = case_death;
	}

	public Integer getCase_status() {
		return case_status;
	}

	public void setCase_status(Integer case_status) {
		this.case_status = case_status;
	}
	
}
