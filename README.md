# WikipediaOffer
Wikipedia offer project


#Creation description

A merchant can create a offer for a product by providing Product Id, description , price, currency, expire date. 

#Assemption 

A merchant can only provide expire data as input while create a offer and developer assumes that the start data of the offer will begin on the offer creation date 

#Design 

Create Offer   -   			Method signature - ResponseEntity<WikiOffer> create(@Valid @RequestBody WikiOffer wikiOffer)
				   			
				   			Method Name : POST
				   			
				   			Parameter to set to create a offer 
				   			
				   			id
				   			
				   			Description
				   			
				   			price
				   			
				   			currency
				   			
				   			expirationDate
				   
Get Offer  -       			Method signature - ResponseEntity<WikiOffer> getWikiOfferById(@PathVariable Long id)
							
							Method Name : GET
				   			
				   			By passing offer ID
				   

Delete/Cancel offer			Method signature - ResponseEntity<String> cancelWikiOfferById(@PathVariable Long id)
							
							Method Name : DELETE
							
							By passing offer ID
							
Get All offers				Method signature - ResponseEntity<List<WikiOffer>> findWikiOfferByMultipleCondition
							
							Method Name : GET
							
							By passing id or description or price or currency or price range or date range
							
#Test coverage

Class Name : WikipediaApplicationTests

All  test cases are covered. Test case methods are ready to execute. 



