
import RoomBooking.*;
import java.util.StringTokenizer;  

import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.net.*;
class DvlRoomRecord
{
	String roomno,date,timeslot,bookedby,bookingid;
	public DvlRoomRecord(String roomno,String date,String timeslot,String bookedby,String bookingid)
	{
		this.roomno=roomno;
		this.date=date;
		this.timeslot=timeslot;
		this.bookedby=bookedby;
		this.bookingid=bookingid;
	}
}



public class DvlBookingServerImpl extends RoomBookingInterfacePOA 
{
	private ORB orb;
	protected static String logfile ="";
	public DvlRoomRecord rr;
	HashMap<Integer,DvlRoomRecord> roomrec=new HashMap<Integer,DvlRoomRecord>();  
	public DvlBookingServerImpl( )  {  super ( ); }
  
	public void setORB(ORB orb_val) 
	{
		orb = orb_val; 
	}
	
	
  	public String createRoom(String no,String date,String timeslot,String username)
	{
		
	
	}
  
			
	public String deleteRoom(String no,String date,String timeslot,String username)
	
	{
	}

  	
	public String getAvailableTimeSlot(String date,String username,String campus)
	{
		
	} 

		
	public String bookRoom(String no,String date,String timeslot,String username,String campus)
	
	{
	  
	}

 	
	public int countLimit(String date,String username)
	
	{
	
	}
	
	public String cancelBooking (String booking_id,String username,String campus)
	
	{
	}
  
	public String changeReservation(String booking_id,String username,String campus,String newroomno,String newdate,String newtimeslot)
	{
	}
 
}