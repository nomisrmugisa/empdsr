package org.pdsr.repo;

import org.pdsr.model.case_referral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CaseReferralRepository extends JpaRepository<case_referral, String> {

}
