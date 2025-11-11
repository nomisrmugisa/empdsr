package org.pdsr.master.repo;

import org.pdsr.master.model.case_referral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CaseReferralRepository extends JpaRepository<case_referral, String> {

}
