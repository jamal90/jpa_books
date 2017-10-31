package com.sap.tutorial.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the "jpa_test.model::books.Author" database table.
 * 
 */
@Entity
@Table(name="\"JPA_TEST\".\"jpa_test.model::books.Author\"")
@NamedQuery(name="Author.findAll", query="SELECT a FROM Author a")
@SequenceGenerator(sequenceName="\"JPA_TEST\".\"jpa_test.sequences::AuthorSequence\"", name = "AuthorSequenceGen", allocationSize=1)

public class Author implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"AuthorID\"")
	@GeneratedValue(generator="AuthorSequenceGen", strategy=GenerationType.SEQUENCE)
	private int authorID;

	@Temporal(TemporalType.DATE)
	@Column(name="\"DoB\"")
	private Date doB;

	@Column(name="\"Gender\"")
	private String gender;

	@Column(name="\"Name.firstName\"")
	private String firstName;

	@Column(name="\"Name.lastName\"")
	private String lastName;

	@Column(name="\"Name.middleName\"")
	private String middleName;

	public Author() {
	}

	public int getAuthorID() {
		return this.authorID;
	}

	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}

	public Date getDoB() {
		return this.doB;
	}

	public void setDoB(Date doB) {
		this.doB = doB;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

}