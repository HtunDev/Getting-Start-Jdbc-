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

		var creator1 = factory.newPreparedStatementCreator(
				List.of("04admin", "admin4", "Naung Naung", "09889977", "naung@gmail.com"));
		var count1 = jdbcOperations.execute(creator1, PreparedStatement::executeUpdate);
	}

	@Test
	@DisplayName("2. SQL Test")
	@Order(2)
	void test2(@Qualifier("memberInserter") PreparedStatementCreatorFactory factory) {
		// Creation Prepare Statement Creator Factory with spring bean

		var creator1 = factory.newPreparedStatementCreator(
				List.of("member05", "member5", "Hla Naung", "09089977", "hlanaung@gmail.com"));
		var count1 = jdbcOperations.update(creator1);
	}

	@Test
	@DisplayName("3. Find By Name Like")
	@Order(3)
	void test3(@Qualifier("memberFindByNameLike") PreparedStatementCreatorFactory factory) {
		var creator = factory.newPreparedStatementCreator(List.of("Naung%"));

		var result = jdbcOperations.execute(creator, stmt -> {
			List<Member> listM = new ArrayList<>();
			var rs = stmt.executeQuery();

			while (rs.next()) {
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
	@DisplayName("4. Query With Operation")
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
	@DisplayName("5. Query With Operation(Row Mapper Spring Bean Class)")
	@Order(5)
	void test5(@Qualifier("memberFindByNameLike") PreparedStatementCreatorFactory factory) {
		var creator = factory.newPreparedStatementCreator(List.of("Naung%"));

		var result = jdbcOperations.query(creator, rowMapper);
		assertEquals(1, result.size());
	}

	@Test
	@DisplayName("6. Query With Operation(Resultset Extractor)")
	@Order(6)
	void test6(@Qualifier("memberFindById") PreparedStatementCreatorFactory factory) {
		var creator = factory.newPreparedStatementCreator(List.of("member05"));

		var result = jdbcOperations.query(creator, rs -> {
			while (rs.next()) {
				return rowMapper.mapRow(rs, 1);
			}
			return null;
		});
		assertEquals("Hla Naung", result.getName());
	}

	@Test
	@DisplayName("7. Execute with simple Sql String")
	@Order(7)
	void test7(@Value("${member.insert}") String sql) {
		var count = jdbcOperations.execute(sql, (PreparedStatement stmt) -> {
			stmt.setString(1, "07member");
			stmt.setString(2, "member7");
			stmt.setString(3, "Hsu Lae");
			stmt.setString(4, "09789862");
			stmt.setString(5, "ariel@gmail.com");
			return stmt.executeUpdate();
		}); 

		System.out.println(count);
		assertEquals(1, count  );

	}
	
	@Test
	@DisplayName("8. Update With Simple Statement")
	@Order(8)
	void test8(@Value("${member.insert}") String sql) {
		int count = jdbcOperations.update(sql, stmt ->{
			stmt.setString(1, "08member");
			stmt.setString(2, "member8");
			stmt.setString(3, "Ma Khaing");
			stmt.setString(4, "0988653");
			stmt.setString(5, "khaing@gmail.com");
		});
		assertEquals(1, count);
	}
	
	@Test
	@DisplayName("9. Update With Parameter")
	@Order(9)
	void test9(@Value("${member.insert}") String sql) {
		var count = jdbcOperations.update(sql, "09member","member9","U Moe Thein","094498937","mgmoe@gmail.com");
		assertEquals(1, count);
	}
	
	@Test
	@DisplayName("10. Query With Simple sql")
	@Order(10)
	void test10(@Value("${member.select.find.by.name}") String sql) {
		RowMapper<Member> rowMapper1 = (rs, rowNum) -> {
			var m = new Member();
			m.setLoginId(rs.getString(1));
			m.setPassword(rs.getString(2));
			m.setName(rs.getString(3));
			m.setPhone(rs.getString(4));
			m.setEmail(rs.getString(5));
			return m;
		};
		var list = jdbcOperations.query(sql, stmt -> stmt.setString(1, "Hsu%"), rowMapper1);
		assertEquals(1, list.size());
	}
}
