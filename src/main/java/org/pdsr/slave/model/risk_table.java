package org.pdsr.slave.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class risk_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String risk_name;

	@Lob
	@Column
	@Size(max = 65535)
	private String risk_desc;

	public String getRisk_name() {
		return risk_name;
	}

	public void setRisk_name(String risk_name) {
		this.risk_name = risk_name;
	}

	public String getRisk_desc() {
		return risk_desc;
	}

	public void setRisk_desc(String risk_desc) {
		this.risk_desc = risk_desc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((risk_name == null) ? 0 : risk_name.hashCode());
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
		risk_table other = (risk_table) obj;
		if (risk_name == null) {
			if (other.risk_name != null)
				return false;
		} else if (!risk_name.equals(other.risk_name))
			return false;
		return true;
	}
	
	
}
