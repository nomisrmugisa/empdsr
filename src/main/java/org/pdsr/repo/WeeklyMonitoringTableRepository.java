package org.pdsr.repo;

import java.util.List;

import org.pdsr.model.weekly_monitoring;
import org.pdsr.model.wmPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyMonitoringTableRepository extends JpaRepository<weekly_monitoring, wmPK> {

	
			@Query("SELECT w.wm_grids.weekly_year, w.wm_grids.weekly_month, w.wm_grids.weekly_mdesc, "
					+ "SUM(CASE WHEN (w.wm_indices.mindex = 100) THEN w.wm_values ELSE 0 END) AS totaldeliveries, "//3
					+ "SUM(CASE WHEN (w.wm_indices.mindex = 101) THEN w.wm_values ELSE 0 END) AS totaldelvaginal, "//4
					+ "SUM(CASE WHEN (w.wm_indices.mindex = 102) THEN w.wm_values ELSE 0 END) AS totaldelassisted, "//5
					+ "SUM(CASE WHEN (w.wm_indices.mindex = 103) THEN w.wm_values ELSE 0 END) AS totaldelcaesarean, "//6

					+ "SUM(CASE WHEN (w.wm_indices.mindex = 110) THEN w.wm_values ELSE 0 END) AS totalbirths, "//7

					+ "SUM(CASE WHEN (w.wm_indices.mindex = 120) THEN w.wm_values ELSE 0 END) AS totalstillbirth, "//8
					+ "SUM(CASE WHEN (w.wm_indices.mindex = 121) THEN w.wm_values ELSE 0 END) AS totalintrapartum, "//9
					+ "SUM(CASE WHEN (w.wm_indices.mindex = 122) THEN w.wm_values ELSE 0 END) AS totalantepartum, "//10

					+ "SUM(CASE WHEN (w.wm_indices.mindex = 130) THEN w.wm_values ELSE 0 END) AS totallivebirths, "//11
					+ "SUM(CASE WHEN (w.wm_indices.mindex = 132) THEN w.wm_values ELSE 0 END) AS totalpretermbirths, "//12
					+ "SUM(CASE WHEN (w.wm_indices.mindex = 137) THEN w.wm_values ELSE 0 END) AS totallowbirthwgt, "//13

					+ "SUM(CASE WHEN (w.wm_indices.mindex = 150) THEN w.wm_values ELSE 0 END) AS totalneondeaths, "//14
					+ "SUM(CASE WHEN (w.wm_indices.mindex = 151) THEN w.wm_values ELSE 0 END) AS totalneondeaths_e, "//15
					+ "SUM(CASE WHEN (w.wm_indices.mindex = 152) THEN w.wm_values ELSE 0 END) AS totalneondeaths_l, "//16

					+ "SUM(CASE WHEN (w.wm_indices.mindex = 161) THEN w.wm_values ELSE 0 END) AS totalmaternaldeaths "//17

					+ "FROM weekly_monitoring w "
					//+ "WHERE (w.wm_grids.weekly_year + w.wm_grids.weekly_month) BETWEEN ?1 AND ?2 "
					+ "GROUP BY w.wm_grids.weekly_year, w.wm_grids.weekly_month, w.wm_grids.weekly_mdesc "
					+ "ORDER BY w.wm_grids.weekly_year, w.wm_grids.weekly_month")
					List<String[]> findAllRates(Integer startyearmonth, Integer endyearmonth);


}//end class




/************************************************************************************************
Query("SELECT w.wm_grids.weekly_year, w.wm_grids.weekly_month, w.wm_grids.weekly_mdesc, "
		+ "SUM(CASE WHEN (w.wm_indices.mindex = 100) THEN w.wm_values ELSE 0 END) AS totaldeliveries, "//3
		+ "SUM(CASE WHEN (w.wm_indices.mindex = 101) THEN w.wm_values ELSE 0 END) AS totaldelvaginal, "//4
		+ "SUM(CASE WHEN (w.wm_indices.mindex = 102) THEN w.wm_values ELSE 0 END) AS totaldelassisted, "//5
		+ "SUM(CASE WHEN (w.wm_indices.mindex = 103) THEN w.wm_values ELSE 0 END) AS totaldelcaesarean, "//6

		+ "SUM(CASE WHEN (w.wm_indices.mindex = 110) THEN w.wm_values ELSE 0 END) AS totalbirths, "//7

		+ "SUM(CASE WHEN (w.wm_indices.mindex = 120) THEN w.wm_values ELSE 0 END) AS totalstillbirth, "//8
		+ "SUM(CASE WHEN (w.wm_indices.mindex = 121) THEN w.wm_values ELSE 0 END) AS totalintrapartum, "//9
		+ "SUM(CASE WHEN (w.wm_indices.mindex = 122) THEN w.wm_values ELSE 0 END) AS totalantepartum, "//10

		+ "SUM(CASE WHEN (w.wm_indices.mindex = 130) THEN w.wm_values ELSE 0 END) AS totallivebirths, "//11
		+ "SUM(CASE WHEN (w.wm_indices.mindex = 132) THEN w.wm_values ELSE 0 END) AS totalpretermbirths, "//12
		+ "SUM(CASE WHEN (w.wm_indices.mindex = 137) THEN w.wm_values ELSE 0 END) AS totallowbirthwgt, "//13

		+ "SUM(CASE WHEN (w.wm_indices.mindex = 150) THEN w.wm_values ELSE 0 END) AS totalneondeaths, "//14
		+ "SUM(CASE WHEN (w.wm_indices.mindex = 151) THEN w.wm_values ELSE 0 END) AS totalneondeaths_e, "//15
		+ "SUM(CASE WHEN (w.wm_indices.mindex = 152) THEN w.wm_values ELSE 0 END) AS totalneondeaths_l, "//16

		+ "SUM(CASE WHEN (w.wm_indices.mindex = 161) THEN w.wm_values ELSE 0 END) AS totalmaternaldeaths, "//17

		+ "((totalstillbirth/totalbirths)*1000) AS isbr,  " //18 (8/7)
		+ "((totalintrapartum/totalbirths)*1000) AS iisbr,  "//19 (9/7)
		+ "(isbr-iisbr) AS aisbr,  " //20
		+ "(iisbr/isbr) AS piisbr,  "//21
		+ "((totalneondeaths_e/totallivebirths)*1000) AS einmr, "//22 (15/11)
		+ "(((totalneondeaths_e + totalstillbirth)/totallivebirths)*1000) AS ipmr, "//23 (15+8)/11
		+ "((totalneondeaths/totallivebirths)*1000) AS inmr, "//24 (14/11)
		+ "((totalmaternaldeaths/totallivebirths)*100000) AS immr, "//25 (17/11)
		+ "(totaldelcaesarean/totaldeliveries) AS icsr, " //26 (6/3)
		+ "(totaldelassisted/totaldeliveries) AS iadr, "//27 (5/3)
		+ "(totallowbirthwgt/totallivebirths) AS ilbwr, " //28 (13/11)
		+ "(totalpretermbirths/totallivebirths) AS iptbr, " //29 (12/11)
		+ "(totalneondeaths_e/totalneondeaths) AS indwk1 " //30 (15/14)
		+ "FROM weekly_monitoring w "
		//+ "WHERE (w.wm_grids.weekly_year + w.wm_grids.weekly_month) BETWEEN ?1 AND ?2 "
		+ "GROUP BY w.wm_grids.weekly_year, w.wm_grids.weekly_month, w.wm_grids.weekly_mdesc "
		+ "ORDER BY w.wm_grids.weekly_year, w.wm_grids.weekly_month")
		List<Object[]> findAllRates();//Integer startyearmonth, Integer endyearmonth);
		*************************************************************************************/

