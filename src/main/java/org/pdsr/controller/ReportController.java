package org.pdsr.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.pdsr.CONSTANTS;
import org.pdsr.model.monitoring_table;
import org.pdsr.model.weekly_monitoring;
import org.pdsr.model.weekly_table;
import org.pdsr.model.wmPK;
import org.pdsr.pojos.weekgrid;
import org.pdsr.pojos.wmindicators;
import org.pdsr.pojos.wmoindicators;
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

		Integer startYM = ((search.getWm_startyear() % 2000) * 12) + search.getWm_startmonth();
		Integer endYM = ((search.getWm_endyear() % 2000) * 12) + search.getWm_endmonth();

		Double isbr = 0.0, iisbr = 0.0, aisbr = 0.0, piisbr = 0.0, einmr = 0.0, ipmr = 0.0, inmr = 0.0, immr = 0.0,
				icsr = 0.0, iadr = 0.0, ilbwr = 0.0, iptbr = 0.0, indwk1 = 0.0;
		Integer mdeath = 0;

		List<String[]> data = wmRepo.findAllRates(startYM, endYM);

		final String[] yearmonth = new String[data.size()];
		final Double[] isbr_array = new Double[data.size()];
		final Double[] iisbr_array = new Double[data.size()];
		final Double[] aisbr_array = new Double[data.size()];
		final Double[] piisbr_array = new Double[data.size()];
		final Double[] einmr_array = new Double[data.size()];
		final Double[] ipmr_array = new Double[data.size()];
		final Double[] inmr_array = new Double[data.size()];
		final Double[] immr_array = new Double[data.size()];
		final Double[] icsr_array = new Double[data.size()];
		final Double[] iadr_array = new Double[data.size()];
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
			isbr += i.getIsbr();
			isbr_array[arrayIndex] = i.getIsbr();

			i.setIisbr(
					(Double.valueOf(elem[7]) == 0.0) ? 0 : (Double.valueOf(elem[9]) / Double.valueOf(elem[7])) * 1000);
			iisbr += i.getIisbr();
			iisbr_array[arrayIndex] = i.getIisbr();

			i.setAisbr(i.getIsbr() - i.getIisbr());
			aisbr += i.getAisbr();
			aisbr_array[arrayIndex] = i.getAisbr();

			i.setPiisbr((i.getIsbr() == 0.0) ? 0 : (i.getIisbr() / i.getIsbr()) * 100);
			piisbr += i.getPiisbr();
			piisbr_array[arrayIndex] = i.getPiisbr();

			i.setEinmr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[15]) / Double.valueOf(elem[11])) * 1000);
			einmr += i.getEinmr();
			einmr_array[arrayIndex] = i.getEinmr();

			i.setIpmr((Double.valueOf(elem[11]) == 0.0) ? 0
					: ((Double.valueOf(elem[15]) + Double.valueOf(elem[8])) / Double.valueOf(elem[11])) * 1000);
			ipmr += i.getIpmr();
			ipmr_array[arrayIndex] = i.getIpmr();

			i.setInmr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[14]) / Double.valueOf(elem[11])) * 1000);
			inmr += i.getInmr();
			inmr_array[arrayIndex] = i.getInmr();

			i.setImmr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[17]) / Double.valueOf(elem[11])) * 100000);
			immr += i.getImmr();
			immr_array[arrayIndex] = i.getImmr();

			i.setIcsr((Double.valueOf(elem[3]) == 0.0) ? 0 : (Double.valueOf(elem[6]) / Double.valueOf(elem[3])) * 100);
			icsr += i.getIcsr();
			icsr_array[arrayIndex] = i.getIcsr();

			i.setIadr((Double.valueOf(elem[3]) == 0.0) ? 0 : (Double.valueOf(elem[5]) / Double.valueOf(elem[3])) * 100);
			iadr += i.getIadr();
			iadr_array[arrayIndex] = i.getIadr();

			i.setIlbwr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[13]) / Double.valueOf(elem[11])) * 100);
			ilbwr += i.getIlbwr();
			ilbwr_array[arrayIndex] = i.getIlbwr();

			i.setIptbr((Double.valueOf(elem[11]) == 0.0) ? 0
					: (Double.valueOf(elem[12]) / Double.valueOf(elem[11])) * 100);
			iptbr += i.getIptbr();
			iptbr_array[arrayIndex] = i.getIptbr();

			i.setIndwk1((Double.valueOf(elem[14]) == 0.0) ? 0
					: (Double.valueOf(elem[15]) / Double.valueOf(elem[14])) * 100);
			indwk1 += i.getIndwk1();
			indwk1_array[arrayIndex] = i.getIndwk1();

			i.setMdeath(Integer.valueOf(elem[17]));
			mdeath += i.getMdeath();
			mdeath_array[arrayIndex] = i.getMdeath();

			indicators.add(i);

			arrayIndex++;

		}
		model.addAttribute("items", indicators);

		// overall averages of data that is pulled
		wmoindicators oindicators = new wmoindicators();
		oindicators.setWmdesc("Overall Averages");

		oindicators.setIsbr_oavg(isbr / indicators.size());

		oindicators.setIisbr_oavg(iisbr / indicators.size());

		oindicators.setAisbr_oavg(aisbr / indicators.size());

		oindicators.setPiisbr_oavg(piisbr / indicators.size());

		oindicators.setEinmr_oavg(einmr / indicators.size());

		oindicators.setIpmr_oavg(ipmr / indicators.size());

		oindicators.setInmr_oavg(inmr / indicators.size());

		oindicators.setImmr_oavg(immr / indicators.size());

		oindicators.setIcsr_oavg(icsr / indicators.size());

		oindicators.setIadr_oavg(iadr / indicators.size());

		oindicators.setIlbwr_oavg(ilbwr / indicators.size());

		oindicators.setIptbr_oavg(iptbr / indicators.size());

		oindicators.setIndwk1_oavg(indwk1 / indicators.size());

		oindicators.setMdeath_osum(mdeath);

		model.addAttribute("oavg", oindicators);

		model.addAttribute("yearmonth_array", yearmonth);
		model.addAttribute("isbr_array", isbr_array);
		model.addAttribute("iisbr_array", iisbr_array);
		model.addAttribute("aisbr_array", aisbr_array);
		model.addAttribute("piisbr_array", piisbr_array);
		model.addAttribute("einmr_array", einmr_array);
		model.addAttribute("ipmr_array", ipmr_array);
		model.addAttribute("inmr_array", inmr_array);
		model.addAttribute("immr_array", immr_array);
		model.addAttribute("icsr_array", icsr_array);
		model.addAttribute("iadr_array", iadr_array);
		model.addAttribute("ilbwr_array", ilbwr_array);
		model.addAttribute("iptbr_array", iptbr_array);
		model.addAttribute("indwk1_array", indwk1_array);
		model.addAttribute("mdeath_array", mdeath_array);

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