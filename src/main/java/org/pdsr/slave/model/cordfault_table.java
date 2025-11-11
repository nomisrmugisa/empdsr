package org.pdsr.slave.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class cordfault_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String cordfault_name;

	@Lob
	@Column
	@Size(max = 65535)
	private String cordfault_desc;

	public String getCordfault_name() {
		return cordfault_name;
	}

	public void setCordfault_name(String cordfault_name) {
		this.cordfault_name = cordfault_name;
	}

	public String getCordfault_desc() {
		return cordfault_desc;
	}

	public void setCordfault_desc(String cordfault_desc) {
		this.cordfault_desc = cordfault_desc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cordfault_name == null) ? 0 : cordfault_name.hashCode());
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
		cordfault_table other = (cordfault_table) obj;
		if (cordfault_name == null) {
			if (other.cordfault_name != null)
				return false;
		} else if (!cordfault_name.equals(other.cordfault_name))
			return false;
		return true;
	}

	
}
