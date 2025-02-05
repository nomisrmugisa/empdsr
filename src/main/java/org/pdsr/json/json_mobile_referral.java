package org.pdsr.json;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_mobile_referral implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String record_id;

	private Integer referral_case;

	private Integer referral_patient;

	private Integer referral_source;

	private String referral_facility;
	
	private Date referral_date;

	private Integer referral_hour;

	private Integer referral_minute;

	private Date referral_time;
	
	private Integer referral_datetime_notstated;

	private Date referral_adate;
	
	private Integer referral_ahour;

	private Integer referral_aminute;
	
	private Date referral_atime;

	private Integer referral_adatetime_notstated;
	
	private Integer referral_transport;

	private String referral_notes;

	private byte[] referral_file;
	
	private Integer data_complete;

	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	public Integer getReferral_case() {
		return referral_case;
	}

	public void setReferral_case(Integer referral_case) {
		this.referral_case = referral_case;
	}

	public Integer getReferral_patient() {
		return referral_patient;
	}

	public void setReferral_patient(Integer referral_patient) {
		this.referral_patient = referral_patient;
	}

	public Integer getReferral_source() {
		return referral_source;
	}

	public void setReferral_source(Integer referral_source) {
		this.referral_source = referral_source;
	}

	public String getReferral_facility() {
		return referral_facility;
	}

	public void setReferral_facility(String referral_facility) {
		this.referral_facility = referral_facility;
	}

	public Date getReferral_date() {
		return referral_date;
	}

	public void setReferral_date(Date referral_date) {
		this.referral_date = referral_date;
	}

	public Integer getReferral_hour() {
		return referral_hour;
	}

	public void setReferral_hour(Integer referral_hour) {
		this.referral_hour = referral_hour;
	}

	public Integer getReferral_minute() {
		return referral_minute;
	}

	public void setReferral_minute(Integer referral_minute) {
		this.referral_minute = referral_minute;
	}

	public Date getReferral_time() {
		return referral_time;
	}

	public void setReferral_time(Date referral_time) {
		this.referral_time = referral_time;
	}

	public Integer getReferral_datetime_notstated() {
		return referral_datetime_notstated;
	}

	public void setReferral_datetime_notstated(Integer referral_datetime_notstated) {
		this.referral_datetime_notstated = referral_datetime_notstated;
	}

	public Date getReferral_adate() {
		return referral_adate;
	}

	public void setReferral_adate(Date referral_adate) {
		this.referral_adate = referral_adate;
	}

	public Integer getReferral_ahour() {
		return referral_ahour;
	}

	public void setReferral_ahour(Integer referral_ahour) {
		this.referral_ahour = referral_ahour;
	}

	public Integer getReferral_aminute() {
		return referral_aminute;
	}

	public void setReferral_aminute(Integer referral_aminute) {
		this.referral_aminute = referral_aminute;
	}

	public Date getReferral_atime() {
		return referral_atime;
	}

	public void setReferral_atime(Date referral_atime) {
		this.referral_atime = referral_atime;
	}

	public Integer getReferral_adatetime_notstated() {
		return referral_adatetime_notstated;
	}

	public void setReferral_adatetime_notstated(Integer referral_adatetime_notstated) {
		this.referral_adatetime_notstated = referral_adatetime_notstated;
	}

	public Integer getReferral_transport() {
		return referral_transport;
	}

	public void setReferral_transport(Integer referral_transport) {
		this.referral_transport = referral_transport;
	}

	public String getReferral_notes() {
		return referral_notes;
	}

	public void setReferral_notes(String referral_notes) {
		this.referral_notes = referral_notes;
	}

	public byte[] getReferral_file() {
		return referral_file;
	}

	public void setReferral_file(byte[] referral_file) {
		this.referral_file = referral_file;
	}

	public Integer getData_complete() {
		return data_complete;
	}

	public void setData_complete(Integer data_complete) {
		this.data_complete = data_complete;
	}

	@Override
	public int hashCode() {
		return Objects.hash(record_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		json_mobile_referral other = (json_mobile_referral) obj;
		return Objects.equals(record_id, other.record_id);
	}

	@Override
	public String toString() {
		return record_id;
	}
	

}
