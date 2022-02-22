package org.pdsr.master.repo;

import org.pdsr.master.model.abnormality_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbnormalityTableRepository extends JpaRepository<abnormality_table, String> {

}
