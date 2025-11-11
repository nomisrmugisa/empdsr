package org.pdsr.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_user_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String code;
	private String country;
	private String region;
	private String district;
	private String username;
	private String password;
	private boolean enabled;
	private String useremail;
	private String userfullname;
	private String usercontact;
	private boolean alerted;
	
	
	public json_user_table() {
		super();
	}


	public json_user_table(String id, String code, String country, String region, String district, String username,
			String password, boolean enabled, String useremail, String userfullname, String usercontact,
			boolean alerted) {
		super();
		this.id = id;
		this.code = code;
		this.country = country;
		this.region = region;
		this.district = district;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.useremail = useremail;
		this.userfullname = userfullname;
		this.usercontact = usercontact;
		this.alerted = alerted;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public String getUseremail() {
		return useremail;
	}


	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}


	public String getUserfullname() {
		return userfullname;
	}


	public void setUserfullname(String userfullname) {
		this.userfullname = userfullname;
	}


	public String getUsercontact() {
		return usercontact;
	}


	public void setUsercontact(String usercontact) {
		this.usercontact = usercontact;
	}


	public boolean isAlerted() {
		return alerted;
	}


	public void setAlerted(boolean alerted) {
		this.alerted = alerted;
	}
	
}
