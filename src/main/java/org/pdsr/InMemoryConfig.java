package org.pdsr;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

@Configuration
public class InMemoryConfig {
	private final String SAMPLE_DATA = "classpath:data.sql";
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate datasource;
	
	@Autowired
	private ResourceLoader resourceLoader;

	@PostConstruct
	public void loadIfInMemory() throws Exception {
		// Commented out to avoid redundant execution. 
		// Spring Boot's spring.sql.init.mode=always already handles data.sql.
		// Resource resource = resourceLoader.getResource(SAMPLE_DATA);
		// ScriptUtils.executeSqlScript(datasource.getDataSource().getConnection(), resource);
	}
}
