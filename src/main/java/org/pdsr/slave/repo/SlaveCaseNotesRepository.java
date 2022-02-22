package org.pdsr.slave.repo;

import org.pdsr.slave.model.case_notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SlaveCaseNotesRepository extends JpaRepository<case_notes, String> {

}
