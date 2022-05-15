package edu.eci.arsw.model;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Message {

	@Id
	@Column(name = "idmessage")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)//, generator = "messages_sequence")
	private Integer idMessage;

	@Column(name = "author")
	private String author;

	@Column(name = "idchat")
	private String idChat;

	@Column(name = "content")
	private String content;


	public Message() {
	}

	public Message(String author, String idChat, String content) {
		this.author = author;
		this.idChat = idChat;
		this.content = content;
	}

	public Integer getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(Integer idMessage) {
		this.idMessage = idMessage;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIdChat() {
		return idChat;
	}

	public void setIdChat(String idChat) {
		this.idChat = idChat;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}