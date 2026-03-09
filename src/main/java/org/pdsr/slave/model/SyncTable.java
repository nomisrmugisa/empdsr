package org.pdsr.slave.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = "slave_SyncTable")
@Table(name = "SYNC_TABLE")
public class SyncTable implements Serializable {
    @Id
    @Column(name = "SYNC_ID")
    private String syncId;

    @Column(name = "SYNC_UUID")
    private String syncUuid;

    @Column(name = "SYNC_CODE")
    private String syncCode;

    @Column(name = "SYNC_NAME")
    private String syncName;

    @Column(name = "SYNC_EMAIL")
    private String syncEmail;

    @Column(name = "SYNC_JSON")
    private String syncJson;

    // Getters and Setters
    public String getSyncId() { return syncId; }
    public void setSyncId(String syncId) { this.syncId = syncId; }
    public String getSyncUuid() { return syncUuid; }
    public void setSyncUuid(String syncUuid) { this.syncUuid = syncUuid; }
    public String getSyncCode() { return syncCode; }
    public void setSyncCode(String syncCode) { this.syncCode = syncCode; }
    public String getSyncName() { return syncName; }
    public void setSyncName(String syncName) { this.syncName = syncName; }
    public String getSyncEmail() { return syncEmail; }
    public void setSyncEmail(String syncEmail) { this.syncEmail = syncEmail; }
    public String getSyncJson() { return syncJson; }
    public void setSyncJson(String syncJson) { this.syncJson = syncJson; }

    // Snake_case aliases for compatibility
    public String getSync_id() { return syncId; }
    public void setSync_id(String syncId) { this.syncId = syncId; }
    public String getSync_name() { return syncName; }
    public void setSync_name(String syncName) { this.syncName = syncName; }
    public String getSync_code() { return syncCode; }
    public void setSync_code(String syncCode) { this.syncCode = syncCode; }
    public String getSync_uuid() { return syncUuid; }
    public void setSync_uuid(String syncUuid) { this.syncUuid = syncUuid; }
    public String getSync_email() { return syncEmail; }
    public void setSync_email(String syncEmail) { this.syncEmail = syncEmail; }
    public String getSync_url() { return syncJson; }
    public void setSync_url(String syncJson) { this.syncJson = syncJson; }
}
