package com.HAH.jdbc.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.Statement;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.HAH.Jdbc.dao.MemberDao;
import com.HAH.Jdbc.dto.Member;

@TestMethodOrder(OrderAnnotation.class)
@SpringJUnitConfig(classes = com.HAH.Jdbc.configuration.MemberConfig.class)
public class TestDemo {
	
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private JdbcOperations jdbcOperations;
	
	@Test
	@Order(1)
	@Sql(scripts = "/database.sql")
	void demoTest() {
		Member member = new Member();
		member.setLoginId("admin01");
		member.setPassword("admin");
		member.setName("admin");
		
		memberDao.create(member);	
	}
	
	@Test
	@Order(2)
	public void testConnection() {
		var data = jdbcOperations.execute((Connection  conn) -> {
			var stmt = conn.createStatement();
			var rs = stmt.executeQuery("select count(*) from member");
			while(rs.next()) {
				return rs.getLong(1);	
			}
			return 0;
		});
		assertEquals(1L, data);
	}
	
	@Test
	@Order(3)
	public void testStatement() {
		var data = jdbcOperations.execute((Statement stmt) -> {
			var rs = stmt.executeQuery("select count(*) from member");
			while(rs.next()) {
				return rs.getLong(1);	
			}
			return 0;
		});
		assertEquals(1L, data);
	}
	
}
