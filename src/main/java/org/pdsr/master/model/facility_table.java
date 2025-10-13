package org.pdsr.master.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class facility_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@NotNull
	@Size(min = 1, max = 80)	
	private String facility_uuid;
	
	@NotNull
	@Column(unique = true)
	@Size(min=1, max = 80)
	private String facility_code;//facility code for country is the license used to activate the program
	
	@NotNull
	@Column
	@Size(min=1, max = 80)
	private String facility_name;
	
	@Column
	private Integer facility_type; //0 National, 1 region, 2 district, 3 facility
	
	@ManyToOne(optional = true)
    @JoinColumn(name = "parent", referencedColumnName = "facility_uuid", insertable = true, updatable = true)
    private facility_table parent;
	
	@JsonIgnore
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<facility_table> children = new ArrayList<>();	
	
	@JsonIgnore
	@Transient
	private facility_table newFacility;

	
	public String getFacility_uuid() {
		return facility_uuid;
	}

	public void setFacility_uuid(String facility_uuid) {
		this.facility_uuid = facility_uuid;
	}

	public String getFacility_code() {
		return facility_code;
	}

	public void setFacility_code(String facility_code) {
		this.facility_code = facility_code;
	}

	public String getFacility_name() {
		return facility_name;
	}

	public void setFacility_name(String facility_name) {
		this.facility_name = facility_name;
	}

	public Integer getFacility_type() {
		return facility_type;
	}

	public void setFacility_type(Integer facility_type) {
		this.facility_type = facility_type;
	}


	public List<facility_table> getChildren() {
		return children;
	}

	public void setChildren(List<facility_table> children) {
		this.children = children;
	}

	public facility_table getParent() {
		return parent;
	}

	public void setParent(facility_table parent) {
		this.parent = parent;
	}

	public facility_table getNewFacility() {
		return newFacility;
	}

	public void setNewFacility(facility_table newFacility) {
		this.newFacility = newFacility;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((facility_uuid == null) ? 0 : facility_uuid.hashCode());
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
		facility_table other = (facility_table) obj;
		if (facility_uuid == null) {
			if (other.facility_uuid != null)
				return false;
		} else if (!facility_uuid.equals(other.facility_uuid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return facility_code;
	}
	
	

}
