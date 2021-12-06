package org.pdsr.repo;

import org.pdsr.model.case_notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CaseNotesRepository extends JpaRepository<case_notes, String> {

}
