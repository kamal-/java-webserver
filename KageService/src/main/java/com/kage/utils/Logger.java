package com.kage.utils;

import java.util.Date;

public class Logger {

	private String outputMessage;
	public Logger() {
		// TODO Auto-generated constructor stub
	}
	
	public void setLogMessage( String n )
	{
		System.out.println(new Date()+"  "+n);
	}
	
	public void info(String message)
	{
		System.out.println("Info:"+message);
	}
	public void warning(String message)
	{
		System.out.println("Warning:"+message);
	}
	public void error(String message)
	{
		System.out.println("Error:"+message);
	}
}
