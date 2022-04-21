package edu.eci.arsw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "messages")
public class Message {

	@Column(name = "content")
	private String content;

	@Column(name = "date")
	private LocalDate date;

	@Column(name = "author")
	private String author;

	@Column(name = "idchat")
	private String idChat;

	@Id
	@Column(name = "idmessage")
	private String idMessage;


	public Message(String content, String author, String idChat) {
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

	public String getAuthor() {
		return author;
	}


	public String getIdChat() {
		return idChat;
	}

	public void setIdChat(String idChat) {
		this.idChat = idChat;
	}
}
