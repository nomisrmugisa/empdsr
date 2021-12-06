package org.pdsr.repo;

import org.pdsr.model.case_pregnancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CasePregnancyRepository extends JpaRepository<case_pregnancy, String> {

}
