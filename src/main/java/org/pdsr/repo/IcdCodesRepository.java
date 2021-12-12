package org.pdsr.repo;

import java.util.List;
import java.util.Optional;

import org.pdsr.model.icd_codes;
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

	@Query("SELECT i FROM icd_codes i WHERE i.icd_code=?1")
	Optional<icd_codes> findPMByICD(String icd_code);

	@Query("SELECT i FROM icd_codes i WHERE i.icd_pma=?1")
	List<icd_codes> findAntepartumPMByICD(String value);

	@Query("SELECT i FROM icd_codes i WHERE i.icd_pmi=?1")
	List<icd_codes> findIntrapartumPMByICD(String value);

	@Query("SELECT i FROM icd_codes i WHERE i.icd_pmn=?1")
	List<icd_codes> findNeonatalPMByICD(String value);
}
