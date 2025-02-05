package org.pdsr.json;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class json_mobile_pregnancy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String record_id;

	private Integer pregnancy_weeks;

	private Integer pregnancy_days;

	private Integer pregnancy_type;
	
	private Integer data_complete;

	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	public Integer getPregnancy_weeks() {
		return pregnancy_weeks;
	}

	public void setPregnancy_weeks(Integer pregnancy_weeks) {
		this.pregnancy_weeks = pregnancy_weeks;
	}

	public Integer getPregnancy_days() {
		return pregnancy_days;
	}

	public void setPregnancy_days(Integer pregnancy_days) {
		this.pregnancy_days = pregnancy_days;
	}

	public Integer getPregnancy_type() {
		return pregnancy_type;
	}

	public void setPregnancy_type(Integer pregnancy_type) {
		this.pregnancy_type = pregnancy_type;
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
		json_mobile_pregnancy other = (json_mobile_pregnancy) obj;
		return Objects.equals(record_id, other.record_id);
	}

	@Override
	public String toString() {
		return record_id;
	}


}
