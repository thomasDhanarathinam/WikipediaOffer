package com.wiki.wikipedia.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author thomas
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "Wikioffer")
public class WikiOffer {

	@Id
	private Long id;
	@NotEmpty(message = "Please provide a description")
	private String description;
	@NotNull(message = "Please provide a price")
	private Double price;
	@NotEmpty(message = "Please provide a currency")
	private String currency;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@NotNull(message = "Please provide a expire date")
	private Date expirationDate;

	private Boolean cancelled = false;
	@Transient
	private Boolean expired = false;

	public WikiOffer() {
	}

	public WikiOffer(Long id, String description, Double price, String currency, Date expirationDate, Boolean expired,
			Boolean cancelled) {
		super();
		this.id = id;
		this.description = description;
		this.price = price;
		this.currency = currency;
		this.expirationDate = expirationDate;
		this.expired = expired;
		this.cancelled = cancelled;
	}

	public WikiOffer(Long id, String description, Double price, String currency, Date expirationDate) {
		super();
		this.id = id;
		this.description = description;
		this.price = price;
		this.currency = currency;
		this.expirationDate = expirationDate;

	}

	public WikiOffer wikiExpireProduct(WikiOffer wikiOffer) {
		return new WikiOffer(wikiOffer.getId(), wikiOffer.getDescription(), wikiOffer.getPrice(),
				wikiOffer.getCurrency(), wikiOffer.getExpirationDate(), true, wikiOffer.cancelled);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Boolean getCancelled() {
		return cancelled;
	}

	public void setCancelled(Boolean cancelled) {
		this.cancelled = cancelled;
	}

	public Boolean getExpired() {
		return expired;
	}

	public void setExpired(Boolean expired) {
		this.expired = expired;
	}

	@Override
	public String toString() {
		return "WikiOffer [id=" + id + ", description=" + description + ", price=" + price + ", currency=" + currency
				+ ", expirationDate=" + expirationDate + ", cancelled=" + cancelled + ", expired=" + expired + "]";
	}

}
