package com.sap.tutorial.odata;

import java.util.ResourceBundle;

import org.apache.olingo.odata2.api.ODataDebugCallback;

public class RestrictedDebugCallback implements ODataDebugCallback {

	ResourceBundle config = ResourceBundle.getBundle("AppConfig");
	
	@Override
	public boolean isDebugEnabled() {
		boolean isDebugEnabled = Boolean.parseBoolean(config.getString("DEBUG_ENABLED"));
		return isDebugEnabled; 
	}
}
