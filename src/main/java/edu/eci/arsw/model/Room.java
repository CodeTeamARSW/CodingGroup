package edu.eci.arsw.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Room {

	private String id;

	private String idChat;

	private Map<String, String> file = new ConcurrentHashMap<>();

	private User admin;

	private ArrayList<User> members;

	public Room(String id) {
		this.id = id;
		this.idChat = id;
		members = new ArrayList<User>();

	}

	public String getId() {
		return id;
	}

	public String getIdChat() {
		return idChat;
	}

	public Map<String, String> getFile() {
		return file;
	}

	public void setFile(Map<String, String> file) {
		this.file = file;
	}

	public User getAdmin() {
		return admin;
	}

	public ArrayList<User> getMembers() {
		return members;
	}
}