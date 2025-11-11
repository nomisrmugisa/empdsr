package org.pdsr.master.repo;

import org.pdsr.master.model.case_biodata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CaseBiodataRepository extends JpaRepository<case_biodata, String> {

}
