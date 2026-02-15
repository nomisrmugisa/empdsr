package org.pdsr.json;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_dhis2_dataValues implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String dataElement;

    private String value;

    public json_dhis2_dataValues() {
        super();
    }

    public json_dhis2_dataValues(String dataElement, String value) {
        super();
        this.dataElement = dataElement;
        this.value = value;
    }

    public json_dhis2_dataValues(String dataElement, Integer value) {
        super();
        this.dataElement = dataElement;
        this.value = "" + value;
    }

    public json_dhis2_dataValues(String dataElement, Double value) {
        super();
        this.dataElement = dataElement;
        this.value = "" + value;
    }

    public String getDataElement() {
        return dataElement;
    }

    public void setDataElement(String dataElement) {
        this.dataElement = dataElement;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataElement);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        json_dhis2_dataValues other = (json_dhis2_dataValues) obj;
        return Objects.equals(dataElement, other.dataElement);
    }

    @Override
    public String toString() {
        return "json_dhis2_dataValues [dataElement=" + dataElement + ", value=" + value + "]";
    }

}
