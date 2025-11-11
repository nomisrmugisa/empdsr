package org.pdsr.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_weekly_monitoring implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String code;
	private String country;
	private String region;
	private String district;
	private Integer weekly_year;
	private Integer weekly_month;
	private String weekly_mdesc;
	private Integer weekly_week;
	private Integer mindex;
    private Integer wm_values;
    private Integer wm_subval;
    

	public json_weekly_monitoring() {
		super();
	}


	public json_weekly_monitoring(String id, String code, String country, String region, String district,
			Integer weekly_year, Integer weekly_month, String weekly_mdesc, Integer weekly_week, Integer mindex,
			Integer wm_values, Integer wm_subval) {
		super();
		this.id = id;
		this.code = code;
		this.country = country;
		this.region = region;
		this.district = district;
		this.weekly_year = weekly_year;
		this.weekly_month = weekly_month;
		this.weekly_mdesc = weekly_mdesc;
		this.weekly_week = weekly_week;
		this.mindex = mindex;
		this.wm_values = wm_values;
		this.wm_subval = wm_subval;
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

	public Integer getWeekly_year() {
		return weekly_year;
	}

	public void setWeekly_year(Integer weekly_year) {
		this.weekly_year = weekly_year;
	}

	public Integer getWeekly_month() {
		return weekly_month;
	}

	public void setWeekly_month(Integer weekly_month) {
		this.weekly_month = weekly_month;
	}

	public String getWeekly_mdesc() {
		return weekly_mdesc;
	}

	public void setWeekly_mdesc(String weekly_mdesc) {
		this.weekly_mdesc = weekly_mdesc;
	}

	public Integer getWeekly_week() {
		return weekly_week;
	}

	public void setWeekly_week(Integer weekly_week) {
		this.weekly_week = weekly_week;
	}

	public Integer getMindex() {
		return mindex;
	}

	public void setMindex(Integer mindex) {
		this.mindex = mindex;
	}

	public Integer getWm_values() {
		return wm_values;
	}

	public void setWm_values(Integer wm_values) {
		this.wm_values = wm_values;
	}

	public Integer getWm_subval() {
		return wm_subval;
	}

	public void setWm_subval(Integer wm_subval) {
		this.wm_subval = wm_subval;
	}

}
