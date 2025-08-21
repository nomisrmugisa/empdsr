package org.pdsr.slave.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class case_identifiers implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String case_uuid;

	@NotNull
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date case_date;

	@NotNull
	@Column
	@Size(min=1, max = 80)
	private String case_sync;
	
	@NotNull
	@Column
	@Size(min=1, max = 80)
	private String case_id;
	
	@NotNull
	@Column
	@Size(min=1, max = 80)
	private String case_mid;
	
	@NotNull
	@Column
	@Size(min=1, max = 80)
	private String case_mname;
    
	@NotNull
	@Column
	private Integer case_death;//1 stillbirth or 2 early neonatal
	
	@Column
	private Integer case_status;//0 entry, 1 auditing new submissions, 2 auditing started auditing, 3 process complete
    
	@Column
	private Integer data_sent;
	
	public Integer getData_sent() {
		return data_sent;
	}

	public void setData_sent(Integer data_sent) {
		this.data_sent = data_sent;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "facility", referencedColumnName = "facility_uuid", insertable = true, updatable = true)
	private facility_table facility;
    
	@OneToOne(cascade = CascadeType.PERSIST, mappedBy = "case_uuid")
	private case_biodata biodata;

	@OneToOne(cascade = CascadeType.PERSIST, mappedBy = "case_uuid")
	private case_pregnancy pregnancy;

	@OneToOne(cascade = CascadeType.PERSIST, mappedBy = "case_uuid")
	private case_delivery delivery;

	@OneToOne(cascade = CascadeType.PERSIST, mappedBy = "case_uuid")
	private case_referral referral;

	@OneToOne(cascade = CascadeType.PERSIST, mappedBy = "case_uuid")
	private case_antenatal antenatal;

	@OneToOne(cascade = CascadeType.PERSIST, mappedBy = "case_uuid")
	private case_labour labour;

	@OneToOne(cascade = CascadeType.PERSIST, mappedBy = "case_uuid")
	private case_fetalheart fetalheart;

	@OneToOne(cascade = CascadeType.PERSIST, mappedBy = "case_uuid")
	private case_birth birth;
	
	@OneToOne(cascade = CascadeType.PERSIST, mappedBy = "case_uuid")
	private case_babydeath babydeath;

	@OneToOne(cascade = CascadeType.PERSIST, mappedBy = "case_uuid")
	private case_notes notes;


	public String getCase_uuid() {
		return case_uuid;
	}

	public void setCase_uuid(String case_uuid) {
		this.case_uuid = case_uuid;
	}

	public Date getCase_date() {
		return case_date;
	}

	public void setCase_date(Date case_date) {
		this.case_date = case_date;
	}

	public String getCase_sync() {
		return case_sync;
	}

	public void setCase_sync(String case_sync) {
		this.case_sync = case_sync;
	}

	public String getCase_id() {
		return case_id;
	}

	public void setCase_id(String case_id) {
		this.case_id = case_id;
	}

	public String getCase_mid() {
		return case_mid;
	}

	public void setCase_mid(String case_mid) {
		this.case_mid = case_mid;
	}

	public String getCase_mname() {
		return case_mname;
	}

	public void setCase_mname(String case_mname) {
		this.case_mname = case_mname;
	}

	public Integer getCase_death() {
		return case_death;
	}

	public void setCase_death(Integer case_death) {
		this.case_death = case_death;
	}

	public facility_table getFacility() {
		return facility;
	}

	public void setFacility(facility_table facility) {
		this.facility = facility;
	}

	public case_biodata getBiodata() {
		return biodata;
	}

	public void setBiodata(case_biodata biodata) {
		this.biodata = biodata;
	}

	public case_pregnancy getPregnancy() {
		return pregnancy;
	}

	public void setPregnancy(case_pregnancy pregnancy) {
		this.pregnancy = pregnancy;
	}

	public case_delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(case_delivery delivery) {
		this.delivery = delivery;
	}

	public case_referral getReferral() {
		return referral;
	}

	public void setReferral(case_referral referral) {
		this.referral = referral;
	}

	public case_antenatal getAntenatal() {
		return antenatal;
	}

	public void setAntenatal(case_antenatal antenatal) {
		this.antenatal = antenatal;
	}

	public case_labour getLabour() {
		return labour;
	}

	public void setLabour(case_labour labour) {
		this.labour = labour;
	}

	public case_fetalheart getFetalheart() {
		return fetalheart;
	}

	public void setFetalheart(case_fetalheart fetalheart) {
		this.fetalheart = fetalheart;
	}

	public case_birth getBirth() {
		return birth;
	}

	public void setBirth(case_birth birth) {
		this.birth = birth;
	}

	public case_babydeath getBabydeath() {
		return babydeath;
	}

	public void setBabydeath(case_babydeath babydeath) {
		this.babydeath = babydeath;
	}

	public case_notes getNotes() {
		return notes;
	}

	public void setNotes(case_notes notes) {
		this.notes = notes;
	}

	public Integer getCase_status() {
		return case_status;
	}

	public void setCase_status(Integer case_status) {
		this.case_status = case_status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((case_uuid == null) ? 0 : case_uuid.hashCode());
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
		case_identifiers other = (case_identifiers) obj;
		if (case_uuid == null) {
			if (other.case_uuid != null)
				return false;
		} else if (!case_uuid.equals(other.case_uuid))
			return false;
		return true;
	}
	
		
}
