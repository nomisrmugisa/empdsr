package org.pdsr.slave.repo;

import java.util.List;

import org.pdsr.slave.model.wmPK;
import org.pdsr.slave.model.weekly_monitoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SlaveWeeklyMonitoringTableRepository extends JpaRepository<weekly_monitoring, wmPK> {

	@Query("SELECT w.wm_grids.weekly_year, w.wm_grids.weekly_month, w.wm_grids.weekly_mdesc, "
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 100) THEN w.wm_values ELSE 0 END) AS totaldeliveries, "// 3
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 101) THEN w.wm_values ELSE 0 END) AS totaldelvaginal, "// 4
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 102) THEN w.wm_values ELSE 0 END) AS totaldelassisted, "// 5
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 103) THEN w.wm_values ELSE 0 END) AS totaldelcaesarean, "// 6

			+ "SUM(CASE WHEN (w.wm_indices.mindex = 110) THEN w.wm_values ELSE 0 END) AS totalbirths, "// 7

			+ "SUM(CASE WHEN (w.wm_indices.mindex = 120) THEN w.wm_values ELSE 0 END) AS totalstillbirth, "// 8
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 121) THEN w.wm_values ELSE 0 END) AS totalintrapartum, "// 9
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 122) THEN w.wm_values ELSE 0 END) AS totalantepartum, "// 10

			+ "SUM(CASE WHEN (w.wm_indices.mindex = 130) THEN w.wm_values ELSE 0 END) AS totallivebirths, "// 11
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 132) THEN w.wm_values ELSE 0 END) AS totalpretermbirths, "// 12
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 137) THEN w.wm_values ELSE 0 END) AS totallowbirthwgt, "// 13

			+ "SUM(CASE WHEN (w.wm_indices.mindex = 150) THEN w.wm_values ELSE 0 END) AS totalneondeaths, "// 14
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 151) THEN w.wm_values ELSE 0 END) AS totalneondeaths_e, "// 15
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 152) THEN w.wm_values ELSE 0 END) AS totalneondeaths_l, "// 16

			+ "SUM(CASE WHEN (w.wm_indices.mindex = 161) THEN w.wm_values ELSE 0 END) AS totalmaternaldeaths "// 17

			+ "FROM weekly_monitoring w "
			+ "WHERE ((MOD(w.wm_grids.weekly_year, 2000)*12) + w.wm_grids.weekly_month) BETWEEN ?1 AND ?2 "
			+ "GROUP BY w.wm_grids.weekly_year, w.wm_grids.weekly_month, w.wm_grids.weekly_mdesc "
			+ "ORDER BY w.wm_grids.weekly_year, w.wm_grids.weekly_month")
	List<String[]> findAllRates(Integer startyearmonth, Integer endyearmonth);

	@Query("SELECT " + "SUM(CASE WHEN (w.wm_indices.mindex = 100) THEN w.wm_values ELSE 0 END) AS totaldeliveries, "// 3
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 101) THEN w.wm_values ELSE 0 END) AS totaldelvaginal, "// 4
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 102) THEN w.wm_values ELSE 0 END) AS totaldelassisted, "// 5
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 103) THEN w.wm_values ELSE 0 END) AS totaldelcaesarean, "// 6

			+ "SUM(CASE WHEN (w.wm_indices.mindex = 110) THEN w.wm_values ELSE 0 END) AS totalbirths, "// 7

			+ "SUM(CASE WHEN (w.wm_indices.mindex = 120) THEN w.wm_values ELSE 0 END) AS totalstillbirth, "// 8
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 121) THEN w.wm_values ELSE 0 END) AS totalintrapartum, "// 9
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 122) THEN w.wm_values ELSE 0 END) AS totalantepartum, "// 10

			+ "SUM(CASE WHEN (w.wm_indices.mindex = 130) THEN w.wm_values ELSE 0 END) AS totallivebirths, "// 11
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 132) THEN w.wm_values ELSE 0 END) AS totalpretermbirths, "// 12
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 137) THEN w.wm_values ELSE 0 END) AS totallowbirthwgt, "// 13

			+ "SUM(CASE WHEN (w.wm_indices.mindex = 150) THEN w.wm_values ELSE 0 END) AS totalneondeaths, "// 14
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 151) THEN w.wm_values ELSE 0 END) AS totalneondeaths_e, "// 15
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 152) THEN w.wm_values ELSE 0 END) AS totalneondeaths_l, "// 16

			+ "SUM(CASE WHEN (w.wm_indices.mindex = 161) THEN w.wm_values ELSE 0 END) AS totalmaternaldeaths "// 17

			+ "FROM weekly_monitoring w")
	List<String[]> findFrontPageRates();

	@Query("SELECT " + "SUM(CASE WHEN (w.wm_indices.mindex = 100) THEN w.wm_values ELSE 0 END) AS totaldeliveries, "// 0
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 101) THEN w.wm_values ELSE 0 END) AS totaldelvaginal, "// 1
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 102) THEN w.wm_values ELSE 0 END) AS totaldelassisted, "// 2
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 103) THEN w.wm_values ELSE 0 END) AS totaldelcaesarean, "// 3

			+ "SUM(CASE WHEN (w.wm_indices.mindex = 110) THEN w.wm_values ELSE 0 END) AS totalbirths, "// 4

			+ "SUM(CASE WHEN (w.wm_indices.mindex = 120) THEN w.wm_values ELSE 0 END) AS totalstillbirth, "// 5
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 121) THEN w.wm_values ELSE 0 END) AS totalintrapartum, "// 6
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 122) THEN w.wm_values ELSE 0 END) AS totalantepartum, "// 7

			+ "SUM(CASE WHEN (w.wm_indices.mindex = 130) THEN w.wm_values ELSE 0 END) AS totallivebirths, "// 8
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 132) THEN w.wm_values ELSE 0 END) AS totalpretermbirths, "// 9
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 137) THEN w.wm_values ELSE 0 END) AS totallowbirthwgt, "// 10

			+ "SUM(CASE WHEN (w.wm_indices.mindex = 150) THEN w.wm_values ELSE 0 END) AS totalneondeaths, "// 11
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 151) THEN w.wm_values ELSE 0 END) AS totalneondeaths_e, "// 12
			+ "SUM(CASE WHEN (w.wm_indices.mindex = 152) THEN w.wm_values ELSE 0 END) AS totalneondeaths_l, "// 13

			+ "SUM(CASE WHEN (w.wm_indices.mindex = 161) THEN w.wm_values ELSE 0 END) AS totalmaternaldeaths "// 14

			+ "FROM weekly_monitoring w WHERE w.wm_grids.weekly_year=?1")
	List<String[]> findFrontPageRates(Integer year);

}// end class
