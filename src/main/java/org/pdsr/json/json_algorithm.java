package org.pdsr.json;

import java.io.Serializable;
import java.util.Deque;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_algorithm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private java.util.Date alg_date;

	private Integer alg_year;//YYYY
	private Integer alg_month;//0,1,2,3,4,5,6,7,8,9,10,11
	private Integer alg_week;//1,2,3,4
	private Integer alg_modulo;
	
	private Integer alg_neonatal;
	private Integer alg_stillbirth;
	private Integer alg_totalcases;
	
	
	private Deque<Integer> alg_deque; 
	

	public json_algorithm() {
		super();
	}
	
	public Integer getAlg_year() {
		return alg_year;
	}
	public void setAlg_year(Integer alg_year) {
		this.alg_year = alg_year;
	}
	public Integer getAlg_month() {
		return alg_month;
	}
	public void setAlg_month(Integer alg_month) {
		this.alg_month = alg_month;
	}
	public Integer getAlg_week() {
		return alg_week;
	}
	public void setAlg_week(Integer alg_week) {
		this.alg_week = alg_week;
	}
	public java.util.Date getAlg_date() {
		return alg_date;
	}
	public void setAlg_date(java.util.Date alg_date) {
		this.alg_date = alg_date;
	}
	public Deque<Integer> getAlg_deque() {
		return alg_deque;
	}
	public void setAlg_deque(Deque<Integer> alg_deque) {
		this.alg_deque = alg_deque;
	}
	public Integer getAlg_modulo() {
		return alg_modulo;
	}
	public void setAlg_modulo(Integer alg_modulo) {
		this.alg_modulo = alg_modulo;
	}

	public Integer getAlg_neonatal() {
		return alg_neonatal;
	}

	public void setAlg_neonatal(Integer alg_neonatal) {
		this.alg_neonatal = alg_neonatal;
	}

	public Integer getAlg_stillbirth() {
		return alg_stillbirth;
	}

	public void setAlg_stillbirth(Integer alg_stillbirth) {
		this.alg_stillbirth = alg_stillbirth;
	}

	public Integer getAlg_totalcases() {
		return alg_totalcases;
	}

	public void setAlg_totalcases(Integer alg_totalcases) {
		this.alg_totalcases = alg_totalcases;
	}

}
