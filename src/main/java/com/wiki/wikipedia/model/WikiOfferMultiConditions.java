/**
 * 
 */
package com.wiki.wikipedia.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author thomas
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WikiOfferMultiConditions {

	private String description;
	private String currency;
	private String priceStart;
	private String priceEnd;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expirationDateStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expirationDateEnd;

	public WikiOfferMultiConditions() {

	}

	public WikiOfferMultiConditions(String description, String currency, String priceStart, String priceEnd,
			Date expirationDateStart, Date expirationDateEnd) {
		super();
		this.description = description;
		this.currency = currency;
		this.priceStart = priceStart;
		this.priceEnd = priceEnd;
		this.expirationDateStart = expirationDateStart;
		this.expirationDateEnd = expirationDateEnd;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPriceStart() {
		return priceStart;
	}

	public void setPriceStart(String priceStart) {
		this.priceStart = priceStart;
	}

	public String getPriceEnd() {
		return priceEnd;
	}

	public void setPriceEnd(String priceEnd) {
		this.priceEnd = priceEnd;
	}

	public Date getExpirationDateStart() {
		return expirationDateStart;
	}

	public void setExpirationDateStart(Date expirationDateStart) {
		this.expirationDateStart = expirationDateStart;
	}

	public Date getExpirationDateEnd() {
		return expirationDateEnd;
	}

	public void setExpirationDateEnd(Date expirationDateEnd) {
		this.expirationDateEnd = expirationDateEnd;
	}

}
