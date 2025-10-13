package org.pdsr.pojos;

import java.io.Serializable;
import java.util.Objects;

import com.opencsv.bean.CsvBindByName;

public class facilities implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@CsvBindByName
	private String facility_code;//facility code for country is the license used to activate the program
	
	@CsvBindByName
	private String facility_name;
	
	@CsvBindByName
	private Integer facility_type; //0 National, 1 region, 2 district, 3 facility
	
	@CsvBindByName
    private String parent_code;
	

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
	public String getParent_code() {
		return parent_code;
	}

	public void setParent_code(String parent_code) {
		this.parent_code = parent_code;
	}

	@Override
	public int hashCode() {
		return Objects.hash(facility_code);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		facilities other = (facilities) obj;
		return Objects.equals(facility_code, other.facility_code);
	}
	
}
