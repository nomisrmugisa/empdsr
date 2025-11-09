package org.pdsr.json;

import java.io.Serializable;
import java.util.List;

import org.pdsr.summary.model.big_audit_audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DecryptedAuditAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean error;
	private String message;
	private big_audit_audit selected;
	private List<big_audit_audit> data;

	public DecryptedAuditAudit() {
	}
	
	public DecryptedAuditAudit(big_audit_audit selected) {
		this.selected = selected;
	}

	public DecryptedAuditAudit(List<big_audit_audit> data) {
		super();
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public big_audit_audit getSelected() {
		return selected;
	}

	public void setSelected(big_audit_audit selected) {
		this.selected = selected;
	}

	public List<big_audit_audit> getData() {
		return data;
	}

	public void setData(List<big_audit_audit> data) {
		this.data = data;
	}

}
