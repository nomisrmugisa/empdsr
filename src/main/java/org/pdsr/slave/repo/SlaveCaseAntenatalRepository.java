package org.pdsr.slave.repo;

import java.util.List;

import org.pdsr.slave.model.case_antenatal;
import org.pdsr.slave.model.risk_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SlaveCaseAntenatalRepository extends JpaRepository<case_antenatal, String> {
	
	@Query("select a.risks from case_antenatal a where a.antenatal_uuid=?1")
	List<risk_table> findRiskByAntenatalUuid(String uuid);


}
