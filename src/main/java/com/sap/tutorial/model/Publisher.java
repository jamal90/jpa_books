package com.sap.tutorial.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the "jpa_test.model::books.Publisher" database table.
 * 
 */
@Entity
@Table(name="\"JPA_TEST\".\"jpa_test.model::books.Publisher\"")
@NamedQuery(name="Publisher.findAll", query="SELECT p FROM Publisher p")
@SequenceGenerator(sequenceName="\"JPA_TEST\".\"jpa_test.sequences::PublisherSequence\"", name = "PublisherSequenceGen", allocationSize=1)
@Cacheable(false)

public class Publisher implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"PublisherID\"")
	@GeneratedValue(generator="PublisherSequenceGen", strategy=GenerationType.SEQUENCE)
	private int publisherID;

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

	@Column(name="\"Name\"")
	private String name;

	public Publisher() {
	}

	public int getPublisherID() {
		return this.publisherID;
	}

	public void setPublisherID(int publisherID) {
		this.publisherID = publisherID;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public int getHouseNumber() {
		return this.houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}