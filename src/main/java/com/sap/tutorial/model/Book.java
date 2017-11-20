package com.sap.tutorial.model;

import static javax.persistence.TemporalType.DATE;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "\"JPA_TEST\".\"jpa_test.model::books.Book\"")
@Cacheable(false)
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

//	@Column(name="\"PublisherID\"")
//	private int publisherId;
	
	@Column(name="\"AdministrativeData.createdAt\"")
	private Timestamp createdAt;

	@Column(name="\"AdministrativeData.createdBy\"")
	private String createdBy;

	@Column(name="\"AdministrativeData.lastUpdatedAt\"")
	private Timestamp lastUpdatedAt;

	@Column(name="\"AdministrativeData.lastUpdatedBy\"")
	private String lastUpdatedBy;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"PublisherID\"", updatable=true, insertable=true) 
	private Publisher publisher;

	@OneToMany(mappedBy="book", cascade=CascadeType.ALL) // the "name" is the column name in the target table
	@JoinColumn(name="\"BookID\"", updatable=true, insertable=true)
	private List<BookAuthor> bookAuthors;

	public Book() {
	}

	public List<BookAuthor> getBookAuthors() {
		if (bookAuthors == null)
			return new ArrayList<>();
		return bookAuthors;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getLanguage() {
		return language;
	}

	public Timestamp getLastUpdatedAt() {
		return lastUpdatedAt;
	} 
	
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	} 
	
	public int getNumOfPages() {
		return numOfPages;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public Publisher getPublisher() {
		return publisher;
	}

//	public int getPublisherId() {
//		return publisherId;
//	}

	public String getTitle() {
		return title;
	}

	public void setBookAuthors(List<BookAuthor> bookAuthors) {
		this.bookAuthors = bookAuthors;
	}
	
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	public void setIsbn(String param) {
		this.isbn = param;
	}

	public void setLanguage(String param) {
		this.language = param;
	}

	public void setLastUpdatedAt(Timestamp lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public void setNumOfPages(int param) {
		this.numOfPages = param;
	}

	public void setPublishedDate(Date param) {
		this.publishedDate = param;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

//	public void setPublisherId(int param) {
//		this.publisherId = param;
//	}
	
	public void setTitle(String param) {
		this.title = param;
	}
	
	
	@PrePersist
	private void persist(){
		if (this.isbn == null || "".equals(this.isbn)){
			// mock logic to generate the isbn13 value
			Random rand = new Random();
			long p1 = Math.round(rand.nextDouble() * 1000);
			long p2 = Math.round(rand.nextDouble() * 10);
			long p3 = Math.round(rand.nextDouble() * 10_000);
			long p4 = Math.round(rand.nextDouble() * 10_000);
			long p5 = Math.round(rand.nextDouble() * 10);
			
			String isbnVal = String.format("%d-%d-%d-%d-%d", p1, p2, p3, p4, p5);
			this.isbn = isbnVal;
		}
	}
	
}