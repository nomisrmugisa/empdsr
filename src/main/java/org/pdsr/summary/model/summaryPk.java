package org.pdsr.summary.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SummaryPk implements Serializable {

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
	
	
	public SummaryPk() {
		super();
	}


	public SummaryPk(String id, String code, String country, String region, String district) {
		super();
		this.id = id;
		this.code = code;
		this.country = country;
		this.region = region;
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
		SummaryPk other = (SummaryPk) obj;
		return Objects.equals(code, other.code) && Objects.equals(country, other.country)
				&& Objects.equals(district, other.district) && Objects.equals(id, other.id)
				&& Objects.equals(region, other.region);
	}


	
	
}
