/**
 * 
 */
package de.cloudscale;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mapping.model.CamelCaseAbbreviatingFieldNamingStrategy;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mapping.model.PropertyNameFieldNamingStrategy;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

/**
 * 
 * @author Johannes Hiemer.
 *
 */
@Configuration
@EnableMongoRepositories(basePackages = {"de.cloudscale.model.nosql.repositories"})
public class CustomMongoDBRepositoryConfig {
	
	@Autowired
	private MongoDbFactory mongoDbFactory;
	
	@Configuration
	@Profile("default")
	static class Default extends AbstractMongoConfiguration {
		
		@Value("${database.nosql.host}")
		private String databaseHost;
		
		@Value("${database.nosql.user}")
		private String databaseUsername;
		
		@Value("${database.nosql.password}")
		private String databasePassword;
		
		@Value("${database.nosql.port}")
		private int databasePort;
		
		@Value("${database.nosql.database}")
		private String databaseName;
		
		@Bean
		public static PropertyPlaceholderConfigurer nosqlPropertyPlaceholderConfigurer() {
			PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
			propertyPlaceholderConfigurer.setLocation(new ClassPathResource("application-model-nosql.properties"));
			propertyPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
			return propertyPlaceholderConfigurer;
		}
		
		@Override
		protected String getDatabaseName() {
			return databaseName;
		}
		
		@Override
	    protected String getMappingBasePackage() {
	        return "de.cloudscale.model.nosql";
	    }
		
		@Override
		public Mongo mongo() throws Exception {
			MongoClient mongoClient = new MongoClient(databaseHost, databasePort);
			mongoClient.setWriteConcern(WriteConcern.UNACKNOWLEDGED);
			return mongoClient;
		}
		
		@Override
		protected UserCredentials getUserCredentials() {
			return new UserCredentials(databaseUsername, databasePassword);
		}

		@Bean
		@SuppressWarnings("deprecation")
		public MongoDbFactory mongoDbFactory() throws Exception {
			return new SimpleMongoDbFactory(mongo(), databaseName, getUserCredentials());
		}
	}
	
	@Configuration
	@Profile("cloud")
	static class Cloud extends AbstractCloudConfig {
		
		@Bean
	    public MongoDbFactory mongoDbFactory() {
			MongoDbFactory mongoDbFactory = connectionFactory().mongoDbFactory();
	        return mongoDbFactory;
	    }
		
		@Bean
		public MongoTemplate mongoTemplate() {
			return new MongoTemplate(mongoDbFactory());
		}
		
		@Bean
		public MongoMappingContext mongoMappingContext() throws ClassNotFoundException {

			MongoMappingContext mappingContext = new MongoMappingContext();
			mappingContext.setInitialEntitySet(getInitialEntitySet());
			mappingContext.setSimpleTypeHolder(customConversions().getSimpleTypeHolder());
			mappingContext.setFieldNamingStrategy(fieldNamingStrategy());

			return mappingContext;
		}
		
		@Bean
		public CustomConversions customConversions() {
			return new CustomConversions(Collections.emptyList());
		}
		
		protected String getMappingBasePackage() {
			return "de.cloudscale.model.nosql";
		}
		
		protected Set<Class<?>> getInitialEntitySet() throws ClassNotFoundException {

			String basePackage = getMappingBasePackage();
			Set<Class<?>> initialEntitySet = new HashSet<Class<?>>();

			if (StringUtils.hasText(basePackage)) {
				ClassPathScanningCandidateComponentProvider componentProvider = new ClassPathScanningCandidateComponentProvider(
						false);
				componentProvider.addIncludeFilter(new AnnotationTypeFilter(Document.class));
				componentProvider.addIncludeFilter(new AnnotationTypeFilter(Persistent.class));

				for (BeanDefinition candidate : componentProvider.findCandidateComponents(basePackage)) {
					initialEntitySet.add(ClassUtils.forName(candidate.getBeanClassName(),
							AbstractMongoConfiguration.class.getClassLoader()));
				}
			}

			return initialEntitySet;
		}
		
		protected boolean abbreviateFieldNames() {
			return false;
		}
		
		protected FieldNamingStrategy fieldNamingStrategy() {
			return abbreviateFieldNames() ? new CamelCaseAbbreviatingFieldNamingStrategy()
					: PropertyNameFieldNamingStrategy.INSTANCE;
		}
		
	}
	
}