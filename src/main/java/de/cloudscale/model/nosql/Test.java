/**
 * 
 */
package de.cloudscale.model.nosql;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Johannes Hiemer.
 *
 */
@Document
public class Test extends AbstractDocument {

	private String name;
	
	private String description;
	
	@DBRef
	private TestRelation testRelation;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TestRelation getTestRelation() {
		return testRelation;
	}

	public void setTestRelation(TestRelation testRelation) {
		this.testRelation = testRelation;
	}
	
}
