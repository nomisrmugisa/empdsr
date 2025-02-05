package org.pdsr.slave.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class case_mdeath implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Size(min = 1, max = 80)
	private String mdeath_uuid;

	@MapsId
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "case_uuid", referencedColumnName = "case_uuid", insertable = true, updatable = true)
	private case_identifiers case_uuid;

	@Column
	private Integer mdeath_wra_misperiod;

	@Column
	private Integer mdeath_wra_stillpreg;

	@Column
	private Integer mdeath_wra_end42abort;

	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date mdeath_date;

	@Column
	private Integer mdeath_hour;

	@Column
	private Integer mdeath_minute;

	@Column
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date mdeath_time;

	@Column
	private Integer mdeath_datetime_notstated;

	@Column
	private Integer mdeath_autopsy;

	@Column
	private Date mdeath_autopsy_date;

	@Column
	private String mdeath_autopsy_location;

	@Column
	private Integer mdeath_autopsy_by;

	@Lob
	@Column
	private String mdeath_autopsy_final_cod;

	@Lob
	@Column
	private String mdeath_autopsy_antec_cod;

	@Lob
	@Column
	private String mdeath_autopsy_ops_cod;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "mdeath_icd", joinColumns = @JoinColumn(name = "icd_uuid"), inverseJoinColumns = @JoinColumn(name = "icd_code"))
	private List<icd_diagnoses> icd_diagnoses;

	@Column
	private Integer mdeath_early_evacuation;

	@Column
	private Integer mdeath_early_antibiotic;

	@Column
	private Integer mdeath_early_laparotomy;

	@Column
	private Integer mdeath_early_hysterectomy;

	@Column
	private Integer mdeath_early_transfusion;

	@Column
	private Integer mdeath_early_antihyper;

	@Column
	private String mdeath_early_other;

	@Column
	private Integer mdeath_ante_transfusion;

	@Column
	private Integer mdeath_ante_antibiotic;

	@Column
	private Integer mdeath_ante_externalv;

	@Column
	private Integer mdeath_ante_magsulphate;

	@Column
	private Integer mdeath_ante_diazepam;

	@Column
	private Integer mdeath_ante_antihyper;

	@Column
	private Integer mdeath_ante_hysterotomy;

	@Column
	private String mdeath_ante_other;

	@Column
	private Integer mdeath_intra_instrumental;

	@Column
	private Integer mdeath_intra_antibiotic;

	@Column
	private Integer mdeath_intra_caesarian;

	@Column
	private Integer mdeath_intra_hysterectomy;

	@Column
	private Integer mdeath_intra_transfusion;

	@Column
	private Integer mdeath_intra_magsulphate;

	@Column
	private Integer mdeath_intra_antihyper;

	@Column
	private Integer mdeath_intra_diazepam;

	@Column
	private String mdeath_intra_other;

	@Column
	private Integer mdeath_postpart_evacuation;

	@Column
	private Integer mdeath_postpart_antibiotic;

	@Column
	private Integer mdeath_postpart_laparotomy;

	@Column
	private Integer mdeath_postpart_hysterectomy;

	@Column
	private Integer mdeath_postpart_transfusion;

	@Column
	private Integer mdeath_postpart_magsulphate;

	@Column
	private Integer mdeath_postpart_placentaremoval;

	@Column
	private Integer mdeath_postpart_antihyper;

	@Column
	private Integer mdeath_postpart_diazepam;

	@Column
	private String mdeath_postpart_other;

	@Column
	private Integer mdeath_other_anaesthga;

	@Column
	private Integer mdeath_other_epidural;

	@Column
	private Integer mdeath_other_spinal;

	@Column
	private Integer mdeath_other_local;

	@Column
	private Integer mdeath_other_invasive;

	@Column
	private Integer mdeath_other_antihyper;

	@Column
	private Integer mdeath_other_icuventilation;

	@Lob
	@Column
	private String mdeath_new_intervention;

	@Lob
	@Column
	private String mdeath_json;

	@Column
	private Integer data_complete;

	public Integer getData_complete() {
		return data_complete;
	}

	public void setData_complete(Integer data_complete) {
		this.data_complete = data_complete;
	}

	public String getMdeath_uuid() {
		return mdeath_uuid;
	}

	public void setMdeath_uuid(String mdeath_uuid) {
		this.mdeath_uuid = mdeath_uuid;
	}

	public case_identifiers getCase_uuid() {
		return case_uuid;
	}

	public void setCase_uuid(case_identifiers case_uuid) {
		this.case_uuid = case_uuid;
	}

	public Integer getMdeath_wra_misperiod() {
		return mdeath_wra_misperiod;
	}

	public void setMdeath_wra_misperiod(Integer mdeath_wra_misperiod) {
		this.mdeath_wra_misperiod = mdeath_wra_misperiod;
	}

	public Integer getMdeath_wra_stillpreg() {
		return mdeath_wra_stillpreg;
	}

	public void setMdeath_wra_stillpreg(Integer mdeath_wra_stillpreg) {
		this.mdeath_wra_stillpreg = mdeath_wra_stillpreg;
	}

	public Integer getMdeath_wra_end42abort() {
		return mdeath_wra_end42abort;
	}

	public void setMdeath_wra_end42abort(Integer mdeath_wra_end42abort) {
		this.mdeath_wra_end42abort = mdeath_wra_end42abort;
	}

	public Date getMdeath_date() {
		return mdeath_date;
	}

	public void setMdeath_date(Date mdeath_date) {
		this.mdeath_date = mdeath_date;
	}

	public Integer getMdeath_hour() {
		return mdeath_hour;
	}

	public void setMdeath_hour(Integer mdeath_hour) {
		this.mdeath_hour = mdeath_hour;
	}

	public Integer getMdeath_minute() {
		return mdeath_minute;
	}

	public void setMdeath_minute(Integer mdeath_minute) {
		this.mdeath_minute = mdeath_minute;
	}

	public Date getMdeath_time() {
		return mdeath_time;
	}

	public void setMdeath_time(Date mdeath_time) {
		this.mdeath_time = mdeath_time;
	}

	public Integer getMdeath_datetime_notstated() {
		return mdeath_datetime_notstated;
	}

	public void setMdeath_datetime_notstated(Integer mdeath_datetime_notstated) {
		this.mdeath_datetime_notstated = mdeath_datetime_notstated;
	}

	public Integer getMdeath_autopsy() {
		return mdeath_autopsy;
	}

	public void setMdeath_autopsy(Integer mdeath_autopsy) {
		this.mdeath_autopsy = mdeath_autopsy;
	}

	public Date getMdeath_autopsy_date() {
		return mdeath_autopsy_date;
	}

	public void setMdeath_autopsy_date(Date mdeath_autopsy_date) {
		this.mdeath_autopsy_date = mdeath_autopsy_date;
	}

	public String getMdeath_autopsy_location() {
		return mdeath_autopsy_location;
	}

	public void setMdeath_autopsy_location(String mdeath_autopsy_location) {
		this.mdeath_autopsy_location = mdeath_autopsy_location;
	}

	public Integer getMdeath_autopsy_by() {
		return mdeath_autopsy_by;
	}

	public void setMdeath_autopsy_by(Integer mdeath_autopsy_by) {
		this.mdeath_autopsy_by = mdeath_autopsy_by;
	}

	public String getMdeath_autopsy_final_cod() {
		return mdeath_autopsy_final_cod;
	}

	public void setMdeath_autopsy_final_cod(String mdeath_autopsy_final_cod) {
		this.mdeath_autopsy_final_cod = mdeath_autopsy_final_cod;
	}

	public String getMdeath_autopsy_antec_cod() {
		return mdeath_autopsy_antec_cod;
	}

	public void setMdeath_autopsy_antec_cod(String mdeath_autopsy_antec_cod) {
		this.mdeath_autopsy_antec_cod = mdeath_autopsy_antec_cod;
	}

	public String getMdeath_autopsy_ops_cod() {
		return mdeath_autopsy_ops_cod;
	}

	public void setMdeath_autopsy_ops_cod(String mdeath_autopsy_ops_cod) {
		this.mdeath_autopsy_ops_cod = mdeath_autopsy_ops_cod;
	}

	public List<icd_diagnoses> getIcd_diagnoses() {
		return icd_diagnoses;
	}

	public void setIcd_diagnoses(List<icd_diagnoses> icd_diagnoses) {
		this.icd_diagnoses = icd_diagnoses;
	}
	
	public Integer getMdeath_early_evacuation() {
		return mdeath_early_evacuation;
	}

	public void setMdeath_early_evacuation(Integer mdeath_early_evacuation) {
		this.mdeath_early_evacuation = mdeath_early_evacuation;
	}

	public Integer getMdeath_early_antibiotic() {
		return mdeath_early_antibiotic;
	}

	public void setMdeath_early_antibiotic(Integer mdeath_early_antibiotic) {
		this.mdeath_early_antibiotic = mdeath_early_antibiotic;
	}

	public Integer getMdeath_early_laparotomy() {
		return mdeath_early_laparotomy;
	}

	public void setMdeath_early_laparotomy(Integer mdeath_early_laparotomy) {
		this.mdeath_early_laparotomy = mdeath_early_laparotomy;
	}

	public Integer getMdeath_early_hysterectomy() {
		return mdeath_early_hysterectomy;
	}

	public void setMdeath_early_hysterectomy(Integer mdeath_early_hysterectomy) {
		this.mdeath_early_hysterectomy = mdeath_early_hysterectomy;
	}

	public Integer getMdeath_early_transfusion() {
		return mdeath_early_transfusion;
	}

	public void setMdeath_early_transfusion(Integer mdeath_early_transfusion) {
		this.mdeath_early_transfusion = mdeath_early_transfusion;
	}

	public Integer getMdeath_early_antihyper() {
		return mdeath_early_antihyper;
	}

	public void setMdeath_early_antihyper(Integer mdeath_early_antihyper) {
		this.mdeath_early_antihyper = mdeath_early_antihyper;
	}

	public String getMdeath_early_other() {
		return mdeath_early_other;
	}

	public void setMdeath_early_other(String mdeath_early_other) {
		this.mdeath_early_other = mdeath_early_other;
	}

	public Integer getMdeath_ante_transfusion() {
		return mdeath_ante_transfusion;
	}

	public void setMdeath_ante_transfusion(Integer mdeath_ante_transfusion) {
		this.mdeath_ante_transfusion = mdeath_ante_transfusion;
	}

	public Integer getMdeath_ante_antibiotic() {
		return mdeath_ante_antibiotic;
	}

	public void setMdeath_ante_antibiotic(Integer mdeath_ante_antibiotic) {
		this.mdeath_ante_antibiotic = mdeath_ante_antibiotic;
	}

	public Integer getMdeath_ante_externalv() {
		return mdeath_ante_externalv;
	}

	public void setMdeath_ante_externalv(Integer mdeath_ante_externalv) {
		this.mdeath_ante_externalv = mdeath_ante_externalv;
	}

	public Integer getMdeath_ante_magsulphate() {
		return mdeath_ante_magsulphate;
	}

	public void setMdeath_ante_magsulphate(Integer mdeath_ante_magsulphate) {
		this.mdeath_ante_magsulphate = mdeath_ante_magsulphate;
	}

	public Integer getMdeath_ante_diazepam() {
		return mdeath_ante_diazepam;
	}

	public void setMdeath_ante_diazepam(Integer mdeath_ante_diazepam) {
		this.mdeath_ante_diazepam = mdeath_ante_diazepam;
	}

	public Integer getMdeath_ante_antihyper() {
		return mdeath_ante_antihyper;
	}

	public void setMdeath_ante_antihyper(Integer mdeath_ante_antihyper) {
		this.mdeath_ante_antihyper = mdeath_ante_antihyper;
	}

	public Integer getMdeath_ante_hysterotomy() {
		return mdeath_ante_hysterotomy;
	}

	public void setMdeath_ante_hysterotomy(Integer mdeath_ante_hysterotomy) {
		this.mdeath_ante_hysterotomy = mdeath_ante_hysterotomy;
	}

	public String getMdeath_ante_other() {
		return mdeath_ante_other;
	}

	public void setMdeath_ante_other(String mdeath_ante_other) {
		this.mdeath_ante_other = mdeath_ante_other;
	}

	public Integer getMdeath_intra_instrumental() {
		return mdeath_intra_instrumental;
	}

	public void setMdeath_intra_instrumental(Integer mdeath_intra_instrumental) {
		this.mdeath_intra_instrumental = mdeath_intra_instrumental;
	}

	public Integer getMdeath_intra_antibiotic() {
		return mdeath_intra_antibiotic;
	}

	public void setMdeath_intra_antibiotic(Integer mdeath_intra_antibiotic) {
		this.mdeath_intra_antibiotic = mdeath_intra_antibiotic;
	}

	public Integer getMdeath_intra_caesarian() {
		return mdeath_intra_caesarian;
	}

	public void setMdeath_intra_caesarian(Integer mdeath_intra_caesarian) {
		this.mdeath_intra_caesarian = mdeath_intra_caesarian;
	}

	public Integer getMdeath_intra_hysterectomy() {
		return mdeath_intra_hysterectomy;
	}

	public void setMdeath_intra_hysterectomy(Integer mdeath_intra_hysterectomy) {
		this.mdeath_intra_hysterectomy = mdeath_intra_hysterectomy;
	}

	public Integer getMdeath_intra_transfusion() {
		return mdeath_intra_transfusion;
	}

	public void setMdeath_intra_transfusion(Integer mdeath_intra_transfusion) {
		this.mdeath_intra_transfusion = mdeath_intra_transfusion;
	}

	public Integer getMdeath_intra_magsulphate() {
		return mdeath_intra_magsulphate;
	}

	public void setMdeath_intra_magsulphate(Integer mdeath_intra_magsulphate) {
		this.mdeath_intra_magsulphate = mdeath_intra_magsulphate;
	}

	public Integer getMdeath_intra_antihyper() {
		return mdeath_intra_antihyper;
	}

	public void setMdeath_intra_antihyper(Integer mdeath_intra_antihyper) {
		this.mdeath_intra_antihyper = mdeath_intra_antihyper;
	}

	public Integer getMdeath_intra_diazepam() {
		return mdeath_intra_diazepam;
	}

	public void setMdeath_intra_diazepam(Integer mdeath_intra_diazepam) {
		this.mdeath_intra_diazepam = mdeath_intra_diazepam;
	}

	public String getMdeath_intra_other() {
		return mdeath_intra_other;
	}

	public void setMdeath_intra_other(String mdeath_intra_other) {
		this.mdeath_intra_other = mdeath_intra_other;
	}

	public Integer getMdeath_postpart_evacuation() {
		return mdeath_postpart_evacuation;
	}

	public void setMdeath_postpart_evacuation(Integer mdeath_postpart_evacuation) {
		this.mdeath_postpart_evacuation = mdeath_postpart_evacuation;
	}

	public Integer getMdeath_postpart_antibiotic() {
		return mdeath_postpart_antibiotic;
	}

	public void setMdeath_postpart_antibiotic(Integer mdeath_postpart_antibiotic) {
		this.mdeath_postpart_antibiotic = mdeath_postpart_antibiotic;
	}

	public Integer getMdeath_postpart_laparotomy() {
		return mdeath_postpart_laparotomy;
	}

	public void setMdeath_postpart_laparotomy(Integer mdeath_postpart_laparotomy) {
		this.mdeath_postpart_laparotomy = mdeath_postpart_laparotomy;
	}

	public Integer getMdeath_postpart_hysterectomy() {
		return mdeath_postpart_hysterectomy;
	}

	public void setMdeath_postpart_hysterectomy(Integer mdeath_postpart_hysterectomy) {
		this.mdeath_postpart_hysterectomy = mdeath_postpart_hysterectomy;
	}

	public Integer getMdeath_postpart_transfusion() {
		return mdeath_postpart_transfusion;
	}

	public void setMdeath_postpart_transfusion(Integer mdeath_postpart_transfusion) {
		this.mdeath_postpart_transfusion = mdeath_postpart_transfusion;
	}

	public Integer getMdeath_postpart_magsulphate() {
		return mdeath_postpart_magsulphate;
	}

	public void setMdeath_postpart_magsulphate(Integer mdeath_postpart_magsulphate) {
		this.mdeath_postpart_magsulphate = mdeath_postpart_magsulphate;
	}

	public Integer getMdeath_postpart_placentaremoval() {
		return mdeath_postpart_placentaremoval;
	}

	public void setMdeath_postpart_placentaremoval(Integer mdeath_postpart_placentaremoval) {
		this.mdeath_postpart_placentaremoval = mdeath_postpart_placentaremoval;
	}

	public Integer getMdeath_postpart_antihyper() {
		return mdeath_postpart_antihyper;
	}

	public void setMdeath_postpart_antihyper(Integer mdeath_postpart_antihyper) {
		this.mdeath_postpart_antihyper = mdeath_postpart_antihyper;
	}

	public Integer getMdeath_postpart_diazepam() {
		return mdeath_postpart_diazepam;
	}

	public void setMdeath_postpart_diazepam(Integer mdeath_postpart_diazepam) {
		this.mdeath_postpart_diazepam = mdeath_postpart_diazepam;
	}

	public String getMdeath_postpart_other() {
		return mdeath_postpart_other;
	}

	public void setMdeath_postpart_other(String mdeath_postpart_other) {
		this.mdeath_postpart_other = mdeath_postpart_other;
	}

	public Integer getMdeath_other_anaesthga() {
		return mdeath_other_anaesthga;
	}

	public void setMdeath_other_anaesthga(Integer mdeath_other_anaesthga) {
		this.mdeath_other_anaesthga = mdeath_other_anaesthga;
	}

	public Integer getMdeath_other_epidural() {
		return mdeath_other_epidural;
	}

	public void setMdeath_other_epidural(Integer mdeath_other_epidural) {
		this.mdeath_other_epidural = mdeath_other_epidural;
	}

	public Integer getMdeath_other_spinal() {
		return mdeath_other_spinal;
	}

	public void setMdeath_other_spinal(Integer mdeath_other_spinal) {
		this.mdeath_other_spinal = mdeath_other_spinal;
	}

	public Integer getMdeath_other_local() {
		return mdeath_other_local;
	}

	public void setMdeath_other_local(Integer mdeath_other_local) {
		this.mdeath_other_local = mdeath_other_local;
	}

	public Integer getMdeath_other_invasive() {
		return mdeath_other_invasive;
	}

	public void setMdeath_other_invasive(Integer mdeath_other_invasive) {
		this.mdeath_other_invasive = mdeath_other_invasive;
	}

	public Integer getMdeath_other_antihyper() {
		return mdeath_other_antihyper;
	}

	public void setMdeath_other_antihyper(Integer mdeath_other_antihyper) {
		this.mdeath_other_antihyper = mdeath_other_antihyper;
	}

	public Integer getMdeath_other_icuventilation() {
		return mdeath_other_icuventilation;
	}

	public void setMdeath_other_icuventilation(Integer mdeath_other_icuventilation) {
		this.mdeath_other_icuventilation = mdeath_other_icuventilation;
	}

	public String getMdeath_new_intervention() {
		return mdeath_new_intervention;
	}

	public void setMdeath_new_intervention(String mdeath_new_intervention) {
		this.mdeath_new_intervention = mdeath_new_intervention;
	}

	public String getMdeath_json() {
		return mdeath_json;
	}

	public void setMdeath_json(String mdeath_json) {
		this.mdeath_json = mdeath_json;
	}

	@Override
	public int hashCode() {
		return Objects.hash(mdeath_uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		case_mdeath other = (case_mdeath) obj;
		return Objects.equals(mdeath_uuid, other.mdeath_uuid);
	}

}
