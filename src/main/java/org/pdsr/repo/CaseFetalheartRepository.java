package org.pdsr.repo;

import org.pdsr.model.case_fetalheart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CaseFetalheartRepository extends JpaRepository<case_fetalheart, String> {

}
