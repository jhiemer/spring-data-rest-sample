/**
 * 
 */
package de.cloudscale.web.documentation;

import static org.springframework.restdocs.RestDocumentation.document;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.RequestDispatcher;

import org.junit.Test;

import de.cloudscale.web.test.MockMvcTest;

/**
 * @author Johannes Hiemer.
 *
 */
public class BaseDocumentation extends MockMvcTest {
	

	@Test
	public void errorExample() throws Exception {
		this.mockMvc
			.perform(get("/error")
				.requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 500)
				.requestAttr(RequestDispatcher.ERROR_REQUEST_URI,
						"/users")
				.requestAttr(RequestDispatcher.ERROR_MESSAGE,
						"The user 'http://localhost:8080/users/123' does not exist"))
		.andDo(print()).andExpect(status().isInternalServerError())
		.andDo(document("error-example"));
	}

	@Test
	public void indexExample() throws Exception {
		this.mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andDo(document("index-example")/*.withLinks(halLinks(),
				linkWithRel("users").description(
						"The <<resources-users,Users resource>>"),
				linkWithRel("addresses").description(
						"The <<resources-addresses,Addresses resource>>"),
				linkWithRel("alps").description(
						"The ALPS profile for the service"))*/);
	}

}
