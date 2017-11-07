## Developing OData Service 

In this document, we'll look into how to convert the JPA entities that we have created into the EDM entities, and enable the ODataJPAProcessor to serve the request. 

1. Importing the required maven dependencies


```pom
   
  <properties>
    <odata.olingo.version>2.0.9</odata.olingo.version>
  </properties>
   
   <dependencies>   
      <!-- olingo core -->
      <dependency>
          <groupId>org.apache.cxf</groupId>
          <artifactId>cxf-rt-frontend-jaxrs</artifactId>
          <version>2.7.5</version>
      </dependency>

      <dependency>
          <groupId>org.apache.olingo</groupId>set
          <artifactId>olingo-odata2-core</artifactId>
          <version>${odata.olingo.version}</version>
      </dependency>

      <dependency>
          <groupId>org.apache.olingo</groupId>
          <artifactId>olingo-odata2-api</artifactId>
          <version>${odata.olingo.version}</version>
      </dependency>

      <!-- jpa odata processors -->
      <dependency>
          <groupId>org.apache.olingo</groupId>
          <artifactId>olingo-odata2-jpa-processor-api</artifactId>
          <version>${odata.olingo.version}</version>
      </dependency>

      <dependency>
          <groupId>org.apache.olingo</groupId>
          <artifactId>olingo-odata2-jpa-processor-core</artifactId>
          <version>${odata.olingo.version}</version>
      </dependency>

      <dependency>
          <groupId>org.apache.olingo</groupId>
          <artifactId>olingo-odata2-jpa-processor-ref</artifactId>
          <version>${odata.olingo.version}</version>
      </dependency>
      ....     
   </dependencies>
```


2. Add the OData Servlet in web.xml. 
   This is similar to the servlet that we have developed earlier. 
   
   
```xml

  <!-- odata servlet -->
	<servlet>
		<servlet-name>ODataServlet</servlet-name>
		<servlet-class>org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet</servlet-class>
		<init-param>
			<param-name>javax.ws.rs.Application</param-name>
			<param-value>org.apache.olingo.odata2.core.rest.app.ODataApplication</param-value>
		</init-param>
		<init-param>
			<param-name>org.apache.olingo.odata2.service.factory</param-name>
			<param-value>com.sap.tutorial.service.ODataServiceFactory</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet>

	<servlet-mapping>
		<servlet-name>ODataServlet</servlet-name>
		<url-pattern>/Books.svc/*</url-pattern>
	</servlet-mapping>

```
     
3. Create the BookODataJpaServiceFactory that given as init param to the ODataServlet. The ODataJPAServiceFactory extends the ODataJPAServiceFactory

```java

package com.sap.tutorial.service;

import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAServiceFactory;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;

import com.sap.tutorial.util.BooksEntityManagerFactory;

public class BookODataServiceFactory extends ODataJPAServiceFactory {

	public static final String PUNIT = "jpa_books";
	@Override
	public ODataJPAContext initializeODataJPAContext() throws ODataJPARuntimeException {
		ODataJPAContext oDataJPAContext = this.getODataJPAContext();
		oDataJPAContext.setEntityManagerFactory(BooksEntityManagerFactory.getEntityManagerFactory(PUNIT));
		oDataJPAContext.setPersistenceUnitName(PUNIT);
		return oDataJPAContext;
	}

}

```

4. Also, create the class that provides a <b>Singleton</b> of EntityManagerFactory for the entire JVM runtime 

```java
package com.sap.tutorial.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BooksEntityManagerFactory {

	private static EntityManagerFactory emf; 
	public static EntityManagerFactory getEntityManagerFactory(String persistenceUnitName) {
		if (emf == null){
			synchronized (BooksEntityManagerFactory.class) {
				if (emf == null){
					emf = Persistence.createEntityManagerFactory(persistenceUnitName);
				}
			}
		}
		return emf; 
	}

}

```

5. Files that you'd have altered / added are highlighted below. 

![image](https://user-images.githubusercontent.com/15712474/32520938-be664586-c3df-11e7-8cfe-ef888a466698.png)




