package org.pdsr.slave.repo;

import org.pdsr.slave.model.case_fetalheart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SlaveCaseFetalheartRepository extends JpaRepository<case_fetalheart, String> {

}
