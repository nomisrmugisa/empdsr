package org.pdsr.master.repo;

import java.util.Set;

import org.pdsr.master.model.icd_diagnoses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IcdDiagnosesRepository extends JpaRepository<icd_diagnoses, String> {	
	
	@Query("SELECT i FROM icd_diagnoses i WHERE i.icd_desc LIKE ?1")
	Set<icd_diagnoses> findByDiagnosese(String search);

}
