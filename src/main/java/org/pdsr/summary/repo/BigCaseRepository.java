package org.pdsr.summary.repo;


import org.pdsr.summary.model.SummaryPk;
import org.pdsr.summary.model.big_case_identifiers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BigCaseRepository extends JpaRepository<big_case_identifiers, SummaryPk> {

	@Query("select COUNT(c) from big_case_identifiers c where c.case_death=?1" 
	+ " and c.summaryPk.country=?2")
	Integer countByCase_death(Integer case_death, String country);

	@Query("select COUNT(c) from big_case_identifiers c where c.case_death=?1"
			+ " and c.summaryPk.country=?2 and c.summaryPk.region=?3")
	Integer countByCase_death(Integer case_death, String country, String region);

	@Query("select COUNT(c) from big_case_identifiers c where c.case_death=?1"
			+ " and c.summaryPk.country=?2 and c.summaryPk.region=?3 and c.summaryPk.district=?4")
	Integer countByCase_death(Integer case_death, String country, String region, String district);

	@Query("select COUNT(c) from big_case_identifiers c where c.case_death=?1  and year(c.case_date)=?2"
			+ " and c.summaryPk.country=?3")
	Integer countByCase_death(Integer case_death, Integer year, String country);

	@Query("select COUNT(c) from big_case_identifiers c where c.case_death=?1 and year(c.case_date)=?2"
			+ " and c.summaryPk.country=?3 and c.summaryPk.region=?4")
	Integer countByCase_death(Integer case_death, Integer year, String country, String region);

	@Query("select COUNT(c) from big_case_identifiers c where c.case_death=?1 and year(c.case_date)=?2"
			+ " and c.summaryPk.country=?3 and c.summaryPk.region=?4 and c.summaryPk.district=?5")
	Integer countByCase_death(Integer case_death, Integer year, String country, String region, String district);

	@Query("select COUNT(c) from big_case_identifiers c where c.case_status>=?1 AND c.case_death=?2"
			+ " and c.summaryPk.country=?3")
	Integer countByCase_statusAndType(Integer case_status, Integer case_death, String country);

	@Query("select COUNT(c) from big_case_identifiers c where c.case_status>=?1 AND c.case_death=?2"
			+ " and c.summaryPk.country=?3 and c.summaryPk.region=?4")
	Integer countByCase_statusAndType(Integer case_status, Integer case_death, String country, String region);

	@Query("select COUNT(c) from big_case_identifiers c where c.case_status>=?1 AND c.case_death=?2"
			+ " and c.summaryPk.country=?3 and c.summaryPk.region=?4 and c.summaryPk.district=?5")
	Integer countByCase_statusAndType(Integer case_status, Integer case_death, String country, String region,
			String district);

	@Query("select COUNT(c) from big_case_identifiers c where c.case_status>=?1 AND c.case_death=?2 and year(c.case_date)=?3"
			+ " and c.summaryPk.country=?4")
	Integer countByCase_statusAndType(Integer case_status, Integer case_death, Integer year, String country);

	@Query("select COUNT(c) from big_case_identifiers c where c.case_status>=?1 AND c.case_death=?2 and year(c.case_date)=?3"
			+ " and c.summaryPk.country=?4 and c.summaryPk.region=?5")
	Integer countByCase_statusAndType(Integer case_status, Integer case_death, Integer year, String country,
			String region);

	@Query("select COUNT(c) from big_case_identifiers c where c.case_status>=?1 AND c.case_death=?2 and year(c.case_date)=?3"
			+ " and c.summaryPk.country=?4 and c.summaryPk.region=?5 and c.summaryPk.district=?6")
	Integer countByCase_statusAndType(Integer case_status, Integer case_death, Integer year, String country,
			String region, String district);
	
}
