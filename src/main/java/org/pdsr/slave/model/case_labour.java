package org.pdsr.slave.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class case_labour implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String labour_uuid;

	@MapsId
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "case_uuid", referencedColumnName = "case_uuid", insertable = true, updatable = true)
	private case_identifiers case_uuid;

	
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate labour_seedate;

	
	@Column
	private Integer labour_seehour;
	
	
	@Column
	private Integer labour_seeminute;
	
	
	@Column
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private java.util.Date labour_seetime;

	@Column
	private Integer labour_seedatetime_notstated;


	@Column
	private Integer labour_seeperiod;
	
	
	@Column
	private Integer labour_startmode;
	
	
	@Column
	private Integer labour_herbalaug;
	
	
	@Column
	private Integer labour_partograph;
	
	
	@Column
	private Integer labour_lasthour1;
	
	
	@Column
	private Integer labour_lastminute1;
	
	
	@Column
	private Integer labour_lasthour2;
	
	
	@Column
	private Integer labour_lastminute2;
	
	
	@Column
	private Integer labour_complications;
	
    @ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "labour_complications", joinColumns = @JoinColumn(name = "complication_uuid"), inverseJoinColumns = @JoinColumn(name = "complication_name"))
	private List<complication_table> complications = new ArrayList<>();

	@Lob
	@Column
	private String new_complications;

	@Lob
	@Column
	private String labour_json;

	@Column
	private Integer data_complete;

	public Integer getData_complete() {
		return data_complete;
	}

	public void setData_complete(Integer data_complete) {
		this.data_complete = data_complete;
	}

	public String getLabour_json() {
		return labour_json;
	}

	public void setLabour_json(String labour_json) {
		this.labour_json = labour_json;
	}

	public String getLabour_uuid() {
		return labour_uuid;
	}

	public void setLabour_uuid(String labour_uuid) {
		this.labour_uuid = labour_uuid;
	}

	public case_identifiers getCase_uuid() {
		return case_uuid;
	}

	public void setCase_uuid(case_identifiers case_uuid) {
		this.case_uuid = case_uuid;
	}

	public LocalDate getLabour_seedate() {
		return labour_seedate;
	}

	public void setLabour_seedate(LocalDate labour_seedate) {
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

	public java.util.Date getLabour_seetime() {
		return labour_seetime;
	}

	public void setLabour_seetime(java.util.Date labour_seetime) {
		this.labour_seetime = labour_seetime;
	}

	public Integer getLabour_complications() {
		return labour_complications;
	}

	public void setLabour_complications(Integer labour_complications) {
		this.labour_complications = labour_complications;
	}

	public List<complication_table> getComplications() {
		return complications;
	}

	public void setComplications(List<complication_table> complications) {
		this.complications = complications;
	}

	public String getNew_complications() {
		return new_complications;
	}

	public void setNew_complications(String new_complications) {
		this.new_complications = new_complications;
	}

	public Integer getLabour_seedatetime_notstated() {
		return labour_seedatetime_notstated;
	}

	public void setLabour_seedatetime_notstated(Integer labour_seedatetime_notstated) {
		this.labour_seedatetime_notstated = labour_seedatetime_notstated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((labour_uuid == null) ? 0 : labour_uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		case_labour other = (case_labour) obj;
		if (labour_uuid == null) {
			if (other.labour_uuid != null)
				return false;
		} else if (!labour_uuid.equals(other.labour_uuid))
			return false;
		return true;
	}
	
	
	
}
