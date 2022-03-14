package org.pdsr.slave.repo;

import org.pdsr.slave.model.mcgroup_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SlaveMcgroupRepository extends JpaRepository<mcgroup_table, String> {
	
}
