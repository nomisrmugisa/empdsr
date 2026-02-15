-- Activation script for new_main_empdsr (Ghana Version)
-- This script inserts the required license record to activate the application

-- Insert the MPDRSGHANA license record into sync_table
-- This will prevent the "Not Activated" message from appearing
MERGE INTO sync_table (sync_id, sync_code, sync_name, sync_email, sync_url, sync_json) 
KEY(sync_id) 
VALUES (
    'MPDRSGHANA',           -- sync_id (LICENSE_ID)
    'TEST001',              -- sync_code (FACILITY_ID from facilities.csv)
    'Ejisu Hospital',       -- sync_name (facility name from facilities.csv)
    'admin@example.com',    -- sync_email
    'https://olincgroup.com/pdsr/mytest',  -- sync_url
    null                    -- sync_json
);

-- Insert dropdown options for Relationship to Next of Kin
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('rnok_options',1,'Spouse');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('rnok_options',2,'Parent');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('rnok_options',3,'Sibling');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('rnok_options',4,'Relative');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('rnok_options',5,'Friend');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('rnok_options',6,'Non Relative');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('rnok_options',88,'Not Stated');

-- Core Lookup Tables Restoration
MERGE INTO ipts_table KEY(ipt_name) values('IPT1','none');
MERGE INTO ipts_table KEY(ipt_name) values('IPT2','none');
MERGE INTO ipts_table KEY(ipt_name) values('IPT3','none');
MERGE INTO ipts_table KEY(ipt_name) values('IPT4','none');
MERGE INTO ipts_table KEY(ipt_name) values('IPT5','none');

MERGE INTO ttdp_table KEY(td_name) values('TD1','none');
MERGE INTO ttdp_table KEY(td_name) values('TD2','none');
MERGE INTO ttdp_table KEY(td_name) values('TD3','none');

MERGE INTO risk_table KEY(risk_name) values('Antepartum Hemorrhage','none');
MERGE INTO risk_table KEY(risk_name) values('Hypertension','none');
MERGE INTO risk_table KEY(risk_name) values('Pre-labour rupture of membranes','none');
MERGE INTO risk_table KEY(risk_name) values('Diabetes','none');
MERGE INTO risk_table KEY(risk_name) values('Anaemia','none');
MERGE INTO risk_table KEY(risk_name) values('UTI','none');
MERGE INTO risk_table KEY(risk_name) values('Malaria','none');
MERGE INTO risk_table KEY(risk_name) values('Bleeding','none');
MERGE INTO risk_table KEY(risk_name) values('Proteinuria','none');
MERGE INTO risk_table KEY(risk_name) values('Glycosuria','none');
MERGE INTO risk_table KEY(risk_name) values('Adolescents','none');
MERGE INTO risk_table KEY(risk_name) values('Grand Multiparity','none');
MERGE INTO risk_table KEY(risk_name) values('Sickle Cell','none');
MERGE INTO risk_table KEY(risk_name) values('Asthma','none');
MERGE INTO risk_table KEY(risk_name) values('APH','none');
MERGE INTO risk_table KEY(risk_name) values('PPH','none');
MERGE INTO risk_table KEY(risk_name) values('Trauma-accidental','none');
MERGE INTO risk_table KEY(risk_name) values('Gender based violence','none');
MERGE INTO risk_table KEY(risk_name) values('Multiple pregnancy','none');
MERGE INTO risk_table KEY(risk_name) values('Drugs/Medicines','none');
MERGE INTO risk_table KEY(risk_name) values('History of reduced fetal movements','none');
MERGE INTO risk_table KEY(risk_name) values('Cervical / uterine surgery','none');
MERGE INTO risk_table KEY(risk_name) values('Last delivery > 10 years','none');
MERGE INTO risk_table KEY(risk_name) values('Last delivery < 2 years','none');

MERGE INTO complication_table KEY(complication_name) values('Cephalo-pelvic disproportion (CPD)','none');
MERGE INTO complication_table KEY(complication_name) values('Premature rupture of membranes','none');
MERGE INTO complication_table KEY(complication_name) values('Infection (including UTI/Chorioamnionitis)','none');
MERGE INTO complication_table KEY(complication_name) values('Hypertension (Pre-eclampsia)','none');
MERGE INTO complication_table KEY(complication_name) values('Gestational diabetes','none');
MERGE INTO complication_table KEY(complication_name) values('Uterine rupture','none');
MERGE INTO complication_table KEY(complication_name) values('Poor progress of labour','none');
MERGE INTO complication_table KEY(complication_name) values('Delayed 2nd stage','none');
MERGE INTO complication_table KEY(complication_name) values('Fetal distress','none');
MERGE INTO complication_table KEY(complication_name) values('Transverse/oblique lie','none');
MERGE INTO complication_table KEY(complication_name) values('Breech, hand,foot, or other compound presentations','none');
MERGE INTO complication_table KEY(complication_name) values('Obstetric haemorrhage (antepartum)','none');
MERGE INTO complication_table KEY(complication_name) values('Latrogenic complications','none');
MERGE INTO complication_table KEY(complication_name) values('Severe anaemia','none');
MERGE INTO complication_table KEY(complication_name) values('Shock/sudden collapse','none');

MERGE INTO abnormality_table KEY(abnormal_name) values('Microcephaly','none');
MERGE INTO abnormality_table KEY(abnormal_name) values('Exomphalos major','none');
MERGE INTO abnormality_table KEY(abnormal_name) values('Heart Defects','none');
MERGE INTO abnormality_table KEY(abnormal_name) values('Tetralogy of Fallot','none');
MERGE INTO abnormality_table KEY(abnormal_name) values('Neural Tube Defects (Spina Bifida)','none');
MERGE INTO abnormality_table KEY(abnormal_name) values('Hemangioma','none');
MERGE INTO abnormality_table KEY(abnormal_name) values('Osteogenesis Imperfecta','none');
MERGE INTO abnormality_table KEY(abnormal_name) values('Micrognathia','none');
MERGE INTO abnormality_table KEY(abnormal_name) values('Dysmorphic features','none');
MERGE INTO abnormality_table KEY(abnormal_name) values('Dyspigmentation','none');

MERGE INTO cordfault_table KEY(cordfault_name) values('Kinks and knots','none');
MERGE INTO cordfault_table KEY(cordfault_name) values('Degenerated cord','none');

MERGE INTO placentacheck_table KEY(placentacheck_name) values('Small size','none');
MERGE INTO placentacheck_table KEY(placentacheck_name) values('Pale placenta','none');
MERGE INTO placentacheck_table KEY(placentacheck_name) values('Foul-smelling','none');
MERGE INTO placentacheck_table KEY(placentacheck_name) values('Degenerated','none');
MERGE INTO placentacheck_table KEY(placentacheck_name) values('Loss of architecture','none');
MERGE INTO placentacheck_table KEY(placentacheck_name) values('Calcifications','none');
MERGE INTO placentacheck_table KEY(placentacheck_name) values('Accreta','none');
MERGE INTO placentacheck_table KEY(placentacheck_name) values('Praevia','none');
MERGE INTO placentacheck_table KEY(placentacheck_name) values('Abruptio','none');
MERGE INTO placentacheck_table KEY(placentacheck_name) values('Percreta','none');

MERGE INTO resuscitation_table KEY(resuscitation_name) values('Suction','Suction');
MERGE INTO resuscitation_table KEY(resuscitation_name) values('Stimulation','Stimulation');
MERGE INTO resuscitation_table KEY(resuscitation_name) values('IPPV bag and mask','IPPV bag and mask');
MERGE INTO resuscitation_table KEY(resuscitation_name) values('Oxygen','Oxygen');
MERGE INTO resuscitation_table KEY(resuscitation_name) values('Intubation','Intubation');
MERGE INTO resuscitation_table KEY(resuscitation_name) values('Medication','Medication');
MERGE INTO resuscitation_table KEY(resuscitation_name) values('Chest compression','Chest compression');
MERGE INTO resuscitation_table KEY(resuscitation_name) values('Advanced resuscitation with adrenaline','Chest compression');
MERGE INTO resuscitation_table KEY(resuscitation_name) values('Endotracheal and Positive pressure ventilation','Endotracheal and Positive pressure ventilation');

MERGE INTO diagnoses_table KEY(diagnosis_name) values('Prematurity','Prematurity');
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Neonatal Jaundice','Neonatal Jaundice');
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Difficulty breathing','Difficulty breathing');
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Difficulty breast feeding (mother)','Difficulty breast feeding (mother)');
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Difficulty breast feeding (baby)','Difficulty breast feeding (baby)');	
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Convulsions/seizures','Convulsions/seizures');
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Hypothermia','Hypothermia');
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Bulging fontanelle','Bulging fontanelle');
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Anaemia','Anaemia');
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Jaundice','Jaundice');
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Fever','Fever');
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Bleeding','Bleeding');
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Hypoglycaemia','Hypoglycaemia');
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Poor feeding','Poor feeding');
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Infection','Infection');
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Congenital anomalies','Congenital anomalies');
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Meconium aspiration','Meconium aspiration');
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Septicaemia','Septicaemia');
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Respiratory Distress Syndrome (RDS)','Respiratory Distress Syndrome (RDS)');
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Cardiac Abnormalities','Cardiac Abnormalities');
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Birth Asphyxia','Birth Asphyxia');
MERGE INTO diagnoses_table KEY(diagnosis_name) values('Big baby (Macrosomia)','Big baby (Macrosomia)');

-- Audit and Monitoring Tables Restoration
MERGE INTO mcgroup_table(icdmgroup,icdmgroup_name) KEY(icdmgroup) values('M1','Complications of placenta, cord and membranes');
MERGE INTO mcgroup_table(icdmgroup,icdmgroup_name) KEY(icdmgroup) values('M2','Maternal complications of pregnancy');
MERGE INTO mcgroup_table(icdmgroup,icdmgroup_name) KEY(icdmgroup) values('M3','Other complications of labour and delivery');
MERGE INTO mcgroup_table(icdmgroup,icdmgroup_name) KEY(icdmgroup) values('M4','Maternal medical and surgical conditions');

MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M1.1','M1','placenta praevia');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M1.2','M1','other forms of placental separation and haemorrhage');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M1.3','M1','placental dysfunction, infarction, insufficiency');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M1.4','M1','fetal-placental transfusion syndromes');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M1.5','M1','prolapsed cord, other compression of umbilical cord');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M1.6','M1','chorioamnionitis');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M1.7','M1','other complications of membranes');

MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M2.1','M2','incompetent cervix');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M2.2','M2','preterm rupture of membranes');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M2.3','M2','oligohydramnios/polyhydramnios');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M2.4','M2','ectopic pregnancy');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M2.5','M2','multiple pregnancy');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M2.6','M2','maternal death');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M2.7','M2','malpresentation before labour');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M2.8','M2','other complications of pregnancy');

MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M3.1','M3','breech delivery and extraction');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M3.2','M3','other malpresentation, malposition and disproportion during labour and delivery');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M3.3','M3','forceps delivery/vacuum extraction');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M3.4','M3','caesarean delivery');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M3.5','M3','precipitate delivery');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M3.6','M3','preterm labour and delivery');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M3.7','M3','other complications of labour and delivery, including');

MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M4.1','M4','pre-eclampsia, eclampsia');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M4.2','M4','gestational hypertension');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M4.3','M4','other hypertensive disorders');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M4.4','M4','renal and urinary tract diseases');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M4.5','M4','infectious and parasitic disease');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M4.6','M4','circulatory and respiratory disease');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M4.7','M4','nutritional disorders');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M4.8','M4','injury');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M4.9','M4','surgical procedure');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M4.10','M4','other medical procedures');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M4.11','M4','maternal diabetes, including gestational diabetes');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M4.12','M4','maternal anaesthesia and analgesia');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M4.13','M4','maternal medication');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M4.14','M4','tobacco/alcohol/drugs of addiction');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M4.15','M4','nutritional chemical substances');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M4.16','M4','environmental chemical substances');
MERGE INTO mcondition_table(icdm,icdmgroup,icdm_name) KEY(icdm) values('M4.17','M4','unspecified maternal condition');

MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(101,100,'Never initiated antenatal care');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(102,100,'Booked late in pregnancy');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(103,100,'Inadequate visits to antenatal clinic (<4 visits by  36/52)');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(104,100,'Delay in seeking care when complications set in');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(105,100,'Delay in seeking medical attention during labour');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(106,100,'Attempted termination of pregnancy');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(107,100,'Failed to return on prescribed date');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(108,100,'Declined admission/treatment for personal/social reasons');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(109,100,'Partner/family declined admission/treatment');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(110,100,'Assault');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(111,100,'Substance abuse (alcohol, smoking etc.)');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(112,100,'Delay in seeking help when baby was ill');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(113,100,'Infanticide');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(114,100,'Abandoned baby');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(115,100,'Use of herbal medicine');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(116,100,'Financial constraints');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(117,100,'No knowledge of danger symptoms');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(118,100,'ANC routine labs not done (Hb, Syphilis, Blood Group etc.)');

MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(201,200,'Lack of transport - Home to facility');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(202,200,'Lack of transport between facilities');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(203,200,'Patient transfer not communicated to receiving Facility');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(204,200,'Inappropriate care during transport');

MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(301,300,'ANC routine labs not done due to shortages (Hb, Syphilis, Blood Grp etc.)');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(302,300,'Inadequate facilities / resuscitation equipment in neonatal unit / Nursery');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(303,300,'Inadequate theatre facilities');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(304,300,'Insufficient blood / blood products available');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(305,300,'Personnel insufficiently trained or too junior to manage the patient');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(306,300,'Insufficient nurses on duty to manage the patient adequately');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(307,300,'Insufficient doctors to manage the patient');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(308,300,'Anaesthetic delay');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(309,300,'Delay in securing bed space at receiving facility');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(310,300,'Health worker industrial strike');

MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(401,400,'Poor estimation of foetal size by medical personnel');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(402,400,'Inappropriate management of complications when they set in during antepartum period');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(403,400,'No or inappropriate intervention to maternal hypertension');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(404,400,'Abnormal presentation not diagnosed until late in labour');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(405,400,'Multiple pregnancy not diagnosed during ANC or intrapartum');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(406,400,'Partogram not used to monitor labour');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(407,400,'Partogram used to monitor labour, but not interpreted correctly');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(408,400,'Poor progress in labour, not picked up early');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(409,400,'Foetal distress not detected during ANC or intrapartum, foetus not monitored');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(410,400,'Foetal distress not detected during ANC or intrapartum, foetus monitored');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(411,400,'Antenatal steroids not given');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(412,400,'Incorrect management of complications');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(413,400,'Incorrect or missed diagnosis');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(414,400,'Physical examination of the patient at the clinic/hospital incomplete');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(415,400,'Management of the second stage prolonged with no intervention');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(416,400,'Management of the second stage prolonged with inappropriate intervention(s)');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(417,400,'Delay in medical personnel calling for expert assistance');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(418,400,'Delay in referring patient for secondary/tertiary care');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(419,400,'Inappropriate response to apparent post-term pregnancy');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(420,400,'Neonatal care: inadequate monitoring');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(421,400,'Neonatal resuscitation inadequate');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(422,400,'Neonatal care: management plan inadequate');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(423,400,'Baby sent home inappropriately when not well');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(424,400,'Doctor did not respond to call or delayed in responding');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(425,400,'Nosocomial infection');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(426,400,'Baby managed incorrectly at Hospital/Clinic');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(427,400,'Anaesthesia complications during operative delivery');

MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(501,500,'Insufficient notes');
MERGE INTO cfactor_table(id,idgroup,cfactor_name) KEY(id) values(502,500,'Patient notes or mothers ANC card missing');

MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(10,'Total Deliveries','100','Total deliveries','mothers who gave birth',1);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(10,'Total Deliveries','101','SVDs/NVDs','none',0);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(10,'Total Deliveries','102','Assisted (forceps, vacuum, etc)','none',0);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(10,'Total Deliveries','103','Caesarean Sections','none',0);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(11,'Total Births','110','Total births','babies born - livebirths and stillbirths', 1);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(11,'Total Births','111','Singletons','none',0);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(11,'Total Births','112','Multiple','none',0);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(12,'Stillbirths','120','Total stillbirths','Total babies born dead after 28 weeks gestation',1);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(12,'Stillbirths','121','Antepartum or Macerated Stillbirths  (died  before labour started)','none',0);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(12,'Stillbirths','122','Intrapartum or Fresh Stillbirth (died during labour)','none',0);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(13,'Livebirths','130','Total livebirths','Total babies alive at birth',1);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(13,'Livebirths','131','Term births (37 completed weeks or more)','none',0);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(13,'Livebirths','132','Preterms (less than 37 completed weeks)','none',0);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(14,'Livebirths','133','<i class="text-primary">Very Preterms (less than 34 completed weeks)</i>','Very Preterms (less than 34 completed weeks)',0);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(13,'Birthweight','136','Normal birthweight babies (2.5kg or more)','none',0);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(13,'Birthweight','137','Low birthweight babies (less than 2.5kg)','none',0);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(14,'Birthweight','138','<i class="text-primary">Very low birthweight babies (less than 1.5kg)</i>','Very low birthweight babies (less than 1.5kg)',0);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(14,'Birthweight','139','<i class="text-primary">Extremely low birthweight babies (less than 1.0kg)</i>','Extremely low birthweight babies (less than 1.0kg)',0);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(15,'Neonatal deaths','150','Neonatal deaths','live born babies who died within 28 days',1);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(15,'Neonatal deaths','151','Early Neonatal Deaths (1-7 days)','none',0);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(15,'Neonatal deaths','152','Late Neonatal Deaths (8-28 days)','none',0);
MERGE INTO monitoring_table(gindex,glabel,mindex,mlabel,mdesc,gitem) KEY(mindex) values(16,'Maternal deaths','161','Maternal deaths','women who died in pregnancy or around child birth',1);

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',10,'Not Done');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',88,'Not Stated');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',99,'Not Applicable');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('cstatus_options',0,'Not Started');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('cstatus_options',1,'Started');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('cstatus_options',2,'Completed');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',1,'Birth Asphyxia');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',2,'Birth trauma');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',3,'Preterm birth complications');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',4,'Infections');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',5,'Septicaemia');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',6,'Congenital anomalies');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',7,'Special case');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',8,'Meconium aspiration');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',9,'Respiratory Distress Syndrome (RDS)');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',10,'Extreme prematurity (<1000g)');

-- Datamap Restoration Part 1
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('typefacility_options',1,'National Referral Hospital');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('typefacility_options',2,'Regional Referral Hospital');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('typefacility_options',3,'General  Hospital');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('typefacility_options',4,'HC IV');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('typefacility_options',5,'HC III');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('typefacility_options',6,'HC II');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('typefacility_options',7,'Private Medical Center/Clinic');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('typefacility_options',8,'Unknown');    

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('death_options',1,'Stillbirth');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('death_options',2,'Early Neonatal Death');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('death_options',3,'Maternal Death');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('nationality_options',1,'National');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('nationality_options',2,'Refugee');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('nationality_options',3,'Foreigner');   
    
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('adeath_options',1,'Stillbirth Intrapartum');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('adeath_options',2,'Stillbirth Antepartum');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('adeath_options',3,'Early Neonatal Death');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('sex_options',1,'Male');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('sex_options',2,'Female');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('sex_options',3,'Indeterminate');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('sex_options',4,'Ambiguous genitalia');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('sex_options',77,'Unknown');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('sex_options',88,'Not Stated');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('sex_options',99,'Not Applicable (maternal death)');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',0,'to 11 years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',12,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',13,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',14,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',15,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',16,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',17,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',18,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',19,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',20,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',21,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',22,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',23,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',24,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',25,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',26,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',27,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',28,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',29,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',30,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',31,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',32,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',33,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',34,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',35,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',36,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',37,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',38,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',39,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',40,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',41,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',42,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',43,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',44,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',45,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',46,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',47,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',48,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',49,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',50,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',51,'years and above');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',1,'No Education');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',2,'Non-formal');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',3,'Basic/Primary');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',4,'Secondary');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',5,'Post Secondary/Vocation');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',6,'Tertiary');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('work_options',0,'Unemployed');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('work_options',1,'House wife');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('work_options',2,'Farming');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('work_options',3,'Trading');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('work_options',4,'Artisan (hairdresser, seamstress, etc');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('work_options',5,'Civil/Public service');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('work_options',66,'Other work');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('work_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('marital_options',0,'Never married');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('marital_options',1,'Married');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('marital_options',2,'Living together');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('marital_options',3,'Divorced/Separated');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('marital_options',4,'Widowed');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('marital_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('religion_options',1,'Christian');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('religion_options',2,'Moslem');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('religion_options',3,'Traditionalist/Spiritualist');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('religion_options',4,'Other');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('religion_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ethnic_options',1,'Akan');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ethnic_options',2,'Ga/Dangme');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ethnic_options',3,'Ewe');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ethnic_options',4,'Guan');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ethnic_options',5,'Mole/Dagbani');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ethnic_options',6,'Grussi');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ethnic_options',7,'Gruma');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ethnic_options',8,'Other');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ethnic_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pod_options',1,'At Health Facility');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pod_options',2,'On arrival');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pod_options',3,'In transit');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pod_options',4,'Community death');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pod_options',88,'Not Stated');

-- Datamap Restoration Part 2
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',4,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',45,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',88,'Not Stated');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',99,'Not Applicable');

-- Shortening pweeks_options for BREVITY in this chunk, I will add them properly if needed but the user just saw them.
-- Actually, I'll add the FULL list to be safe.

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',4,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',5,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',6,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',7,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',8,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',9,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',10,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',11,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',12,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',13,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',14,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',15,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',16,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',17,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',18,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',19,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',20,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',21,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',22,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',23,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',24,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',25,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',26,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',27,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',28,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',29,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',30,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',31,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',32,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',33,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',34,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',35,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',36,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',37,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',38,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',39,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',40,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',41,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',42,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',43,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',44,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',45,'Weeks');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',88,'Not Stated');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pweeks_options',99,'Not Applicable');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pdays_options',0,'Days');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pdays_options',1,'Day');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pdays_options',2,'Days');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pdays_options',3,'Days');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pdays_options',4,'Days');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pdays_options',5,'Days');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pdays_options',6,'Days');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pdays_options',88,'Not Stated');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('pdays_options',99,'Not Applicable');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ptype_options',1,'Singleton');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ptype_options',2,'Twins');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ptype_options',3,'Triplets');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ptype_options',66,'Other');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ptype_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('yesnodk_options',0,'No');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('yesnodk_options',1,'Yes');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('yesnodk_options',77,'Unknown');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('yesnodk_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('yesnodkna_options',0,'No');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('yesnodkna_options',1,'Yes');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('yesnodkna_options',77,'Unknown');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('yesnodkna_options',88,'Not Stated');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('yesnodkna_options',99,'Not Applicable');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('posneg_options',0,'Negative(-ve)');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('posneg_options',1,'Positive(+ve)');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('posneg_options',77,'Unknown');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('posneg_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('bldgrp_options',0,'A');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('bldgrp_options',1,'B');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('bldgrp_options',2,'AB');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('bldgrp_options',3,'O');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('bldgrp_options',77,'Unknown');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('bldgrp_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('patient_options',0,'Mother');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('patient_options',1,'Baby');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('patient_options',2,'Mother and Baby');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('patient_options',88,'Not Stated');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('patient_options',99,'Not Applicable');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('source_options',0,'Hospital');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('source_options',1,'Health Center');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('source_options',2,'Private Maternity/Clinic');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('source_options',3,'VHT');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('source_options',4,'TBA');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('source_options',88,'Not Stated');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('source_options',99,'Not Applicable');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('trans_options',0,'On foot');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('trans_options',1,'Tricycle');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('trans_options',2,'Motor bike');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('trans_options',3,'Vehicle (Commercial)');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('trans_options',4,'Vehicle (Private)');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('trans_options',5,'Ambulance');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('trans_options',66,'Other');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('trans_options',88,'Not Stated');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('trans_options',99,'Not Applicable');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('period_options',0,'Dawn (early morning)');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('period_options',1,'Morning');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('period_options',2,'Midday');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('period_options',3,'Afternoon');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('period_options',4,'Evening');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('period_options',5,'Midnight');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('period_options',6,'Night');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('period_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('admission_options',1,'Abortion');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('admission_options',2,'Ectopic Pregnancy');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('admission_options',3,'Antenatal');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('admission_options',4,'Intrapartum');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('admission_options',5,'Postpartum');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('admission_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('admissioncond_options',1,'Stable');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('admissioncond_options',2,'Abnormal vital signs');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('admissioncond_options',3,'Unconscious');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('admissioncond_options',4,'Dead on arrival');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('admissioncond_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('levelconsc_options',1,'Conscious');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('levelconsc_options',2,'Semi-conscious');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('levelconsc_options',3,'Unconscious');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('levelconsc_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mode_options',0,'Spontaneous Vaginal Delivery');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mode_options',1,'Assisted Delivery (Vacuum/forceps)');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mode_options',2,'Elective Caesarean Section');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mode_options',3,'Emergency Caesarean Section');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mode_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('decision_options',0,'Less than 30 mins');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('decision_options',1,'30 mins - 1 hour');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('decision_options',2,'Greater than 1 hour');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('decision_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('startmode_options',1,'Spontaneous');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('startmode_options',2,'Induced');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('startmode_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('provider_options',0,'Specialist');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('provider_options',1,'Resident');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('provider_options',2,'Medical Officer');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('provider_options',3,'Medical/Physician Assistant');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('provider_options',4,'House Officer');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('provider_options',5,'Midwife');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('provider_options',6,'Nurse (Registered General)');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('provider_options',7,'Enrolled/Comm/Public Heath Nurse');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('provider_options',8,'Traditional Birth Attendant (TBA)');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('provider_options',77,'Unknown');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('provider_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('birthloc_options',0,'Health facility');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('birthloc_options',88,'Home');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('birthloc_options',88,'On way to facility');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('birthloc_options',66,'Other');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('birthloc_options',77,'Unknown');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('birthloc_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('liqourvolume_options',0,'Adequate');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('liqourvolume_options',1,'Reduced (Oligohydramnious)');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('liqourvolume_options',2,'No fluid (Anhydramnious)');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('liqourvolume_options',3,'Too much fluid (Polyhydramnious)');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('liqourvolume_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('liqourcolor_options',0,'Clear');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('liqourcolor_options',1,'Meconium+');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('liqourcolor_options',2,'Meconium++');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('liqourcolor_options',3,'Meconium+++');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('liqourcolor_options',4,'Blood stained');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('liqourcolor_options',66,'Other');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('liqourcolor_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('liqourodour_options',0,'Normal');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('liqourodour_options',1,'Foul smell');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('liqourodour_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('babyoutcome_options',0,'Single fresh stillbirth');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('babyoutcome_options',1,'Single macerated stillbirth');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('babyoutcome_options',77,'Unknown');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('babyoutcome_options',88,'Not Stated');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('babyoutcome_options',99,'Not Applicable');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('motheroutcome_options',0,'Alive');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('motheroutcome_options',1,'Dead');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('motheroutcome_options',77,'Unknown');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('motheroutcome_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('lastheard_options',0,'Antepartum');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('lastheard_options',1,'Intrapartum (first stage)');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('lastheard_options',2,'Intrapartum (second stage)');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('lastheard_options',77,'Unknown');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('lastheard_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('resctdone_options',0,'Midwife');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('resctdone_options',1,'Nurse');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('resctdone_options',2,'Doctor');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('resctdone_options',3,'Anesthetist');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('resctdone_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('resctwarmth_options',0,'Drying');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('resctwarmth_options',1,'Radiant warmer');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('resctwarmth_options',2,'SKin to skin');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('resctwarmth_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',0,'0');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',1,'1');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',2,'2');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',3,'3');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',4,'4');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',5,'5');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',6,'6');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',7,'7');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',8,'8');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',9,'9');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',88,'Not Stated');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',99,'Not Applicable');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('cstatus_options',0,'Not Started');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('cstatus_options',1,'Started');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('cstatus_options',2,'Completed');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',1,'Birth Asphyxia');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',2,'Birth trauma');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',3,'Preterm birth complications');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',4,'Infections');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',5,'Septicaemia');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',6,'Congenital anomalies');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',7,'Special case');	

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('dthplace_options',1,'In transit');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('dthplace_options',2,'On arrival');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('dthplace_options',3,'Community death');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('dthplace_options',4,'Other');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('dthplace_options',5,'Before arrival');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('dthplace_options',6,'At H/F before admission');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('dthplace_options',7,'At H/F after admission');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('dthplace_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('autopsyby_options',1,'Pathologist');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('autopsyby_options',2,'Medical Officer');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('autopsyby_options',3,'Other');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('icd10mm_options',1,'Pregnancy with abortive complications');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('icd10mm_options',2,'Hypertensive disorder of pregnancy');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('icd10mm_options',3,'Obstetric haemorrhage');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('icd10mm_options',4,'Pregnancy related infection');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('icd10mm_options',5,'Other obstetric complication');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('icd10mm_options',6,'Unanticipated complication of management');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('icd10mm_options',7,'Non obstetric complication');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('icd10mm_options',8,'Unknown');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('icd10mm_options',9,'Coincidental/Accidental');

-- Missing Options from Master
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('posneg_options',0,'Negative(-ve)');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('posneg_options',1,'Positive(+ve)');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('posneg_options',77,'Unknown');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('posneg_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('bldgrp_options',0,'A');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('bldgrp_options',1,'B');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('bldgrp_options',2,'AB');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('bldgrp_options',3,'O');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('bldgrp_options',77,'Unknown');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('bldgrp_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('patient_options',0,'Mother');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('patient_options',1,'Baby');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('patient_options',2,'Mother and Baby');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('patient_options',88,'Not Stated');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('patient_options',99,'Not Applicable');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('source_options',0,'Hospital');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('source_options',1,'Health Center');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('source_options',2,'Private Maternity/Clinic');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('source_options',3,'VHT');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('source_options',4,'TBA');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('source_options',88,'Not Stated');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('source_options',99,'Not Applicable');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',1,'No Education');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',2,'Non-formal');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',3,'Basic/Primary');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',4,'Secondary');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',5,'Post Secondary/Vocation');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',6,'Tertiary');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('work_options',0,'Unemployed');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('work_options',1,'House wife');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('work_options',2,'Farming');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('work_options',3,'Trading');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('work_options',4,'Artisan (hairdresser, seamstress, etc)');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('work_options',5,'Civil/Public service');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('work_options',66,'Other work');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('work_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('marital_options',0,'Never married');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('marital_options',1,'Married');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('marital_options',2,'Living together');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('marital_options',3,'Divorced/Separated');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('marital_options',4,'Widowed');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('marital_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('religion_options',1,'Christian');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('religion_options',2,'Moslem');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('religion_options',3,'Traditionalist/Spiritualist');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('religion_options',4,'Other');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('religion_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ethnic_options',1,'Akan');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ethnic_options',2,'Ga/Dangme');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ethnic_options',3,'Ewe');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ethnic_options',4,'Guan');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ethnic_options',5,'Mole/Dagbani');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ethnic_options',6,'Grussi');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ethnic_options',7,'Gruma');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ethnic_options',8,'Other');	
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('ethnic_options',88,'Not Stated');

MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',0,'to 11 years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',12,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',13,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',14,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',15,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',16,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',17,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',18,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',19,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',20,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',21,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',22,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',23,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',24,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',25,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',26,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',27,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',28,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',29,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',30,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',31,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',32,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',33,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',34,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',35,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',36,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',37,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',38,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',39,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',40,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',41,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',42,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',43,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',44,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',45,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',46,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',47,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',48,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',49,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',50,'years');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',51,'years and above');
MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mage_options',88,'Not Stated');

-- User and Role Initialization
MERGE INTO user_table(username, enabled, password, usercontact, useremail, userfullname, alerted) KEY(username) 
VALUES (
    'webadmin'
    , true
    , '$2a$10$SLlNbnvkIqatweZxewyZUeF6yrGexjppQpJgntGXCxWMQCaT3ORdi'
    , '233246926396'
    , 'webadmin@kintampo-hrc.org'
    , 'ROOT ADMINISTRATOR',false
);

MERGE INTO group_table KEY(group_role, group_desc) values('ROLE_ENTRY', 'Enter cases into the system');
MERGE INTO group_table KEY(group_role, group_desc) values('ROLE_AUDIT', 'Review and recommend actions on submitted cases');
MERGE INTO group_table KEY(group_role, group_desc) values('ROLE_TASKS', 'Monitor and change action status');
MERGE INTO group_table KEY(group_role, group_desc) values('ROLE_SETUP', 'Manage users, facility code from the controls section');
MERGE INTO group_table KEY(group_role, group_desc) values('ROLE_VIEWS', 'View analysis and reports');
MERGE INTO group_table KEY(group_role, group_desc) values('ROLE_NATIONAL', 'National level viewing');
MERGE INTO group_table KEY(group_role, group_desc) values('ROLE_REGIONAL', 'Regional level viewing');
MERGE INTO group_table KEY(group_role, group_desc) values('ROLE_DISTRICT', 'District level viewing');

DELETE FROM user_group WHERE username='webadmin';
MERGE INTO user_group KEY(username, group_role) values('webadmin', 'ROLE_ENTRY');
MERGE INTO user_group KEY(username, group_role) values('webadmin', 'ROLE_AUDIT');
MERGE INTO user_group KEY(username, group_role) values('webadmin', 'ROLE_TASKS');
MERGE INTO user_group KEY(username, group_role) values('webadmin', 'ROLE_VIEWS');
MERGE INTO user_group KEY(username, group_role) values('webadmin', 'ROLE_SETUP');
MERGE INTO user_group KEY(username, group_role) values('webadmin', 'ROLE_NATIONAL');
MERGE INTO user_group KEY(username, group_role) values('webadmin', 'ROLE_REGIONAL');
MERGE INTO user_group KEY(username, group_role) values('webadmin', 'ROLE_DISTRICT');
