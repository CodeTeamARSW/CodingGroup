package edu.eci.arsw.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rooms")
public class Room {
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "idchat")
	private String idChat;

	//@Column(name = "file")
	//private Map<String, String> file = new ConcurrentHashMap<>();

	@Column(name = "file")
	private byte file;

	@Column(name = "admin")
	private String admin;

	private ArrayList<User> members;

	public Room(String id, String admin) {
		this.id = id;
		this.idChat = id;
		members = new ArrayList<User>();
		this.admin = admin;
	}

	public String getId() {
		return id;
	}

	public String getIdChat() {
		return idChat;
	}

	/*public Map<String, String> getFile() {
		return file;
	}*/

	/*public void setFile(Map<String, String> file) {
		this.file = file;
	}*/

	public byte getFile(){
		return file;
	}

	public void setFile(byte file) {
		this.file = file;
	}

	public String getAdmin() {
		return admin;
	}

	public ArrayList<User> getMembers() {
		return members;
	}
}