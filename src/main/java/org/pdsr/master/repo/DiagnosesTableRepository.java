package org.pdsr.master.repo;

import org.pdsr.master.model.diagnoses_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosesTableRepository extends JpaRepository<diagnoses_table, String> {

}
