package org.pdsr.master.repo;

import java.util.List;

import org.pdsr.master.model.mcondition_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MconditionsRepository extends JpaRepository<mcondition_table, String> {
	
	@Query("select m FROM mcondition_table m WHERE m.icdmgroup=?1")
	List<mcondition_table> findByIcdmgroup(String icdgroup);

}
