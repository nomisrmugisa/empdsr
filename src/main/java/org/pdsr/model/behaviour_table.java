package org.pdsr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class behaviour_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String behaviour_name;

	@Lob
	@Column
	@Size(max = 65535)
	private String behaviour_desc;

	public String getBehaviour_name() {
		return behaviour_name;
	}

	public void setBehaviour_name(String behaviour_name) {
		this.behaviour_name = behaviour_name;
	}

	public String getBehaviour_desc() {
		return behaviour_desc;
	}

	public void setBehaviour_desc(String behaviour_desc) {
		this.behaviour_desc = behaviour_desc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((behaviour_name == null) ? 0 : behaviour_name.hashCode());
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
		behaviour_table other = (behaviour_table) obj;
		if (behaviour_name == null) {
			if (other.behaviour_name != null)
				return false;
		} else if (!behaviour_name.equals(other.behaviour_name))
			return false;
		return true;
	}
	
}
