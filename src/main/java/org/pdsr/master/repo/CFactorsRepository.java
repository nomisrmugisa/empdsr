package org.pdsr.master.repo;

import java.util.List;

import org.pdsr.master.model.cfactor_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CFactorsRepository extends JpaRepository<cfactor_table, Integer> {
	
	@Query("select c FROM cfactor_table c WHERE c.idgroup=?1")
	List<cfactor_table> findByIdgroup(Integer idgroup);

}
