package edu.eci.arsw.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


public class Message {

	private String content;
	private LocalDate date;
	private User author;

	public Message(String content, User author) {
		this.content = content;
		this.date = java.time.LocalDate.now();
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public LocalDate getDate() {
		return date;
	}

	public User getAuthor() {
		return author;
	}



}
