package org.pdsr.repo;

import java.util.Optional;

import org.pdsr.model.facility_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityTableRepository extends JpaRepository<facility_table, String> {
	
	@Query("SELECT f FROM facility_table f WHERE f.facility_code= ?1")
	Optional<facility_table> findByFacility_code(String facility_code);

}
