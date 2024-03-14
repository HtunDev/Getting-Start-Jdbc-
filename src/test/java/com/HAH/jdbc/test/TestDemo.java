package com.HAH.jdbc.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

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
		var data = jdbcOperations.execute((Connection conn) -> {
			var stmt = conn.createStatement();
			var rs = stmt.executeQuery("select count(*) from member");
			while (rs.next()) {
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
			while (rs.next()) {
				return rs.getLong(1);
			}
			return 0;
		});
		assertEquals(1L, data);
	}

	@Test
	@Order(4)
	public void testStatement1() {
		var data = jdbcOperations.execute((Statement stmt) -> {
			return stmt.executeUpdate("""
					insert into member (loginId,password,name) values ('101MB','member1','U Moe Thein')
					""");
		});
		assertEquals(1, data);
	}

	@Test
	@Order(5)
	public void testStaticQueryRowExtractor() {
		var result = jdbcOperations.query("select count(*) from member", rs -> {
			while (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		});
		assertEquals(2, result);

		var list = jdbcOperations.query("select * from member", rs -> {
			var memberData = new ArrayList<Member>();
			while (rs.next()) {
				var m = new Member();
				m.setLoginId(rs.getString(1));
				m.setPassword(rs.getString(2));
				m.setName(rs.getString(3));
				m.setPhone(rs.getString(4));
				m.setEmail(rs.getString(5));
				memberData.add(m);
			}
			return memberData;
		});
		assertEquals(2, list.size());
	}

	@Test
	@Order(6)
	public void testStaticQueryRowCallbackHandler() {
		var memberData = new ArrayList<Member>();
		jdbcOperations.query("select * from member", rs -> {
			var m = new Member();
			m.setLoginId(rs.getString(1));
			m.setPassword(rs.getString(2));
			m.setName(rs.getString(3));
			m.setPhone(rs.getString(4));
			m.setEmail(rs.getString(5));
			memberData.add(m);
		});
		System.out.println(memberData);
		assertEquals(2, memberData.size());
	}
	
	@Test
	@Order(7)
	public void testStaticQueryRowMapper() {
		var list = jdbcOperations.query("select * from member", (rs,no) -> {
			var m = new Member();
			m.setLoginId(rs.getString(1));
			m.setPassword(rs.getString(2));
			m.setName(rs.getString(3));
			m.setPhone(rs.getString(4));
			m.setEmail(rs.getString(5));
			return m;
		});
		assertEquals(2, list.size());
	}
}
