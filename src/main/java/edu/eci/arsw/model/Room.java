package edu.eci.arsw.model;

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

	public Room() {
	}

	// A constructor.
	public Room(String idroom, String admin) {
		this.idroom = idroom;
		this.admin = admin;
	}

	public String getIdroom() {
		return idroom;
	}

	public String getAdmin() {
		return admin;
	}
}