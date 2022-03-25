package edu.eci.arsw.model;

import java.util.ArrayList;

public class Chat {

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
