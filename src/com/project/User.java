package com.project;

import java.util.ArrayList;

public class User {
	private Integer id;
	private String login;
	private int password;
	private UserTypes type;
	public ArrayList<Request> requests;	
	private Request currentRequest;	
	
	public Request getCurrentRequest() {
		return currentRequest;
	}

	public int getId() {
		return id;
	}
	
	public void setCurrentRequest(Request currentRequest) {
		this.currentRequest = currentRequest;
	}

	public String getLogin() {
		return login;
	}

	public int getPassword() {
		return password;
	}

	public UserTypes getType() {
		return type;
	}	
	
	public User(Integer _id, String _login, int _password, UserTypes _type){
		id = _id;
		login = _login;
		password = _password;
		type = _type;
	}

	public User(Integer _id, String _login, UserTypes _type){
		id = _id;
		login = _login;
		type = _type;
	}
}
