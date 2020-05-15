import java.io.*;
import java.net.*;

class UDPClient
{
   public static void main(String args[]) throws Exception
   {
	new UDPClient().Get("GETAVAILABLEROOMSWST");
	new UDPClient().Get("GETAVAILABLEROOMSDVL");
	new UDPClient().Get("GETAVAILABLEROOMSKKL");
	
   }


	public void Get(String m)
	{
	try{
      DatagramSocket clientSocket1 = new DatagramSocket();
      InetAddress IPAddress1 = InetAddress.getByName("localhost");
      byte[] sendData1 = new byte[1024];
      byte[] receiveData1 = new byte[1024];
      String sentence1 = m;
      sendData1 = sentence1.getBytes();
      DatagramPacket sendPacket1 = new DatagramPacket(sendData1, sendData1.length, IPAddress1, 9999);
      clientSocket1.send(sendPacket1);
      DatagramPacket receivePacket1 = new DatagramPacket(receiveData1, receiveData1.length);
      clientSocket1.receive(receivePacket1);
      String modifiedSentence1 = new String(receivePacket1.getData());
      System.out.println("FROM SERVER:" + modifiedSentence1);
	}catch(Exception e){}		


	}



}