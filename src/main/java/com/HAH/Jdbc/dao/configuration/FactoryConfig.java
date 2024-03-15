package com.HAH.Jdbc.dao.configuration;

import java.sql.Types;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;

@Configuration
public class FactoryConfig {

	@Bean
	@Qualifier("memberInserter")
	 public PreparedStatementCreatorFactory memberCreatorFactory(@Value("${member.insert}") String sql) {
		 return new PreparedStatementCreatorFactory(sql, new int[] {
				 	Types.VARCHAR,
					Types.VARCHAR,
					Types.VARCHAR,
					Types.VARCHAR,
					Types.VARCHAR
			});
	 }
}
