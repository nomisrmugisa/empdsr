package org.pdsr.json;

import java.io.Serializable;
import java.util.List;

import org.pdsr.summary.model.big_weekly_monitoring;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DecryptedWeeklyMonitoring implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean error;
	private String message;
	private big_weekly_monitoring selected;
	private List<big_weekly_monitoring> data;

	public DecryptedWeeklyMonitoring() {
	}
	
	public DecryptedWeeklyMonitoring(big_weekly_monitoring selected) {
		this.selected = selected;
	}
	
	

	public DecryptedWeeklyMonitoring(List<big_weekly_monitoring> data) {
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

	public big_weekly_monitoring getSelected() {
		return selected;
	}

	public void setSelected(big_weekly_monitoring selected) {
		this.selected = selected;
	}
	
	public List<big_weekly_monitoring> getData() {
		return data;
	}

	public void setData(List<big_weekly_monitoring> data) {
		this.data = data;
	}

}
