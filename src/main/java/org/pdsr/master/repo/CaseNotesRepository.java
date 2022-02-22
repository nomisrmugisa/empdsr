package org.pdsr.master.repo;

import org.pdsr.master.model.case_notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CaseNotesRepository extends JpaRepository<case_notes, String> {

}
