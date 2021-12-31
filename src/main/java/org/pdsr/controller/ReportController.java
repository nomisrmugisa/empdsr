package org.pdsr.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.transaction.Transactional;

import org.pdsr.CONSTANTS;
import org.pdsr.model.monitoring_table;
import org.pdsr.model.weekly_monitoring;
import org.pdsr.model.weekly_table;
import org.pdsr.model.wmPK;
import org.pdsr.pojos.weekgrid;
import org.pdsr.pojos.wmindicators;
import org.pdsr.pojos.wmsearch;
import org.pdsr.repo.MonitoringTableRepository;
import org.pdsr.repo.SyncTableRepository;
import org.pdsr.repo.WeeklyMonitoringTableRepository;
import org.pdsr.repo.WeeklyTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	private MessageSource msg;

	@GetMapping("")
	public String list(Principal principal, Model model) {

		if (syncRepo.findById(CONSTANTS.FACILITY_ID).isEmpty()) {
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

		return "reporting/report-retrieve";
	}

	@GetMapping("/edit/{yearid}/{monthid}")
	public String add(Principal principal, Model model, @PathVariable("yearid") Integer yearid,
			@PathVariable("monthid") Integer monthid) {

		if (syncRepo.findById(CONSTANTS.FACILITY_ID).isEmpty()) {
			model.addAttribute("activated", "0");
			return "home";
		}

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

				week.setWeekly_id(Integer.valueOf(yearid + "" + monthid + "" + week.getWeekly_week()));

				List<weekly_monitoring> weekbuider = new ArrayList<>();

				for (monitoring_table elem : monRepo.findAll()) {

					weekly_monitoring wm = new weekly_monitoring();
					wm.setId(new wmPK(week.getWeekly_id(), elem.getMindex()));
					wm.setWm_indices(elem);
					wm.setWm_grids(week);
					wm.setWm_values(0);
					wm.setWm_subval(0);

					weekbuider.add(wm);

				}

				week.setStatistics(weekbuider);

				weeklist.add(week);
			}

			weekRepo.saveAll(weeklist);

		}

		selected.setGrid_weekly(weeklist);
		model.addAttribute("selected", selected);

		return "reporting/report-update";
	}

	@Transactional
	@PostMapping("/edit/{yearid}/{monthid}")
	public String add(Principal principal, Model model, @ModelAttribute("selected") weekgrid selected,
			@PathVariable("yearid") Integer yearid, @PathVariable("monthid") Integer monthid) {

		if (syncRepo.findById(CONSTANTS.FACILITY_ID).isEmpty()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		weekRepo.saveAll(selected.getGrid_weekly());

		return "redirect:/reporting/edit/" + selected.getGrid_year() + "/" + selected.getGrid_month();
	}

	@GetMapping("/search")
	public String search(Principal principal, Model model) {

		if (syncRepo.findById(CONSTANTS.FACILITY_ID).isEmpty()) {
			model.addAttribute("activated", "0");
			return "home";

		}

		model.addAttribute("selected", new wmsearch());

		return "reporting/report-search";
	}

	@PostMapping("/search")
	public String search(Principal principal, Model model, @ModelAttribute("selected") wmsearch search) {

		if (syncRepo.findById(CONSTANTS.FACILITY_ID).isEmpty()) {
			model.addAttribute("activated", "0");
			return "home";

		}

		Integer startYM = search.getWm_startyear() + search.getWm_startmonth();
		Integer endYM = search.getWm_endyear() + search.getWm_endmonth();

		List<wmindicators> indicators = new ArrayList<>();
		for (String[] elem : wmRepo.findAllRates(startYM, endYM))// startYM, endYM
		{
			wmindicators i = new wmindicators();
			i.setWyear((Integer.valueOf(elem[0])));
			i.setWmonth(Integer.valueOf(elem[1]));
			i.setWmdesc(elem[2]);

			
			i.setIsbr((Double.valueOf(elem[7]) == 0.0) ? 0 : (Double.valueOf(elem[8]) / Double.valueOf(elem[7])) * 1000.0);
			i.setIisbr((Double.valueOf(elem[7]) == 0.0) ? 0 : (Double.valueOf(elem[9]) / Double.valueOf(elem[7])) * 1000.0);
			i.setAisbr(i.getIsbr() - i.getIisbr());
			i.setPiisbr(i.getIisbr() / i.getIsbr());
			i.setEinmr((Double.valueOf(elem[11]) == 0.0) ? 0 : (Double.valueOf(elem[15]) / Double.valueOf(elem[11])) * 1000.0);
			i.setIpmr((Double.valueOf(elem[11]) == 0.0) ? 0 : ((Double.valueOf(elem[15])+Double.valueOf(elem[8])) / Double.valueOf(elem[11])) * 1000.0);
			i.setInmr((Double.valueOf(elem[11]) == 0.0) ? 0 : (Double.valueOf(elem[14]) / Double.valueOf(elem[11])) * 1000.0);
			i.setImmr((Double.valueOf(elem[11]) == 0.0) ? 0 : (Double.valueOf(elem[17]) / Double.valueOf(elem[11])) * 100000.0);
			i.setIcsr((Double.valueOf(elem[3]) == 0.0) ? 0 : (Double.valueOf(elem[6]) / Double.valueOf(elem[3])));
			i.setIadr((Double.valueOf(elem[3]) == 0.0) ? 0 : (Double.valueOf(elem[5]) / Double.valueOf(elem[3])));
			i.setIlbwr((Double.valueOf(elem[11]) == 0.0) ? 0 : (Double.valueOf(elem[13]) / Double.valueOf(elem[11])));
			i.setIptbr((Double.valueOf(elem[11]) == 0.0) ? 0 : (Double.valueOf(elem[12]) / Double.valueOf(elem[11])));
			i.setIndwk1((Double.valueOf(elem[14]) == 0.0) ? 0 : (Double.valueOf(elem[15]) / Double.valueOf(elem[14])));
			i.setMdeath(Integer.valueOf(elem[17]));

			indicators.add(i);

		}
		model.addAttribute("items", indicators);

		return "reporting/report-search";
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

}// end class