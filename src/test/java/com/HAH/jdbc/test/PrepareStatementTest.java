package com.HAH.jdbc.test;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.HAH.Jdbc.configuration.MemberConfig;
import com.HAH.Jdbc.dao.configuration.FactoryConfig;

@SpringJUnitConfig(classes = MemberConfig.class)
@TestMethodOrder(OrderAnnotation.class)
public class PrepareStatementTest {
	
	@Autowired
	JdbcOperations jdbcOperations;
	
//	@Test
//	@DisplayName("1. SQL Test")
//	@Sql(scripts = "/database.sql")
//	@Order(1)
//	void test1(@Value("${member.insert}") String sql) {
//		//Using Prepare Statement Creator
//		PreparedStatementCreator creator = conn -> {
//			var stmt = conn.prepareStatement(sql);
//			stmt.setString(1, "admin03");
//			stmt.setString(2, "admin3");
//			stmt.setString(3, "Aung Aung");
//			stmt.setString(4, "098855773");
//			stmt.setString(5, "admin3@gmail.com");
//			return stmt;
//		};
//		
//		var count = jdbcOperations.execute(creator, PreparedStatement::executeUpdate);
//		// Using Prepare Statement Creator Factory 
//		var factory = new PreparedStatementCreatorFactory(sql, new int[] {
//				Types.VARCHAR,
//				Types.VARCHAR,
//				Types.VARCHAR,
//				Types.VARCHAR,
//				Types.VARCHAR
//		});
//		
//		var creator1 = factory.newPreparedStatementCreator(List.of("04admin","admin4","Naung Naung","09889977","naung@gmail.com"));	
//		var count1 = jdbcOperations.execute(creator1, PreparedStatement::executeUpdate);
//	}
	
	@Test
	@DisplayName("1. SQL Test")
	@Sql(scripts = "/database.sql")
	@Order(1)
	void test1(@Qualifier("memberInserter") PreparedStatementCreatorFactory factory) {
		// Creation Prepare Statement Creator Factory with spring bean
		
		var creator1 = factory.newPreparedStatementCreator(List.of("04admin","admin4","Naung Naung","09889977","naung@gmail.com"));	
		var count1 = jdbcOperations.execute(creator1, PreparedStatement::executeUpdate);
	}
}
