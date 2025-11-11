package org.pdsr.master.model;

import java.io.Serializable;
import java.util.Objects;


public class datamapPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String map_feature;
	private Integer map_value;
	
	public datamapPK() {
		super();
	}
	public datamapPK(String map_feature, Integer map_value) {
		super();
		this.map_feature = map_feature;
		this.map_value = map_value;
	}
	@Override
	public int hashCode() {
		return Objects.hash(map_feature, map_value);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		datamapPK other = (datamapPK) obj;
		return Objects.equals(map_feature, other.map_feature) && Objects.equals(map_value, other.map_value);
	}

	
}
