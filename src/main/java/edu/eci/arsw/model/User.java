package edu.eci.arsw.model;

public class User {

	private String name;

	private String email;

	private String passwd;

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
