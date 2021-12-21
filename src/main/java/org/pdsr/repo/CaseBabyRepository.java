package org.pdsr.repo;

import org.pdsr.model.case_babydeath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CaseBabyRepository extends JpaRepository<case_babydeath, String> {

}
