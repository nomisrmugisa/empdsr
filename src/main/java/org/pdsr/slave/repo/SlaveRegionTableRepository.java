package org.pdsr.slave.repo;

import org.pdsr.slave.model.region_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlaveRegionTableRepository extends JpaRepository<region_table, String> {

}
