package org.pdsr.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_mobile_fetalheart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String record_id;

	private Integer fetalheart_refered;

	private Integer fetalheart_arrival;

	private Integer fetalheart_lastheard;

	private String fetalheart_json;

	private Integer data_complete;

	public Integer getData_complete() {
		return data_complete;
	}

	public void setData_complete(Integer data_complete) {
		this.data_complete = data_complete;
	}

	public String getFetalheart_json() {
		return fetalheart_json;
	}

	public void setFetalheart_json(String fetalheart_json) {
		this.fetalheart_json = fetalheart_json;
	}

	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	public Integer getFetalheart_refered() {
		return fetalheart_refered;
	}

	public void setFetalheart_refered(Integer fetalheart_refered) {
		this.fetalheart_refered = fetalheart_refered;
	}

	public Integer getFetalheart_arrival() {
		return fetalheart_arrival;
	}

	public void setFetalheart_arrival(Integer fetalheart_arrival) {
		this.fetalheart_arrival = fetalheart_arrival;
	}

	public Integer getFetalheart_lastheard() {
		return fetalheart_lastheard;
	}

	public void setFetalheart_lastheard(Integer fetalheart_lastheard) {
		this.fetalheart_lastheard = fetalheart_lastheard;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((record_id == null) ? 0 : record_id.hashCode());
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
		json_mobile_fetalheart other = (json_mobile_fetalheart) obj;
		if (record_id == null) {
			if (other.record_id != null)
				return false;
		} else if (!record_id.equals(other.record_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return record_id;
	}


}
