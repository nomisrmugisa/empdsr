 
  
    MERGE INTO group_table KEY(group_role, group_desc) values('ROLE_ENTRY', 'Enter cases into the system');
    MERGE INTO group_table KEY(group_role, group_desc) values('ROLE_AUDIT', 'Review and recommend actions on submitted cases');
    MERGE INTO group_table KEY(group_role, group_desc) values('ROLE_TASKS', 'Monitor and change action status');
	MERGE INTO group_table KEY(group_role, group_desc) values('ROLE_SETUP', 'Manage users, facility code from the controls section');
	MERGE INTO group_table KEY(group_role, group_desc) values('ROLE_VIEWS', 'View analysis and reports');


    MERGE INTO user_table(username, enabled, password, usercontact, useremail, userfullname) KEY(username) 
    VALUES (
	    'webadmin'
	    , true
	    , '$2a$10$SLlNbnvkIqatweZxewyZUeF6yrGexjppQpJgntGXCxWMQCaT3ORdi'
	    , '233246926396'
	    , 'webadmin@kintampo-hrc.org'
	    , 'ROOT ADMINISTRATOR'
    );



	DELETE FROM user_group WHERE username='webadmin';
    MERGE INTO user_group KEY(username, group_role) values('webadmin', 'ROLE_ENTRY');
    MERGE INTO user_group KEY(username, group_role) values('webadmin', 'ROLE_AUDIT');
    MERGE INTO user_group KEY(username, group_role) values('webadmin', 'ROLE_TASKS');
    MERGE INTO user_group KEY(username, group_role) values('webadmin', 'ROLE_VIEWS');
    MERGE INTO user_group KEY(username, group_role) values('webadmin', 'ROLE_SETUP');


    MERGE INTO country_table KEY(country_uuid) values('1','Ghana');


    MERGE INTO risk_table KEY(risk_name) values('Cervical / uterine surgery','none');
    MERGE INTO risk_table KEY(risk_name) values('Last delivery > 10 years','none');
    MERGE INTO risk_table KEY(risk_name) values('Last delivery < 2 years','none');

    MERGE INTO complication_table KEY(complication_name) values('Complex delivery','none');
    MERGE INTO complication_table KEY(complication_name) values('More complex','none');
    MERGE INTO complication_table KEY(complication_name) values('Very complex','none');

    MERGE INTO abnormality_table KEY(abnormal_name) values('Abnormal birth','none');
    MERGE INTO abnormality_table KEY(abnormal_name) values('More abnormal','none');
    MERGE INTO abnormality_table KEY(abnormal_name) values('Very abnormal','none');

    MERGE INTO cordfault_table KEY(cordfault_name) values('Faulty cord','none');
    MERGE INTO cordfault_table KEY(cordfault_name) values('More faulty','none');
    MERGE INTO cordfault_table KEY(cordfault_name) values('Very faulty','none');

    MERGE INTO placentacheck_table KEY(placentacheck_name) values('Placenta check','none');
    MERGE INTO placentacheck_table KEY(placentacheck_name) values('More placenta','none');
    MERGE INTO placentacheck_table KEY(placentacheck_name) values('Very placenta','none');

	MERGE INTO resuscitation_table KEY(resuscitation_name) values('Suction and stimulation','Suction and stimulation');
	MERGE INTO resuscitation_table KEY(resuscitation_name) values('IPPV bag and mask','IPPV bag and mask');
	MERGE INTO resuscitation_table KEY(resuscitation_name) values('Oxygen','Oxygen');
	MERGE INTO resuscitation_table KEY(resuscitation_name) values('Chest compression','Chest compression');

	MERGE INTO diagnoses_table KEY(diagnosis_name) values('Prematurity','Prematurity');
	MERGE INTO diagnoses_table KEY(diagnosis_name) values('Neonatal Jaundice','Neonatal Jaundice');
	MERGE INTO diagnoses_table KEY(diagnosis_name) values('Difficulty breathing','Difficulty breathing');
	MERGE INTO diagnoses_table KEY(diagnosis_name) values('Convulsions/seizures','Convulsions/seizures');
	MERGE INTO diagnoses_table KEY(diagnosis_name) values('Hypothermia','Hypothermia');
	MERGE INTO diagnoses_table KEY(diagnosis_name) values('Bulging fontanelle','Bulging fontanelle');
	MERGE INTO diagnoses_table KEY(diagnosis_name) values('Anaemia','Anaemia');
	MERGE INTO diagnoses_table KEY(diagnosis_name) values('Hypoglycaemia','Hypoglycaemia');
	MERGE INTO diagnoses_table KEY(diagnosis_name) values('Poor feeding','Poor feeding');
	MERGE INTO diagnoses_table KEY(diagnosis_name) values('Infection','Infection');
	

    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('death_options',1,'Stillbirth');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('death_options',2,'Early Neonatal Death');
    
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('adeath_options',1,'Stillbirth Intrapartum');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('adeath_options',2,'Stillbirth Antepartum');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('adeath_options',3,'Early Neonatal Death');

    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('sex_options',1,'Male');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('sex_options',2,'Female');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('sex_options',3,'Indeterminate');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('sex_options',77,'Unknown');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('sex_options',88,'Not Stated');
    
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',1,'No Education');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',2,'Non-formal');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',3,'Basic/Primary');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',4,'Secondary');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',5,'Post Secondary/Vocation');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',6,'Tertiary');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('edu_options',88,'Not Stated');
    
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

    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('hiv_options',0,'Negative(-ve)');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('hiv_options',1,'Positive(+ve)');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('hiv_options',77,'Unknown');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('hiv_options',88,'Not Stated');
    
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('patient_options',0,'Mother');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('patient_options',1,'Baby');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('patient_options',2,'Mother and Baby');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('patient_options',88,'Not Stated');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('patient_options',99,'Not Applicable');
    
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('source_options',0,'Health Facility');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('source_options',1,'Home');
    MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('source_options',66,'Other');
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
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('period_options',88,'Not Stated');
  
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mode_options',0,'Spontaneous Vaginal Delivery');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mode_options',1,'Assisted Delivery (Vacuum/forceps)');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mode_options',2,'Caesarean Section');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('mode_options',88,'Not Stated');
  
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('startmode_options',0,'No labour');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('startmode_options',1,'Spontaneous');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('startmode_options',2,'Induced');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('startmode_options',88,'Not Stated');
  
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('provider_options',0,'Specialist');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('provider_options',1,'Resident');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('provider_options',2,'Medical Officer');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('provider_options',3,'Midwife');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('provider_options',4,'Nurse');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('provider_options',5,'Traditional Birth Attendant (TBA)');
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
  
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('babyoutcome_options',0,'Fresh stillbirth');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('babyoutcome_options',1,'Macerated stillbirth');
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
	
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',0,'APGAR');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',1,'APGAR');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',2,'APGAR');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',3,'APGAR');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',4,'APGAR');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',5,'APGAR');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',6,'APGAR');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',7,'APGAR');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',8,'APGAR');	
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('apgar_options',9,'APGAR');
	
	
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('cstatus_options',0,'Not Started');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('cstatus_options',1,'Started');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('cstatus_options',2,'Completed');
	
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',1,'Asphyxia');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',2,'Preterm birth complications');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',3,'Infections');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',4,'Congenital anomalies');
	MERGE INTO datamap(map_feature,map_value,map_label) KEY(map_feature,map_value) values('neocod_options',5,'Special case');
	