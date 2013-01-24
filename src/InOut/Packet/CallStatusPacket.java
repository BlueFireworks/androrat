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
package Packet;

import java.nio.ByteBuffer;

public class CallStatusPacket implements Packet{

	int type;
	/*
	 * 1 -> Incoming call
	 * 2 -> Missed call
	 * 3 -> Call accepted
	 * 4 -> Call send
	 * 5 -> Hang Up
	 * 
	 */
	String phonenumber;
	
	public CallStatusPacket() {
		
	}
	
	public CallStatusPacket(int type, String phone) {
		this.type = type;
		this.phonenumber = phone;
	}
	
	public byte[] build() {
		ByteBuffer b;
		if(phonenumber == null) {
			b = ByteBuffer.allocate(4);
			b.putInt(type);
		}
		else {
			b = ByteBuffer.allocate(4+phonenumber.length());
			b.putInt(type);
			b.put(phonenumber.getBytes());
		}
		return b.array();
	}

	public void parse(byte[] packet) {
		ByteBuffer b= ByteBuffer.wrap(packet);
		this.type = b.getInt();
		if(b.hasRemaining()) {
			byte[] tmp = new byte[b.remaining()];
			b.get(tmp);
			this.phonenumber = new String(tmp);
		}
		else
			this.phonenumber = null;
	}

	public int getType() {
		return type;
	}

	public String getPhonenumber() {
		return phonenumber;
	}
}
