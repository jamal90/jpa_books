package com.sap.tutorial.model;

import static javax.persistence.TemporalType.DATE;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	
	@OneToOne
	@JoinColumn(referencedColumnName="\"PublisherID\"", name="\"PublisherID\"", updatable=false, insertable=false) // the "name" is the column name in the target table
	private Publisher publisher; 
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(referencedColumnName="\"ISBN\"", name="\"BookID\"", updatable=false, insertable=true)
	private List<BookAuthor> bookAuthors; 
	
	public Book() {
	}

	public List<BookAuthor> getBookAuthors() {
		return bookAuthors;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getLanguage() {
		return language;
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
	
	public int getPublisherId() {
		return publisherId;
	}

	public String getTitle() {
		return title;
	}



	public void setBookAuthors(List<BookAuthor> bookAuthors) {
		this.bookAuthors = bookAuthors;
	}

	public void setIsbn(String param) {
		this.isbn = param;
	}

	public void setLanguage(String param) {
		this.language = param;
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

	public void setPublisherId(int param) {
		this.publisherId = param;
	}

	public void setTitle(String param) {
		this.title = param;
	}
	
}