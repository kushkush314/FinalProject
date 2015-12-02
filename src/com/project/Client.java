package com.project;

public class Client extends User{

	public Client(int _id, String _login, int _password) {
		super(_id, _login, _password, UserTypes.CLIENT);
		// TODO Auto-generated constructor stub
	}
	
	public boolean createNewRequest(Route route, CarTypes carType){
		Request request = new Request(route, carType, this);
		return request.setStatus(RequestStatus.CREATED, this);
		
	}
	
}
