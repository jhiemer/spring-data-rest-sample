/**
 * 
 */
package de.cloudscale.model.nosql.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.cloudscale.model.nosql.Test;

/**
 * @author Johannes Hiemer.
 *
 */
public abstract interface TestRepository extends CrudRepository<Test, ObjectId>, 
	PagingAndSortingRepository<Test, ObjectId> {
	
}
