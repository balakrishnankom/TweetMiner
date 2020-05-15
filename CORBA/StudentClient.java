import RoomBooking.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.io.*;
import java.util.*;
 
public class StudentClient
{
	//User credentials for students
	public static String[][] login={
					{"DVLS1111","DVLS1111"},{"DVLS1112","DVLS1112"},{"DVLS1113","DVLS1113"},
					{"KKLS1111","KKLS1111"},{"KKLS1112","KKLS1112"},{"KKLS1113","KKLS1113"},
					{"WSTS1111","WSTS1111"},{"WSTS1112","WSTS1112"},{"WSTS1113","WSTS1113"}};
	
	public static void main(String args[])throws IOException
	{
		
		String username,password;
		int roomslotchoice;
		String room_slot="",camp="",name="";
		RoomBookingInterface rbi;
		//Login validation
		
		java.io.DataInputStream ip=new java.io.DataInputStream(System.in);
		System.out.println("\n\n\n\t\t\t-------------Login--------------");
		System.out.print("\n\n\t\t Username : ");
		username=ip.readLine();
		System.out.print("\n\n\t\t Password : ");
		password=ip.readLine();

		for(int i=0;i<9;i++)
		{
			if(login[i][0].equals(username))
			{
				if(login[i][1].equals(password))
				{
					camp=login[i][0].substring(0,3);
					System.out.print("\n\n\t\tSuccessful login..! Connecting to server...");
					switch (camp) 
					{
						case "WST":name = "WST";break;
						case "DVL":name = "DVL";break;
						case "KKL":name = "KKL";break;
					}
				}
			}
		}
		
		try
		{
			//Getting object reference
			ORB orb = ORB.init(args, null);
			org.omg.CORBA.Object objRef =   orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			RoomBookingInterface addobj = (RoomBookingInterface) RoomBookingInterfaceHelper.narrow(ncRef.resolve_str(name));
	
			//List of operations that user can do
		    for(;;)
			{
				System.out.println("\n\n\t\t--------------------Room Booking Service--------------------");
				System.out.println("\n\n\tPlease select a service");
				System.out.println("\t1. Book Rooms");
				System.out.println("\t2. Get availability of room");
				System.out.println("\t3. Cancel booking");
				System.out.println("\t4. Change Reservation");
				System.out.println("\t5. Exit ");
				BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("\tChoose any option (0-5) :");
				System.out.flush();
				String response = input.readLine();
				int i = Integer.parseInt(response);
				try
				{
					switch (i)
					{
						case 1:
								//Room booking
								System.out.println("\n\t\tRoom Booking Service");
								System.out.print("\n\t\tEnter the room name : ");
								String roomno= input.readLine();
								System.out.print("\n\t\tEnter the date : "); 
								String date = input.readLine();
								System.out.print("\n\tAvailable Slots :");
								System.out.println ("\n\n\t 1. 1-3 \n\t 2. 3-5 \n\t 3. 5-7 \n\t 4. 7-9 \n\t 5. 9-11 \n\t 6. 11-13 \n\t 7. 13-15 \n\t 8.15-17 \n\t 9.17-19 \n\t 10. 19-21 \n\t 11. 21-23 \n\t 12. 23-1"); 
								System.out.print("\n Enter your choice : ");
								roomslotchoice=Integer.parseInt(input.readLine());
								switch(roomslotchoice)
								{
									case 1: room_slot="1-3";break;
									case 2: room_slot="3-5";break;
									case 3: room_slot="5-7";break;
									case 4: room_slot="7-9";break;
									case 5: room_slot="9-11";break;
									case 6: room_slot="11-13";break;
									case 7: room_slot="13-15";break;
									case 8: room_slot="15-17";break;
									case 9: room_slot="17-19";break;
									case 10: room_slot="19-21";break;
									case 11: room_slot="21-23";break;
									case 12: room_slot="23-1";break;
								}
								//Invoking the method to book Room
								String resp = addobj.bookRoom(roomno,date,room_slot,username,(username.substring(0,3)));
								System.out.println("Log :"+resp);
								break;
						case 2:
								//Invoking the method to list available rooms across the servers
								System.out.println("\n\t\t Available rooms ");
								System.out.print("\n\t\t Enter the date : ");
								date= input.readLine();
								String repo=addobj.getAvailableTimeSlot(date,username,(username.substring(0,3)));
								System.out.print("Available Rooms : "+repo);
								break;
						case 3:	
								//Invoking the method to cancel the reservation	
								System.out.println("\n\t\tRoom Cancelling Service ");
								System.out.print("\n\n\t Enter the booking ID : ");
								String booking_id=input.readLine();
								String respcan = addobj.cancelBooking(booking_id,username,username.substring(0,3));
								System.out.println("Cancel status  : "+respcan);	  
								break;
							
						case 4:
								//Invoking the method to change the reservation
								String oldroomno="",olddate="",oldtimeslot="",newroomno="",newdate="",newtimeslot="",bookingid="";
								int newtimeslotchoice;
								int oldtimeslotchoice;
								System.out.println("\n\t\tChanging Reservation ");
								
								System.out.print("\n\n\t Enter the booking ID : ");
								bookingid=input.readLine();
						
							
								System.out.print("\n\t\tEnter the new room name : ");
								newroomno= input.readLine();
								System.out.print("\n\t\tEnter the new date : "); 
								newdate = input.readLine();
								System.out.print("\n\t\tEnter the new time slot : ");
							
								System.out.print("\n\tAvailable Slots :");
								System.out.println ("\n\n\t 1. 1-3 \n\t 2. 3-5 \n\t 3. 5-7 \n\t 4. 7-9 \n\t 5. 9-11 \n\t 6. 11-13 \n\t 7. 13-15 \n\t 8.15-17 \n\t 9.17-19 \n\t 10. 19-21 \n\t 11. 21-23 \n\t 12. 23-1"); 
								System.out.print("\n Enter your choice : ");
								newtimeslotchoice=Integer.parseInt(input.readLine());
								switch(newtimeslotchoice)
								{
										case 1: newtimeslot="1-3";break;
										case 2: newtimeslot="3-5";break;
										case 3: newtimeslot="5-7";break;
										case 4: newtimeslot="7-9";break;
										case 5: newtimeslot="9-11";break;
										case 6: newtimeslot="11-13";break;
										case 7: newtimeslot="13-15";break;
										case 8: newtimeslot="15-17";break;
										case 9: newtimeslot="17-19";break;
										case 10: newtimeslot="19-21";break;
										case 11: newtimeslot="21-23";break;
										case 12: newtimeslot="23-1";break;
								}
								String respchreser = addobj.changeReservation(bookingid,username,username.substring(0,3),newroomno,newdate,newtimeslot);
								System.out.println("Changing reservation : "+respchreser);
								break;
					case 5:
							System.out.println("\tClosing..."); 
							System.exit(0);
							break;
					
				}
			}catch(Exception e){}
		}
	}catch(Exception e){ e.printStackTrace();}	
}
}

	