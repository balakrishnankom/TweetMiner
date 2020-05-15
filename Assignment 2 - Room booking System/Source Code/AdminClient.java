import RoomBooking.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.io.*;
import java.util.*;
 
public class AdminClient 
{
	
	//User credentials for admin - for three campuses
 	public static String[][] login={
					{"DVLA1111","DVLA1111"},{"DVLA1112","DVLA1112"},{"DVLA1113","DVLA1113"},
					{"KKLA1111","KKLA1111"},{"KKLA1112","KKLA1112"},{"KKLA1113","KKLA1113"},
					{"WSTA1111","WSTA1111"},{"WSTA1112","WSTA1112"},{"WSTA1113","WSTA1113"}};

    public static void main(String[] args) 
	{
		try 
		{
		  
			String roomno,room_slot="";
			int roomslotchoice;
			String username,password,camp,name="";

			//login validation
			System.out.println("\n\n\n\t\t\t-------------Login--------------");
			java.io.DataInputStream ip=new java.io.DataInputStream(System.in);
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
						System.out.print("\n\n\t\tSuccessful login..! Connecting to server...\n\n\n");
						switch (camp) 
						{
								case "WST":name = "WST";break;
								case "DVL":name = "DVL";break;
								case "KKL":name = "KKL";break;
						}
						System.out.print(login[i][1]);
						System.out.print("\t");
					}
					else
						System.exit(0);
				}
			}
			//Initialising ORB and getting object reference
			ORB orb = ORB.init(args, null);
			org.omg.CORBA.Object objRef =   orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
			RoomBookingInterface addobj = (RoomBookingInterface) RoomBookingInterfaceHelper.narrow(ncRef.resolve_str(name));
	
			//Displaying list of operations that admin can do
		    for(;;)
			{
				System.out.println("\n\n\n\tConnected to Server...!");
				System.out.println(" ");
				System.out.println("\t\t\t--------------Room Admin Service----------------");
				System.out.println("");
				System.out.println("\n\n\t\tPlease select an option ");
				System.out.println("");
				System.out.println("\t1. Create Rooms");
				System.out.println("\t2. Delete Rooms");
				System.out.println("\t3. Exit ");
				System.out.println("");
				BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("\n\n\t choose any option (1-3) :");
				System.out.print("");
				System.out.flush();
				String response = input.readLine();
				int i = Integer.parseInt(response);
				
				switch (i)
				{
				
					case 1: 
					
						//Creating room
						System.out.println("\n\n\tCreating room...");
						System.out.print("\n\n\tEnter the room no (RRXXXX): ");
						roomno=input.readLine();
						System.out.print("\n\tEnter the date (DD-MM-YYYY): ");
						String date=input.readLine();
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
						
						//Invoking the remote method to create room
						String createlog=addobj.createRoom(roomno,date,room_slot,username);
						String defaultLogFile="E:\\Admin_Client_"+username+"_log.txt";
					
						//Writing the response in log file
						try (FileWriter f = new FileWriter(defaultLogFile, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);)
						{ 
							p.println(createlog);  
						} 
						catch (IOException ioe) { ioe.printStackTrace(); }
						
						System.out.println("Status :  ");   
						System.out.println(createlog);	
						break;					
					case 2:	
					
						//Deleting room
						System.out.println("\n\n\t\t Deleting room");
						System.out.print("\n\n\t Enter the room no (RRXXXX) : ");
						roomno=input.readLine();
						System.out.print("\n\n\t Enter the date (DD-MM-YYYY) : ");
						date=input.readLine();						
						
						System.out.print("\n\t Slots :");
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
						
						//Invoking method to delete
						String deletelog=addobj.deleteRoom(roomno,date,room_slot,username);
						defaultLogFile="E:\\Admin_Client_"+username+"_log.txt";
						
						//Writing the server resonse in a log file
						try (FileWriter f = new FileWriter(defaultLogFile, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);)
						{ 
							p.println(deletelog);  
						} 
						catch (IOException ioe) 
						{
							System.out.println("Error in Connection : ");
							ioe.printStackTrace(); 
						}
						System.out.println(deletelog);	
						break;
					case 3: 
						//Exiting application
						System.out.println("Closing");   //User has quit the application.
						System.exit(0);
						break;
				}
            }
       }catch (Exception e) 
	   {
		   e.printStackTrace();
       }
	}
}
 
