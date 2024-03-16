package com.HAH.jdbc.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.HAH.Jdbc.configuration.MemberConfig;
import com.HAH.Jdbc.dao.configuration.FactoryConfig;
import com.HAH.Jdbc.dao.meta.MapRow;
import com.HAH.Jdbc.dto.Member;

@SpringJUnitConfig(classes = MemberConfig.class)
@TestMethodOrder(OrderAnnotation.class)
public class PrepareStatementTest {
	
	@Autowired
	JdbcOperations jdbcOperations;
	
	@Autowired
	RowMapper<Member> rowMapper;
	
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
	
	@Test
	@DisplayName("1. SQL Test")
	@Order(2)
	void test2(@Qualifier("memberInserter") PreparedStatementCreatorFactory factory) {
		// Creation Prepare Statement Creator Factory with spring bean
		
		var creator1 = factory.newPreparedStatementCreator(List.of("member05","member5","Hla Naung","09089977","hlanaung@gmail.com"));	
		var count1 = jdbcOperations.update(creator1);
	}
	
	@Test
	@DisplayName("2. Find By Name Like")
	@Order(3)
	void test3(@Qualifier("memberFindByNameLike") PreparedStatementCreatorFactory factory) {
		var creator = factory.newPreparedStatementCreator(List.of("Naung%"));
		
		var result = jdbcOperations.execute(creator, stmt -> {
			List<Member> listM = new ArrayList<>();
			var rs = stmt.executeQuery();
			
			while(rs.next()) {
				var member = new Member();
				member.setLoginId(rs.getString(1));
				member.setPassword(rs.getString(2));
				member.setName(rs.getString(3));
				member.setPhone(rs.getString(4));
				member.setEmail(rs.getString(5));
				listM.add(member);
			}
			return listM;
		});
		System.out.println(result); 
		assertEquals(1, result.size());
	}
	
	@Test
	@DisplayName("2. Query With Operation")
	@Order(4)
	void test4(@Qualifier("memberFindByNameLike") PreparedStatementCreatorFactory factory) {
		
		RowMapper<Member> rowMapper = (rs, rowNum) -> {
			var member = new Member();
			member.setLoginId(rs.getString(1));
			member.setPassword(rs.getString(2));
			member.setName(rs.getString(3));
			member.setPhone(rs.getString(4));
			member.setEmail(rs.getString(5));
			return member;
		};
		var creator = factory.newPreparedStatementCreator(List.of("Naung%"));
		
		var result = jdbcOperations.query(creator, rowMapper);
		System.out.println(result); 
		assertEquals(1, result.size());
	}
	
	@Test
	@DisplayName("2. Query With Operation(Row Mapper Spring Bean Class)")
	@Order(5)
	void test5(@Qualifier("memberFindByNameLike") PreparedStatementCreatorFactory factory) {
		var creator = factory.newPreparedStatementCreator(List.of("Naung%"));
		
		var result = jdbcOperations.query(creator, rowMapper);
		assertEquals(1, result.size());
	}
}
