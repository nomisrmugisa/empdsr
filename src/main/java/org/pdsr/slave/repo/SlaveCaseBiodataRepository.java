package org.pdsr.slave.repo;

import org.pdsr.slave.model.case_biodata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SlaveCaseBiodataRepository extends JpaRepository<case_biodata, String> {

}
