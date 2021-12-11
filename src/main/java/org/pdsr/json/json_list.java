package org.pdsr.json;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_list implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<json_data> biodata, pregnancy, referral, delivery, antenatal, labour, birth, fetalheart, notes;
	
	

	public json_list() {
		super();
	}



	public List<json_data> getBiodata() {
		return biodata;
	}



	public void setBiodata(List<json_data> biodata) {
		this.biodata = biodata;
	}



	public List<json_data> getPregnancy() {
		return pregnancy;
	}



	public void setPregnancy(List<json_data> pregnancy) {
		this.pregnancy = pregnancy;
	}



	public List<json_data> getReferral() {
		return referral;
	}



	public void setReferral(List<json_data> referral) {
		this.referral = referral;
	}



	public List<json_data> getDelivery() {
		return delivery;
	}



	public void setDelivery(List<json_data> delivery) {
		this.delivery = delivery;
	}



	public List<json_data> getAntenatal() {
		return antenatal;
	}



	public void setAntenatal(List<json_data> antenatal) {
		this.antenatal = antenatal;
	}



	public List<json_data> getLabour() {
		return labour;
	}



	public void setLabour(List<json_data> labour) {
		this.labour = labour;
	}



	public List<json_data> getBirth() {
		return birth;
	}



	public void setBirth(List<json_data> birth) {
		this.birth = birth;
	}



	public List<json_data> getFetalheart() {
		return fetalheart;
	}



	public void setFetalheart(List<json_data> fetalheart) {
		this.fetalheart = fetalheart;
	}



	public List<json_data> getNotes() {
		return notes;
	}



	public void setNotes(List<json_data> notes) {
		this.notes = notes;
	}

	
}
