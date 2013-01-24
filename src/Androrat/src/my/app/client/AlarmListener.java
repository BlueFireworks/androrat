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
package my.app.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AlarmListener extends BroadcastReceiver { 
	
	public final String TAG = AlarmListener.class.getSimpleName();
	
    @Override
    public void onReceive(Context context, Intent intent) {
    	Log.d(TAG, "Alarm received !");
	   try {
			Bundle bundle = intent.getExtras();
			String message = bundle.getString("alarm_message");
			if(message != null) {
				Log.i(TAG, "Message received: "+message);
				
				Intent serviceIntent = new Intent(context, Client.class);
				serviceIntent.setAction(AlarmListener.class.getSimpleName());//By this way the Client will know that it was AlarmListener that launched it
				context.startService(serviceIntent);
				
			}
			//Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Log.e(TAG, "Error in Alarm received !"+ e.getMessage());
	   }
    }
}