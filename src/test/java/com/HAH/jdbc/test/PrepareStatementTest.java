package com.HAH.jdbc.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.HAH.Jdbc.configuration.MemberConfig;

@SpringJUnitConfig(classes = MemberConfig.class)
@TestMethodOrder(OrderAnnotation.class)
public class PrepareStatementTest {

	@Test
	@DisplayName("1. SQL Test")
	@Sql(scripts = "/database.sql")
	@Order(1)
	void test1(@Value("${member.insert}") String sql) {
		System.out.println(sql);
	}
}
