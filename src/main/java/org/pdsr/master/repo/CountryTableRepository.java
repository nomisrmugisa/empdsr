package org.pdsr.master.repo;

import org.pdsr.master.model.country_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryTableRepository extends JpaRepository<country_table, String> {

}
