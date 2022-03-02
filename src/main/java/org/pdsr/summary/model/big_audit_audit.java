package org.pdsr.summary.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class big_audit_audit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@EmbeddedId
	private SummaryPK summaryPk;
	
	@NotNull
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date audit_cdate;

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

	@Lob
	@Column
	private String audit_ifcmfs;
	
	@Lob
	@Column
	private String audit_sysmfs;
	
	@Lob
	@Column
	private String audit_facmfs;

	@Lob
	@Column
	private String audit_hwkmfs;
	
	@Column
	private Integer rec_complete;

	public Integer getRec_complete() {
		return rec_complete;
	}
	public void setRec_complete(Integer rec_complete) {
		this.rec_complete = rec_complete;
	}
	public SummaryPK getSummaryPk() {
		return summaryPk;
	}
	public void setSummaryPk(SummaryPK summaryPk) {
		this.summaryPk = summaryPk;
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
	
	public java.util.Date getAudit_cdate() {
		return audit_cdate;
	}
	public void setAudit_cdate(java.util.Date audit_cdate) {
		this.audit_cdate = audit_cdate;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(summaryPk);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		big_audit_audit other = (big_audit_audit) obj;
		return Objects.equals(summaryPk, other.summaryPk);
	}
		
	
}
