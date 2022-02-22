package org.pdsr.slave.repo;

import org.pdsr.slave.model.case_pregnancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SlaveCasePregnancyRepository extends JpaRepository<case_pregnancy, String> {

}
