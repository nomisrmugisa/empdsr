package org.pdsr.master.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
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

	@JsonIgnore
	@MapsId
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "case_uuid", referencedColumnName = "case_uuid", insertable = true, updatable = true)
	private case_identifiers case_uuid;

	
	@Column
	private Integer labour_occured;
	
	
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date labour_seedate;

	
	@Column
	private Integer labour_seehour;
	
	
	@Column
	private Integer labour_seeminute;
	
	
	@Column
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date labour_seetime;

	
	@Column
	private Integer labour_seedatetime_notstated;

	@Column
	private Integer labour_seeperiod;
	
	@Column
	private Integer labour_admission;
	
	@Column
	private Integer labour_admission_cond;
	
	@Column
	@Size(max = 80)
	private String labour_blood_pressure;
	
	@Column
	@Size(max = 80)
	private String labour_pulsebp;
	
	@Column
	@Size(max = 80)
	private String labour_temptr;
	
	@Column
	@Size(max = 80)
	private String labour_respr;	
	
	@Column
	private Integer labour_lvlcons;
	
	@Column
	@Size(max = 65535)
	private String labour_whyadmiss;
	
	@Column
	@Size(max = 65535)
	private String labour_admiss_diag;	
	
	@Column
	private Integer labour_herbalind;
	
	@Column
	private Integer labour_startmode;
	
	
	@Column
	private Integer labour_herbalaug;
	
	
	@Column
	private Integer labour_partograph;
	
	@Column
	private Integer labour_partograph_yes;
	
	@Lob
	@Column
	private String labour_partograph_no;
	
	@Column
	private Integer labour_lasthour0;
	
	
	@Column
	private Integer labour_lastminute0;
	
	@Column
	private Integer labour_lasthour1;
	
	
	@Column
	private Integer labour_lastminute1;
	
	
	@Column
	private Integer labour_lasthour2;
	
	
	@Column
	private Integer labour_lastminute2;
	
	@Column
	private Integer labour_lasthour3;	
	
	@Column
	private Integer labour_lastminute3;
	
	
	@Column
	private Integer labour_complications;
	
    @ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "labour_complications", joinColumns = @JoinColumn(name = "complication_uuid"), inverseJoinColumns = @JoinColumn(name = "complication_name"))
	private List<complication_table> complications = new ArrayList<>();

	@Lob
	@Column
	private String new_complications;
	
	@JsonIgnore
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

	public Integer getLabour_lasthour0() {
		return labour_lasthour0;
	}

	public void setLabour_lasthour0(Integer labour_lasthour0) {
		this.labour_lasthour0 = labour_lasthour0;
	}

	public Integer getLabour_lastminute0() {
		return labour_lastminute0;
	}

	public void setLabour_lastminute0(Integer labour_lastminute0) {
		this.labour_lastminute0 = labour_lastminute0;
	}

	public Integer getLabour_lasthour3() {
		return labour_lasthour3;
	}

	public void setLabour_lasthour3(Integer labour_lasthour3) {
		this.labour_lasthour3 = labour_lasthour3;
	}

	public Integer getLabour_lastminute3() {
		return labour_lastminute3;
	}

	public void setLabour_lastminute3(Integer labour_lastminute3) {
		this.labour_lastminute3 = labour_lastminute3;
	}

	public Integer getLabour_admission() {
		return labour_admission;
	}

	public void setLabour_admission(Integer labour_admission) {
		this.labour_admission = labour_admission;
	} 

	public Integer getLabour_admission_cond() {
		return labour_admission_cond;
	}

	public void setLabour_admission_cond(Integer labour_admission_cond) {
		this.labour_admission_cond = labour_admission_cond;
	}	

	public String getLabour_blood_pressure() {
		return labour_blood_pressure;
	}

	public void setLabour_blood_pressure(String labour_blood_pressure) {
		this.labour_blood_pressure = labour_blood_pressure;
	}

	public String getLabour_pulsebp() {
		return labour_pulsebp;
	}

	public void setLabour_pulsebp(String labour_pulsebp) {
		this.labour_pulsebp = labour_pulsebp;
	}

	public String getLabour_temptr() {
		return labour_temptr;
	}

	public void setLabour_temptr(String labour_temptr) {
		this.labour_temptr = labour_temptr;
	}

	public String getLabour_respr() {
		return labour_respr;
	}

	public void setLabour_respr(String labour_respr) {
		this.labour_respr = labour_respr;
	}

	public Integer getLabour_lvlcons() {
		return labour_lvlcons;
	}

	public void setLabour_lvlcons(Integer labour_lvlcons) {
		this.labour_lvlcons = labour_lvlcons;
	}

	public String getLabour_whyadmiss() {
		return labour_whyadmiss;
	}

	public void setLabour_whyadmiss(String labour_whyadmiss) {
		this.labour_whyadmiss = labour_whyadmiss;
	}

	public String getLabour_admiss_diag() {
		return labour_admiss_diag;
	}

	public void setLabour_admiss_diag(String labour_admiss_diag) {
		this.labour_admiss_diag = labour_admiss_diag;
	}

	public Integer getLabour_partograph_yes() {
		return labour_partograph_yes;
	}

	public void setLabour_partograph_yes(Integer labour_partograph_yes) {
		this.labour_partograph_yes = labour_partograph_yes;
	}

	public String getLabour_partograph_no() {
		return labour_partograph_no;
	}

	public void setLabour_partograph_no(String labour_partograph_no) {
		this.labour_partograph_no = labour_partograph_no;
	}

	public Date getLabour_seetime() {
		return labour_seetime;
	}

	public void setLabour_seetime(Date labour_seetime) {
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


	public Integer getLabour_occured() {
		return labour_occured;
	}

	public void setLabour_occured(Integer labour_occured) {
		this.labour_occured = labour_occured;
	}

	public Integer getLabour_herbalind() {
		return labour_herbalind;
	}

	public void setLabour_herbalind(Integer labour_herbalind) {
		this.labour_herbalind = labour_herbalind;
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
