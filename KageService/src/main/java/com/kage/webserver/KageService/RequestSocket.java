package com.kage.webserver.KageService;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import com.kage.utils.Logger;


public class RequestSocket implements Runnable{

	private Socket clientSocket;
	private File iFile;
	private Logger log;
	public RequestSocket() {
		// TODO Auto-generated constructor stub
	}
	public RequestSocket(Socket clientRequest,File target)
	{
		this.clientSocket = clientRequest;
		this.iFile = target;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		// uploading file to target location....
		log.info("Processing socket thread.");
		InputStream in = null ;
		BufferedReader br = null;
		OutputStream socketOutputStream =null;
		OutputStream out = null;
		
		try{
		in=clientSocket.getInputStream();
		socketOutputStream = new DataOutputStream(clientSocket.getOutputStream());
		br = new BufferedReader(new InputStreamReader(in));
		out = new FileOutputStream(iFile);
		byte[] buffer = new byte[2048];
		int read=0;
		int uploadLength=0;
		String line = null;
		int contentLenght=0;
		while((line = br.readLine()) != null)
		{
			if(line.isEmpty())
					break;
			if(line.startsWith("Content-Length:"))
			{
				int index = line.indexOf(':') + 1;
                String len = line.substring(index).trim();
                contentLenght = Integer.parseInt(len);
			}
			if (contentLenght > 0) {
                while ((read = in.read(buffer)) != -1) {
                	out.write(buffer);
        			out.flush();
        			uploadLength+=buffer.length;
        			log.info("processed.."+((uploadLength/contentLenght)*100)+"%");
        			if (uploadLength == contentLenght)
                        break;
                }
            }
		}
		if(null!=in)
			in.close();
		if(null!=out)
			out.close();
		}
		catch(IOException e )
		{
			log.error("Something has happened..."+e.getMessage());
			e.printStackTrace();
		}
		finally{
			try {
				if(null!=clientSocket)clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
