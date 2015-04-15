/**
 * 
 */
package de.cloudscale.model.nosql;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Johannes Hiemer.
 *
 */
@Document
public class TestRelation extends AbstractDocument {

	private String name;
	
	private String description;

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
	
}
