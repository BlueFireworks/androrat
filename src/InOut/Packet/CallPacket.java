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

import java.io.Serializable;
import java.nio.ByteBuffer;

public class CallPacket implements Packet, Serializable{

	private static final long serialVersionUID = 3972539952673409279L;
	
	private int id;
	private int type;
	private long date;
	private long duration;
	private int contact_id;
	private int phoneNumberSize;
	private String phoneNumber;
	private int nameSize;
	private String name;
	
	public CallPacket() {
	}
	
	public CallPacket(int id, int type, long date, long duration, int contact_id, String number, String name) {
		this.id = id;
		this.type = type;
		this.date = date;
		this.duration = duration;
		this.contact_id = contact_id;
		this.phoneNumber = number;
		if(phoneNumber != null)
			this.phoneNumberSize = number.length();
		else
			this.phoneNumberSize = 0;
		this.name = name;
		if(name != null)
			this.nameSize = name.length();
		else
			this.nameSize = 0;
	}
	
	public byte[] build() {
		ByteBuffer b = ByteBuffer.allocate(4*5+8*2+phoneNumberSize+nameSize);
		b.putInt(id);
		b.putInt(type);
		b.putLong(date);
		b.putLong(duration);
		b.putInt(contact_id);
		b.putInt(phoneNumberSize);
		b.put(phoneNumber.getBytes());
		b.putInt(nameSize);
		b.put(name.getBytes());
		return b.array();
	}

	public void parse(byte[] packet) {
		ByteBuffer b = ByteBuffer.wrap(packet);
		this.id = b.getInt();
		this.type = b.getInt();
		this.date = b.getLong();
		this.duration = b.getLong();
		this.contact_id = b.getInt();
		this.phoneNumberSize = b.getInt();
		byte[] tmp = new byte[phoneNumberSize];
		b.get(tmp);
		this.phoneNumber = new String(tmp);
		this.nameSize = b.getInt();
		tmp = new byte[nameSize];
		b.get(tmp);
		this.name = new String(tmp);
	}

	public int getId() {
		return id;
	}

	public int getType() {
		return type;
	}

	public long getDate() {
		return date;
	}

	public long getDuration() {
		return duration;
	}

	public int getContact_id() {
		return contact_id;
	}

	public int getPhoneNumberSize() {
		return phoneNumberSize;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public int getNameSize() {
		return nameSize;
	}

	public String getName() {
		return name;
	}
}
