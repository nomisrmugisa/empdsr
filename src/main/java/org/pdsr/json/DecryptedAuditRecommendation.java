package org.pdsr.json;

import java.io.Serializable;
import java.util.List;

import org.pdsr.summary.model.big_audit_recommendation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DecryptedAuditRecommendation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean error;
	private String message;
	private big_audit_recommendation selected;
	private List<big_audit_recommendation> data;

	public DecryptedAuditRecommendation() {
	}
	
	public DecryptedAuditRecommendation(big_audit_recommendation selected) {
		this.selected = selected;
	}

	
	public DecryptedAuditRecommendation(List<big_audit_recommendation> data) {
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

	public big_audit_recommendation getSelected() {
		return selected;
	}

	public void setSelected(big_audit_recommendation selected) {
		this.selected = selected;
	}

	public List<big_audit_recommendation> getData() {
		return data;
	}

	public void setData(List<big_audit_recommendation> data) {
		this.data = data;
	}


}
