package org.pdsr.slave.repo;

import org.pdsr.slave.model.district_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlaveDistrictTableRepository extends JpaRepository<district_table, String> {

}
