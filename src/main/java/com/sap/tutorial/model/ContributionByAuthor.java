package com.sap.tutorial.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Cacheable;
import javax.persistence.Column;

@Entity
@Table(name="\"_SYS_BIC\".\"jpa_test.views/PagesByAuthorsInUS\"")
@Cacheable(false)
public class ContributionByAuthor implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "\"AuthorID\"")
	private int authorID;
	@Column(name = "\"FullName\"")
	private String fullName;
	@Column(name = "\"AllPages\"")
	private int allPages;
	@Column(name = "\"AvgNumOfPages\"")
	private float avgNumOfPages;

	public ContributionByAuthor() {
	}

	public int getAuthorID() {
		return authorID;
	}

	public void setAuthorID(int param) {
		this.authorID = param;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String param) {
		this.fullName = param;
	}

	public int getAllPages() {
		return allPages;
	}

	public void setAllPages(int param) {
		this.allPages = param;
	}

	public float getAvgNumOfPages() {
		return avgNumOfPages;
	}

	public void setAvgNumOfPages(float param) {
		this.avgNumOfPages = param;
	}

}