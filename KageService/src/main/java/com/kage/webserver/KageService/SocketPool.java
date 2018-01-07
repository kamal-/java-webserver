package com.kage.webserver.KageService;

import java.util.LinkedList;
import java.util.Queue;

import com.kage.utils.Logger;

public class SocketPool {

	private Queue<RequestSocket> clientSocketpool;
	private Logger log;
	public SocketPool() {
		clientSocketpool = new LinkedList<>();
		log = new Logger();
	}
	
	public void addSocket(RequestSocket e){
		if(clientSocketpool.size() == 1000 )
		{
			try{
				this.wait();
				}
				catch(InterruptedException ex )
				{
					log.warning("Something wrong happened..."+ex.getMessage());
				}
		}
		
		synchronized(this) {
			boolean isAdded = clientSocketpool.offer(e);
			this.notify();
			//this.processSocket();
			if(!isAdded)
				log.error("Something bad has happened.");
		}
	}
	public void processSocket()
	{
		log.info("Client Request initialized.");
		while(true)
		{
			if(this.clientSocketpool.size()<0);
			RequestSocket sock = this.clientSocketpool.poll();
			if(null == sock) continue;
			
			log.info("Create a thread.");
			Thread socketThread = new Thread(sock);
			socketThread.start();
		}
		
	}
}
