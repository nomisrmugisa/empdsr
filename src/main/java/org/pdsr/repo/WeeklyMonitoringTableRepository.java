package org.pdsr.repo;

import java.util.List;

import org.pdsr.model.monitoring_table;
import org.pdsr.model.weekly_monitoring;
import org.pdsr.model.wmPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyMonitoringTableRepository extends JpaRepository<weekly_monitoring, wmPK> {
/*
	@Query("SELECT w.wm_grids.weekly_year, w.wm_grids.weekly_month, w.wm_grids.weekly_mdesc, "
			+ "SUM(CASE WHEN (w.wm_indices.gindex = 12) THEN w.wm_values ELSE 0 END) "
			+ "SUM(CASE WHEN (w.wm_indices.gindex = 11) THEN w.wm_values ELSE 0 END) "
			+ "FROM weekly_monitoring w "
			+ "WHERE w.wm_grids.weekly_year=?1 AND w.wm_grids.weekly_month=?2 AND w.wm_indices.gitem=?3"
			+ "GROUP BY w.wm_grids.weekly_year, w.wm_grids.weekly_month, w.wm_grids.weekly_mdesc")
	List<Object[]> findstillBirthRate(Integer wyear, Integer wmonth, boolean ishead);
*/
	
	@Query("SELECT w.wm_grids.weekly_year, w.wm_grids.weekly_month, w.wm_grids.weekly_mdesc, "
			+ "SUM(CASE WHEN (w.wm_indices.gindex = 11) THEN w.wm_values ELSE 0 END) AS totallivebirths, "
			+ "SUM(CASE WHEN (w.wm_indices.gindex = 12) THEN w.wm_values ELSE 0 END) AS totalstillbirth, "
			+ "(SUM(CASE WHEN (w.wm_indices.gindex = 12) THEN w.wm_values ELSE 0 END)*1000/SUM(CASE WHEN (w.wm_indices.gindex = 11) THEN w.wm_values ELSE 0 END)) AS isbr  "
			+ "FROM weekly_monitoring w WHERE w.wm_indices.gitem=1"
			+ "GROUP BY w.wm_grids.weekly_year, w.wm_grids.weekly_month, w.wm_grids.weekly_mdesc")
	List<Object[]> findiSBR();

	@Query("SELECT w.wm_grids.weekly_year, w.wm_grids.weekly_month, w.wm_grids.weekly_mdesc, "
			+ "SUM(CASE WHEN (w.wm_indices.gindex = 11) THEN w.wm_values ELSE 0 END) AS totallivebirths, "
			+ "SUM(CASE WHEN (w.wm_indices.gindex = 12) THEN w.wm_values ELSE 0 END) AS totalstillbirth, "
			+ "(SUM(CASE WHEN (w.wm_indices.gindex = 12) THEN w.wm_values ELSE 0 END)*1000/SUM(CASE WHEN (w.wm_indices.gindex = 11) THEN w.wm_values ELSE 0 END)) AS isbr  "
			+ "FROM weekly_monitoring w WHERE w.wm_indices.gitem=1"
			+ "GROUP BY w.wm_grids.weekly_year, w.wm_grids.weekly_month, w.wm_grids.weekly_mdesc")
	List<Object[]> findAllRates();


}
