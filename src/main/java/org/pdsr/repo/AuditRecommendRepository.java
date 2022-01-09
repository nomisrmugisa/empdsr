package org.pdsr.repo;

import java.util.List;

import org.pdsr.model.audit_recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRecommendRepository extends JpaRepository<audit_recommendation, String> {
	
	@Query("select r FROM audit_recommendation r WHERE r.recommendation_status<>2 ORDER BY r.recommendation_date DESC")
	List<audit_recommendation> findByPendingAction();


}
