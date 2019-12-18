/**
 * 
 */
package com.wiki.wikipedia.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.wiki.wikipedia.error.WikiOfferNotFoundException;
import com.wiki.wikipedia.model.WikiOffer;
import com.wiki.wikipedia.model.WikiOfferMultiConditions;
import com.wiki.wikipedia.repository.WikiOfferRepository;

/**
 * @author thomas
 *
 */
@Service
public class WikiOfferServiceImpl implements WikiOfferService {

	private static final Logger logger = LoggerFactory.getLogger(WikiOfferServiceImpl.class);

	@Autowired
	WikiOfferRepository wikiOfferRepository;

	@Autowired
	EntityManager entityManager;

	public WikiOffer save(WikiOffer wikiOffer) throws Exception {
		logger.info("In save(WikiOffer wikiOffer) in WikiOfferServiceImpl class ");
		WikiOffer responseWikiOffer = null;
		try {
			responseWikiOffer = wikiExpireDateValidation(wikiOfferRepository.save(wikiOffer));
		} catch (Exception ex) {
			logger.error("Error in  save(WikiOffer wikiOffer) in WikiOfferServiceImpl class");
			throw ex;
		}
		return responseWikiOffer;
	}

	/*
	 * public ResponseEntity<WikiOffer> getWikiOfferById(Long id) { logger.
	 * info("In getWikiOfferById(Long id) in WikiOfferServiceImpl class- Offer Id "
	 * + id); ResponseEntity<WikiOffer> responseEntity = null; try {
	 * Optional<WikiOffer> wikiOfferOptional = getWikiOfferByid(id); responseEntity
	 * = wikiOfferOptional.map(wikiOffer -> getOptionalResponseEntity(wikiOffer))
	 * .orElseThrow(() -> new WikiOfferNotFoundException()); } catch (Exception ex)
	 * { logger.
	 * error("Error in  getWikiOfferById(Long id) in WikiOfferServiceImpl class");
	 * throw ex; } return responseEntity; }
	 */
	 
	 
	 public ResponseEntity<WikiOffer> getWikiOfferById(Long id) {
			logger.info("In getWikiOfferById(Long id) in WikiOfferServiceImpl class- Offer Id " + id);
			ResponseEntity<WikiOffer> responseEntity = null;
			WikiOffer wikioffer = null;
			try {
				Optional<WikiOffer> wikiOfferOptional = getWikiOfferByid(id);
				wikioffer = wikiOfferOptional.map(wikiOffer -> wikiExpireDateValidation(wikiOffer))
						.orElseThrow(() -> new WikiOfferNotFoundException());
				
				responseEntity = ResponseEntity.ok().body(wikioffer);
			} catch (Exception ex) {
				logger.error("Error in  getWikiOfferById(Long id) in WikiOfferServiceImpl class");
				throw ex;
			}
			return responseEntity;
		}

	public ResponseEntity<String> cancelWikiOfferById(Long id) {
		logger.info("In cancelWikiOfferById(Long id) in WikiOfferServiceImpl class- Offer Id " + id);
		ResponseEntity<String> responseEntiry = null;
		try {
			Optional<WikiOffer> wikiOfferOptional = getWikiOfferByid(id);
			responseEntiry = cancelWikiOfferByIdDecision(wikiOfferOptional) ? ResponseEntity.ok("Ok")
					: ResponseEntity.notFound().build();
		} catch (Exception ex) {
			logger.error("Error in  cancelWikiOfferById(Long id) in WikiOfferServiceImpl class");
			throw ex;
		}
		return responseEntiry;
	}

	public boolean cancelWikiOfferByIdDecision(Optional<WikiOffer> wikiOfferOptional) {
		logger.info(
				"In cancelWikiOfferByIdDecision(Optional<WikiOffer> wikiOfferOptional) in WikiOfferServiceImpl class ");
		boolean cancelFlag = false;
		try {
			if (wikiOfferOptional.isPresent()) {
				wikiOfferOptional.get().setCancelled(true);
				wikiOfferRepository.save(wikiOfferOptional.get());
				cancelFlag = true;
			}
		} catch (Exception ex) {
			logger.error("Error in  cancelWikiOfferById(Long id) in WikiOfferServiceImpl class");
			throw ex;
		}
		return cancelFlag;
	}

	private Optional<WikiOffer> getWikiOfferByid(Long id) {
		logger.info("In getWikiOfferByid(Long id) in WikiOfferServiceImpl class- Offer Id " + id);
		Optional<WikiOffer> responseWikiOfferOptional = null;
		try {
			responseWikiOfferOptional = wikiOfferRepository.findById(id);
		} catch (Exception ex) {
			logger.error("Error in  getWikiOfferByid(Long id) in WikiOfferServiceImpl class");
			throw ex;
		}
		return responseWikiOfferOptional;
	}

	public ResponseEntity<List<WikiOffer>> getWikiOfferByMultipleConditions(
			WikiOfferMultiConditions wikiOfferMultiConditions) {
		logger.info(
				"In getWikiOfferByMultipleConditions(WikiOfferMultiConditions wikiOfferMultiConditions)  in WikiOfferServiceImpl class");
		ResponseEntity<List<WikiOffer>> responseEntity = null;
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<WikiOffer> cq = cb.createQuery(WikiOffer.class);
			TypedQuery<WikiOffer> query = entityManager
					.createQuery(prepareWhereCondition(cb, cq, wikiOfferMultiConditions));
			List<WikiOffer> listOfWikiOffers = prepareCollectionOfOffers(query.getResultList());
			if (null != listOfWikiOffers && listOfWikiOffers.size() > 0) {
				responseEntity = ResponseEntity.ok(listOfWikiOffers);
			} else {
				responseEntity = ResponseEntity.notFound().build();
			}
		} catch (Exception ex) {
			logger.error("Error in  getWikiOfferByid(Long id) in WikiOfferServiceImpl class");
			throw ex;
		}

		return responseEntity;

	}

	public List<WikiOffer> prepareCollectionOfOffers(List<WikiOffer> listOfwikiOffers) {
		logger.info("In prepareCollectionOfOffers(List<WikiOffer> listOfwikiOffers) in WikiOfferServiceImpl class ");
		List<WikiOffer> listOfWikiOffer = null;
		try {
			listOfWikiOffer = listOfwikiOffers.stream().map(wikiOffer -> wikiExpireDateValidation(wikiOffer))
					.collect(Collectors.toList());
		} catch (Exception ex) {
			logger.error(
					"Error in  prepareCollectionOfOffers(List<WikiOffer> listOfwikiOffers) in WikiOfferServiceImpl class");
			throw ex;
		}
		return listOfWikiOffer;
	}

	public CriteriaQuery<WikiOffer> prepareWhereCondition(CriteriaBuilder cb, CriteriaQuery<WikiOffer> cq,
			WikiOfferMultiConditions wikiOfferMultiConditions) {
		logger.info(
				"In prepareWhereCondition(CriteriaBuilder cb, CriteriaQuery<WikiOffer> cq,WikiOfferMultiConditions wikiOfferMultiConditions) in WikiOfferServiceImpl class ");
		Predicate where = null;
		
		
		try {
			Root<WikiOffer> wikiOffer = cq.from(WikiOffer.class);
			where = cb.conjunction();
			where = cb.equal(wikiOffer.get("cancelled"), false);

			if (!StringUtils.isEmpty(wikiOfferMultiConditions.getDescription())) {
				where = cb.and(where,
						cb.equal(wikiOffer.get("description"), wikiOfferMultiConditions.getDescription()));
			}
			if (!(StringUtils.isEmpty(wikiOfferMultiConditions.getExpirationDateStart())
					&& StringUtils.isEmpty(wikiOfferMultiConditions.getExpirationDateEnd()))) {
				where = cb.and(where,
						cb.between(wikiOffer.get("expirationDate"), wikiOfferMultiConditions.getExpirationDateStart(),
								wikiOfferMultiConditions.getExpirationDateEnd()));
			}

			if (!(StringUtils.isEmpty(wikiOfferMultiConditions.getPriceStart())
					&& StringUtils.isEmpty(wikiOfferMultiConditions.getPriceEnd()))) {
				where = cb.and(where,
						cb.between(wikiOffer.get("price"), Double.valueOf(wikiOfferMultiConditions.getPriceStart()),
								Double.valueOf(wikiOfferMultiConditions.getPriceEnd())));
			}
		} catch (Exception ex) {
			logger.error(
					"Error in   prepareWhereCondition(CriteriaBuilder cb, CriteriaQuery<WikiOffer> cq,WikiOfferMultiConditions wikiOfferMultiConditions) in WikiOfferServiceImpl class");
			throw ex;
		}
		return cq.where(where);

	}

	public ResponseEntity<WikiOffer> getOptionalResponseEntity(WikiOffer wikiOffer) {
		logger.info("In getOptionalResponseEntity(WikiOffer wikiOffer) in WikiOfferServiceImpl class ");
		ResponseEntity<WikiOffer> responseEntity = null;
		try {
			responseEntity = ResponseEntity.ok(wikiExpireDateValidation(wikiOffer));
		} catch (Exception ex) {
			logger.error("Error in  getOptionalResponseEntity(WikiOffer wikiOffer) in WikiOfferServiceImpl class");
			throw ex;
		}
		return responseEntity;
	}

	public WikiOffer wikiExpireDateValidation(WikiOffer wikiOffer) {
		logger.info("In  wikiExpireDateValidation(WikiOffer wikiOffer) in WikiOfferServiceImpl class ");
		WikiOffer wikiOfferResponse = null;
		try {
			wikiOfferResponse = expireFlagForProduct(wikiOffer) ? wikiOffer.wikiExpireProduct(wikiOffer) : wikiOffer;
		} catch (Exception ex) {
			logger.error("Error in  wikiExpireDateValidation(WikiOffer wikiOffer) in WikiOfferServiceImpl class");
			throw ex;
		}
		return wikiOfferResponse;

	}

	public boolean expireFlagForProduct(WikiOffer wikiOffer) {
		logger.info("In  expireFlagForProduct(WikiOffer wikiOffer) in WikiOfferServiceImpl class ");
		boolean expireFlag;
		try {
			expireFlag = getCurrentDate().after(wikiOffer.getExpirationDate());
		} catch (Exception ex) {
			logger.error("Error in  expireFlagForProduct(WikiOffer wikiOffer) in WikiOfferServiceImpl class");
			throw ex;
		}
		return expireFlag;
	}

	public Date getCurrentDate() {
		return new Date();
	}

	public static Logger getLogger() {
		return logger;
	}

}
