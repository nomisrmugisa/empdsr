package org.pdsr.json;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class json_redcap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// IDENTIFICATION
	private String record_id;

	private Date case_date;

	private String case_id;

	private String case_mid;

	private String case_mname;

	private Integer case_death;// 1 stillbirth or 2 early neonatal or 3 maternal

	private Integer case_status;// 0 entry, 1 auditing new submissions, 2 auditing started auditing, 3 process
								// complete
	private Integer case_identification_complete;

	// BIODATA
	private Integer biodata_sex;

	private Integer biodata_mage;

	private Integer biodata_medu;

	private Integer case_demographic_form_complete;

	// REFERAL

	private Integer referral_case;

	private Integer referral_patient;

	private Integer referral_source;

	private String referral_facility;

	private Date referral_date;

	private Date referral_time;

	private Integer referral_datetime_notstated;

	private Date referral_adate;

	private Date referral_atime;

	private Integer referral_adatetime_notstated;

	private Integer referral_transport;

	private String referral_notes;

	private byte[] referral_file;

	private Integer case_referral_form_complete;

	private Integer pregnancy_weeks;

	private Integer pregnancy_days;

	private Integer pregnancy_type;

	private Integer case_pregnancy_form_complete;

	private Integer antenatal_gravida;

	private Integer antenatal_para;

	private Integer antenatal_attend;

	private Integer antenatal_attendno;

	private String antenatal_facility;

	private Integer antenatal_weeks;// first visit

	private Integer antenatal_days;// first visit

	private Integer antenatal_hiv;// first visit

	private Integer antenatal_art;// first visit

	private Integer antenatal_alcohol;// first visit

	private Integer antenatal_smoker;// first visit

	private Integer antenatal_herbal;// first visit

	private Integer antenatal_folicacid;// first visit

	private Integer antenatal_folicacid3m;// first visit

	private Integer antenatal_tetanus;// first visit

	private Integer antenatal_malprophy;// firisit

	private Integer antenatal_risks;// firisit

	private String new_risks;

	private Integer case_antenatal_form_complete;

	private Date labour_seedate;

	private Date labour_seetime;

	private Integer labour_seedatetime_notstated;

	private Integer labour_seeperiod;

	private Integer labour_startmode;

	private Integer labour_herbalaug;

	private Integer labour_partograph;

	private Integer labour_lasthour1;

	private Integer labour_lastminute1;

	private Integer labour_lasthour2;

	private Integer labour_lastminute2;

	private Integer labour_complications;

	private String new_complications;

	private Integer case_labour_form_complete;

	private Date delivery_date;

	private Date delivery_time;

	private Double delivery_weight;

	private Integer delivery_datetime_notstated;

	private Integer delivery_period;// dawn,morning,afternoon,evening,night

	private Integer case_delivery_form_complete;

	private Integer birth_mode;

	private Integer birth_insistnormal;

	private Date birth_csproposedate;

	private Date birth_csproposetime;

	private Integer birth_provider;

	private Integer birth_facility;

	private Integer birth_abnormalities;

	private String new_abnormalities;

	private Integer birth_cordfaults;

	private String new_cordfaults;

	private Integer birth_placentachecks;

	private String new_placentachecks;

	private Integer birth_liqourvolume;

	private Integer birth_liqourcolor;

	private Integer birth_liqourodour;

	private Integer birth_motheroutcome;

	private Integer birth_babyoutcome;

	private Integer case_birth_form_complete;

	private Integer fetalheart_refered;

	private Integer fetalheart_arrival;

	private Integer fetalheart_lastheard;

	private Integer case_fetal_heart_complete;

	private Integer baby_cry;

	private Integer baby_resuscitation;

	private String new_resuscitation;

	private Integer baby_apgar1;

	private Integer baby_apgar5;

	private Integer baby_admitted;

	private String new_diagnoses;

	private String icd_diagnoses;

	private Date baby_ddate;

	private Date baby_dtime;

	private Integer baby_ddatetime_notstated;

	private Integer baby_medicalcod;

	private Integer case_baby_death_complete;

	private Integer mdeath_wra_misperiod;

	private Integer mdeath_wra_stillpreg;

	private Integer mdeath_wra_end42abort;

	private Date mdeath_date;

	private Integer mdeath_hour;

	private Integer mdeath_minute;

	private Date mdeath_time;

	private Integer mdeath_datetime_notstated;

	private Integer mdeath_autopsy;

	private Date mdeath_autopsy_date;

	private String mdeath_autopsy_location;

	private Integer mdeath_autopsy_by;

	private String mdeath_autopsy_final_cod;

	private String mdeath_autopsy_antec_cod;

	private String mdeath_autopsy_ops_cod;

	private String mdeath_icd_diagnoses;

	private Integer mdeath_early_evacuation;

	private Integer mdeath_early_antibiotic;

	private Integer mdeath_early_laparotomy;

	private Integer mdeath_early_hysterectomy;

	private Integer mdeath_early_transfusion;

	private Integer mdeath_early_antihyper;

	private String mdeath_early_other;

	private Integer mdeath_ante_transfusion;

	private Integer mdeath_ante_antibiotic;

	private Integer mdeath_ante_externalv;

	private Integer mdeath_ante_magsulphate;

	private Integer mdeath_ante_diazepam;

	private Integer mdeath_ante_antihyper;

	private Integer mdeath_ante_hysterotomy;

	private String mdeath_ante_other;

	private Integer mdeath_intra_instrumental;

	private Integer mdeath_intra_antibiotic;

	private Integer mdeath_intra_caesarian;

	private Integer mdeath_intra_hysterectomy;

	private Integer mdeath_intra_transfusion;

	private Integer mdeath_intra_magsulphate;

	private Integer mdeath_intra_antihyper;

	private Integer mdeath_intra_diazepam;

	private String mdeath_intra_other;

	private Integer mdeath_postpart_evacuation;

	private Integer mdeath_postpart_antibiotic;

	private Integer mdeath_postpart_laparotomy;

	private Integer mdeath_postpart_hysterectomy;

	private Integer mdeath_postpart_transfusion;

	private Integer mdeath_postpart_magsulphate;

	private Integer mdeath_postpart_placentaremoval;

	private Integer mdeath_postpart_antihyper;

	private Integer mdeath_postpart_diazepam;

	private String mdeath_postpart_other;

	private Integer mdeath_other_anaesthga;

	private Integer mdeath_other_epidural;

	private Integer mdeath_other_spinal;

	private Integer mdeath_other_local;

	private Integer mdeath_other_invasive;

	private Integer mdeath_other_antihyper;

	private Integer mdeath_other_icuventilation;

	private String mdeath_new_intervention;

	private Integer case_mdeath_complete;

	private String notes_text;

	private byte[] notes_file;

	private Integer case_narratives_complete;

	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	public Date getCase_date() {
		return case_date;
	}

	public void setCase_date(Date case_date) {
		this.case_date = case_date;
	}

	public String getCase_id() {
		return case_id;
	}

	public void setCase_id(String case_id) {
		this.case_id = case_id;
	}

	public String getCase_mid() {
		return case_mid;
	}

	public void setCase_mid(String case_mid) {
		this.case_mid = case_mid;
	}

	public String getCase_mname() {
		return case_mname;
	}

	public void setCase_mname(String case_mname) {
		this.case_mname = case_mname;
	}

	public Integer getCase_death() {
		return case_death;
	}

	public void setCase_death(Integer case_death) {
		this.case_death = case_death;
	}

	public Integer getCase_status() {
		return case_status;
	}

	public void setCase_status(Integer case_status) {
		this.case_status = case_status;
	}

	public Integer getCase_identification_complete() {
		return case_identification_complete;
	}

	public void setCase_identification_complete(Integer case_identification_complete) {
		this.case_identification_complete = case_identification_complete;
	}

	public Integer getBiodata_sex() {
		return biodata_sex;
	}

	public void setBiodata_sex(Integer biodata_sex) {
		this.biodata_sex = biodata_sex;
	}

	public Integer getBiodata_mage() {
		return biodata_mage;
	}

	public void setBiodata_mage(Integer biodata_mage) {
		this.biodata_mage = biodata_mage;
	}

	public Integer getBiodata_medu() {
		return biodata_medu;
	}

	public void setBiodata_medu(Integer biodata_medu) {
		this.biodata_medu = biodata_medu;
	}

	public Integer getCase_demographic_form_complete() {
		return case_demographic_form_complete;
	}

	public void setCase_demographic_form_complete(Integer case_demographic_form_complete) {
		this.case_demographic_form_complete = case_demographic_form_complete;
	}

	public Integer getReferral_case() {
		return referral_case;
	}

	public void setReferral_case(Integer referral_case) {
		this.referral_case = referral_case;
	}

	public Integer getReferral_patient() {
		return referral_patient;
	}

	public void setReferral_patient(Integer referral_patient) {
		this.referral_patient = referral_patient;
	}

	public Integer getReferral_source() {
		return referral_source;
	}

	public void setReferral_source(Integer referral_source) {
		this.referral_source = referral_source;
	}

	public String getReferral_facility() {
		return referral_facility;
	}

	public void setReferral_facility(String referral_facility) {
		this.referral_facility = referral_facility;
	}

	public Date getReferral_date() {
		return referral_date;
	}

	public void setReferral_date(Date referral_date) {
		this.referral_date = referral_date;
	}

	public Date getReferral_time() {
		return referral_time;
	}

	public void setReferral_time(Date referral_time) {
		this.referral_time = referral_time;
	}

	public Integer getReferral_datetime_notstated() {
		return referral_datetime_notstated;
	}

	public void setReferral_datetime_notstated(Integer referral_datetime_notstated) {
		this.referral_datetime_notstated = referral_datetime_notstated;
	}

	public Date getReferral_adate() {
		return referral_adate;
	}

	public void setReferral_adate(Date referral_adate) {
		this.referral_adate = referral_adate;
	}

	public Date getReferral_atime() {
		return referral_atime;
	}

	public void setReferral_atime(Date referral_atime) {
		this.referral_atime = referral_atime;
	}

	public Integer getReferral_adatetime_notstated() {
		return referral_adatetime_notstated;
	}

	public void setReferral_adatetime_notstated(Integer referral_adatetime_notstated) {
		this.referral_adatetime_notstated = referral_adatetime_notstated;
	}

	public Integer getReferral_transport() {
		return referral_transport;
	}

	public void setReferral_transport(Integer referral_transport) {
		this.referral_transport = referral_transport;
	}

	public String getReferral_notes() {
		return referral_notes;
	}

	public void setReferral_notes(String referral_notes) {
		this.referral_notes = referral_notes;
	}

	public byte[] getReferral_file() {
		return referral_file;
	}

	public void setReferral_file(byte[] referral_file) {
		this.referral_file = referral_file;
	}

	public Integer getCase_referral_form_complete() {
		return case_referral_form_complete;
	}

	public void setCase_referral_form_complete(Integer case_referral_form_complete) {
		this.case_referral_form_complete = case_referral_form_complete;
	}

	public Integer getPregnancy_weeks() {
		return pregnancy_weeks;
	}

	public void setPregnancy_weeks(Integer pregnancy_weeks) {
		this.pregnancy_weeks = pregnancy_weeks;
	}

	public Integer getPregnancy_days() {
		return pregnancy_days;
	}

	public void setPregnancy_days(Integer pregnancy_days) {
		this.pregnancy_days = pregnancy_days;
	}

	public Integer getPregnancy_type() {
		return pregnancy_type;
	}

	public void setPregnancy_type(Integer pregnancy_type) {
		this.pregnancy_type = pregnancy_type;
	}

	public Integer getCase_pregnancy_form_complete() {
		return case_pregnancy_form_complete;
	}

	public void setCase_pregnancy_form_complete(Integer case_pregnancy_form_complete) {
		this.case_pregnancy_form_complete = case_pregnancy_form_complete;
	}

	public Integer getAntenatal_gravida() {
		return antenatal_gravida;
	}

	public void setAntenatal_gravida(Integer antenatal_gravida) {
		this.antenatal_gravida = antenatal_gravida;
	}

	public Integer getAntenatal_para() {
		return antenatal_para;
	}

	public void setAntenatal_para(Integer antenatal_para) {
		this.antenatal_para = antenatal_para;
	}

	public Integer getAntenatal_attend() {
		return antenatal_attend;
	}

	public void setAntenatal_attend(Integer antenatal_attend) {
		this.antenatal_attend = antenatal_attend;
	}

	public Integer getAntenatal_attendno() {
		return antenatal_attendno;
	}

	public void setAntenatal_attendno(Integer antenatal_attendno) {
		this.antenatal_attendno = antenatal_attendno;
	}

	public String getAntenatal_facility() {
		return antenatal_facility;
	}

	public void setAntenatal_facility(String antenatal_facility) {
		this.antenatal_facility = antenatal_facility;
	}

	public Integer getAntenatal_weeks() {
		return antenatal_weeks;
	}

	public void setAntenatal_weeks(Integer antenatal_weeks) {
		this.antenatal_weeks = antenatal_weeks;
	}

	public Integer getAntenatal_days() {
		return antenatal_days;
	}

	public void setAntenatal_days(Integer antenatal_days) {
		this.antenatal_days = antenatal_days;
	}

	public Integer getAntenatal_hiv() {
		return antenatal_hiv;
	}

	public void setAntenatal_hiv(Integer antenatal_hiv) {
		this.antenatal_hiv = antenatal_hiv;
	}

	public Integer getAntenatal_art() {
		return antenatal_art;
	}

	public void setAntenatal_art(Integer antenatal_art) {
		this.antenatal_art = antenatal_art;
	}

	public Integer getAntenatal_alcohol() {
		return antenatal_alcohol;
	}

	public void setAntenatal_alcohol(Integer antenatal_alcohol) {
		this.antenatal_alcohol = antenatal_alcohol;
	}

	public Integer getAntenatal_smoker() {
		return antenatal_smoker;
	}

	public void setAntenatal_smoker(Integer antenatal_smoker) {
		this.antenatal_smoker = antenatal_smoker;
	}

	public Integer getAntenatal_herbal() {
		return antenatal_herbal;
	}

	public void setAntenatal_herbal(Integer antenatal_herbal) {
		this.antenatal_herbal = antenatal_herbal;
	}

	public Integer getAntenatal_folicacid() {
		return antenatal_folicacid;
	}

	public void setAntenatal_folicacid(Integer antenatal_folicacid) {
		this.antenatal_folicacid = antenatal_folicacid;
	}

	public Integer getAntenatal_folicacid3m() {
		return antenatal_folicacid3m;
	}

	public void setAntenatal_folicacid3m(Integer antenatal_folicacid3m) {
		this.antenatal_folicacid3m = antenatal_folicacid3m;
	}

	public Integer getAntenatal_tetanus() {
		return antenatal_tetanus;
	}

	public void setAntenatal_tetanus(Integer antenatal_tetanus) {
		this.antenatal_tetanus = antenatal_tetanus;
	}

	public Integer getAntenatal_malprophy() {
		return antenatal_malprophy;
	}

	public void setAntenatal_malprophy(Integer antenatal_malprophy) {
		this.antenatal_malprophy = antenatal_malprophy;
	}

	public Integer getAntenatal_risks() {
		return antenatal_risks;
	}

	public void setAntenatal_risks(Integer antenatal_risks) {
		this.antenatal_risks = antenatal_risks;
	}

	public String getNew_risks() {
		return new_risks;
	}

	public void setNew_risks(String new_risks) {
		this.new_risks = new_risks;
	}

	public Integer getCase_antenatal_form_complete() {
		return case_antenatal_form_complete;
	}

	public void setCase_antenatal_form_complete(Integer case_antenatal_form_complete) {
		this.case_antenatal_form_complete = case_antenatal_form_complete;
	}

	public Date getLabour_seedate() {
		return labour_seedate;
	}

	public void setLabour_seedate(Date labour_seedate) {
		this.labour_seedate = labour_seedate;
	}

	public Date getLabour_seetime() {
		return labour_seetime;
	}

	public void setLabour_seetime(Date labour_seetime) {
		this.labour_seetime = labour_seetime;
	}

	public Integer getLabour_seedatetime_notstated() {
		return labour_seedatetime_notstated;
	}

	public void setLabour_seedatetime_notstated(Integer labour_seedatetime_notstated) {
		this.labour_seedatetime_notstated = labour_seedatetime_notstated;
	}

	public Integer getLabour_seeperiod() {
		return labour_seeperiod;
	}

	public void setLabour_seeperiod(Integer labour_seeperiod) {
		this.labour_seeperiod = labour_seeperiod;
	}

	public Integer getLabour_startmode() {
		return labour_startmode;
	}

	public void setLabour_startmode(Integer labour_startmode) {
		this.labour_startmode = labour_startmode;
	}

	public Integer getLabour_herbalaug() {
		return labour_herbalaug;
	}

	public void setLabour_herbalaug(Integer labour_herbalaug) {
		this.labour_herbalaug = labour_herbalaug;
	}

	public Integer getLabour_partograph() {
		return labour_partograph;
	}

	public void setLabour_partograph(Integer labour_partograph) {
		this.labour_partograph = labour_partograph;
	}

	public Integer getLabour_lasthour1() {
		return labour_lasthour1;
	}

	public void setLabour_lasthour1(Integer labour_lasthour1) {
		this.labour_lasthour1 = labour_lasthour1;
	}

	public Integer getLabour_lastminute1() {
		return labour_lastminute1;
	}

	public void setLabour_lastminute1(Integer labour_lastminute1) {
		this.labour_lastminute1 = labour_lastminute1;
	}

	public Integer getLabour_lasthour2() {
		return labour_lasthour2;
	}

	public void setLabour_lasthour2(Integer labour_lasthour2) {
		this.labour_lasthour2 = labour_lasthour2;
	}

	public Integer getLabour_lastminute2() {
		return labour_lastminute2;
	}

	public void setLabour_lastminute2(Integer labour_lastminute2) {
		this.labour_lastminute2 = labour_lastminute2;
	}

	public Integer getLabour_complications() {
		return labour_complications;
	}

	public void setLabour_complications(Integer labour_complications) {
		this.labour_complications = labour_complications;
	}

	public String getNew_complications() {
		return new_complications;
	}

	public void setNew_complications(String new_complications) {
		this.new_complications = new_complications;
	}

	public Integer getCase_labour_form_complete() {
		return case_labour_form_complete;
	}

	public void setCase_labour_form_complete(Integer case_labour_form_complete) {
		this.case_labour_form_complete = case_labour_form_complete;
	}

	public Date getDelivery_date() {
		return delivery_date;
	}

	public void setDelivery_date(Date delivery_date) {
		this.delivery_date = delivery_date;
	}

	public Double getDelivery_weight() {
		return delivery_weight;
	}

	public void setDelivery_weight(Double delivery_weight) {
		this.delivery_weight = delivery_weight;
	}

	public Date getDelivery_time() {
		return delivery_time;
	}

	public void setDelivery_time(Date delivery_time) {
		this.delivery_time = delivery_time;
	}

	public Integer getDelivery_datetime_notstated() {
		return delivery_datetime_notstated;
	}

	public void setDelivery_datetime_notstated(Integer delivery_datetime_notstated) {
		this.delivery_datetime_notstated = delivery_datetime_notstated;
	}

	public Integer getDelivery_period() {
		return delivery_period;
	}

	public void setDelivery_period(Integer delivery_period) {
		this.delivery_period = delivery_period;
	}

	public Integer getCase_delivery_form_complete() {
		return case_delivery_form_complete;
	}

	public void setCase_delivery_form_complete(Integer case_delivery_form_complete) {
		this.case_delivery_form_complete = case_delivery_form_complete;
	}

	public Integer getBirth_mode() {
		return birth_mode;
	}

	public void setBirth_mode(Integer birth_mode) {
		this.birth_mode = birth_mode;
	}

	public Integer getBirth_insistnormal() {
		return birth_insistnormal;
	}

	public void setBirth_insistnormal(Integer birth_insistnormal) {
		this.birth_insistnormal = birth_insistnormal;
	}

	public Date getBirth_csproposedate() {
		return birth_csproposedate;
	}

	public void setBirth_csproposedate(Date birth_csproposedate) {
		this.birth_csproposedate = birth_csproposedate;
	}

	public Date getBirth_csproposetime() {
		return birth_csproposetime;
	}

	public void setBirth_csproposetime(Date birth_csproposetime) {
		this.birth_csproposetime = birth_csproposetime;
	}

	public Integer getBirth_provider() {
		return birth_provider;
	}

	public void setBirth_provider(Integer birth_provider) {
		this.birth_provider = birth_provider;
	}

	public Integer getBirth_facility() {
		return birth_facility;
	}

	public void setBirth_facility(Integer birth_facility) {
		this.birth_facility = birth_facility;
	}

	public Integer getBirth_abnormalities() {
		return birth_abnormalities;
	}

	public void setBirth_abnormalities(Integer birth_abnormalities) {
		this.birth_abnormalities = birth_abnormalities;
	}

	public String getNew_abnormalities() {
		return new_abnormalities;
	}

	public void setNew_abnormalities(String new_abnormalities) {
		this.new_abnormalities = new_abnormalities;
	}

	public Integer getBirth_cordfaults() {
		return birth_cordfaults;
	}

	public void setBirth_cordfaults(Integer birth_cordfaults) {
		this.birth_cordfaults = birth_cordfaults;
	}

	public String getNew_cordfaults() {
		return new_cordfaults;
	}

	public void setNew_cordfaults(String new_cordfaults) {
		this.new_cordfaults = new_cordfaults;
	}

	public Integer getBirth_placentachecks() {
		return birth_placentachecks;
	}

	public void setBirth_placentachecks(Integer birth_placentachecks) {
		this.birth_placentachecks = birth_placentachecks;
	}

	public String getNew_placentachecks() {
		return new_placentachecks;
	}

	public void setNew_placentachecks(String new_placentachecks) {
		this.new_placentachecks = new_placentachecks;
	}

	public Integer getBirth_liqourvolume() {
		return birth_liqourvolume;
	}

	public void setBirth_liqourvolume(Integer birth_liqourvolume) {
		this.birth_liqourvolume = birth_liqourvolume;
	}

	public Integer getBirth_liqourcolor() {
		return birth_liqourcolor;
	}

	public void setBirth_liqourcolor(Integer birth_liqourcolor) {
		this.birth_liqourcolor = birth_liqourcolor;
	}

	public Integer getBirth_liqourodour() {
		return birth_liqourodour;
	}

	public void setBirth_liqourodour(Integer birth_liqourodour) {
		this.birth_liqourodour = birth_liqourodour;
	}

	public Integer getBirth_motheroutcome() {
		return birth_motheroutcome;
	}

	public void setBirth_motheroutcome(Integer birth_motheroutcome) {
		this.birth_motheroutcome = birth_motheroutcome;
	}

	public Integer getBirth_babyoutcome() {
		return birth_babyoutcome;
	}

	public void setBirth_babyoutcome(Integer birth_babyoutcome) {
		this.birth_babyoutcome = birth_babyoutcome;
	}

	public Integer getCase_birth_form_complete() {
		return case_birth_form_complete;
	}

	public void setCase_birth_form_complete(Integer case_birth_form_complete) {
		this.case_birth_form_complete = case_birth_form_complete;
	}

	public Integer getFetalheart_refered() {
		return fetalheart_refered;
	}

	public void setFetalheart_refered(Integer fetalheart_refered) {
		this.fetalheart_refered = fetalheart_refered;
	}

	public Integer getFetalheart_arrival() {
		return fetalheart_arrival;
	}

	public void setFetalheart_arrival(Integer fetalheart_arrival) {
		this.fetalheart_arrival = fetalheart_arrival;
	}

	public Integer getFetalheart_lastheard() {
		return fetalheart_lastheard;
	}

	public void setFetalheart_lastheard(Integer fetalheart_lastheard) {
		this.fetalheart_lastheard = fetalheart_lastheard;
	}

	public Integer getCase_fetal_heart_complete() {
		return case_fetal_heart_complete;
	}

	public void setCase_fetal_heart_complete(Integer case_fetal_heart_complete) {
		this.case_fetal_heart_complete = case_fetal_heart_complete;
	}

	public Integer getBaby_cry() {
		return baby_cry;
	}

	public void setBaby_cry(Integer baby_cry) {
		this.baby_cry = baby_cry;
	}

	public Integer getBaby_resuscitation() {
		return baby_resuscitation;
	}

	public void setBaby_resuscitation(Integer baby_resuscitation) {
		this.baby_resuscitation = baby_resuscitation;
	}

	public String getNew_resuscitation() {
		return new_resuscitation;
	}

	public void setNew_resuscitation(String new_resuscitation) {
		this.new_resuscitation = new_resuscitation;
	}

	public Integer getBaby_apgar1() {
		return baby_apgar1;
	}

	public void setBaby_apgar1(Integer baby_apgar1) {
		this.baby_apgar1 = baby_apgar1;
	}

	public Integer getBaby_apgar5() {
		return baby_apgar5;
	}

	public void setBaby_apgar5(Integer baby_apgar5) {
		this.baby_apgar5 = baby_apgar5;
	}

	public Integer getBaby_admitted() {
		return baby_admitted;
	}

	public void setBaby_admitted(Integer baby_admitted) {
		this.baby_admitted = baby_admitted;
	}

	public String getNew_diagnoses() {
		return new_diagnoses;
	}

	public void setNew_diagnoses(String new_diagnoses) {
		this.new_diagnoses = new_diagnoses;
	}

	public String getIcd_diagnoses() {
		return icd_diagnoses;
	}

	public void setIcd_diagnoses(String icd_diagnoses) {
		this.icd_diagnoses = icd_diagnoses;
	}

	public Date getBaby_ddate() {
		return baby_ddate;
	}

	public void setBaby_ddate(Date baby_ddate) {
		this.baby_ddate = baby_ddate;
	}

	public Date getBaby_dtime() {
		return baby_dtime;
	}

	public void setBaby_dtime(Date baby_dtime) {
		this.baby_dtime = baby_dtime;
	}

	public Integer getBaby_ddatetime_notstated() {
		return baby_ddatetime_notstated;
	}

	public void setBaby_ddatetime_notstated(Integer baby_ddatetime_notstated) {
		this.baby_ddatetime_notstated = baby_ddatetime_notstated;
	}

	public Integer getBaby_medicalcod() {
		return baby_medicalcod;
	}

	public void setBaby_medicalcod(Integer baby_medicalcod) {
		this.baby_medicalcod = baby_medicalcod;
	}

	public Integer getCase_baby_death_complete() {
		return case_baby_death_complete;
	}

	public void setCase_baby_death_complete(Integer case_baby_death_complete) {
		this.case_baby_death_complete = case_baby_death_complete;
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

	public String getMdeath_icd_diagnoses() {
		return mdeath_icd_diagnoses;
	}

	public void setMdeath_icd_diagnoses(String mdeath_icd_diagnoses) {
		this.mdeath_icd_diagnoses = mdeath_icd_diagnoses;
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

	public Integer getCase_mdeath_complete() {
		return case_mdeath_complete;
	}

	public void setCase_mdeath_complete(Integer case_mdeath_complete) {
		this.case_mdeath_complete = case_mdeath_complete;
	}

	public String getNotes_text() {
		return notes_text;
	}

	public void setNotes_text(String notes_text) {
		this.notes_text = notes_text;
	}

	public byte[] getNotes_file() {
		return notes_file;
	}

	public void setNotes_file(byte[] notes_file) {
		this.notes_file = notes_file;
	}

	public Integer getCase_narratives_complete() {
		return case_narratives_complete;
	}

	public void setCase_narratives_complete(Integer case_narratives_complete) {
		this.case_narratives_complete = case_narratives_complete;
	}

	@Override
	public int hashCode() {
		return Objects.hash(record_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		json_redcap other = (json_redcap) obj;
		return Objects.equals(record_id, other.record_id);
	}

	@Override
	public String toString() {
		return record_id;
	}

}
