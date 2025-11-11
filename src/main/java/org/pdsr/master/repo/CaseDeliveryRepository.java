package org.pdsr.master.repo;

import org.pdsr.master.model.case_delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CaseDeliveryRepository extends JpaRepository<case_delivery, String> {

}
