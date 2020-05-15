import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

class RoomRecord
{
	String roomno,date,timeslot,bookedby,bookingid;
	public RoomRecord(String roomno,String date,String timeslot,String bookedby,String bookingid)
	{
		this.roomno=roomno;
		this.date=date;
		this.timeslot=timeslot;
		this.bookedby=bookedby;
		this.bookingid=bookingid;
	}
}
class UserRes
{
	public String username,booking_id1,booking_id2,booking_id3;
	public int booking_count;
	public UserRes(String username,int booking_count,String booking_id1,String booking_id2,String booking_id3)
	{
		this.username=username;
		this.booking_count=booking_count;
		this.booking_id1=booking_id1;
		this.booking_id2=booking_id2;
		this.booking_id3=booking_id3;
	}
}
public class KklBookingServer extends UnicastRemoteObject implements RoomBookingInterface
{
  protected static String defaultLogFile ="";
  public RoomRecord rr;
  public UserRes ur;
  HashMap<Integer,RoomRecord> roomrec=new HashMap<Integer,RoomRecord>();  
  HashMap<Integer,RoomRecord> userrec=new HashMap<Integer,RoomRecord>();  
  
  public WstBookingServer ( ) throws RemoteException {  super ( ); }

public String createRoom(String no,String date,String timeslot,String username)
{
	defaultLogFile="E:\\WSTCampus_Server_Logs_"+username+"_log.txt";
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
				RoomRecord ras=(RoomRecord)pair.getValue();
				
				try
				{
					if(ras.date.equals(date))
					{
						if(ras.roomno.equals(no))
						{
							if(ras.timeslot.equals(timeslot))
							{
								log="Room Creation \t Unsuccessful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t Booking ID :" +bookingid ;  
								
								break;
							}
							else
							{
								rr=new RoomRecord(no,date,timeslot,bookedby,bookingid);
								roomrec.put(count,rr);  
								log="Room Creation \t Successful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t Booking ID :" +bookingid ;  
								break;
							}
						}
						else
						{
								rr=new RoomRecord(no,date,timeslot,bookedby,bookingid);
								roomrec.put(count,rr);  
								log="Room Creation \t Successful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t Booking ID :" +bookingid ;  
								break;
						}
					}
					else
					{
						rr=new RoomRecord(no,date,timeslot,bookedby,bookingid);
						roomrec.put(count,rr);  
						log="Room Creation \t Successful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t Booking ID :" +bookingid ;  
									break;
					}
				}catch(Exception e){}

			}	
		}
		else
		{				
			rr=new RoomRecord(no,date,timeslot,bookedby,bookingid);
			roomrec.put(count,rr); 
			log="Room Creation \t Successful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t" +bookingid ;  	log="Room Creation \t Successful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t Booking ID :" +bookingid ;  
		}
	}catch(Exception e){}
					
			try (FileWriter f = new FileWriter(defaultLogFile, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);)
			{ 
				p.println(log); 
			} 
			catch (IOException i) { i.printStackTrace(); }
			return log;
}



public String deleteRoom(String no,String date,String timeslot,String username)
{
	defaultLogFile="E:\\WSTCampus_Server_Logs_"+username+"_log.txt";
	int count= (int) (new Date().getTime()/1000);
	String log="";
	
	try
	{
		if(!roomrec.isEmpty())
		{	
			Iterator it1 = roomrec.entrySet().iterator();
			while (it1.hasNext()) 
			{
				Map.Entry pair = (Map.Entry)it1.next();
				RoomRecord ras=(RoomRecord)pair.getValue();
				//System.out.println(pair.getKey() + " = " + ras.roomno+","+ras.date+" ,"+ras.timeslot);
				try
				{
					if(ras.date.equals(date))
					{
						if(ras.roomno.equals(no))
						{
							if(ras.timeslot.equals(timeslot))
							{
									
								log="Room-Timeslot Deletion  Successful  Room No : "+ras.roomno+" Date : "+ras.date+" TimeSlot : "+ras.timeslot+" Booked by : "+ras.bookedby+ " Booking ID :" +ras.bookingid+"";  
								roomrec.remove(pair.getKey(),(RoomRecord)pair.getValue());
								break;
							}
							else
							{
								System.out.println("No records found");
								break;
							}
						}
						else
						{
							System.out.println("No records found");
								break;
							
						}
					}
					else
					{
						System.out.println("No Records found");
						break;
					}
				}catch(Exception e){}

			}	
		}
		else
		{				
				//System.out.println("There is no room to delete !");
				log="Room-Timeslot deletion \t unsuccessful \t";
				
				/*Room No : "+ras.roomno+"\t Date : "+ras.date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t" +bookingid ;  
					*/	 
			//	System.out.println("Room no : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : NA");
			}
		
	}catch(Exception e){}
		
			/*Iterator it = roomrec.entrySet().iterator();
			while (it.hasNext()) 
			{
            Map.Entry pair = (Map.Entry)it.next();
			RoomRecord ras=(RoomRecord)pair.getValue();
            System.out.println(pair.getKey() + " = " + ras.roomno+","+ras.date+" ,"+ras.timeslot);
			}*/
			
			System.out.println(log);
			if(log=="")
			 log="Room Deletion \t unsuccessful ";  
		 
			/*\\ Room No : "+ras.roomno+"\t Date : "+ras.date+" TimeSlot : "+ras.timeslot+"\t Booked by : "+ras.bookedby+ "\t" +ras.bookingid ;  
				*/		
			
			try (FileWriter f = new FileWriter(defaultLogFile, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);)
			{ 
				p.println(log); 
			} 
			catch (IOException i) { i.printStackTrace(); }



			return log;
}


public String cancelBooking (String bookingID,String username,String no,String date,String timeslot)throws RemoteException
{
	defaultLogFile="E:\\WSTCampus_Server_Logs_"+username+"_log.txt";
	String log="";
		
	try
	{
		if(!roomrec.isEmpty())
		{	
			Iterator it1 = roomrec.entrySet().iterator();
			while (it1.hasNext()) 
			{
				Map.Entry pair = (Map.Entry)it1.next();
				RoomRecord ras=(RoomRecord)pair.getValue();
				System.out.println(pair.getKey() + " = " + ras.roomno+","+ras.date+" ,"+ras.timeslot);
				try
				{
					if(ras.date.equals(date))
					{
						if(ras.roomno.equals(no))
						{
							if(ras.timeslot.equals(timeslot))
							{
								int k=(int)pair.getKey();
								RoomRecord rc=(RoomRecord)pair.getValue();
								roomrec.remove(pair.getKey(),(RoomRecord)pair.getValue());
								rc.bookedby=username;
								System.out.println(rc.bookedby);
								roomrec.put(k,(RoomRecord)rc);
								System.out.println("After updating :");
								Iterator ittemp = roomrec.entrySet().iterator();
								while (ittemp.hasNext()) 
								{
									//RoomRecord ras1=(RoomRecord)pair1.getValue();
									Map.Entry pair1 = (Map.Entry)ittemp.next();
									RoomRecord ras1=(RoomRecord)pair1.getValue();
									System.out.println(pair1.getKey() + " = " + ras1.roomno+","+ras1.date+" ,"+ras1.timeslot+","+ras1.bookedby);
								}
								System.out.println("printed :");
			
								System.out.println("Booked successfully");
								log=username+""+(int)(new Date().getTime()/1000);
							break;
							}
							else
								System.out.println("No records found");
						}
						else
							System.out.println("No records found");
					}
					else
						System.out.println("No Records found");
				}catch(Exception e){}
			}	
		}
		else
		{				
			log="There is no room to book !";
				//log+="Room no : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by ";  
				//log+="Room no : "+no+"\t Date : "+date+"";// TimeSlot : "+timeslot+"\t Booked by ";  
			//	System.out.println("Room no : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : NA");
			}
		
	}catch(Exception e){}
		
			Iterator it = roomrec.entrySet().iterator();
			while (it.hasNext()) 
			{
            Map.Entry pair = (Map.Entry)it.next();
			RoomRecord ras=(RoomRecord)pair.getValue();
            System.out.println(pair.getKey() + " = " + ras.roomno+","+ras.date+" ,"+ras.timeslot);
			}
			
			System.out.println(log);
			if(log=="")
				log=" Unable to book room! Record doesnt exists ";//: Room no : "+no+"; Date :"+date+"";// Time slot : "+timeslot+"";		
			try (FileWriter f = new FileWriter(defaultLogFile, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);)
			{ 
				p.println(log); 
			} 
			catch (IOException i) { i.printStackTrace(); }



			return log;

  

	
	
	
	
}



  public String bookRoom(String no,String date,String timeslot,String username)throws RemoteException	
  {
	defaultLogFile="E:\\WSTCampus_Server_Logs_"+username+"_log.txt";
				String log="";
	try
	{
		if(!roomrec.isEmpty())
		{	
			Iterator it1 = roomrec.entrySet().iterator();
			while (it1.hasNext()) 
			{
				Map.Entry pair = (Map.Entry)it1.next();
				RoomRecord ras=(RoomRecord)pair.getValue();
				System.out.println(pair.getKey() + " = " + ras.roomno+","+ras.date+" ,"+ras.timeslot);
				try
				{
					if(ras.date.equals(date))
					{
						if(ras.roomno.equals(no))
						{
							if(ras.timeslot.equals(timeslot))
							{
								if(ras.bookedby=="")
								{
								int k=(int)pair.getKey();
								RoomRecord rc=(RoomRecord)pair.getValue();
								roomrec.remove(pair.getKey(),(RoomRecord)pair.getValue());
								rc.bookedby=username;
								System.out.println(rc.bookedby);
								roomrec.put(k,(RoomRecord)rc);
								Iterator ittemp = roomrec.entrySet().iterator();
								while (ittemp.hasNext()) 
								{
									//RoomRecord ras1=(RoomRecord)pair1.getValue();
									Map.Entry pair1 = (Map.Entry)ittemp.next();
									RoomRecord ras1=(RoomRecord)pair1.getValue();
									System.out.println(pair1.getKey() + " = " + ras1.roomno+","+ras1.date+" ,"+ras1.timeslot+","+ras1.bookedby);
								}
								}
								else
									System.out.println("This room is booked already ");
								log=username+""+(int)(new Date().getTime()/1000);
							/*	======================================
									
									if(!userrec.isEmpty())
									{
									Iterator it2 = userrec.entrySet().iterator();
									while (it2.hasNext()) 
									{
										Map.Entry upair = (Map.Entry)it2.next();
										UserRes uras=(UserRes)upair.getValue();
										try
										{
											if(uras.username.equals(username))
												{
													if(uras.booking_count<3)
													{
														if(uras.booking_id1==null)
														{
															booking_count+=1;
															booking_id1=
															ur=new UserRes(username,booking_count,booking_id1,booking_id2,booking_id3);
															userrec.put(count,ur);  
															log="Room Boooking \t Successful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t Booking ID :" +bookingid ;  
												
														}	
															if(uras.booking_id2==null)
															{	
																if(uras.booking_id3==null)
																{
														break;
											}
										}
										else
										{
												rr=new RoomRecord(no,date,timeslot,bookedby,bookingid);
												roomrec.put(count,rr);  
												log="Room Creation \t Successful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t Booking ID :" +bookingid ;  
												break;
										}
									}
									else
									{
										rr=new RoomRecord(no,date,timeslot,bookedby,bookingid);
										roomrec.put(count,rr);  
										log="Room Creation \t Successful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t Booking ID :" +bookingid ;  
													break;
									}
								}catch(Exception e){}
				
							}	
							}
							else
							{				
								rr=new RoomRecord(no,date,timeslot,bookedby,bookingid);
								roomrec.put(count,rr); 
								log="Room Creation \t Successful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t" +bookingid ;  	log="Room Creation \t Successful \t Room No : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : "+bookedby+ "\t Booking ID :" +bookingid ;  
							}
						}catch(Exception e){}
					
													
								
								======================================
								*/	
							break;
							}
							else
								System.out.println("No records found");
						}
						else
							System.out.println("No records found");
					}
					else
						System.out.println("No Records found");
				}catch(Exception e){}
			}	
		}
		else
		{				
			System.out.println("There is no room to book !");
				log+="Room no : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by ";  
				System.out.println("Room no : "+no+"\t Date : "+date+" TimeSlot : "+timeslot+"\t Booked by : NA");
			}
		
	}catch(Exception e){}
		
			Iterator it = roomrec.entrySet().iterator();
			while (it.hasNext()) 
			{
            Map.Entry pair = (Map.Entry)it.next();
			RoomRecord ras=(RoomRecord)pair.getValue();
            System.out.println(pair.getKey() + " = " + ras.roomno+","+ras.date+" ,"+ras.timeslot);
			}
			
			System.out.println(log);
			if(log=="")
				log=" Unable to book room! Record doesnt exists : Room no : "+no+"; Date :"+date+"; Time slot : "+timeslot+"; Booked by : NA ";
			
			try (FileWriter f = new FileWriter(defaultLogFile, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);)
			{ 
				p.println(log); 
			} 
			catch (IOException i) { i.printStackTrace(); }
			return log;
  }
  
  public static void main (String[] args)
  {
    try
    {
      DvlBookingServer server = new DvlBookingServer();
      String name = "rmi://localhost:9999/KklBookingSystem";
      Naming.rebind (name, server);
      System.out.println (name + " is running");
    }
    catch (Exception ex)
    {
      System.err.println (ex);
	//Naming.unbind("rmi://localhost:9999/WstBookingSystem");
    }
  }
}

 	