package org.pdsr.master.repo;

import java.util.List;

import org.pdsr.master.model.datamap;
import org.pdsr.master.model.datamapPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DatamapRepository extends JpaRepository<datamap, datamapPK> {

	@Query("select d from datamap d where d.map_feature=?1 ORDER BY d.map_value")
	List<datamap> findByMap_feature(String map_feature);

}
