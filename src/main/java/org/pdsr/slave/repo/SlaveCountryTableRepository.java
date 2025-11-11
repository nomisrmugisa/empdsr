package org.pdsr.slave.repo;

import org.pdsr.slave.model.country_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlaveCountryTableRepository extends JpaRepository<country_table, String> {

}
