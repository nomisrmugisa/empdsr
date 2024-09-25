package org.pdsr.slave.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Entity
public class case_referral implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String referral_uuid;

	@MapsId
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "case_uuid", referencedColumnName = "case_uuid", insertable = true, updatable = true)
	private case_identifiers case_uuid;

	
	@Column
	private Integer referral_case;

	
	@Column
	private Integer referral_patient;

	
	@Column
	private Integer referral_source;

	@Column
	@Size(max = 80)
	private String referral_facility;

	
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate referral_date;

	
	@Column
	private Integer referral_hour;

	
	@Column
	private Integer referral_minute;

	
	@Column
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime referral_time;

	@Column
	private Integer referral_datetime_notstated;


	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate referral_adate;

	
	@Column
	private Integer referral_ahour;

	
	@Column
	private Integer referral_aminute;

	
	@Column
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime referral_atime;

	@Column
	private Integer referral_adatetime_notstated;

	@Column
	private Integer referral_transport;

	@Lob
	@Column
	@Size(max = 65535)
	private String referral_notes;

	@Lob
	@Column
	private byte[] referral_file;
	
	@Column
	private String referral_filetype;


	@Lob
	@Column
	private String referral_json;

	@Transient
	private MultipartFile file;

	@Column
	private Integer data_complete;

	public Integer getData_complete() {
		return data_complete;
	}

	public void setData_complete(Integer data_complete) {
		this.data_complete = data_complete;
	}
	

	public String getReferral_uuid() {
		return referral_uuid;
	}

	public void setReferral_uuid(String referral_uuid) {
		this.referral_uuid = referral_uuid;
	}

	public case_identifiers getCase_uuid() {
		return case_uuid;
	}

	public void setCase_uuid(case_identifiers case_uuid) {
		this.case_uuid = case_uuid;
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

	public LocalDate getReferral_date() {
		return referral_date;
	}

	public void setReferral_date(LocalDate referral_date) {
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

	public LocalDate getReferral_adate() {
		return referral_adate;
	}

	public void setReferral_adate(LocalDate referral_adate) {
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

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public LocalTime getReferral_time() {
		return referral_time;
	}

	public void setReferral_time(LocalTime referral_time) {
		this.referral_time = referral_time;
	}

	public LocalTime getReferral_atime() {
		return referral_atime;
	}

	public void setReferral_atime(LocalTime referral_atime) {
		this.referral_atime = referral_atime;
	}

	public String getReferral_filetype() {
		return referral_filetype;
	}

	public void setReferral_filetype(String referral_filetype) {
		this.referral_filetype = referral_filetype;
	}

	public String getReferral_json() {
		return referral_json;
	}

	public void setReferral_json(String referral_json) {
		this.referral_json = referral_json;
	}

	public Integer getReferral_datetime_notstated() {
		return referral_datetime_notstated;
	}

	public void setReferral_datetime_notstated(Integer referral_datetime_notstated) {
		this.referral_datetime_notstated = referral_datetime_notstated;
	}

	public Integer getReferral_adatetime_notstated() {
		return referral_adatetime_notstated;
	}

	public void setReferral_adatetime_notstated(Integer referral_adatetime_notstated) {
		this.referral_adatetime_notstated = referral_adatetime_notstated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((referral_uuid == null) ? 0 : referral_uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		case_referral other = (case_referral) obj;
		if (referral_uuid == null) {
			if (other.referral_uuid != null)
				return false;
		} else if (!referral_uuid.equals(other.referral_uuid))
			return false;
		return true;
	}

}
