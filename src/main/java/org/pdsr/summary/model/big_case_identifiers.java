package org.pdsr.summary.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class big_case_identifiers implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	@EmbeddedId
	private SummaryPK summaryPk;
	
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date case_date;

	@Column
	private Integer case_death;//1 stillbirth or 2 early neonatal

	@Column
	private Integer case_status;//0 entry, 1 submitted, 2 selected, 3 reviewed
	
	public SummaryPK getSummaryPk() {
		return summaryPk;
	}

	public void setSummaryPk(SummaryPK summaryPk) {
		this.summaryPk = summaryPk;
	}

	public java.util.Date getCase_date() {
		return case_date;
	}

	public void setCase_date(java.util.Date case_date) {
		this.case_date = case_date;
	}

	public Integer getCase_death() {
		return case_death;
	}

	public void setCase_death(Integer case_death) {
		this.case_death = case_death;
	}

	public Integer getCase_status() {
		return case_status;
	}

	public void setCase_status(Integer case_status) {
		this.case_status = case_status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(summaryPk);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		big_case_identifiers other = (big_case_identifiers) obj;
		return Objects.equals(summaryPk, other.summaryPk);
	}
		
}
