package org.pdsr.repo;

import org.pdsr.model.diagnoses_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosesTableRepository extends JpaRepository<diagnoses_table, String> {

}
