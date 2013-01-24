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
package in;

import Packet.TransportPacket;
import inout.Controler;
import inout.Protocol;
import java.nio.ByteBuffer;

public class Demux {
	// acces au controler
	private Controler controler;

	// un packet
	private TransportPacket p;


	// l'identifiant du client
	private String imei;

	// le buffer de lecture
	private ByteBuffer buffer;

	// variables de controle
	private boolean partialDataExpected, reading;

	public Demux(Controler s, String i) {
		imei = i;
		controler = s;
    	reading = true;
		partialDataExpected = false;

	}

	public boolean receive(ByteBuffer buffer) throws Exception
	{

		while (reading) {
			
	
				if(!partialDataExpected)
					//si on n'attend pas de donn�es partielles(dans le cas d'un paquet pas re�ue enti�rement)
				{	
						// si la taille du buffer est insuffisante
						if ((buffer.limit() - buffer.position()) < Protocol.HEADER_LENGTH_DATA) 
						{
							
							return true;
						}
				}
	
				// dans le cas d'un paquet partiellement recue
				if (partialDataExpected)
					partialDataExpected = p.parseCompleter(buffer);
				else 
				{
					p = new TransportPacket();
					partialDataExpected = p.parse(buffer);
				}
				
				
				
				if (partialDataExpected)
					return true;
				else
					controler.Storage(p, imei);
			
		}


		reading = true;
		return true;
	}

	public void setImei(String i) {
		imei = i;
	}

}
