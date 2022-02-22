package org.pdsr.master.repo;

import org.pdsr.master.model.case_pregnancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CasePregnancyRepository extends JpaRepository<case_pregnancy, String> {

}
