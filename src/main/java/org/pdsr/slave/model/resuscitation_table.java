package org.pdsr.slave.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class resuscitation_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String resuscitation_name;

	@Lob
	@Column
	@Size(max = 65535)
	private String resuscitation_desc;

	public String getResuscitation_name() {
		return resuscitation_name;
	}

	public void setResuscitation_name(String resuscitation_name) {
		this.resuscitation_name = resuscitation_name;
	}

	public String getResuscitation_desc() {
		return resuscitation_desc;
	}

	public void setResuscitation_desc(String resuscitation_desc) {
		this.resuscitation_desc = resuscitation_desc;
	}

	@Override
	public int hashCode() {
		return Objects.hash(resuscitation_name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		resuscitation_table other = (resuscitation_table) obj;
		return Objects.equals(resuscitation_name, other.resuscitation_name);
	}

	
}
