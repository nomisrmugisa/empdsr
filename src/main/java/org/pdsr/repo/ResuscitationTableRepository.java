package org.pdsr.repo;

import org.pdsr.model.resuscitation_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResuscitationTableRepository extends JpaRepository<resuscitation_table, String> {

}
