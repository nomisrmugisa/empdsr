package org.pdsr.master.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class monitoring_tool implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String survey_id;

	@NotNull
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date survey_date;

	@NotNull
	@Size(min = 1, max = 80)
	private String survey_author;

	@NotNull
	@Column
	private Integer lead_coordinator;
	
	@NotNull
	@Column
	private Integer lead_supervision;
	
	@NotNull
	@Column
	private Integer lead_engagement;
	
	@NotNull
	@Column
	private Integer policy_guidlines;
	
	@NotNull
	@Column
	private Integer struct_mat_committee;
	
	@NotNull
	@Column
	private Integer struct_peri_committee;
	
	@NotNull
	@Column
	private Integer struct_multi_committee;
	
	@NotNull
	@Column
	private Integer struct_formal_committee;
	
	@NotNull
	@Column
	private Integer meet_exists;
	
	@NotNull
	@Column
	private Integer meet_routine;
	
	@NotNull
	@Column
	private Integer tools_mat_available;
	
	@NotNull
	@Column
	private Integer tools_peri_available;
	
	@NotNull
	@Column
	private Integer tools_mat_used;
	
	@NotNull
	@Column
	private Integer tools_peri_used;
	
	@NotNull
	@Column
	private Integer tools_mat_correctly;
	
	@NotNull
	@Column
	private Integer tools_peri_correctly;
	
	@NotNull
	@Column
	private Integer train_any_done;
	
	@NotNull
	@Column
	private Integer train_recent_done;
	
	@NotNull
	@Column
	private Integer train_embed_work;
	
	@NotNull
	@Column
	private Integer res_processes;
	
	@NotNull
	@Column
	private Integer res_mobilisation;
	
	@NotNull
	@Column
	private Integer res_expenditure;
	
	@NotNull
	@Column
	private Integer community_structure;
	
	@NotNull
	@Column
	private Integer community_feedback;
	
	@NotNull
	@Column
	private Integer impl_reinforcement;
	
	@NotNull
	@Column
	private Integer impl_confidentiality;
	
	@NotNull
	@Column
	private Integer impl_participation;
	
	@NotNull
	@Column
	private Integer track_rec_low;
	
	@NotNull
	@Column
	private Integer track_rec_mid;
	
	@NotNull
	@Column
	private Integer track_rec_high;

	@Size(max = 80)
	private String survey_reviewer;

	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date survey_review_date;
	
	@NotNull
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date entry_date;

	public Integer getLead_coordinator() {
		return lead_coordinator;
	}

	public void setLead_coordinator(Integer lead_coordinator) {
		this.lead_coordinator = lead_coordinator;
	}

	public Integer getLead_supervision() {
		return lead_supervision;
	}

	public void setLead_supervision(Integer lead_supervision) {
		this.lead_supervision = lead_supervision;
	}

	public Integer getLead_engagement() {
		return lead_engagement;
	}

	public void setLead_engagement(Integer lead_engagement) {
		this.lead_engagement = lead_engagement;
	}

	public Integer getPolicy_guidlines() {
		return policy_guidlines;
	}

	public void setPolicy_guidlines(Integer policy_guidlines) {
		this.policy_guidlines = policy_guidlines;
	}

	public Integer getStruct_mat_committee() {
		return struct_mat_committee;
	}

	public void setStruct_mat_committee(Integer struct_mat_committee) {
		this.struct_mat_committee = struct_mat_committee;
	}

	public Integer getStruct_peri_committee() {
		return struct_peri_committee;
	}

	public void setStruct_peri_committee(Integer struct_peri_committee) {
		this.struct_peri_committee = struct_peri_committee;
	}

	public Integer getStruct_multi_committee() {
		return struct_multi_committee;
	}

	public void setStruct_multi_committee(Integer struct_multi_committee) {
		this.struct_multi_committee = struct_multi_committee;
	}

	public Integer getStruct_formal_committee() {
		return struct_formal_committee;
	}

	public void setStruct_formal_committee(Integer struct_formal_committee) {
		this.struct_formal_committee = struct_formal_committee;
	}

	public Integer getMeet_exists() {
		return meet_exists;
	}

	public void setMeet_exists(Integer meet_exists) {
		this.meet_exists = meet_exists;
	}

	public Integer getMeet_routine() {
		return meet_routine;
	}

	public void setMeet_routine(Integer meet_routine) {
		this.meet_routine = meet_routine;
	}

	public Integer getTools_mat_available() {
		return tools_mat_available;
	}

	public void setTools_mat_available(Integer tools_mat_available) {
		this.tools_mat_available = tools_mat_available;
	}

	public Integer getTools_peri_available() {
		return tools_peri_available;
	}

	public void setTools_peri_available(Integer tools_peri_available) {
		this.tools_peri_available = tools_peri_available;
	}

	public Integer getTools_mat_used() {
		return tools_mat_used;
	}

	public void setTools_mat_used(Integer tools_mat_used) {
		this.tools_mat_used = tools_mat_used;
	}

	public Integer getTools_peri_used() {
		return tools_peri_used;
	}

	public void setTools_peri_used(Integer tools_peri_used) {
		this.tools_peri_used = tools_peri_used;
	}

	public Integer getTools_mat_correctly() {
		return tools_mat_correctly;
	}

	public void setTools_mat_correctly(Integer tools_mat_correctly) {
		this.tools_mat_correctly = tools_mat_correctly;
	}

	public Integer getTools_peri_correctly() {
		return tools_peri_correctly;
	}

	public void setTools_peri_correctly(Integer tools_peri_correctly) {
		this.tools_peri_correctly = tools_peri_correctly;
	}

	public Integer getTrain_any_done() {
		return train_any_done;
	}

	public void setTrain_any_done(Integer train_any_done) {
		this.train_any_done = train_any_done;
	}

	public Integer getTrain_recent_done() {
		return train_recent_done;
	}

	public void setTrain_recent_done(Integer train_recent_done) {
		this.train_recent_done = train_recent_done;
	}

	public Integer getTrain_embed_work() {
		return train_embed_work;
	}

	public void setTrain_embed_work(Integer train_embed_work) {
		this.train_embed_work = train_embed_work;
	}

	public Integer getRes_processes() {
		return res_processes;
	}

	public void setRes_processes(Integer res_processes) {
		this.res_processes = res_processes;
	}

	public Integer getRes_mobilisation() {
		return res_mobilisation;
	}

	public void setRes_mobilisation(Integer res_mobilisation) {
		this.res_mobilisation = res_mobilisation;
	}

	public Integer getRes_expenditure() {
		return res_expenditure;
	}

	public void setRes_expenditure(Integer res_expenditure) {
		this.res_expenditure = res_expenditure;
	}

	public Integer getCommunity_structure() {
		return community_structure;
	}

	public void setCommunity_structure(Integer community_structure) {
		this.community_structure = community_structure;
	}

	public Integer getCommunity_feedback() {
		return community_feedback;
	}

	public void setCommunity_feedback(Integer community_feedback) {
		this.community_feedback = community_feedback;
	}

	public Integer getImpl_reinforcement() {
		return impl_reinforcement;
	}

	public void setImpl_reinforcement(Integer impl_reinforcement) {
		this.impl_reinforcement = impl_reinforcement;
	}

	public Integer getImpl_confidentiality() {
		return impl_confidentiality;
	}

	public void setImpl_confidentiality(Integer impl_confidentiality) {
		this.impl_confidentiality = impl_confidentiality;
	}

	public Integer getImpl_participation() {
		return impl_participation;
	}

	public void setImpl_participation(Integer impl_participation) {
		this.impl_participation = impl_participation;
	}

	public Integer getTrack_rec_low() {
		return track_rec_low;
	}

	public void setTrack_rec_low(Integer track_rec_low) {
		this.track_rec_low = track_rec_low;
	}

	public Integer getTrack_rec_mid() {
		return track_rec_mid;
	}

	public void setTrack_rec_mid(Integer track_rec_mid) {
		this.track_rec_mid = track_rec_mid;
	}

	public Integer getTrack_rec_high() {
		return track_rec_high;
	}

	public void setTrack_rec_high(Integer track_rec_high) {
		this.track_rec_high = track_rec_high;
	}

	public String getSurvey_id() {
		return survey_id;
	}

	public void setSurvey_id(String survey_id) {
		this.survey_id = survey_id;
	}

	public Date getSurvey_date() {
		return survey_date;
	}

	public void setSurvey_date(Date survey_date) {
		this.survey_date = survey_date;
	}

	public String getSurvey_author() {
		return survey_author;
	}

	public void setSurvey_author(String survey_author) {
		this.survey_author = survey_author;
	}

	public String getSurvey_reviewer() {
		return survey_reviewer;
	}

	public void setSurvey_reviewer(String survey_reviewer) {
		this.survey_reviewer = survey_reviewer;
	}

	public Date getSurvey_review_date() {
		return survey_review_date;
	}

	public void setSurvey_review_date(Date survey_review_date) {
		this.survey_review_date = survey_review_date;
	}

	public Date getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(Date entry_date) {
		this.entry_date = entry_date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(survey_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		monitoring_tool other = (monitoring_tool) obj;
		return Objects.equals(survey_id, other.survey_id);
	}

	@Override
	public String toString() {
		return  survey_id;
	}
	
	
}
