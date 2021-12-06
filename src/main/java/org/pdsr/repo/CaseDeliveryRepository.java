package org.pdsr.repo;

import org.pdsr.model.case_delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CaseDeliveryRepository extends JpaRepository<case_delivery, String> {

}
