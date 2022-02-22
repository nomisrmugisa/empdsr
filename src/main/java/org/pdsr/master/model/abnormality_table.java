package org.pdsr.master.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class abnormality_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String abnormal_name;

	@Lob
	@Column
	@Size(max = 65535)
	private String abnormal_desc;

	public String getAbnormal_name() {
		return abnormal_name;
	}

	public void setAbnormal_name(String abnormal_name) {
		this.abnormal_name = abnormal_name;
	}

	public String getAbnormal_desc() {
		return abnormal_desc;
	}

	public void setAbnormal_desc(String abnormal_desc) {
		this.abnormal_desc = abnormal_desc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abnormal_name == null) ? 0 : abnormal_name.hashCode());
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
		abnormality_table other = (abnormality_table) obj;
		if (abnormal_name == null) {
			if (other.abnormal_name != null)
				return false;
		} else if (!abnormal_name.equals(other.abnormal_name))
			return false;
		return true;
	}
	
}
