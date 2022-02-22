package org.pdsr.master.repo;

import java.util.List;
import java.util.Optional;

import org.pdsr.master.model.user_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTableRepository extends JpaRepository<user_table, String> {

	@Query("SELECT u FROM user_table u WHERE u.useremail = ?1")
	Optional<user_table> findByUser_email(String user_email);

	@Query("SELECT u.useremail FROM user_table u WHERE u.alerted = ?1")
	List<String> findByUser_alerted(boolean user_email);

}
