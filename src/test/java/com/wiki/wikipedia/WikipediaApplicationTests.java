package com.wiki.wikipedia;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.wiki.wikipedia.model.WikiOffer;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class WikipediaApplicationTests {

	@LocalServerPort
	int randomServerPort;

	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	/**
	 * Operation : Create wiki offer
	 * 
	 * @throws URISyntaxException The Wiki offer with id as 103 should be persisted
	 *                            in the database
	 *
	 *                            Expected result - HTTP status code should be 201.
	 */

	@Test
	public void postWikiOffer() throws URISyntaxException {
		WikiOffer wikiOffer = new WikiOffer(103L, "Offer for sany television", 12.09, "USD", new Date());
		ResponseEntity<WikiOffer> responseEntity = getRestTemplate().postForEntity(buildURI("/wikiOffer/createOffer"),
				wikiOffer, WikiOffer.class);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}

	/**
	 * Operation : Retrieve created wiki offer
	 * 
	 * @throws URISyntaxException The Wiki offer with ID as 101 and description as
	 *                            “Electronics product” records is available in the
	 *                            database and trying to retrieve the same.
	 * 
	 *                            Expected Result - HTTP status should be 200 OK
	 * 
	 */

	@Test
	public void getWikiOffer() throws URISyntaxException {
		ResponseEntity<WikiOffer> result = getRestTemplate().getForEntity(buildURI("/wikiOffer/getOffer/112"),
				WikiOffer.class);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals("Electronics product", result.getBody().getDescription());
	}

	/**
	 * Operation : Cancel created Wiki offer
	 * 
	 * 
	 * @throws URISyntaxException
	 * 
	 *                            Three steps should be passed
	 * 
	 *                            step 1
	 * 
	 *                            The Wiki offer with id as 104 should be persisted
	 *                            in the database
	 *
	 *                            Expected result - HTTP status code should be 201.
	 * 
	 *                            step 2
	 * 
	 *                            Retrieve wiki offer with id as 104 and do cancel
	 *                            the offer by changing the cancelled column as true
	 * 
	 *                            Expected result - HTTP status code should be 200.
	 * 
	 *                            step 3
	 * 
	 *                            Retrive wiki offer with id as 104 and do check the
	 *                            cancelled flag should has to be true
	 * 
	 *                            Expected result - Cancelled flag should be true
	 * 
	 */

	@Test
	public void cancelWikiOffer() throws URISyntaxException {

		/* step 1 */
		WikiOffer wikiOffer = new WikiOffer(104L, "Offer for sony television", 12.09, "USD", new Date());
		ResponseEntity<WikiOffer> responseEntityForCreation = getRestTemplate()
				.postForEntity(buildURI("/wikiOffer/createOffer"), wikiOffer, WikiOffer.class);
		assertEquals(HttpStatus.CREATED, responseEntityForCreation.getStatusCode());

		/* step 2 */
		ResponseEntity<String> responseEntityForDelete = getRestTemplate()
				.exchange(buildURI("/wikiOffer/cancelOffer/104"), HttpMethod.DELETE, null, String.class);
		assertEquals(HttpStatus.OK, responseEntityForDelete.getStatusCode());

		/* step 3 */
		ResponseEntity<WikiOffer> responseToRetriveCancelOffer = getRestTemplate()
				.getForEntity(buildURI("/wikiOffer/getOffer/104"), WikiOffer.class);
		assertEquals(HttpStatus.OK, responseToRetriveCancelOffer.getStatusCode());
		assertEquals(true, responseToRetriveCancelOffer.getBody().getCancelled());

	}

	/**
	 * Operation : Retrieve all non cancelled Wiki offer
	 * 
	 * @throws URISyntaxException
	 * 
	 *                            Retrieve all non-cancelled wiki offer Expected
	 *                            Result - Four(4) should be retrieved
	 * 
	 * 
	 */

	@Test
	public void checkAllWikiOffers() throws URISyntaxException {
		try {
			ResponseEntity<List> responseEntityForCreation = getRestTemplate()
					.getForEntity(buildURI("/wikiOffer/getOfferByMultiCond"), List.class);
			assertEquals(HttpStatus.OK, responseEntityForCreation.getStatusCode());
			assertEquals(4, responseEntityForCreation.getBody().size());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Operation : Retrieve all wiki offers first and then cancel one offer and
	 * again retrieve wiki offer and test the size of the record
	 * 
	 * @throws URISyntaxException
	 * 
	 *                            Test contains three steps
	 * 
	 *                            step 1 - Retrieve all offers and get the count
	 * 
	 *                            Expected Result - Retrieve Wiki offer size should
	 *                            be 4
	 * 
	 * 
	 *                            step 2 - Delete the wiki offer by changes the
	 *                            cancelled flag for wiki offer id 101
	 * 
	 *                            Expected result - Http status should be 200
	 * 
	 *                            step 3 - Retrieve all wiki offers.By default the
	 *                            API should not consider cancelled records wiki
	 *                            offer
	 * 
	 *                            Expected result - Retrieve Wiki offer size should
	 *                            be 3
	 * 
	 */
	@Test
	public void checkAllWikiOffersByCancellingOffer() throws URISyntaxException {
		try {
			/* step 1 */
			ResponseEntity<List> responseEntityForGet = getRestTemplate()
					.getForEntity(buildURI("/wikiOffer/getOfferByMultiCond"), List.class);
			assertEquals(HttpStatus.OK, responseEntityForGet.getStatusCode());
			assertEquals(4, responseEntityForGet.getBody().size());

			/* step 2 */
			ResponseEntity<String> responseEntityForDelete = getRestTemplate()
					.exchange(buildURI("/wikiOffer/cancelOffer/101"), HttpMethod.DELETE, null, String.class);
			assertEquals(HttpStatus.OK, responseEntityForDelete.getStatusCode());

			/* step 3 */
			ResponseEntity<List> responseEntityForAfterCancel = getRestTemplate()
					.getForEntity(buildURI("/wikiOffer/getOfferByMultiCond"), List.class);
			assertEquals(HttpStatus.OK, responseEntityForAfterCancel.getStatusCode());
			assertEquals(3, responseEntityForAfterCancel.getBody().size());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Operation : Retrieve all non cancelled Wiki offer with description as "Food
	 * Product"
	 * 
	 * @throws URISyntaxException
	 * 
	 *                            Retrieve all non-cancelled wiki offer
	 * 
	 *                            Expected Result - Four(4) should be retrieved
	 * 
	 * 
	 */

	@Test
	public void checkAllWikiOffersWithDescription() throws URISyntaxException {

		try {
			ResponseEntity<List> responseEntityForCreation = getRestTemplate()
					.getForEntity(buildURI("/wikiOffer/getOfferByMultiCond?description=Food%20product"), List.class);
			assertEquals(HttpStatus.OK, responseEntityForCreation.getStatusCode());
			assertEquals(1, responseEntityForCreation.getBody().size());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Operation : Retrieve all non cancelled Wiki offer by applying price range
	 * 
	 * @throws URISyntaxException
	 * 
	 *                            Retrieve all non-cancelled wiki offer by applying
	 *                            price range between 100 to 2100
	 * 
	 *                            Expected Result - Three(3) should be retrieved
	 * 
	 * 
	 */
	@Test
	public void checkAllWikiOffersWithPriceRange() throws URISyntaxException {

		try {
			ResponseEntity<List> responseEntityForCreation = getRestTemplate()
					.getForEntity(buildURI("/wikiOffer/getOfferByMultiCond?priceStart=100&priceEnd=2100"), List.class);
			assertEquals(HttpStatus.OK, responseEntityForCreation.getStatusCode());
			assertEquals(3, responseEntityForCreation.getBody().size());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public URI buildURI(String uriResource) throws URISyntaxException {
		final String ConstructbaseUrl = "http://localhost:" + randomServerPort + uriResource;
		return new URI(ConstructbaseUrl);
	}
}
