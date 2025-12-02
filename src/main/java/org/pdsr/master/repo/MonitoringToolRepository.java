package org.pdsr.master.repo;

import org.pdsr.master.model.monitoring_tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoringToolRepository extends JpaRepository<monitoring_tool, String> {

}
