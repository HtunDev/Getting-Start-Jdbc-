package com.HAH.Jdbc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.HAH.Jdbc.dao.meta.MapRow;
import com.HAH.Jdbc.dto.Member;

@MapRow
public class MemberRowMapper implements RowMapper<Member> {

	@Override
	public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
		var member = new Member();
		member.setLoginId(rs.getString(1));
		member.setPassword(rs.getString(2));
		member.setName(rs.getString(3));
		member.setPhone(rs.getString(4));
		member.setEmail(rs.getString(5));
		return member;
	}

}
