package org.pdsr.master.repo;

import org.pdsr.master.model.district_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictTableRepository extends JpaRepository<district_table, String> {

}
