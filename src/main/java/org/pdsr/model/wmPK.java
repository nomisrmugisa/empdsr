package org.pdsr.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class wmPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column
	private Integer weekly_id;
	@Column
	private Integer mindex;
	
	public wmPK() {
		super();
	}

	public wmPK(Integer weekly_id, Integer mindex) {
		super();
		this.weekly_id = weekly_id;
		this.mindex = mindex;
	}

	public Integer getWeekly_id() {
		return weekly_id;
	}

	public void setWeekly_id(Integer weekly_id) {
		this.weekly_id = weekly_id;
	}

	public Integer getMindex() {
		return mindex;
	}

	public void setMindex(Integer mindex) {
		this.mindex = mindex;
	}

	@Override
	public int hashCode() {
		return Objects.hash(mindex, weekly_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		wmPK other = (wmPK) obj;
		return Objects.equals(mindex, other.mindex) && Objects.equals(weekly_id, other.weekly_id);
	}

	
	
}
