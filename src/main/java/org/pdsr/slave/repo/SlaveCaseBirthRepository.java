package org.pdsr.slave.repo;

import org.pdsr.slave.model.case_birth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SlaveCaseBirthRepository extends JpaRepository<case_birth, String> {

}
