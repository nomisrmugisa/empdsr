package org.pdsr.json;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_mobile_labour implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String record_id;

	private Date labour_seedate;

	private Integer labour_seehour;
	
	private Integer labour_seeminute;
	
	private Date labour_seetime;

	private Integer labour_seedatetime_notstated;

	private Integer labour_seeperiod;
	
	private Integer labour_startmode;
	
	private Integer labour_herbalaug;
	
	private Integer labour_partograph;
	
	private Integer labour_lasthour1;
	
	private Integer labour_lastminute1;
	
	private Integer labour_lasthour2;
	
	private Integer labour_lastminute2;
	
	private Integer labour_complications;
	
	private String new_complications;

	private Integer data_complete;

	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	public Date getLabour_seedate() {
		return labour_seedate;
	}

	public void setLabour_seedate(Date labour_seedate) {
		this.labour_seedate = labour_seedate;
	}

	public Integer getLabour_seehour() {
		return labour_seehour;
	}

	public void setLabour_seehour(Integer labour_seehour) {
		this.labour_seehour = labour_seehour;
	}

	public Integer getLabour_seeminute() {
		return labour_seeminute;
	}

	public void setLabour_seeminute(Integer labour_seeminute) {
		this.labour_seeminute = labour_seeminute;
	}

	public Date getLabour_seetime() {
		return labour_seetime;
	}

	public void setLabour_seetime(Date labour_seetime) {
		this.labour_seetime = labour_seetime;
	}

	public Integer getLabour_seedatetime_notstated() {
		return labour_seedatetime_notstated;
	}

	public void setLabour_seedatetime_notstated(Integer labour_seedatetime_notstated) {
		this.labour_seedatetime_notstated = labour_seedatetime_notstated;
	}

	public Integer getLabour_seeperiod() {
		return labour_seeperiod;
	}

	public void setLabour_seeperiod(Integer labour_seeperiod) {
		this.labour_seeperiod = labour_seeperiod;
	}

	public Integer getLabour_startmode() {
		return labour_startmode;
	}

	public void setLabour_startmode(Integer labour_startmode) {
		this.labour_startmode = labour_startmode;
	}

	public Integer getLabour_herbalaug() {
		return labour_herbalaug;
	}

	public void setLabour_herbalaug(Integer labour_herbalaug) {
		this.labour_herbalaug = labour_herbalaug;
	}

	public Integer getLabour_partograph() {
		return labour_partograph;
	}

	public void setLabour_partograph(Integer labour_partograph) {
		this.labour_partograph = labour_partograph;
	}

	public Integer getLabour_lasthour1() {
		return labour_lasthour1;
	}

	public void setLabour_lasthour1(Integer labour_lasthour1) {
		this.labour_lasthour1 = labour_lasthour1;
	}

	public Integer getLabour_lastminute1() {
		return labour_lastminute1;
	}

	public void setLabour_lastminute1(Integer labour_lastminute1) {
		this.labour_lastminute1 = labour_lastminute1;
	}

	public Integer getLabour_lasthour2() {
		return labour_lasthour2;
	}

	public void setLabour_lasthour2(Integer labour_lasthour2) {
		this.labour_lasthour2 = labour_lasthour2;
	}

	public Integer getLabour_lastminute2() {
		return labour_lastminute2;
	}

	public void setLabour_lastminute2(Integer labour_lastminute2) {
		this.labour_lastminute2 = labour_lastminute2;
	}

	public Integer getLabour_complications() {
		return labour_complications;
	}

	public void setLabour_complications(Integer labour_complications) {
		this.labour_complications = labour_complications;
	}

	public String getNew_complications() {
		return new_complications;
	}

	public void setNew_complications(String new_complications) {
		this.new_complications = new_complications;
	}

	public Integer getData_complete() {
		return data_complete;
	}

	public void setData_complete(Integer data_complete) {
		this.data_complete = data_complete;
	}

	@Override
	public int hashCode() {
		return Objects.hash(record_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		json_mobile_labour other = (json_mobile_labour) obj;
		return Objects.equals(record_id, other.record_id);
	}

	@Override
	public String toString() {
		return record_id;
	}



	
}
