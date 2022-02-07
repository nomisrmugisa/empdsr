package org.pdsr.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class case_babydeath implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String baby_uuid;

	@MapsId
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "case_uuid", referencedColumnName = "case_uuid", insertable = true, updatable = true)
	private case_identifiers case_uuid;

	
	@Column
	private Integer baby_cry;

	
	@Column
	private Integer baby_resuscitation;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "baby_resuscitation", joinColumns = @JoinColumn(name = "resuscitation_uuid"), inverseJoinColumns = @JoinColumn(name = "resuscitation_name"))
	private List<resuscitation_table> resuscitations;

	@Lob
	@Column
	private String new_resuscitation;

	
	@Column
	private Integer baby_apgar1;

	
	@Column
	private Integer baby_apgar5;

	
	@Column
	private Integer baby_admitted;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "baby_diagnoses", joinColumns = @JoinColumn(name = "diagnosis_uuid"), inverseJoinColumns = @JoinColumn(name = "diagnosis_name"))
	private List<diagnoses_table> diagnoses;

	@Lob
	@Column
	private String new_diagnoses;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "baby_icd", joinColumns = @JoinColumn(name = "icd_uuid"), inverseJoinColumns = @JoinColumn(name = "icd_code"))
	private List<icd_diagnoses> icd_diagnoses;
	
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private java.util.Date baby_ddate;

	
	@Column
	private Integer baby_dhour;

	
	@Column
	private Integer baby_dminute;

	
	@Column
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private java.util.Date baby_dtime;

	@Column
	private Integer baby_medicalcod;

	@Lob
	@Column
	private String baby_json;

	
	@Column
	private Integer data_complete;

	public Integer getData_complete() {
		return data_complete;
	}

	public void setData_complete(Integer data_complete) {
		this.data_complete = data_complete;
	}

	public String getBaby_uuid() {
		return baby_uuid;
	}

	public void setBaby_uuid(String baby_uuid) {
		this.baby_uuid = baby_uuid;
	}

	public case_identifiers getCase_uuid() {
		return case_uuid;
	}

	public void setCase_uuid(case_identifiers case_uuid) {
		this.case_uuid = case_uuid;
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

	public List<resuscitation_table> getResuscitations() {
		return resuscitations;
	}

	public void setResuscitations(List<resuscitation_table> resuscitations) {
		this.resuscitations = resuscitations;
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

	public List<diagnoses_table> getDiagnoses() {
		return diagnoses;
	}

	public void setDiagnoses(List<diagnoses_table> diagnoses) {
		this.diagnoses = diagnoses;
	}

	public String getNew_diagnoses() {
		return new_diagnoses;
	}

	public void setNew_diagnoses(String new_diagnoses) {
		this.new_diagnoses = new_diagnoses;
	}

	public List<icd_diagnoses> getIcd_diagnoses() {
		return icd_diagnoses;
	}

	public void setIcd_diagnoses(List<icd_diagnoses> icd_diagnoses) {
		this.icd_diagnoses = icd_diagnoses;
	}

	public java.util.Date getBaby_ddate() {
		return baby_ddate;
	}

	public void setBaby_ddate(java.util.Date baby_ddate) {
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

	public java.util.Date getBaby_dtime() {
		return baby_dtime;
	}

	public void setBaby_dtime(java.util.Date baby_dtime) {
		this.baby_dtime = baby_dtime;
	}

	public Integer getBaby_medicalcod() {
		return baby_medicalcod;
	}

	public void setBaby_medicalcod(Integer baby_medicalcod) {
		this.baby_medicalcod = baby_medicalcod;
	}

	public String getBaby_json() {
		return baby_json;
	}

	public void setBaby_json(String baby_json) {
		this.baby_json = baby_json;
	}

	@Override
	public int hashCode() {
		return Objects.hash(baby_uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		case_babydeath other = (case_babydeath) obj;
		return Objects.equals(baby_uuid, other.baby_uuid);
	}

}
