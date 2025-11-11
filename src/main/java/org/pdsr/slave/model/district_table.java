package org.pdsr.slave.model;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class district_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String district_uuid;

	@NotNull
	@Column(unique = true)
	@Size(min=1, max = 80)
	private String district_name;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "region", referencedColumnName = "region_uuid", insertable = true, updatable = true)
	private region_table region;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "district")
    private List<facility_table> facilityList = new ArrayList<>();


	public String getDistrict_uuid() {
		return district_uuid;
	}

	public void setDistrict_uuid(String district_uuid) {
		this.district_uuid = district_uuid;
	}

	public String getDistrict_name() {
		return district_name;
	}

	public void setDistrict_name(String district_name) {
		this.district_name = district_name;
	}

	public region_table getRegion() {
		return region;
	}

	public void setRegion(region_table region) {
		this.region = region;
	}

	public List<facility_table> getFacilityList() {
		return facilityList;
	}

	public void setFacilityList(List<facility_table> facilityList) {
		this.facilityList = facilityList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((district_uuid == null) ? 0 : district_uuid.hashCode());
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
		district_table other = (district_table) obj;
		if (district_uuid == null) {
			if (other.district_uuid != null)
				return false;
		} else if (!district_uuid.equals(other.district_uuid))
			return false;
		return true;
	}
	
	

}
