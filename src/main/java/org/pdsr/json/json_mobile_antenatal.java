package org.pdsr.json;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_mobile_antenatal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String record_id;

	private Integer antenatal_gravida;

	private Integer antenatal_para;

	private Integer antenatal_attend;

	private Integer antenatal_attendno;

	private String antenatal_facility;

	private Integer antenatal_weeks;// first visit

	private Integer antenatal_days;// first visit

	private Integer antenatal_hiv;// first visit

	private Integer antenatal_art;// first visit

	private Integer antenatal_alcohol;// first visit

	private Integer antenatal_smoker;// first visit

	private Integer antenatal_herbal;// first visit

	private Integer antenatal_folicacid;// first visit

	private Integer antenatal_folicacid3m;// first visit

	private Integer antenatal_tetanus;// first visit

	private Integer antenatal_malprophy;// firisit

	private Integer antenatal_risks;// firisit

	private String new_risks;

	private Integer data_complete;

	public Integer getData_complete() {
		return data_complete;
	}

	public void setData_complete(Integer data_complete) {
		this.data_complete = data_complete;
	}


	public Integer getAntenatal_gravida() {
		return antenatal_gravida;
	}

	public void setAntenatal_gravida(Integer antenatal_gravida) {
		this.antenatal_gravida = antenatal_gravida;
	}

	public Integer getAntenatal_para() {
		return antenatal_para;
	}

	public void setAntenatal_para(Integer antenatal_para) {
		this.antenatal_para = antenatal_para;
	}

	public Integer getAntenatal_attend() {
		return antenatal_attend;
	}

	public void setAntenatal_attend(Integer antenatal_attend) {
		this.antenatal_attend = antenatal_attend;
	}

	public Integer getAntenatal_attendno() {
		return antenatal_attendno;
	}

	public void setAntenatal_attendno(Integer antenatal_attendno) {
		this.antenatal_attendno = antenatal_attendno;
	}

	public String getAntenatal_facility() {
		return antenatal_facility;
	}

	public void setAntenatal_facility(String antenatal_facility) {
		this.antenatal_facility = antenatal_facility;
	}

	public Integer getAntenatal_weeks() {
		return antenatal_weeks;
	}

	public void setAntenatal_weeks(Integer antenatal_weeks) {
		this.antenatal_weeks = antenatal_weeks;
	}

	public Integer getAntenatal_days() {
		return antenatal_days;
	}

	public void setAntenatal_days(Integer antenatal_days) {
		this.antenatal_days = antenatal_days;
	}

	public Integer getAntenatal_hiv() {
		return antenatal_hiv;
	}

	public void setAntenatal_hiv(Integer antenatal_hivpos) {
		this.antenatal_hiv = antenatal_hivpos;
	}

	public Integer getAntenatal_alcohol() {
		return antenatal_alcohol;
	}

	public void setAntenatal_alcohol(Integer antenatal_alcohol) {
		this.antenatal_alcohol = antenatal_alcohol;
	}

	public Integer getAntenatal_smoker() {
		return antenatal_smoker;
	}

	public void setAntenatal_smoker(Integer antenatal_smoker) {
		this.antenatal_smoker = antenatal_smoker;
	}

	public Integer getAntenatal_herbal() {
		return antenatal_herbal;
	}

	public void setAntenatal_herbal(Integer antenatal_herbal) {
		this.antenatal_herbal = antenatal_herbal;
	}

	public Integer getAntenatal_folicacid() {
		return antenatal_folicacid;
	}

	public void setAntenatal_folicacid(Integer antenatal_folicacid) {
		this.antenatal_folicacid = antenatal_folicacid;
	}

	public Integer getAntenatal_folicacid3m() {
		return antenatal_folicacid3m;
	}

	public void setAntenatal_folicacid3m(Integer antenatal_folicacid3m) {
		this.antenatal_folicacid3m = antenatal_folicacid3m;
	}

	public Integer getAntenatal_risks() {
		return antenatal_risks;
	}

	public void setAntenatal_risks(Integer antenatal_risks) {
		this.antenatal_risks = antenatal_risks;
	}

	public Integer getAntenatal_tetanus() {
		return antenatal_tetanus;
	}

	public void setAntenatal_tetanus(Integer antenatal_tetanus) {
		this.antenatal_tetanus = antenatal_tetanus;
	}

	public Integer getAntenatal_malprophy() {
		return antenatal_malprophy;
	}

	public void setAntenatal_malprophy(Integer antenatal_malprophy) {
		this.antenatal_malprophy = antenatal_malprophy;
	}

	public String getNew_risks() {
		return new_risks;
	}

	public void setNew_risks(String new_risks) {
		this.new_risks = new_risks;
	}

	public Integer getAntenatal_art() {
		return antenatal_art;
	}

	public void setAntenatal_art(Integer antenatal_art) {
		this.antenatal_art = antenatal_art;
	}

	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
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
		json_mobile_antenatal other = (json_mobile_antenatal) obj;
		return Objects.equals(record_id, other.record_id);
	}

	@Override
	public String toString() {
		return record_id;
	}


}
