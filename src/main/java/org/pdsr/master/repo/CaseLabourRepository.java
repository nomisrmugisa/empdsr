package org.pdsr.master.repo;

import org.pdsr.master.model.case_labour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CaseLabourRepository extends JpaRepository<case_labour, String> {

}
