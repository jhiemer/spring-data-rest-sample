/**
 * 
 */
package de.cloudscale.model.nosql;

import org.springframework.data.rest.core.config.Projection;

/**
 * @author Johannes Hiemer
 *
 */
@Projection(types = Test.class)
public interface TestTestRelationProjection {
	
	String getName();
	
	String getDescription();
	
	TestRelation getTestRelation();

}
