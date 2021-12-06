package org.pdsr.repo;

import org.pdsr.model.country_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryTableRepository extends JpaRepository<country_table, String> {

}
