package org.pdsr;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.pdsr.enums.GroupRoles;
import org.pdsr.master.model.abnormality_table;
import org.pdsr.master.model.cfactor_table;
import org.pdsr.master.model.complication_table;
import org.pdsr.master.model.cordfault_table;
import org.pdsr.master.model.datamap;
import org.pdsr.master.model.diagnoses_table;
import org.pdsr.master.model.facility_table;
import org.pdsr.master.model.group_table;
import org.pdsr.master.model.mcgroup_table;
import org.pdsr.master.model.mcondition_table;
import org.pdsr.master.model.monitoring_table;
import org.pdsr.master.model.placentacheck_table;
import org.pdsr.master.model.resuscitation_table;
import org.pdsr.master.model.risk_table;
import org.pdsr.master.model.user_table;
import org.pdsr.master.repo.AbnormalityTableRepository;
import org.pdsr.master.repo.CFactorsRepository;
import org.pdsr.master.repo.ComplicationTableRepository;
import org.pdsr.master.repo.CordfaultTableRepository;
import org.pdsr.master.repo.DatamapRepository;
import org.pdsr.master.repo.DiagnosesTableRepository;
import org.pdsr.master.repo.FacilityTableRepository;
import org.pdsr.master.repo.GroupTableRepository;
import org.pdsr.master.repo.McgroupRepository;
import org.pdsr.master.repo.MconditionsRepository;
import org.pdsr.master.repo.MonitoringTableRepository;
import org.pdsr.master.repo.PlacentacheckTableRepository;
import org.pdsr.master.repo.ResuscitationTableRepository;
import org.pdsr.master.repo.RiskTableRepository;
import org.pdsr.master.repo.UserTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PreDataConfig {

	@Autowired
	private BCryptPasswordEncoder bCrypt;

	@Autowired
	private GroupTableRepository groupRepo;

	@Autowired
	private UserTableRepository userRepo;

	@Autowired
	private FacilityTableRepository countryRepo;

	@Autowired
	private ResuscitationTableRepository resRepo;

	@Autowired
	private RiskTableRepository riskRepo;

	@Autowired
	private ComplicationTableRepository compRepo;

	@Autowired
	private AbnormalityTableRepository abnorRepo;

	@Autowired
	private CordfaultTableRepository cordRepo;

	@Autowired
	private PlacentacheckTableRepository placentaRepo;

	@Autowired
	private DiagnosesTableRepository diagRepo;

	@Autowired
	private McgroupRepository mcgrpRepo;

	@Autowired
	private MconditionsRepository mcondRepo;

	@Autowired
	private CFactorsRepository cfactorRepo;

	@Autowired
	private MonitoringTableRepository monRepo;

	@Autowired
	private DatamapRepository dmapRepo;

	@PostConstruct
	public void preloadingDatabase() {

		Optional<user_table> webadminuser = userRepo.findById(CONSTANTS.WEBADMIN_ID);

		if (!webadminuser.isPresent()) {

			List<group_table> groupList = new ArrayList<group_table>();
			groupList.add(new group_table(GroupRoles.ROLE_ENTRY.getId(), GroupRoles.ROLE_ENTRY.getDescription()));
			groupList.add(new group_table(GroupRoles.ROLE_AUDIT.getId(), GroupRoles.ROLE_AUDIT.getDescription()));
			groupList.add(new group_table(GroupRoles.ROLE_TASKS.getId(), GroupRoles.ROLE_TASKS.getDescription()));
			groupList.add(new group_table(GroupRoles.ROLE_SETUP.getId(), GroupRoles.ROLE_SETUP.getDescription()));
			groupList.add(new group_table(GroupRoles.ROLE_VIEWS.getId(), GroupRoles.ROLE_VIEWS.getDescription()));
			groupList.add(new group_table(GroupRoles.ROLE_NATIONAL.getId(), GroupRoles.ROLE_NATIONAL.getDescription()));
			groupList.add(new group_table(GroupRoles.ROLE_REGIONAL.getId(), GroupRoles.ROLE_REGIONAL.getDescription()));
			groupList.add(new group_table(GroupRoles.ROLE_DISTRICT.getId(), GroupRoles.ROLE_DISTRICT.getDescription()));
			groupRepo.saveAll(groupList);

			user_table webadmin = new user_table();
			webadmin.setUsername(CONSTANTS.WEBADMIN_ID);
			webadmin.setUseremail("webadmin@olincgroup.com");
			webadmin.setUserfullname("Root Administrator");
			webadmin.setUsercontact("+000000000000");
			webadmin.setAlerted(true);
			webadmin.setEnabled(true);
			webadmin.setPassword(bCrypt.encode("admin"));
			webadmin.setGroups(groupList);
			userRepo.save(webadmin);

		}

		Optional<facility_table> countryConfig = countryRepo.findById(CONSTANTS.LICENSE_ID);

		if (!countryConfig.isPresent()) {

			facility_table country = new facility_table();
			country.setFacility_uuid(CONSTANTS.LICENSE_ID);
			country.setFacility_code(CONSTANTS.LICENSE_ID);
			country.setFacility_name(CONSTANTS.LICENSE_COUNTRY);
			country.setFacility_type(0);
			country.setParent(null);
			countryRepo.save(country);

		}

		{
			List<resuscitation_table> res = new ArrayList<>();
			res.add(new resuscitation_table("Suction and stimulation", "Suction and stimulation"));
			res.add(new resuscitation_table("IPPV bag and mask", "IPPV bag and mask"));
			res.add(new resuscitation_table("Oxygen", "Oxygen"));
			res.add(new resuscitation_table("Chest compression", "Chest compression"));
			res.add(new resuscitation_table("Advanced resuscitation with adrenaline",
					"Advanced resuscitation with adrenaline"));
			res.add(new resuscitation_table("Advanced resuscitation with adrenaline",
					"Advanced resuscitation with adrenaline"));
			res.add(new resuscitation_table("Intubation", "Intubation"));
			res.add(new resuscitation_table("Drying", "Drying"));
			res.add(new resuscitation_table("Radiant warmer", "Radiant warmer"));
			res.add(new resuscitation_table("Skin to skin", "Skin to skin"));
			res.add(new resuscitation_table("Antibiotics", "Antibiotics"));
			resRepo.saveAll(res);
		}

		{
			List<risk_table> res = new ArrayList<>();
			res.add(new risk_table("Cervical / uterine surgery", "none"));
			res.add(new risk_table("Last delivery > 10 years", "none"));
			res.add(new risk_table("Last delivery < 2 years", "none"));
			res.add(new risk_table("Pre-eclampsia/eclampsia", "none"));
			res.add(new risk_table("Anemia", "none"));
			res.add(new risk_table("Antepartum hemorrhage", "none"));
			res.add(new risk_table("Malaria", "none"));
			res.add(new risk_table("UTI/pyelonephritis", "none"));
			res.add(new risk_table("Unintended pregnancy", "none"));
			res.add(new risk_table("Abnormal lie/presentation", "none"));
			res.add(new risk_table("previous cesarean section", "none"));
			res.add(new risk_table("history of postpartum hemorrhage", "none"));
			riskRepo.saveAll(res);
		}

		{
			List<complication_table> res = new ArrayList<>();
			res.add(new complication_table("Cephalo-pelvic disproportion (CPD)", "none"));
			res.add(new complication_table("Premature rupture of membranes", "none"));
			res.add(new complication_table("Infection (including UTI/Chorioamnionitis)", "none"));
			res.add(new complication_table("Hypertension (Pre-eclampsia)", "none"));
			res.add(new complication_table("Gestational diabetes", "none"));
			res.add(new complication_table("Uterine rupture", "none"));
			res.add(new complication_table("Poor progress of labour", "none"));
			res.add(new complication_table("Delayed 2nd stage", "none"));
			res.add(new complication_table("Fetal distress", "none"));
			res.add(new complication_table("Transverse/oblique lie", "none"));
			res.add(new complication_table("Breech, hand,foot, or other compound presentations", "none"));
			res.add(new complication_table("Obstetric haemorrhage (antepartum)", "none"));
			res.add(new complication_table("Latrogenic complications", "none"));
			res.add(new complication_table("Latrogenic complications", "none"));
			res.add(new complication_table("Severe anaemia", "none"));
			compRepo.saveAll(res);
		}

		{
			List<abnormality_table> res = new ArrayList<>();
			res.add(new abnormality_table("Microcephaly", "none"));
			res.add(new abnormality_table("Exomphalos major", "none"));
			res.add(new abnormality_table("Heart Defects", "none"));
			res.add(new abnormality_table("Tetralogy of Fallot", "none"));
			res.add(new abnormality_table("Neural Tube Defects (Spina Bifida)", "none"));
			res.add(new abnormality_table("Hemangioma", "none"));
			res.add(new abnormality_table("Osteogenesis Imperfecta", "none"));
			res.add(new abnormality_table("Micrognathia", "none"));
			res.add(new abnormality_table("Dysmorphic features", "none"));
			res.add(new abnormality_table("Dyspigmentation", "none"));
			abnorRepo.saveAll(res);
		}

		{
			List<cordfault_table> res = new ArrayList<>();
			res.add(new cordfault_table("Kinks and knots", "none"));
			res.add(new cordfault_table("Degenerated cord", "none"));
			cordRepo.saveAll(res);
		}

		{
			List<placentacheck_table> res = new ArrayList<>();
			res.add(new placentacheck_table("Small size", "none"));
			res.add(new placentacheck_table("Pale placenta", "none"));
			res.add(new placentacheck_table("Foul-smelling", "none"));
			res.add(new placentacheck_table("Degenerated", "none"));
			res.add(new placentacheck_table("Loss of architecture", "none"));
			res.add(new placentacheck_table("Calcifications", "none"));
			res.add(new placentacheck_table("Accreta", "none"));
			res.add(new placentacheck_table("Praevia", "none"));
			res.add(new placentacheck_table("Abruptio", "none"));
			res.add(new placentacheck_table("Percreta", "none"));
			placentaRepo.saveAll(res);
		}

		{
			List<diagnoses_table> res = new ArrayList<>();
			res.add(new diagnoses_table("Prematurity", "Prematurity"));
			res.add(new diagnoses_table("Neonatal Jaundice", "Neonatal Jaundice"));
			res.add(new diagnoses_table("Difficulty breathing", "Difficulty breathing"));
			res.add(new diagnoses_table("Convulsions/seizures", "Convulsions/seizures"));
			res.add(new diagnoses_table("Hypothermia", "Hypothermia"));
			res.add(new diagnoses_table("Bulging fontanelle", "Bulging fontanelle"));
			res.add(new diagnoses_table("Anaemia", "Anaemia"));
			res.add(new diagnoses_table("Hypoglycaemia", "Hypoglycaemia"));
			res.add(new diagnoses_table("Poor feeding", "Poor feeding"));
			res.add(new diagnoses_table("Infection", "Infection"));
			res.add(new diagnoses_table("Congenital Infection", "Congenital Infection"));
			res.add(new diagnoses_table("Congenital Syphilis", "Congenital Syphilis"));
			res.add(new diagnoses_table("Septicaemia", "Septicaemia"));
			res.add(new diagnoses_table("Respiratory Distress Syndrome (RDS)", "Respiratory Distress Syndrome (RDS)"));
			res.add(new diagnoses_table("Cardiac Abnormalities", "Cardiac Abnormalities"));
			res.add(new diagnoses_table("Asphyxia (HIE)", "Asphyxia (HIE)"));
			res.add(new diagnoses_table("Big baby (Macrosomia)", "Big baby (Macrosomia)"));
			diagRepo.saveAll(res);

		}

		{
			List<mcgroup_table> res = new ArrayList<>();
			res.add(new mcgroup_table("M1", "Complications of placenta, cord and membranes"));
			res.add(new mcgroup_table("M2", "Maternal complications of pregnancy"));
			res.add(new mcgroup_table("M3", "Other complications of labour and delivery"));
			res.add(new mcgroup_table("M4", "Maternal medical and surgical conditions"));
			mcgrpRepo.saveAll(res);

		}

		{
			List<mcondition_table> res = new ArrayList<>();
			res.add(new mcondition_table("M1.1", "M1", "placenta praevia"));
			res.add(new mcondition_table("M1.2", "M1", "other forms of placental separation and haemorrhage"));
			res.add(new mcondition_table("M1.3", "M1", "placental dysfunction, infarction, insufficiency"));
			res.add(new mcondition_table("M1.4", "M1", "fetal-placental transfusion syndromes"));
			res.add(new mcondition_table("M1.5", "M1", "prolapsed cord, other compression of umbilical cord"));
			res.add(new mcondition_table("M1.6", "M1", "chorioamnionitis"));
			res.add(new mcondition_table("M1.7", "M1", "other complications of membranes"));

			res.add(new mcondition_table("M2.1", "M2", "incompetent cervix"));
			res.add(new mcondition_table("M2.2", "M2", "preterm rupture of membranes"));
			res.add(new mcondition_table("M2.3", "M2", "oligohydramnios/polyhydramnios"));
			res.add(new mcondition_table("M2.4", "M2", "ectopic pregnancy"));
			res.add(new mcondition_table("M2.5", "M2", "multiple pregnancy"));
			res.add(new mcondition_table("M2.6", "M2", "maternal death"));
			res.add(new mcondition_table("M2.7", "M2", "malpresentation before labour"));
			res.add(new mcondition_table("M2.8", "M2", "other complications of pregnancy"));

			res.add(new mcondition_table("M3.1", "M3", "breech delivery and extraction"));
			res.add(new mcondition_table("M3.2", "M3",
					"other malpresentation, malposition and disproportion during labour and delivery"));
			res.add(new mcondition_table("M3.3", "M3", "forceps delivery/vacuum extraction"));
			res.add(new mcondition_table("M3.4", "M3", "caesarean delivery"));
			res.add(new mcondition_table("M3.5", "M3", "precipitate delivery"));
			res.add(new mcondition_table("M3.6", "M3", "preterm labour and delivery"));
			res.add(new mcondition_table("M3.7", "M3", "other complications of labour and delivery"));

			res.add(new mcondition_table("M4.1", "M4", "pre-eclampsia, eclampsia"));
			res.add(new mcondition_table("M4.2", "M4", "gestational hypertension"));
			res.add(new mcondition_table("M4.3", "M4", "other hypertensive disorders"));
			res.add(new mcondition_table("M4.4", "M4", "renal and urinary tract diseases"));
			res.add(new mcondition_table("M4.5", "M4", "infectious and parasitic disease"));
			res.add(new mcondition_table("M4.6", "M4", "circulatory and respiratory disease"));
			res.add(new mcondition_table("M4.7", "M4", "nutritional disorders"));
			res.add(new mcondition_table("M4.8", "M4", "injury"));
			res.add(new mcondition_table("M4.9", "M4", "surgical procedure"));
			res.add(new mcondition_table("M4.10", "M4", "other medical procedures"));
			res.add(new mcondition_table("M4.11", "M4", "maternal diabetes, including gestational diabetes"));
			res.add(new mcondition_table("M4.12", "M4", "maternal anaesthesia and analgesia"));
			res.add(new mcondition_table("M4.13", "M4", "maternal medication"));
			res.add(new mcondition_table("M4.14", "M4", "tobacco/alcohol/drugs of addiction"));
			res.add(new mcondition_table("M4.15", "M4", "nutritional chemical substances"));
			res.add(new mcondition_table("M4.16", "M4", "environmental chemical substances"));
			res.add(new mcondition_table("M4.17", "M4", "unspecified maternal condition"));
			mcondRepo.saveAll(res);

		}

		{
			List<cfactor_table> res = new ArrayList<>();
			res.add(new cfactor_table(101, 100, "Never initiated antenatal care"));
			res.add(new cfactor_table(102, 100, "Booked late in pregnancy"));
			res.add(new cfactor_table(103, 100, "Inadequate visits to antenatal clinic (<4 visits by  36/52)"));
			res.add(new cfactor_table(104, 100, "Delay in seeking care when complications set in"));
			res.add(new cfactor_table(105, 100, "Delay in seeking medical attention during labour"));
			res.add(new cfactor_table(106, 100, "Attempted termination of pregnancy"));
			res.add(new cfactor_table(107, 100, "Failed to return on prescribed date"));
			res.add(new cfactor_table(108, 100, "Declined admission/treatment for personal/social reasons"));
			res.add(new cfactor_table(109, 100, "Partner/family declined admission/treatment"));
			res.add(new cfactor_table(110, 100, "Assault"));
			res.add(new cfactor_table(111, 100, "Substance abuse (alcohol, smoking etc.)"));
			res.add(new cfactor_table(112, 100, "Delay in seeking help when baby was ill"));
			res.add(new cfactor_table(113, 100, "Infanticide"));
			res.add(new cfactor_table(114, 100, "Abandoned baby"));
			res.add(new cfactor_table(115, 100, "Use of herbal medicine"));
			res.add(new cfactor_table(116, 100, "Financial constraints"));
			res.add(new cfactor_table(117, 100, "No knowledge of danger symptoms"));
			res.add(new cfactor_table(118, 100, "ANC routine labs not done (Hb, Syphilis, Blood Group etc.)"));

			res.add(new cfactor_table(201, 200, "Lack of transport - Home to facility"));
			res.add(new cfactor_table(202, 200, "Lack of transport between facilities"));
			res.add(new cfactor_table(203, 200, "Patient transfer not communicated to receiving Facility"));
			res.add(new cfactor_table(204, 200, "Inappropriate care during transport"));
			res.add(new cfactor_table(301, 300,
					"ANC routine labs not done due to shortages (Hb, Syphilis, Blood Grp etc.)"));
			res.add(new cfactor_table(302, 300,
					"Inadequate facilities / resuscitation equipment in neonatal unit / Nursery"));
			res.add(new cfactor_table(303, 300, "Inadequate theatre facilities"));
			res.add(new cfactor_table(304, 300, "Insufficient blood / blood products available"));
			res.add(new cfactor_table(305, 300,
					"Personnel insufficiently trained or too junior to manage the patient"));
			res.add(new cfactor_table(306, 300, "Insufficient nurses on duty to manage the patient adequately"));
			res.add(new cfactor_table(307, 300, "Insufficient doctors to manage the patient"));
			res.add(new cfactor_table(308, 300, "Anaesthetic delay"));
			res.add(new cfactor_table(309, 300, "Delay in securing bed space at receiving facility"));
			res.add(new cfactor_table(310, 300, "Health worker industrial strike"));

			res.add(new cfactor_table(401, 400, "Poor estimation of foetal size by medical personnel"));
			res.add(new cfactor_table(402, 400,
					"Inappropriate management of complications when they set in during antepartum period"));
			res.add(new cfactor_table(403, 400, "No or inappropriate intervention to maternal hypertension"));
			res.add(new cfactor_table(404, 400, "Abnormal presentation not diagnosed until late in labour"));
			res.add(new cfactor_table(405, 400, "Multiple pregnancy not diagnosed during ANC or intrapartum"));
			res.add(new cfactor_table(406, 400, "Partogram not used to monitor labour"));
			res.add(new cfactor_table(407, 400, "Partogram used to monitor labour, but not interpreted correctly"));
			res.add(new cfactor_table(408, 400, "Poor progress in labour, not picked up early"));
			res.add(new cfactor_table(409, 400,
					"Foetal distress not detected during ANC or intrapartum, foetus not monitored"));
			res.add(new cfactor_table(410, 400,
					"Foetal distress not detected during ANC or intrapartum, foetus monitored"));
			res.add(new cfactor_table(411, 400, "Antenatal steroids not given"));
			res.add(new cfactor_table(412, 400, "Incorrect management of complications"));
			res.add(new cfactor_table(413, 400, "Incorrect or missed diagnosis"));
			res.add(new cfactor_table(414, 400,
					"Physical examination of the patient at the clinic/hospital incomplete"));
			res.add(new cfactor_table(415, 400, "Management of the second stage prolonged with no intervention"));
			res.add(new cfactor_table(416, 400,
					"Management of the second stage prolonged with inappropriate intervention(s)"));
			res.add(new cfactor_table(417, 400, "Delay in medical personnel calling for expert assistance"));
			res.add(new cfactor_table(418, 400, "Delay in referring patient for secondary/tertiary care"));
			res.add(new cfactor_table(419, 400, "Inappropriate response to apparent post-term pregnancy"));
			res.add(new cfactor_table(420, 400, "Neonatal care: inadequate monitoring"));
			res.add(new cfactor_table(421, 400, "Neonatal resuscitation inadequate"));
			res.add(new cfactor_table(422, 400, "Neonatal care: management plan inadequate"));
			res.add(new cfactor_table(423, 400, "Baby sent home inappropriately when not well"));
			res.add(new cfactor_table(424, 400, "Doctor did not respond to call or delayed in responding"));
			res.add(new cfactor_table(425, 400, "Nosocomial infection"));
			res.add(new cfactor_table(426, 400, "Baby managed incorrectly at Hospital/Clinic"));
			res.add(new cfactor_table(427, 400, "Anaesthesia complications during operative delivery"));
			res.add(new cfactor_table(501, 500, "Insufficient notes"));
			res.add(new cfactor_table(502, 500, "Patient notes or mothers ANC card missing"));
			cfactorRepo.saveAll(res);

		}

		{
			List<monitoring_table> res = new ArrayList<>();

			res.add(new monitoring_table(10, "Total Deliveries", 100, "Total deliveries", "mothers who gave birth",
					true));
			res.add(new monitoring_table(10, "Total Deliveries", 101, "SVDs/NVDs", "none", false));
			res.add(new monitoring_table(10, "Total Deliveries", 102, "Assisted (forceps, vacuum, etc)", "none",
					false));
			res.add(new monitoring_table(10, "Total Deliveries", 103, "Caesarean Sections", "none", false));

			res.add(new monitoring_table(11, "Total Births", 110, "Total births",
					"babies born - livebirths and stillbirths", true));
			res.add(new monitoring_table(11, "Total Births", 111, "Singletons", "none", false));
			res.add(new monitoring_table(11, "Total Births", 112, "Multiple", "none", false));

			res.add(new monitoring_table(12, "Stillbirths", 120, "Total stillbirths",
					"Total babies born dead after 28 weeks gestation", true));
			res.add(new monitoring_table(12, "Stillbirths", 121,
					"Antepartum or Macerated Stillbirths  (died  before labour started)", "none", false));
			res.add(new monitoring_table(12, "Stillbirths", 122, "Intrapartum or Fresh Stillbirth (died during labour)",
					"none", false));

			res.add(new monitoring_table(13, "Livebirths", 130, "Total livebirths", "Total babies alive at birth",
					true));
			res.add(new monitoring_table(13, "Livebirths", 131, "Term births (37 completed weeks or more)", "none",
					false));
			res.add(new monitoring_table(13, "Livebirths", 132, "Preterms (less than 37 completed weeks)", "none",
					false));
			res.add(new monitoring_table(14, "Livebirths", 133,
					"<i class=\"text-primary\">Very Preterms (less than 34 completed weeks)</i>",
					"Very Preterms (less than 34 completed weeks)", false));

			res.add(new monitoring_table(13, "Birthweight", 136, "Normal birthweight babies (2.5kg or more)", "none",
					false));
			res.add(new monitoring_table(13, "Birthweight", 137, "Low birthweight babies (less than 2.5kg)", "none",
					false));
			res.add(new monitoring_table(14, "Birthweight", 138,
					"<i class=\"text-primary\">Very low birthweight babies (less than 1.5kg)</i>",
					"Very low birthweight babies (less than 1.5kg)", false));
			res.add(new monitoring_table(14, "Birthweight", 139,
					"<i class=\"text-primary\">Extremely low birthweight babies (less than 1.0kg)</i>",
					"Extremely low birthweight babies (less than 1.0kg)", false));

			res.add(new monitoring_table(15, "Neonatal deaths", 150, "Neonatal deaths",
					"live born babies who died within 28 days", true));
			res.add(new monitoring_table(15, "Neonatal deaths", 151, "Early Neonatal Deaths (1-7 days)", "none",
					false));
			res.add(new monitoring_table(15, "Neonatal deaths", 152, "Late Neonatal Deaths (8-28 days)", "none",
					false));

			res.add(new monitoring_table(16, "Maternal deaths", 161, "Maternal deaths",
					"women who died in pregnancy or around child birth", true));

			monRepo.saveAll(res);

		}

		{
			List<datamap> res = new ArrayList<>();

			res.add(new datamap("death_options", 1, "Stillbirth"));
			res.add(new datamap("death_options", 2, "Early Neonatal Death"));
			res.add(new datamap("death_options", 3, "Maternal Death"));

			res.add(new datamap("adeath_options", 1, "Stillbirth Intrapartum"));
			res.add(new datamap("adeath_options", 2, "Stillbirth Antepartum"));
			res.add(new datamap("adeath_options", 3, "Early Neonatal Death"));

			res.add(new datamap("sex_options", 1, "Male"));
			res.add(new datamap("sex_options", 2, "Female"));
			res.add(new datamap("sex_options", 3, "Indeterminate"));
			res.add(new datamap("sex_options", 4, "Ambiguous genitalia"));
			res.add(new datamap("sex_options", 77, "Unknown"));
			res.add(new datamap("sex_options", 88, "Not Stated"));
			res.add(new datamap("sex_options", 99, "Not Applicable (maternal death)"));

			res.add(new datamap("mage_options", 0, "to 11 years"));
			res.add(new datamap("mage_options", 12, "years"));
			res.add(new datamap("mage_options", 13, "years"));
			res.add(new datamap("mage_options", 14, "years"));
			res.add(new datamap("mage_options", 15, "years"));
			res.add(new datamap("mage_options", 16, "years"));
			res.add(new datamap("mage_options", 17, "years"));
			res.add(new datamap("mage_options", 18, "years"));
			res.add(new datamap("mage_options", 19, "years"));
			res.add(new datamap("mage_options", 20, "years"));
			res.add(new datamap("mage_options", 21, "years"));
			res.add(new datamap("mage_options", 22, "years"));
			res.add(new datamap("mage_options", 23, "years"));
			res.add(new datamap("mage_options", 24, "years"));
			res.add(new datamap("mage_options", 25, "years"));
			res.add(new datamap("mage_options", 26, "years"));
			res.add(new datamap("mage_options", 27, "years"));
			res.add(new datamap("mage_options", 28, "years"));
			res.add(new datamap("mage_options", 29, "years"));
			res.add(new datamap("mage_options", 30, "years"));
			res.add(new datamap("mage_options", 31, "years"));
			res.add(new datamap("mage_options", 32, "years"));
			res.add(new datamap("mage_options", 33, "years"));
			res.add(new datamap("mage_options", 34, "years"));
			res.add(new datamap("mage_options", 35, "years"));
			res.add(new datamap("mage_options", 36, "years"));
			res.add(new datamap("mage_options", 37, "years"));
			res.add(new datamap("mage_options", 38, "years"));
			res.add(new datamap("mage_options", 39, "years"));
			res.add(new datamap("mage_options", 40, "years"));
			res.add(new datamap("mage_options", 41, "years"));
			res.add(new datamap("mage_options", 42, "years"));
			res.add(new datamap("mage_options", 43, "years"));
			res.add(new datamap("mage_options", 44, "years"));
			res.add(new datamap("mage_options", 45, "years"));
			res.add(new datamap("mage_options", 46, "years"));
			res.add(new datamap("mage_options", 47, "years"));
			res.add(new datamap("mage_options", 48, "years"));
			res.add(new datamap("mage_options", 49, "years"));
			res.add(new datamap("mage_options", 50, "years"));
			res.add(new datamap("mage_options", 51, "years and above"));
			res.add(new datamap("mage_options", 88, "Not Stated"));

			res.add(new datamap("edu_options", 1, "No Education"));
			res.add(new datamap("edu_options", 2, "Non-formal"));
			res.add(new datamap("edu_options", 3, "Basic/Primary"));
			res.add(new datamap("edu_options", 4, "Secondary"));
			res.add(new datamap("edu_options", 5, "Post Secondary/Vocation"));
			res.add(new datamap("edu_options", 6, "Tertiary"));
			res.add(new datamap("edu_options", 88, "Not Stated"));

			res.add(new datamap("work_options", 0, "Unemployed"));
			res.add(new datamap("work_options", 1, "House wife"));
			res.add(new datamap("work_options", 2, "Farming"));
			res.add(new datamap("work_options", 3, "Trading"));
			res.add(new datamap("work_options", 4, "Artisan (hairdresser, seamstress, etc"));
			res.add(new datamap("work_options", 5, "Civil/Public service"));
			res.add(new datamap("work_options", 66, "Other work"));
			res.add(new datamap("work_options", 88, "Not Stated"));

			res.add(new datamap("marital_options", 0, "Never married"));
			res.add(new datamap("marital_options", 1, "Married"));
			res.add(new datamap("marital_options", 2, "Living together"));
			res.add(new datamap("marital_options", 3, "Divorced/Separated"));
			res.add(new datamap("marital_options", 4, "Widowed"));
			res.add(new datamap("marital_options", 88, "Not Stated"));

			res.add(new datamap("religion_options", 1, "Christian"));
			res.add(new datamap("religion_options", 2, "Moslem"));
			res.add(new datamap("religion_options", 3, "Traditionalist/Spiritualist"));
			res.add(new datamap("religion_options", 4, "Other"));
			res.add(new datamap("religion_options", 88, "Not Stated"));

			res.add(new datamap("ethnic_options", 1, "Akan"));
			res.add(new datamap("ethnic_options", 2, "Ga/Dangme"));
			res.add(new datamap("ethnic_options", 3, "Ewe"));
			res.add(new datamap("ethnic_options", 4, "Guan"));
			res.add(new datamap("ethnic_options", 5, "Mole/Dagbani"));
			res.add(new datamap("ethnic_options", 6, "Grussi"));
			res.add(new datamap("ethnic_options", 7, "Gruma"));
			res.add(new datamap("ethnic_options", 8, "Other"));
			res.add(new datamap("ethnic_options", 88, "Not Stated"));

			res.add(new datamap("pweeks_options", 4, "Weeks"));
			res.add(new datamap("pweeks_options", 5, "Weeks"));
			res.add(new datamap("pweeks_options", 6, "Weeks"));
			res.add(new datamap("pweeks_options", 7, "Weeks"));
			res.add(new datamap("pweeks_options", 8, "Weeks"));
			res.add(new datamap("pweeks_options", 9, "Weeks"));
			res.add(new datamap("pweeks_options", 10, "Weeks"));
			res.add(new datamap("pweeks_options", 11, "Weeks"));
			res.add(new datamap("pweeks_options", 12, "Weeks"));
			res.add(new datamap("pweeks_options", 13, "Weeks"));
			res.add(new datamap("pweeks_options", 14, "Weeks"));
			res.add(new datamap("pweeks_options", 15, "Weeks"));
			res.add(new datamap("pweeks_options", 16, "Weeks"));
			res.add(new datamap("pweeks_options", 17, "Weeks"));
			res.add(new datamap("pweeks_options", 18, "Weeks"));
			res.add(new datamap("pweeks_options", 19, "Weeks"));
			res.add(new datamap("pweeks_options", 20, "Weeks"));
			res.add(new datamap("pweeks_options", 21, "Weeks"));
			res.add(new datamap("pweeks_options", 22, "Weeks"));
			res.add(new datamap("pweeks_options", 23, "Weeks"));
			res.add(new datamap("pweeks_options", 24, "Weeks"));
			res.add(new datamap("pweeks_options", 25, "Weeks"));
			res.add(new datamap("pweeks_options", 26, "Weeks"));
			res.add(new datamap("pweeks_options", 27, "Weeks"));
			res.add(new datamap("pweeks_options", 28, "Weeks"));
			res.add(new datamap("pweeks_options", 29, "Weeks"));
			res.add(new datamap("pweeks_options", 30, "Weeks"));
			res.add(new datamap("pweeks_options", 31, "Weeks"));
			res.add(new datamap("pweeks_options", 32, "Weeks"));
			res.add(new datamap("pweeks_options", 33, "Weeks"));
			res.add(new datamap("pweeks_options", 34, "Weeks"));
			res.add(new datamap("pweeks_options", 35, "Weeks"));
			res.add(new datamap("pweeks_options", 36, "Weeks"));
			res.add(new datamap("pweeks_options", 37, "Weeks"));
			res.add(new datamap("pweeks_options", 38, "Weeks"));
			res.add(new datamap("pweeks_options", 39, "Weeks"));
			res.add(new datamap("pweeks_options", 40, "Weeks"));
			res.add(new datamap("pweeks_options", 41, "Weeks"));
			res.add(new datamap("pweeks_options", 42, "Weeks"));
			res.add(new datamap("pweeks_options", 43, "Weeks"));
			res.add(new datamap("pweeks_options", 44, "Weeks"));
			res.add(new datamap("pweeks_options", 45, "Weeks"));
			res.add(new datamap("pweeks_options", 88, "Not Stated"));
			res.add(new datamap("pweeks_options", 99, "Not Applicable"));

			res.add(new datamap("pdays_options", 0, "Days"));
			res.add(new datamap("pdays_options", 1, "Day"));
			res.add(new datamap("pdays_options", 2, "Days"));
			res.add(new datamap("pdays_options", 3, "Days"));
			res.add(new datamap("pdays_options", 4, "Days"));
			res.add(new datamap("pdays_options", 5, "Days"));
			res.add(new datamap("pdays_options", 6, "Days"));
			res.add(new datamap("pdays_options", 88, "Not Stated"));
			res.add(new datamap("pdays_options", 99, "Not Applicable"));

			res.add(new datamap("ptype_options", 1, "Singleton"));
			res.add(new datamap("ptype_options", 2, "Twins"));
			res.add(new datamap("ptype_options", 3, "Triplets"));
			res.add(new datamap("ptype_options", 66, "Other"));
			res.add(new datamap("ptype_options", 88, "Not Stated"));

			res.add(new datamap("yesnodk_options", 0, "No"));
			res.add(new datamap("yesnodk_options", 1, "Yes"));
			res.add(new datamap("yesnodk_options", 77, "Unknown"));
			res.add(new datamap("yesnodk_options", 88, "Not Stated"));

			res.add(new datamap("yesnodkna_options", 0, "No"));
			res.add(new datamap("yesnodkna_options", 1, "Yes"));
			res.add(new datamap("yesnodkna_options", 77, "Unknown"));
			res.add(new datamap("yesnodkna_options", 88, "Not Stated"));
			res.add(new datamap("yesnodkna_options", 99, "Not Applicable"));

			res.add(new datamap("hiv_options", 0, "Negative(-ve)"));
			res.add(new datamap("hiv_options", 1, "Positive(+ve)"));
			res.add(new datamap("hiv_options", 77, "Unknown"));
			res.add(new datamap("hiv_options", 88, "Not Stated"));

			res.add(new datamap("patient_options", 0, "Mother"));
			res.add(new datamap("patient_options", 1, "Baby"));
			res.add(new datamap("patient_options", 2, "Mother and Baby"));
			res.add(new datamap("patient_options", 88, "Not Stated"));
			res.add(new datamap("patient_options", 99, "Not Applicable"));

			res.add(new datamap("source_options", 0, "Health Facility"));
			res.add(new datamap("source_options", 1, "Home"));
			res.add(new datamap("source_options", 66, "Other"));
			res.add(new datamap("source_options", 88, "Not Stated"));
			res.add(new datamap("source_options", 99, "Not Applicable"));

			res.add(new datamap("trans_options", 0, "On foot"));
			res.add(new datamap("trans_options", 1, "Tricycle"));
			res.add(new datamap("trans_options", 2, "Motor bike"));
			res.add(new datamap("trans_options", 3, "Vehicle (Commercial)"));
			res.add(new datamap("trans_options", 4, "Vehicle (Private)"));
			res.add(new datamap("trans_options", 5, "Ambulance"));
			res.add(new datamap("trans_options", 66, "Other"));
			res.add(new datamap("trans_options", 88, "Not Stated"));
			res.add(new datamap("trans_options", 99, "Not Applicable"));

			res.add(new datamap("period_options", 0, "Dawn (early morning)"));
			res.add(new datamap("period_options", 1, "Morning"));
			res.add(new datamap("period_options", 2, "Midday"));
			res.add(new datamap("period_options", 3, "Afternoon"));
			res.add(new datamap("period_options", 4, "Evening"));
			res.add(new datamap("period_options", 5, "Midnight"));
			res.add(new datamap("period_options", 6, "Night"));
			res.add(new datamap("period_options", 88, "Not Stated"));

			res.add(new datamap("admission_options", 1, "Abortion"));
			res.add(new datamap("admission_options", 2, "Ectopic Pregnancy"));
			res.add(new datamap("admission_options", 3, "Antenatal"));
			res.add(new datamap("admission_options", 4, "Intrapartum"));
			res.add(new datamap("admission_options", 5, "Postpartum"));
			res.add(new datamap("admission_options", 88, "Not Stated"));

			res.add(new datamap("mode_options", 0, "Spontaneous Vaginal Delivery"));
			res.add(new datamap("mode_options", 1, "Assisted Delivery (Vacuum/forceps)"));
			res.add(new datamap("mode_options", 2, "Elective Caesarean Section"));
			res.add(new datamap("mode_options", 3, "Emergency Caesarean Section"));
			res.add(new datamap("mode_options", 88, "Not Stated"));

			res.add(new datamap("startmode_options", 1, "Spontaneous"));
			res.add(new datamap("startmode_options", 2, "Induced"));
			res.add(new datamap("startmode_options", 88, "Not Stated"));

			res.add(new datamap("provider_options", 0, "Specialist"));
			res.add(new datamap("provider_options", 1, "Resident"));
			res.add(new datamap("provider_options", 2, "Medical Officer"));
			res.add(new datamap("provider_options", 3, "Medical/Physician Assistant"));
			res.add(new datamap("provider_options", 4, "House Officer"));
			res.add(new datamap("provider_options", 5, "Midwife"));
			res.add(new datamap("provider_options", 6, "Nurse (Registered General)"));
			res.add(new datamap("provider_options", 7, "Enrolled/Comm/Public Heath Nurse"));
			res.add(new datamap("provider_options", 8, "Traditional Birth Attendant (TBA)"));
			res.add(new datamap("provider_options", 77, "Unknown"));
			res.add(new datamap("provider_options", 88, "Not Stated"));

			res.add(new datamap("birthloc_options", 0, "Health facility"));
			res.add(new datamap("birthloc_options", 88, "Home"));
			res.add(new datamap("birthloc_options", 88, "On way to facility"));
			res.add(new datamap("birthloc_options", 66, "Other"));
			res.add(new datamap("birthloc_options", 77, "Unknown"));
			res.add(new datamap("birthloc_options", 88, "Not Stated"));

			res.add(new datamap("liqourvolume_options", 0, "Adequate"));
			res.add(new datamap("liqourvolume_options", 1, "Reduced (Oligohydramnious)"));
			res.add(new datamap("liqourvolume_options", 2, "No fluid (Anhydramnious)"));
			res.add(new datamap("liqourvolume_options", 3, "Too much fluid (Polyhydramnious)"));
			res.add(new datamap("liqourvolume_options", 88, "Not Stated"));

			res.add(new datamap("liqourcolor_options", 0, "Clear"));
			res.add(new datamap("liqourcolor_options", 1, "Meconium+"));
			res.add(new datamap("liqourcolor_options", 2, "Meconium++"));
			res.add(new datamap("liqourcolor_options", 3, "Meconium+++"));
			res.add(new datamap("liqourcolor_options", 4, "Blood stained"));
			res.add(new datamap("liqourcolor_options", 66, "Other"));
			res.add(new datamap("liqourcolor_options", 88, "Not Stated"));

			res.add(new datamap("liqourodour_options", 0, "Normal"));
			res.add(new datamap("liqourodour_options", 1, "Foul smell"));
			res.add(new datamap("liqourodour_options", 88, "Not Stated"));

			res.add(new datamap("babyoutcome_options", 0, "Single fresh stillbirth"));
			res.add(new datamap("babyoutcome_options", 1, "Single macerated stillbirth"));
			res.add(new datamap("babyoutcome_options_ext", 2, "Single very early neonatal death within 24hrs"));
			res.add(new datamap("babyoutcome_options_ext", 3, "Multiple live birth"));
			res.add(new datamap("babyoutcome_options_ext", 4, "Multiple with one or more fresh stillbirth"));
			res.add(new datamap("babyoutcome_options_ext", 5, "Multiple with one or more macerated stillbirth"));
			res.add(new datamap("babyoutcome_options_ext", 6,
					"Multiple with one or more very early neonatal death (within 24hrs)"));
			res.add(new datamap("babyoutcome_options", 77, "Unknown"));
			res.add(new datamap("babyoutcome_options", 88, "Not Stated"));
			res.add(new datamap("babyoutcome_options", 99, "Not Applicable"));

			res.add(new datamap("motheroutcome_options", 0, "Alive"));
			res.add(new datamap("motheroutcome_options", 1, "Dead"));
			res.add(new datamap("motheroutcome_options", 77, "Unknown"));
			res.add(new datamap("motheroutcome_options", 88, "Not Stated"));

			res.add(new datamap("lastheard_options", 0, "Antepartum"));
			res.add(new datamap("lastheard_options", 1, "Intrapartum (first stage)"));
			res.add(new datamap("lastheard_options", 2, "Intrapartum (second stage)"));
			res.add(new datamap("lastheard_options", 77, "Unknown"));
			res.add(new datamap("lastheard_options", 88, "Not Stated"));

			res.add(new datamap("apgar_options", 0, "0"));
			res.add(new datamap("apgar_options", 1, "1"));
			res.add(new datamap("apgar_options", 2, "2"));
			res.add(new datamap("apgar_options", 3, "3"));
			res.add(new datamap("apgar_options", 4, "4"));
			res.add(new datamap("apgar_options", 5, "5"));
			res.add(new datamap("apgar_options", 6, "6"));
			res.add(new datamap("apgar_options", 7, "7"));
			res.add(new datamap("apgar_options", 8, "8"));
			res.add(new datamap("apgar_options", 9, "9"));
			res.add(new datamap("apgar_options", 88, "Not Stated"));
			res.add(new datamap("apgar_options", 99, "Not Applicable"));

			res.add(new datamap("cstatus_options", 0, "Not Started"));
			res.add(new datamap("cstatus_options", 1, "Started"));
			res.add(new datamap("cstatus_options", 2, "Completed"));

			res.add(new datamap("neocod_options", 1, "Asphyxia"));
			res.add(new datamap("neocod_options", 2, "Preterm birth complications"));
			res.add(new datamap("neocod_options", 3, "Infections"));
			res.add(new datamap("neocod_options", 4, "Congenital anomalies"));
			res.add(new datamap("neocod_options", 5, "Special case"));

			res.add(new datamap("autopsyby_options", 1, "Pathologist"));
			res.add(new datamap("autopsyby_options", 2, "Medical Officer"));
			res.add(new datamap("autopsyby_options", 3, "Other"));

			res.add(new datamap("dthplace_options", 1, "In transit"));
			res.add(new datamap("dthplace_options", 2, "On arrival"));
			res.add(new datamap("dthplace_options", 3, "Community death"));
			res.add(new datamap("dthplace_options", 4, "Other"));
			res.add(new datamap("dthplace_options", 88, "Not Stated"));

			res.add(new datamap("icd10mm_options", 1, "Pregnancy with abortive complications"));
			res.add(new datamap("icd10mm_options", 2, "Hypertensive disorder of pregnancy"));
			res.add(new datamap("icd10mm_options", 3, "Obstetric haemorrhage"));
			res.add(new datamap("icd10mm_options", 4, "Pregnancy related infection"));
			res.add(new datamap("icd10mm_options", 5, "Other obstetric complication"));
			res.add(new datamap("icd10mm_options", 6, "Unanticipated complication of management"));
			res.add(new datamap("icd10mm_options", 7, "Non obstetric complication"));
			res.add(new datamap("icd10mm_options", 8, "Unknown"));
			res.add(new datamap("icd10mm_options", 9, "Coincidental/Accidental"));

			dmapRepo.saveAll(res);

		}

	}

}