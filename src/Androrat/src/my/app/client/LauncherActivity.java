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

import my.app.client.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LauncherActivity extends Activity {
    /** Called when the activity is first created. */
	
	Intent Client, ClientAlt;
	Button btnStart, btnStop;
	EditText ipfield, portfield;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Client = new Intent(this, Client.class);
        Client.setAction(LauncherActivity.class.getName());
        
        btnStart = (Button) findViewById(R.id.buttonstart);
        btnStop = (Button) findViewById(R.id.buttonstop);
        ipfield = (EditText) findViewById(R.id.ipfield);
        portfield = (EditText) findViewById(R.id.portfield);
        
        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	Client.putExtra("IP", ipfield.getText().toString());
            	Client.putExtra("PORT", new Integer(portfield.getText().toString()));
                startService(Client);
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);
                //finish();                
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {             
                stopService(Client);  
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                //finish(); 
            }
        });
    }
}