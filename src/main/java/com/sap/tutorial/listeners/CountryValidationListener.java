package com.sap.tutorial.listeners;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.sap.tutorial.model.Author;
import com.sap.tutorial.model.Publisher;

public class CountryValidationListener {
	
	// sample list of countries - to be fetched from DB table
	private static List<String> countryList = 	new ArrayList<>();	 
	
	static {
		countryList.add("US");
		countryList.add("CA");
		countryList.add("IN");
		countryList.add("DE");
	}
	
	@PrePersist
	@PreUpdate
	public void validateCountry(Object obj){
		
		// check the object is either of type Publisher or Author
		if (obj instanceof Publisher){
			Publisher p1 = (Publisher) obj;
			checkCountry(p1.getCountryCode());
		}else if (obj instanceof Author){
			Author author = (Author) obj;
			checkCountry(author.getCountryCode());
		}else{
			throw new IllegalArgumentException("Validation failed for country check");
		}
		
	}
	
	private boolean checkCountry(String countryCode){
		
		if (countryList.contains(countryCode)){
			return true; 
		}
		
		throw new IllegalStateException("Invalid value given for the attribute country");
	}
	
	
}
