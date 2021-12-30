package org.pdsr.repo;

import java.util.List;

import org.pdsr.model.weekly_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyTableRepository extends JpaRepository<weekly_table, Integer> {

	@Query("select w.weekly_year, w.weekly_month, w.weekly_mdesc, w.weekly_date FROM weekly_table w GROUP BY w.weekly_year, w.weekly_month ORDER BY w.weekly_year, w.weekly_month")
	List<Object[]> findAllWeeklyYearAndMonth();

	@Query("select w FROM weekly_table w WHERE w.weekly_year=?1 AND w.weekly_month=?2")
	List<weekly_table> findByWeeklyYearAndMonth(Integer yearid, Integer monthid);

}
