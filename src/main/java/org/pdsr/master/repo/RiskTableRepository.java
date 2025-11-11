package org.pdsr.master.repo;

import org.pdsr.master.model.risk_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskTableRepository extends JpaRepository<risk_table, String> {

}
