package org.pdsr.master.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	private String facility_code;
	
	@NotNull
	@Column
	@Size(min=1, max = 80)
	private String facility_name;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "district", referencedColumnName = "district_uuid", insertable = true, updatable = true)
	private district_table district;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facility")
	private List<case_identifiers> caseList;

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

	public district_table getDistrict() {
		return district;
	}

	public void setDistrict(district_table district) {
		this.district = district;
	}

	public List<case_identifiers> getCaseList() {
		return caseList;
	}

	public void setCaseList(List<case_identifiers> caseList) {
		this.caseList = caseList;
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
