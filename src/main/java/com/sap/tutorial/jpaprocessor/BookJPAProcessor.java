package com.sap.tutorial.jpaprocessor;

import java.io.InputStream;
import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.EntityManager;

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.core.ODataEntityParser;
import org.apache.olingo.odata2.jpa.processor.core.access.data.JPAEntity;

import com.sap.tutorial.model.Book;
import com.sap.tutorial.util.UserInfo;

public class BookJPAProcessor {

	public Book createEntity(PostUriInfo uriParserResultView, InputStream content, String requestContentType,
			String contentType, ODataJPAContext oDataJPAContext) throws ODataException {

		// implement the logic to add the administrative data
		if (content != null) {

			EdmEntitySet oDataEntitySet = uriParserResultView.getTargetEntitySet();
			EdmEntityType oDataEntityType = oDataEntitySet.getEntityType();
			
			final JPAEntity virtualJPAEntity = new JPAEntity(oDataEntityType, oDataEntitySet, oDataJPAContext);
			final ODataEntityParser oDataEntityParser = new ODataEntityParser(oDataJPAContext);
			final ODataEntry oDataEntry = oDataEntityParser.parseEntry(oDataEntitySet, content, requestContentType, false);

			virtualJPAEntity.create(oDataEntry);
			Book jpaEntity = (Book) virtualJPAEntity.getJPAEntity();

			jpaEntity.setCreatedAt(Timestamp.from(Instant.now()));

			UserInfo currentUser = UserInfo.getCurrentUser(oDataJPAContext);
			jpaEntity.setCreatedBy(currentUser.getUserId());
			jpaEntity.setLastUpdatedAt(null);
			jpaEntity.setLastUpdatedBy(null);

			final EntityManager em = oDataJPAContext.getEntityManager();
			em.getTransaction().begin();
			em.persist(jpaEntity);
			em.getTransaction().commit();
			return jpaEntity;
		}
		
		throw new ODataException("Entity content is empty"); 

	}

}
