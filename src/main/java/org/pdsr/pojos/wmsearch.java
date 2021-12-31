package org.pdsr.pojos;

import java.io.Serializable;

public class wmsearch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer wm_startyear;
	private Integer wm_startmonth;
	private Integer wm_endyear;
	private Integer wm_endmonth;
	public Integer getWm_startyear() {
		return wm_startyear;
	}
	public void setWm_startyear(Integer wm_startyear) {
		this.wm_startyear = wm_startyear;
	}
	public Integer getWm_startmonth() {
		return wm_startmonth;
	}
	public void setWm_startmonth(Integer wm_startmonth) {
		this.wm_startmonth = wm_startmonth;
	}
	public Integer getWm_endyear() {
		return wm_endyear;
	}
	public void setWm_endyear(Integer wm_endyear) {
		this.wm_endyear = wm_endyear;
	}
	public Integer getWm_endmonth() {
		return wm_endmonth;
	}
	public void setWm_endmonth(Integer wm_endmonth) {
		this.wm_endmonth = wm_endmonth;
	}
	
	
}
