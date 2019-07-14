package com.src.oauth2.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoClientFactory;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class MongoConfiguration extends AbstractMongoConfiguration {

	@Value("${spring.mongo.uri}")
	private String uri;
	@Value("${spring.mongo.host}")
	private String host;
	@Value("${spring.mongo.port}")
	private Integer port;
	@Value("${spring.mongo.username}")
	private String username;
	@Value("${spring.mongo.password}")
	private char[] password;
	@Value("${spring.mongo.database}")
	private String database;
	@Autowired
	private Environment env;

	@Bean
	public MongoDbFactory mongoDbFactory2() throws Exception {
		return new SimpleMongoDbFactory(mongoClient(), getDatabaseName());
	}

	@Bean
	public MongoTemplate mongoTemplate2() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory2());
		return mongoTemplate;
	}

	@Bean
	public MongoOperations mongoOperations2() throws Exception {
		return mongoTemplate2();
	}

	@Override
	public MongoClient mongoClient() {
		MongoProperties mongoProperties = new MongoProperties();
		mongoProperties.setHost(host);
		mongoProperties.setPassword(password);
		mongoProperties.setPort(port);
		mongoProperties.setDatabase(getDatabaseName());
		mongoProperties.setUsername(username);
//		mongoProperties.setUri(uri);
		MongoClientFactory mongoClientFactory = new MongoClientFactory(mongoProperties, env);

		return mongoClientFactory.createMongoClient(MongoClientOptions.builder().build());
	}

	@Override
	protected String getDatabaseName() {
		return database;
	}
}
