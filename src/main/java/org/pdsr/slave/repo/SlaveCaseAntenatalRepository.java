package org.pdsr.slave.repo;

import org.pdsr.slave.model.case_antenatal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SlaveCaseAntenatalRepository extends JpaRepository<case_antenatal, String> {

}
