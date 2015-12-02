package com.project;

public class Driver extends User {
	Car car;
	
	public Driver(int _id, String _login, int _password, Car _car) {
		super(_id, _login, _password, UserTypes.DRIVER);
		car = _car;
	}
	
	public Driver(int _id, String _login, Car _car){
		super(_id, _login, UserTypes.DRIVER);
		car = _car;
	}
	
	public boolean completeRequest(){
		return getCurrentRequest().setStatus(RequestStatus.COMPLETED, this);
	}
}
