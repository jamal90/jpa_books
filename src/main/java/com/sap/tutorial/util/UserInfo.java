package com.sap.tutorial.util;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.security.um.service.UserManagementAccessor;
import com.sap.security.um.user.PersistenceException;
import com.sap.security.um.user.UnsupportedUserAttributeException;
import com.sap.security.um.user.User;
import com.sap.security.um.user.UserProvider;

public class UserInfo {
	private static final Logger LOG = LoggerFactory.getLogger(UserInfo.class);
	

	private String userId;

	private String email;

	private String firstName;

	private String lastName;
	
	private UserInfo(String userId, String email, String firstName, String lastName) {
		super();
		this.userId = userId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public static UserInfo getCurrentUser(ODataJPAContext oDataJPAContext) {
		HttpServletRequest request = (HttpServletRequest) oDataJPAContext.getODataContext()
				.getParameter(ODataContext.HTTP_SERVLET_REQUEST_OBJECT);
		
		if (request.getUserPrincipal() != null) {
			try {
				// UserProvider provides access to the user storage
				UserProvider users = UserManagementAccessor.getUserProvider();

				// Read the currently logged in user from the user storage
				User user = users.getUser(request.getUserPrincipal().getName());

				//	Assigned groups: user.getAttributeValues("groups")
				return new UserInfo(request.getUserPrincipal().getName(), user.getAttribute("email"), user.getAttribute("firstname"), user.getAttribute("lastname"));
				
			} catch (PersistenceException | UnsupportedUserAttributeException e) { // broad exception
				LOG.error("Error in fetching user context", e);
				throw new RuntimeException("No User Context");
			}
		}
		
		throw new RuntimeException("No User Context exists");
		
	}
	
	public String getEmail() {
		return email;
	}
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public String getUserId() {
		return userId;
	}

}
