package com.sap.tutorial.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

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
