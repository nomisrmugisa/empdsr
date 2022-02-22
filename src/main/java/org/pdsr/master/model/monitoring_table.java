package org.pdsr.master.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Immutable;

@Immutable
@Entity
public class monitoring_table implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer mindex;
	
	@NotNull
	@Column(unique = true)
	private String mlabel;

	@Column
	private Integer gindex;

	@Column
	private String glabel;
	
	@Lob
	@Column
	private String mdesc;
	
	@Column
	private boolean gitem;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "wm_indices")
	private List<weekly_monitoring> statistics = new ArrayList<>();

	public Integer getMindex() {
		return mindex;
	}
	public void setMindex(Integer mindex) {
		this.mindex = mindex;
	}
	public String getMlabel() {
		return mlabel;
	}
	public void setMlabel(String mlabel) {
		this.mlabel = mlabel;
	}
	public String getMdesc() {
		return mdesc;
	}
	public void setMdesc(String mdesc) {
		this.mdesc = mdesc;
	}
	
	public Integer getGindex() {
		return gindex;
	}
	public void setGindex(Integer gindex) {
		this.gindex = gindex;
	}
	public String getGlabel() {
		return glabel;
	}
	public void setGlabel(String glabel) {
		this.glabel = glabel;
	}
	
	public boolean isGitem() {
		return gitem;
	}
	public void setGitem(boolean gitem) {
		this.gitem = gitem;
	}
	
	public List<weekly_monitoring> getStatistics() {
		return statistics;
	}
	public void setStatistics(List<weekly_monitoring> statistics) {
		this.statistics = statistics;
	}
	@Override
	public int hashCode() {
		return Objects.hash(mindex);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		monitoring_table other = (monitoring_table) obj;
		return Objects.equals(mindex, other.mindex);
	}

	
}
