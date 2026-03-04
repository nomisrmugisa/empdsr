package org.pdsr.master.repo;

import org.pdsr.slave.model.SyncTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyncTableRepository extends JpaRepository<SyncTable, String> {

}
