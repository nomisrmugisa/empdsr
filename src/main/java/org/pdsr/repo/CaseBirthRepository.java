package org.pdsr.repo;

import org.pdsr.model.case_birth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CaseBirthRepository extends JpaRepository<case_birth, String> {

}
