package org.pdsr.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;
import org.pdsr.AuditListener;

@Entity
@RevisionEntity(AuditListener.class)
public class revision_audit_trail implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@RevisionNumber
	private int revisionId;

	@RevisionTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date revisiontimestamp;

	private String username;

	public int getRevisionId() {
		return revisionId;
	}

	public void setRevisionId(int revisionId) {
		this.revisionId = revisionId;
	}

	public java.util.Date getRevisiontimestamp() {
		return revisiontimestamp;
	}

	public void setRevisiontimestamp(java.util.Date revisiontimestamp) {
		this.revisiontimestamp = revisiontimestamp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + revisionId;
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
		revision_audit_trail other = (revision_audit_trail) obj;
		if (revisionId != other.revisionId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "" + revisionId;
	}

}