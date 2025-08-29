package org.pdsr.json;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_mobile_identifiers implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String case_uuid;

	private Date case_date;

	private String case_sync;

	private String case_id;

	private String case_mid;

	private String case_mname;

	private Integer case_death;// 1 stillbirth or 2 early neonatal or 3 maternal

	private Integer case_status;// 0 entry, 1 auditing new submissions, 2 auditing started auditing, 3 process
								// complete
	private Integer data_sent;
	
	private String facility;

	public String getCase_uuid() {
		return case_uuid;
	}

	public void setCase_uuid(String case_uuid) {
		this.case_uuid = case_uuid;
	}

	public Date getCase_date() {
		return case_date;
	}

	public void setCase_date(Date case_date) {
		this.case_date = case_date;
	}

	public String getCase_sync() {
		return case_sync;
	}

	public void setCase_sync(String case_sync) {
		this.case_sync = case_sync;
	}

	public String getCase_id() {
		return case_id;
	}

	public void setCase_id(String case_id) {
		this.case_id = case_id;
	}

	public String getCase_mid() {
		return case_mid;
	}

	public void setCase_mid(String case_mid) {
		this.case_mid = case_mid;
	}

	public String getCase_mname() {
		return case_mname;
	}

	public void setCase_mname(String case_mname) {
		this.case_mname = case_mname;
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

	public Integer getData_sent() {
		return data_sent;
	}

	public void setData_sent(Integer data_sent) {
		this.data_sent = data_sent;
	}

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((case_uuid == null) ? 0 : case_uuid.hashCode());
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
		json_mobile_identifiers other = (json_mobile_identifiers) obj;
		if (case_uuid == null) {
			if (other.case_uuid != null)
				return false;
		} else if (!case_uuid.equals(other.case_uuid))
			return false;
		return true;
	}

}
