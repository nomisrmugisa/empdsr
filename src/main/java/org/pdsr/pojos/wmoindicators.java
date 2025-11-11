package org.pdsr.pojos;

import java.io.Serializable;

public class wmoindicators implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String wmdesc;

	private Integer totalbirths = 0;
	private Integer totallivebirths = 0;
	private Integer totalperinatals = 0;
	private Integer totalneonatals = 0;
	private Integer totalstillbirths = 0;

	private Double isbr_oavg = 0.0;
	private Double iisbr_oavg = 0.0;
	private Double aisbr_oavg = 0.0;
	private Double piisbr_oavg = 0.0;
	private Double einmr_oavg = 0.0;
	private Double linmr_oavg = 0.0;
	private Double ipmr_oavg = 0.0;
	private Double inmr_oavg = 0.0;
	private Double immr_oavg = 0.0;
	private Double icsr_oavg = 0.0;
	private Double iadr_oavg = 0.0;
	private Double ivdr_oavg = 0.0;
	private Double ilbwr_oavg = 0.0;
	private Double iptbr_oavg = 0.0;
	private Double indwk1_oavg = 0.0;
	private Integer mdeath_osum = 0;

	public String getWmdesc() {
		return wmdesc;
	}

	public void setWmdesc(String wmdesc) {
		this.wmdesc = wmdesc;
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

	public Double getIvdr_oavg() {
		return ivdr_oavg;
	}

	public void setIvdr_oavg(Double ivdr_oavg) {
		this.ivdr_oavg = ivdr_oavg;
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

}
