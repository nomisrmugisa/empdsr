package org.pdsr.summary.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SummaryPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column
	private String id;
	@Column
	private String code;
	@Column 
	private String country;
	@Column
	private String region;
	@Column
	private String district;
	
	
	public SummaryPK() {
		super();
	}


	public SummaryPK(String id, String code, String country, String region, String district) {
		super();
		this.id = id;
		this.code = code;
		this.country = country;
		this.region = region;
		this.district = district;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}


	public String getDistrict() {
		return district;
	}


	public void setDistrict(String district) {
		this.district = district;
	}


	@Override
	public int hashCode() {
		return Objects.hash(code, country, district, id, region);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SummaryPK other = (SummaryPK) obj;
		return Objects.equals(code, other.code) && Objects.equals(country, other.country)
				&& Objects.equals(district, other.district) && Objects.equals(id, other.id)
				&& Objects.equals(region, other.region);
	}


	
	
}
