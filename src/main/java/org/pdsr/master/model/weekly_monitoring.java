package org.pdsr.master.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class weekly_monitoring implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private wmPK id;

	@ManyToOne
    @MapsId("weekly_id")
    @JoinColumn(name = "weekly_id", insertable = true, updatable = true)
    private weekly_table wm_grids;

    @ManyToOne
    @MapsId("mindex")
    @JoinColumn(name = "mindex", insertable = true, updatable = true)
    private monitoring_table wm_indices;

    @Column
    private Integer wm_values;
    
    @Column
    private Integer wm_subval;
    
	@Column
	private Integer data_sent;
	
	public Integer getData_sent() {
		return data_sent;
	}

	public void setData_sent(Integer data_sent) {
		this.data_sent = data_sent;
	}

	public wmPK getId() {
		return id;
	}

	public void setId(wmPK id) {
		this.id = id;
	}

	public weekly_table getWm_grids() {
		return wm_grids;
	}

	public void setWm_grids(weekly_table wm_grids) {
		this.wm_grids = wm_grids;
	}

	public monitoring_table getWm_indices() {
		return wm_indices;
	}

	public void setWm_indices(monitoring_table wm_indices) {
		this.wm_indices = wm_indices;
	}

	public Integer getWm_values() {
		return wm_values;
	}

	public void setWm_values(Integer wm_values) {
		this.wm_values = wm_values;
	}

	public Integer getWm_subval() {
		return wm_subval;
	}

	public void setWm_subval(Integer wm_subval) {
		this.wm_subval = wm_subval;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		weekly_monitoring other = (weekly_monitoring) obj;
		return Objects.equals(id, other.id);
	}




}
