package org.pdsr.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class json_audit_audit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String id;
	private String code;
	private String country;
	private String region;
	private String district;
	private String audit_cdate;
	private Integer audit_death;
	private String audit_icd10;
	private String audit_icdpm;
	private String audit_csc;
	private Integer audit_delay1;
	private Integer audit_delay2;
	private Integer audit_delay3a;
	private Integer audit_delay3b;
	private Integer audit_delay3c;
	private String audit_ifcmfs;
	private String audit_sysmfs;
	private String audit_facmfs;
	private String audit_hwkmfs;
	private Integer rec_complete;
	
	//json data for generating a report about the case
	private String case_data;
	private String audit_data;



	public json_audit_audit() {
		super();
	}
	
	public json_audit_audit(String id, String code, String country, String region, String district, String audit_cdate,
			Integer audit_death, String audit_icd10, String audit_icdpm, String audit_csc, Integer audit_delay1,
			Integer audit_delay2, Integer audit_delay3a, Integer audit_delay3b, Integer audit_delay3c,
			String audit_ifcmfs, String audit_sysmfs, String audit_facmfs, String audit_hwkmfs, Integer rec_complete,
			String case_data, String audit_data) {
		super();
		this.id = id;
		this.code = code;
		this.country = country;
		this.region = region;
		this.district = district;
		this.audit_cdate = audit_cdate;
		this.audit_death = audit_death;
		this.audit_icd10 = audit_icd10;
		this.audit_icdpm = audit_icdpm;
		this.audit_csc = audit_csc;
		this.audit_delay1 = audit_delay1;
		this.audit_delay2 = audit_delay2;
		this.audit_delay3a = audit_delay3a;
		this.audit_delay3b = audit_delay3b;
		this.audit_delay3c = audit_delay3c;
		this.audit_ifcmfs = audit_ifcmfs;
		this.audit_sysmfs = audit_sysmfs;
		this.audit_facmfs = audit_facmfs;
		this.audit_hwkmfs = audit_hwkmfs;
		this.rec_complete = rec_complete;
		this.case_data = case_data;
		this.audit_data = audit_data;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public Integer getAudit_death() {
		return audit_death;
	}
	public void setAudit_death(Integer audit_death) {
		this.audit_death = audit_death;
	}
	public String getAudit_icd10() {
		return audit_icd10;
	}
	public void setAudit_icd10(String audit_icd10) {
		this.audit_icd10 = audit_icd10;
	}
	public String getAudit_icdpm() {
		return audit_icdpm;
	}
	public void setAudit_icdpm(String audit_icdpm) {
		this.audit_icdpm = audit_icdpm;
	}
	public String getAudit_csc() {
		return audit_csc;
	}
	public void setAudit_csc(String audit_csc) {
		this.audit_csc = audit_csc;
	}
	
	
	public Integer getAudit_delay1() {
		return audit_delay1;
	}
	public void setAudit_delay1(Integer audit_delay1) {
		this.audit_delay1 = audit_delay1;
	}
	public Integer getAudit_delay2() {
		return audit_delay2;
	}
	public void setAudit_delay2(Integer audit_delay2) {
		this.audit_delay2 = audit_delay2;
	}
	public Integer getAudit_delay3a() {
		return audit_delay3a;
	}
	public void setAudit_delay3a(Integer audit_delay3a) {
		this.audit_delay3a = audit_delay3a;
	}
	public Integer getAudit_delay3b() {
		return audit_delay3b;
	}
	public void setAudit_delay3b(Integer audit_delay3b) {
		this.audit_delay3b = audit_delay3b;
	}
	public Integer getAudit_delay3c() {
		return audit_delay3c;
	}
	public void setAudit_delay3c(Integer audit_delay3c) {
		this.audit_delay3c = audit_delay3c;
	}
	public String getAudit_ifcmfs() {
		return audit_ifcmfs;
	}
	public void setAudit_ifcmfs(String audit_ifcmfs) {
		this.audit_ifcmfs = audit_ifcmfs;
	}
	public String getAudit_sysmfs() {
		return audit_sysmfs;
	}
	public void setAudit_sysmfs(String audit_sysmfs) {
		this.audit_sysmfs = audit_sysmfs;
	}
	public String getAudit_facmfs() {
		return audit_facmfs;
	}
	public void setAudit_facmfs(String audit_facmfs) {
		this.audit_facmfs = audit_facmfs;
	}
	public String getAudit_hwkmfs() {
		return audit_hwkmfs;
	}
	public void setAudit_hwkmfs(String audit_hwkmfs) {
		this.audit_hwkmfs = audit_hwkmfs;
	}
	
	public String getAudit_cdate() {
		return audit_cdate;
	}
	public void setAudit_cdate(String audit_cdate) {
		this.audit_cdate = audit_cdate;
	}
	public Integer getRec_complete() {
		return rec_complete;
	}
	public void setRec_complete(Integer rec_complete) {
		this.rec_complete = rec_complete;
	}
	
	
	public String getCase_data() {
		return case_data;
	}
	public void setCase_data(String case_data) {
		this.case_data = case_data;
	}
	public String getAudit_data() {
		return audit_data;
	}
	public void setAudit_data(String audit_data) {
		this.audit_data = audit_data;
	}
			
	
}
