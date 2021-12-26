package org.pdsr.repo;

import java.util.Optional;

import org.pdsr.model.user_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserTableRepository extends JpaRepository<user_table, String> {

    @Query("SELECT u FROM user_table u WHERE u.useremail = ?1")
    Optional<user_table> findByUser_email(String user_email);

}
