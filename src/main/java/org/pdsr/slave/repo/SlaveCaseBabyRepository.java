package org.pdsr.slave.repo;

import java.util.List;

import org.pdsr.slave.model.resuscitation_table;
import org.pdsr.slave.model.case_babydeath;
import org.pdsr.slave.model.diagnoses_table;
import org.pdsr.slave.model.icd_diagnoses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SlaveCaseBabyRepository extends JpaRepository<case_babydeath, String> {

	@Query("select a.resuscitations from case_babydeath a where a.baby_uuid=?1")
	List<resuscitation_table> findResuscitationsByUuid(String uuid);

	@Query("select a.diagnoses from case_babydeath a where a.baby_uuid=?1")
	List<diagnoses_table> findDiagnosesByUuid(String uuid);

	@Query("select a.icd_diagnoses from case_babydeath a where a.baby_uuid=?1")
	List<icd_diagnoses> findICDDiagnosesByUuid(String uuid);


}
