package com.project;

import java.util.ArrayList;

import java.sql.*;

public class DB {
	private final String HOST = "localhost";
	private final String USER = "root";
	private final String PASSWORD = "0314";
	private final String DATABASE = "taxi";
	private static Connection connection = null;
	
	public DB()	{
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + DATABASE, USER, PASSWORD);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	/*public static DB getInstance(){
		return INSTANCE;
	}*/
	
	public static ArrayList<User> getUsers(){
		return null;
	}
	
	public static User getUserById(Integer id){
		if(id == null)
			return null;
		
		try{		
			PreparedStatement stm = connection.prepareStatement("SELECT * FROM `user` WHERE `id` = ?;"); 
			stm.setInt(1, id);
			ResultSet result = stm.executeQuery();
					
			if(result.next()){
				UserTypes type = UserTypes.valueOf(result.getString("type"));
				return new User(result.getInt("id"), result.getString("login"), result.getInt("password"), type);
			}
			else{
				return null;
			}			
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
	public static User getUserByLogin(String login){
		try{		
			PreparedStatement stm = connection.prepareStatement("SELECT * FROM `user` WHERE `login` = ?;"); 
			stm.setString(1, login);
			ResultSet result = stm.executeQuery();
					
			if(result.next()){
				UserTypes type = UserTypes.valueOf(result.getString("type"));
				return new User(result.getInt("id"), result.getString("login"), result.getInt("password"), type);
			}
			else{
				return null;
			}			
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		
		return null;

	}
	
	public static boolean insertUser(User user){
		try{
			PreparedStatement stm = connection.prepareStatement("INSERT INTO `user` (`login`, `password`, `type`) VALUES (?, ?, ?);");
			stm.setString(1, user.getLogin());
			stm.setInt(2, user.getPassword());
			stm.setString(3, user.getType().toString());
			return stm.execute();			
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	
	public static boolean removeUser(User user){
		return false;
	}
	
	public static boolean updateRequestStatus(Request request){
		if(request.getStatus() == RequestStatus.CREATED)
		{
			return createRequest(request);
		}
		
		try{
			PreparedStatement stm = connection.prepareStatement("UPDATE `request` SET `status` = ? WHERE `id` = ?;");
			stm.setString(1, request.getStatus().toString());
			stm.setInt(2, request.getId());
			return stm.execute();			
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	
	private static boolean createRequest(Request request){
		try{
			int id = insertRoute(request.getRoute());			
			PreparedStatement stm = connection.prepareStatement("INSERT INTO `request` (`id`, `status`, `route`, `carType`, `client`) VALUES (?, ?, ?, ?, ?);");
			stm.setInt(1, request.getId());
			stm.setString(2, request.getStatus().toString());
			stm.setInt(3, id);
			stm.setString(4, request.carType.toString());
			stm.setInt(5, request.getClient().getId());			
			return stm.execute();			
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	
	public static int insertRoute(Route route)
	{
		try{
			PreparedStatement stm = connection.prepareStatement("INSERT INTO `route` (`from`, `to`, `time`) VALUES(?, ?, ?);");
			stm.setString(1, route.from);
			stm.setString(2, route.to);
			stm.setLong(3, route.date.getTime());
			stm.execute();	
			
			stm = connection.prepareStatement("SELECT * FROM `route` WHERE `from` = ? AND `to` = ? AND `time` = ?;");
			stm.setString(1, route.from);
			stm.setString(2, route.to);
			stm.setLong(3, route.date.getTime());
			ResultSet result = stm.executeQuery();
			
			if(result.next())
				return result.getInt("id");
			else
				return 0;
		
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		
		return 0;
	}

	public static boolean updateDriver(Request request) {
		try{
			PreparedStatement stm = connection.prepareStatement("UPDATE `request` SET `driver` = ? WHERE `id` = ?;");
			stm.setInt(1, request.getDriver().getId());
			stm.setInt(2, request.getId());
			return stm.execute();			
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		
		return false;		
	}
	
	public static boolean updateClient(Request request) {
		try{
			PreparedStatement stm = connection.prepareStatement("UPDATE `request` SET `client` = ? WHERE `id` = ?;");
			stm.setInt(1, request.getClient().getId());
			stm.setInt(2, request.getId());
			return stm.execute();			
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		
		return false;		
	}
	
	public static boolean updateDispatcher(Request request) {
		try{
			PreparedStatement stm = connection.prepareStatement("UPDATE `request` SET `dispatcher` = ? WHERE `id` = ?;");
			stm.setInt(1, request.getDispatcher().getId());
			stm.setInt(2, request.getId());
			return stm.execute();			
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		
		return false;		
	}
	
	public static ArrayList<Request> getRequests(){
		ArrayList<Request> list = new ArrayList<>();		
		
		try{		
			PreparedStatement stm = connection.prepareStatement("SELECT * FROM `request` INNER JOIN `route` ON request.route = route.id;"); 
			ResultSet result = stm.executeQuery();
			
			while(result.next()){
				
				Client client = (Client)getUserById(result.getInt("client"));
				Driver driver = (Driver)getUserById(result.getInt("driver"));
				Dispatcher dispatcher = (Dispatcher)getUserById(result.getInt("dispatcher"));
				
				Request request = new Request(
										result.getInt("id"),
										new Route(
												result.getString("from"),
												result.getString("to"),
												result.getLong("time")
										),
										CarTypes.valueOf(result.getString("carType")),
										RequestStatus.valueOf(result.getString("status")),
										client,
										driver,
										dispatcher
										);
				
				list.add(request);
			}	
			
			return list;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		
		return list;
	}
	
	public static ArrayList<Driver> getDrivers(){
		ArrayList<Driver> list = new ArrayList<>();		
		
		try{		
			PreparedStatement stm = connection.prepareStatement("SELECT * FROM `user` INNER JOIN `car` ON user.id = car.driver WHERE user.type = 'DRIVER';"); 
			ResultSet result = stm.executeQuery();
			
			while(result.next()){
				list.add(new Driver(result.getInt("id"),
									result.getString("login"),
									new Car(CarTypes.valueOf(result.getString("car.type")), result.getInt("durability"))
									));
			}	
			
			return list;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		
		return list;	
	}
}
