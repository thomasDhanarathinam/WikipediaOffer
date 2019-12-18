package com.wiki.wikipedia.error;

public class WikiOfferNotFoundException extends RuntimeException{
	
	public WikiOfferNotFoundException() {
		super("Entity not found");
	}
	

}
