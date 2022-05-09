package edu.eci.arsw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "nameuser")
	private String nameuser;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "passwd")
	private String passwd;

	public User(){
	}

	public User(String nameuser, String name, String email, String passwd){
		this.nameuser = nameuser;
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

	public String getNameuser() {
		return nameuser;
	}

	public void setNameuser(String nameuser) {
		this.nameuser = nameuser;
	}
}
