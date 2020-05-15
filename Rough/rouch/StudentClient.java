
import java.rmi.*;
import java.rmi.server.*;
import java.io.*;

public class StudentClient
{
	public static String[][] login={{"DVL1111","DVL1111"},{"DVL1112","DVL1112"},{"DVL1113","DVL1113"},
					{"KKL1111","KKL1111"},{"KKL1112","KKL1112"},{"KKL1113","KKL1113"},
					{"WST1111","WST1111"},{"WST1112","WST1112"},{"WST1113","WST1113"}};
	public static RoomBookingInterface rbi;
	
	public static void main(String args[])throws IOException
	{
	String username,password;
	String camp="";
	String name="";
	DataInputStream ip=new DataInputStream(System.in);
	System.out.print("Username : ");
	username=ip.readLine();
	System.out.print("Password : ");
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
            			case "WST":name = "rmi://localhost:9999/WstBookingSystem";break;
        			case "DVL":name = "rmi://localhost:9999/DvlBookingSystem";break;
            			case "KKL":name = "rmi://localhost:9999/KklBookingSystem";break;
				}
          			System.out.print(login[i][1]);
				System.out.print("\t");
			}
		}
	}


	try{
		System.setSecurityManager ( new RMISecurityManager ( ));  //set up the security manager
		rbi =(RoomBookingInterface) Naming.lookup (name);
		System.out.println("Connected to Server...!");


		 //A small command line interface for the user to use the system.
        	System.out.println(" ");
       		System.out.println("*********************Room Booking Service********************");
        	System.out.println("");
        	System.out.println("                   Please select a service");
        	System.out.println("");
       		System.out.println("1. Book Rooms");
        	System.out.println("2. Get availability of room");
        	System.out.println("3. Cancel booking");
		System.out.println("4. Exit ");
        	System.out.println("");

        	//A buffered reader to allow input from the command line from the user.
        	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        	System.out.println("choose any option (0-4) :");
                System.out.println("");
        	System.out.flush();
        	String response = input.readLine();
	        int i = Integer.parseInt(response);
		
		try{
			 switch (i)
            		{
        		      case 0: System.out.println("Closing");   //User has quit the application.
                     		      System.exit(0);
                     		      break;
					case 1:
					  
					  System.out.println("Room Booking Service - Rooms can be booked from 8am to 8pm");
					  System.out.println("");
					  System.out.println("Time slots go from 0 for 8am up to 11 for 7pm - Enter a value in this range");
                      System.out.println("");
                      System.out.println("Enter the room name");
                      String roomno= input.readLine();

					  
					  
					  
                      System.out.println("");
                      System.out.println("Enter the date -"); 
					  String date = input.readLine();
                     
                      System.out.println("");
                      System.out.println("Enter the time -"); 
					  System.out.println("0=8am , 1=9am , 2=10am , 3=11am , 4=12pm , 5=1pm , 6=2pm , 7=3pm , 8=4pm , 9=5pm , 10=6pm , 11= 7pm");
                      String timeslot = input.readLine();
                      
                      //This checks whether a room is available, if it is it then reserves the room.
                      String resp = rbi.bookRoom(roomno,date,timeslot,username);
					  System.out.println("Booking ID :");
                      System.out.println(resp);
					  System.out.println("");
					
					case 2:
							System.exit(0);
								break;
								
					case 3:	
						
					  
					  break;
			}	
		}catch(Exception e){}
	}catch(Exception e){ e.printStackTrace();}	

}
}

	