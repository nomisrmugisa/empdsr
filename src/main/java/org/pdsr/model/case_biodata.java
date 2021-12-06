package org.pdsr.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class case_biodata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String biodata_uuid;

	@MapsId
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "case_uuid", referencedColumnName = "case_uuid", insertable = true, updatable = true)
	private case_identifiers case_uuid;

	@NotNull
	@Column
	private Integer biodata_sex;

	@NotNull
	@Column
	private Integer biodata_mage;

	@NotNull
	@Column
	private Integer biodata_medu;
	
	@Lob
	@Column
	private String biodata_json;

	public String getBiodata_uuid() {
		return biodata_uuid;
	}

	public void setBiodata_uuid(String biodata_uuid) {
		this.biodata_uuid = biodata_uuid;
	}

	public case_identifiers getCase_uuid() {
		return case_uuid;
	}

	public void setCase_uuid(case_identifiers case_uuid) {
		this.case_uuid = case_uuid;
	}

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
	

	public String getBiodata_json() {
		return biodata_json;
	}

	public void setBiodata_json(String biodata_json) {
		this.biodata_json = biodata_json;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((biodata_uuid == null) ? 0 : biodata_uuid.hashCode());
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
		case_biodata other = (case_biodata) obj;
		if (biodata_uuid == null) {
			if (other.biodata_uuid != null)
				return false;
		} else if (!biodata_uuid.equals(other.biodata_uuid))
			return false;
		return true;
	}

}
