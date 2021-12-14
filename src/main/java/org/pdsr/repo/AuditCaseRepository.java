package org.pdsr.repo;

import java.util.List;

import org.pdsr.model.audit_case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditCaseRepository extends JpaRepository<audit_case, String> {
	@Query("select DISTINCT a FROM audit_case a LEFT JOIN audit_audit t ON(a.audit_uuid=t.audit_uuid) WHERE t.audit_uuid IS NULL")
	List<audit_case> findByPendingAudit();

}
