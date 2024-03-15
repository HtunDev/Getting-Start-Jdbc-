package com.HAH.Jdbc.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.jdbc.core.JdbcTemplate;

import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

@Configuration
@PropertySources({ @PropertySource("database.properties"), @PropertySource("sql.properties") })
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
		var boneCPConfig = new BoneCPConfig();
		boneCPConfig.setJdbcUrl(url);
		boneCPConfig.setUsername(user);
		boneCPConfig.setPassword(password);
		return new BoneCPDataSource(boneCPConfig);
	}

	@Bean
	public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource, true);
	}

}
