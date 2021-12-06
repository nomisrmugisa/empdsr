package org.pdsr.repo;

import org.pdsr.model.risk_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskTableRepository extends JpaRepository<risk_table, String> {

}
