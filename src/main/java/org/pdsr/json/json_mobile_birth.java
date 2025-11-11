package org.pdsr.json;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_mobile_birth implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String record_id;

	private Integer birth_mode;

	private Integer birth_insistnormal;

	private Date birth_csproposedate;

	private Integer birth_csproposehour;
	
	private Integer birth_csproposeminute;
		
	private Date birth_csproposetime;

	private Integer birth_provider;
	
	private Integer birth_facility;
	
	private Integer birth_abnormalities;
	
	private String new_abnormalities;

	private Integer birth_cordfaults;
	
	private String new_cordfaults;
	
	private Integer birth_placentachecks;
	
	private String new_placentachecks;
	
	private Integer birth_liqourvolume;
	
	private Integer birth_liqourcolor;
	
	private Integer birth_liqourodour;
	
	private Integer birth_motheroutcome;

	private Integer birth_babyoutcome;

	private String birth_json;

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

	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
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

	public Date getBirth_csproposedate() {
		return birth_csproposedate;
	}

	public void setBirth_csproposedate(Date birth_csproposedate) {
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

	public Date getBirth_csproposetime() {
		return birth_csproposetime;
	}

	public void setBirth_csproposetime(Date birth_csproposetime) {
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
		result = prime * result + ((record_id == null) ? 0 : record_id.hashCode());
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
		json_mobile_birth other = (json_mobile_birth) obj;
		if (record_id == null) {
			if (other.record_id != null)
				return false;
		} else if (!record_id.equals(other.record_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return record_id;
	}
	
	
}
