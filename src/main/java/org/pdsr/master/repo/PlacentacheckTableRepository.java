package org.pdsr.master.repo;

import org.pdsr.master.model.placentacheck_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacentacheckTableRepository extends JpaRepository<placentacheck_table, String> {

}
