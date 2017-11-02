package com.sap.tutorial.service;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;

import com.sap.tutorial.odata.CustomODataJPAServiceFactory;
import com.sap.tutorial.odata.MinimalODataErrorCallback;
import com.sap.tutorial.util.BooksEntityManagerFactory;

public class ODataServiceFactory extends CustomODataJPAServiceFactory {

	public static final String PUNIT = "jpa_books";
	@Override
	public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
		ODataJPAContext oDataJPAContext = this.getODataJPAContext();
		oDataJPAContext.setEntityManagerFactory(BooksEntityManagerFactory.getEntityManagerFactory(PUNIT));
		oDataJPAContext.setPersistenceUnitName(PUNIT);
		return oDataJPAContext;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ODataCallback> T getCallback(Class<T> callbackInterface) {
		
		T callback; 
		
		if (callbackInterface.isAssignableFrom(MinimalODataErrorCallback.class)){
			callback = (T) new MinimalODataErrorCallback();
		}else{
			callback = (T) super.getCallback(callbackInterface);
		}
		
		return callback; 
	}
	
	

}
