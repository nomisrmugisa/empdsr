package org.pdsr.master.model;

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
public class case_pregnancy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String pregnancy_uuid;

	@MapsId
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "case_uuid", referencedColumnName = "case_uuid", insertable = true, updatable = true)
	private case_identifiers case_uuid;

	
	@Column
	private Integer pregnancy_weeks;

	
	@Column
	private Integer pregnancy_days;

	
	@Column
	private Integer pregnancy_type;
	
	@Column
	private Integer data_complete;

	@Lob
	@Column
	private String pregnancy_json;


	public String getPregnancy_uuid() {
		return pregnancy_uuid;
	}

	public void setPregnancy_uuid(String pregnancy_uuid) {
		this.pregnancy_uuid = pregnancy_uuid;
	}

	public case_identifiers getCase_uuid() {
		return case_uuid;
	}

	public void setCase_uuid(case_identifiers case_uuid) {
		this.case_uuid = case_uuid;
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

	public String getPregnancy_json() {
		return pregnancy_json;
	}

	public void setPregnancy_json(String pregnancy_json) {
		this.pregnancy_json = pregnancy_json;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pregnancy_uuid == null) ? 0 : pregnancy_uuid.hashCode());
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
		case_pregnancy other = (case_pregnancy) obj;
		if (pregnancy_uuid == null) {
			if (other.pregnancy_uuid != null)
				return false;
		} else if (!pregnancy_uuid.equals(other.pregnancy_uuid))
			return false;
		return true;
	}
	

}
