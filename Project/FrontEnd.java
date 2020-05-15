
import RoomBooking.*;
import java.util.StringTokenizer;  
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;
import java.io.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.net.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.logging.Logger;
public class FrontEnd extends RoomBookingInterfacePOA 
{
	int replica1error=0,replica2error=0,replica3error=0;
	private ORB orb;
	protected static String logfile ="";
	//Sequencer seq;
	public FrontEnd( )  {  super ( ); }
	public void setORB(ORB orb_val) 
	{
		orb = orb_val; 
	}

  	public String createRoom(String roomno,String date,String timeslot,String username)
	{
		String[] unres;
		String createroommsg="";
		String result="";
		String result1="",result2="",result3="";
		int portno1=0,portno2=0,portno3=0;
		InetAddress createIpadd1,createIpadd2,createIpadd3;
		byte[] receiveCreate1 = new byte[1024];
		byte[] receiveCreate2 = new byte[1024];
		byte[] receiveCreate3 = new byte[1024];
	
		createroommsg="CREATE#"+"#"+roomno+"#"+date+"#"+timeslot+"#"+username+"#localhost#"+"9989";
		//seq=new Sequencer();
		//sending request id -- sequencer
		//unres=seq.setUniqueID(createroommsg);
		//sending the message to sequencer to forward the request to replicas
		//seq.sendMessage(unres);
		//after sending the message to replicas it is waiting for the response
		
		try
		{
			DatagramSocket createSocket = new DatagramSocket();
			InetAddress createIpadd = InetAddress.getByName("localhost");
		
		
			byte[] receiveCreate = new byte[1024];
			createIpadd1 = InetAddress.getByName("localhost");
			createIpadd2 = InetAddress.getByName("localhost");
			createIpadd3 = InetAddress.getByName("localhost");
		
			switch(username.substring(0,3))
			{
				case "WST": portno1=9991;portno2=9992;portno3=9993; 	break;
				case "DVL": portno1=9994;portno2=9995;portno3=9996; 	break;
				case "KKL": portno1=9997;portno2=9998;portno3=9999;		break;
			}
		
			ReplicaUdpListener replis=new ReplicaUdpListener(createIpadd1,portno1,createIpadd2,portno2,createIpadd3,portno3);
		
			DatagramPacket receiveCreatePacket1 = new DatagramPacket(receiveCreate1, receiveCreate1.length,createIpadd1,portno1);
			createSocket.receive(receiveCreatePacket1);
			result1 = new String(receiveCreatePacket1.getData());
		
			DatagramPacket receiveCreatePacket2 = new DatagramPacket(receiveCreate2, receiveCreate2.length,createIpadd2,portno2);
			createSocket.receive(receiveCreatePacket2);
			result2 = new String(receiveCreatePacket2.getData());
										
			DatagramPacket receiveCreatePacket3 = new DatagramPacket(receiveCreate3, receiveCreate3.length,createIpadd3,portno3);
			createSocket.receive(receiveCreatePacket3);
			result3 = new String(receiveCreatePacket3.getData());
		
		}catch(Exception e)
		{
		}
		
		
		
		
		
		return result;
	}


	public String deleteRoom(String roomno,String date,String timeslot,String username)
	{
	

		int portno1,portno2,portno3;
		String createIpadd1,createIpadd2,createIpadd3;
		String[] unres;
		String createroommsg="";
		String result="";
		String result1="",result2="",result3="";
	
		String deleteroommsg="";
		
		
		try{
				deleteroommsg="DELETE#"+"#"+roomno+"#"+date+"#"+timeslot+"#"+username+"#localhost#"+"9989";
			 	
				DatagramSocket deleteSocket = new DatagramSocket();
				InetAddress deleteIpadd = InetAddress.getByName("localhost");
				byte[] senddelete = new byte[1024];
				byte[] receivedelete = new byte[1024];
				senddelete = deleteroommsg.getBytes();
				DatagramPacket senddeletePacket = new DatagramPacket(senddelete, senddelete.length, deleteIpadd, 9995);
				deleteSocket.send(senddeletePacket);
	
				deleteSocket.close();
				
				result=result.trim();
		}catch(Exception e){}
		return result;
	}
	public String getAvailableTimeSlot(String date,String username)
	{
		String gettimeslotmsg="";
		String result="";
		try{
				gettimeslotmsg="GET#"+"#"+date+"#"+username+"#localhost#"+"9989";
				DatagramSocket getSocket = new DatagramSocket();
				InetAddress getIpadd = InetAddress.getByName("localhost");
				byte[] sendget = new byte[1024];
				byte[] receiveget = new byte[1024];
				sendget = gettimeslotmsg.getBytes();
				DatagramPacket sendgetPacket = new DatagramPacket(sendget, sendget.length, getIpadd, 9995);
				getSocket.send(sendgetPacket);
				DatagramPacket receivegetPacket = new DatagramPacket(receiveget, receiveget.length);
				getSocket.receive(receivegetPacket);
				result = new String(receivegetPacket.getData());
				getSocket.close();
				result=result.trim();
			}catch(Exception e){}
		return result;
	}	 

	public String bookRoom(String roomno,String date,String timeslot,String username)
	{
		String bookroommsg="";

		String result="";
		try{
				bookroommsg="BOOK#"+"#"+roomno+"#"+date+"#"+timeslot+"#"+username+"#localhost#"+"9989";
				DatagramSocket bookSocket = new DatagramSocket();
				InetAddress bookIpadd = InetAddress.getByName("localhost");
				byte[] sendbook = new byte[1024];
				byte[] receivebook = new byte[1024];
				sendbook = bookroommsg.getBytes();
				DatagramPacket sendbookPacket = new DatagramPacket(sendbook, sendbook.length, bookIpadd, 9995);
				bookSocket.send(sendbookPacket);
				DatagramPacket receivebookPacket = new DatagramPacket(receivebook, receivebook.length);
				bookSocket.receive(receivebookPacket);
				result = new String(receivebookPacket.getData());
				bookSocket.close();
				result=result.trim();
			}catch(Exception e){}
		return result;
	}

	public String cancelBooking(String booking_id,String username)
	{
		String cancelroommsg="";

		
		String result="";
		try{
				cancelroommsg="CANCEL#"+"#"+booking_id+"#"+username+"#localhost#"+"9989";
				DatagramSocket cancelSocket = new DatagramSocket();
				InetAddress cancelIpadd = InetAddress.getByName("localhost");
				byte[] sendcancel = new byte[1024];
				byte[] receivecancel = new byte[1024];
				sendcancel = cancelroommsg.getBytes();
				DatagramPacket sendcancelPacket = new DatagramPacket(sendcancel, sendcancel.length, cancelIpadd, 9995);
				cancelSocket.send(sendcancelPacket);
				
				
				
				
				
				
				
				
				
				
				cancelSocket.close();
				result=result.trim();
			}catch(Exception e){}
		return result;
	}
  
   
	public String changeReservation(String booking_id,String username,String campus,String newroomno,String newdate,String newtimeslot)
	{
		String changereservmsg="";
		String result="";
		try{
				changereservmsg="CHANGE#"+"#"+booking_id+"#"+username+"#"+campus+"#"+newroomno+"#"+newdate+"#"+newtimeslot+"#localhost#"+"9989";
				DatagramSocket changeSocket = new DatagramSocket();
				InetAddress changeIpadd = InetAddress.getByName("localhost");
				byte[] sendchange = new byte[1024];
				byte[] receivechange = new byte[1024];
				sendchange = changereservmsg.getBytes();
				DatagramPacket sendchangePacket = new DatagramPacket(sendchange, sendchange.length, changeIpadd, 9995);
				changeSocket.send(sendchangePacket);
				DatagramPacket receivechangePacket = new DatagramPacket(receivechange, receivechange.length);
				changeSocket.receive(receivechangePacket);
				result = new String(receivechangePacket.getData());
				changeSocket.close();
				result=result.trim();
			}catch(Exception e){}
		return result;
	}
 
 
	public static void main(String args[])
	{
		FrontEnd addobj = new FrontEnd();
		Thread t2=new Thread( new Runnable()
		{ 
			public void run()
			{
				try
				{
					// create and initialize the ORB //// get reference to rootpoa &amp; activate the POAManager
					ORB orb = ORB.init(args, null);      
					POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
					rootpoa.the_POAManager().activate();
				
					// create servant and register it with the ORB
					
					addobj.setORB(orb); 
				
					// get object reference from the servant
					org.omg.CORBA.Object ref = rootpoa.servant_to_reference(addobj);
					RoomBookingInterface href = RoomBookingInterfaceHelper.narrow(ref);
				
					org.omg.CORBA.Object objRef =  orb.resolve_initial_references("NameService");
					NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
				
					NameComponent path[] = ncRef.to_name( "FE" );
					ncRef.rebind(path, href);
				
					System.out.println("Server ready and waiting ...");
				
					// wait for invocations from clients
					for (;;)
					{
					orb.run();
					}
				} 
		
				catch (Exception e) 
				{
						System.err.println("ERROR: " + e);
						e.printStackTrace(System.out);
				}
			}
		});
		t2.start();

		
		
		
		
	}
 
 
	public String compareResults(String[] mes[])
	{
		
		
		
		
		
		
		
		
		
		
		
		
		
		
			return "f";
	}
 
 
 
 
}	 