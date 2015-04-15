/**
 * 
 */
package de.cloudscale.model.nosql.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.cloudscale.model.nosql.TestRelation;

/**
 * @author Johannes Hiemer.
 *
 */
public abstract interface TestRelationRepository extends CrudRepository<TestRelation, ObjectId>, 
	PagingAndSortingRepository<TestRelation, ObjectId> {
	
}
