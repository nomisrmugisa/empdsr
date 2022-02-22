package org.pdsr.master.repo;

import org.pdsr.master.model.case_birth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CaseBirthRepository extends JpaRepository<case_birth, String> {

}
