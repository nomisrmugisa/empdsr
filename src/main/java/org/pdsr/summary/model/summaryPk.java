package org.pdsr.summary.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class summaryPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column
	private String facility_code;
	@Column
	private String wmdesc;
	
	public summaryPk() {
		super();
	}
	
	public summaryPk(String facility_code, String wmdesc) {
		super();
		this.facility_code = facility_code;
		this.wmdesc = wmdesc;
	}

	public String getFacility_code() {
		return facility_code;
	}

	public void setFacility_code(String facility_code) {
		this.facility_code = facility_code;
	}

	public String getWmdesc() {
		return wmdesc;
	}

	public void setWmdesc(String wmdesc) {
		this.wmdesc = wmdesc;
	}

	@Override
	public int hashCode() {
		return Objects.hash(facility_code, wmdesc);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		summaryPk other = (summaryPk) obj;
		return Objects.equals(facility_code, other.facility_code) && Objects.equals(wmdesc, other.wmdesc);
	}

	

}
