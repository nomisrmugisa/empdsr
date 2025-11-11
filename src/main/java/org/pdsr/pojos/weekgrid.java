package org.pdsr.pojos;

import java.io.Serializable;
import java.util.List;

import org.pdsr.master.model.weekly_table;

public class weekgrid implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String grid_yearmonth;
	
	private Integer grid_year;
	private Integer grid_month;
	
	private List<weekly_table> grid_weekly;
	

	public String getGrid_yearmonth() {
		return grid_yearmonth;
	}

	public void setGrid_yearmonth(String grid_yearmonth) {
		this.grid_yearmonth = grid_yearmonth;
	}

	public List<weekly_table> getGrid_weekly() {
		return grid_weekly;
	}

	public void setGrid_weekly(List<weekly_table> grid_weekly) {
		this.grid_weekly = grid_weekly;
	}

	public Integer getGrid_year() {
		return grid_year;
	}

	public void setGrid_year(Integer grid_year) {
		this.grid_year = grid_year;
	}

	public Integer getGrid_month() {
		return grid_month;
	}

	public void setGrid_month(Integer grid_month) {
		this.grid_month = grid_month;
	}
	
	

}
