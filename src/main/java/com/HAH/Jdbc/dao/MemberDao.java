package com.HAH.Jdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.HAH.Jdbc.dto.Member;

@Repository
public class MemberDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void create(Member member) {
		jdbcTemplate.update("insert into member values (?,?,?,?,?)", 
				member.getLoginId(), 
				member.getPassword(),
				member.getName(), 
				member.getPhone(), 
				member.getEmail());
	}

}
