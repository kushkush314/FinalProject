package com.project;

import java.util.Date;

public class Route {
	public String from;
	public String to;
	public Date date;
	
	public Route(String _from, String _to)	{
		from = _from;
		to = _to;
		date = new Date();
	}
	
	public Route(String _from, String _to, Date _date)	{
		from = _from;
		to = _to;
		date = _date;
	}
	
	public Route(String _from, String _to, Long _time){
		date = new Date(_time);
		to = _to;
		from = _from;
	}
}
