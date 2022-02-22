package org.pdsr.master.repo;

import java.util.List;

import org.pdsr.master.model.audit_recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRecommendRepository extends JpaRepository<audit_recommendation, String> {

	@Query("select r FROM audit_recommendation r ORDER BY r.recommendation_date DESC")
	List<audit_recommendation> findByPendingAction();

	@Query("select COUNT(r) FROM audit_recommendation r WHERE r.recommendation_status=2")
	Integer countByCompleted();

	@Query("select COUNT(r) FROM audit_recommendation r WHERE r.recommendation_status<>2 AND recommendation_deadline > ?1")
	Integer countByPending(java.util.Date date);

	@Query("select COUNT(r) FROM audit_recommendation r WHERE r.recommendation_status<>2 AND recommendation_deadline <= ?1")
	Integer countByOverdue(java.util.Date date);

	
	
	@Query("select COUNT(r) FROM audit_recommendation r WHERE YEAR(r.recommendation_date)=?1")
	Integer count(Integer year);

	@Query("select COUNT(r) FROM audit_recommendation r WHERE r.recommendation_status=2 AND YEAR(r.recommendation_date)=?1")
	Integer countByCompleted(Integer year);

	@Query("select COUNT(r) FROM audit_recommendation r WHERE r.recommendation_status<>2 AND recommendation_deadline > ?1 AND YEAR(r.recommendation_date)=?2")
	Integer countByPending(java.util.Date date, Integer year);

	@Query("select COUNT(r) FROM audit_recommendation r WHERE r.recommendation_status<>2 AND recommendation_deadline <= ?1 AND YEAR(r.recommendation_date)=?2")
	Integer countByOverdue(java.util.Date date, Integer year);

}
