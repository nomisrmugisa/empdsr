package org.pdsr.master.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "SYNC_TABLE")
public class sync_table implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "SYNC_ID")
    private String sync_id;

    @Column(name = "SYNC_UUID")
    private String sync_uuid;

    @Column(name = "SYNC_CODE")
    private String sync_code;

    @Column(name = "SYNC_NAME")
    private String sync_name;

    @Column(name = "SYNC_EMAIL")
    private String sync_email;

    @Column(name = "SYNC_JSON")
    private String sync_url;

    public sync_table() {}

    public String getSync_id() {
        return sync_id;
    }

    public void setSync_id(String sync_id) {
        this.sync_id = sync_id;
    }

    public String getSync_uuid() {
        return sync_uuid;
    }

    public void setSync_uuid(String sync_uuid) {
        this.sync_uuid = sync_uuid;
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

    // CamelCase aliases for compatibility
    public String getSyncId()             { return sync_id; }
    public void   setSyncId(String id)    { this.sync_id = id; }
    public String getSyncName()           { return sync_name; }
    public void   setSyncName(String n)   { this.sync_name = n; }
    public String getSyncCode()           { return sync_code; }
    public void   setSyncCode(String c)   { this.sync_code = c; }
    public String getSyncEmail()          { return sync_email; }
    public void   setSyncEmail(String e)  { this.sync_email = e; }
    public String getSyncJson()           { return sync_url; }
    public void   setSyncJson(String j)   { this.sync_url = j; }
}
