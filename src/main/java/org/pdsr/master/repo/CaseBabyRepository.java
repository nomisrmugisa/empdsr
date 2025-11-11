package org.pdsr.master.repo;

import org.pdsr.master.model.case_babydeath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CaseBabyRepository extends JpaRepository<case_babydeath, String> {

}
