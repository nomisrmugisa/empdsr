package org.pdsr.repo;

import org.pdsr.model.case_biodata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CaseBiodataRepository extends JpaRepository<case_biodata, String> {

}
