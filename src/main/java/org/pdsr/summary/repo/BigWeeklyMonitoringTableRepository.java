package org.pdsr.summary.repo;

import java.util.List;

import org.pdsr.summary.model.SummaryPK;
import org.pdsr.summary.model.big_weekly_monitoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BigWeeklyMonitoringTableRepository extends JpaRepository<big_weekly_monitoring, SummaryPK> {

	@Query("select DISTINCT w.weekly_year FROM big_weekly_monitoring w ORDER BY w.weekly_year")
	List<Integer> findYears();

	@Query("select DISTINCT w.weekly_month, w.weekly_mdesc FROM big_weekly_monitoring w ORDER BY w.weekly_month")
	List<Object[]> findMonths();

	@Query("SELECT w.summaryPk.country, w.summaryPk.region, w.summaryPk.region, "
			+ "SUM(CASE WHEN (w.mindex = 100) THEN w.wm_values ELSE 0 END) AS totaldeliveries, "// 3
			+ "SUM(CASE WHEN (w.mindex = 101) THEN w.wm_values ELSE 0 END) AS totaldelvaginal, "// 4
			+ "SUM(CASE WHEN (w.mindex = 102) THEN w.wm_values ELSE 0 END) AS totaldelassisted, "// 5
			+ "SUM(CASE WHEN (w.mindex = 103) THEN w.wm_values ELSE 0 END) AS totaldelcaesarean, "// 6

			+ "SUM(CASE WHEN (w.mindex = 110) THEN w.wm_values ELSE 0 END) AS totalbirths, "// 7

			+ "SUM(CASE WHEN (w.mindex = 120) THEN w.wm_values ELSE 0 END) AS totalstillbirth, "// 8
			+ "SUM(CASE WHEN (w.mindex = 121) THEN w.wm_values ELSE 0 END) AS totalintrapartum, "// 9
			+ "SUM(CASE WHEN (w.mindex = 122) THEN w.wm_values ELSE 0 END) AS totalantepartum, "// 10

			+ "SUM(CASE WHEN (w.mindex = 130) THEN w.wm_values ELSE 0 END) AS totallivebirths, "// 11
			+ "SUM(CASE WHEN (w.mindex = 132) THEN w.wm_values ELSE 0 END) AS totalpretermbirths, "// 12
			+ "SUM(CASE WHEN (w.mindex = 137) THEN w.wm_values ELSE 0 END) AS totallowbirthwgt, "// 13

			+ "SUM(CASE WHEN (w.mindex = 150) THEN w.wm_values ELSE 0 END) AS totalneondeaths, "// 14
			+ "SUM(CASE WHEN (w.mindex = 151) THEN w.wm_values ELSE 0 END) AS totalneondeaths_e, "// 15
			+ "SUM(CASE WHEN (w.mindex = 152) THEN w.wm_values ELSE 0 END) AS totalneondeaths_l, "// 16

			+ "SUM(CASE WHEN (w.mindex = 161) THEN w.wm_values ELSE 0 END) AS totalmaternaldeaths "// 17

			+ "FROM big_weekly_monitoring w"
			+ " WHERE ((MOD(w.weekly_year, 2000)*12) + w.weekly_month) BETWEEN ?1 AND ?2"
			+ " AND w.summaryPk.country=?3 GROUP BY w.summaryPk.country, w.summaryPk.region"
			+ " ORDER BY w.summaryPk.country, w.summaryPk.region")
	List<String[]> findAllRates(Integer startyearmonth, Integer endyearmonth, String country);

	@Query("SELECT w.summaryPk.country, w.summaryPk.region, w.summaryPk.district, "
			+ "SUM(CASE WHEN (w.mindex = 100) THEN w.wm_values ELSE 0 END) AS totaldeliveries, "// 3
			+ "SUM(CASE WHEN (w.mindex = 101) THEN w.wm_values ELSE 0 END) AS totaldelvaginal, "// 4
			+ "SUM(CASE WHEN (w.mindex = 102) THEN w.wm_values ELSE 0 END) AS totaldelassisted, "// 5
			+ "SUM(CASE WHEN (w.mindex = 103) THEN w.wm_values ELSE 0 END) AS totaldelcaesarean, "// 6

			+ "SUM(CASE WHEN (w.mindex = 110) THEN w.wm_values ELSE 0 END) AS totalbirths, "// 7

			+ "SUM(CASE WHEN (w.mindex = 120) THEN w.wm_values ELSE 0 END) AS totalstillbirth, "// 8
			+ "SUM(CASE WHEN (w.mindex = 121) THEN w.wm_values ELSE 0 END) AS totalintrapartum, "// 9
			+ "SUM(CASE WHEN (w.mindex = 122) THEN w.wm_values ELSE 0 END) AS totalantepartum, "// 10

			+ "SUM(CASE WHEN (w.mindex = 130) THEN w.wm_values ELSE 0 END) AS totallivebirths, "// 11
			+ "SUM(CASE WHEN (w.mindex = 132) THEN w.wm_values ELSE 0 END) AS totalpretermbirths, "// 12
			+ "SUM(CASE WHEN (w.mindex = 137) THEN w.wm_values ELSE 0 END) AS totallowbirthwgt, "// 13

			+ "SUM(CASE WHEN (w.mindex = 150) THEN w.wm_values ELSE 0 END) AS totalneondeaths, "// 14
			+ "SUM(CASE WHEN (w.mindex = 151) THEN w.wm_values ELSE 0 END) AS totalneondeaths_e, "// 15
			+ "SUM(CASE WHEN (w.mindex = 152) THEN w.wm_values ELSE 0 END) AS totalneondeaths_l, "// 16

			+ "SUM(CASE WHEN (w.mindex = 161) THEN w.wm_values ELSE 0 END) AS totalmaternaldeaths "// 17

			+ "FROM big_weekly_monitoring w"
			+ " WHERE ((MOD(w.weekly_year, 2000)*12) + w.weekly_month) BETWEEN ?1 AND ?2"
			+ " AND w.summaryPk.country=?3 AND w.summaryPk.region=?4"
			+ " GROUP BY w.summaryPk.country, w.summaryPk.region, w.summaryPk.district"
			+ " ORDER BY w.summaryPk.country, w.summaryPk.region, w.summaryPk.district")
	List<String[]> findAllRates(Integer startyearmonth, Integer endyearmonth, String country, String region);

	@Query("SELECT w.summaryPk.country, w.summaryPk.region, w.summaryPk.code, "
			+ "SUM(CASE WHEN (w.mindex = 100) THEN w.wm_values ELSE 0 END) AS totaldeliveries, "// 3
			+ "SUM(CASE WHEN (w.mindex = 101) THEN w.wm_values ELSE 0 END) AS totaldelvaginal, "// 4
			+ "SUM(CASE WHEN (w.mindex = 102) THEN w.wm_values ELSE 0 END) AS totaldelassisted, "// 5
			+ "SUM(CASE WHEN (w.mindex = 103) THEN w.wm_values ELSE 0 END) AS totaldelcaesarean, "// 6

			+ "SUM(CASE WHEN (w.mindex = 110) THEN w.wm_values ELSE 0 END) AS totalbirths, "// 7

			+ "SUM(CASE WHEN (w.mindex = 120) THEN w.wm_values ELSE 0 END) AS totalstillbirth, "// 8
			+ "SUM(CASE WHEN (w.mindex = 121) THEN w.wm_values ELSE 0 END) AS totalintrapartum, "// 9
			+ "SUM(CASE WHEN (w.mindex = 122) THEN w.wm_values ELSE 0 END) AS totalantepartum, "// 10

			+ "SUM(CASE WHEN (w.mindex = 130) THEN w.wm_values ELSE 0 END) AS totallivebirths, "// 11
			+ "SUM(CASE WHEN (w.mindex = 132) THEN w.wm_values ELSE 0 END) AS totalpretermbirths, "// 12
			+ "SUM(CASE WHEN (w.mindex = 137) THEN w.wm_values ELSE 0 END) AS totallowbirthwgt, "// 13

			+ "SUM(CASE WHEN (w.mindex = 150) THEN w.wm_values ELSE 0 END) AS totalneondeaths, "// 14
			+ "SUM(CASE WHEN (w.mindex = 151) THEN w.wm_values ELSE 0 END) AS totalneondeaths_e, "// 15
			+ "SUM(CASE WHEN (w.mindex = 152) THEN w.wm_values ELSE 0 END) AS totalneondeaths_l, "// 16

			+ "SUM(CASE WHEN (w.mindex = 161) THEN w.wm_values ELSE 0 END) AS totalmaternaldeaths,"//17
			+ "w.summaryPk.district"// 18

			+ " FROM big_weekly_monitoring w"
			+ " WHERE ((MOD(w.weekly_year, 2000)*12) + w.weekly_month) BETWEEN ?1 AND ?2"
			+ " AND w.summaryPk.country=?3 AND w.summaryPk.region=?4 AND w.summaryPk.district=?5"
			+ " GROUP BY w.summaryPk.country, w.summaryPk.region, w.summaryPk.district, w.summaryPk.code"
			+ " ORDER BY w.summaryPk.country, w.summaryPk.region, w.summaryPk.district, w.summaryPk.code")
	List<String[]> findAllRates(Integer startyearmonth, Integer endyearmonth, String country, String region,
			String district);

	@Query("SELECT SUM(CASE WHEN (w.mindex = 100) THEN w.wm_values ELSE 0 END) AS totaldeliveries, "// 3
			+ "SUM(CASE WHEN (w.mindex = 101) THEN w.wm_values ELSE 0 END) AS totaldelvaginal, "// 4
			+ "SUM(CASE WHEN (w.mindex = 102) THEN w.wm_values ELSE 0 END) AS totaldelassisted, "// 5
			+ "SUM(CASE WHEN (w.mindex = 103) THEN w.wm_values ELSE 0 END) AS totaldelcaesarean, "// 6

			+ "SUM(CASE WHEN (w.mindex = 110) THEN w.wm_values ELSE 0 END) AS totalbirths, "// 7

			+ "SUM(CASE WHEN (w.mindex = 120) THEN w.wm_values ELSE 0 END) AS totalstillbirth, "// 8
			+ "SUM(CASE WHEN (w.mindex = 121) THEN w.wm_values ELSE 0 END) AS totalintrapartum, "// 9
			+ "SUM(CASE WHEN (w.mindex = 122) THEN w.wm_values ELSE 0 END) AS totalantepartum, "// 10

			+ "SUM(CASE WHEN (w.mindex = 130) THEN w.wm_values ELSE 0 END) AS totallivebirths, "// 11
			+ "SUM(CASE WHEN (w.mindex = 132) THEN w.wm_values ELSE 0 END) AS totalpretermbirths, "// 12
			+ "SUM(CASE WHEN (w.mindex = 137) THEN w.wm_values ELSE 0 END) AS totallowbirthwgt, "// 13

			+ "SUM(CASE WHEN (w.mindex = 150) THEN w.wm_values ELSE 0 END) AS totalneondeaths, "// 14
			+ "SUM(CASE WHEN (w.mindex = 151) THEN w.wm_values ELSE 0 END) AS totalneondeaths_e, "// 15
			+ "SUM(CASE WHEN (w.mindex = 152) THEN w.wm_values ELSE 0 END) AS totalneondeaths_l, "// 16

			+ "SUM(CASE WHEN (w.mindex = 161) THEN w.wm_values ELSE 0 END) AS totalmaternaldeaths "// 17

			+ "FROM big_weekly_monitoring w WHERE w.summaryPk.country=?1 GROUP BY w.summaryPk.country")
	List<String[]> findFrontPageRates(String country);

	@Query("SELECT SUM(CASE WHEN (w.mindex = 100) THEN w.wm_values ELSE 0 END) AS totaldeliveries, "// 3
			+ "SUM(CASE WHEN (w.mindex = 101) THEN w.wm_values ELSE 0 END) AS totaldelvaginal, "// 4
			+ "SUM(CASE WHEN (w.mindex = 102) THEN w.wm_values ELSE 0 END) AS totaldelassisted, "// 5
			+ "SUM(CASE WHEN (w.mindex = 103) THEN w.wm_values ELSE 0 END) AS totaldelcaesarean, "// 6

			+ "SUM(CASE WHEN (w.mindex = 110) THEN w.wm_values ELSE 0 END) AS totalbirths, "// 7

			+ "SUM(CASE WHEN (w.mindex = 120) THEN w.wm_values ELSE 0 END) AS totalstillbirth, "// 8
			+ "SUM(CASE WHEN (w.mindex = 121) THEN w.wm_values ELSE 0 END) AS totalintrapartum, "// 9
			+ "SUM(CASE WHEN (w.mindex = 122) THEN w.wm_values ELSE 0 END) AS totalantepartum, "// 10

			+ "SUM(CASE WHEN (w.mindex = 130) THEN w.wm_values ELSE 0 END) AS totallivebirths, "// 11
			+ "SUM(CASE WHEN (w.mindex = 132) THEN w.wm_values ELSE 0 END) AS totalpretermbirths, "// 12
			+ "SUM(CASE WHEN (w.mindex = 137) THEN w.wm_values ELSE 0 END) AS totallowbirthwgt, "// 13

			+ "SUM(CASE WHEN (w.mindex = 150) THEN w.wm_values ELSE 0 END) AS totalneondeaths, "// 14
			+ "SUM(CASE WHEN (w.mindex = 151) THEN w.wm_values ELSE 0 END) AS totalneondeaths_e, "// 15
			+ "SUM(CASE WHEN (w.mindex = 152) THEN w.wm_values ELSE 0 END) AS totalneondeaths_l, "// 16

			+ "SUM(CASE WHEN (w.mindex = 161) THEN w.wm_values ELSE 0 END) AS totalmaternaldeaths "// 17

			+ "FROM big_weekly_monitoring w WHERE w.summaryPk.country=?1 AND w.summaryPk.region=?2")
	List<String[]> findFrontPageRates(String country, String region);

	@Query("SELECT "
			+ "SUM(CASE WHEN (w.mindex = 100) THEN w.wm_values ELSE 0 END) AS totaldeliveries, "// 3
			+ "SUM(CASE WHEN (w.mindex = 101) THEN w.wm_values ELSE 0 END) AS totaldelvaginal, "// 4
			+ "SUM(CASE WHEN (w.mindex = 102) THEN w.wm_values ELSE 0 END) AS totaldelassisted, "// 5
			+ "SUM(CASE WHEN (w.mindex = 103) THEN w.wm_values ELSE 0 END) AS totaldelcaesarean, "// 6

			+ "SUM(CASE WHEN (w.mindex = 110) THEN w.wm_values ELSE 0 END) AS totalbirths, "// 7

			+ "SUM(CASE WHEN (w.mindex = 120) THEN w.wm_values ELSE 0 END) AS totalstillbirth, "// 8
			+ "SUM(CASE WHEN (w.mindex = 121) THEN w.wm_values ELSE 0 END) AS totalintrapartum, "// 9
			+ "SUM(CASE WHEN (w.mindex = 122) THEN w.wm_values ELSE 0 END) AS totalantepartum, "// 10

			+ "SUM(CASE WHEN (w.mindex = 130) THEN w.wm_values ELSE 0 END) AS totallivebirths, "// 11
			+ "SUM(CASE WHEN (w.mindex = 132) THEN w.wm_values ELSE 0 END) AS totalpretermbirths, "// 12
			+ "SUM(CASE WHEN (w.mindex = 137) THEN w.wm_values ELSE 0 END) AS totallowbirthwgt, "// 13

			+ "SUM(CASE WHEN (w.mindex = 150) THEN w.wm_values ELSE 0 END) AS totalneondeaths, "// 14
			+ "SUM(CASE WHEN (w.mindex = 151) THEN w.wm_values ELSE 0 END) AS totalneondeaths_e, "// 15
			+ "SUM(CASE WHEN (w.mindex = 152) THEN w.wm_values ELSE 0 END) AS totalneondeaths_l, "// 16

			+ "SUM(CASE WHEN (w.mindex = 161) THEN w.wm_values ELSE 0 END) AS totalmaternaldeaths "// 17

			+ "FROM big_weekly_monitoring w"
			+ " WHERE w.summaryPk.country=?1 AND w.summaryPk.region=?2 AND w.summaryPk.district=?3")
	List<String[]> findFrontPageRates(String country, String region, String district);

	@Query("SELECT SUM(CASE WHEN (w.mindex = 100) THEN w.wm_values ELSE 0 END) AS totaldeliveries, "// 0
			+ "SUM(CASE WHEN (w.mindex = 101) THEN w.wm_values ELSE 0 END) AS totaldelvaginal, "// 1
			+ "SUM(CASE WHEN (w.mindex = 102) THEN w.wm_values ELSE 0 END) AS totaldelassisted, "// 2
			+ "SUM(CASE WHEN (w.mindex = 103) THEN w.wm_values ELSE 0 END) AS totaldelcaesarean, "// 3

			+ "SUM(CASE WHEN (w.mindex = 110) THEN w.wm_values ELSE 0 END) AS totalbirths, "// 4

			+ "SUM(CASE WHEN (w.mindex = 120) THEN w.wm_values ELSE 0 END) AS totalstillbirth, "// 5
			+ "SUM(CASE WHEN (w.mindex = 121) THEN w.wm_values ELSE 0 END) AS totalintrapartum, "// 6
			+ "SUM(CASE WHEN (w.mindex = 122) THEN w.wm_values ELSE 0 END) AS totalantepartum, "// 7

			+ "SUM(CASE WHEN (w.mindex = 130) THEN w.wm_values ELSE 0 END) AS totallivebirths, "// 8
			+ "SUM(CASE WHEN (w.mindex = 132) THEN w.wm_values ELSE 0 END) AS totalpretermbirths, "// 9
			+ "SUM(CASE WHEN (w.mindex = 137) THEN w.wm_values ELSE 0 END) AS totallowbirthwgt, "// 10

			+ "SUM(CASE WHEN (w.mindex = 150) THEN w.wm_values ELSE 0 END) AS totalneondeaths, "// 11
			+ "SUM(CASE WHEN (w.mindex = 151) THEN w.wm_values ELSE 0 END) AS totalneondeaths_e, "// 12
			+ "SUM(CASE WHEN (w.mindex = 152) THEN w.wm_values ELSE 0 END) AS totalneondeaths_l, "// 13

			+ "SUM(CASE WHEN (w.mindex = 161) THEN w.wm_values ELSE 0 END) AS totalmaternaldeaths "// 14

			+ "FROM big_weekly_monitoring w WHERE w.weekly_year=?1 AND w.summaryPk.country=?2")
	List<String[]> findFrontPageRates(Integer year, String country);

	@Query("SELECT SUM(CASE WHEN (w.mindex = 100) THEN w.wm_values ELSE 0 END) AS totaldeliveries, "// 0
			+ "SUM(CASE WHEN (w.mindex = 101) THEN w.wm_values ELSE 0 END) AS totaldelvaginal, "// 1
			+ "SUM(CASE WHEN (w.mindex = 102) THEN w.wm_values ELSE 0 END) AS totaldelassisted, "// 2
			+ "SUM(CASE WHEN (w.mindex = 103) THEN w.wm_values ELSE 0 END) AS totaldelcaesarean, "// 3

			+ "SUM(CASE WHEN (w.mindex = 110) THEN w.wm_values ELSE 0 END) AS totalbirths, "// 4

			+ "SUM(CASE WHEN (w.mindex = 120) THEN w.wm_values ELSE 0 END) AS totalstillbirth, "// 5
			+ "SUM(CASE WHEN (w.mindex = 121) THEN w.wm_values ELSE 0 END) AS totalintrapartum, "// 6
			+ "SUM(CASE WHEN (w.mindex = 122) THEN w.wm_values ELSE 0 END) AS totalantepartum, "// 7

			+ "SUM(CASE WHEN (w.mindex = 130) THEN w.wm_values ELSE 0 END) AS totallivebirths, "// 8
			+ "SUM(CASE WHEN (w.mindex = 132) THEN w.wm_values ELSE 0 END) AS totalpretermbirths, "// 9
			+ "SUM(CASE WHEN (w.mindex = 137) THEN w.wm_values ELSE 0 END) AS totallowbirthwgt, "// 10

			+ "SUM(CASE WHEN (w.mindex = 150) THEN w.wm_values ELSE 0 END) AS totalneondeaths, "// 11
			+ "SUM(CASE WHEN (w.mindex = 151) THEN w.wm_values ELSE 0 END) AS totalneondeaths_e, "// 12
			+ "SUM(CASE WHEN (w.mindex = 152) THEN w.wm_values ELSE 0 END) AS totalneondeaths_l, "// 13

			+ "SUM(CASE WHEN (w.mindex = 161) THEN w.wm_values ELSE 0 END) AS totalmaternaldeaths "// 14

			+ "FROM big_weekly_monitoring w WHERE w.weekly_year=?1"
			+ " AND w.summaryPk.country=?2 AND w.summaryPk.region=?3")
	List<String[]> findFrontPageRates(Integer year, String country, String region);

	@Query("SELECT SUM(CASE WHEN (w.mindex = 100) THEN w.wm_values ELSE 0 END) AS totaldeliveries, "// 0
			+ "SUM(CASE WHEN (w.mindex = 101) THEN w.wm_values ELSE 0 END) AS totaldelvaginal, "// 1
			+ "SUM(CASE WHEN (w.mindex = 102) THEN w.wm_values ELSE 0 END) AS totaldelassisted, "// 2
			+ "SUM(CASE WHEN (w.mindex = 103) THEN w.wm_values ELSE 0 END) AS totaldelcaesarean, "// 3

			+ "SUM(CASE WHEN (w.mindex = 110) THEN w.wm_values ELSE 0 END) AS totalbirths, "// 4

			+ "SUM(CASE WHEN (w.mindex = 120) THEN w.wm_values ELSE 0 END) AS totalstillbirth, "// 5
			+ "SUM(CASE WHEN (w.mindex = 121) THEN w.wm_values ELSE 0 END) AS totalintrapartum, "// 6
			+ "SUM(CASE WHEN (w.mindex = 122) THEN w.wm_values ELSE 0 END) AS totalantepartum, "// 7

			+ "SUM(CASE WHEN (w.mindex = 130) THEN w.wm_values ELSE 0 END) AS totallivebirths, "// 8
			+ "SUM(CASE WHEN (w.mindex = 132) THEN w.wm_values ELSE 0 END) AS totalpretermbirths, "// 9
			+ "SUM(CASE WHEN (w.mindex = 137) THEN w.wm_values ELSE 0 END) AS totallowbirthwgt, "// 10

			+ "SUM(CASE WHEN (w.mindex = 150) THEN w.wm_values ELSE 0 END) AS totalneondeaths, "// 11
			+ "SUM(CASE WHEN (w.mindex = 151) THEN w.wm_values ELSE 0 END) AS totalneondeaths_e, "// 12
			+ "SUM(CASE WHEN (w.mindex = 152) THEN w.wm_values ELSE 0 END) AS totalneondeaths_l, "// 13

			+ "SUM(CASE WHEN (w.mindex = 161) THEN w.wm_values ELSE 0 END) AS totalmaternaldeaths "// 14

			+ "FROM big_weekly_monitoring w WHERE w.weekly_year=?1"
			+ " AND w.summaryPk.country=?2 and w.summaryPk.region=?3 AND w.summaryPk.district=?4")
	List<String[]> findFrontPageRates(Integer year, String country, String region, String district);

}// end class
