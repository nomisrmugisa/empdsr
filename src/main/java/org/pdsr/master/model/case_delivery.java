package org.pdsr.master.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class case_delivery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String delivery_uuid;

	@JsonIgnore
	@MapsId
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "case_uuid", referencedColumnName = "case_uuid", insertable = true, updatable = true)
	private case_identifiers case_uuid;

	@Column
	private Integer delivery_occured;
	
	@Column
	private Integer delivery_abortion;
	
	@Column
	private Integer delivery_occured_facility;
	
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date delivery_date;

	
	@Column
	private Integer delivery_hour;
	
	
	@Column
	private Integer delivery_minute;
	
	@Column
	private Double delivery_weight;
	
	
	@Column
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date delivery_time;

	@Column
	private Integer delivery_datetime_notstated;
	
	@Column
	private Integer delivery_period;//dawn,morning,afternoon,evening,night
	
	@Column
	private String delivery_mode;
	
	@Column
	private Integer data_complete;

	@JsonIgnore
	@Lob
	@Column
	private String delivery_json;
		
	public String getDelivery_json() {
		return delivery_json;
	}

	public void setDelivery_json(String delivery_json) {
		this.delivery_json = delivery_json;
	}

	public String getDelivery_uuid() {
		return delivery_uuid;
	}

	public void setDelivery_uuid(String delivery_uuid) {
		this.delivery_uuid = delivery_uuid;
	}

	public case_identifiers getCase_uuid() {
		return case_uuid;
	}

	public void setCase_uuid(case_identifiers case_uuid) {
		this.case_uuid = case_uuid;
	}

	public Date getDelivery_date() {
		return delivery_date;
	}

	public void setDelivery_date(Date delivery_date) {
		this.delivery_date = delivery_date;
	}

	public Integer getDelivery_hour() {
		return delivery_hour;
	}

	public void setDelivery_hour(Integer delivery_hour) {
		this.delivery_hour = delivery_hour;
	}

	public Integer getDelivery_minute() {
		return delivery_minute;
	}

	public void setDelivery_minute(Integer delivery_minute) {
		this.delivery_minute = delivery_minute;
	}

	public Integer getDelivery_period() {
		return delivery_period;
	}

	public void setDelivery_period(Integer delivery_period) {
		this.delivery_period = delivery_period;
	}

	public Date getDelivery_time() {
		return delivery_time;
	}

	public void setDelivery_time(Date delivery_time) {
		this.delivery_time = delivery_time;
	}

	public Double getDelivery_weight() {
		return delivery_weight;
	}

	public void setDelivery_weight(Double delivery_weight) {
		this.delivery_weight = delivery_weight;
	}

	public Integer getData_complete() {
		return data_complete;
	}

	public void setData_complete(Integer data_complete) {
		this.data_complete = data_complete;
	}

	public Integer getDelivery_datetime_notstated() {
		return delivery_datetime_notstated;
	}

	public void setDelivery_datetime_notstated(Integer delivery_datetime_notstated) {
		this.delivery_datetime_notstated = delivery_datetime_notstated;
	}

	public Integer getDelivery_occured() {
		return delivery_occured;
	}

	public void setDelivery_occured(Integer delivery_occured) {
		this.delivery_occured = delivery_occured;
	}

	public Integer getDelivery_occured_facility() {
		return delivery_occured_facility;
	}

	public void setDelivery_occured_facility(Integer delivery_occured_facility) {
		this.delivery_occured_facility = delivery_occured_facility;
	}

	public Integer getDelivery_abortion() {
		return delivery_abortion;
	}

	public void setDelivery_abortion(Integer delivery_abortion) {
		this.delivery_abortion = delivery_abortion;
	}

	public String getDelivery_mode() {
		return delivery_mode;
	}

	public void setDelivery_mode(String delivery_mode) {
		this.delivery_mode = delivery_mode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((delivery_uuid == null) ? 0 : delivery_uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		case_delivery other = (case_delivery) obj;
		if (delivery_uuid == null) {
			if (other.delivery_uuid != null)
				return false;
		} else if (!delivery_uuid.equals(other.delivery_uuid))
			return false;
		return true;
	}
		

}
