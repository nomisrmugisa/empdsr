package org.pdsr.slave.repo;

import org.pdsr.slave.model.case_delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SlaveCaseDeliveryRepository extends JpaRepository<case_delivery, String> {

}
