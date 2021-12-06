package org.pdsr.repo;

import org.pdsr.model.user_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTableRepository extends JpaRepository<user_table, String> {

}
