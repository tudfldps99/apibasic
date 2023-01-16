package com.example.apibasic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class ApibasicApplicationTests {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	void dbConnectTest() {
		String sql = "SELECT NOW() AS now FROM dual";
		String now = jdbcTemplate.queryForObject(sql, String.class);

		System.out.println("now = " + now);
	}
}
