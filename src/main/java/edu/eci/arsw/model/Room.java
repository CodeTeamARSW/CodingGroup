package edu.eci.arsw.model;

import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rooms")
public class Room {

	//Columnas DB
	@Id
	@Column(name = "idroom")
	private String idroom;

	@Column(name = "admin")
	private String admin;

	//Atributos de Room
	/*private String idChat;

	private String idFile;
	private String nameFile;
	private ArrayList<String> code_lines;*/

	public Room() {
	}

	/**public Room(String idroom, String admin, String idChat, String idFile, String nameFile, ArrayList<String> code_lines) {
		this.idroom = idroom;
		this.admin = admin;
		this.idChat = idChat;
		this.idFile = idFile;
		this.nameFile = nameFile;
		this.code_lines = code_lines;
	}*/

	public Room(String idroom, String admin) {
		this.idroom = idroom;
		this.admin = admin;
	}

	public String getIdroom() {
		return idroom;
	}

	/*public String getIdChat() {
		return idChat;
	}

	public ArrayList<String> getCode_lines() {
		return code_lines;
	}

	public void setCode_lines(ArrayList<String> code_lines) {
		this.code_lines = code_lines;
	}*/

	public String getAdmin() {
		return admin;
	}

	/*public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}*/
}