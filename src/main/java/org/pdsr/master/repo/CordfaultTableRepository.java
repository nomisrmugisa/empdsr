package org.pdsr.master.repo;

import org.pdsr.master.model.cordfault_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CordfaultTableRepository extends JpaRepository<cordfault_table, String> {

}
