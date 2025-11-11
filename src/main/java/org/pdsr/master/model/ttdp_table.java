package org.pdsr.master.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class ttdp_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String td_name;

	@Lob
	@Column
	@Size(max = 65535)
	private String td_desc;
	

	public String getTd_name() {
		return td_name;
	}

	public void setTd_name(String td_name) {
		this.td_name = td_name;
	}

	public String getTd_desc() {
		return td_desc;
	}

	public void setTd_desc(String td_desc) {
		this.td_desc = td_desc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((td_name == null) ? 0 : td_name.hashCode());
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
		ttdp_table other = (ttdp_table) obj;
		if (td_name == null) {
			if (other.td_name != null)
				return false;
		} else if (!td_name.equals(other.td_name))
			return false;
		return true;
	}
	
	
}
