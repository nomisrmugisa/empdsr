package org.pdsr.master.repo;

import org.pdsr.master.model.mcgroup_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface McgroupRepository extends JpaRepository<mcgroup_table, String> {
	
}
