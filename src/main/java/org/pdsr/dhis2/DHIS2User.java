package org.pdsr.dhis2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DHIS2User implements Serializable {
    private String id;
    private String username;
    private String displayName;
    private List<OrganisationUnit> organisationUnits;

    public DHIS2User() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public List<OrganisationUnit> getOrganisationUnits() { return organisationUnits; }
    public void setOrganisationUnits(List<OrganisationUnit> organisationUnits) { this.organisationUnits = organisationUnits; }
}
