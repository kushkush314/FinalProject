package com.project;

public class Dispatcher extends User{
	
	
	public Dispatcher(int _id, String _login, int _password) {
		super(_id, _login, _password, UserTypes.DISPATCHER);
		// TODO Auto-generated constructor stub
	}
	
	public boolean assignDriver(Driver driver, Request request){
		if(request.getStatus() != RequestStatus.RECEIVED){
			return false;
		}
		
		if(driver.getCurrentRequest() != null){
			return false;
		}
		
		request.setDriver(driver);
		return request.setStatus(RequestStatus.ASSIGNED, this);		
	}
	
	public boolean closeRequest(Request request){
		return request.setStatus(RequestStatus.CLOSED, this);
	}

}
