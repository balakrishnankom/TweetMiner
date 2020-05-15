import java.util.StringTokenizer;  

import RoomBooking.*;
import java.net.*; 
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;
 
public class DvlBookingServer 
{
	
 
	public static void main(String args[]) 
	{

	DvlBookingServerImpl addobj = new DvlBookingServerImpl();
						


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
					
						NameComponent path[] = ncRef.to_name( "DVL" );
						ncRef.rebind(path, href);
					
						System.out.println("Addition Server ready and waiting ...");
					
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
	
 	Thread t1=new Thread( new Runnable()
	{ 
		public void run()
		{
			try
			{
						
				DatagramSocket sock = new DatagramSocket(9999);
				//buffer to receive incoming data
				byte[] buffer = new byte[65536];
				DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
				//2. Wait for an incoming data
				System.out.println("Server socket created. Waiting for incoming data...");
				//communication loop
				while(true)
				{
					sock.receive(incoming);
					byte[] data = incoming.getData();
					String s = new String(data, 0, incoming.getLength());
					String temptimeslot=s.substring(0,20);
					System.out.println("received string : "+s);
					
					
					
					//for timeslots
					String date_get="";
					date_get=s.substring(20);
					
					//getting available time slots
					System.out.println("Temp time slot : "+temptimeslot);
					if(temptimeslot.equals("GETAVAILABLEROOMSDVL"))
					{	
						StringTokenizer get=new StringTokenizer(s);
						String tmpcmdtimeslot=get.nextToken("#");
						String tmpcmddate=get.nextToken("#");
						String tmpcmdusername=get.nextToken("#");
						
						String no="";
						no=addobj.getAvailableTimeSlot(tmpcmddate,tmpcmdusername,"DVLSERVER");
						String tempcount=""+no+" ";
						System.out.println(" Result after getting available time slot : "+s);
						DatagramPacket dpget = new DatagramPacket(tempcount.getBytes() , tempcount.getBytes().length , incoming.getAddress() , incoming.getPort());
						sock.send(dpget);
					}
					
					
					
					String tempbook=s.substring(0,11);
					System.out.println("tempbook value before sending : "+tempbook);
					String tempcancel=s.substring(0,13);
					System.out.println("tempcancel value before sending :"+tempcancel);
					//booking room
					if(tempbook.equals("BOOKROOMDVL"))
					{
						
						System.out.println("book room dvl invoked in server");
						
						//Booking room server code
						StringTokenizer st = new StringTokenizer(s);  
						String tmpcmd="",tmproomno="",tmpdate="",tmptimeslot="",tmpusername="";
						tmpcmd=st.nextToken("#");
						tmproomno=st.nextToken("#");
						tmpdate=st.nextToken("#");
						tmptimeslot=st.nextToken("#");
						tmpusername=st.nextToken("#");
									
						System.out.println("after string tokenizer");
						
						
							
						
						
						String result="";
						result=addobj.bookRoom(tmproomno,tmpdate,tmptimeslot,tmpusername,"DVLSERVER");
						
						System.out.println("result after booking is  : "+result);
						DatagramPacket dpbook = new DatagramPacket(result.getBytes() , result.getBytes().length , incoming.getAddress() , incoming.getPort());
						sock.send(dpbook);

					}
					
					//cancelling room
					if(tempcancel.equals("CANCELROOMDVL"))
					{
						
							
							//cancelling room server code - yet to edit
						StringTokenizer can = new StringTokenizer(s);  
						String tmpcancmd="",tmpcanroomno="",tmpcandate="",tmpcantimeslot="",tmpcanusername="",tmpcanbookingid="";
						tmpcancmd=can.nextToken("#");
						tmpcanbookingid=can.nextToken("#");
						tmpcanusername=can.nextToken("#");
						tmpcanroomno=can.nextToken("#");
						tmpcandate=can.nextToken("#");
						tmpcantimeslot=can.nextToken("#");
									

					
						
							String cancelresult="";

							cancelresult=addobj.cancelBooking(tmpcanbookingid,tmpcanusername,tmpcanroomno,tmpcandate,tmpcantimeslot,"DVLSERVER");			
							DatagramPacket dpcancel = new DatagramPacket(cancelresult.getBytes() , cancelresult.getBytes().length , incoming.getAddress() , incoming.getPort());
							sock.send(dpcancel);
						
					}
					
					
						String tempgetcount=s.substring(0,11);
						System.out.println("Tempget count : "+tempgetcount);
						if(tempgetcount.equals("GETLIMITDVL"))
						{
							StringTokenizer tmpcount=new StringTokenizer(s);
							String tmpcmdcount=tmpcount.nextToken("#");
							String tmpdtcount=tmpcount.nextToken("#");
							String tmpusernamecount=tmpcount.nextToken("#");
							
							System.out.println(" Count date  "+tmpdtcount+" username : "+tmpusernamecount);
							String countlimit="";
							countlimit=""+addobj.countLimit(tmpdtcount,tmpusernamecount);
							
							System.out.println("Count limit in dvl : "+countlimit);
							DatagramPacket dpcountlimit = new DatagramPacket(countlimit.getBytes() , countlimit.getBytes().length , incoming.getAddress() , incoming.getPort());
							sock.send(dpcountlimit);
						}
			
					
				}
			}catch(Exception e){}
		}
			
	});
	t1.start();
	}
  }
