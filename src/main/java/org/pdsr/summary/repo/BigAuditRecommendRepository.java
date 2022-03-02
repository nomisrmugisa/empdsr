package org.pdsr.summary.repo;

import org.pdsr.summary.model.SummaryPK;
import org.pdsr.summary.model.big_audit_recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BigAuditRecommendRepository extends JpaRepository<big_audit_recommendation, SummaryPK> {

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE r.summaryPk.country=?1")
	Integer count(String country);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE"
			+ " r.summaryPk.country=?1 AND r.summaryPk.region=?2")
	Integer count(String country, String region);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE"
			+ " r.summaryPk.country=?1 AND r.summaryPk.region=?2 AND r.summaryPk.district=?3")
	Integer count(String country, String region, String district);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE r.recommendation_status=2"
			+ " AND r.summaryPk.country=?1")
	Integer countByCompleted(String country);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE r.recommendation_status=2"
			+ " AND r.summaryPk.country=?1 AND r.summaryPk.region=?2")
	Integer countByCompleted(String country, String region);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE r.recommendation_status=2"
			+ " AND r.summaryPk.country=?1 AND r.summaryPk.region=?2 AND r.summaryPk.district=?3")
	Integer countByCompleted(String country, String region, String district);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE r.recommendation_status<>2 AND recommendation_deadline > ?1"
			+ " AND r.summaryPk.country=?2")
	Integer countByPending(java.util.Date date, String country);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE r.recommendation_status<>2 AND recommendation_deadline > ?1"
			+ " AND r.summaryPk.country=?2 AND r.summaryPk.region=?3")
	Integer countByPending(java.util.Date date, String country, String region);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE r.recommendation_status<>2 AND recommendation_deadline > ?1"
			+ " AND r.summaryPk.country=?2 AND r.summaryPk.region=?3 AND r.summaryPk.district=?4")
	Integer countByPending(java.util.Date date, String country, String region, String district);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE r.recommendation_status<>2 AND recommendation_deadline <= ?1"
			+ " AND r.summaryPk.country=?2")
	Integer countByOverdue(java.util.Date date, String country);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE r.recommendation_status<>2 AND recommendation_deadline <= ?1"
			+ " AND r.summaryPk.country=?2 AND r.summaryPk.region=?3")
	Integer countByOverdue(java.util.Date date, String country, String region);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE r.recommendation_status<>2 AND recommendation_deadline <= ?1"
			+ " AND r.summaryPk.country=?2 AND r.summaryPk.region=?3 AND r.summaryPk.district=?4")
	Integer countByOverdue(java.util.Date date, String country, String region, String district);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE YEAR(r.recommendation_date)=?1"
			+ " AND r.summaryPk.country=?2")
	Integer count(Integer year, String country);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE YEAR(r.recommendation_date)=?1"
			+ " AND r.summaryPk.country=?2 AND r.summaryPk.region=?3")
	Integer count(Integer year, String country, String region);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE YEAR(r.recommendation_date)=?1"
			+ " AND r.summaryPk.country=?2 AND r.summaryPk.region=?3 AND r.summaryPk.district=?4")
	Integer count(Integer year, String country, String region, String district);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE r.recommendation_status=2 AND YEAR(r.recommendation_date)=?1"
			+ " AND r.summaryPk.country=?2")
	Integer countByCompleted(Integer year, String country);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE r.recommendation_status=2 AND YEAR(r.recommendation_date)=?1"
			+ " AND r.summaryPk.country=?2 AND r.summaryPk.region=?3")
	Integer countByCompleted(Integer year, String country, String region);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE r.recommendation_status=2 AND YEAR(r.recommendation_date)=?1"
			+ " AND r.summaryPk.country=?2 AND r.summaryPk.region=?3 AND r.summaryPk.district=?4")
	Integer countByCompleted(Integer year, String country, String region, String district);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE r.recommendation_status<>2 AND recommendation_deadline > ?1 AND YEAR(r.recommendation_date)=?2"
			+ " AND r.summaryPk.country=?3")
	Integer countByPending(java.util.Date date, Integer year, String country);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE r.recommendation_status<>2 AND recommendation_deadline > ?1 AND YEAR(r.recommendation_date)=?2"
			+ " AND r.summaryPk.country=?3 AND r.summaryPk.region=?4")
	Integer countByPending(java.util.Date date, Integer year, String country, String region);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE r.recommendation_status<>2 AND recommendation_deadline > ?1 AND YEAR(r.recommendation_date)=?2"
			+ " AND r.summaryPk.country=?3 AND r.summaryPk.region=?4 AND r.summaryPk.district=?5")
	Integer countByPending(java.util.Date date, Integer year, String country, String region, String district);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE r.recommendation_status<>2 AND recommendation_deadline <= ?1 AND YEAR(r.recommendation_date)=?2"
			+ " AND r.summaryPk.country=?3")
	Integer countByOverdue(java.util.Date date, Integer year, String country);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE r.recommendation_status<>2 AND recommendation_deadline <= ?1 AND YEAR(r.recommendation_date)=?2"
			+ " AND r.summaryPk.country=?3 AND r.summaryPk.region=?4")
	Integer countByOverdue(java.util.Date date, Integer year, String country, String region);

	@Query("select COUNT(r) FROM big_audit_recommendation r WHERE r.recommendation_status<>2 AND recommendation_deadline <= ?1 AND YEAR(r.recommendation_date)=?2"
			+ " AND r.summaryPk.country=?3 AND r.summaryPk.region=?4 AND r.summaryPk.district=?5")
	Integer countByOverdue(java.util.Date date, Integer year, String country, String region, String district);

}
