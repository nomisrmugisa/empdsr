package org.pdsr.slave.repo;

import org.pdsr.slave.model.case_babydeath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SlaveCaseBabyRepository extends JpaRepository<case_babydeath, String> {

}
