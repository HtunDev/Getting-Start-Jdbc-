package com.HAH.Jdbc.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

@Configuration
@PropertySource("/database.properties")
@ComponentScan(basePackages = "com.HAH.Jdbc.dao")
public class MemberConfig {

	@Value("${db.url}")
	private String url;
	@Value("${db.username}")
	private String user;
	@Value("${db.password}")
	private String password;

	@Bean
	public DataSource getDataSource() {
		var ds = new MysqlDataSource();
		ds.setUrl(url);
		ds.setUser(user);
		ds.setPassword(password);
		return ds;
	}

	@Bean
	public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource, true);
	}

}
