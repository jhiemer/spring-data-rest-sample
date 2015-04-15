/**
 * 
 */
package de.cloudscale.web.test;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import de.cloudscale.config.CustomRepositoryRestMvcConfiguration;

/**
 * @author Johannes Hiemer.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration
@WebAppConfiguration
@ContextConfiguration(classes = {  
		CustomRepositoryRestMvcConfiguration.class, 
})
public abstract class MockMvcTest {
	
	protected MockMvc mockMvc;
	
	@Value("${auth.token.name}")
	protected String tokenName;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	
	@Before
    public final void initMockMvc() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

}
