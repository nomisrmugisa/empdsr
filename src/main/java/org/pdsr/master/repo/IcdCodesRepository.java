package org.pdsr.master.repo;

import java.util.List;
import java.util.Optional;

import org.pdsr.master.model.icd_codes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IcdCodesRepository extends JpaRepository<icd_codes, String> {

	@Query("SELECT i FROM icd_codes i WHERE i.icd_pma<>?1")
	List<icd_codes> findAntepartumICD(String value);

	@Query("SELECT i FROM icd_codes i WHERE i.icd_pmi<>?1")
	List<icd_codes> findIntrapartumICD(String value);

	@Query("SELECT i FROM icd_codes i WHERE i.icd_pmn<>?1")
	List<icd_codes> findNeonatalICD(String value);

	@Query("SELECT i FROM icd_codes i WHERE i.icd_mmg<>?1")
	List<icd_codes> findMaternalICD(String value);

	
	
	@Query("SELECT i FROM icd_codes i WHERE i.icd_code=?1")
	Optional<icd_codes> findICDByICD(String icd_code);
	
	

	@Query("SELECT i FROM icd_codes i WHERE i.icd_pma=?1")
	List<icd_codes> findAntepartumPMByICD(String value);

	@Query("SELECT i FROM icd_codes i WHERE i.icd_pmi=?1")
	List<icd_codes> findIntrapartumPMByICD(String value);

	@Query("SELECT i FROM icd_codes i WHERE i.icd_pmn=?1")
	List<icd_codes> findNeonatalPMByICD(String value);
	
	@Query("SELECT i FROM icd_codes i WHERE i.icd_mmg=?1")
	List<icd_codes> findMaternalMMByICDGroup(String value);
	
	
	
	@Query("SELECT DISTINCT i.icd_pmn_desc FROM icd_codes i WHERE i.icd_pmn=?1")
	Optional<String> findDescriptionOfICDPMNeonatal(String code);
	
	@Query("SELECT DISTINCT i.icd_pmi_desc FROM icd_codes i WHERE i.icd_pmi=?1")
	Optional<String> findDescriptionOfICDPMIntrapartum(String code);
	
	@Query("SELECT DISTINCT i.icd_pma_desc FROM icd_codes i WHERE i.icd_pma=?1")
	Optional<String> findDescriptionOfICDPMAntepartum(String code);
	
	@Query("SELECT DISTINCT i.icd_mmg_desc FROM icd_codes i WHERE i.icd_mmg=?1")
	Optional<String> findDescriptionOfICDMMMaternal(String code);
	
	
}
