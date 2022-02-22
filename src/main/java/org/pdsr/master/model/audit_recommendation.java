package org.pdsr.master.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class audit_recommendation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String recommendation_uuid;

	@JoinColumn(name = "audit_uuid", referencedColumnName = "audit_uuid", insertable = true, updatable = true)
	@ManyToOne(optional = false)
	private audit_audit audit_uuid;
	
	@NotNull
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.util.Date recommendation_date;
		
	@Column
	@NotNull
	@Size(min = 1, max = 80)
	private String recommendation_title;

	@Lob
	@Column
	@NotNull
	private String recommendation_task;

	@Column
	@NotNull
	@Size(min = 1, max = 80)
	private String recommendation_leader;

	@Column
	@NotNull
	@Size(min = 1, max = 80)
	private String recommendation_reporter;

	@NotNull
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private java.util.Date recommendation_deadline;

	@Column
	@NotNull
	@Size(min = 1, max = 80)
	private String recommendation_resources;
	
	@Column
	@NotNull
	private Integer recommendation_status;//0 not started 1 started 2 completed

	@Lob
	@Column
	private String recommendation_comments;
	
	@Transient
	private String rec_color;

	@Transient
	private String bg_color;

	
	public String getBg_color() {
		return bg_color;
	}

	public void setBg_color(String bg_color) {
		this.bg_color = bg_color;
	}

	public String getRec_color() {
		return rec_color;
	}

	public void setRec_color(String rec_color) {
		this.rec_color = rec_color;
	}

	public String getRecommendation_uuid() {
		return recommendation_uuid;
	}

	public void setRecommendation_uuid(String recommendation_uuid) {
		this.recommendation_uuid = recommendation_uuid;
	}

	public audit_audit getAudit_uuid() {
		return audit_uuid;
	}

	public void setAudit_uuid(audit_audit audit_uuid) {
		this.audit_uuid = audit_uuid;
	}

	public java.util.Date getRecommendation_date() {
		return recommendation_date;
	}

	public void setRecommendation_date(java.util.Date recommendation_date) {
		this.recommendation_date = recommendation_date;
	}

	public String getRecommendation_title() {
		return recommendation_title;
	}

	public void setRecommendation_title(String recommendation_title) {
		this.recommendation_title = recommendation_title;
	}

	public String getRecommendation_task() {
		return recommendation_task;
	}

	public void setRecommendation_task(String recommendation_task) {
		this.recommendation_task = recommendation_task;
	}

	public String getRecommendation_leader() {
		return recommendation_leader;
	}

	public void setRecommendation_leader(String recommendation_leader) {
		this.recommendation_leader = recommendation_leader;
	}

	public String getRecommendation_reporter() {
		return recommendation_reporter;
	}

	public void setRecommendation_reporter(String recommendation_reporter) {
		this.recommendation_reporter = recommendation_reporter;
	}

	public java.util.Date getRecommendation_deadline() {
		return recommendation_deadline;
	}

	public void setRecommendation_deadline(java.util.Date recommendation_deadline) {
		this.recommendation_deadline = recommendation_deadline;
	}

	public String getRecommendation_resources() {
		return recommendation_resources;
	}

	public void setRecommendation_resources(String recommendation_resources) {
		this.recommendation_resources = recommendation_resources;
	}

	public Integer getRecommendation_status() {
		return recommendation_status;
	}

	public void setRecommendation_status(Integer recommendation_status) {
		this.recommendation_status = recommendation_status;
	}

	public String getRecommendation_comments() {
		return recommendation_comments;
	}

	public void setRecommendation_comments(String recommendation_comments) {
		this.recommendation_comments = recommendation_comments;
	}

	@Override
	public int hashCode() {
		return Objects.hash(recommendation_uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		audit_recommendation other = (audit_recommendation) obj;
		return Objects.equals(recommendation_uuid, other.recommendation_uuid);
	}
	
	
	
}
