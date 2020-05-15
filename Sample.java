import java.util.*;
import java.io.*;
import sun.net.*;
import java.net.*;

public class Sample
{

public static void main(String args[])
{	try (DatagramSocket serverSocket = new DatagramSocket()) 
			{
			String INET_ADDR = "224.4.5.8";
			int PORT = 9002;
                 
		
			// Get the address that we are going to connect to.
			InetAddress addr = InetAddress.getByName(INET_ADDR);
			// Open a new DatagramSocket, which will be used to send the data.
		
		
			String msg = "hELLO WORLD";
			// Create a packet that will contain the data
		
			// (in the form of bytes) and send it.
			DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
			msg.getBytes().length, addr, PORT);
			serverSocket.send(msgPacket);
			System.out.println("Server sent packet with msg: " + msg);
			
		
			} catch (IOException ex) {
			ex.printStackTrace();
			}
}
}