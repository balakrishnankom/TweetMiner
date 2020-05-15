
import java.rmi.*;
import java.rmi.server.*;
import java.io.*;

class AdminClient
{
	public static String[][] login={
					{"DVLA1111","DVLA1111"},{"DVLA1112","DVLA1112"},{"DVLA1113","DVLA1113"},
					{"KKLA1111","KKLA1111"},{"KKLA1112","KKLA1112"},{"KKLA1113","KKLA1113"},
					{"WSTA1111","WSTA1111"},{"WSTA1112","WSTA1112"},{"WSTA1113","WSTA1113"}};
	public String roomno;
	public String room_slot;
	public String username,password;
	public String camp;
	public String name;
	RoomBookingInterface rbi;

	public boolean login(String username, String password)
	{
		
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
							case "WST":name = "rmi://localhost:9999/WstBookingSystem";break;
							case "DVL":name = "rmi://localhost:9999/DvlBookingSystem";break;
							case "KKL":name = "rmi://localhost:9999/KklBookingSystem";break;
						}
						System.out.print(login[i][1]);
						System.out.print("\t");
					}
					else
					{
						System.out.println(" Invalid Credentials ! Try again.");
						System.exit(0);
					}
				}
			}
	}

	
	public static void main(String args[])throws IOException
	{
		DataInputStream ip=new DataInputStream(System.in);
	System.out.print("Username : ");
	username=ip.readLine();
	System.out.print("Password : ");
	password=ip.readLine();
	
	
	System.out.println(name);
	try{
		System.setSecurityManager ( new RMISecurityManager ( ));  //set up the security manager
		rbi =(RoomBookingInterface) Naming.lookup (name);
		System.out.println("Connected to Server...!");
		
		while(true)
		{

		 //A small command line interface for the user to use the system.
        	System.out.println(" ");
       		System.out.println("*********************Room Booking Service********************");
        	System.out.println("");
        	System.out.println("                   Please select a service");
        	System.out.println("");
       		System.out.println("1. Create Rooms");
        	System.out.println("2. Delete Rooms");
        	System.out.println("3. Exit ");
        	System.out.println("");

        	//A buffered reader to allow input from the command line from the user.
        	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        	System.out.print("choose any option (1-3) :");
            System.out.print("");
        	System.out.flush();
        	String response = input.readLine();
	        int i = Integer.parseInt(response);
		
		try{
			 switch (i)
            		{
        		    case 3: System.out.println("Closing");   //User has quit the application.
                     		      System.exit(0);
                     		      break;
					case 1: 
					
							System.out.println("Creating room");
							System.out.println("Enter the room details : ");
							System.out.print("Enter the room no : ");
							roomno=input.readLine();
							System.out.print("Enter the date : ");
							String date=input.readLine();
							System.out.print("Enter the slot time :");
							room_slot=input.readLine();
							String createlog=rbi.createroom(roomno,date,room_slot,username);
							String defaultLogFile="E:\\Admin_Client_"+username+"_log.txt";
			
							try (FileWriter f = new FileWriter(defaultLogFile, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);)
							{ 
								p.println(createlog);  
							} 
							catch (IOException ioe) { ioe.printStackTrace(); }

							System.out.println(createlog);	
							break;					
					case 2:	
							System.out.println("Deleting room");
							System.out.println("Enter the room details : ");
							
							System.out.print(" Enter the room no (RRXXXX) : ");
							roomno=input.readLine();
							System.out.print(" Enter the date (DD-MM-YYYY) : ");
							date=input.readLine();							
							System.out.print("Enter the slot time (8AM-10PM) :");
							room_slot=input.readLine();
							String deletelog=rbi.deleteroom(roomno,date,room_slot,username);
							defaultLogFile="E:\\Admin_Client_"+username+"_log.txt";
			
							try (FileWriter f = new FileWriter(defaultLogFile, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);)
							{ 
								p.println(deletelog);  
							} 
							catch (IOException ioe) { ioe.printStackTrace(); }

							System.out.println(deletelog);	
							break;
							
					
			}
		}catch(Exception e){}
		}
	}catch(Exception e){ e.printStackTrace();}	

}
}

	