package org.pdsr.master.repo;

import org.pdsr.master.model.ttdp_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TtdpTableRepository extends JpaRepository<ttdp_table, Integer> {

}
