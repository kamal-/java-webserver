package com.kage.tasks;

import com.kage.utils.Logger;
import com.kage.webserver.KageService.SocketPool;

public class NotifierTask implements Runnable{

	private SocketPool socks;
	private Logger log;
	public NotifierTask() {
		
		log = new Logger();
		// TODO Auto-generated constructor stub
	}
	public NotifierTask(SocketPool pool) {
		log = new Logger();
		this.socks = pool;
	}
	
	@Override
    public void run() {
		log.info("Initiating socket process listener...");
        socks.processSocket();
    }

}
