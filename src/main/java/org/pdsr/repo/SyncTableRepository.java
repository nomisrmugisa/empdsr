package org.pdsr.repo;

import org.pdsr.model.sync_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyncTableRepository extends JpaRepository<sync_table, String> {

}
