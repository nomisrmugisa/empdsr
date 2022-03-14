package org.pdsr.slave.repo;

import java.util.List;

import org.pdsr.slave.model.mcondition_table;
import org.pdsr.slave.model.audit_audit;
import org.pdsr.slave.model.cfactor_table;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SlaveAuditAuditRepository extends JpaRepository<audit_audit, String> {

	@Query("select t.patient_factors FROM audit_audit t WHERE t.audit_uuid=?1")
	List<cfactor_table> findPatientFactorsByUuid(String uuid);

	@Query("select t.transport_factors FROM audit_audit t WHERE t.audit_uuid=?1")
	List<cfactor_table> findTransportFactorsByUuid(String uuid);

	@Query("select t.administrative_factors FROM audit_audit t WHERE t.audit_uuid=?1")
	List<cfactor_table> findAdministrativeFactorsByUuid(String uuid);

	@Query("select t.healthworker_factors FROM audit_audit t WHERE t.audit_uuid=?1")
	List<cfactor_table> findHealthworkerFactorsByUuid(String uuid);

	@Query("select t.document_factors FROM audit_audit t WHERE t.audit_uuid=?1")
	List<cfactor_table> findDocumentFactorsByUuid(String uuid);

	@Query("select t.maternal_conditions FROM audit_audit t WHERE t.audit_uuid=?1")
	List<mcondition_table> findMaternalConditionsByUuid(String uuid);


	
	@Query("select t FROM audit_audit t LEFT JOIN audit_recommendation r ON(t.audit_uuid=r.audit_uuid) WHERE r.recommendation_uuid IS NULL")
	List<audit_audit> findByPendingRecommendation();

	@Query("select t.audit_icdpm, count(t.audit_icdpm) FROM audit_audit t WHERE t.audit_death=1 "
			+ "GROUP BY t.audit_icdpm ORDER BY count(t.audit_icdpm) DESC")
	List<String[]> findByTopPMCodesStillBirth(Pageable pageable);

	@Query("select t.audit_icdpm, count(t.audit_icdpm) FROM audit_audit t WHERE t.audit_death=1 AND YEAR(t.audit_cdate)=:cyear "
			+ "GROUP BY t.audit_icdpm ORDER BY count(t.audit_icdpm) DESC")
	List<String[]> findByTopPMCodesStillBirth(@Param("cyear") Integer year, Pageable pageable);

	@Query("select t.audit_icdpm, count(t.audit_icdpm) FROM audit_audit t WHERE t.audit_death > 1 "
			+ "GROUP BY t.audit_icdpm ORDER BY count(t.audit_icdpm) DESC")
	List<String[]> findByTopPMCodesNeonatal(Pageable pageable);

	@Query("select t.audit_icdpm, count(t.audit_icdpm) FROM audit_audit t WHERE t.audit_death > 1 AND YEAR(t.audit_cdate)=:cyear "
			+ "GROUP BY t.audit_icdpm ORDER BY count(t.audit_icdpm) DESC")
	List<String[]> findByTopPMCodesNeonatal( @Param("cyear") Integer year, Pageable pageable);

}
