package org.pdsr.slave.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class placentacheck_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String placentacheck_name;

	@Lob
	@Column
	@Size(max = 65535)
	private String placentacheck_desc;

	public String getPlacentacheck_name() {
		return placentacheck_name;
	}

	public void setPlacentacheck_name(String placentacheck_name) {
		this.placentacheck_name = placentacheck_name;
	}

	public String getPlacentacheck_desc() {
		return placentacheck_desc;
	}

	public void setPlacentacheck_desc(String placentacheck_desc) {
		this.placentacheck_desc = placentacheck_desc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((placentacheck_name == null) ? 0 : placentacheck_name.hashCode());
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
		placentacheck_table other = (placentacheck_table) obj;
		if (placentacheck_name == null) {
			if (other.placentacheck_name != null)
				return false;
		} else if (!placentacheck_name.equals(other.placentacheck_name))
			return false;
		return true;
	}
	
}
