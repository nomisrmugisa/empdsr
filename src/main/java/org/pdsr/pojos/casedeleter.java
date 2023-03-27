package org.pdsr.pojos;

import java.io.Serializable;

import org.pdsr.master.model.case_identifiers;

public class casedeleter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String case_id;
	private case_identifiers case_identifiers;
	private boolean delete_remotely;
	private boolean clear_summeries;
	private boolean clear_uploaded;
	
	public String getCase_id() {
		return case_id;
	}
	public void setCase_id(String case_id) {
		this.case_id = case_id;
	}
	public boolean isDelete_remotely() {
		return delete_remotely;
	}
	public void setDelete_remotely(boolean delete_remotely) {
		this.delete_remotely = delete_remotely;
	}
	public boolean isClear_summeries() {
		return clear_summeries;
	}
	public void setClear_summeries(boolean clear_summeries) {
		this.clear_summeries = clear_summeries;
	}
	public boolean isClear_uploaded() {
		return clear_uploaded;
	}
	public void setClear_uploaded(boolean clear_uploaded) {
		this.clear_uploaded = clear_uploaded;
	}
	public case_identifiers getCase_identifiers() {
		return case_identifiers;
	}
	public void setCase_identifiers(case_identifiers case_identifiers) {
		this.case_identifiers = case_identifiers;
	}
	
	

}
