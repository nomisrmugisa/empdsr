package org.pdsr.json;

import java.io.Serializable;
import java.util.List;

import org.pdsr.summary.model.big_case_identifiers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DecryptedCaseIdentifiers implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean error;
	private String message;
	private big_case_identifiers selected;
	private List<big_case_identifiers> data;

	public DecryptedCaseIdentifiers() {
	}
	
	public DecryptedCaseIdentifiers(big_case_identifiers selected) {
		this.selected = selected;
	}

	public DecryptedCaseIdentifiers(List<big_case_identifiers> data) {
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

	public big_case_identifiers getSelected() {
		return selected;
	}

	public void setSelected(big_case_identifiers selected) {
		this.selected = selected;
	}

	public List<big_case_identifiers> getData() {
		return data;
	}

	public void setData(List<big_case_identifiers> data) {
		this.data = data;
	}
}
