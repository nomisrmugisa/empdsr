package org.pdsr.master.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
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
public class case_birth implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String birth_uuid;

	@MapsId
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "case_uuid", referencedColumnName = "case_uuid", insertable = true, updatable = true)
	private case_identifiers case_uuid;

	
	@Column
	private Integer birth_mode;

	
	@Column
	private Integer birth_insistnormal;

	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate birth_csproposedate;

	@Column
	private Integer birth_csproposehour;
	
	
	@Column
	private Integer birth_csproposeminute;
	
	
	@Column
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime birth_csproposetime;


	
	@Column
	private Integer birth_provider;
	
	
	@Column
	private Integer birth_facility;
	
	
	@Column
	private Integer birth_abnormalities;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "birth_abnormalities", joinColumns = @JoinColumn(name = "abnormal_uuid"), inverseJoinColumns = @JoinColumn(name = "abnormal_name"))
	private List<abnormality_table> abnormalities;

	@Lob
	@Column
	private String new_abnormalities;

	
	@Column
	private Integer birth_cordfaults;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "birth_cordfaults", joinColumns = @JoinColumn(name = "cordfault_uuid"), inverseJoinColumns = @JoinColumn(name = "cordfault_name"))
	private List<cordfault_table> cordfaults = new ArrayList<>();

	@Lob
	@Column
	private String new_cordfaults;
	
	
	@Column
	private Integer birth_placentachecks;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "birth_pacentachecks", joinColumns = @JoinColumn(name = "placentacheck_uuid"), inverseJoinColumns = @JoinColumn(name = "placentacheck_name"))
	private List<placentacheck_table> placentachecks = new ArrayList<>();

	@Lob
	@Column
	private String new_placentachecks;
	
	
	@Column
	private Integer birth_liqourvolume;
	
	
	@Column
	private Integer birth_liqourcolor;
	
	
	@Column
	private Integer birth_liqourodour;
	
	
	@Column
	private Integer birth_motheroutcome;

	
	@Column
	private Integer birth_babyoutcome;

	@Lob
	@Column
	private String birth_json;

	@Column
	private Integer data_complete;

	public Integer getData_complete() {
		return data_complete;
	}

	public void setData_complete(Integer data_complete) {
		this.data_complete = data_complete;
	}

	public String getBirth_json() {
		return birth_json;
	}

	public void setBirth_json(String birth_json) {
		this.birth_json = birth_json;
	}

	public String getBirth_uuid() {
		return birth_uuid;
	}

	public void setBirth_uuid(String birth_uuid) {
		this.birth_uuid = birth_uuid;
	}

	public case_identifiers getCase_uuid() {
		return case_uuid;
	}

	public void setCase_uuid(case_identifiers case_uuid) {
		this.case_uuid = case_uuid;
	}

	public Integer getBirth_mode() {
		return birth_mode;
	}

	public void setBirth_mode(Integer birth_mode) {
		this.birth_mode = birth_mode;
	}

	public Integer getBirth_insistnormal() {
		return birth_insistnormal;
	}

	public void setBirth_insistnormal(Integer birth_insistnormal) {
		this.birth_insistnormal = birth_insistnormal;
	}

	public LocalDate getBirth_csproposedate() {
		return birth_csproposedate;
	}

	public void setBirth_csproposedate(LocalDate birth_csproposedate) {
		this.birth_csproposedate = birth_csproposedate;
	}

	public Integer getBirth_csproposehour() {
		return birth_csproposehour;
	}

	public void setBirth_csproposehour(Integer birth_csproposehour) {
		this.birth_csproposehour = birth_csproposehour;
	}

	public Integer getBirth_csproposeminute() {
		return birth_csproposeminute;
	}

	public void setBirth_csproposeminute(Integer birth_csproposeminute) {
		this.birth_csproposeminute = birth_csproposeminute;
	}

	public Integer getBirth_provider() {
		return birth_provider;
	}

	public void setBirth_provider(Integer birth_provider) {
		this.birth_provider = birth_provider;
	}

	public Integer getBirth_facility() {
		return birth_facility;
	}

	public void setBirth_facility(Integer birth_facility) {
		this.birth_facility = birth_facility;
	}

	public Integer getBirth_abnormalities() {
		return birth_abnormalities;
	}

	public void setBirth_abnormalities(Integer birth_abnormalities) {
		this.birth_abnormalities = birth_abnormalities;
	}

	public List<abnormality_table> getAbnormalities() {
		return abnormalities;
	}

	public void setAbnormalities(List<abnormality_table> abnormalities) {
		this.abnormalities = abnormalities;
	}

	public String getNew_abnormalities() {
		return new_abnormalities;
	}

	public void setNew_abnormalities(String new_abnormalities) {
		this.new_abnormalities = new_abnormalities;
	}

	public Integer getBirth_cordfaults() {
		return birth_cordfaults;
	}

	public void setBirth_cordfaults(Integer birth_cordfaults) {
		this.birth_cordfaults = birth_cordfaults;
	}

	public List<cordfault_table> getCordfaults() {
		return cordfaults;
	}

	public void setCordfaults(List<cordfault_table> cordfaults) {
		this.cordfaults = cordfaults;
	}

	public String getNew_cordfaults() {
		return new_cordfaults;
	}

	public void setNew_cordfaults(String new_cordfaults) {
		this.new_cordfaults = new_cordfaults;
	}


	public Integer getBirth_placentachecks() {
		return birth_placentachecks;
	}

	public void setBirth_placentachecks(Integer birth_placentachecks) {
		this.birth_placentachecks = birth_placentachecks;
	}

	public List<placentacheck_table> getPlacentachecks() {
		return placentachecks;
	}

	public void setPlacentachecks(List<placentacheck_table> placentachecks) {
		this.placentachecks = placentachecks;
	}

	public String getNew_placentachecks() {
		return new_placentachecks;
	}

	public void setNew_placentachecks(String new_placentachecks) {
		this.new_placentachecks = new_placentachecks;
	}

	public Integer getBirth_liqourvolume() {
		return birth_liqourvolume;
	}

	public void setBirth_liqourvolume(Integer birth_liqourvolume) {
		this.birth_liqourvolume = birth_liqourvolume;
	}

	public Integer getBirth_liqourcolor() {
		return birth_liqourcolor;
	}

	public void setBirth_liqourcolor(Integer birth_liqourcolor) {
		this.birth_liqourcolor = birth_liqourcolor;
	}

	public Integer getBirth_liqourodour() {
		return birth_liqourodour;
	}

	public void setBirth_liqourodour(Integer birth_liqourodour) {
		this.birth_liqourodour = birth_liqourodour;
	}


	public Integer getBirth_motheroutcome() {
		return birth_motheroutcome;
	}

	public void setBirth_motheroutcome(Integer birth_motheroutcome) {
		this.birth_motheroutcome = birth_motheroutcome;
	}

	public LocalTime getBirth_csproposetime() {
		return birth_csproposetime;
	}

	public void setBirth_csproposetime(LocalTime birth_csproposetime) {
		this.birth_csproposetime = birth_csproposetime;
	}

	public Integer getBirth_babyoutcome() {
		return birth_babyoutcome;
	}

	public void setBirth_babyoutcome(Integer birth_babyoutcome) {
		this.birth_babyoutcome = birth_babyoutcome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birth_uuid == null) ? 0 : birth_uuid.hashCode());
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
		case_birth other = (case_birth) obj;
		if (birth_uuid == null) {
			if (other.birth_uuid != null)
				return false;
		} else if (!birth_uuid.equals(other.birth_uuid))
			return false;
		return true;
	}
	
	
}
