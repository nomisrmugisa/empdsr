package org.pdsr.slave.repo;

import org.pdsr.slave.model.case_labour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SlaveCaseLabourRepository extends JpaRepository<case_labour, String> {

}
