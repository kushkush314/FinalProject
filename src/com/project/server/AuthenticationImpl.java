package com.project.server;

import com.project.DB;
import com.project.User;
import com.project.UserTypes;
import com.project.client.Authentication;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class AuthenticationImpl extends RemoteServiceServlet implements Authentication {

	public boolean login(String login, int passwordHash) throws IllegalArgumentException{
		if(login.isEmpty() || login == null)
			throw new IllegalArgumentException("Empty login form");
		
		if(passwordHash == 0)
			throw new IllegalArgumentException("Empty password form");
				
		DB db = new DB();
		User user = DB.getUserByLogin(login);
		
		if(user == null){
			DB.insertUser(new User(null, login, passwordHash, UserTypes.CLIENT));
			return true;
		}
		
		if(user.getPassword() == passwordHash){
			return true;
		}
		else{
			return false;
		}		
	}
}
