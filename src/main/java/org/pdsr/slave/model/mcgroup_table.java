package org.pdsr.slave.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Immutable;

@Immutable
@Entity
public class mcgroup_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String icdmgroup;
	@Lob
	@Column
	private String icdmgroup_name;
	public String getIcdmgroup() {
		return icdmgroup;
	}
	public void setIcdmgroup(String icdmgroup) {
		this.icdmgroup = icdmgroup;
	}
	public String getIcdmgroup_name() {
		return icdmgroup_name;
	}
	public void setIcdmgroup_name(String icdmgroup_name) {
		this.icdmgroup_name = icdmgroup_name;
	}
	@Override
	public int hashCode() {
		return Objects.hash(icdmgroup);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		mcgroup_table other = (mcgroup_table) obj;
		return Objects.equals(icdmgroup, other.icdmgroup);
	}
	
}
