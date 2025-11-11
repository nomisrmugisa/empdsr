package org.pdsr.slave.model;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class audit_audit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String audit_uuid;
	
	@MapsId
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "audit_uuid", referencedColumnName = "audit_uuid", insertable = true, updatable = true)
	private audit_case audit_case;

	@Column
	private Integer audit_death;
	
	@Column
	private String audit_icd10;
	
	@Column
	@NotNull
	private String audit_icdpm;
	
	@Column
	private String audit_csc;
	
	@Column
	@NotNull
	private Integer audit_delay1;
	
	@Column
	@NotNull
	private Integer audit_delay2;
	
	@Column
	@NotNull
	private Integer audit_delay3a;

	@Column
	@NotNull
	private Integer audit_delay3b;

	@Column
	@NotNull
	private Integer audit_delay3c;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "audit_patient", joinColumns = @JoinColumn(name = "audit_uuid"), inverseJoinColumns = @JoinColumn(name = "id"))
	private List<cfactor_table> patient_factors;

	@Column
	private Integer maternal_condition;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "audit_mconditions", joinColumns = @JoinColumn(name = "audit_uuid"), inverseJoinColumns = @JoinColumn(name = "icdm"))
	private List<mcondition_table> maternal_conditions;
	

	@Lob
	@Column
	private String audit_ifcmfs;
	

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "audit_transport", joinColumns = @JoinColumn(name = "audit_uuid"), inverseJoinColumns = @JoinColumn(name = "id"))
	private List<cfactor_table> transport_factors;

	@Lob
	@Column
	private String audit_sysmfs;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "audit_administrative", joinColumns = @JoinColumn(name = "audit_uuid"), inverseJoinColumns = @JoinColumn(name = "id"))
	private List<cfactor_table> administrative_factors;

	@Lob
	@Column
	private String audit_facmfs;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "audit_healthworker", joinColumns = @JoinColumn(name = "audit_uuid"), inverseJoinColumns = @JoinColumn(name = "id"))
	private List<cfactor_table> healthworker_factors;

	@Lob
	@Column
	private String audit_hwkmfs;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "audit_document", joinColumns = @JoinColumn(name = "audit_uuid"), inverseJoinColumns = @JoinColumn(name = "id"))
	private List<cfactor_table> document_factors;

	@NotNull
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date audit_cdate;

	
	@Lob
	@Column
	private String audit_json;

	@NotAudited
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "audit_uuid")
	private List<audit_recommendation> recommendations = new ArrayList<>();

	@Column
	private Integer data_sent;
	
	public Integer getData_sent() {
		return data_sent;
	}

	public void setData_sent(Integer data_sent) {
		this.data_sent = data_sent;
	}

	@Column
	private Integer rec_complete;

	public Integer getRec_complete() {
		return rec_complete;
	}
	public void setRec_complete(Integer rec_complete) {
		this.rec_complete = rec_complete;
	}
	public String getAudit_uuid() {
		return audit_uuid;
	}
	public void setAudit_uuid(String audit_uuid) {
		this.audit_uuid = audit_uuid;
	}
	public Integer getAudit_death() {
		return audit_death;
	}
	public void setAudit_death(Integer audit_death) {
		this.audit_death = audit_death;
	}
	public String getAudit_icd10() {
		return audit_icd10;
	}
	public void setAudit_icd10(String audit_icd10) {
		this.audit_icd10 = audit_icd10;
	}
	public String getAudit_icdpm() {
		return audit_icdpm;
	}
	public void setAudit_icdpm(String audit_icdpm) {
		this.audit_icdpm = audit_icdpm;
	}
	public String getAudit_csc() {
		return audit_csc;
	}
	public void setAudit_csc(String audit_csc) {
		this.audit_csc = audit_csc;
	}
	
	
	public Integer getAudit_delay1() {
		return audit_delay1;
	}
	public void setAudit_delay1(Integer audit_delay1) {
		this.audit_delay1 = audit_delay1;
	}
	public Integer getAudit_delay2() {
		return audit_delay2;
	}
	public void setAudit_delay2(Integer audit_delay2) {
		this.audit_delay2 = audit_delay2;
	}
	public Integer getAudit_delay3a() {
		return audit_delay3a;
	}
	public void setAudit_delay3a(Integer audit_delay3a) {
		this.audit_delay3a = audit_delay3a;
	}
	public Integer getAudit_delay3b() {
		return audit_delay3b;
	}
	public void setAudit_delay3b(Integer audit_delay3b) {
		this.audit_delay3b = audit_delay3b;
	}
	public Integer getAudit_delay3c() {
		return audit_delay3c;
	}
	public void setAudit_delay3c(Integer audit_delay3c) {
		this.audit_delay3c = audit_delay3c;
	}
	public String getAudit_ifcmfs() {
		return audit_ifcmfs;
	}
	public void setAudit_ifcmfs(String audit_ifcmfs) {
		this.audit_ifcmfs = audit_ifcmfs;
	}
	public String getAudit_sysmfs() {
		return audit_sysmfs;
	}
	public void setAudit_sysmfs(String audit_sysmfs) {
		this.audit_sysmfs = audit_sysmfs;
	}
	public String getAudit_facmfs() {
		return audit_facmfs;
	}
	public void setAudit_facmfs(String audit_facmfs) {
		this.audit_facmfs = audit_facmfs;
	}
	public String getAudit_hwkmfs() {
		return audit_hwkmfs;
	}
	public void setAudit_hwkmfs(String audit_hwkmfs) {
		this.audit_hwkmfs = audit_hwkmfs;
	}
	public audit_case getAudit_case() {
		return audit_case;
	}
	public void setAudit_case(audit_case audit_case) {
		this.audit_case = audit_case;
	}
	
	public java.util.Date getAudit_cdate() {
		return audit_cdate;
	}
	public void setAudit_cdate(java.util.Date audit_cdate) {
		this.audit_cdate = audit_cdate;
	}
	
	public String getAudit_json() {
		return audit_json;
	}
	public void setAudit_json(String audit_json) {
		this.audit_json = audit_json;
	}
	public List<audit_recommendation> getRecommendations() {
		return recommendations;
	}
	public void setRecommendations(List<audit_recommendation> recommendations) {
		this.recommendations = recommendations;
	}
	
	public List<cfactor_table> getPatient_factors() {
		return patient_factors;
	}

	public void setPatient_factors(List<cfactor_table> patient_factors) {
		this.patient_factors = patient_factors;
	}

	public List<cfactor_table> getTransport_factors() {
		return transport_factors;
	}

	public void setTransport_factors(List<cfactor_table> transport_factors) {
		this.transport_factors = transport_factors;
	}

	public List<cfactor_table> getAdministrative_factors() {
		return administrative_factors;
	}

	public void setAdministrative_factors(List<cfactor_table> administrative_factors) {
		this.administrative_factors = administrative_factors;
	}

	public List<cfactor_table> getHealthworker_factors() {
		return healthworker_factors;
	}

	public void setHealthworker_factors(List<cfactor_table> healthworker_factors) {
		this.healthworker_factors = healthworker_factors;
	}

	public List<cfactor_table> getDocument_factors() {
		return document_factors;
	}

	public void setDocument_factors(List<cfactor_table> document_factors) {
		this.document_factors = document_factors;
	}

	public List<mcondition_table> getMaternal_conditions() {
		return maternal_conditions;
	}

	public void setMaternal_conditions(List<mcondition_table> maternal_conditions) {
		this.maternal_conditions = maternal_conditions;
	}

	public Integer getMaternal_condition() {
		return maternal_condition;
	}

	public void setMaternal_condition(Integer maternal_condition) {
		this.maternal_condition = maternal_condition;
	}

	@Override
	public int hashCode() {
		return Objects.hash(audit_uuid);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		audit_audit other = (audit_audit) obj;
		return Objects.equals(audit_uuid, other.audit_uuid);
	}
	
	
}
