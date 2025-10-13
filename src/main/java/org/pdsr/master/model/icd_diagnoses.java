package org.pdsr.master.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.sun.istack.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class icd_diagnoses implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@CsvBindByName
	@Id
	@NotNull
	private String icd_code;
	
	@CsvBindByName
	@Column
	@NotNull
	private String icd_desc;
	

	public String getIcd_code() {
		return icd_code;
	}

	public void setIcd_code(String icd_code) {
		this.icd_code = icd_code;
	}

	public String getIcd_desc() {
		return icd_desc;
	}

	public void setIcd_desc(String icd_desc) {
		this.icd_desc = icd_desc;
	}

	@Override
	public int hashCode() {
		return Objects.hash(icd_code);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		icd_diagnoses other = (icd_diagnoses) obj;
		return Objects.equals(icd_code, other.icd_code);
	}
	
}
