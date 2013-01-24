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

public class LogPacket implements Packet {

	long date;
	byte type; //0 ok / 1 Error
	String message;
	
	public LogPacket() {
		
	}
	
	public LogPacket(long date, byte type, String message) {
		this.date = date;
		this.type = type;
		this.message = message;
	}
	
	public byte[] build() {
		ByteBuffer b = ByteBuffer.allocate(9+message.length());
		b.putLong(date);
		b.put(type);
		b.put(message.getBytes());
		return b.array();
	}

	public void parse(byte[] packet) {
		ByteBuffer b = ByteBuffer.wrap(packet);
		date = b.getLong();
		type = b.get();
		byte[] tmp = new byte[b.remaining()];
		b.get(tmp);
		message = new String(tmp);
	}

	public long getDate() {
		return date;
	}

	public byte getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}

}
