package org.pdsr.summary.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class big_weekly_monitoring implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private SummaryPk summaryPk;

	@Column
	private Integer weekly_year;

	@Column
	private Integer weekly_month;

	@Column
	private String weekly_mdesc;

	@Column
	private Integer weekly_week;

	@Column
	private Integer mindex;

    @Column
    private Integer wm_values;
    
    @Column
    private Integer wm_subval;
    

	public SummaryPk getSummaryPk() {
		return summaryPk;
	}

	public void setSummaryPk(SummaryPk summaryPk) {
		this.summaryPk = summaryPk;
	}

	public Integer getWeekly_year() {
		return weekly_year;
	}

	public void setWeekly_year(Integer weekly_year) {
		this.weekly_year = weekly_year;
	}

	public Integer getWeekly_month() {
		return weekly_month;
	}

	public void setWeekly_month(Integer weekly_month) {
		this.weekly_month = weekly_month;
	}

	public String getWeekly_mdesc() {
		return weekly_mdesc;
	}

	public void setWeekly_mdesc(String weekly_mdesc) {
		this.weekly_mdesc = weekly_mdesc;
	}

	public Integer getWeekly_week() {
		return weekly_week;
	}

	public void setWeekly_week(Integer weekly_week) {
		this.weekly_week = weekly_week;
	}

	public Integer getMindex() {
		return mindex;
	}

	public void setMindex(Integer mindex) {
		this.mindex = mindex;
	}

	public Integer getWm_values() {
		return wm_values;
	}

	public void setWm_values(Integer wm_values) {
		this.wm_values = wm_values;
	}

	public Integer getWm_subval() {
		return wm_subval;
	}

	public void setWm_subval(Integer wm_subval) {
		this.wm_subval = wm_subval;
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
		big_weekly_monitoring other = (big_weekly_monitoring) obj;
		return Objects.equals(summaryPk, other.summaryPk);
	}


}
