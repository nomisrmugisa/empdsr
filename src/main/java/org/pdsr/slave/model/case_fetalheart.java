package org.pdsr.slave.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class case_fetalheart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String fetalheart_uuid;

	@MapsId
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "case_uuid", referencedColumnName = "case_uuid", insertable = true, updatable = true)
	private case_identifiers case_uuid;

	
	@Column
	private Integer fetalheart_refered;

	
	@Column
	private Integer fetalheart_arrival;

	
	@Column
	private Integer fetalheart_lastheard;

	@Lob
	@Column
	private String fetalheart_json;

	@Column
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

	public String getFetalheart_uuid() {
		return fetalheart_uuid;
	}

	public void setFetalheart_uuid(String fetalheart_uuid) {
		this.fetalheart_uuid = fetalheart_uuid;
	}

	public case_identifiers getCase_uuid() {
		return case_uuid;
	}

	public void setCase_uuid(case_identifiers case_uuid) {
		this.case_uuid = case_uuid;
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
		result = prime * result + ((fetalheart_uuid == null) ? 0 : fetalheart_uuid.hashCode());
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
		case_fetalheart other = (case_fetalheart) obj;
		if (fetalheart_uuid == null) {
			if (other.fetalheart_uuid != null)
				return false;
		} else if (!fetalheart_uuid.equals(other.fetalheart_uuid))
			return false;
		return true;
	}


}
