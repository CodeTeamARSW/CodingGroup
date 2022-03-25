package edu.eci.arsw.model;

import java.util.ArrayList;

public class Room {

	private String id;

	private String idChat;

	private ArrayList<String> file;

	private User admin;

	private ArrayList<User> members;

	public Room(String id, User admin) {
		this.id = id;
		this.admin = admin;
		file = new ArrayList<String>();
		this.idChat = id;
		members = new ArrayList<User>();

	}

	public String getId() {
		return id;
	}

	public String getIdChat() {
		return idChat;
	}

	public ArrayList<String> getFile() {
		return file;
	}

	public User getAdmin() {
		return admin;
	}

	public ArrayList<User> getMembers() {
		return members;
	}
}