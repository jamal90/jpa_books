package com.sap.tutorial.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.sap.tutorial.model.Author;

@Entity
@Table(name = "\"JPA_TEST\".\"jpa_test.model::books.Book_Author\"")
@IdClass(BookAuthorKey.class)
@Cacheable(false)
public class BookAuthor implements Serializable {

	private static final long serialVersionUID = 1L;

	public BookAuthor() {
	}

	@Id
	@Column(name="\"BookID\"")
	private String isbn;
	
	@Id
	@Column(name = "\"AuthorID\"")
	int authorId;
	
	@ManyToOne
	@JoinColumn(name = "\"BookID\"", referencedColumnName="\"ISBN\"", insertable=false, updatable=false)
	private Book book;
	
	
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	@OneToOne
	@JoinColumn(name="\"AuthorID\"", insertable=false, updatable=false)
	private Author author;

	public Author getAuthor() {
	    return author;
	}

	public void setAuthor(Author param) {
	    this.author = param;
	}
}

/*@Embeddable
class BookAuthorKey {

	@Column(name = "\"BookID\"")
	private String isbn;

	@Column(name = "\"AuthorID\"")
	private int authorId;
	
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String id) {
		this.isbn = id;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int param) {
		this.authorId = param;
	}
	
}*/
