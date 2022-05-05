package org.pdsr.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.pdsr.CONSTANTS;
import org.pdsr.ReportExcelExporter;
import org.pdsr.master.model.audit_recommendation;
import org.pdsr.master.model.monitoring_table;
import org.pdsr.master.model.sync_table;
import org.pdsr.master.model.weekly_monitoring;
import org.pdsr.master.model.weekly_table;
import org.pdsr.master.model.wmPK;
import org.pdsr.master.repo.AuditRecommendRepository;
import org.pdsr.master.repo.MonitoringTableRepository;
import org.pdsr.master.repo.SyncTableRepository;
import org.pdsr.master.repo.WeeklyMonitoringTableRepository;
import org.pdsr.master.repo.WeeklyTableRepository;
import org.pdsr.pojos.upload;
import org.pdsr.pojos.weekgrid;
import org.pdsr.pojos.wmindicators;
import org.pdsr.pojos.wmoindicators;
import org.pdsr.pojos.wmsearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/reporting")
public class ReportController {

	@Autowired
	private SyncTableRepository syncRepo;

	@Autowired
	private MonitoringTableRepository monRepo;

	@Autowired
	private WeeklyTableRepository weekRepo;

	@Autowired
	private WeeklyMonitoringTableRepository wmRepo;

	@Autowired
	private AuditRecommendRepository recRepo;

	@Autowired
	private MessageSource msg;

	@GetMapping("")
	public String list(Principal principal, Model model,
			@RequestParam(name = "success", required = false) String success) {

		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";

		}

		List<Object[]> weeklist = weekRepo.findAllWeeklyYearAndMonth();

		List<weekly_table> curweek = weekRepo.findByWeeklyYearAndMonth(Calendar.getInstance().get(Calendar.YEAR),
				Calendar.getInstance().get(Calendar.MONTH));

		if (curweek == null || curweek.isEmpty()) {
			Object[] newmonthyear = new Object[] { Calendar.getInstance().get(Calendar.YEAR),
					Calendar.getInstance().get(Calendar.MONTH),
					msg.getMessage("month" + Calendar.getInstance().get(Calendar.MONTH), null, Locale.getDefault()),
					null };

			weeklist.add(newmonthyear);
		}

		model.addAttribute("back", "back");
		model.addAttribute("yearmonthitems", weeklist);
		model.addAttribute("selected", new upload());

		if (success != null) {
			model.addAttribute("success", "Successfully uploaded");
		}

		return "reporting/report-retrieve";
	}

	@PostMapping("")
	public String list(Principal principal, @ModelAttribute("selected") upload selected, Model model) {

		if (selected.getFile() == null || selected.getFile().isEmpty()) {
			model.addAttribute("fileerror1", "No csv file selected");
			return "reporting/report-retrieve";
		}

		final sync_table sync = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		Calendar cal = Calendar.getInstance();
		cal.set(selected.getDatayear(), selected.getDatamonth(), 1);

		final int maxNumberOfWeeks = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);

		List<weekly_monitoring> datagrid = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(selected.getFile().getInputStream())) {

			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 1, 100, sync.getSync_code()));// total
																											// deliveries
			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 2, 101, sync.getSync_code()));// Vaginal
																											// deliveries
			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 3, 102, sync.getSync_code()));// Assited
																											// deliveries
			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 4, 103, sync.getSync_code()));// Caesarean
																											// deliveries

			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 5, 110, sync.getSync_code()));// total
																											// births
			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 6, 111, sync.getSync_code()));// singleton
			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 7, 112, sync.getSync_code()));// multiple

			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 8, 120, sync.getSync_code()));// stillbirths
			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 9, 121, sync.getSync_code()));// antepartum
			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 10, 122, sync.getSync_code()));// intrapartum

			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 11, 130, sync.getSync_code()));// livebirths
			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 12, 131, sync.getSync_code()));// term
			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 13, 132, sync.getSync_code()));// preterm
			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 14, 133, sync.getSync_code()));// very
																											// preterm

			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 15, 136, sync.getSync_code()));// normal
																											// birthwght
			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 16, 137, sync.getSync_code()));// low
																											// birthwght
			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 17, 138, sync.getSync_code()));// very low
																											// birthwght
			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 18, 139, sync.getSync_code()));// extremely
																											// low
																											// birthwght

			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 19, 150, sync.getSync_code()));// neonatal
																											// deaths
			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 20, 151, sync.getSync_code()));// early
																											// deaths
			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 21, 152, sync.getSync_code()));// late
																											// deaths

			datagrid.addAll(importRow(selected, workbook, maxNumberOfWeeks, 22, 161, sync.getSync_code()));// maternal
																											// deaths

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (datagrid.size() > 0) {
			wmRepo.saveAll(datagrid);
		}

		// write the original file to the disk somewhere for backup purposes
		selected.setFilelocation(
				"MONTHLYREPORT_" + (new java.util.Date().getTime()) + "_" + selected.getFile().getOriginalFilename());
		try {
			CONSTANTS.writeToDisk("FACILITY_" + sync.getSync_code(),
					"PERIOD_" + selected.getDatamonth() + "_" + selected.getDatayear(), selected.getFilelocation(),
					selected.getFile().getBytes());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/reporting?success=yes";
	}

	private List<weekly_monitoring> importRow(upload selected, Workbook workbook, final int maxNumberOfWeeks,
			int rowIndex, int statId, String case_sync) {
		List<weekly_monitoring> subgrid = new ArrayList<>();

		Sheet sheet = workbook.getSheetAt(selected.getSheetnumber());// maybe I can later allow for multiple sheet
																		// import

		// IMPORT DATA ON TOTAL DELVERES
		Row row = sheet.getRow(rowIndex);
		monitoring_table m = monRepo.findById(statId).get();

		for (int week = 1; week < maxNumberOfWeeks; week++) {

			weekly_table w = new weekly_table();
			w.setWeekly_date(new java.util.Date());
			w.setWeekly_year(selected.getDatayear());
			w.setWeekly_month(selected.getDatamonth());
			w.setWeekly_mdesc(msg.getMessage("month" + selected.getDatamonth(), null, Locale.getDefault()));
			w.setWeekly_week(week);
			w.setWeekly_id(Integer.valueOf(selected.getDatayear() + "" + selected.getDatamonth() + "" + week));
			w.setCase_sync(case_sync);

			weekRepo.save(w);

			weekly_monitoring wm = new weekly_monitoring();
			wm.setId(new wmPK(w.getWeekly_id(), m.getMindex()));
			wm.setWm_values(0);
			wm.setWm_subval(0);// don't worry about this, when the data is being opened, the subval
			// calculations will be triggered
			wm.setData_sent(0);//reset the data sending indicator when any record is edited

			wm.setWm_grids(w);// link it to the weeks
			wm.setWm_indices(m);// link it to the statistic indicator being monitored

			subgrid.add(wm);

		}

		int wsum = 0;
		for (int cellIndex = 2; cellIndex <= 6; cellIndex++) {
			// we will sum up all the weeks and place in the last week
			wsum += (row.getCell(cellIndex).getCellType() == CellType.NUMERIC)
					? (int) row.getCell(cellIndex).getNumericCellValue()
					: 0;

		}

		// treat the last week in the month differently
		weekly_table lw = new weekly_table();
		lw.setWeekly_date(new java.util.Date());
		lw.setWeekly_year(selected.getDatayear());
		lw.setWeekly_month(selected.getDatamonth());
		lw.setWeekly_mdesc(msg.getMessage("month" + selected.getDatamonth(), null, Locale.getDefault()));
		lw.setWeekly_week(maxNumberOfWeeks);
		lw.setWeekly_id(Integer.valueOf(selected.getDatayear() + "" + selected.getDatamonth() + "" + maxNumberOfWeeks));
		lw.setCase_sync(case_sync);

		weekRepo.save(lw);

		weekly_monitoring wm = new weekly_monitoring();
		wm.setId(new wmPK(lw.getWeekly_id(), m.getMindex()));
		wm.setWm_values(wsum);
		wm.setWm_subval(0);// don't worry about this, when the data is being opened, the subval
		// calculations will be triggered

		wm.setWm_grids(lw);// link it to the weeks
		wm.setWm_indices(m);// link it to the statistic indicator being monitored

		subgrid.add(wm);

		return subgrid;

	}

	@GetMapping("/edit/{yearid}/{monthid}")
	public String add(Principal principal, Model model, @PathVariable("yearid") Integer yearid,
			@PathVariable("monthid") Integer monthid) {

		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table sync = syncRepo.findById(CONSTANTS.FACILITY_ID).get();
		List<monitoring_table> monlist = monRepo.findGlabels(true);
		List<weekly_monitoring> headlist = new ArrayList<>();
		for (monitoring_table elem : monlist) {

			Integer gindex = elem.getGindex();
			weekly_monitoring head = null;
			for (weekly_monitoring e : elem.getStatistics()) {
				if (gindex == e.getWm_indices().getGindex() && (e.getWm_indices().getMindex() % gindex) == 0) {
					head = e;

					int headTotal = 0;
					for (weekly_monitoring sube : head.getWm_grids().getStatistics()) {
						if (head.getWm_indices().getGindex() == sube.getWm_indices().getGindex()
								&& (sube.getWm_indices().getMindex() % head.getWm_indices().getGindex()) != 0
								&& (sube.getWm_indices().getMindex() % head.getWm_indices().getGindex()) <= 5) {

							headTotal += sube.getWm_values();
						}

					}

					head.setWm_subval(head.getWm_values() - headTotal);
					for (weekly_monitoring sube : head.getWm_grids().getStatistics()) {
						if (head.getWm_indices().getGindex() == sube.getWm_indices().getGindex()
								&& (sube.getWm_indices().getMindex() % head.getWm_indices().getGindex()) != 0
								&& (sube.getWm_indices().getMindex() % head.getWm_indices().getGindex()) <= 5) {

							sube.setData_sent(0);//reset the data sending indicator when any record is edited
							sube.setWm_subval(head.getWm_subval());
						}

					}

					int headTotal1 = 0;
					for (weekly_monitoring sube : head.getWm_grids().getStatistics()) {
						if (head.getWm_indices().getGindex() == sube.getWm_indices().getGindex()
								&& (sube.getWm_indices().getMindex() % head.getWm_indices().getGindex()) > 5) {

							headTotal1 += sube.getWm_values();
						}

					}

					int secondtotal = head.getWm_values() - headTotal1;
					if (secondtotal != head.getWm_values()) {
						head.setWm_subval(secondtotal);
					}
					for (weekly_monitoring sube : head.getWm_grids().getStatistics()) {
						if (head.getWm_indices().getGindex() == sube.getWm_indices().getGindex()
								&& (sube.getWm_indices().getMindex() % head.getWm_indices().getGindex()) > 5) {

							sube.setData_sent(0);//reset the data sending indicator when any record is edited
							sube.setWm_subval(head.getWm_subval());
						}

					}

					headlist.add(head);

				}

			}

		}

		wmRepo.saveAll(headlist);

		weekgrid selected = new weekgrid();
		selected.setGrid_yearmonth(msg.getMessage("month" + monthid, null, Locale.getDefault()) + "-" + yearid);
		selected.setGrid_year(yearid);
		selected.setGrid_month(monthid);

		List<weekly_table> weeklist = weekRepo.findByWeeklyYearAndMonth(yearid, monthid);

		if (weeklist == null || weeklist.isEmpty()) {

			Calendar cal = Calendar.getInstance();
			cal.set(yearid, monthid, 1);
			int maxWeeknumber = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);

			for (int i = 1; i <= maxWeeknumber; i++) {
				weekly_table week = new weekly_table();
				week.setWeekly_date(new java.util.Date());
				week.setWeekly_year(yearid);
				week.setWeekly_month(monthid);
				week.setWeekly_mdesc(msg.getMessage("month" + monthid, null, Locale.getDefault()));
				week.setWeekly_week(i);
				week.setCase_sync(sync.getSync_code());

				week.setWeekly_id(Integer.valueOf(yearid + "" + monthid + "" + week.getWeekly_week()));

				List<weekly_monitoring> weekbuider = new ArrayList<>();

				for (monitoring_table elem : monRepo.findAll()) {

					weekly_monitoring wm = new weekly_monitoring();
					wm.setId(new wmPK(week.getWeekly_id(), elem.getMindex()));
					wm.setWm_indices(elem);
					wm.setWm_grids(week);
					wm.setWm_values(0);
					wm.setWm_subval(0);
					wm.setData_sent(0);//reset the data sending indicator when any record is edited

					weekbuider.add(wm);

				}

				week.setStatistics(weekbuider);

				weeklist.add(week);
			}

			weekRepo.saveAll(weeklist);

		}

		selected.setGrid_weekly(weeklist);
		model.addAttribute("selected", selected);

		List<audit_recommendation> recommendations = new ArrayList<>();
		for (audit_recommendation elem : recRepo.findActionsByMonthYear(yearid, monthid + 1)) {

			if (elem.getRecommendation_status() == 2) {
				elem.setRec_color("bg-success text-white");

			} else if (new java.util.Date().before(elem.getRecommendation_deadline())) {// date passed but not
																						// completed

				if (elem.getRecommendation_status() == 1) {
					elem.setRec_color("bg-warning text-dark");
				} else {
					elem.setRec_color("bg-white text-dark");
				}

			} else {
				elem.setRec_color("bg-danger text-white");
				elem.setBg_color("fw-bold");
			}

			recommendations.add(elem);
		}
		model.addAttribute("ymaction", recommendations);

		return "reporting/report-update";
	}

	@Transactional
	@PostMapping("/edit/{yearid}/{monthid}")
	public String add(Principal principal, Model model, @ModelAttribute("selected") weekgrid selected,
			@PathVariable("yearid") Integer yearid, @PathVariable("monthid") Integer monthid) {

		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		weekRepo.saveAll(selected.getGrid_weekly());

		return "redirect:/reporting/edit/" + selected.getGrid_year() + "/" + selected.getGrid_month();
	}

	@GetMapping("/search")
	public String search(Principal principal, Model model) {

		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";

		}

		model.addAttribute("selected", new wmsearch());
		model.addAttribute("oavg", new wmoindicators());

		return "reporting/report-search";
	}

	@PostMapping("/search")
	public String search(Principal principal, Model model, @ModelAttribute("selected") wmsearch search) {

		if (!syncRepo.findById(CONSTANTS.FACILITY_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";

		}

		Integer startYM = ((search.getWm_startyear() % 2000) * 12) + search.getWm_startmonth();
		Integer endYM = ((search.getWm_endyear() % 2000) * 12) + search.getWm_endmonth();

		Double totaldeliveries = 0.0, totaldelvaginal = 0.0, totaldelassisted = 0.0, totaldelcaesarean = 0.0,

				totalbirths = 0.0, totalstillbirth = 0.0, totalintrapartum = 0.0, totalantepartum = 0.0,

				totallivebirths = 0.0, totalpretermbirths = 0.0, totallowbirthwgt = 0.0,

				totalneondeaths = 0.0, totalneondeaths_e = 0.0, totalneondeaths_l = 0.0;

		Integer totalmaternaldeaths = 0;

		List<String[]> data = wmRepo.findAllRates(startYM, endYM);

		final String[] yearmonth = new String[data.size()];
		final Double[] isbr_array = new Double[data.size()];
		final Double[] iisbr_array = new Double[data.size()];
		final Double[] aisbr_array = new Double[data.size()];
		final Double[] piisbr_array = new Double[data.size()];
		final Double[] einmr_array = new Double[data.size()];
		final Double[] linmr_array = new Double[data.size()];
		final Double[] ipmr_array = new Double[data.size()];
		final Double[] inmr_array = new Double[data.size()];
		final Double[] immr_array = new Double[data.size()];
		final Double[] icsr_array = new Double[data.size()];
		final Double[] iadr_array = new Double[data.size()];
		final Double[] ivdr_array = new Double[data.size()];
		final Double[] ilbwr_array = new Double[data.size()];
		final Double[] iptbr_array = new Double[data.size()];
		final Double[] indwk1_array = new Double[data.size()];
		final Integer[] mdeath_array = new Integer[data.size()];

		List<wmindicators> indicators = new ArrayList<>();
		int arrayIndex = 0;
		for (String[] elem : data)// startYM, endYM
		{
			wmindicators i = new wmindicators();
			i.setWyear((Integer.valueOf(elem[0])));
			i.setWmonth(Integer.valueOf(elem[1]));
			i.setWmdesc(elem[2]);
			yearmonth[arrayIndex] = i.getWmdesc() + "-" + i.getWyear();

			i.setIsbr(
					(Double.valueOf(elem[7]) == 0.0) ? 0 : (Double.valueOf(elem[8]) / Double.valueOf(elem[7])) * 1000);
			isbr_array[arrayIndex] = Math.round(i.getIsbr() * 10.0) / 10.0;

			i.setIisbr(
					(Double.valueOf(elem[7]) == 0.0) ? 0 : (Double.valueOf(elem[9]) / Double.valueOf(elem[7])) * 1000);
			iisbr_array[arrayIndex] = Math.round(i.getIisbr() * 10.0) / 10.0;

			i.setAisbr(i.getIsbr() - i.getIisbr());
			aisbr_array[arrayIndex] = Math.round(i.getAisbr() * 10.0) / 10.0;

			i.setPiisbr((i.getIsbr() == 0.0) ? 0 : (i.getIisbr() / i.getIsbr()) * 100);
			piisbr_array[arrayIndex] = Math.round(i.getPiisbr() * 10.0) / 10.0;

			i.setInmr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[14]) / Double.valueOf(elem[11])) * 1000);
			inmr_array[arrayIndex] = Math.round(i.getInmr() * 10.0) / 10.0;

			i.setIndwk1((Double.valueOf(elem[14]) == 0.0) ? 0
					: (Double.valueOf(elem[15]) / Double.valueOf(elem[14])) * 100);
			indwk1_array[arrayIndex] = Math.round(i.getIndwk1() * 10.0) / 10.0;

			i.setEinmr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[15]) / Double.valueOf(elem[11])) * 1000);
			einmr_array[arrayIndex] = Math.round(i.getEinmr() * 10.0) / 10.0;

			i.setLinmr(i.getInmr() - i.getEinmr());
			linmr_array[arrayIndex] = Math.round(i.getLinmr() * 10.0) / 10.0;

			i.setIpmr((Double.valueOf(elem[11]) == 0.0) ? 0
					: ((Double.valueOf(elem[15]) + Double.valueOf(elem[8])) / Double.valueOf(elem[11])) * 1000);
			ipmr_array[arrayIndex] = Math.round(i.getIpmr() * 10.0) / 10.0;

			i.setImmr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[17]) / Double.valueOf(elem[11])) * 100000);
			immr_array[arrayIndex] = Math.round(i.getImmr() * 10.0) / 10.0;

			i.setIcsr((Double.valueOf(elem[3]) == 0.0) ? 0 : (Double.valueOf(elem[6]) / Double.valueOf(elem[3])) * 100);
			icsr_array[arrayIndex] = Math.round(i.getIcsr() * 10.0) / 10.0;

			i.setIadr((Double.valueOf(elem[3]) == 0.0) ? 0 : (Double.valueOf(elem[5]) / Double.valueOf(elem[3])) * 100);
			iadr_array[arrayIndex] = Math.round(i.getIadr() * 10.0) / 10.0;

			i.setIvdr((Double.valueOf(elem[3]) == 0.0) ? 0 : (Double.valueOf(elem[4]) / Double.valueOf(elem[3])) * 100);
			ivdr_array[arrayIndex] = Math.round(i.getIvdr() * 10.0) / 10.0;

			i.setIlbwr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[13]) / Double.valueOf(elem[11])) * 100);
			ilbwr_array[arrayIndex] = Math.round(i.getIlbwr() * 10.0) / 10.0;

			i.setIptbr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[12]) / Double.valueOf(elem[11])) * 100);
			iptbr_array[arrayIndex] = Math.round(i.getIptbr() * 10.0) / 10.0;

			i.setMdeath(Integer.valueOf(elem[17]));
			mdeath_array[arrayIndex] = i.getMdeath();

			totaldeliveries += Double.valueOf(elem[3]);
			totaldelvaginal += Double.valueOf(elem[4]);
			totaldelassisted += Double.valueOf(elem[5]);
			totaldelcaesarean += Double.valueOf(elem[6]);

			totalbirths += Double.valueOf(elem[7]);
			totalstillbirth += Double.valueOf(elem[8]);
			totalintrapartum += Double.valueOf(elem[9]);
			totalantepartum += Double.valueOf(elem[10]);

			totallivebirths += Double.valueOf(elem[11]);
			totalpretermbirths += Double.valueOf(elem[12]);
			totallowbirthwgt += Double.valueOf(elem[13]);

			totalneondeaths += Double.valueOf(elem[14]);
			totalneondeaths_e += Double.valueOf(elem[15]);
			totalneondeaths_l += Double.valueOf(elem[16]);

			totalmaternaldeaths += Integer.valueOf(elem[17]);

			indicators.add(i);

			arrayIndex++;

		}
		model.addAttribute("items", indicators);

		// overall averages of data that is pulled
		wmoindicators oindicators = new wmoindicators();
		oindicators.setWmdesc("Overall Averages");

		oindicators.setIsbr_oavg(
				totalbirths == 0 ? 0 : Math.round(((totalstillbirth / totalbirths) * 1000) * 10.0) / 10.0);

		oindicators.setIisbr_oavg(
				totalbirths == 0 ? 0 : Math.round(((totalintrapartum / totalbirths) * 1000) * 10.0) / 10.0);

		oindicators.setAisbr_oavg(oindicators.getIsbr_oavg() - oindicators.getIisbr_oavg());

		oindicators.setPiisbr_oavg(
				Math.round(((oindicators.getIisbr_oavg() / oindicators.getIsbr_oavg()) * 100) * 10.0) / 10.0);

		oindicators.setInmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((totalneondeaths / totallivebirths) * 1000) * 10.0) / 10.0);

		oindicators.setIndwk1_oavg(
				totalneondeaths == 0 ? 0 : Math.round(((totalneondeaths_e / totalneondeaths) * 100) * 10.0) / 10.0);

		oindicators.setEinmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((totalneondeaths_e / totallivebirths) * 1000) * 10.0) / 10.0);

		oindicators.setLinmr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((totalneondeaths_l / totallivebirths) * 1000) * 10.0) / 10.0);

		oindicators.setIpmr_oavg(totalbirths == 0 ? 0
				: Math.round((((totalneondeaths_e + totalstillbirth) / totalbirths) * 1000) * 10.0) / 10.0);

		oindicators.setImmr_oavg(totallivebirths == 0 ? 0
				: Math.round(((totalmaternaldeaths / totallivebirths) * 100000) * 10.0) / 10.0);

		oindicators.setIcsr_oavg(
				totaldeliveries == 0 ? 0 : Math.round(((totaldelcaesarean / totaldeliveries) * 100) * 10.0) / 10.0);

		oindicators.setIadr_oavg(
				totaldeliveries == 0 ? 0 : Math.round(((totaldelassisted / totaldeliveries) * 100) * 10.0) / 10.0);

		oindicators.setIvdr_oavg(
				totaldeliveries == 0 ? 0 : Math.round(((totaldelvaginal / totaldeliveries) * 100) * 10.0) / 10.0);

		oindicators.setIlbwr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((totallowbirthwgt / totallivebirths) * 100) * 10.0) / 10.0);

		oindicators.setIptbr_oavg(
				totallivebirths == 0 ? 0 : Math.round(((totalpretermbirths / totallivebirths) * 100) * 10.0) / 10.0);

		oindicators.setMdeath_osum(totalmaternaldeaths);

		model.addAttribute("oavg", oindicators);

		model.addAttribute("yearmonth_array", yearmonth);
		model.addAttribute("isbr_array", isbr_array);
		model.addAttribute("iisbr_array", iisbr_array);
		model.addAttribute("aisbr_array", aisbr_array);
		model.addAttribute("piisbr_array", piisbr_array);
		model.addAttribute("einmr_array", einmr_array);
		model.addAttribute("linmr_array", linmr_array);
		model.addAttribute("ipmr_array", ipmr_array);
		model.addAttribute("inmr_array", inmr_array);
		model.addAttribute("immr_array", immr_array);
		model.addAttribute("icsr_array", icsr_array);
		model.addAttribute("iadr_array", iadr_array);
		model.addAttribute("ivdr_array", ivdr_array);
		model.addAttribute("ilbwr_array", ilbwr_array);
		model.addAttribute("iptbr_array", iptbr_array);
		model.addAttribute("indwk1_array", indwk1_array);
		model.addAttribute("mdeath_array", mdeath_array);

		return "reporting/report-search";
	}

	@GetMapping("/export/excel/{wyear}/{wmonth}")
	@ResponseBody
	public void exportToExcel(HttpServletResponse response, @PathVariable("wyear") Integer year,
			@PathVariable("wmonth") Integer month) throws IOException {

		List<weekly_table> weeks = weekRepo.findByWeeklyYearAndMonth(year, month);
		if (weeks != null && !weeks.isEmpty()) {
			response.setContentType("application/octet-stream");
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			String currentDateTime = dateFormatter.format(new java.util.Date());

			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=report_" + currentDateTime + ".xlsx";
			response.setHeader(headerKey, headerValue);

			weekly_table week = weeks.get(0);

			ReportExcelExporter excelExporter = new ReportExcelExporter(weeks,
					week.getWeekly_mdesc() + "-" + week.getWeekly_year());

			excelExporter.export(response);
		}
	}

	@ModelAttribute("wmyear_options")
	public Map<Integer, String> wmyearOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Year");
		for (Integer elem : weekRepo.findYears()) {
			map.put(elem, "" + elem);
		}

		return map;
	}

	@ModelAttribute("wmmonth_options")
	public Map<Integer, String> wmmonthOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Month");
		for (Object[] elem : weekRepo.findMonths()) {
			map.put((Integer) elem[0], "" + elem[1]);
		}

		return map;
	}

	@ModelAttribute("iyear_options")
	public Map<Integer, String> importyearOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Year");
		for (int year = 2019; year <= Calendar.getInstance().get(Calendar.YEAR); year++) {
			map.put(year, "" + year);
		}

		return map;
	}

	@ModelAttribute("imonth_options")
	public Map<Integer, String> importmonthOptionsSelectOne() {
		final Map<Integer, String> map = new LinkedHashMap<>();

		map.put(null, "Month");
		map.put(0, "Jan");
		map.put(1, "Feb");
		map.put(2, "Mar");
		map.put(3, "Apr");
		map.put(4, "May");
		map.put(5, "Jun");
		map.put(6, "Jul");
		map.put(7, "Aug");
		map.put(8, "Sep");
		map.put(9, "Oct");
		map.put(10, "Nov");
		map.put(11, "Dec");

		return map;
	}

}// end class