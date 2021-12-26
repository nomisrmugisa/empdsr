package org.pdsr.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.opencsv.bean.CsvBindByName;
import com.sun.istack.NotNull;

@Entity
public class sm_codes implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@CsvBindByName
	@Id
	@NotNull
	private String sm_code;
	
	@CsvBindByName
	@Column
	@NotNull
	private String sm_desc;
	
	@CsvBindByName
	@Column
	private String m_code;
	
	@CsvBindByName
	@Column
	private String m_desc;

	public String getSm_code() {
		return sm_code;
	}

	public void setSm_code(String sm_code) {
		this.sm_code = sm_code;
	}

	public String getSm_desc() {
		return sm_desc;
	}

	public void setSm_desc(String sm_desc) {
		this.sm_desc = sm_desc;
	}

	public String getM_code() {
		return m_code;
	}

	public void setM_code(String m_code) {
		this.m_code = m_code;
	}

	public String getM_desc() {
		return m_desc;
	}

	public void setM_desc(String m_desc) {
		this.m_desc = m_desc;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sm_code);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		sm_codes other = (sm_codes) obj;
		return Objects.equals(sm_code, other.sm_code);
	}
	
	
}
