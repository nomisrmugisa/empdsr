package org.pdsr.master.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class case_antenatal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String antenatal_uuid;

	@MapsId
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "case_uuid", referencedColumnName = "case_uuid", insertable = true, updatable = true)
	private case_identifiers case_uuid;

	@Column
	private Integer antenatal_gravida;

	@Column
	private Integer antenatal_para;

	@Column
	private Integer antenatal_attend;

	@Column
	private Integer antenatal_attendno;

	@Column
	@Size(max = 80)
	private String antenatal_facility;

	@Column
	private Integer antenatal_weeks;// first visit

	@Column
	private Integer antenatal_days;// first visit

	@Column
	private Integer antenatal_hiv;// first visit

	@Column
	private Integer antenatal_art;// first visit

	@Column
	private Integer antenatal_alcohol;// first visit

	@Column
	private Integer antenatal_smoker;// first visit

	@Column
	private Integer antenatal_herbal;// first visit

	@Column
	private Integer antenatal_folicacid;// first visit

	@Column
	private Integer antenatal_folicacid3m;// first visit

	@Column
	private Integer antenatal_tetanus;// first visit

	@Column
	private Integer antenatal_malprophy;// firisit

	@Column
	private Integer antenatal_risks;// firisit

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "antenatal_risks", joinColumns = @JoinColumn(name = "antenatal_uuid"), inverseJoinColumns = @JoinColumn(name = "risk_name"))
	private List<risk_table> risks = new ArrayList<>();

	@Lob
	@Column
	private String new_risks;

	@Lob
	@Column
	private String antenatal_json;

	@Column
	private Integer data_complete;

	public Integer getData_complete() {
		return data_complete;
	}

	public void setData_complete(Integer data_complete) {
		this.data_complete = data_complete;
	}

	public String getAntenatal_json() {
		return antenatal_json;
	}

	public void setAntenatal_json(String antenatal_json) {
		this.antenatal_json = antenatal_json;
	}

	public String getAntenatal_uuid() {
		return antenatal_uuid;
	}

	public void setAntenatal_uuid(String antenatal_uuid) {
		this.antenatal_uuid = antenatal_uuid;
	}

	public case_identifiers getCase_uuid() {
		return case_uuid;
	}

	public void setCase_uuid(case_identifiers case_uuid) {
		this.case_uuid = case_uuid;
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

	public List<risk_table> getRisks() {
		return risks;
	}

	public void setRisks(List<risk_table> risks) {
		this.risks = risks;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((antenatal_uuid == null) ? 0 : antenatal_uuid.hashCode());
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
		case_antenatal other = (case_antenatal) obj;
		if (antenatal_uuid == null) {
			if (other.antenatal_uuid != null)
				return false;
		} else if (!antenatal_uuid.equals(other.antenatal_uuid))
			return false;
		return true;
	}

}
