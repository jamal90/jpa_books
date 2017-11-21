package com.sap.tutorial.odata;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPADefaultProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.tutorial.jpaprocessor.BookJPAProcessor;
import com.sap.tutorial.model.Book;

public class ODataJPACustomProcessor extends ODataJPADefaultProcessor {

	private static final AtomicInteger READ_COUNT = new AtomicInteger(0);
	private static final Logger LOG = LoggerFactory.getLogger(ODataJPACustomProcessor.class);
	private static final BookJPAProcessor bookJpaProcessor = new BookJPAProcessor();
	
	public ODataJPACustomProcessor(ODataJPAContext oDataJPAContext) {
		super(oDataJPAContext);
	}

	@Override
	public ODataResponse readEntitySet(GetEntitySetUriInfo uriParserResultView, String contentType)
			throws ODataException {

		// pre-process
		int readCount = READ_COUNT.incrementAndGet();
		LOG.info("Start read access number '" + readCount + "' for '"
				+ uriParserResultView.getTargetEntitySet().getName() + "'.");
		long start = System.currentTimeMillis();
		
		// process - calling default provider
		ODataResponse oDataResponse  = processsWithDefaultUserFilter(uriParserResultView, contentType);
//		ODataResponse oDataResponse = super.readEntitySet(uriParserResultView, contentType);    // DEFAULT 
		
		// post-process
		long duration = System.currentTimeMillis() - start;
		LOG.info("Finished read access number '" + readCount + "' after '" + duration + "'ms.");

		return oDataResponse;
	}

	private ODataResponse processsWithDefaultUserFilter(GetEntitySetUriInfo uriParserResultView, String contentType) throws ODataException {
		uriParserResultView.getFilter();
		ODataResponse odataResp = null; 
		
		String entityName = uriParserResultView.getTargetEntitySet().getEntityType().getName();
		switch(entityName){
			case "Book":
				Object whereClause  = "";
				if (uriParserResultView.getFilter() != null)
					whereClause = uriParserResultView.getFilter().accept(new JDBCFilterExpressionVisitor(uriParserResultView.getTargetEntitySet().getEntityType()));
				
				List<Object> jpaEntities = bookJpaProcessor.readEntitySet(whereClause, oDataJPAContext);
				odataResp =  responseBuilder.build(uriParserResultView, jpaEntities, contentType);
				break;
			default:
				odataResp = super.readEntitySet(uriParserResultView, contentType);
		}
		
		return odataResp; 
	}

	@Override
	public ODataResponse createEntity(PostUriInfo uriParserResultView, InputStream content, String requestContentType,
			String contentType) throws ODataException {

		EdmEntitySet oDataEntitySet = uriParserResultView.getTargetEntitySet();
		EdmEntityType oDataEntityType = oDataEntitySet.getEntityType();

		switch (oDataEntityType.getName()) {
		case "Book":
			Book createdEntiy = bookJpaProcessor.createEntity(uriParserResultView, content, requestContentType, contentType, this.oDataJPAContext);
			ODataResponse oDataResponse = responseBuilder.build(uriParserResultView, createdEntiy, contentType);
			return oDataResponse;
			
		case "ContributionByAuthor":
			throw new IllegalArgumentException("Not allowed to write to Calc View");
		}

		return super.createEntity(uriParserResultView, content, requestContentType, contentType);
	}

}
