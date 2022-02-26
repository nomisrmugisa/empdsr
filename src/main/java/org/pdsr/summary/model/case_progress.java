package org.pdsr.summary.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class case_progress implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String facility_code;
	@Column
	private String facility_name;
	@Column
	private String district_name;
	@Column
	private String region_name;
	@Column
	private String country_name;

	@Column
	private Integer overall_total;
	@Column
	private Integer overall_completed;
	@Column
	private Integer overall_pending;
	@Column
	private Integer overall_overdue;

	@Column
	private Integer current_total;
	@Column
	private Integer current_completed;
	@Column
	private Integer current_pending;
	@Column
	private Integer current_overdue;

	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date last_updated;

	public String getFacility_code() {
		return facility_code;
	}

	public void setFacility_code(String facility_code) {
		this.facility_code = facility_code;
	}

	public String getFacility_name() {
		return facility_name;
	}

	public void setFacility_name(String facility_name) {
		this.facility_name = facility_name;
	}

	public String getDistrict_name() {
		return district_name;
	}

	public void setDistrict_name(String district_name) {
		this.district_name = district_name;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public Integer getOverall_total() {
		return overall_total;
	}

	public void setOverall_total(Integer overall_total) {
		this.overall_total = overall_total;
	}

	public Integer getOverall_completed() {
		return overall_completed;
	}

	public void setOverall_completed(Integer overall_completed) {
		this.overall_completed = overall_completed;
	}

	public Integer getOverall_pending() {
		return overall_pending;
	}

	public void setOverall_pending(Integer overall_pending) {
		this.overall_pending = overall_pending;
	}

	public Integer getOverall_overdue() {
		return overall_overdue;
	}

	public void setOverall_overdue(Integer overall_overdue) {
		this.overall_overdue = overall_overdue;
	}

	public Integer getCurrent_total() {
		return current_total;
	}

	public void setCurrent_total(Integer current_total) {
		this.current_total = current_total;
	}

	public Integer getCurrent_completed() {
		return current_completed;
	}

	public void setCurrent_completed(Integer current_completed) {
		this.current_completed = current_completed;
	}

	public Integer getCurrent_pending() {
		return current_pending;
	}

	public void setCurrent_pending(Integer current_pending) {
		this.current_pending = current_pending;
	}

	public Integer getCurrent_overdue() {
		return current_overdue;
	}

	public void setCurrent_overdue(Integer current_overdue) {
		this.current_overdue = current_overdue;
	}

	public Date getLast_updated() {
		return last_updated;
	}

	public void setLast_updated(Date last_updated) {
		this.last_updated = last_updated;
	}

	@Override
	public int hashCode() {
		return Objects.hash(facility_code);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		case_progress other = (case_progress) obj;
		return Objects.equals(facility_code, other.facility_code);
	}

	
}
