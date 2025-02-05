package org.pdsr.json;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_mobile_babydeath implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String record_id;

	private Integer baby_cry;
	
	private Integer baby_resuscitation;

	private String new_resuscitation;

	private Integer baby_apgar1;

	private Integer baby_apgar5;

	private Integer baby_admitted;

	private String new_diagnoses;
	
	private String icd_diagnoses;
	
	private Date baby_ddate;

	private Integer baby_dhour;

	private Integer baby_dminute;

	private Date baby_dtime;

	private Integer baby_ddatetime_notstated;

	private Integer baby_medicalcod;

	private Integer data_complete;

	public Integer getData_complete() {
		return data_complete;
	}

	public void setData_complete(Integer data_complete) {
		this.data_complete = data_complete;
	}


	public Integer getBaby_cry() {
		return baby_cry;
	}

	public void setBaby_cry(Integer baby_cry) {
		this.baby_cry = baby_cry;
	}

	public Integer getBaby_resuscitation() {
		return baby_resuscitation;
	}

	public void setBaby_resuscitation(Integer baby_resuscitation) {
		this.baby_resuscitation = baby_resuscitation;
	}

	public String getNew_resuscitation() {
		return new_resuscitation;
	}

	public void setNew_resuscitation(String new_resuscitation) {
		this.new_resuscitation = new_resuscitation;
	}

	public Integer getBaby_apgar1() {
		return baby_apgar1;
	}

	public void setBaby_apgar1(Integer baby_apgar1) {
		this.baby_apgar1 = baby_apgar1;
	}

	public Integer getBaby_apgar5() {
		return baby_apgar5;
	}

	public void setBaby_apgar5(Integer baby_apgar5) {
		this.baby_apgar5 = baby_apgar5;
	}

	public Integer getBaby_admitted() {
		return baby_admitted;
	}

	public void setBaby_admitted(Integer baby_admitted) {
		this.baby_admitted = baby_admitted;
	}


	public String getNew_diagnoses() {
		return new_diagnoses;
	}

	public void setNew_diagnoses(String new_diagnoses) {
		this.new_diagnoses = new_diagnoses;
	}

	public Date getBaby_ddate() {
		return baby_ddate;
	}

	public void setBaby_ddate(Date baby_ddate) {
		this.baby_ddate = baby_ddate;
	}

	public Integer getBaby_dhour() {
		return baby_dhour;
	}

	public void setBaby_dhour(Integer baby_dhour) {
		this.baby_dhour = baby_dhour;
	}

	public Integer getBaby_dminute() {
		return baby_dminute;
	}

	public void setBaby_dminute(Integer baby_dminute) {
		this.baby_dminute = baby_dminute;
	}

	public Date getBaby_dtime() {
		return baby_dtime;
	}

	public void setBaby_dtime(Date baby_dtime) {
		this.baby_dtime = baby_dtime;
	}

	public Integer getBaby_medicalcod() {
		return baby_medicalcod;
	}

	public void setBaby_medicalcod(Integer baby_medicalcod) {
		this.baby_medicalcod = baby_medicalcod;
	}

	public Integer getBaby_ddatetime_notstated() {
		return baby_ddatetime_notstated;
	}

	public void setBaby_ddatetime_notstated(Integer baby_ddatetime_notstated) {
		this.baby_ddatetime_notstated = baby_ddatetime_notstated;
	}

	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	public String getIcd_diagnoses() {
		return icd_diagnoses;
	}

	public void setIcd_diagnoses(String icd_diagnoses) {
		this.icd_diagnoses = icd_diagnoses;
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
		json_mobile_babydeath other = (json_mobile_babydeath) obj;
		return Objects.equals(record_id, other.record_id);
	}

	@Override
	public String toString() {
		return record_id;
	}


}
