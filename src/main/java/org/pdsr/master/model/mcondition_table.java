package org.pdsr.master.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Immutable;

@Entity
public class mcondition_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String icdm;
	@Column
	private String icdmgroup;
	@Lob
	@Column
	private String icdm_name;
	public String getIcdm() {
		return icdm;
	}
	public void setIcdm(String icdm) {
		this.icdm = icdm;
	}
	public String getIcdmgroup() {
		return icdmgroup;
	}
	public void setIcdmgroup(String icdmgroup) {
		this.icdmgroup = icdmgroup;
	}
	public String getIcdm_name() {
		return icdm_name;
	}
	public void setIcdm_name(String icdm_name) {
		this.icdm_name = icdm_name;
	}
	@Override
	public int hashCode() {
		return Objects.hash(icdm);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		mcondition_table other = (mcondition_table) obj;
		return Objects.equals(icdm, other.icdm);
	}
	
}
