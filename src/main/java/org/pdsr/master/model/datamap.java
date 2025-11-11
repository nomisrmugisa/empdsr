package org.pdsr.master.model;

import java.io.Serializable;
import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;

@Immutable
@Entity
@IdClass(datamapPK.class)
public class datamap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	private String map_feature;

	@Id
	@NotNull
	private Integer map_value;

	@Column
	@NotNull
	private String map_label;

	public String getMap_feature() {
		return map_feature;
	}

	public void setMap_feature(String map_feature) {
		this.map_feature = map_feature;
	}

	public Integer getMap_value() {
		return map_value;
	}

	public void setMap_value(Integer map_value) {
		this.map_value = map_value;
	}

	public String getMap_label() {
		return map_label;
	}

	public void setMap_label(String map_label) {
		this.map_label = map_label;
	}

}

class SortbyValue implements Comparator<datamap> {

	public int compare(datamap a, datamap b) {
		return a.getMap_value() - b.getMap_value();
	}
}
