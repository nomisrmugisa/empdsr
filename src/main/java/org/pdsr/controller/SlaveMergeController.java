package org.pdsr.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.pdsr.CONSTANTS;
import org.pdsr.master.model.abnormality_table;
import org.pdsr.master.model.audit_audit;
import org.pdsr.master.model.audit_case;
import org.pdsr.master.model.audit_recommendation;
import org.pdsr.master.model.case_antenatal;
import org.pdsr.master.model.case_babydeath;
import org.pdsr.master.model.case_biodata;
import org.pdsr.master.model.case_birth;
import org.pdsr.master.model.case_delivery;
import org.pdsr.master.model.case_fetalheart;
import org.pdsr.master.model.case_identifiers;
import org.pdsr.master.model.case_labour;
import org.pdsr.master.model.case_mdeath;
import org.pdsr.master.model.case_notes;
import org.pdsr.master.model.case_pregnancy;
import org.pdsr.master.model.case_referral;
import org.pdsr.master.model.cfactor_table;
import org.pdsr.master.model.complication_table;
import org.pdsr.master.model.cordfault_table;
import org.pdsr.master.model.diagnoses_table;
import org.pdsr.master.model.icd_diagnoses;
import org.pdsr.master.model.mcondition_table;
import org.pdsr.master.model.monitoring_table;
import org.pdsr.master.model.placentacheck_table;
import org.pdsr.master.model.resuscitation_table;
import org.pdsr.master.model.risk_table;
import org.pdsr.master.model.sync_table;
import org.pdsr.master.model.weekly_monitoring;
import org.pdsr.master.model.weekly_table;
import org.pdsr.master.model.wmPK;
import org.pdsr.master.repo.AuditAuditRepository;
import org.pdsr.master.repo.AuditCaseRepository;
import org.pdsr.master.repo.AuditRecommendRepository;
import org.pdsr.master.repo.CaseAntenatalRepository;
import org.pdsr.master.repo.CaseBabyRepository;
import org.pdsr.master.repo.CaseBiodataRepository;
import org.pdsr.master.repo.CaseBirthRepository;
import org.pdsr.master.repo.CaseDeliveryRepository;
import org.pdsr.master.repo.CaseFetalheartRepository;
import org.pdsr.master.repo.CaseLabourRepository;
import org.pdsr.master.repo.CaseMdeathRepository;
import org.pdsr.master.repo.CaseNotesRepository;
import org.pdsr.master.repo.CasePregnancyRepository;
import org.pdsr.master.repo.CaseReferralRepository;
import org.pdsr.master.repo.CaseRepository;
import org.pdsr.master.repo.MonitoringTableRepository;
import org.pdsr.master.repo.SyncTableRepository;
import org.pdsr.master.repo.WeeklyMonitoringTableRepository;
import org.pdsr.master.repo.WeeklyTableRepository;
import org.pdsr.pojos.datamerger;
import org.pdsr.slave.repo.SlaveAuditAuditRepository;
import org.pdsr.slave.repo.SlaveAuditCaseRepository;
import org.pdsr.slave.repo.SlaveAuditRecommendRepository;
import org.pdsr.slave.repo.SlaveCaseAntenatalRepository;
import org.pdsr.slave.repo.SlaveCaseBabyRepository;
import org.pdsr.slave.repo.SlaveCaseBiodataRepository;
import org.pdsr.slave.repo.SlaveCaseBirthRepository;
import org.pdsr.slave.repo.SlaveCaseDeliveryRepository;
import org.pdsr.slave.repo.SlaveCaseFetalheartRepository;
import org.pdsr.slave.repo.SlaveCaseLabourRepository;
import org.pdsr.slave.repo.SlaveCaseMdeathRepository;
import org.pdsr.slave.repo.SlaveCaseNotesRepository;
import org.pdsr.slave.repo.SlaveCasePregnancyRepository;
import org.pdsr.slave.repo.SlaveCaseReferralRepository;
import org.pdsr.slave.repo.SlaveCaseRepository;
import org.pdsr.slave.repo.SlaveWeeklyMonitoringTableRepository;
import org.pdsr.slave.repo.SlaveWeeklyTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/controls/datamerge")
public class SlaveMergeController {

	@Autowired
	private SyncTableRepository syncRepo;

	@Autowired
	private MonitoringTableRepository monRepo;

	@Autowired
	private WeeklyTableRepository weeklyRepo;

	@Autowired
	private SlaveWeeklyTableRepository sweeklyRepo;

	@Autowired
	private SlaveWeeklyMonitoringTableRepository sweekMRepo;

	@Autowired
	private WeeklyMonitoringTableRepository weekMRepo;

	@Autowired
	private CaseRepository caseRepo;

	@Autowired
	private SlaveCaseRepository scaseRepo;

	@Autowired
	private CaseBiodataRepository bioRepo;

	@Autowired
	private SlaveCaseBiodataRepository sbioRepo;

	@Autowired
	private CasePregnancyRepository pregRepo;

	@Autowired
	private SlaveCasePregnancyRepository spregRepo;

	@Autowired
	private CaseReferralRepository refRepo;

	@Autowired
	private SlaveCaseReferralRepository srefRepo;

	@Autowired
	private CaseDeliveryRepository delRepo;

	@Autowired
	private SlaveCaseDeliveryRepository sdelRepo;

	@Autowired
	private CaseAntenatalRepository antRepo;

	@Autowired
	private SlaveCaseAntenatalRepository santRepo;

	@Autowired
	private CaseLabourRepository labRepo;

	@Autowired
	private SlaveCaseLabourRepository slabRepo;

	@Autowired
	private CaseBirthRepository birthRepo;

	@Autowired
	private SlaveCaseBirthRepository sbirthRepo;

	@Autowired
	private CaseFetalheartRepository fetRepo;

	@Autowired
	private SlaveCaseFetalheartRepository sfetRepo;

	@Autowired
	private CaseBabyRepository babyRepo;

	@Autowired
	private SlaveCaseBabyRepository sbabyRepo;

	@Autowired
	private CaseMdeathRepository mdeathRepo;

	@Autowired
	private SlaveCaseMdeathRepository smdeathRepo;

	@Autowired
	private CaseNotesRepository noteRepo;

	@Autowired
	private SlaveCaseNotesRepository snoteRepo;

	@Autowired
	private AuditCaseRepository audRepo;

	@Autowired
	private SlaveAuditCaseRepository saudRepo;

	@Autowired
	private AuditAuditRepository aaudRepo;

	@Autowired
	private SlaveAuditAuditRepository saaudRepo;

	@Autowired
	private AuditRecommendRepository recRepo;

	@Autowired
	private SlaveAuditRecommendRepository srecRepo;

	@GetMapping("")
	public String datamerge(Principal principal, Model model, @RequestParam(required = false) String success) {

		model.addAttribute("selected", new datamerger());

		if (success != null) {
			model.addAttribute("success", "Merged Successfully!");
		}

		return "controls/merger-peer";
	}

	@Transactional
	@PostMapping("")
	public String datamerge(Principal principal, @ModelAttribute("selected") datamerger selected) {

		// merge weekly reports from slave to master (overrides if exists)
		if (selected.isMerge_weekly()) {
			mergeWeeklyTable();
			mergeWeeklyMonitoring();

		}

		sync_table object = syncRepo.findById(CONSTANTS.LICENSE_ID).get();
		// merge case entries from slave to master (overrides if exists)
		if (selected.isMerge_cases()) {
			mergeCaseIdentifier(object);
		}

		// merge case audits from slave to master (overrides if exists)
		if (selected.isMerge_audit()) {
			mergeAuditCase();
		}

		return "redirect:/controls/datamerge?success=yes";
	}

	private void mergeWeeklyTable() {
		List<org.pdsr.slave.model.weekly_table> sweeklys = sweeklyRepo.findAll();
		if (sweeklys != null && sweeklys.size() > 0) {
			List<weekly_table> weeklys = new ArrayList<weekly_table>();
			for (org.pdsr.slave.model.weekly_table s : sweeklys) {
				weekly_table weekly = new weekly_table();
				weekly.setWeekly_id(s.getWeekly_id());
				weekly.setWeekly_date(s.getWeekly_date());
				weekly.setWeekly_year(s.getWeekly_year());
				weekly.setWeekly_month(s.getWeekly_month());
				weekly.setWeekly_mdesc(s.getWeekly_mdesc());
				weekly.setWeekly_week(s.getWeekly_week());

				weeklys.add(weekly);
			}

			weeklyRepo.saveAll(weeklys);
		}

	}

	private void mergeWeeklyMonitoring() {
		List<org.pdsr.slave.model.weekly_monitoring> smonitors = sweekMRepo.findAll();
		if (smonitors != null && smonitors.size() > 0) {
			List<weekly_monitoring> monitors = new ArrayList<weekly_monitoring>();
			for (org.pdsr.slave.model.weekly_monitoring s : smonitors) {
				Optional<weekly_table> weekly = weeklyRepo.findById(s.getWm_grids().getWeekly_id());
				Optional<monitoring_table> mont = monRepo.findById(s.getWm_indices().getMindex());
				if (weekly.isPresent() && mont.isPresent()) {
					weekly_monitoring monitor = new weekly_monitoring();
					monitor.setId(new wmPK(s.getWm_grids().getWeekly_id(), s.getWm_indices().getMindex()));
					monitor.setWm_values(s.getWm_values());
					;
					monitor.setWm_subval(s.getWm_subval());
					monitor.setWm_grids(weekly.get());
					monitor.setWm_indices(mont.get());
					monitor.setData_sent(s.getData_sent());

					monitors.add(monitor);
				}
			}

			weekMRepo.saveAll(monitors);
		}
	}

	private void mergeCaseIdentifier(sync_table facility) {
		List<org.pdsr.slave.model.case_identifiers> sdeaths = scaseRepo.findAll();
		if (sdeaths != null && sdeaths.size() > 0) {
			List<case_identifiers> deaths = new ArrayList<case_identifiers>();
			for (org.pdsr.slave.model.case_identifiers s : sdeaths) {
				case_identifiers death = new case_identifiers();
				death.setCase_uuid(s.getCase_uuid());
				death.setCase_id(s.getCase_id());
				death.setCase_date(s.getCase_date());
				death.setCase_mid(s.getCase_mid());
				death.setCase_mname(s.getCase_mname());
				death.setCase_status(s.getCase_status());
				death.setCase_death(s.getCase_death());
				death.setData_sent(s.getData_sent());

				death.setCase_sync(facility);

				deaths.add(death);

			}

			caseRepo.saveAll(deaths);
		}

		mergeCaseBiodata();
		mergeCasePregnancy();
		mergeCaseReferral();
		mergeCaseDelivery();
		mergeCaseAntenatal();
		mergeCaseLabour();
		mergeCaseBirth();
		mergeCaseFetalheart();
		mergeCaseBabydeath();
		mergeCaseMdeath();
		mergeCaseNotes();

	}

	private void mergeCaseBiodata() {
		List<org.pdsr.slave.model.case_biodata> scases = sbioRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_biodata> mcases = new ArrayList<case_biodata>();
			for (org.pdsr.slave.model.case_biodata s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {
					case_biodata mcase = new case_biodata();
					mcase.setBiodata_uuid(s.getBiodata_uuid());
					mcase.setBiodata_sex(s.getBiodata_sex());
					mcase.setBiodata_mage(s.getBiodata_mage());
					mcase.setBiodata_medu(s.getBiodata_medu());
					mcase.setBiodata_mdob(s.getBiodata_mdob());
					mcase.setBiodata_maddress(s.getBiodata_maddress());
					mcase.setBiodata_location(s.getBiodata_location());
					mcase.setBiodata_contact(s.getBiodata_contact());
					mcase.setBiodata_work(s.getBiodata_work());
					mcase.setBiodata_marital(s.getBiodata_marital());
					mcase.setBiodata_religion(s.getBiodata_religion());
					mcase.setBiodata_ethnic(s.getBiodata_ethnic());
					mcase.setBiodata_json(s.getBiodata_json());
					mcase.setData_complete(s.getData_complete());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			bioRepo.saveAll(mcases);
		}

	}

	private void mergeCasePregnancy() {
		List<org.pdsr.slave.model.case_pregnancy> scases = spregRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_pregnancy> mcases = new ArrayList<case_pregnancy>();
			for (org.pdsr.slave.model.case_pregnancy s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {
					case_pregnancy mcase = new case_pregnancy();
					mcase.setPregnancy_uuid(s.getPregnancy_uuid());
					mcase.setPregnancy_type(s.getPregnancy_type());
					mcase.setPregnancy_days(s.getPregnancy_days());
					mcase.setPregnancy_weeks(s.getPregnancy_weeks());
					mcase.setPregnancy_json(s.getPregnancy_json());

					mcase.setData_complete(s.getData_complete());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			pregRepo.saveAll(mcases);
		}

	}

	private void mergeCaseReferral() {
		List<org.pdsr.slave.model.case_referral> scases = srefRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_referral> mcases = new ArrayList<case_referral>();
			for (org.pdsr.slave.model.case_referral s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {
					case_referral mcase = new case_referral();
					mcase.setReferral_uuid(s.getReferral_uuid());
					mcase.setReferral_adate(s.getReferral_adate());
					mcase.setReferral_ahour(s.getReferral_ahour());
					mcase.setReferral_aminute(s.getReferral_aminute());
					mcase.setReferral_atime(s.getReferral_atime());
					mcase.setReferral_case(s.getReferral_case());
					mcase.setReferral_date(s.getReferral_date());
					mcase.setReferral_facility(s.getReferral_facility());
					mcase.setReferral_file(s.getReferral_file());
					mcase.setReferral_filetype(s.getReferral_filetype());
					mcase.setReferral_hour(s.getReferral_hour());
					mcase.setReferral_minute(s.getReferral_minute());
					mcase.setReferral_patient(s.getReferral_patient());
					mcase.setReferral_source(s.getReferral_source());
					mcase.setReferral_time(s.getReferral_time());
					mcase.setReferral_transport(s.getReferral_transport());
					mcase.setReferral_notes(s.getReferral_notes());
					mcase.setReferral_json(s.getReferral_json());

					mcase.setData_complete(s.getData_complete());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			refRepo.saveAll(mcases);
		}

	}

	private void mergeCaseDelivery() {
		List<org.pdsr.slave.model.case_delivery> scases = sdelRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_delivery> mcases = new ArrayList<case_delivery>();
			for (org.pdsr.slave.model.case_delivery s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {
					case_delivery mcase = new case_delivery();
					mcase.setDelivery_uuid(s.getDelivery_uuid());
					mcase.setDelivery_date(s.getDelivery_date());
					mcase.setDelivery_hour(s.getDelivery_hour());
					mcase.setDelivery_minute(s.getDelivery_minute());
					mcase.setDelivery_period(s.getDelivery_period());
					mcase.setDelivery_time(s.getDelivery_time());
					mcase.setDelivery_weight(s.getDelivery_weight());
					mcase.setDelivery_occured(s.getDelivery_occured());
					mcase.setDelivery_json(s.getDelivery_json());

					mcase.setData_complete(s.getData_complete());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			delRepo.saveAll(mcases);
		}

	}

	private void mergeCaseAntenatal() {
		List<org.pdsr.slave.model.case_antenatal> scases = santRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_antenatal> mcases = new ArrayList<case_antenatal>();
			for (org.pdsr.slave.model.case_antenatal s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {
					case_antenatal mcase = new case_antenatal();
					mcase.setAntenatal_uuid(s.getAntenatal_uuid());
					mcase.setAntenatal_alcohol(s.getAntenatal_alcohol());
					mcase.setAntenatal_attend(s.getAntenatal_attend());
					mcase.setAntenatal_attendno(s.getAntenatal_attendno());
					mcase.setAntenatal_days(s.getAntenatal_days());
					mcase.setAntenatal_facility(s.getAntenatal_facility());
					mcase.setAntenatal_folicacid(s.getAntenatal_folicacid());
					mcase.setAntenatal_folicacid3m(s.getAntenatal_folicacid3m());
					mcase.setAntenatal_gravida(s.getAntenatal_gravida());
					mcase.setAntenatal_herbal(s.getAntenatal_herbal());
					mcase.setAntenatal_hiv(s.getAntenatal_hiv());
					mcase.setAntenatal_art(s.getAntenatal_art());
					mcase.setAntenatal_malprophy(s.getAntenatal_malprophy());
					mcase.setAntenatal_para(s.getAntenatal_para());
					mcase.setAntenatal_risks(s.getAntenatal_risks());

					List<risk_table> list = new ArrayList<risk_table>();
					for (org.pdsr.slave.model.risk_table r : santRepo.findRiskByAntenatalUuid(s.getAntenatal_uuid())) {
						risk_table item = new risk_table();
						item.setRisk_name(r.getRisk_name());
						item.setRisk_desc(r.getRisk_desc());

						list.add(item);
					}
					mcase.setRisks(list);
					mcase.setNew_risks(s.getNew_risks());

					mcase.setAntenatal_smoker(s.getAntenatal_smoker());
					mcase.setAntenatal_tetanus(s.getAntenatal_tetanus());
					mcase.setAntenatal_weeks(s.getAntenatal_weeks());

					mcase.setAntenatal_json(s.getAntenatal_json());

					mcase.setData_complete(s.getData_complete());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			antRepo.saveAll(mcases);
		}

	}

	private void mergeCaseLabour() {
		List<org.pdsr.slave.model.case_labour> scases = slabRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_labour> mcases = new ArrayList<case_labour>();
			for (org.pdsr.slave.model.case_labour s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {
					case_labour mcase = new case_labour();
					mcase.setLabour_uuid(s.getLabour_uuid());
					mcase.setLabour_complications(s.getLabour_complications());

					List<complication_table> list = new ArrayList<complication_table>();
					for (org.pdsr.slave.model.complication_table r : slabRepo
							.findComplicationsByUuid(s.getLabour_uuid())) {
						complication_table item = new complication_table();
						item.setComplication_name(r.getComplication_name());
						item.setComplication_desc(r.getComplication_desc());

						list.add(item);
					}

					mcase.setComplications(list);
					mcase.setNew_complications(s.getNew_complications());

					mcase.setLabour_herbalaug(s.getLabour_herbalaug());
					mcase.setLabour_lasthour1(s.getLabour_lasthour1());
					mcase.setLabour_lasthour2(s.getLabour_lasthour2());
					mcase.setLabour_lastminute1(s.getLabour_lastminute1());
					mcase.setLabour_lastminute2(s.getLabour_lastminute2());
					mcase.setLabour_partograph(s.getLabour_partograph());
					mcase.setLabour_seedate(s.getLabour_seedate());
					mcase.setLabour_seehour(s.getLabour_seehour());
					mcase.setLabour_seeminute(s.getLabour_seeminute());
					mcase.setLabour_seeperiod(s.getLabour_seeperiod());
					mcase.setLabour_startmode(s.getLabour_startmode());

					mcase.setLabour_json(s.getLabour_json());

					mcase.setData_complete(s.getData_complete());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			labRepo.saveAll(mcases);
		}

	}

	private void mergeCaseBirth() {
		List<org.pdsr.slave.model.case_birth> scases = sbirthRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_birth> mcases = new ArrayList<case_birth>();
			for (org.pdsr.slave.model.case_birth s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {
					case_birth mcase = new case_birth();
					mcase.setBirth_uuid(s.getBirth_uuid());
					mcase.setBirth_abnormalities(s.getBirth_abnormalities());

					List<abnormality_table> list = new ArrayList<abnormality_table>();
					for (org.pdsr.slave.model.abnormality_table r : sbirthRepo
							.findComplicationsByUuid(s.getBirth_uuid())) {
						abnormality_table item = new abnormality_table();
						item.setAbnormal_name(r.getAbnormal_name());
						item.setAbnormal_desc(r.getAbnormal_desc());

						list.add(item);
					}
					mcase.setAbnormalities(list);
					mcase.setNew_abnormalities(s.getNew_abnormalities());

					mcase.setBirth_babyoutcome(s.getBirth_babyoutcome());
					mcase.setBirth_cordfaults(s.getBirth_cordfaults());

					List<cordfault_table> list1 = new ArrayList<cordfault_table>();
					for (org.pdsr.slave.model.cordfault_table r : sbirthRepo.findCordfaultsByUuid(s.getBirth_uuid())) {
						cordfault_table item = new cordfault_table();
						item.setCordfault_name(r.getCordfault_name());
						item.setCordfault_desc(r.getCordfault_desc());

						list1.add(item);
					}
					mcase.setCordfaults(list1);
					mcase.setNew_cordfaults(s.getNew_cordfaults());

					mcase.setBirth_csproposedate(s.getBirth_csproposedate());
					mcase.setBirth_csproposehour(s.getBirth_csproposehour());
					mcase.setBirth_csproposeminute(s.getBirth_csproposeminute());
					mcase.setBirth_csproposetime(s.getBirth_csproposetime());
					mcase.setBirth_facility(s.getBirth_facility());
					mcase.setBirth_insistnormal(s.getBirth_insistnormal());
					mcase.setBirth_liqourcolor(s.getBirth_liqourcolor());
					mcase.setBirth_liqourodour(s.getBirth_liqourodour());
					mcase.setBirth_liqourvolume(s.getBirth_liqourvolume());
					mcase.setBirth_mode(s.getBirth_mode());
					mcase.setBirth_motheroutcome(s.getBirth_motheroutcome());
					mcase.setBirth_placentachecks(s.getBirth_placentachecks());

					List<placentacheck_table> list2 = new ArrayList<placentacheck_table>();
					for (org.pdsr.slave.model.placentacheck_table r : sbirthRepo
							.findPlacentachecksByUuid(s.getBirth_uuid())) {
						placentacheck_table item = new placentacheck_table();
						item.setPlacentacheck_name(r.getPlacentacheck_name());
						item.setPlacentacheck_desc(r.getPlacentacheck_desc());

						list2.add(item);
					}
					mcase.setPlacentachecks(list2);
					mcase.setNew_placentachecks(s.getNew_placentachecks());

					mcase.setBirth_provider(s.getBirth_provider());

					mcase.setBirth_mbabyoutcome(s.getBirth_mbabyoutcome());

					mcase.setBirth_json(s.getBirth_json());

					mcase.setData_complete(s.getData_complete());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			birthRepo.saveAll(mcases);
		}

	}

	private void mergeCaseFetalheart() {
		List<org.pdsr.slave.model.case_fetalheart> scases = sfetRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_fetalheart> mcases = new ArrayList<case_fetalheart>();
			for (org.pdsr.slave.model.case_fetalheart s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {
					case_fetalheart mcase = new case_fetalheart();
					mcase.setFetalheart_uuid(s.getFetalheart_uuid());
					mcase.setFetalheart_arrival(s.getFetalheart_arrival());
					mcase.setFetalheart_lastheard(s.getFetalheart_lastheard());
					mcase.setFetalheart_refered(s.getFetalheart_refered());

					mcase.setFetalheart_json(s.getFetalheart_json());
					mcase.setData_complete(s.getData_complete());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			fetRepo.saveAll(mcases);
		}

	}

	private void mergeCaseBabydeath() {
		List<org.pdsr.slave.model.case_babydeath> scases = sbabyRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_babydeath> mcases = new ArrayList<case_babydeath>();
			for (org.pdsr.slave.model.case_babydeath s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {
					case_babydeath mcase = new case_babydeath();
					mcase.setBaby_uuid(s.getBaby_uuid());
					mcase.setBaby_apgar1(s.getBaby_apgar1());
					mcase.setBaby_apgar5(s.getBaby_apgar5());
					mcase.setBaby_cry(s.getBaby_cry());
					mcase.setBaby_ddate(s.getBaby_ddate());
					mcase.setBaby_dhour(s.getBaby_dhour());
					mcase.setBaby_dminute(s.getBaby_dminute());
					mcase.setBaby_dtime(s.getBaby_dtime());
					mcase.setBaby_medicalcod(s.getBaby_medicalcod());

					mcase.setBaby_resuscitation(s.getBaby_resuscitation());

					List<resuscitation_table> list = new ArrayList<resuscitation_table>();
					for (org.pdsr.slave.model.resuscitation_table r : sbabyRepo
							.findResuscitationsByUuid(s.getBaby_uuid())) {
						resuscitation_table item = new resuscitation_table();
						item.setResuscitation_name(r.getResuscitation_name());
						item.setResuscitation_desc(r.getResuscitation_desc());

						list.add(item);
					}

					mcase.setResuscitations(list);
					mcase.setNew_resuscitation(s.getNew_resuscitation());

					mcase.setBaby_admitted(s.getBaby_admitted());
					List<diagnoses_table> list1 = new ArrayList<diagnoses_table>();
					for (org.pdsr.slave.model.diagnoses_table r : sbabyRepo.findDiagnosesByUuid(s.getBaby_uuid())) {
						diagnoses_table item = new diagnoses_table();
						item.setDiagnosis_name(r.getDiagnosis_name());
						item.setDiagnosis_desc(r.getDiagnosis_desc());

						list1.add(item);
					}

					mcase.setDiagnoses(list1);

					List<icd_diagnoses> list2 = new ArrayList<icd_diagnoses>();
					for (org.pdsr.slave.model.icd_diagnoses r : sbabyRepo.findICDDiagnosesByUuid(s.getBaby_uuid())) {
						icd_diagnoses item = new icd_diagnoses();
						item.setIcd_code(r.getIcd_code());
						item.setIcd_desc(r.getIcd_desc());

						list2.add(item);
					}

					mcase.setIcd_diagnoses(list2);

					mcase.setBaby_json(s.getBaby_json());
					;

					mcase.setData_complete(s.getData_complete());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			babyRepo.saveAll(mcases);
		}

	}

	private void mergeCaseMdeath() {
		List<org.pdsr.slave.model.case_mdeath> scases = smdeathRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_mdeath> mcases = new ArrayList<case_mdeath>();
			for (org.pdsr.slave.model.case_mdeath s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {

					case_mdeath mcase = new case_mdeath();
					mcase.setMdeath_uuid(s.getMdeath_uuid());

					mcase.setMdeath_date(s.getMdeath_date());
					mcase.setMdeath_time(s.getMdeath_time());
					mcase.setMdeath_hour(s.getMdeath_hour());
					mcase.setMdeath_minute(s.getMdeath_minute());
					mcase.setMdeath_datetime_notstated(s.getMdeath_datetime_notstated());
					mcase.setMdeath_autopsy(s.getMdeath_autopsy());
					mcase.setMdeath_autopsy_date(s.getMdeath_autopsy_date());
					mcase.setMdeath_autopsy_location(s.getMdeath_autopsy_location());
					mcase.setMdeath_autopsy_by(s.getMdeath_autopsy_by());
					mcase.setMdeath_autopsy_final_cod(s.getMdeath_autopsy_final_cod());
					mcase.setMdeath_autopsy_antec_cod(s.getMdeath_autopsy_antec_cod());
					mcase.setMdeath_autopsy_ops_cod(s.getMdeath_autopsy_ops_cod());

					mcase.setMdeath_early_evacuation(s.getMdeath_early_evacuation());
					mcase.setMdeath_early_antibiotic(s.getMdeath_early_antibiotic());
					mcase.setMdeath_early_laparotomy(s.getMdeath_early_laparotomy());
					mcase.setMdeath_early_hysterectomy(s.getMdeath_early_hysterectomy());
					mcase.setMdeath_early_transfusion(s.getMdeath_early_transfusion());
					mcase.setMdeath_early_antihyper(s.getMdeath_early_antihyper());
					mcase.setMdeath_early_other(s.getMdeath_early_other());

					mcase.setMdeath_ante_transfusion(s.getMdeath_ante_transfusion());
					mcase.setMdeath_ante_antibiotic(s.getMdeath_ante_antibiotic());
					mcase.setMdeath_ante_externalv(s.getMdeath_ante_externalv());
					mcase.setMdeath_ante_magsulphate(s.getMdeath_ante_magsulphate());
					mcase.setMdeath_ante_diazepam(s.getMdeath_ante_diazepam());
					mcase.setMdeath_ante_antihyper(s.getMdeath_ante_antihyper());
					mcase.setMdeath_ante_hysterotomy(s.getMdeath_ante_hysterotomy());
					mcase.setMdeath_ante_other(s.getMdeath_ante_other());

					mcase.setMdeath_intra_instrumental(s.getMdeath_intra_instrumental());
					mcase.setMdeath_intra_antibiotic(s.getMdeath_intra_antibiotic());
					mcase.setMdeath_intra_caesarian(s.getMdeath_intra_caesarian());
					mcase.setMdeath_intra_hysterectomy(s.getMdeath_intra_hysterectomy());
					mcase.setMdeath_intra_transfusion(s.getMdeath_intra_transfusion());
					mcase.setMdeath_intra_magsulphate(s.getMdeath_intra_magsulphate());
					mcase.setMdeath_intra_antihyper(s.getMdeath_intra_antihyper());
					mcase.setMdeath_intra_diazepam(s.getMdeath_intra_diazepam());
					mcase.setMdeath_intra_other(s.getMdeath_intra_other());

					mcase.setMdeath_postpart_evacuation(s.getMdeath_postpart_evacuation());
					mcase.setMdeath_postpart_antibiotic(s.getMdeath_postpart_antibiotic());
					mcase.setMdeath_postpart_laparotomy(s.getMdeath_postpart_laparotomy());
					mcase.setMdeath_postpart_hysterectomy(s.getMdeath_postpart_hysterectomy());
					mcase.setMdeath_postpart_transfusion(s.getMdeath_postpart_transfusion());
					mcase.setMdeath_postpart_magsulphate(s.getMdeath_postpart_magsulphate());
					mcase.setMdeath_postpart_placentaremoval(s.getMdeath_postpart_placentaremoval());
					mcase.setMdeath_postpart_antihyper(s.getMdeath_postpart_antihyper());
					mcase.setMdeath_postpart_diazepam(s.getMdeath_postpart_diazepam());
					mcase.setMdeath_postpart_other(s.getMdeath_postpart_other());

					mcase.setMdeath_other_anaesthga(s.getMdeath_other_anaesthga());
					mcase.setMdeath_other_epidural(s.getMdeath_other_epidural());
					mcase.setMdeath_other_spinal(s.getMdeath_other_spinal());
					mcase.setMdeath_other_local(s.getMdeath_other_local());
					mcase.setMdeath_other_invasive(s.getMdeath_other_invasive());
					mcase.setMdeath_other_antihyper(s.getMdeath_other_antihyper());
					mcase.setMdeath_other_icuventilation(s.getMdeath_other_icuventilation());
					mcase.setMdeath_new_intervention(s.getMdeath_new_intervention());

					mcase.setMdeath_json(s.getMdeath_json());

					mcase.setData_complete(s.getData_complete());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			mdeathRepo.saveAll(mcases);
		}

	}

	private void mergeCaseNotes() {
		List<org.pdsr.slave.model.case_notes> scases = snoteRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<case_notes> mcases = new ArrayList<case_notes>();
			for (org.pdsr.slave.model.case_notes s : scases) {
				Optional<case_identifiers> icase = caseRepo.findById(s.getCase_uuid().getCase_uuid());
				if (icase.isPresent()) {
					case_notes mcase = new case_notes();
					mcase.setNotes_uuid(s.getNotes_uuid());
					mcase.setNotes_file(s.getNotes_file());
					mcase.setNotes_filetype(s.getNotes_filetype());
					mcase.setNotes_text(s.getNotes_text());

					mcase.setNotes_json(s.getNotes_json());

					mcase.setCase_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			noteRepo.saveAll(mcases);
		}

	}

	private void mergeAuditCase() {
		List<org.pdsr.slave.model.audit_case> scases = saudRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<audit_case> mcases = new ArrayList<audit_case>();
			for (org.pdsr.slave.model.audit_case s : scases) {
				audit_case mcase = new audit_case();
				mcase.setAudit_uuid(s.getAudit_uuid());
				mcase.setAudit_data(s.getAudit_data());
				mcase.setAudit_date(s.getAudit_date());
				mcase.setAudit_expired(s.getAudit_expired());
				mcase.setCase_death(s.getCase_death());

				mcases.add(mcase);

			}

			// save master
			audRepo.saveAll(mcases);
		}

		mergeAuditAudit();
	}

	private void mergeAuditAudit() {
		List<org.pdsr.slave.model.audit_audit> scases = saaudRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<audit_audit> mcases = new ArrayList<audit_audit>();
			for (org.pdsr.slave.model.audit_audit s : scases) {
				Optional<audit_case> icase = audRepo.findById(s.getAudit_case().getAudit_uuid());
				if (icase.isPresent()) {
					audit_audit mcase = new audit_audit();
					mcase.setAudit_uuid(s.getAudit_uuid());
					mcase.setAudit_cdate(s.getAudit_cdate());
					mcase.setAudit_csc(s.getAudit_csc());
					mcase.setAudit_death(s.getAudit_death());
					mcase.setAudit_delay1(s.getAudit_delay1());
					mcase.setAudit_delay2(s.getAudit_delay2());
					mcase.setAudit_delay3a(s.getAudit_delay3a());
					mcase.setAudit_delay3b(s.getAudit_delay3b());
					mcase.setAudit_delay3c(s.getAudit_delay3c());
					mcase.setAudit_facmfs(s.getAudit_facmfs());
					mcase.setAudit_hwkmfs(s.getAudit_hwkmfs());
					mcase.setAudit_icd10(s.getAudit_icd10());
					mcase.setAudit_icdpm(s.getAudit_icdpm());
					mcase.setAudit_ifcmfs(s.getAudit_ifcmfs());

					List<cfactor_table> list1 = new ArrayList<cfactor_table>();
					for (org.pdsr.slave.model.cfactor_table r : saaudRepo.findPatientFactorsByUuid(s.getAudit_uuid())) {
						cfactor_table item = new cfactor_table();
						item.setId(r.getId());
						item.setIdgroup(r.getIdgroup());
						item.setCfactor_name(r.getCfactor_name());
						list1.add(item);
					}
					mcase.setPatient_factors(list1);

					List<cfactor_table> list2 = new ArrayList<cfactor_table>();
					for (org.pdsr.slave.model.cfactor_table r : saaudRepo
							.findTransportFactorsByUuid(s.getAudit_uuid())) {
						cfactor_table item = new cfactor_table();
						item.setId(r.getId());
						item.setIdgroup(r.getIdgroup());
						item.setCfactor_name(r.getCfactor_name());
						list2.add(item);
					}
					mcase.setTransport_factors(list2);

					List<cfactor_table> list3 = new ArrayList<cfactor_table>();
					for (org.pdsr.slave.model.cfactor_table r : saaudRepo
							.findAdministrativeFactorsByUuid(s.getAudit_uuid())) {
						cfactor_table item = new cfactor_table();
						item.setId(r.getId());
						item.setIdgroup(r.getIdgroup());
						item.setCfactor_name(r.getCfactor_name());
						list3.add(item);
					}
					mcase.setAdministrative_factors(list3);

					List<cfactor_table> list4 = new ArrayList<cfactor_table>();
					for (org.pdsr.slave.model.cfactor_table r : saaudRepo
							.findHealthworkerFactorsByUuid(s.getAudit_uuid())) {
						cfactor_table item = new cfactor_table();
						item.setId(r.getId());
						item.setIdgroup(r.getIdgroup());
						item.setCfactor_name(r.getCfactor_name());
						list4.add(item);
					}
					mcase.setHealthworker_factors(list4);

					List<cfactor_table> list5 = new ArrayList<cfactor_table>();
					for (org.pdsr.slave.model.cfactor_table r : saaudRepo
							.findDocumentFactorsByUuid(s.getAudit_uuid())) {
						cfactor_table item = new cfactor_table();
						item.setId(r.getId());
						item.setIdgroup(r.getIdgroup());
						item.setCfactor_name(r.getCfactor_name());
						list5.add(item);
					}
					mcase.setDocument_factors(list5);

					List<mcondition_table> list6 = new ArrayList<mcondition_table>();
					for (org.pdsr.slave.model.mcondition_table r : saaudRepo
							.findMaternalConditionsByUuid(s.getAudit_uuid())) {
						mcondition_table item = new mcondition_table();
						item.setIcdm(r.getIcdm());
						item.setIcdmgroup(r.getIcdmgroup());
						item.setIcdm_name(r.getIcdm_name());
						list6.add(item);
					}
					mcase.setMaternal_conditions(list6);

					mcase.setData_sent(s.getData_sent());

					mcase.setAudit_json(s.getAudit_json());

					mcase.setAudit_case(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			aaudRepo.saveAll(mcases);
		}

		mergeAuditRecommendations();

	}

	private void mergeAuditRecommendations() {
		List<org.pdsr.slave.model.audit_recommendation> scases = srecRepo.findAll();
		if (scases != null && scases.size() > 0) {

			List<audit_recommendation> mcases = new ArrayList<audit_recommendation>();
			for (org.pdsr.slave.model.audit_recommendation s : scases) {
				Optional<audit_audit> icase = aaudRepo.findById(s.getAudit_uuid().getAudit_uuid());
				if (icase.isPresent()) {
					audit_recommendation mcase = new audit_recommendation();
					mcase.setRecommendation_uuid(s.getRecommendation_uuid());
					mcase.setRecommendation_comments(s.getRecommendation_comments());
					mcase.setRecommendation_date(s.getRecommendation_date());
					mcase.setRecommendation_deadline(s.getRecommendation_deadline());
					mcase.setRecommendation_leader(s.getRecommendation_leader());
					mcase.setRecommendation_reporter(s.getRecommendation_reporter());
					mcase.setRecommendation_resources(s.getRecommendation_resources());
					mcase.setRecommendation_status(s.getRecommendation_status());
					mcase.setRecommendation_task(s.getRecommendation_task());
					mcase.setRecommendation_title(s.getRecommendation_title());
					mcase.setData_sent(s.getData_sent());

					mcase.setAudit_uuid(icase.get());

					mcases.add(mcase);
				}
			}

			// save master
			recRepo.saveAll(mcases);
		}

	}

}
// end class