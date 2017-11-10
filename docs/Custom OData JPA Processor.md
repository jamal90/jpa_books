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
   

2. 
