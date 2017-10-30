package com.sap.tutorial.model;

import static javax.persistence.TemporalType.DATE;
import static org.eclipse.persistence.annotations.CacheType.NONE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.NamedQuery;

@Entity
@org.eclipse.persistence.annotations.Cache(type = NONE)
@Table(name = "\"JPA_TEST\".\"jpa_test.model::books.Book\"")
@NamedQuery(name = "AllBooks", query = "select p from Book p")
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"ISBN\"")
	private String isbn;

	@Column(name="\"Language\"")
	private String language;
	
	@Column(name="\"Title\"")
	private String title;
	
	@Column(name="\"NumOfPages\"")
	private int numOfPages;

	@Temporal(DATE)
	@Column(name="\"PublishedDate\"")
	private Date publishedDate;

	@Column(name="\"PublisherID\"")
	private int publisherId;

	public Book() {
	}

	public String getISBN() {
		return isbn;
	}

	public String getLanguage() {
		return language;
	}

	public String getTitle() {
		return title;
	}

	public void setISBN(String param) {
		this.isbn = param;
	}

	public void setLanguage(String param) {
		this.language = param;
	}

	public void setTitle(String param) {
		this.title = param;
	}

	public int getNumOfPages() {
		return numOfPages;
	}

	public void setNumOfPages(int param) {
		this.numOfPages = param;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date param) {
		this.publishedDate = param;
	}

	public int getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(int param) {
		this.publisherId = param;
	}
	
}