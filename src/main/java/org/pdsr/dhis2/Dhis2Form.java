package org.pdsr.dhis2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Dhis2Form implements Serializable {

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

	private Dhis2Coordinate coordinate;

	private List<Dhis2DataValues> dataValues;

	public Dhis2Form() {
		super();

		this.coordinate = new Dhis2Coordinate();
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

	public Dhis2Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Dhis2Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public List<Dhis2DataValues> getDataValues() {
		return dataValues;
	}

	public void setDataValues(List<Dhis2DataValues> dataValues) {
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
		Dhis2Form other = (Dhis2Form) obj;
		return Objects.equals(program, other.program);
	}

	@Override
	public String toString() {
		return "json_dhis2_form [program=" + program + ", orgUnit=" + orgUnit + ", eventDate=" + eventDate + ", event="
				+ event + ", status=" + status + ", storedBy=" + storedBy + ", attributeOptionCombo="
				+ attributeOptionCombo + "]";
	}

}
