package org.pdsr.slave.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class audit_case implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String audit_uuid;///should be same as case_uuid programmatically
	
	@NotNull
	@Column
	private Integer case_death;//1 stillbirth or 2 early neonatal

	@NotNull
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date audit_date;

	@Lob
	@Column
	private String audit_data;
	
	@Column
	private Integer audit_expired;//if null then audit is still active else audit expired

	@OneToOne(mappedBy = "audit_case")
	private audit_audit audit_audit;

	
	public String getAudit_uuid() {
		return audit_uuid;
	}
	public void setAudit_uuid(String audit_uuid) {
		this.audit_uuid = audit_uuid;
	}
	
	public Integer getCase_death() {
		return case_death;
	}
	public void setCase_death(Integer case_death) {
		this.case_death = case_death;
	}
	public java.util.Date getAudit_date() {
		return audit_date;
	}
	public void setAudit_date(java.util.Date audit_date) {
		this.audit_date = audit_date;
	}
	public String getAudit_data() {
		return audit_data;
	}
	public void setAudit_data(String audit_data) {
		this.audit_data = audit_data;
	}
	
	
	public Integer getAudit_expired() {
		return audit_expired;
	}
	public void setAudit_expired(Integer audit_expired) {
		this.audit_expired = audit_expired;
	}
	
	
	public audit_audit getAudit_audit() {
		return audit_audit;
	}
	public void setAudit_audit(audit_audit audit_audit) {
		this.audit_audit = audit_audit;
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
		audit_case other = (audit_case) obj;
		return Objects.equals(audit_uuid, other.audit_uuid);
	}
	
	
}
