package com.src.oauth2;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Locale;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@ComponentScan("com.src.oauth2.*")
//public class MainApplication {
public class MainApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(MainApplication.class);
		application.setDefaultProperties(Collections.singletonMap("spring.profiles.active", "dev"));
		application.run(args);
	}

	@Value("${spring.datasource.driverClassName}")
	private String databaseDriverClassName;

	@Value("${spring.datasource.password}")
	private String databasePassword;

	@Value("${spring.datasource.username}")
	private String databaseUsername;

	@Value("${spring.datasource.url}")
	private String datasourceUrl;

	@Value("${redis.host.url}")
	private String redisHostUrl;

	@Value("${redis.port}")
	private Integer redisPort;

//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(localeChangeInterceptor());
//	}
//
//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/login").setViewName("login");
//	}

	@Bean
	public DataSource datasource() {
		org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
		ds.setDriverClassName(databaseDriverClassName);
		ds.setUrl(datasourceUrl);
		ds.setUsername(databaseUsername);
		ds.setPassword(databasePassword);

		return ds;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.US);
		return slr;
	}

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
		jedisConFactory.setHostName(redisHostUrl);
		jedisConFactory.setPort(redisPort);
		return jedisConFactory;
	}


	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
