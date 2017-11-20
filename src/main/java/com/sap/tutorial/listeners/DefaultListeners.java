package com.sap.tutorial.listeners;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class DefaultListeners {
	@PrePersist
	public void beforePersist(Object obj) {
		// TODO: logger
		System.out.println("Before Persisting the obj" + obj.toString());
	}
	
	@PreUpdate
	public void beforeUpdate(Object obj) {
		// TODO: logger
		System.out.println("Before udpating the obj" + obj.toString());
	}
}
