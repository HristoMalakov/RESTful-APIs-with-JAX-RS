package org.hristo.javabrains.messenger.model;

import java.util.Date;

public class Comment {

	private long id;
	private String comment;
	private Date date;
	private String author;
	
	public Comment() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String message) {
		this.comment = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
}
