package com.HAH.jdbc.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.HAH.Jdbc.dao.MemberDao;
import com.HAH.Jdbc.dto.Member;

@SpringJUnitConfig(classes = com.HAH.Jdbc.configuration.MemberConfig.class)
public class TestDemo {
	
	@Autowired
	private MemberDao memberDao;
	
	@Test
	@Sql(scripts = "/database.sql")
	void demoTest() {
		Member member = new Member();
		member.setLoginId("admin01");
		member.setPassword("admin");
		member.setName("admin");
		
		memberDao.create(member);	
	}
}
