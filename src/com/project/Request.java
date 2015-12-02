package com.project;



public class Request {
	private Integer id;
	private RequestStatus status;
	private Route route;	
	public CarTypes carType;
	private Driver driver;
	private Client client;
	private Dispatcher dispatcher;
	
	public Request(Route _route, CarTypes _carType, Client _client)
	{
		status = null;
		route = _route;
		carType = _carType;
		client = _client;
		id = Math.toIntExact(System.currentTimeMillis());
	}
	
	public Request(int _id, Route _route, CarTypes _carType, RequestStatus _status, Client _client)
	{
		status = _status;
		route = _route;
		carType = _carType;
		client = _client;
		id = _id;		
	}
	
	public Request(int _id, Route _route, CarTypes _carType, RequestStatus _status, Client _client, Driver _driver, Dispatcher _dispatcher) {
		status = _status;
		route = _route;
		carType = _carType;
		id = _id;
		client = _client;
		driver = _driver;
		dispatcher = _dispatcher;
	}

	public Route getRoute() {
		return route;
	}
	
	public int getId(){
		return id;
	}
	
	public RequestStatus getStatus() {
		return status;
	}
	
	public boolean setStatus(RequestStatus newStatus, User user){
		if(newStatus == status)
			return false;		
		
		switch(newStatus){
			case ASSIGNED:
			case CLOSED:
			case ERRORCREATING:
			case RECEIVED:
				if(user.getType() == UserTypes.DISPATCHER)
					status = newStatus;				
			break;
			
			case COMPLETED:
				if(user.getType() == UserTypes.DRIVER)
					status = newStatus;
			break;	
			
			case CREATED:
				if(user.getType() == UserTypes.CLIENT)
					status = newStatus;
			break;		
		}
				
		if(status == newStatus){
			return DB.updateRequestStatus(this);
		}
		
		return false;
	}
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
		DB.updateDriver(this);
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
		DB.updateClient(this);
	}
	public Dispatcher getDispatcher() {
		return dispatcher;
	}
	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
		DB.updateDispatcher(this);
	}
}
