package org.pdsr.slave.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class user_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Column(name = "username")
	@Size(min = 1, max = 80)
	private String username;

	@NotNull
	@Column(name = "password")
	@Size(min = 1, max = 80)
	private String password;

	@NotNull
	@Column(name = "enabled")
	private boolean enabled;

	@NotNull
	@Column(name = "useremail", unique = true)
	@Size(min = 1, max = 255)
	private String useremail;

	@NotNull
	@Column(name = "userfullname")
	@Size(min = 1, max = 255)
	private String userfullname;

	@NotNull
	@Column(name = "usercontact", unique = true)
	@Size(min = 1, max = 20)
	private String usercontact;

	@NotNull
	@Column(name = "alerted")
	private boolean alerted;

	@Transient
	private String cur_password;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "user_group", joinColumns = @JoinColumn(name = "username"), inverseJoinColumns = @JoinColumn(name = "group_role"))
	private List<group_table> groups = new ArrayList<>();

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "user_facilities", joinColumns = @JoinColumn(name = "username"), inverseJoinColumns = @JoinColumn(name = "facility_uuid"))
	private List<facility_table> facilities = new ArrayList<>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public String getUserfullname() {
		return userfullname;
	}

	public void setUserfullname(String userfullname) {
		this.userfullname = userfullname;
	}

	public String getUsercontact() {
		return usercontact;
	}

	public void setUsercontact(String usercontact) {
		this.usercontact = usercontact;
	}

	public boolean isAlerted() {
		return alerted;
	}

	public void setAlerted(boolean alerted) {
		this.alerted = alerted;
	}

	public String getCur_password() {
		return cur_password;
	}

	public void setCur_password(String cur_password) {
		this.cur_password = cur_password;
	}


	public List<group_table> getGroups() {
		return groups;
	}

	public void setGroups(List<group_table> groups) {
		this.groups = groups;
	}

	public List<facility_table> getFacilities() {
		return facilities;
	}

	public void setFacilities(List<facility_table> facilities) {
		this.facilities = facilities;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		user_table other = (user_table) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return useremail;
	}

}
