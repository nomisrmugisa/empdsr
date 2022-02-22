package org.pdsr.master.repo;

import org.pdsr.master.model.case_fetalheart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CaseFetalheartRepository extends JpaRepository<case_fetalheart, String> {

}
