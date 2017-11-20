package com.sap.tutorial.odata;

import java.util.Random;
import javax.persistence.PersistenceException;

import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.processor.ODataErrorCallback;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.tutorial.util.MessageBundle;

public class MinimalODataErrorCallback implements ODataErrorCallback {

	Logger LOG = LoggerFactory.getLogger(MinimalODataErrorCallback.class);
	
	@Override
	public ODataResponse handleError(ODataErrorContext context) throws ODataApplicationException {
		
		String corrId = "CR-" + new Random().nextInt(Integer.MAX_VALUE); // to use GUID identifying the logs uniquely
		LOG.error(context.getException().getClass().getName() + ":" + "Correlation ID - " + corrId + " -- " + context.getMessage());
		
		Exception ex = context.getException();
		if (ex instanceof PersistenceException){
			context.setMessage(MessageBundle.getInstance().getLocalizedText("INTERNAL_ERROR", corrId));
//			context.setMessage("An internal error occured with persisting the records. Check logs with Correlation ID - " + corrId); // TODO: read message from i18n and then apply localization
		}
			
		return EntityProvider.writeErrorDocument(context);
	}

}
