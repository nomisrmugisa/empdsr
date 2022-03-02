package org.pdsr.summary.repo;

import java.util.List;

import org.pdsr.summary.model.SummaryPK;
import org.pdsr.summary.model.big_audit_audit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BigAuditAuditRepository extends JpaRepository<big_audit_audit, SummaryPK> {

	@Query("select t.audit_icdpm, count(t.audit_icdpm) FROM big_audit_audit t WHERE t.audit_death=1"
			+ " AND t.summaryPk.country=:country" + " GROUP BY t.audit_icdpm ORDER BY count(t.audit_icdpm) DESC")
	List<String[]> findByTopPMCodesStillBirth(@Param("country") String country, Pageable pageable);

	@Query("select t.audit_icdpm, count(t.audit_icdpm) FROM big_audit_audit t" + " WHERE t.audit_death=1"
			+ " AND t.summaryPk.country=:country AND t.summaryPk.region=:region"
			+ " GROUP BY t.audit_icdpm ORDER BY count(t.audit_icdpm) DESC")
	List<String[]> findByTopPMCodesStillBirth(@Param("country") String country, @Param("region") String region,
			Pageable pageable);

	@Query("select t.audit_icdpm, count(t.audit_icdpm) FROM big_audit_audit t" + " WHERE t.audit_death=1"
			+ " AND t.summaryPk.country=:country AND t.summaryPk.region=:region AND t.summaryPk.district=:district"
			+ " GROUP BY t.audit_icdpm ORDER BY count(t.audit_icdpm) DESC")
	List<String[]> findByTopPMCodesStillBirth(@Param("country") String country, @Param("region") String region,
			@Param("district") String district, Pageable pageable);

	
	
	@Query("select t.audit_icdpm, count(t.audit_icdpm) FROM big_audit_audit t WHERE t.audit_death=1 AND YEAR(t.audit_cdate)=:cyear"
			+ " AND t.summaryPk.country=:country GROUP BY t.audit_icdpm ORDER BY count(t.audit_icdpm) DESC")
	List<String[]> findByTopPMCodesStillBirth(@Param("cyear") Integer year, @Param("country") String country,
			Pageable pageable);

	@Query("select t.audit_icdpm, count(t.audit_icdpm) FROM big_audit_audit t WHERE t.audit_death=1 AND YEAR(t.audit_cdate)=:cyear"
			+ " AND t.summaryPk.country=:country AND t.summaryPk.region=:region"
			+ " GROUP BY t.audit_icdpm ORDER BY count(t.audit_icdpm) DESC")
	List<String[]> findByTopPMCodesStillBirth(@Param("cyear") Integer year, @Param("country") String country,
			@Param("region") String region, Pageable pageable);

	@Query("select t.audit_icdpm, count(t.audit_icdpm) FROM big_audit_audit t WHERE t.audit_death=1 AND YEAR(t.audit_cdate)=:cyear "
			+ " AND t.summaryPk.country=:country AND t.summaryPk.region=:region AND t.summaryPk.district=:district"
			+ " GROUP BY t.audit_icdpm ORDER BY count(t.audit_icdpm) DESC")
	List<String[]> findByTopPMCodesStillBirth(@Param("cyear") Integer year, @Param("country") String country,
			@Param("region") String region, @Param("district") String district, Pageable pageable);


	
	@Query("select t.audit_icdpm, count(t.audit_icdpm) FROM big_audit_audit t" + " WHERE t.audit_death>1"
			+ " AND t.summaryPk.country=:country" + " GROUP BY t.audit_icdpm ORDER BY count(t.audit_icdpm) DESC")
	List<String[]> findByTopPMCodesNeonatal(@Param("country") String country, Pageable pageable);

	@Query("select t.audit_icdpm, count(t.audit_icdpm) FROM big_audit_audit t" + " WHERE t.audit_death>1"
			+ " AND t.summaryPk.country=:country AND t.summaryPk.region=:region"
			+ " GROUP BY t.audit_icdpm ORDER BY count(t.audit_icdpm) DESC")
	List<String[]> findByTopPMCodesNeonatal(@Param("country") String country, @Param("region") String region,
			Pageable pageable);

	@Query("select t.audit_icdpm, count(t.audit_icdpm) FROM big_audit_audit t" + " WHERE t.audit_death>1"
			+ " AND t.summaryPk.country=:country AND t.summaryPk.region=:region AND t.summaryPk.district=:district"
			+ " GROUP BY t.audit_icdpm ORDER BY count(t.audit_icdpm) DESC")
	List<String[]> findByTopPMCodesNeonatal(@Param("country") String country, @Param("region") String region,
			@Param("district") String district, Pageable pageable);

	
	@Query("select t.audit_icdpm, count(t.audit_icdpm) FROM big_audit_audit t WHERE t.audit_death>1 AND YEAR(t.audit_cdate)=:cyear "
			+ " AND t.summaryPk.country=:country" + " GROUP BY t.audit_icdpm ORDER BY count(t.audit_icdpm) DESC")
	List<String[]> findByTopPMCodesNeonatal(@Param("cyear") Integer year, @Param("country") String country,
			Pageable pageable);

	@Query("select t.audit_icdpm, count(t.audit_icdpm) FROM big_audit_audit t WHERE t.audit_death>1 AND YEAR(t.audit_cdate)=:cyear "
			+ " AND t.summaryPk.country=:country AND t.summaryPk.region=:region"
			+ " GROUP BY t.audit_icdpm ORDER BY count(t.audit_icdpm) DESC")
	List<String[]> findByTopPMCodesNeonatal(@Param("cyear") Integer year, @Param("country") String country,
			@Param("region") String region, Pageable pageable);

	@Query("select t.audit_icdpm, count(t.audit_icdpm) FROM big_audit_audit t WHERE t.audit_death>1 AND YEAR(t.audit_cdate)=:cyear "
			+ " AND t.summaryPk.country=:country AND t.summaryPk.region=:region AND t.summaryPk.district=:district"
			+ " GROUP BY t.audit_icdpm ORDER BY count(t.audit_icdpm) DESC")
	List<String[]> findByTopPMCodesNeonatal(@Param("cyear") Integer year, @Param("country") String country,
			@Param("region") String region, @Param("district") String district, Pageable pageable);	

}
