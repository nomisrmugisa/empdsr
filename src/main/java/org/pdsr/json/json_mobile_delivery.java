package org.pdsr.json;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_mobile_delivery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String record_id;

	private Date delivery_date;

	private Integer delivery_hour;
	
	private Integer delivery_minute;
	
	private Double delivery_weight;
	
	private Date delivery_time;

	private Integer delivery_datetime_notstated;
	
	private Integer delivery_period;//dawn,morning,afternoon,evening,night
	
	private Integer data_complete;

	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((record_id == null) ? 0 : record_id.hashCode());
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
		json_mobile_delivery other = (json_mobile_delivery) obj;
		if (record_id == null) {
			if (other.record_id != null)
				return false;
		} else if (!record_id.equals(other.record_id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  record_id;
	}
		

}
