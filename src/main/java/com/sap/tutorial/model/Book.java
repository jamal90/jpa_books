package com.sap.tutorial.model;

import static javax.persistence.TemporalType.DATE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "\"JPA_TEST\".\"jpa_test.model::books.Book\"")
@NamedQueries({ 
	@NamedQuery(name = "AllBooks", query = "select p from Book p"), 
	@NamedQuery(name = "FindBookByISBN", query = "select p from Book p where p.isbn = :in_isbn") 
})
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

	public String getIsbn() {
		return isbn;
	}

	public String getLanguage() {
		return language;
	}

	public String getTitle() {
		return title;
	}

	public void setIsbn(String param) {
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