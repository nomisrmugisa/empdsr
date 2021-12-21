package org.pdsr.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class diagnoses_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String diagnosis_name;

	@Lob
	@Column
	@Size(max = 65535)
	private String diagnosis_desc;

	public String getDiagnosis_name() {
		return diagnosis_name;
	}

	public void setDiagnosis_name(String diagnosis_name) {
		this.diagnosis_name = diagnosis_name;
	}

	public String getDiagnosis_desc() {
		return diagnosis_desc;
	}

	public void setDiagnosis_desc(String diagnosis_desc) {
		this.diagnosis_desc = diagnosis_desc;
	}

	@Override
	public int hashCode() {
		return Objects.hash(diagnosis_name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		diagnoses_table other = (diagnoses_table) obj;
		return Objects.equals(diagnosis_name, other.diagnosis_name);
	}
	
	
	
}
