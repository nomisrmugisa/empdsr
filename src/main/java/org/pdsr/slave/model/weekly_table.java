package org.pdsr.slave.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class weekly_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Integer weekly_id;

	@Column
	private Integer weekly_year;

	@Column
	private Integer weekly_month;

	@Column
	private String weekly_mdesc;

	@Column
	private Integer weekly_week;
	
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date weekly_date;

	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "wm_grids")
	private List<weekly_monitoring> statistics = new ArrayList<>();

	public Integer getWeekly_id() {
		return weekly_id;
	}

	public void setWeekly_id(Integer weekly_id) {
		this.weekly_id = weekly_id;
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

	public java.util.Date getWeekly_date() {
		return weekly_date;
	}

	public void setWeekly_date(java.util.Date weekly_date) {
		this.weekly_date = weekly_date;
	}

	public List<weekly_monitoring> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<weekly_monitoring> statistics) {
		this.statistics = statistics;
	}

	@Override
	public int hashCode() {
		return Objects.hash(weekly_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		weekly_table other = (weekly_table) obj;
		return Objects.equals(weekly_id, other.weekly_id);
	}
	
	

}
