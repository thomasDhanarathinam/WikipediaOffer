/**
 * 
 */
package com.wiki.wikipedia.error;

import javax.persistence.EntityNotFoundException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.wiki.wikipedia.model.WikiOffer;

/**
 * @author thoma
 *
 */
//@Order(Ordered.HIGHEST_PRECEDENCE)
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class WikiOfferExceptionHandler extends ResponseEntityExceptionHandler {
	
	  @ExceptionHandler(WikiOfferNotFoundException.class) protected
	  ResponseEntity<Object> handleEntityNotFound( WikiOfferNotFoundException ex) {
	  System.out.println("Enter into errorhandling");
	  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	  
	  }
	 

	


}
