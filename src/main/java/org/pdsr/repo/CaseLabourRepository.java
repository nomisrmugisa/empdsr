package org.pdsr.repo;

import org.pdsr.model.case_labour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CaseLabourRepository extends JpaRepository<case_labour, String> {

}
