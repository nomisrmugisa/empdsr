package org.pdsr.slave.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class complication_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String complication_name;

	@Lob
	@Column
	@Size(max = 65535)
	private String complication_desc;
	
	public String getComplication_name() {
		return complication_name;
	}

	public void setComplication_name(String complication_name) {
		this.complication_name = complication_name;
	}

	public String getComplication_desc() {
		return complication_desc;
	}

	public void setComplication_desc(String complication_desc) {
		this.complication_desc = complication_desc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((complication_name == null) ? 0 : complication_name.hashCode());
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
		complication_table other = (complication_table) obj;
		if (complication_name == null) {
			if (other.complication_name != null)
				return false;
		} else if (!complication_name.equals(other.complication_name))
			return false;
		return true;
	}


	
}
