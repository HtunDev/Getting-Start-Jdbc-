package com.HAH.Jdbc.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.HAH.Jdbc.dto.Member;

public class MemberDao {

	private JdbcTemplate jdbcTemplate;

	public MemberDao(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	public void create(Member member) {
		jdbcTemplate.update("insert into member values (?,?,?,?,?)", 
				member.getLoginId(), 
				member.getPassword(),
				member.getName(), 
				member.getPhone(), 
				member.getEmail());
	}

}
