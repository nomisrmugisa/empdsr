package org.pdsr.master.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class sync_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String sync_id;//REMOTE SYNC ID (LICENSE ID) ALSO THE COUNTRY ID
	
	@Column(unique = true)
	@NotNull
	@Size(min = 1, max = 80)
	private String sync_code;//FACILITY ID
	
	@Column
	@NotNull
	@Size(min = 1, max = 80)	
	private String sync_name;//facility name
	
	@Column
	@Size(min = 1, max = 80)	
	private String sync_email;
		
	@Column
	private String sync_url;
	
	@Lob
	@Column
	private String sync_json;
		
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "case_sync")
    @JsonIgnore
	private List<case_identifiers> cases;


	public String getSync_id() {
		return sync_id;
	}

	public void setSync_id(String sync_id) {
		this.sync_id = sync_id;
	}

	public String getSync_code() {
		return sync_code;
	}

	public void setSync_code(String sync_code) {
		this.sync_code = sync_code;
	}

	public String getSync_name() {
		return sync_name;
	}

	public void setSync_name(String sync_name) {
		this.sync_name = sync_name;
	}

	public String getSync_email() {
		return sync_email;
	}

	public void setSync_email(String sync_email) {
		this.sync_email = sync_email;
	}

	public String getSync_url() {
		return sync_url;
	}

	public void setSync_url(String sync_url) {
		this.sync_url = sync_url;
	}

	public String getSync_json() {
		return sync_json;
	}

	public void setSync_json(String sync_json) {
		this.sync_json = sync_json;
	}

	public List<case_identifiers> getCases() {
		return cases;
	}

	public void setCases(List<case_identifiers> cases) {
		this.cases = cases;
	}

}
