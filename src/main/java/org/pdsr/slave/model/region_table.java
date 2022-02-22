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
public class region_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String region_uuid;
	
	@NotNull
	@Column(unique = true)
	@Size(min=1, max = 80)
	private String region_name;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "country", referencedColumnName = "country_uuid", insertable = true, updatable = true)
	private country_table country;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "region")
    private List<district_table> districtList = new ArrayList<>();


	public String getRegion_uuid() {
		return region_uuid;
	}

	public void setRegion_uuid(String region_uuid) {
		this.region_uuid = region_uuid;
	}

	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	public country_table getCountry() {
		return country;
	}

	public void setCountry(country_table country) {
		this.country = country;
	}

	public List<district_table> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<district_table> districtList) {
		this.districtList = districtList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((region_uuid == null) ? 0 : region_uuid.hashCode());
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
		region_table other = (region_table) obj;
		if (region_uuid == null) {
			if (other.region_uuid != null)
				return false;
		} else if (!region_uuid.equals(other.region_uuid))
			return false;
		return true;
	}

	

}
