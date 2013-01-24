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
package utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class EncoderHelper {

	public static byte[] encodeHashMap(HashMap<String, String> h) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(h);
			return bos.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}
	
	public static HashMap<String, String> decodeHashMap(byte[] data) {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(bis);
			return (HashMap<String, String>) in.readObject();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static byte[] encodeArrayList(ArrayList<String> l) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(l);
			return bos.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}
	
	public static ArrayList<String> decodeArrayList(byte[] data) {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(bis);
			return (ArrayList<String>) in.readObject();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static byte[] encodeHashSet(HashSet<String> l) {
		if(l == null)
			return null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(l);
			return bos.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}
	
	public static HashSet<String> decodeHashSet(byte[] data) {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(bis);
			return (HashSet<String>) in.readObject();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static byte[] encodeLong(long l) {
		ByteBuffer b = ByteBuffer.allocate(8);
		b.putLong(l);
		return b.array();
	}
}
