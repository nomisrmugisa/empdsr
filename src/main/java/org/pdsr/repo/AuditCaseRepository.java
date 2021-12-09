package org.pdsr.repo;

import org.pdsr.model.audit_case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuditCaseRepository extends JpaRepository<audit_case, String> {

}
