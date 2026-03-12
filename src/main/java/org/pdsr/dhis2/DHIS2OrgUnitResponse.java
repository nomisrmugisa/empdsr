package org.pdsr.dhis2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DHIS2OrgUnitResponse {
    private List<OrganisationUnit> organisationUnits;

    public List<OrganisationUnit> getOrganisationUnits() { return organisationUnits; }
    public void setOrganisationUnits(List<OrganisationUnit> organisationUnits) { this.organisationUnits = organisationUnits; }
}
