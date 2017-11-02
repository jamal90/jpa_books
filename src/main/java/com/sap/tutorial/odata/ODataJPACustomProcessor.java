package com.sap.tutorial.odata;

import java.io.InputStream;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.EntityManager;

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPADefaultProcessor;
import org.apache.olingo.odata2.jpa.processor.core.ODataEntityParser;
import org.apache.olingo.odata2.jpa.processor.core.access.data.JPAEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.tutorial.model.Book;
import com.sap.tutorial.util.UserInfo;

public class ODataJPACustomProcessor extends ODataJPADefaultProcessor {

	private static final AtomicInteger READ_COUNT = new AtomicInteger(0);
	private static final Logger LOG = LoggerFactory.getLogger(ODataJPACustomProcessor.class);

	public ODataJPACustomProcessor(ODataJPAContext oDataJPAContext) {
		super(oDataJPAContext);
	}

	@Override
	public ODataResponse readEntitySet(GetEntitySetUriInfo uriParserResultView, String contentType)
			throws ODataException {

		// preprocess
		int readCount = READ_COUNT.incrementAndGet();
		LOG.info("Start read access number '" + readCount + "' for '"
				+ uriParserResultView.getTargetEntitySet().getName() + "'.");
		long start = System.currentTimeMillis();
		List<Object> jpaEntities = jpaProcessor.process(uriParserResultView);
		ODataResponse oDataResponse = responseBuilder.build(uriParserResultView, jpaEntities, contentType);

		// post process
		long duration = System.currentTimeMillis() - start;
		LOG.info("Finished read access number '" + readCount + "' after '" + duration + "'ms.");

		return oDataResponse;
	}

	@Override
	public ODataResponse createEntity(PostUriInfo uriParserResultView, InputStream content, String requestContentType,
			String contentType) throws ODataException {

		EdmEntitySet oDataEntitySet = uriParserResultView.getTargetEntitySet();
		EdmEntityType oDataEntityType = oDataEntitySet.getEntityType();

		switch (oDataEntityType.getName()) {
		case "Book":
			// implement the logic to add the administrative data
			if (content != null) {
				final JPAEntity virtualJPAEntity = new JPAEntity(oDataEntityType, oDataEntitySet, oDataJPAContext);

				final ODataEntityParser oDataEntityParser = new ODataEntityParser(oDataJPAContext);
				final ODataEntry oDataEntry = oDataEntityParser.parseEntry(oDataEntitySet, content, requestContentType,
						false);

				virtualJPAEntity.create(oDataEntry);
				Book jpaEntity = (Book) virtualJPAEntity.getJPAEntity();

				jpaEntity.setCreatedAt(Timestamp.from(Instant.now()));
				
				UserInfo currentUser = UserInfo.getCurrentUser(this.oDataJPAContext);
				jpaEntity.setCreatedBy(currentUser.getUserId()); 
				jpaEntity.setLastUpdatedAt(null);
				jpaEntity.setLastUpdatedBy(null);

				final EntityManager em = oDataJPAContext.getEntityManager();
				em.getTransaction().begin();
				em.persist(jpaEntity);
				em.getTransaction().commit();
				ODataResponse oDataResponse = responseBuilder.build(uriParserResultView, jpaEntity, contentType);
				return oDataResponse;
			}
		}

		return super.createEntity(uriParserResultView, content, requestContentType, contentType);
	}

}
