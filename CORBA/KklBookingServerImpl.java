
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
class KklRoomRecord
{
	String roomno,date,timeslot,bookedby,bookingid;
	public KklRoomRecord(String roomno,String date,String timeslot,String bookedby,String bookingid)
	{
		this.roomno=roomno;
		this.date=date;
		this.timeslot=timeslot;
		this.bookedby=bookedby;
		this.bookingid=bookingid;
	}
}
public class KklBookingServerImpl extends RoomBookingInterfacePOA 
{
	private ORB orb;
	protected static String logfile ="";
	public KklRoomRecord rr;
	HashMap<Integer,KklRoomRecord> roomrec=new HashMap<Integer,KklRoomRecord>();  
	public KklBookingServerImpl( )  {  super ( ); }
  
	public void setORB(ORB orb_val) 
	{
		orb = orb_val; 
	}
		
  	public String createRoom(String no,String date,String timeslot,String username)
	{
		String isavailable="";
		logfile="E:\\KKLCampus_Server_Logs_"+username+"_log.txt";
		int count= (int) (new Date().getTime()/1000);
		String log="",bookedby="-",bookingid="-";
		try
		{
			if(!roomrec.isEmpty())
			{	
				Iterator it1 = roomrec.entrySet().iterator();
				while (it1.hasNext()) 
				{
					Map.Entry pair = (Map.Entry)it1.next();
					KklRoomRecord ras=(KklRoomRecord)pair.getValue();
				
					if(ras.date.equals(date)==true && ras.timeslot.equals(timeslot)==true && ras.roomno.equals(no)==true)
					{
						isavailable="true";
						log="Room Creation \t Unsuccessful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t Booking ID : " +bookingid ;  
					}
					else
					{
						if(!isavailable.equals("true"))
							isavailable="false";
					}
				}
			}
			else
				isavailable="false";
			
			if(isavailable.equals("false"))
			{
				rr=new KklRoomRecord(no,date,timeslot,bookedby,bookingid);
				synchronized(this)
				{
					roomrec.put(count,rr);
				}
				log="Room Creation \t Successful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t Booking ID : " +bookingid ;  
			}
			else
				log="Room Creation \t Unsuccessful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t Booking ID : " +bookingid ;  
				
			
		}catch(Exception e){}	
		
		try (FileWriter f = new FileWriter(logfile, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);)
		{ 
			p.println(log); 
		} 
		catch (IOException i) { i.printStackTrace(); }
		
		System.out.println("\n\n\t\t Available rooms-slots in KKL campus ");
		System.out.print("\t\t------------------------------------\n\n");
		Iterator itview = roomrec.entrySet().iterator();
		
		while (itview.hasNext()) 
		{
			Map.Entry pair = (Map.Entry)itview.next();
			KklRoomRecord ras=(KklRoomRecord)pair.getValue();
			System.out.println("Room No : "+ras.roomno+" Date : "+ras.date+" TimeSlot : "+ras.timeslot+" Booked by : "+ras.bookedby+ " Booking ID : " +ras.bookingid+""); 
		}
		return log;
	
}
		
	public String deleteRoom(String no,String date,String timeslot,String username)
	{
	
		String check="false";
		logfile="E:\\KKLCampus_Server_Logs_"+username+"_log.txt";
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
					KklRoomRecord ras=(KklRoomRecord)pair.getValue();
					
					if(ras.date.equals(date)==true && ras.timeslot.equals(timeslot)==true && ras.roomno.equals(no)==true)
					{
						
						synchronized(this){roomrec.remove(pair.getKey(),(KklRoomRecord)pair.getValue());}
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
		try (FileWriter f = new FileWriter(logfile, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);)
		{ 
			p.println(log); 
		} 
		catch (IOException i) { i.printStackTrace(); }
		System.out.println("\n\n\t\t Available rooms-slots in KKL campus ");
		System.out.print("\t\t------------------------------------\n\n");
		Iterator itview = roomrec.entrySet().iterator();
		while (itview.hasNext()) 
		{
			Map.Entry pair = (Map.Entry)itview.next();
			KklRoomRecord ras=(KklRoomRecord)pair.getValue();
			System.out.println("Room No : "+ras.roomno+" Date : "+ras.date+" TimeSlot : "+ras.timeslot+" Booked by : "+ras.bookedby+ " Booking ID :" +ras.bookingid+""); 
		}
		return log;
	}

	public String getAvailableTimeSlot(String date,String username,String campus)
	{
			String return_value="";
			int count=0;
			logfile="E:\\KKLCampus_Server_Logs_"+username+"_log.txt";
			String modifiedSentencedvl="",modifiedSentencekkl="",modifiedSentencewst="";

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
							KklRoomRecord ras=(KklRoomRecord)pair.getValue();
							if(ras.date.equals(date)==true && ras.bookedby=="-")
							{
									count++;
							}
						}
						return_value=""+count;
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
					
					return_value=" DVL : "+(modifiedSentencedvl.trim())+"\t";
					
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
					
					return_value=return_value+" KKL : "+(modifiedSentencekkl.trim())+"\t";
					
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
			
		try (FileWriter f = new FileWriter(logfile, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);)
		{ 
			p.println(return_value); 
		} 
		catch (IOException i) { i.printStackTrace(); }
		System.out.println("\n\n\t\t Available rooms-slots in KKL campus ");
		System.out.print("\t\t------------------------------------\n\n");
		Iterator itview = roomrec.entrySet().iterator();
		while (itview.hasNext()) 
		{
			Map.Entry pair = (Map.Entry)itview.next();
			KklRoomRecord ras=(KklRoomRecord)pair.getValue();
			
			System.out.println("Room No : "+ras.roomno+" Date : "+ras.date+" TimeSlot : "+ras.timeslot+" Booked by : "+ras.bookedby+ " Booking ID :" +ras.bookingid+""); 
		}
		return return_value;
} 

	public String bookRoom(String no,String date,String timeslot,String username,String campus)
	{
	  
		int countdvl=0,countwst=0,countkkl=0;
		
		String dvlbooked="false",wstbooked="false",kklbooked="false";
		logfile="E:\\KKLCampus_Server_Logs_"+username+"_log.txt";
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
						KklRoomRecord ras=(KklRoomRecord)pair.getValue();
						System.out.println(ras.date.equals(date)+","+ras.timeslot.equals(timeslot)+","+ras.roomno.equals(no));
						
						if(ras.date.equals(date)==true && ras.timeslot.equals(timeslot)==true && ras.roomno.equals(no)==true && ras.bookedby=="-" && booklim<3)
						{
							int k=(int)pair.getKey();
							KklRoomRecord rc=(KklRoomRecord)pair.getValue();
							synchronized(this){
							roomrec.remove(pair.getKey(),(KklRoomRecord)pair.getValue());
							rc.bookedby=username;
							rc.bookingid=username+""+(int)(new Date().getTime()/1000);
							bookingid=rc.bookingid;
							roomrec.put(k,(KklRoomRecord)rc);
							}
							log="Roombooking Successful\t"+" Room No : "+ ras.roomno+"\t Booking Date : "+ras.date+"\t Timeslot : "+ras.timeslot+"\t Booked by : "+username+"\t Booking ID : "+bookingid;
							kklbooked="true";
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
					//DVL Available time slots
					DatagramSocket clientSocketdvlcount = new DatagramSocket();
					InetAddress IPAddressdvlcount = InetAddress.getByName("localhost");
					byte[] sendDatadvlcount = new byte[1024];
					byte[] receiveDatadvlcount = new byte[1024];
					String sentencedvlcount = "GETLIMITDVL#"+date+"#"+username;
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
					sendDatawstcount = sentencewstcount.getBytes();
						
					DatagramPacket sendPacketwstcount = new DatagramPacket(sendDatawstcount, sendDatawstcount.length, IPAddresswstcount, 9997);
					clientSocketwstcount.send(sendPacketwstcount);
					DatagramPacket receivePacketwstcount = new DatagramPacket(receiveDatawstcount, receiveDatawstcount.length);
					clientSocketwstcount.receive(receivePacketwstcount);
					modifiedSentencewstcount = new String(receivePacketwstcount.getData());
					clientSocketwstcount.close();
					return_valuecount=""+(modifiedSentencewstcount.trim())+"";
					countwst=Integer.parseInt(return_valuecount);
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
			log=" Unable to book room!  Room no : "+no+"; Date :"+date+"; Time slot : "+timeslot+"; Booked by : - \t Booking ID : -";
				
		try (FileWriter f = new FileWriter(logfile, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);)
		{ 
			p.println(log); 
		} 
		catch (IOException i) { i.printStackTrace(); }
		System.out.println("\n\n\t\t Available rooms-slots in KKL campus ");
		System.out.print("\t\t------------------------------------\n\n");
		
		Iterator it = roomrec.entrySet().iterator();
		while (it.hasNext()) 
		{
			Map.Entry pair = (Map.Entry)it.next();
			KklRoomRecord ras=(KklRoomRecord)pair.getValue();
			System.out.println(pair.getKey() + " = " + ras.roomno+","+ras.date+" ,"+ras.timeslot+"  Booked by : "+ras.bookedby+" Booking ID : "+ras.bookingid);
		}
		return log;
	}

 	public int countLimit(String date,String username)
	{
		
		int booklim=0;
		try{
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
						
				if(!roomrec.isEmpty())
				{
					Iterator rese = roomrec.entrySet().iterator();
					while (rese.hasNext()) 
					{
						Map.Entry lim= (Map.Entry)rese.next();
						KklRoomRecord res=(KklRoomRecord)lim.getValue();
					
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
		}catch(Exception er){}
		return booklim;	
	}
	
	public String cancelBooking (String booking_id,String username,String campus)
	{
		logfile="E:\\KKLCampus_Server_Logs_"+username+"_log.txt";
		int count= (int) (new Date().getTime()/1000);
		String log="",flag="",bookedby="-",bookingid="-";
		String modifiedSentencedvl="",modifiedSentencekkl="",modifiedSentencewst="";
		String return_value="",return_valuecandvl="",return_valuecankkl="",return_valuecanwst="";
		
			if(campus.equals("KKLSERVER"))
			{
			
						try
						{
								if(!roomrec.isEmpty())
								{	
									Iterator it1 = roomrec.entrySet().iterator();
									while (it1.hasNext()) 
									{
										Map.Entry pair = (Map.Entry)it1.next();
										KklRoomRecord ras=(KklRoomRecord)pair.getValue();
										
											System.out.println((ras.bookingid.equals(booking_id)));
											System.out.println((ras.bookingid==booking_id));
										//if(ras.date.equals(date)==true && ras.timeslot.equals(timeslot)==true && ras.roomno.equals(no)==true && ras.bookedby!="-" && (ras.bookedby.equals(username)) && (ras.bookingid.equals(booking_id)))
										if((ras.bookedby.equals(username)) && (ras.bookingid.equals(booking_id)))
										{
											int k=(int)pair.getKey();
											KklRoomRecord rc=(KklRoomRecord)pair.getValue();
											synchronized(this){
											roomrec.remove(pair.getKey(),(KklRoomRecord)pair.getValue());
											rc.bookedby="-";
											rc.bookingid="-";
											
											System.out.println("\n\n Room is cancelled by : "+username);
											roomrec.put(k,(KklRoomRecord)rc);
											}
											log="Room Cancellation Successful \t Date : "+ras.date+"\tTime slot : "+ras.timeslot+"\t Room no : "+ras.roomno+"\t Cancelled by : "+ras.bookedby+ "\t Booking ID : "+ras.bookingid;
										}
										else
										{
											log="Room Cancellation Unsuccessful \t Booking ID : "+booking_id;
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
					
						String sentencedvl = "CANCELROOMDVL#"+booking_id+"#"+username;
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
							
							
							String sentencekkl = "CANCELROOMKKL#"+booking_id+"#"+username;
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
							
							
							String sentencewst = "CANCELROOMWST#"+booking_id+"#"+username;
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
						else
							log="";
							
						System.out.println("Log value before catch : "+log);				
				
					}catch(Exception e){}
				
			}
			
	
						
		if(log=="")
			log=" Unable to cancel room! Booking ID : "+booking_id;
				
		try (FileWriter f = new FileWriter(logfile, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);)
		{ 
			p.println(log); 
		} 
		catch (IOException i) { i.printStackTrace(); }
		
		System.out.println("\n\n\t\t Available rooms-slots in KKL campus ");
		System.out.print("\t\t------------------------------------\n\n");
		
		
		Iterator it = roomrec.entrySet().iterator();
		while (it.hasNext()) 
		{
			Map.Entry pair = (Map.Entry)it.next();
			KklRoomRecord ras=(KklRoomRecord)pair.getValue();
			System.out.println(pair.getKey() + " = " + ras.roomno+","+ras.date+" ,"+ras.timeslot+"  Booked by : "+ras.bookedby+" Booking ID : "+ras.bookingid);
		}
		
		
		
		return log;
	}
  
   
 public String changeReservation(String booking_id,String username,String campus,String newroomno,String newdate,String newtimeslot)
 {
	String logbook="",logcan=""; 
	String oldcampus="",newcampus="";
	String dvlbooked="false",wstbooked="false",kklbooked="false";
	logfile="E:\\KKLCampus_Server_Logs_"+username+"_log.txt";
	int count= (int) (new Date().getTime()/1000);
	String log="",flag="",bookedby="-",bookingid="-";
	int max=0;
	int booklim=0;
	String modifiedSentencedvl1="",modifiedSentencekkl1="",modifiedSentencewst1="";
	String modifiedSentencedvl2="",modifiedSentencekkl2="",modifiedSentencewst2="";
	String modifiedSentencedvl3="",modifiedSentencekkl3="",modifiedSentencewst3="";
	String return_value="";
	String return_valuedvl1="",return_valuekkl1="",return_valuewst1="";
	String return_valuedvl2="",return_valuekkl2="",return_valuewst2="";
	String return_valuedvl3="",return_valuekkl3="",return_valuewst3="";
	
	String return_valuecandvl1="",return_valuecankkl1="",return_valuecanwst1="";
	String return_valuecandvl2="",return_valuecankkl2="",return_valuecanwst2="";
	String return_valuecandvl3="",return_valuecankkl3="",return_valuecanwst3="";
	String revert_bookid="",newbookid="";
	try
	{
			//Booking the new room
			
			//DVL Available time slots
			DatagramSocket clientSocketdvl1 = new DatagramSocket();
			InetAddress IPAddressdvl1 = InetAddress.getByName("localhost");
			byte[] sendDatadvl1 = new byte[1024];
			byte[] receiveDatadvl1 = new byte[1024];
			
			String sentencedvl1 = "BOOKROOMDVL#"+newroomno+"#"+newdate+"#"+newtimeslot+"#"+username;
			sendDatadvl1 = sentencedvl1.getBytes();
				
			DatagramPacket sendPacketdvl1 = new DatagramPacket(sendDatadvl1, sendDatadvl1.length, IPAddressdvl1, 9999);
			clientSocketdvl1.send(sendPacketdvl1);
			DatagramPacket receivePacketdvl1 = new DatagramPacket(receiveDatadvl1, receiveDatadvl1.length);
			clientSocketdvl1.receive(receivePacketdvl1);
			modifiedSentencedvl1 = new String(receivePacketdvl1.getData());
			clientSocketdvl1.close();
			
			return_valuedvl1=modifiedSentencedvl1.substring(12,22);
			return_valuedvl1=return_valuedvl1.trim();
			System.out.println("Return value after dvl booking :"+return_valuedvl1);
			if(!return_valuedvl1.equals("Successful"))
			{
			
					//KKL Available time slots
					DatagramSocket clientSocketkkl1 = new DatagramSocket();
					InetAddress IPAddresskkl1 = InetAddress.getByName("localhost");
					byte[] sendDatakkl1 = new byte[1024];
					byte[] receiveDatakkl1 = new byte[1024];
					
					String sentencekkl1 = "BOOKROOMKKL#"+newroomno+"#"+newdate+"#"+newtimeslot+"#"+username;
					sendDatakkl1 = sentencekkl1.getBytes();
					
					DatagramPacket sendPacketkkl1 = new DatagramPacket(sendDatakkl1, sendDatakkl1.length, IPAddresskkl1, 9998);
					clientSocketkkl1.send(sendPacketkkl1);
					DatagramPacket receivePacketkkl1 = new DatagramPacket(receiveDatakkl1, receiveDatakkl1.length);
					clientSocketkkl1.receive(receivePacketkkl1);
					modifiedSentencekkl1 = new String(receivePacketkkl1.getData());
					clientSocketkkl1.close();
			
			
					return_valuekkl1=modifiedSentencekkl1.substring(12,22);
					return_valuekkl1=return_valuekkl1.trim();
					
					
					
			}	
			if(!return_valuedvl1.equals("Successful") && !return_valuekkl1.equals("Successful"))
			{
						//WST Available time slots
						DatagramSocket clientSocketwst1 = new DatagramSocket();
						InetAddress IPAddresswst1 = InetAddress.getByName("localhost");
						byte[] sendDatawst1 = new byte[1024];
						byte[] receiveDatawst1 = new byte[1024];
						
						String sentencewst1 = "BOOKROOMWST#"+newroomno+"#"+newdate+"#"+newtimeslot+"#"+username;
						sendDatawst1 = sentencewst1.getBytes();
					
						DatagramPacket sendPacketwst1 = new DatagramPacket(sendDatawst1, sendDatawst1.length, IPAddresswst1, 9997);
						clientSocketwst1.send(sendPacketwst1);
						DatagramPacket receivePacketwst1 = new DatagramPacket(receiveDatawst1, receiveDatawst1.length);
						clientSocketwst1.receive(receivePacketwst1);
						modifiedSentencewst1 = new String(receivePacketwst1.getData());
						clientSocketwst1.close();
						
						return_valuewst1=modifiedSentencewst1.substring(12,22);
						return_valuewst1=return_valuewst1.trim();
			}
			StringTokenizer st1=new StringTokenizer(modifiedSentencedvl1);
			StringTokenizer st2=new StringTokenizer(modifiedSentencekkl1);
			
			StringTokenizer st3=new StringTokenizer(modifiedSentencewst1);
			String temp1,temp2,temp3,temp4,temp5,temp6;
			if(return_valuedvl1.equals("Successful"))
			{
				logbook=modifiedSentencedvl1;
				temp1=st1.nextToken(":");
				temp2=st1.nextToken(":");
				temp3=st1.nextToken(":");
				temp4=st1.nextToken(":");
				temp5=st1.nextToken(":");
				temp6=st1.nextToken(":");
				revert_bookid=temp6.trim();
			}
			else if(return_valuekkl1.equals("Successful"))
			{
				logbook=modifiedSentencekkl1;
				temp1=st2.nextToken(":");
				temp2=st2.nextToken(":");
				temp3=st2.nextToken(":");
				temp4=st2.nextToken(":");
				temp5=st2.nextToken(":");
				temp6=st2.nextToken(":");
				revert_bookid=temp6.trim();
			}
			else if(return_valuewst1.equals("Successful"))
			{
				logbook=modifiedSentencewst1;
				temp1=st3.nextToken(":");
				temp2=st3.nextToken(":");
				temp3=st3.nextToken(":");
				temp4=st3.nextToken(":");
				temp5=st3.nextToken(":");
				temp6=st3.nextToken(":");
				revert_bookid=temp6.trim();
			}
			else
					logbook="Unsuccessful! New room not found";
		
			if((return_valuedvl1.equals("Successful") || return_valuekkl1.equals("Successful")||return_valuewst1.equals("Successful")))
			{
				//Cancelling the old reservation
				if((return_valuedvl1.equals("Successful") || return_valuekkl1.equals("Successful")||return_valuewst1.equals("Successful")))
				{
							//DVL Available time slots
							DatagramSocket clientSocketdvl2 = new DatagramSocket();
							InetAddress IPAddressdvl2 = InetAddress.getByName("localhost");
							byte[] sendDatadvl2 = new byte[1024];
							byte[] receiveDatadvl2 = new byte[1024];
						
							String sentencedvl2 = "CANCELROOMDVL#"+booking_id+"#"+username;
							sendDatadvl2 = sentencedvl2.getBytes();
							
								
							DatagramPacket sendPacketdvl2 = new DatagramPacket(sendDatadvl2, sendDatadvl2.length, IPAddressdvl2, 9999);
							clientSocketdvl2.send(sendPacketdvl2);
							DatagramPacket receivePacketdvl2 = new DatagramPacket(receiveDatadvl2, receiveDatadvl2.length);
							clientSocketdvl2.receive(receivePacketdvl2);
							modifiedSentencedvl2 = new String(receivePacketdvl2.getData());
							clientSocketdvl2.close();
							
							return_valuecandvl2=""+(modifiedSentencedvl2.trim())+"";
							return_valuecandvl2=return_valuecandvl2.substring(18,28);
							if(!return_valuecandvl2.equals("Successful"))
							{					
								//KKL Cancel booking
								DatagramSocket clientSocketkkl2 = new DatagramSocket();
								InetAddress IPAddresskkl2 = InetAddress.getByName("localhost");
								byte[] sendDatakkl2 = new byte[1024];
								byte[] receiveDatakkl2 = new byte[1024];
								
								
								String sentencekkl2 = "CANCELROOMKKL#"+booking_id+"#"+username;
								sendDatakkl2 = sentencekkl2.getBytes();
								
								
								DatagramPacket sendPacketkkl2 = new DatagramPacket(sendDatakkl2, sendDatakkl2.length, IPAddresskkl2, 9998);
								clientSocketkkl2.send(sendPacketkkl2);
								DatagramPacket receivePacketkkl2 = new DatagramPacket(receiveDatakkl2, receiveDatakkl2.length);
								clientSocketkkl2.receive(receivePacketkkl2);
								modifiedSentencekkl2 = new String(receivePacketkkl2.getData());
								clientSocketkkl2.close();
								
								return_valuecankkl2=""+(modifiedSentencekkl2.trim())+"";
								return_valuecankkl2=return_valuecankkl2.substring(18,28);
								
							}
							if(!return_valuecandvl2.equals("Successful") && !return_valuecankkl2.equals("Successful"))
							{
								//WST cancel booking
								DatagramSocket clientSocketwst2 = new DatagramSocket();
								InetAddress IPAddresswst2 = InetAddress.getByName("localhost");
								byte[] sendDatawst2 = new byte[1024];
								byte[] receiveDatawst2 = new byte[1024];
								
								
								String sentencewst2 = "CANCELROOMWST#"+booking_id+"#"+username;
								sendDatawst2 = sentencewst2.getBytes();
												
								
								DatagramPacket sendPacketwst2 = new DatagramPacket(sendDatawst2, sendDatawst2.length, IPAddresswst2, 9997);
								clientSocketwst2.send(sendPacketwst2);
								DatagramPacket receivePacketwst2 = new DatagramPacket(receiveDatawst2, receiveDatawst2.length);
								clientSocketwst2.receive(receivePacketwst2);
								modifiedSentencewst2 = new String(receivePacketwst2.getData());
								clientSocketwst2.close();
								
								return_valuecanwst2=""+(modifiedSentencewst2.trim());
								return_valuecanwst2=return_valuecanwst2.substring(18,28);
							}
		
				}
							if(return_valuecandvl2.equals("Successful"))
									logcan=modifiedSentencedvl2;
							else if(return_valuecankkl2.equals("Successful"))
									logcan=modifiedSentencekkl2;
							else if(return_valuecanwst2.equals("Successful"))
									logcan=modifiedSentencewst2;
							else
									logcan="Unsuccessful";
				
				//reverting the booking if new room is not bookedby
				
				if((!return_valuecandvl2.equals("Successful") && !return_valuecankkl2.equals("Successful")&&!return_valuecanwst2.equals("Successful")))
				{
					
									//DVL Available time slots
							DatagramSocket clientSocketdvl3 = new DatagramSocket();
							InetAddress IPAddressdvl3 = InetAddress.getByName("localhost");
							byte[] sendDatadvl3 = new byte[1024];
							byte[] receiveDatadvl3 = new byte[1024];
						
							String sentencedvl3 = "CANCELROOMDVL#"+revert_bookid+"#"+username;
							sendDatadvl3 = sentencedvl3.getBytes();
							
								
							DatagramPacket sendPacketdvl3 = new DatagramPacket(sendDatadvl3, sendDatadvl3.length, IPAddressdvl3, 9999);
							clientSocketdvl3.send(sendPacketdvl3);
							DatagramPacket receivePacketdvl3 = new DatagramPacket(receiveDatadvl3, receiveDatadvl3.length);
							clientSocketdvl3.receive(receivePacketdvl3);
							modifiedSentencedvl3 = new String(receivePacketdvl3.getData());
							clientSocketdvl3.close();
							
							return_valuecandvl3=""+(modifiedSentencedvl3.trim())+"";
							return_valuecandvl3=return_valuecandvl3.substring(18,28);
							
							if(!return_valuecandvl3.equals("Successful"))
							{					
								//KKL Cancel booking
								DatagramSocket clientSocketkkl3 = new DatagramSocket();
								InetAddress IPAddresskkl3 = InetAddress.getByName("localhost");
								byte[] sendDatakkl3 = new byte[1024];
								byte[] receiveDatakkl3 = new byte[1024];
								
								
								String sentencekkl3 = "CANCELROOMKKL#"+revert_bookid+"#"+username;
								sendDatakkl3 = sentencekkl3.getBytes();
								
								
								DatagramPacket sendPacketkkl3 = new DatagramPacket(sendDatakkl3, sendDatakkl3.length, IPAddresskkl3, 9998);
								clientSocketkkl3.send(sendPacketkkl3);
								DatagramPacket receivePacketkkl3 = new DatagramPacket(receiveDatakkl3, receiveDatakkl3.length);
								clientSocketkkl3.receive(receivePacketkkl3);
								modifiedSentencekkl3 = new String(receivePacketkkl3.getData());
								clientSocketkkl3.close();
								
								return_valuecankkl3=""+(modifiedSentencekkl3.trim())+"";
								return_valuecankkl3=return_valuecankkl3.substring(18,28);
							
							}
							if(!return_valuecandvl3.equals("Successful") && !return_valuecankkl3.equals("Successful"))
							{
								//WST cancel booking
								DatagramSocket clientSocketwst3 = new DatagramSocket();
								InetAddress IPAddresswst3 = InetAddress.getByName("localhost");
								byte[] sendDatawst3 = new byte[1024];
								byte[] receiveDatawst3 = new byte[1024];
								
								
								String sentencewst3 = "CANCELROOMWST#"+revert_bookid+"#"+username;
								sendDatawst3 = sentencewst3.getBytes();
												
								
								DatagramPacket sendPacketwst3 = new DatagramPacket(sendDatawst3, sendDatawst3.length, IPAddresswst3, 9997);
								clientSocketwst3.send(sendPacketwst3);
								DatagramPacket receivePacketwst3 = new DatagramPacket(receiveDatawst3, receiveDatawst3.length);
								clientSocketwst3.receive(receivePacketwst3);
								modifiedSentencewst3 = new String(receivePacketwst3.getData());
								clientSocketwst3.close();
								
								return_valuecanwst3=""+(modifiedSentencewst3.trim());
								return_valuecanwst3=return_valuecanwst3.substring(18,28);
							}
							logbook="Unsuccessful";
							logcan="Unsuccessful";
			
						}
						
						
				}
				}		catch(Exception ch){}
				
				
		try (FileWriter f = new FileWriter(logfile, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);)
		{ 
			p.println("Change reservation : "+logbook+"\n"+logcan); 
		} 
		catch (IOException i) { i.printStackTrace(); }
		System.out.println("\n\n\t\t Available rooms-slots in KKL campus ");
		System.out.print("\t\t------------------------------------\n\n");
			
		Iterator it = roomrec.entrySet().iterator();
		while (it.hasNext()) 
		{
			Map.Entry pair = (Map.Entry)it.next();
			KklRoomRecord ras=(KklRoomRecord)pair.getValue();
			System.out.println(pair.getKey() + " = " + ras.roomno+","+ras.date+" ,"+ras.timeslot+"  Booked by : "+ras.bookedby+" Booking ID : "+ras.bookingid);
		}
	 
	 
	 return ("\n"+logbook.trim()+"\n"+logcan.trim());
 }
 
  
  
  
  
  
  
  
  
  
  
 
}