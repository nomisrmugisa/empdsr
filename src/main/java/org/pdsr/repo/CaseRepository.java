package org.pdsr.repo;

import java.util.List;

import org.pdsr.model.case_identifiers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseRepository extends JpaRepository<case_identifiers, String> {

	@Query("select c from case_identifiers c where c.case_status=?1")
	List<case_identifiers> findByCase_status(Integer case_status);

	@Query("select c from case_identifiers c LEFT JOIN audit_case a ON(c.case_uuid=a.audit_uuid) WHERE a.audit_uuid IS NULL AND c.case_status=?1")
	List<case_identifiers> findByPendingCase_status(Integer case_status);

}
