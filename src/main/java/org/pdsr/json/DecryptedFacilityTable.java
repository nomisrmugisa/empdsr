package org.pdsr.json;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DecryptedFacilityTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean error;
	private String message;
	private json_facility_table selected;
	private List<json_facility_table> data;

	public DecryptedFacilityTable() {
	}
	
	public DecryptedFacilityTable(json_facility_table selected) {
		this.selected = selected;
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

	public json_facility_table getSelected() {
		return selected;
	}

	public void setSelected(json_facility_table selected) {
		this.selected = selected;
	}

	public List<json_facility_table> getData() {
		return data;
	}

	public void setData(List<json_facility_table> data) {
		this.data = data;
	}

}
