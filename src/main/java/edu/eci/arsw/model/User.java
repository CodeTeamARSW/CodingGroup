package edu.eci.arsw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Column(name = "name")
	private String name;

	@Column(name = "email")
	@Id
	private String email;

	@Column(name = "passwd")
	private String passwd;

	public User(){
	}

	public User(String name, String email, String passwd){
		this.name = name;
		this.email = email;
		this.passwd = passwd;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}
