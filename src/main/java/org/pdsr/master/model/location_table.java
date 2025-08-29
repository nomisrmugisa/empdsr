package org.pdsr.master.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;


@Entity(name = "location")
public class location_table implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(nullable = false)
	private String uuid;
	
	@Column(nullable = false)
	private String locationCode;
	
	@Column(nullable = false)
	private String locationName;
	
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<location_table> locations = new ArrayList<>();	
	
	@ManyToOne(optional = true)
    @JoinColumn(name = "parent", referencedColumnName = "uuid", insertable = true, updatable = true)
    private location_table parent;
	
	@Transient
	private location_table newLocation;
	

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public List<location_table> getLocations() {
		return locations;
	}

	public void setLocations(List<location_table> locations) {
		this.locations = locations;
	}

	public location_table getParent() {
		return parent;
	}

	public void setParent(location_table parent) {
		this.parent = parent;
	}

	public location_table getNewLocation() {
		return newLocation;
	}

	public void setNewLocation(location_table newLocation) {
		this.newLocation = newLocation;
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		location_table other = (location_table) obj;
		return Objects.equals(uuid, other.uuid);
	}
	
	
	@Override
	public String toString() {
		return uuid;
	}


}
