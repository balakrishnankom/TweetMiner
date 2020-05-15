
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
 
  public void setORB(ORB orb_val) 
  {
    orb = orb_val; 
  }
 

  protected static String defaultLogFile ="";
  public DvlRoomRecord rr;
  HashMap<Integer,DvlRoomRecord> roomrec=new HashMap<Integer,DvlRoomRecord>();  
  public DvlBookingServerImpl( )  {  super ( ); }
  
	public String createRoom(String no,String date,String timeslot,String username)
	{
		String check="false";
		defaultLogFile="E:\\DVLCampus_Server_Logs_"+username+"_log.txt";
		int count= (int) (new Date().getTime()/1000);
		String log="",flag="",bookedby="-",bookingid="-";
		try
		{
			if(!roomrec.isEmpty())
			{	
				Iterator it1 = roomrec.entrySet().iterator();
				while (it1.hasNext()) 
				{
					Map.Entry pair = (Map.Entry)it1.next();
					DvlRoomRecord ras=(DvlRoomRecord)pair.getValue();
					System.out.println(ras.date.equals(date)+","+ras.timeslot.equals(timeslot)+","+ras.roomno.equals(no));
					
					if(ras.date.equals(date)==true && ras.timeslot.equals(timeslot)==true && ras.roomno.equals(no)==true)
					{
						if(check=="true")
						{	
							check="false";
							log="Room Creation \t Unsuccessful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t Booking ID :" +bookingid ;  
							break;
						}
						else
							break;
					}
					else
						check="true";
				}
			}
			else
			{
				rr=new DvlRoomRecord(no,date,timeslot,bookedby,bookingid);
				synchronized (this){
				roomrec.put(count,rr); 
				}
				log="Room Creation \t Successful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t" +bookingid ;  	log="Room Creation \t Successful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t Booking ID :" +bookingid ;  
			}
			if(check=="true")
			{
				rr=new DvlRoomRecord(no,date,timeslot,bookedby,bookingid);
				synchronized(this){
				roomrec.put(count,rr);
				}
				log="Room Creation \t Successful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t" +bookingid ;  	log="Room Creation \t Successful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t Booking ID :" +bookingid ;  
			}
		}catch(Exception eere){}	
		try (FileWriter f = new FileWriter(defaultLogFile, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);)
		{ 
			p.println(log); 
		} 
		catch (IOException i) { i.printStackTrace(); }
		System.out.println("\n\n\t\t Available rooms-slots in DVL campus ");
		System.out.print("\t\t------------------------------------\n\n");
		Iterator itview = roomrec.entrySet().iterator();
		while (itview.hasNext()) 
		{
			Map.Entry pair = (Map.Entry)itview.next();
			DvlRoomRecord ras=(DvlRoomRecord)pair.getValue();
			System.out.println("Room No : "+ras.roomno+" Date : "+ras.date+" TimeSlot : "+ras.timeslot+" Booked by : "+ras.bookedby+ " Booking ID :" +ras.bookingid+""); 
		}
		return log;
}

		
	public String getAvailableTimeSlot(String date,String username,String campus)
	{
			String return_value="";
			String modifiedSentence1="";
			String modifiedSentence2="";
			int slot=0;
			String avail_slots="";
			int count=0;
			defaultLogFile="E:\\DVLCampus_Server_Logs_"+username+"_log.txt";
			String log="",flag="",bookedby="-",bookingid="-";
		
			String modifiedSentencedvl="";		
			String modifiedSentencekkl="";
			String modifiedSentencewst="";

		
			if(campus.equals("DVLSERVER")|| campus.equals("KKLSERVER")|| campus.equals("WSTSERVER"))
			{
				
				
				
				try
				{
					if(!roomrec.isEmpty())
					{	
						Iterator it1 = roomrec.entrySet().iterator();
						while (it1.hasNext()) 
						{
							Map.Entry pair = (Map.Entry)it1.next();
							DvlRoomRecord ras=(DvlRoomRecord)pair.getValue();
							if(ras.date.equals(date)==true && ras.bookedby=="-")
							{
									count++;
							}
						}
						return_value=""+count;
						log="Checking Availability\tNo of rooms : "+count;
					}
					else
					{
						return_value=""+count;
					}
				}catch(Exception eere){}	
	
			}
			else
			{
				try
				{
					//DVL Available time slots
					DatagramSocket clientSocketdvl = new DatagramSocket();
					InetAddress IPAddressdvl = InetAddress.getByName("localhost");
					byte[] sendDatadvl = new byte[1024];
					byte[] receiveDatadvl = new byte[1024];
					
					
					String sentencedvl = "GETAVAILABLEROOMSDVL"+date;
					sendDatadvl = sentencedvl.getBytes();
					
						
					DatagramPacket sendPacketdvl = new DatagramPacket(sendDatadvl, sendDatadvl.length, IPAddressdvl, 9999);
					clientSocketdvl.send(sendPacketdvl);
					DatagramPacket receivePacketdvl = new DatagramPacket(receiveDatadvl, receiveDatadvl.length);
					clientSocketdvl.receive(receivePacketdvl);
					modifiedSentencedvl = new String(receivePacketdvl.getData());
					clientSocketdvl.close();
					
					return_value="DVL : "+(modifiedSentencedvl.trim())+"";
					
					//KKL Available time slots
					DatagramSocket clientSocketkkl = new DatagramSocket();
					InetAddress IPAddresskkl = InetAddress.getByName("localhost");
					byte[] sendDatakkl = new byte[1024];
					byte[] receiveDatakkl = new byte[1024];
					
					
					String sentencekkl = "GETAVAILABLEROOMSKKL"+date;
					sendDatakkl = sentencekkl.getBytes();
					
					
					DatagramPacket sendPacketkkl = new DatagramPacket(sendDatakkl, sendDatakkl.length, IPAddresskkl, 9998);
					clientSocketkkl.send(sendPacketkkl);
					DatagramPacket receivePacketkkl = new DatagramPacket(receiveDatakkl, receiveDatakkl.length);
					clientSocketkkl.receive(receivePacketkkl);
					modifiedSentencekkl = new String(receivePacketkkl.getData());
					clientSocketkkl.close();
					
					return_value=return_value+" KKL : "+(modifiedSentencekkl.trim())+"";
					
					//WST Available time slots
					
					
					DatagramSocket clientSocketwst = new DatagramSocket();
					InetAddress IPAddresswst = InetAddress.getByName("localhost");
					byte[] sendDatawst = new byte[1024];
					byte[] receiveDatawst = new byte[1024];
					
					
					String sentencewst = "GETAVAILABLEROOMSWST"+date;
					sendDatawst = sentencewst.getBytes();
									
					
					DatagramPacket sendPacketwst = new DatagramPacket(sendDatawst, sendDatawst.length, IPAddresswst, 9997);
					clientSocketwst.send(sendPacketwst);
					DatagramPacket receivePacketwst = new DatagramPacket(receiveDatawst, receiveDatawst.length);
					clientSocketwst.receive(receivePacketwst);
					modifiedSentencewst = new String(receivePacketwst.getData());
					clientSocketwst.close();
					
					return_value=return_value+" WST : "+(modifiedSentencewst.trim());
					
					
				}catch(Exception e){}		
			}
			/*
		try (FileWriter f = new FileWriter(defaultLogFile, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);)
		{ 
			p.println(log); 
		} 
		catch (IOException i) { i.printStackTrace(); }
		System.out.println("\n\n\t\t Available rooms-slots in WST campus ");
		System.out.print("\t\t------------------------------------\n\n");
		Iterator itview = roomrec.entrySet().iterator();
		while (itview.hasNext()) 
		{
			Map.Entry pair = (Map.Entry)itview.next();
			DvlRoomRecord ras=(DvlRoomRecord)pair.getValue();
			
			System.out.println("Room No : "+ras.roomno+" Date : "+ras.date+" TimeSlot : "+ras.timeslot+" Booked by : "+ras.bookedby+ " Booking ID :" +ras.bookingid+""); 
		}*/
		
			return return_value;
} 

	
		
	
public int countLimit(String date,String username)
{
	System.out.println(" count limit is invoked");
		int booklim=0;
		
	
	try{
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				
		if(!roomrec.isEmpty())
		{
			Iterator rese = roomrec.entrySet().iterator();
			while (rese.hasNext()) 
			{
				Map.Entry lim= (Map.Entry)rese.next();
				DvlRoomRecord res=(DvlRoomRecord)lim.getValue();
			
				Date date1 = df.parse(date);
				Date date2 = df.parse(res.date);
				Calendar cal = Calendar.getInstance();
				
				
				cal.setTime(date1);
				int weekcur = cal.get(Calendar.WEEK_OF_YEAR);
				cal.setTime(date2);
				int weekpre = cal.get(Calendar.WEEK_OF_YEAR);
				
				if(weekcur==weekpre && res.bookedby.equals(username))
				{
					booklim++;
				}		
			}
		}
		
		System.out.println("Limit : "+booklim);
	}catch(Exception er){}
	return booklim;	
	
}
	

		
public String deleteRoom(String no,String date,String timeslot,String username)
{
	
	String check="false";
	defaultLogFile="E:\\DVLCampus_Server_Logs_"+username+"_log.txt";
	int count= (int) (new Date().getTime()/1000);
	String log="",flag="",bookedby="-",bookingid="-";
	try
	{
		if(!roomrec.isEmpty())
		{	log="";
			Iterator it1 = roomrec.entrySet().iterator();
			while (it1.hasNext()) 
			{
				Map.Entry pair = (Map.Entry)it1.next();
				DvlRoomRecord ras=(DvlRoomRecord)pair.getValue();
				
				if(ras.date.equals(date)==true && ras.timeslot.equals(timeslot)==true && ras.roomno.equals(no)==true)
				{
					
					synchronized(this){roomrec.remove(pair.getKey(),(DvlRoomRecord)pair.getValue());}
					log="Room Deletion \t Successful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t Booking ID : " +bookingid ; 
					if(check=="true")
					{	
						check="false";
						break;
					}
					else
						break;
				}
				else
					check="true";
			}
		}
		else
			log="Room Deletion \t Unsuccessful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t Booking ID : " +bookingid ; 
			
		if(check=="false")
		{
		}
	}catch(Exception eere){}	
	try (FileWriter f = new FileWriter(defaultLogFile, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);)
	{ 
		p.println(log); 
	} 
	catch (IOException i) { i.printStackTrace(); }
	System.out.println("\n\n\t\t Available rooms-slots in DVL campus ");
	System.out.print("\t\t------------------------------------\n\n");
	Iterator itview = roomrec.entrySet().iterator();
	while (itview.hasNext()) 
	{
		Map.Entry pair = (Map.Entry)itview.next();
		DvlRoomRecord ras=(DvlRoomRecord)pair.getValue();
		System.out.println("Room No : "+ras.roomno+" Date : "+ras.date+" TimeSlot : "+ras.timeslot+" Booked by : "+ras.bookedby+ " Booking ID :" +ras.bookingid+""); 
	}
	return log;
}


public String cancelBooking (String booking_id,String username,String no,String date,String timeslot,String campus)
{
	String check="false";
	defaultLogFile="E:\\DVLCampus_Server_Logs_"+username+"_log.txt";
	int count= (int) (new Date().getTime()/1000);
	String log="",flag="",bookedby="-",bookingid="-";
	String modifiedSentencedvl="",modifiedSentencekkl="",modifiedSentencewst="";
	String return_value="",return_valuecandvl="",return_valuecankkl="",return_valuecanwst="";
	
		if(campus.equals("DVLSERVER")|| campus.equals("KKLSERVER")|| campus.equals("WSTSERVER"))
		{
		
					try
					{
							if(!roomrec.isEmpty())
							{	
								Iterator it1 = roomrec.entrySet().iterator();
								while (it1.hasNext()) 
								{
									Map.Entry pair = (Map.Entry)it1.next();
									DvlRoomRecord ras=(DvlRoomRecord)pair.getValue();
									
									if(ras.date.equals(date)==true && ras.timeslot.equals(timeslot)==true && ras.roomno.equals(no)==true && ras.bookedby!="-" && (ras.bookedby.equals(username)))
									{
										int k=(int)pair.getKey();
										DvlRoomRecord rc=(DvlRoomRecord)pair.getValue();
										synchronized(this){
										roomrec.remove(pair.getKey(),(DvlRoomRecord)pair.getValue());
										rc.bookedby="-";
										rc.bookingid="-";
										
										System.out.println("\n\n Room is cancelled by : "+username);
										roomrec.put(k,(DvlRoomRecord)rc);
										}
										log="Room Cancellation Successful \t Date : "+ras.date+"\tTime slot : "+ras.timeslot+"\t Room no : "+ras.roomno+"\t Cancelled by : "+ras.bookedby+ "\t Booking ID : "+ras.bookingid;
									}
									else
									{
										log="Room Cancellation Unsuccessful \t Date : "+date+"\tTime slot : "+timeslot+"\t Room no : "+no+"\t Booking ID : "+booking_id;
									}						
								}
							}
						
					}catch(Exception ex){}
					
		}
		else
		{
				try
				{
					//DVL Available time slots
					DatagramSocket clientSocketdvl = new DatagramSocket();
					InetAddress IPAddressdvl = InetAddress.getByName("localhost");
					byte[] sendDatadvl = new byte[1024];
					byte[] receiveDatadvl = new byte[1024];
				
					String sentencedvl = "CANCELROOMDVL#"+booking_id+"#"+username+"#"+no+"#"+date+"#"+timeslot;
					sendDatadvl = sentencedvl.getBytes();
					
						
					DatagramPacket sendPacketdvl = new DatagramPacket(sendDatadvl, sendDatadvl.length, IPAddressdvl, 9999);
					clientSocketdvl.send(sendPacketdvl);
					DatagramPacket receivePacketdvl = new DatagramPacket(receiveDatadvl, receiveDatadvl.length);
					clientSocketdvl.receive(receivePacketdvl);
					modifiedSentencedvl = new String(receivePacketdvl.getData());
					clientSocketdvl.close();
					
					return_valuecandvl=""+(modifiedSentencedvl.trim())+"";
					return_valuecandvl=return_valuecandvl.substring(18,28);
					
					if(!return_valuecandvl.equals("Successful"))
					{					
						//KKL Cancel booking
						DatagramSocket clientSocketkkl = new DatagramSocket();
						InetAddress IPAddresskkl = InetAddress.getByName("localhost");
						byte[] sendDatakkl = new byte[1024];
						byte[] receiveDatakkl = new byte[1024];
						
						
						String sentencekkl = "CANCELROOMKKL#"+booking_id+"#"+username+"#"+no+"#"+date+"#"+timeslot;
						sendDatakkl = sentencekkl.getBytes();
						
						
						DatagramPacket sendPacketkkl = new DatagramPacket(sendDatakkl, sendDatakkl.length, IPAddresskkl, 9998);
						clientSocketkkl.send(sendPacketkkl);
						DatagramPacket receivePacketkkl = new DatagramPacket(receiveDatakkl, receiveDatakkl.length);
						clientSocketkkl.receive(receivePacketkkl);
						modifiedSentencekkl = new String(receivePacketkkl.getData());
						clientSocketkkl.close();
						
						return_valuecankkl=""+(modifiedSentencekkl.trim())+"";
						return_valuecankkl=return_valuecankkl.substring(18,28);
					
					}
					if(!return_valuecandvl.equals("Successful") && !return_valuecankkl.equals("Successful"))
					{
						//WST cancel booking
						DatagramSocket clientSocketwst = new DatagramSocket();
						InetAddress IPAddresswst = InetAddress.getByName("localhost");
						byte[] sendDatawst = new byte[1024];
						byte[] receiveDatawst = new byte[1024];
						
						
						String sentencewst = "CANCELROOMWST#"+booking_id+"#"+username+"#"+no+"#"+date+"#"+timeslot;
						sendDatawst = sentencewst.getBytes();
										
						
						DatagramPacket sendPacketwst = new DatagramPacket(sendDatawst, sendDatawst.length, IPAddresswst, 9997);
						clientSocketwst.send(sendPacketwst);
						DatagramPacket receivePacketwst = new DatagramPacket(receiveDatawst, receiveDatawst.length);
						clientSocketwst.receive(receivePacketwst);
						modifiedSentencewst = new String(receivePacketwst.getData());
						clientSocketwst.close();
						
						return_valuecanwst=""+(modifiedSentencewst.trim());
						return_valuecanwst=return_valuecanwst.substring(18,28);
					}
						
					if(return_valuecandvl.equals("Successful"))
						log=modifiedSentencedvl;
					else if(return_valuecankkl.equals("Successful"))
						log=modifiedSentencekkl;
					else if(return_valuecanwst.equals("Successful"))
						log=modifiedSentencewst;
					
					
					
					
					System.out.println("DVL result :"+modifiedSentencedvl);
					
					System.out.println("KKL result :"+modifiedSentencekkl);
					
					System.out.println("WST result :"+modifiedSentencewst);
						
					
					System.out.println("Log value before catch : "+log);
					
					System.out.println("Log value before catch : "+log);				
			
				}catch(Exception e){}
			
		}
		

					
	if(log=="")
		log=" Unable to cancel room! Record doesnt exists : \tRoom no : "+no+"\t Date :"+date+"\t Time slot : "+timeslot;
			
	try (FileWriter f = new FileWriter(defaultLogFile, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);)
	{ 
		p.println(log); 
	} 
	catch (IOException i) { i.printStackTrace(); }
	
	System.out.println("\n\n\t\t Available rooms-slots in DVL campus ");
	System.out.print("\t\t------------------------------------\n\n");
	
	
	Iterator it = roomrec.entrySet().iterator();
	while (it.hasNext()) 
	{
		Map.Entry pair = (Map.Entry)it.next();
		DvlRoomRecord ras=(DvlRoomRecord)pair.getValue();
		System.out.println(pair.getKey() + " = " + ras.roomno+","+ras.date+" ,"+ras.timeslot+"  Booked by : "+ras.bookedby+" Booking ID : "+ras.bookingid);
	}
	
	
	
	return log;
  }
  




  public String bookRoom(String no,String date,String timeslot,String username,String campus)
  {
	  
		int countdvl=0,countwst=0,countkkl=0;
		
		String dvlbooked="false",wstbooked="false",kklbooked="false";
		String check="false";
		defaultLogFile="E:\\DVLCampus_Server_Logs_"+username+"_log.txt";
		int count= (int) (new Date().getTime()/1000);
		String log="",flag="",bookedby="-",bookingid="-";
		int max=0;
		int booklim=0;
		
	
		String modifiedSentencedvl="";		
		String modifiedSentencekkl="";
		String modifiedSentencewst="";

		String modifiedSentencedvlcount="";		
		String modifiedSentencekklcount="";
		String modifiedSentencewstcount="";


		String return_value="";
		
		String return_valuecount="";
		String return_valuedvl="";
		String return_valuekkl="";
		String return_valuewst="";
		String return_valuedvlcount="";
		String return_valuekklcount="";
		String return_valuewstcount="";
		
		
		
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			
		System.out.println("Book room has been invoked");
		try
		{
			if(campus.equals("DVLSERVER")|| campus.equals("KKLSERVER") || campus.equals("WSTSERVER"))
			{
				
				if(!roomrec.isEmpty())
				{	
				
					Iterator it1 = roomrec.entrySet().iterator();
					while (it1.hasNext()) 
					{
						Map.Entry pair = (Map.Entry)it1.next();
						DvlRoomRecord ras=(DvlRoomRecord)pair.getValue();
						System.out.println(ras.date.equals(date)+","+ras.timeslot.equals(timeslot)+","+ras.roomno.equals(no));
						
						if(ras.date.equals(date)==true && ras.timeslot.equals(timeslot)==true && ras.roomno.equals(no)==true && ras.bookedby=="-" && booklim<3)
						{
							int k=(int)pair.getKey();
							DvlRoomRecord rc=(DvlRoomRecord)pair.getValue();
							synchronized(this){
							roomrec.remove(pair.getKey(),(DvlRoomRecord)pair.getValue());
							rc.bookedby=username;
							rc.bookingid=username+""+(int)(new Date().getTime()/1000);
							bookingid=rc.bookingid;
							System.out.println("\n\n Room is booked by : "+rc.bookedby);
							roomrec.put(k,(DvlRoomRecord)rc);
							}
							log="Roombooking Successful\t"+" Room No : "+ ras.roomno+"\t Booking Date : "+ras.date+"\t Timeslot : "+ras.timeslot+"\t Booked by : "+username+"\t Booking ID : "+bookingid;
							dvlbooked="true";
						}
						else
						{
							log="Roombooking unsuccessful\t"+" Room No : "+ no+"\t Booking Date : "+date+"\t Timeslot : "+timeslot;
						}						
					}
				}
				
			}
			else
			{
					try
					{
								System.out.println("DVL is gonna be executed:");
										
										//DVL Available time slots
										DatagramSocket clientSocketdvlcount = new DatagramSocket();
										InetAddress IPAddressdvlcount = InetAddress.getByName("localhost");
										byte[] sendDatadvlcount = new byte[1024];
										byte[] receiveDatadvlcount = new byte[1024];
										String sentencedvlcount = "GETLIMITDVL#"+date+"#"+username;
										System.out.println("request to be sent to dvl server  :"+sentencedvlcount);
										sendDatadvlcount = sentencedvlcount.getBytes();
											
										DatagramPacket sendPacketdvlcount = new DatagramPacket(sendDatadvlcount, sendDatadvlcount.length, IPAddressdvlcount, 9999);
										clientSocketdvlcount.send(sendPacketdvlcount);
										DatagramPacket receivePacketdvlcount = new DatagramPacket(receiveDatadvlcount, receiveDatadvlcount.length);
										clientSocketdvlcount.receive(receivePacketdvlcount);
										modifiedSentencedvlcount = new String(receivePacketdvlcount.getData());
										clientSocketdvlcount.close();
										return_valuecount=""+(modifiedSentencedvlcount.trim())+"";
										countdvl=Integer.parseInt(return_valuecount);
										
										//KKL Available time slots
										DatagramSocket clientSocketkklcount = new DatagramSocket();
										InetAddress IPAddresskklcount = InetAddress.getByName("localhost");
										byte[] sendDatakklcount = new byte[1024];
										byte[] receiveDatakklcount = new byte[1024];
										String sentencekklcount = "GETLIMITKKL#"+date+"#"+username;
										sendDatakklcount = sentencekklcount.getBytes();
											
										DatagramPacket sendPacketkklcount = new DatagramPacket(sendDatakklcount, sendDatakklcount.length, IPAddresskklcount, 9998);
										clientSocketkklcount.send(sendPacketkklcount);
										DatagramPacket receivePacketkklcount = new DatagramPacket(receiveDatakklcount, receiveDatakklcount.length);
										clientSocketkklcount.receive(receivePacketkklcount);
										modifiedSentencekklcount = new String(receivePacketkklcount.getData());
										clientSocketkklcount.close();
										return_valuecount=""+(modifiedSentencekklcount.trim())+"";
										countkkl=Integer.parseInt(return_valuecount);
										//WST Available time slots
										
										DatagramSocket clientSocketwstcount = new DatagramSocket();
										InetAddress IPAddresswstcount = InetAddress.getByName("localhost");
										byte[] sendDatawstcount = new byte[1024];
										byte[] receiveDatawstcount = new byte[1024];
										String sentencewstcount = "GETLIMITWST#"+date+"#"+username;
										System.out.println("request to be sent to wst server  :"+sentencewstcount);
										sendDatawstcount = sentencewstcount.getBytes();
											
										DatagramPacket sendPacketwstcount = new DatagramPacket(sendDatawstcount, sendDatawstcount.length, IPAddresswstcount, 9997);
										clientSocketwstcount.send(sendPacketwstcount);
										DatagramPacket receivePacketwstcount = new DatagramPacket(receiveDatawstcount, receiveDatawstcount.length);
										clientSocketwstcount.receive(receivePacketwstcount);
										modifiedSentencewstcount = new String(receivePacketwstcount.getData());
										clientSocketwstcount.close();
										return_valuecount=""+(modifiedSentencewstcount.trim())+"";
										countwst=Integer.parseInt(return_valuecount);
										System.out.println("Total booking : "+(countdvl+countkkl+countwst));
										if((countdvl+countkkl+countwst)<3)
										{
										
												//DVL Available time slots
												DatagramSocket clientSocketdvl = new DatagramSocket();
												InetAddress IPAddressdvl = InetAddress.getByName("localhost");
												byte[] sendDatadvl = new byte[1024];
												byte[] receiveDatadvl = new byte[1024];
												
												
												String sentencedvl = "BOOKROOMDVL#"+no+"#"+date+"#"+timeslot+"#"+username;
												sendDatadvl = sentencedvl.getBytes();
												
													
												DatagramPacket sendPacketdvl = new DatagramPacket(sendDatadvl, sendDatadvl.length, IPAddressdvl, 9999);
												clientSocketdvl.send(sendPacketdvl);
												DatagramPacket receivePacketdvl = new DatagramPacket(receiveDatadvl, receiveDatadvl.length);
												clientSocketdvl.receive(receivePacketdvl);
												modifiedSentencedvl = new String(receivePacketdvl.getData());
												clientSocketdvl.close();
								
								
												return_valuedvl=modifiedSentencedvl.substring(12,22);
												return_valuedvl=return_valuedvl.trim();
												if(!return_valuedvl.equals("Successful"))
												{
												
														//KKL Available time slots
														DatagramSocket clientSocketkkl = new DatagramSocket();
														InetAddress IPAddresskkl = InetAddress.getByName("localhost");
														byte[] sendDatakkl = new byte[1024];
														byte[] receiveDatakkl = new byte[1024];
														
														
														String sentencekkl = "BOOKROOMKKL#"+no+"#"+date+"#"+timeslot+"#"+username;
														sendDatakkl = sentencekkl.getBytes();
														
														
														DatagramPacket sendPacketkkl = new DatagramPacket(sendDatakkl, sendDatakkl.length, IPAddresskkl, 9998);
														clientSocketkkl.send(sendPacketkkl);
														DatagramPacket receivePacketkkl = new DatagramPacket(receiveDatakkl, receiveDatakkl.length);
														clientSocketkkl.receive(receivePacketkkl);
														modifiedSentencekkl = new String(receivePacketkkl.getData());
														clientSocketkkl.close();
											
														return_valuekkl=modifiedSentencekkl.substring(12,22);
														return_valuekkl=return_valuekkl.trim();
											
												}		
												if(!return_valuedvl.equals("Successful") && !return_valuekkl.equals("Successful"))
												{
														//WST Available time slots
														DatagramSocket clientSocketwst = new DatagramSocket();
														InetAddress IPAddresswst = InetAddress.getByName("localhost");
														byte[] sendDatawst = new byte[1024];
														byte[] receiveDatawst = new byte[1024];
														
														String sentencewst = "BOOKROOMWST#"+no+"#"+date+"#"+timeslot+"#"+username;
														sendDatawst = sentencewst.getBytes();
													
														DatagramPacket sendPacketwst = new DatagramPacket(sendDatawst, sendDatawst.length, IPAddresswst, 9997);
														clientSocketwst.send(sendPacketwst);
														DatagramPacket receivePacketwst = new DatagramPacket(receiveDatawst, receiveDatawst.length);
														clientSocketwst.receive(receivePacketwst);
														modifiedSentencewst = new String(receivePacketwst.getData());
														clientSocketwst.close();
														
														return_valuewst=modifiedSentencewst.substring(12,22);
														return_valuewst=return_valuewst.trim();
												}
												
												if(return_valuedvl.equals("Successful"))
													log=modifiedSentencedvl;
												else if(return_valuekkl.equals("Successful"))
													log=modifiedSentencekkl;
												else if(return_valuewst.equals("Successful"))
													log=modifiedSentencewst;
												else
													log="";
										}
						
					}catch(Exception e){}		
			
				}	
		}
		catch(Exception ex){}
		if(log=="")
			log=" Unable to book room! Record doesnt exists : Room no : "+no+"; Date :"+date+"; Time slot : "+timeslot+"; Booked by : - \t Booking ID : -";
				
		try (FileWriter f = new FileWriter(defaultLogFile, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);)
		{ 
			p.println(log); 
		} 
		catch (IOException i) { i.printStackTrace(); }
		System.out.println("\n\n\t\t Available rooms-slots in DVL campus ");
		System.out.print("\t\t------------------------------------\n\n");
		
			
		Iterator it = roomrec.entrySet().iterator();
		while (it.hasNext()) 
		{
			Map.Entry pair = (Map.Entry)it.next();
			DvlRoomRecord ras=(DvlRoomRecord)pair.getValue();
			System.out.println(pair.getKey() + " = " + ras.roomno+","+ras.date+" ,"+ras.timeslot+"  Booked by : "+ras.bookedby+" Booking ID : "+ras.bookingid);
		}
		return log;
  }

 
 
 public String changeReservation(String bookingID,String oldroomno,String olddate,String oldtimeslot,String username,String campus,String newroomno,String newdate,String newtimeslot)
 {
	String dvlbooked="false",wstbooked="false",kklbooked="false";
	String check="false";
	defaultLogFile="E:\\DVLCampus_Server_Logs_"+username+"_log.txt";
	int count= (int) (new Date().getTime()/1000);
	String log="",flag="",bookedby="-",bookingid="-";
	int max=0;
	int booklim=0;
	String modifiedSentencedvl="",modifiedSentencekkl="",modifiedSentencewst="";
	String return_value="";
	String return_valuedvl="",return_valuekkl="",return_valuewst="";
	
	try
	{
		//Verifying the old reservation to change
		if(!roomrec.isEmpty())
		{	
			Iterator itold = roomrec.entrySet().iterator();
			while (itold.hasNext()) 
			{
				Map.Entry pairold = (Map.Entry)itold.next();
				DvlRoomRecord rasold=(DvlRoomRecord)pairold.getValue();
				
				if(rasold.date.equals(olddate)==true && rasold.timeslot.equals(oldtimeslot)==true && rasold.roomno.equals(oldroomno)==true && rasold.bookedby==username )
				{
					//checking for availability in wst
					Iterator itnew = roomrec.entrySet().iterator();
					while (itnew.hasNext()) 
					{
						Map.Entry pairnew = (Map.Entry)itnew.next();
						DvlRoomRecord rasnew=(DvlRoomRecord)pairnew.getValue();
						if(rasnew.date.equals(newdate)==true && rasnew.timeslot.equals(newtimeslot)==true && rasnew.roomno.equals(newroomno)==true && rasnew.bookedby=="-" )
						{
								//new room is available and booking
								int k=(int)pairnew.getKey();
								DvlRoomRecord rc=(DvlRoomRecord)pairnew.getValue();
								synchronized(this)
								{
									roomrec.remove(pairnew.getKey(),(DvlRoomRecord)pairnew.getValue());
									rc.bookedby=username;
									rc.bookingid=username+""+(int)(new Date().getTime()/1000);
									bookingid=rc.bookingid;
									System.out.println("\n\n Room is booked by : "+rc.bookedby);
									roomrec.put(k,(DvlRoomRecord)rc);
									//updating the old reservation
								}
								int m=(int)pairold.getKey();
								DvlRoomRecord rcold=(DvlRoomRecord)pairold.getValue();
								synchronized(this)
								{
									roomrec.remove(pairold.getKey(),(DvlRoomRecord)pairold.getValue());
									rcold.bookedby="-";
									rcold.bookingid="-";
									System.out.println("\n\n Room is booked by : "+rc.bookedby);
									roomrec.put(k,(DvlRoomRecord)rcold);
									log="Change Successful\t"+" Room No : "+ rasnew.roomno+"\t Booking Date : "+rasnew.date+"\t Timeslot : "+rasnew.timeslot+"\t Booked by : "+username+"\t Booking ID : "+bookingid;
								}
								dvlbooked="true";
									
						}
					}
				}
				else if((campus.equals("DVLSERVER")|| campus.equals("KKLSERVER") || campus.equals("WSTSERVER") && !dvlbooked.equals("true")))
				{
					//checking for new rooms availability
					
					//DVL Available time slots
					DatagramSocket clientSocketwst = new DatagramSocket();
					InetAddress IPAddresswst = InetAddress.getByName("localhost");
					byte[] sendDatawst = new byte[1024];
					byte[] receiveDatawst = new byte[1024];
					
					
					String sentencewst = "BOOKROOMWST#"+newroomno+"#"+newdate+"#"+newtimeslot+"#"+username;
					sendDatawst = sentencewst.getBytes();
					
						
					DatagramPacket sendPacketwst = new DatagramPacket(sendDatawst, sendDatawst.length, IPAddresswst, 9999);
					clientSocketwst.send(sendPacketwst);
					DatagramPacket receivePacketwst = new DatagramPacket(receiveDatawst, receiveDatawst.length);
					clientSocketwst.receive(receivePacketwst);
					modifiedSentencewst = new String(receivePacketwst.getData());
					clientSocketwst.close();
					
					
					return_valuewst=modifiedSentencewst.substring(12,22);
					return_valuewst=return_valuewst.trim();
					
					
					if(!return_valuewst.equals("Successful"))
					{
					
							//KKL Available time slots
							DatagramSocket clientSocketkkl = new DatagramSocket();
							InetAddress IPAddresskkl = InetAddress.getByName("localhost");
							byte[] sendDatakkl = new byte[1024];
							byte[] receiveDatakkl = new byte[1024];
							
							
							String sentencekkl = "BOOKROOMKKL#"+newroomno+"#"+newdate+"#"+newtimeslot+"#"+username;
							sendDatakkl = sentencekkl.getBytes();
							
							
							DatagramPacket sendPacketkkl = new DatagramPacket(sendDatakkl, sendDatakkl.length, IPAddresskkl, 9998);
							clientSocketkkl.send(sendPacketkkl);
							DatagramPacket receivePacketkkl = new DatagramPacket(receiveDatakkl, receiveDatakkl.length);
							clientSocketkkl.receive(receivePacketkkl);
							modifiedSentencekkl = new String(receivePacketkkl.getData());
							clientSocketkkl.close();
					
					
							return_valuekkl=modifiedSentencekkl.substring(12,22);
							return_valuekkl=return_valuekkl.trim();
					
					
					}	
					if(return_valuewst.equals("Successful") || return_valuekkl.equals("Successful"))
					{
						
						int n=(int)pairold.getKey();
						DvlRoomRecord rcold=(DvlRoomRecord)pairold.getValue();
						synchronized(this)
						{
							roomrec.remove(pairold.getKey(),(DvlRoomRecord)pairold.getValue());
							rcold.bookedby="-";
							rcold.bookingid="-";
							
							roomrec.put(n,(DvlRoomRecord)rcold);
							log="Change Successful\t"+"Old Room No : "+ newroomno+"\t Booking Date : "+newdate+"\t Timeslot : "+newtimeslot+"\t Booked by : "+username;
			        
						}
						dvlbooked="true";
					}
				}
			}
		}
	}catch(Exception ch){}
	return log;
 }
 
 
 
 
 
 
 
 
 
}