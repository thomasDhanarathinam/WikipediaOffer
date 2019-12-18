/**
 * 
 */
package com.wiki.wikipedia.controller;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wiki.wikipedia.model.WikiOffer;
import com.wiki.wikipedia.model.WikiOfferMultiConditions;
import com.wiki.wikipedia.service.WikiOfferService;

/**
 * @author thomas
 *
 */
@RestController
@Validated
public class WikiOfferController {
	private static final Logger logger = LoggerFactory.getLogger(WikiOfferController.class);

	@Autowired
	private WikiOfferService wikiOfferService;

	/**
	 * 
	 * @param wikiOffer
	 * @return ResponseEntity<WikiOffer>
	 * 
	 *         Description : Create wiki offer
	 */
	@PostMapping(path = "wikiOffer/createOffer", consumes = "application/json", produces = "application/json")
	public ResponseEntity<WikiOffer> create(@Valid @RequestBody WikiOffer wikiOffer) {
		logger.info("In wikiOffer/createOffer in WikiOfferController class ");
		ResponseEntity<WikiOffer> responseEntity = null;
		try {
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(wikiOfferService.save(wikiOffer));
		} catch (Exception ex) {
			logger.error("Error in wikiOffer/createOffer request : " + ex.getMessage());
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return responseEntity;
	}

	/**
	 * 
	 * @param id
	 * @return ResponseEntity<WikiOffer>
	 * 
	 *         Description : Retrieve wiki offer by ID
	 */

	@GetMapping(path = "wikiOffer/getOffer/{id}", produces = "application/json")
	public ResponseEntity<WikiOffer> getWikiOfferById(@PathVariable Long id) {
		logger.info("In wikiOffer/getOffer/{id} in WikiOfferController class with Id: {} ", id);
		ResponseEntity<WikiOffer> responseEntity = null;
		try {
			responseEntity = wikiOfferService.getWikiOfferById(id);
		} catch (Exception ex) {
			logger.error("Error in wikiOffer/getOffer/{id} request : " + ex.getMessage());
			responseEntity = ResponseEntity.notFound().build();
		}
		return responseEntity;
	}

	/**
	 * 
	 * @param id
	 * @return ResponseEntity<String>
	 * 
	 *         Description : Delete wiki offer by ID(Cancelled flag will change to
	 *         true)
	 */
	@DeleteMapping(path = "wikiOffer/cancelOffer/{id}", produces = "application/json")
	public ResponseEntity<String> cancelWikiOfferById(@PathVariable Long id) {
		logger.info("In wikiOffer/cancelOffer/{id} in WikiOfferController class with Id: {}", id);
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = wikiOfferService.cancelWikiOfferById(id);
		} catch (Exception ex) {
			logger.error("Error in wikiOffer/cancelOffer/{id} request : " + ex.getMessage());
			responseEntity = ResponseEntity.notFound().build();
		}
		return responseEntity;
	}

	/**
	 * 
	 * @param wikiOfferMultiConditions
	 * @return ResponseEntity<List<WikiOffer>>
	 * 
	 *         Description : Retrieve wiki offer based of description or currency or
	 *         price or experie date
	 */
	@GetMapping(path = "wikiOffer/getOfferByMultiCond", produces = "application/json")
	public ResponseEntity<List<WikiOffer>> findWikiOfferByMultipleCondition(
			@RequestParam(required = false) String description, @RequestParam(required = false) String currency,
			@RequestParam(required = false) String priceStart, @RequestParam(required = false) String priceEnd,
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date expirationDateStart,
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date expirationDateEnd) {
		logger.info(
				"In wikiOffer/getOfferByMultiCond in WikiOfferController class with parameter : description : {}, currency : {} ,priceStart : {}, priceEnd : {}, expirationDateStart : {}, expirationDateEnd :{} ",
				description, currency, priceStart, priceEnd, expirationDateStart, expirationDateEnd);
		ResponseEntity<List<WikiOffer>> responseEntity = null;
		WikiOfferMultiConditions wikiOfferMultiConditions;
		try {
			wikiOfferMultiConditions = new WikiOfferMultiConditions(description, currency, priceStart, priceEnd,
					expirationDateStart, expirationDateEnd);
			responseEntity = wikiOfferService.getWikiOfferByMultipleConditions(wikiOfferMultiConditions);
		} catch (Exception ex) {
			logger.error("Error in wikiOffer/getOfferByMultiCond request : " + ex.getMessage());
			responseEntity = ResponseEntity.notFound().build();
		}
		return responseEntity;
	}

	public static Logger getLogger() {
		return logger;
	}

}
