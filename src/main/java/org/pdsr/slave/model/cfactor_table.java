package org.pdsr.slave.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;

@Immutable
@Entity
public class cfactor_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	@Column
	private Integer idgroup;
	@Column
	private String cfactor_name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdgroup() {
		return idgroup;
	}
	public void setIdgroup(Integer idgroup) {
		this.idgroup = idgroup;
	}
	public String getCfactor_name() {
		return cfactor_name;
	}
	public void setCfactor_name(String cfactor_name) {
		this.cfactor_name = cfactor_name;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		cfactor_table other = (cfactor_table) obj;
		return Objects.equals(id, other.id);
	}
	
}
