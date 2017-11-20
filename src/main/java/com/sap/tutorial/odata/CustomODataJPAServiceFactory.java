package com.sap.tutorial.odata;

import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;

public abstract class CustomODataJPAServiceFactory extends ODataJPAServiceFactory {

	@Override
	public ODataSingleProcessor createCustomODataProcessor(ODataJPAContext oDataJPAContext) {
		return new ODataJPACustomProcessor(oDataJPAContext);
	}

}
