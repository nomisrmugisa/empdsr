package org.pdsr.slave.repo;

import java.util.List;

import org.pdsr.slave.model.case_labour;
import org.pdsr.slave.model.complication_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SlaveCaseLabourRepository extends JpaRepository<case_labour, String> {

	@Query("select a.complications from case_labour a where a.labour_uuid=?1")
	List<complication_table> findComplicationsByUuid(String uuid);

}
