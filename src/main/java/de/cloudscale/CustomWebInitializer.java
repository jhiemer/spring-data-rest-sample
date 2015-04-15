/**
 *
 */
package de.cloudscale;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import de.cloudscale.config.CustomMvcConfiguration;
import de.cloudscale.config.CustomRepositoryRestMvcConfiguration;
import de.cloudscale.config.cors.CORSFilter;

/**
 *
 * @author Johannes Hiemer.
 *
 */
public class CustomWebInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext rootCtx = new AnnotationConfigWebApplicationContext();
		rootCtx.register(
				CustomMongoDBRepositoryConfig.class
		);

		servletContext.addListener(new ContextLoaderListener(rootCtx));

		servletContext.addFilter("corsFilter", CORSFilter.class);
		servletContext.getFilterRegistration("corsFilter").addMappingForUrlPatterns(
			EnumSet.of(DispatcherType.REQUEST),
			false,
			"/*"
		);

		AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();
		webCtx.setParent(rootCtx);
		webCtx.setServletContext(servletContext);
        webCtx.register(CustomRepositoryRestMvcConfiguration.class);
        webCtx.register(CustomMvcConfiguration.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(webCtx);
        ServletRegistration.Dynamic appServlet = servletContext.addServlet("exporter", dispatcherServlet);
        appServlet.setAsyncSupported(true);
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/");
	}

}
