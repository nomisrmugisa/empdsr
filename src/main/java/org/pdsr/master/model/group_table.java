package org.pdsr.master.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class group_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Column(name = "group_role")
	@Size(min = 1, max = 80)
	private String group_role;

	@NotNull
	@Column(name = "group_desc")
	@Size(min = 1, max = 255)
	private String group_desc;


	public group_table() {
		super();
		// TODO Auto-generated constructor stub
	}

	public group_table(@NotNull @Size(min = 1, max = 80) String group_role,
			@NotNull @Size(min = 1, max = 255) String group_desc) {
		super();
		this.group_role = group_role;
		this.group_desc = group_desc;
	}

	public String getGroup_role() {
		return group_role;
	}

	public void setGroup_role(String group_role) {
		this.group_role = group_role;
	}

	public String getGroup_desc() {
		return group_desc;
	}

	public void setGroup_desc(String group_desc) {
		this.group_desc = group_desc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group_role == null) ? 0 : group_role.hashCode());
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
		group_table other = (group_table) obj;
		if (group_role == null) {
			if (other.group_role != null)
				return false;
		} else if (!group_role.equals(other.group_role))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return group_role;
	}

}
