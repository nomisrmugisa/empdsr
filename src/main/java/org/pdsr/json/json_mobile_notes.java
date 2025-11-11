package org.pdsr.json;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_mobile_notes implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String record_id;

	private String notes_text;
	
	private byte[] notes_file;
	
	private Integer data_complete;

	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	public String getNotes_text() {
		return notes_text;
	}

	public void setNotes_text(String notes_text) {
		this.notes_text = notes_text;
	}

	public byte[] getNotes_file() {
		return notes_file;
	}

	public void setNotes_file(byte[] notes_file) {
		this.notes_file = notes_file;
	}

	public Integer getData_complete() {
		return data_complete;
	}

	public void setData_complete(Integer data_complete) {
		this.data_complete = data_complete;
	}

	@Override
	public int hashCode() {
		return Objects.hash(record_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		json_mobile_notes other = (json_mobile_notes) obj;
		return Objects.equals(record_id, other.record_id);
	}

	@Override
	public String toString() {
		return record_id;
	}
	

	
}
