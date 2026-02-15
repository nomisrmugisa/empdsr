package org.pdsr.json;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_dhis2_coordinate implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Double latitude;

    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        json_dhis2_coordinate other = (json_dhis2_coordinate) obj;
        return Objects.equals(latitude, other.latitude) && Objects.equals(longitude, other.longitude);
    }

    @Override
    public String toString() {
        return "json_dhis2_coordinate [latitude=" + latitude + ", longitude=" + longitude + "]";
    }

}
