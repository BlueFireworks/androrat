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
package in;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import inout.Protocol;

public class Receiver {

	private Socket socket;
	private byte[] received_data;
	private ByteBuffer buffer;
	private InputStream is;

	public Receiver(Socket s) throws IOException {
		socket = s;
		is = socket.getInputStream();

		received_data = new byte[Protocol.MAX_PACKET_SIZE];
		buffer = ByteBuffer.allocate(Protocol.MAX_PACKET_SIZE);
	}

	public ByteBuffer read() throws IOException, SocketException {
		int n = 0;

		n = is.read(received_data);

		buffer.clear();		
		buffer = ByteBuffer.wrap(received_data, 0, n);
		//System.out.println("data has been read:" + buffer.limit());

		return buffer;
	}

}
