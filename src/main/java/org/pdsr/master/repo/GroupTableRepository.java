package org.pdsr.master.repo;

import org.pdsr.master.model.group_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupTableRepository extends JpaRepository<group_table, String> {

}
