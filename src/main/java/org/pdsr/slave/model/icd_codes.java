package org.pdsr.slave.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.opencsv.bean.CsvBindByName;
import com.sun.istack.NotNull;

@Entity
public class icd_codes implements Serializable {

	
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
	
	@CsvBindByName
	@Column
	private String icd_pma;
	
	@CsvBindByName
	@Column
	private String icd_pma_desc;
	
	@CsvBindByName
	@Column
	private String icd_pmi;
	
	@CsvBindByName
	@Column
	private String icd_pmi_desc;
	
	@CsvBindByName
	@Column
	private String icd_pmn;
	
	@CsvBindByName
	@Column
	private String icd_pmn_desc;

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

	public String getIcd_pma() {
		return icd_pma;
	}

	public void setIcd_pma(String icd_pma) {
		this.icd_pma = icd_pma;
	}

	public String getIcd_pma_desc() {
		return icd_pma_desc;
	}

	public void setIcd_pma_desc(String icd_pma_desc) {
		this.icd_pma_desc = icd_pma_desc;
	}

	public String getIcd_pmi() {
		return icd_pmi;
	}

	public void setIcd_pmi(String icd_pmi) {
		this.icd_pmi = icd_pmi;
	}

	public String getIcd_pmi_desc() {
		return icd_pmi_desc;
	}

	public void setIcd_pmi_desc(String icd_pmi_desc) {
		this.icd_pmi_desc = icd_pmi_desc;
	}

	public String getIcd_pmn() {
		return icd_pmn;
	}

	public void setIcd_pmn(String icd_pmn) {
		this.icd_pmn = icd_pmn;
	}

	public String getIcd_pmn_desc() {
		return icd_pmn_desc;
	}

	public void setIcd_pmn_desc(String icd_pmn_desc) {
		this.icd_pmn_desc = icd_pmn_desc;
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
		icd_codes other = (icd_codes) obj;
		return Objects.equals(icd_code, other.icd_code);
	}
	
}
