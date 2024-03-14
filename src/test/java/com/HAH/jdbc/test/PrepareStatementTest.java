package com.HAH.jdbc.test;

import java.sql.PreparedStatement;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.HAH.Jdbc.configuration.MemberConfig;

@SpringJUnitConfig(classes = MemberConfig.class)
@TestMethodOrder(OrderAnnotation.class)
public class PrepareStatementTest {
	
	@Autowired
	JdbcOperations jdbcOperations;

	@Test
	@DisplayName("1. SQL Test")
	@Sql(scripts = "/database.sql")
	@Order(1)
	void test1(@Value("${member.insert}") String sql) {
		PreparedStatementCreator creator = conn -> {
			var stmt = conn.prepareStatement(sql);
			stmt.setString(1, "admin02");
			stmt.setString(2, "admin2");
			stmt.setString(3, "admin2");
			stmt.setString(4, "09789567");
			stmt.setString(5, "admin2@gmail.com");
			
			return stmt;
		};
		
		var count = jdbcOperations.execute(creator, PreparedStatement::executeUpdate);
	}
}
