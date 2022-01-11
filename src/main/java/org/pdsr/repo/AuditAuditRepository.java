package org.pdsr.repo;

import java.util.List;

import org.pdsr.model.audit_audit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditAuditRepository extends JpaRepository<audit_audit, String> {

	@Query("select t FROM audit_audit t LEFT JOIN audit_recommendation r ON(t.audit_uuid=r.audit_uuid) WHERE r.recommendation_uuid IS NULL")
	List<audit_audit> findByPendingRecommendation();

	@Query("select t.audit_icdpm, count(t.audit_icdpm) FROM audit_audit t WHERE t.audit_death=:audit_death "
			+ "GROUP BY t.audit_icdpm ORDER BY count(t.audit_icdpm) DESC")
	List<String[]> findByTopPMCodes(@Param("audit_death") Integer audit_death, Pageable pageable);

	@Query("select t.audit_icdpm, count(t.audit_icdpm) FROM audit_audit t WHERE t.audit_death=:audit_death AND YEAR(t.audit_cdate)=:cyear "
			+ "GROUP BY t.audit_icdpm ORDER BY count(t.audit_icdpm) DESC")
	List<String[]> findByTopPMCodes(@Param("audit_death") Integer audit_death, @Param("cyear") Integer year, Pageable pageable);

}
