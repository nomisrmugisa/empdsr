package org.pdsr.slave.repo;

import org.pdsr.slave.model.case_referral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SlaveCaseReferralRepository extends JpaRepository<case_referral, String> {

}
