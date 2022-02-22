package org.pdsr.master.repo;

import org.pdsr.master.model.case_antenatal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CaseAntenatalRepository extends JpaRepository<case_antenatal, String> {

}
