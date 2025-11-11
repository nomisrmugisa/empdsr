package org.pdsr.slave.repo;

import java.util.List;

import org.pdsr.slave.model.cordfault_table;
import org.pdsr.slave.model.placentacheck_table;
import org.pdsr.slave.model.abnormality_table;
import org.pdsr.slave.model.case_birth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SlaveCaseBirthRepository extends JpaRepository<case_birth, String> {

	@Query("select a.abnormalities from case_birth a where a.birth_uuid=?1")
	List<abnormality_table> findComplicationsByUuid(String birth_uuid);

	@Query("select a.cordfaults from case_birth a where a.birth_uuid=?1")
	List<cordfault_table> findCordfaultsByUuid(String birth_uuid);

	@Query("select a.placentachecks from case_birth a where a.birth_uuid=?1")
	List<placentacheck_table> findPlacentachecksByUuid(String birth_uuid);

}
