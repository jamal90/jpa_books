## Basic Setup for introducing Custom OData JPA Processor

1. Create class extending from **ODataJPADefaultProcessor** overriding methods as needed. This is the class that would **intercept** the request and response and modify the handling of the request as expected

    ```java
    package com.sap.tutorial.odata;

    import java.util.concurrent.atomic.AtomicInteger;

    import org.apache.olingo.odata2.api.exception.ODataException;
    import org.apache.olingo.odata2.api.processor.ODataResponse;
    import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
    import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
    import org.apache.olingo.odata2.jpa.processor.api.ODataJPADefaultProcessor;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    import com.sap.tutorial.jpaprocessor.BookJPAProcessor;

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
      ODataResponse oDataResponse = super.readEntitySet(uriParserResultView, contentType);

      // post-process
      long duration = System.currentTimeMillis() - start;
      LOG.info("Finished read access number '" + readCount + "' after '" + duration + "'ms.");

      return oDataResponse;
    }

    }

    ```

   You can also extend from the class **ODataJPAProcessor**, in which case it's expected to redefine all the methods like readEntitySet, readEntity, createEntity, deleteEntity, and so on.. 
   When overriding from **ODataJPADefaultProcessor** it gives the flexibility to only override required methods. 
   

2. Override the method **createCustomODataProcessor** of the class **ODataJPAServiceFactory**. This allows to pass the previously redefined JPA processor to the JPA Service Factory. 


    ```java
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

    ```

3. Now in the already existing **BooksODataJPAServiceFactory** implementation, repalce the super class from **ODataJAPServiceFactory** to the newly defined **CustomODataJPAServiceFactory**

    ```java
    package com.sap.tutorial.service;

    import javax.persistence.EntityManagerFactory;

    import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
    import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
    import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;

    import com.sap.tutorial.util.BooksJPAEntityManagerFactoryProvider;

    public class BooksODataJPAServiceFactory extends CustomODataJPAServiceFactory {

        private static final String PUNIT = "jpa_books";

        @Override
        public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
            ODataJPAContext oDataJPAContext2 = this.getODataJPAContext();

            // get the entity manager factory
            EntityManagerFactory emf = BooksJPAEntityManagerFactoryProvider.getEMF(PUNIT);
            oDataJPAContext2.setEntityManagerFactory(emf);
            oDataJPAContext2.setPersistenceUnitName(PUNIT);

            return oDataJPAContext2;
        }

    }

    ```

