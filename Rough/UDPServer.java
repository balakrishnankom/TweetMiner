import java.io.*;
import java.net.*;

class UDPServer
{
   public static void main(String args[]) throws Exception
      {
      
	  DatagramSocket sock = new DatagramSocket(9999);
             
            //buffer to receive incoming data
            byte[] buffer = new byte[65536];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
             
            //2. Wait for an incoming data
            System.out.println("Server socket created. Waiting for incoming data...");
             
            //communication loop
            while(true)
            {
                sock.receive(incoming);
                byte[] data = incoming.getData();
                String s = new String(data, 0, incoming.getLength());
		if(s.equals("GETAVAILABLEROOMSWST"))
		{
			s = "WST";
			DatagramPacket dp = new DatagramPacket(s.getBytes() , s.getBytes().length , incoming.getAddress() , incoming.getPort());
			sock.send(dp);
			System.out.println("Its your name buddy");
			
		}
		else if(s.equals("GETAVAILABLEROOMSDVL"))
		{
			s = "DVL";
			DatagramPacket dp1 = new DatagramPacket(s.getBytes() , s.getBytes().length , incoming.getAddress() , incoming.getPort());
			sock.send(dp1);
		}
		else if(s.equals("GETAVAILABLEROOMSKKL"))
		{
						
			s = "DVL";
			DatagramPacket dp2 = new DatagramPacket(s.getBytes() , s.getBytes().length , incoming.getAddress() , incoming.getPort());
			sock.send(dp2);
		}
		else
		{
			s="";
			DatagramPacket dp3 = new DatagramPacket(s.getBytes() , s.getBytes().length , incoming.getAddress() , incoming.getPort());
			sock.send(dp3);
		}

	}




		}
      }
