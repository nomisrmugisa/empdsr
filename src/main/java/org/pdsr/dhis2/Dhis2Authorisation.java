package org.pdsr.dhis2;

import java.io.Serializable;

public class Dhis2Authorisation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String dhis2_username;
	
	private String dhis2_password;

	public String getDhis2_username() {
		return dhis2_username;
	}

	public void setDhis2_username(String dhis2_username) {
		this.dhis2_username = dhis2_username;
	}

	public String getDhis2_password() {
		return dhis2_password;
	}

	public void setDhis2_password(String dhis2_password) {
		this.dhis2_password = dhis2_password;
	}

	

}
