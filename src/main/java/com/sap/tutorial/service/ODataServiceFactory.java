package com.sap.tutorial.service;

import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;

import com.sap.tutorial.util.BooksEntityManagerFactory;

public class ODataServiceFactory extends ODataJPAServiceFactory {

	public static final String PUNIT = "jpa_books";
	@Override
	public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
		ODataJPAContext oDataJPAContext = this.getODataJPAContext();
		oDataJPAContext.setEntityManagerFactory(BooksEntityManagerFactory.getEntityManagerFactory(PUNIT));
		oDataJPAContext.setPersistenceUnitName(PUNIT);
		return oDataJPAContext;
	}

}
