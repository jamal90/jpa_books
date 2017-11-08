package com.sap.tutorial.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the "jpa_test.model::books.Author" database table.
 * 
 */
@Entity
@Table(name="\"JPA_TEST\".\"jpa_test.model::books.Author\"")
@Cacheable(false)
@NamedQuery(name="Author.findAll", query="SELECT a FROM Author a")
@SequenceGenerator(sequenceName="\"JPA_TEST\".\"jpa_test.sequences::AuthorSequence\"", name = "AuthorSequenceGen", allocationSize=1)

@org.eclipse.persistence.annotations.Cache
public class Author implements Serializable {
	private static final long serialVersionUID = 1L;
	private static List<String> countryList = 	new ArrayList<>();	// sample list of countries - to be fetched from DB table 
			
	static {
		countryList.add("US");
		countryList.add("CA");
		countryList.add("IN");
		countryList.add("DE");
	}
	
	@Id
	@Column(name="\"AuthorID\"")
	@GeneratedValue(generator="AuthorSequenceGen", strategy=GenerationType.SEQUENCE)
	private int authorID;

	@Temporal(TemporalType.DATE)
	@Column(name="\"DoB\"")
	private Date doB;

	@Column(name="\"Gender\"")
	private String gender;

	@Column(name="\"Name.firstName\"", length = 80)
	private String firstName;

	@Column(name="\"Name.lastName\"")
	private String lastName;

	@Column(name="\"Name.middleName\"")
	private String middleName;
	
	@Column(name="\"Location.city\"")
	private String city;

	@Column(name="\"Location.countryCode\"")
	private String countryCode;

	@Column(name="\"Location.number\"")
	private int houseNumber;

	@Column(name="\"Location.street\"")
	private String street;

	@Column(name="\"Location.zipCode\"")
	private String zipcode;
	
	public Author() {
	}

	public int getAuthorID() {
		return this.authorID;
	}

	public String getCity() {
		return city;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public Date getDoB() {
		return this.doB;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getGender() {
		return this.gender;
	}

	public int getHouseNumber() {
		return houseNumber;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public String getStreet() {
		return street;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public void setDoB(Date doB) {
		this.doB = doB;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	@PrePersist
	@PreUpdate
	public void checkValidations(){
		// check the country code is from the list of entities
		if (!countryList.contains(this.countryCode)){
			throw new RuntimeException("CountryCode Not valid");
		}
	}
	
	
}