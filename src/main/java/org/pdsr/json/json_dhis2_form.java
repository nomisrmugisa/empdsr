package org.pdsr.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_dhis2_form implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String program;

    private String orgUnit;

    private String eventDate;

    private String event;

    private String status;

    private String storedBy;

    private String attributeOptionCombo;

    private json_dhis2_coordinate coordinate;

    private List<json_dhis2_dataValues> dataValues;

    public json_dhis2_form() {
        super();

        this.coordinate = new json_dhis2_coordinate();
        this.dataValues = new ArrayList<>();

    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(String orgUnit) {
        this.orgUnit = orgUnit;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStoredBy() {
        return storedBy;
    }

    public void setStoredBy(String storedBy) {
        this.storedBy = storedBy;
    }

    public json_dhis2_coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(json_dhis2_coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public List<json_dhis2_dataValues> getDataValues() {
        return dataValues;
    }

    public void setDataValues(List<json_dhis2_dataValues> dataValues) {
        this.dataValues = dataValues;
    }

    public String getAttributeOptionCombo() {
        return attributeOptionCombo;
    }

    public void setAttributeOptionCombo(String attributeOptionCombo) {
        this.attributeOptionCombo = attributeOptionCombo;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public int hashCode() {
        return Objects.hash(program);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        json_dhis2_form other = (json_dhis2_form) obj;
        return Objects.equals(program, other.program);
    }

    @Override
    public String toString() {
        return "json_dhis2_form [program=" + program + ", orgUnit=" + orgUnit + ", eventDate=" + eventDate + ", event="
                + event + ", status=" + status + ", storedBy=" + storedBy + ", attributeOptionCombo="
                + attributeOptionCombo + ", coordinate=" + coordinate.toString() + ", dataValues="
                + dataValues.toString() + "]";
    }

}
