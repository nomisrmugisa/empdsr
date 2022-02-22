package org.pdsr.slave.repo;

import java.util.List;

import org.pdsr.slave.model.monitoring_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SlaveMonitoringTableRepository extends JpaRepository<monitoring_table, Integer> {

	@Query("select m FROM monitoring_table m WHERE m.gitem=?1")
	List<monitoring_table> findGlabels(boolean gitem);
	
}
