package com.example.hb;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@SpringBootApplication
public class HbApplication {
	
	public static final String APPLICATION_LOCATIONS = "spring.config.location="
	            + "classpath:application.properties";

	/*
	public static void main(String[] args) {
		SpringApplication.run(HbApplication.class, args);
	}*/
	
	 public static void main(String[] args) {
	        new SpringApplicationBuilder(HbApplication.class)
	                .properties(APPLICATION_LOCATIONS)
	                .run(args);
	    }
	
	// MyBatis 초기화
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		
		Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*Mapper.xml");
		sessionFactory.setMapperLocations(res);
		
		return sessionFactory.getObject();
	}

}
