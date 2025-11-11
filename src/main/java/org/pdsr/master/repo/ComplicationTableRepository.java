package org.pdsr.master.repo;

import org.pdsr.master.model.complication_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplicationTableRepository extends JpaRepository<complication_table, String> {

}
