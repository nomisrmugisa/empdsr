package org.pdsr.model;

import java.io.Serializable;

import com.opencsv.bean.CsvBindByName;

public class icd_codes implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@CsvBindByName
	private String icd_code;
	
	@CsvBindByName
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
	
	
}
