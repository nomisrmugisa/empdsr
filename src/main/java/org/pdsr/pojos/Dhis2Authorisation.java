package org.pdsr.pojos;

import java.io.Serializable;

public class Dhis2Authorisation implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String dhis2_username;

    private String dhis2_password;

    /** Base URL of the DHIS2 instance, e.g. https://ug.sk-engine.online/hmis */
    private String dhis2_url;

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

    public String getDhis2_url() {
        return dhis2_url;
    }

    public void setDhis2_url(String dhis2_url) {
        this.dhis2_url = dhis2_url;
    }

}
