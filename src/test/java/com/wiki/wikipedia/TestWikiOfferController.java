/**
 * 
 */
package com.wiki.wikipedia;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PathVariable;

import com.wiki.wikipedia.controller.WikiOfferController;
import com.wiki.wikipedia.model.WikiOffer;
import com.wiki.wikipedia.service.WikiOfferService;

import ch.qos.logback.core.status.Status;

/**
 * @author thoma
 *
 */

@ActiveProfiles("test")

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TestWikiOfferController {
	
	  @Autowired
	   private TestEntityManager entityManager;
	
	@Autowired
	WikiOfferService wikiOfferService;
	
	  @Test
	public void testGet() {
		
	}
	
	

}
