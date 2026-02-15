package org.pdsr.master.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
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

	@JsonIgnore
	@MapsId
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "case_uuid", referencedColumnName = "case_uuid", insertable = true, updatable = true)
	private case_identifiers case_uuid;

	@Column
	private Integer biodata_sex;

	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date biodata_mdob;

	@Column
	private Integer biodata_mage;

	@Column
	private Integer biodata_medu;

	@Lob
	@Column
	private String biodata_maddress;

	@Lob
	@Column
	private String biodata_location;

	@Column
	private String biodata_village;

	@Lob
	@Column
	private String biodata_contact;

	@Column
	private Integer biodata_work;

	@Column
	private Integer biodata_marital;

	@Column
	private Integer biodata_religion;

	@Column
	private Integer biodata_ethnic;

	@Column
	private Integer biodata_pod;

	@Lob
	@Column
	private String biodata_nok;

	@Column
	private Integer biodata_rnok;

	@Column
	private Integer data_complete;

	@JsonIgnore
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

	public String getBiodata_location() {
		return biodata_location;
	}

	public void setBiodata_location(String biodata_location) {
		this.biodata_location = biodata_location;
	}

	public String getBiodata_village() {
		return biodata_village;
	}

	public void setBiodata_village(String biodata_village) {
		this.biodata_village = biodata_village;
	}

	public Date getBiodata_mdob() {
		return biodata_mdob;
	}

	public void setBiodata_mdob(Date biodata_mdob) {
		this.biodata_mdob = biodata_mdob;
	}

	public String getBiodata_maddress() {
		return biodata_maddress;
	}

	public void setBiodata_maddress(String biodata_maddress) {
		this.biodata_maddress = biodata_maddress;
	}

	public String getBiodata_contact() {
		return biodata_contact;
	}

	public void setBiodata_contact(String biodata_contact) {
		this.biodata_contact = biodata_contact;
	}

	public Integer getBiodata_work() {
		return biodata_work;
	}

	public void setBiodata_work(Integer biodata_work) {
		this.biodata_work = biodata_work;
	}

	public Integer getBiodata_marital() {
		return biodata_marital;
	}

	public void setBiodata_marital(Integer biodata_marital) {
		this.biodata_marital = biodata_marital;
	}

	public Integer getBiodata_religion() {
		return biodata_religion;
	}

	public void setBiodata_religion(Integer biodata_religion) {
		this.biodata_religion = biodata_religion;
	}

	public Integer getBiodata_ethnic() {
		return biodata_ethnic;
	}

	public void setBiodata_ethnic(Integer biodata_ethnic) {
		this.biodata_ethnic = biodata_ethnic;
	}

	public Integer getBiodata_pod() {
		return biodata_pod;
	}

	public void setBiodata_pod(Integer biodata_pod) {
		this.biodata_pod = biodata_pod;
	}

	public String getBiodata_nok() {
		return biodata_nok;
	}

	public void setBiodata_nok(String biodata_nok) {
		this.biodata_nok = biodata_nok;
	}

	public Integer getBiodata_rnok() {
		return biodata_rnok;
	}

	public void setBiodata_rnok(Integer biodata_rnok) {
		this.biodata_rnok = biodata_rnok;
	}

	public Integer getData_complete() {
		return data_complete;
	}

	public void setData_complete(Integer data_complete) {
		this.data_complete = data_complete;
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
