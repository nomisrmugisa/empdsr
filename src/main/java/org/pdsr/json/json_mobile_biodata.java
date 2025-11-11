package org.pdsr.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_mobile_biodata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String record_id;

	private Integer biodata_sex;

	private Integer biodata_mage;

	private Integer biodata_medu;
	
	private Integer data_complete;


	public Integer getBiodata_sex() {
		return biodata_sex;
	}

	public void setBiodata_sex(Integer biodata_sex) {
		this.biodata_sex = biodata_sex;
	}

	public Integer getBiodata_mage() {
		return biodata_mage;
	}

	public void setBiodata_mage(Integer biodata_mage) {
		this.biodata_mage = biodata_mage;
	}

	public Integer getBiodata_medu() {
		return biodata_medu;
	}

	public void setBiodata_medu(Integer biodata_medu) {
		this.biodata_medu = biodata_medu;
	}
	
	public Integer getData_complete() {
		return data_complete;
	}

	public void setData_complete(Integer data_complete) {
		this.data_complete = data_complete;
	}

	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((record_id == null) ? 0 : record_id.hashCode());
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
		json_mobile_biodata other = (json_mobile_biodata) obj;
		if (record_id == null) {
			if (other.record_id != null)
				return false;
		} else if (!record_id.equals(other.record_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return record_id;
	}

	
}
