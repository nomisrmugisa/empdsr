package org.pdsr.master.repo;

import java.util.List;

import org.pdsr.master.model.case_mdeath;
import org.pdsr.master.model.icd_diagnoses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseMdeathRepository extends JpaRepository<case_mdeath, String> {

	@Query("select a.icd_diagnoses from case_babydeath a where a.baby_uuid=?1")
	List<icd_diagnoses> findICDDiagnosesByUuid(String uuid);

}
