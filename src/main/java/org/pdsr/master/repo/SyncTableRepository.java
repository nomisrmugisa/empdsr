package org.pdsr.master.repo;

import org.pdsr.master.model.sync_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyncTableRepository extends JpaRepository<sync_table, String> {

}
