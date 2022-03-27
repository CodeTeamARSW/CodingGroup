package edu.eci.arsw.model;

import java.time.LocalDate;


public class Message {

	private String content;
	private LocalDate date;
	private User author;
	private String idChat;

	public Message(String content, User author, String idChat) {
		this.content = content;
		this.idChat = idChat;
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


	public String getIdChat() {
		return idChat;
	}

	public void setIdChat(String idChat) {
		this.idChat = idChat;
	}
}
