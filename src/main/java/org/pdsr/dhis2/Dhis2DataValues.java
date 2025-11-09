package org.pdsr.dhis2;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Dhis2DataValues implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String dataElement;
	
	private String value;

	public Dhis2DataValues() {
		super();
	}

	public Dhis2DataValues(String dataElement, String value) {
		super();
		this.dataElement = dataElement;
		this.value = value;
	}
	
	public Dhis2DataValues(String dataElement, Integer value) {
		super();
		this.dataElement = dataElement;
		this.value = "" + value;
	}
	
	public Dhis2DataValues(String dataElement, Double value) {
		super();
		this.dataElement = dataElement;
		this.value = "" + value;
	}

	public String getDataElement() {
		return dataElement;
	}

	public void setDataElement(String dataElement) {
		this.dataElement = dataElement;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataElement);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dhis2DataValues other = (Dhis2DataValues) obj;
		return Objects.equals(dataElement, other.dataElement);
	}

	@Override
	public String toString() {
		return "json_dhis2_dataValues [dataElement=" + dataElement + ", value=" + value + "]";
	}

	
	
	

}
