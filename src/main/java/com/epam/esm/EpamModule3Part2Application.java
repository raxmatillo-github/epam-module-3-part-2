package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EpamModule3Part2Application {

	public static void main(String[] args) {
		SpringApplication.run(EpamModule3Part2Application.class, args);
	}

//	@Bean
//	public DataSource postgresDataSource() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("org.postgresql.Driver");
//		dataSource.setUrl("jdbc:postgresql://localhost:5432/epam");
//		dataSource.setUsername("postgres");
//		dataSource.setPassword("postgres");
//		return dataSource;
//	}
//
//	@Bean
//	public JdbcTemplate jdbcTemplate() {
//		JdbcTemplate jdbcTemplate = new JdbcTemplate();
//		jdbcTemplate.setDataSource(postgresDataSource());
//		return jdbcTemplate;
//	}

}
