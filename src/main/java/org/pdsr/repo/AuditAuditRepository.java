package org.pdsr.repo;

import org.pdsr.model.audit_audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditAuditRepository extends JpaRepository<audit_audit, String> {
}
