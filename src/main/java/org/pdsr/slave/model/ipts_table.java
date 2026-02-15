package org.pdsr.slave.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class ipts_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String ipt_name;

	@Lob
	@Column
	@Size(max = 65535)
	private String ipt_desc;
	

	public String getIpt_name() {
		return ipt_name;
	}

	public void setIpt_name(String ipt_name) {
		this.ipt_name = ipt_name;
	}

	public String getIpt_desc() {
		return ipt_desc;
	}

	public void setIpt_desc(String ipt_desc) {
		this.ipt_desc = ipt_desc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ipt_name == null) ? 0 : ipt_name.hashCode());
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
		ipts_table other = (ipts_table) obj;
		if (ipt_name == null) {
			if (other.ipt_name != null)
				return false;
		} else if (!ipt_name.equals(other.ipt_name))
			return false;
		return true;
	}
	
	
}
