package org.pdsr.slave.repo;

import org.pdsr.slave.model.SyncTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlaveSyncTableRepository extends JpaRepository<SyncTable, String> {

}
