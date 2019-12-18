/**
 * 
 */
package com.wiki.wikipedia.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import com.wiki.wikipedia.model.WikiOffer;
import com.wiki.wikipedia.model.WikiOfferMultiConditions;

/**
 * @author thomas
 *
 */
public interface WikiOfferService {

	WikiOffer save(WikiOffer wikiOffer) throws Exception;

	ResponseEntity<WikiOffer> getWikiOfferById(Long id) throws Exception;

	ResponseEntity<String> cancelWikiOfferById(Long id) throws Exception;

	ResponseEntity<List<WikiOffer>> getWikiOfferByMultipleConditions(WikiOfferMultiConditions wikiOfferMultiConditions)
			throws Exception;

}
