package org.pdsr.repo;

import java.util.List;

import org.pdsr.model.audit_audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditAuditRepository extends JpaRepository<audit_audit, String> {
	
	@Query("select t FROM audit_audit t LEFT JOIN audit_recommendation r ON(t.audit_uuid=r.audit_uuid) WHERE r.recommendation_uuid IS NULL")
	List<audit_audit> findByPendingRecommendation();


}
