package com.sap.tutorial.jpaprocessor;

import java.io.InputStream;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.core.ODataEntityParser;
import org.apache.olingo.odata2.jpa.processor.core.access.data.JPAEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.tutorial.model.Book;
import com.sap.tutorial.util.UserInfo;

public class BookJPAProcessor {

	private static final Logger LOG = LoggerFactory.getLogger(BookJPAProcessor.class);
	
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

	@SuppressWarnings("unchecked")
	public List<Object> readEntitySet(Object whereClause, ODataJPAContext oDataJPAContext) {
		
		String finalFilterExp = ""; 
		// default user filter
		String userFilter = "p.createdBy = :user_id";
		
		if (whereClause == null || "".equals(whereClause)){
			finalFilterExp = " where " + userFilter;
		}else{
			finalFilterExp += whereClause + " AND " + userFilter;
		}
		
		final EntityManager em = oDataJPAContext.getEntityManager();
		String jpqlQuery =  "select p from Book p" + finalFilterExp;
		LOG.info(jpqlQuery);
		Query bookQuery = em.createQuery(jpqlQuery);
		bookQuery.setParameter("user_id", UserInfo.getCurrentUser(oDataJPAContext).getUserId());
		return bookQuery.getResultList();
	}

}
