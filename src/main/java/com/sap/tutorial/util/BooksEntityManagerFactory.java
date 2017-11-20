package com.sap.tutorial.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BooksEntityManagerFactory {

	private static EntityManagerFactory emf; 
	public static EntityManagerFactory getEntityManagerFactory(String persistenceUnitName) {
		if (emf == null){
			synchronized (BooksEntityManagerFactory.class) {
				if (emf == null){
					try{
						emf = Persistence.createEntityManagerFactory(persistenceUnitName);
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}
			}
		}
		return emf; 
	}

}
