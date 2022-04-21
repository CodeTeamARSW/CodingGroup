package edu.eci.arsw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;

@Entity
@Table(name = "chats")
public class Chat {
	@Id
	@Column(name = "idchat")
	private String id;

	private ArrayList<Message> messages = new ArrayList<>();

	public Chat(String id){
		this.id = id;
	}

	public ArrayList<Message> getMessages(){
		return messages;
	}

	public String getId() {
		return id;
	}
}
