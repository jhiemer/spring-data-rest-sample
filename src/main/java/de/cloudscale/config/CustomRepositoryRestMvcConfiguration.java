/**
 * 
 */
package de.cloudscale.config;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.hateoas.mvc.ControllerLinkBuilderFactory;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import de.cloudscale.web.utils.converter.ObjectIdSerializer;

/**
 * 
 * @author Johannes Hiemer.
 *
 */
@EnableAsync
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@ComponentScan(basePackages = { "de.cloudscale.web", "de.cloudscale.monitor",
		"de.cloudscale.model.nosql.migration", "de.cloudscale.monitor.plugins"  })
public class CustomRepositoryRestMvcConfiguration extends RepositoryRestMvcConfiguration {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory
			.getLogger(CustomRepositoryRestMvcConfiguration.class);
	
	@Bean(autowire = Autowire.BY_TYPE)
	public ControllerLinkBuilderFactory controllerLinkBuilderFacotry() {
		return new ControllerLinkBuilderFactory();
	}
	
	/**
	 * SD REST Settings
	 */
	@Override
	protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.setReturnBodyOnCreate(true);
		config.setReturnBodyOnUpdate(true);
		config.setMaxPageSize(100);	
		config.setDefaultPageSize(25);
		config.setDefaultMediaType(MediaType.APPLICATION_JSON);
		config.useHalAsDefaultJsonMediaType(false);
	}
	
	@Override
	protected void configureJacksonObjectMapper(ObjectMapper objectMapper) {
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		
		SimpleModule module = new SimpleModule("ObjectIdmodule");
	    module.addSerializer(ObjectId.class, new ObjectIdSerializer());
	    objectMapper.registerModule(module);
	}

}
