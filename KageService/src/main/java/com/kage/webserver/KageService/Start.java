package com.kage.webserver.KageService;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.kage.tasks.NotifierTask;
import com.kage.utils.Logger;

public class Start {

	private SocketPool socketPool;
	
	public Start() {
		socketPool = new SocketPool();
	}
	
	public SocketPool getConnectionPool()
	{
		return this.socketPool;
	}
	
	public static void main(String[] args)
	{
		Logger	notifier = new Logger();
		ServerSocket zServer=null;
		if( args.length < 2 )
		{
			System.out.println("Please provide port and target directory information.");
		}
		else if (args[1].contains("/") || args[1].contains("\\"))
		{
			//File targetLocation = new File("/"+args[1].split("/")[0]);
			File targetLocation = new File("C:\\Users\\Dell-pc\\Desktop\\");
			if(!targetLocation.exists())
				notifier.setLogMessage("Error: Target file location does not exist.");
			else if(!targetLocation.canWrite())
			{
				if(targetLocation.canRead())
				notifier.setLogMessage("Error: You do not have enough permission for read and write.");	
				else
				notifier.setLogMessage("Error: You do not have enough permission for read and write.");
			}
			else if(!targetLocation.isDirectory())
				notifier.setLogMessage("Error: Target file location is not a directory.");
			else{
				try {
				final int port =Integer.valueOf(args[0]);
				final Start initialize = new Start();
				final SocketPool connections = initialize.getConnectionPool();
					if( port > 0 )
					{
						zServer =new ServerSocket(port);
						Thread processConnection = new Thread(new NotifierTask(connections));
						processConnection.start();
						while(true)
						{
							notifier.info("Processing thread state is "+ processConnection.getState());
							Socket request = zServer.accept();
							connections.addSocket(new RequestSocket( request,targetLocation));
						}
						
					}
				}
				catch(NumberFormatException E) // Supressing Runtime Exceptions..
				{
					notifier.setLogMessage("Error: Please pass correct port information. It accepts a numeric value only.");
					E.printStackTrace();
				}
				catch(IOException e )
				{
					notifier.error(e.getMessage());
					e.printStackTrace();
				}
				finally{
					try {
						if(null!=zServer)zServer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
			
	}
}
