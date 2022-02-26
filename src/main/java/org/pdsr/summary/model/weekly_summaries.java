package org.pdsr.summary.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class weekly_summaries implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private summaryPk facility;
	
	
	@Column
	private String facility_name;
	@Column
	private String district_name;
	@Column
	private String region_name;
	@Column
	private String country_name;
	
	@Column
	private Integer totalbirths = 0;
	@Column
	private Integer totallivebirths = 0;
	@Column
	private Integer totalperinatals = 0;
	@Column
	private Integer totalneonatals = 0;
	@Column
	private Integer totalstillbirths = 0;

	@Column
	private Double isbr_oavg = 0.0;
	@Column
	private Double iisbr_oavg = 0.0;
	@Column
	private Double aisbr_oavg = 0.0;
	@Column
	private Double piisbr_oavg = 0.0;
	@Column
	private Double einmr_oavg = 0.0;
	@Column
	private Double linmr_oavg = 0.0;
	@Column
	private Double ipmr_oavg = 0.0;
	@Column
	private Double inmr_oavg = 0.0;
	@Column
	private Double immr_oavg = 0.0;
	@Column
	private Double icsr_oavg = 0.0;
	@Column
	private Double iadr_oavg = 0.0;
	@Column
	private Double ivdr_oavg = 0.0;
	@Column
	private Double ilbwr_oavg = 0.0;
	@Column
	private Double iptbr_oavg = 0.0;
	@Column
	private Double indwk1_oavg = 0.0;
	@Column
	private Integer mdeath_osum = 0;
	public summaryPk getFacility() {
		return facility;
	}
	public void setFacility(summaryPk facility) {
		this.facility = facility;
	}
	public String getFacility_name() {
		return facility_name;
	}
	public void setFacility_name(String facility_name) {
		this.facility_name = facility_name;
	}
	public String getDistrict_name() {
		return district_name;
	}
	public void setDistrict_name(String district_name) {
		this.district_name = district_name;
	}
	public String getRegion_name() {
		return region_name;
	}
	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	public Integer getTotalbirths() {
		return totalbirths;
	}
	public void setTotalbirths(Integer totalbirths) {
		this.totalbirths = totalbirths;
	}
	public Integer getTotallivebirths() {
		return totallivebirths;
	}
	public void setTotallivebirths(Integer totallivebirths) {
		this.totallivebirths = totallivebirths;
	}
	public Integer getTotalperinatals() {
		return totalperinatals;
	}
	public void setTotalperinatals(Integer totalperinatals) {
		this.totalperinatals = totalperinatals;
	}
	public Integer getTotalneonatals() {
		return totalneonatals;
	}
	public void setTotalneonatals(Integer totalneonatals) {
		this.totalneonatals = totalneonatals;
	}
	public Integer getTotalstillbirths() {
		return totalstillbirths;
	}
	public void setTotalstillbirths(Integer totalstillbirths) {
		this.totalstillbirths = totalstillbirths;
	}
	public Double getIsbr_oavg() {
		return isbr_oavg;
	}
	public void setIsbr_oavg(Double isbr_oavg) {
		this.isbr_oavg = isbr_oavg;
	}
	public Double getIisbr_oavg() {
		return iisbr_oavg;
	}
	public void setIisbr_oavg(Double iisbr_oavg) {
		this.iisbr_oavg = iisbr_oavg;
	}
	public Double getAisbr_oavg() {
		return aisbr_oavg;
	}
	public void setAisbr_oavg(Double aisbr_oavg) {
		this.aisbr_oavg = aisbr_oavg;
	}
	public Double getPiisbr_oavg() {
		return piisbr_oavg;
	}
	public void setPiisbr_oavg(Double piisbr_oavg) {
		this.piisbr_oavg = piisbr_oavg;
	}
	public Double getEinmr_oavg() {
		return einmr_oavg;
	}
	public void setEinmr_oavg(Double einmr_oavg) {
		this.einmr_oavg = einmr_oavg;
	}
	public Double getLinmr_oavg() {
		return linmr_oavg;
	}
	public void setLinmr_oavg(Double linmr_oavg) {
		this.linmr_oavg = linmr_oavg;
	}
	public Double getIpmr_oavg() {
		return ipmr_oavg;
	}
	public void setIpmr_oavg(Double ipmr_oavg) {
		this.ipmr_oavg = ipmr_oavg;
	}
	public Double getInmr_oavg() {
		return inmr_oavg;
	}
	public void setInmr_oavg(Double inmr_oavg) {
		this.inmr_oavg = inmr_oavg;
	}
	public Double getImmr_oavg() {
		return immr_oavg;
	}
	public void setImmr_oavg(Double immr_oavg) {
		this.immr_oavg = immr_oavg;
	}
	public Double getIcsr_oavg() {
		return icsr_oavg;
	}
	public void setIcsr_oavg(Double icsr_oavg) {
		this.icsr_oavg = icsr_oavg;
	}
	public Double getIadr_oavg() {
		return iadr_oavg;
	}
	public void setIadr_oavg(Double iadr_oavg) {
		this.iadr_oavg = iadr_oavg;
	}
	public Double getIvdr_oavg() {
		return ivdr_oavg;
	}
	public void setIvdr_oavg(Double ivdr_oavg) {
		this.ivdr_oavg = ivdr_oavg;
	}
	public Double getIlbwr_oavg() {
		return ilbwr_oavg;
	}
	public void setIlbwr_oavg(Double ilbwr_oavg) {
		this.ilbwr_oavg = ilbwr_oavg;
	}
	public Double getIptbr_oavg() {
		return iptbr_oavg;
	}
	public void setIptbr_oavg(Double iptbr_oavg) {
		this.iptbr_oavg = iptbr_oavg;
	}
	public Double getIndwk1_oavg() {
		return indwk1_oavg;
	}
	public void setIndwk1_oavg(Double indwk1_oavg) {
		this.indwk1_oavg = indwk1_oavg;
	}
	public Integer getMdeath_osum() {
		return mdeath_osum;
	}
	public void setMdeath_osum(Integer mdeath_osum) {
		this.mdeath_osum = mdeath_osum;
	}
	@Override
	public int hashCode() {
		return Objects.hash(facility);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		weekly_summaries other = (weekly_summaries) obj;
		return Objects.equals(facility, other.facility);
	}


}
