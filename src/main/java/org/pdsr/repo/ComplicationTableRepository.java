package org.pdsr.repo;

import org.pdsr.model.complication_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplicationTableRepository extends JpaRepository<complication_table, String> {

}
