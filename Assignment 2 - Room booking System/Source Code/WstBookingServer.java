import java.util.StringTokenizer;  
import RoomBooking.*;
import java.net.*; 
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;
 
public class WstBookingServer 
{
	public static void main(String args[]) 
	{
		//Creating an object to invoke server methods
		WstBookingServerImpl addobj = new WstBookingServerImpl();
		
		
		Thread t2=new Thread( new Runnable()
		{ 
			public void run()
			{
					try
					{
						
						System.out.println("Thread 1 has just started :");
						// create and initialize the ORB 
						// get reference to rootpoa and activate the POAManager
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
					
						NameComponent path[] = ncRef.to_name( "WST" );
						ncRef.rebind(path, href);
					
						// wait for invocations from clients
						for (;;)
						{
						orb.run();
						}
					} 
					catch (Exception e) 
					{
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
							
					DatagramSocket sock = new DatagramSocket(9997);
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
						
						//for timeslots
						String date_get="";
						date_get=s.substring(20);
						//getting available time slots
						if(temptimeslot.equals("GETAVAILABLEROOMSWST"))
						{	
					
							System.out.println("Get available time slot invoked");
							StringTokenizer get=new StringTokenizer(s);
							String tmpcmdtimeslot=get.nextToken("#");
							String tmpcmddate=get.nextToken("#");
							String tmpcmdusername=get.nextToken("#");
							System.out.println("Time slot : "+tmpcmdtimeslot);
							System.out.println("Date : "+tmpcmddate);
							
							System.out.println("User : "+tmpcmdusername);
							
							
							String no="";
							no=addobj.getAvailableTimeSlot(tmpcmddate,tmpcmdusername,"WSTSERVER");
							String tempcount=""+no+" ";
							System.out.println(" Result after getting available time slot : "+s);
							DatagramPacket dpget = new DatagramPacket(tempcount.getBytes() , tempcount.getBytes().length , incoming.getAddress() , incoming.getPort());
							sock.send(dpget);
						}
						String tempbook=s.substring(0,11);
						String tempcancel=s.substring(0,13);
						//booking room
						if(tempbook.equals("BOOKROOMWST"))
						{
							//Booking room server code
							StringTokenizer st = new StringTokenizer(s);  
							String tmpcmd="",tmproomno="",tmpdate="",tmptimeslot="",tmpusername="";
							tmpcmd=st.nextToken("#");
							tmproomno=st.nextToken("#");
							tmpdate=st.nextToken("#");
							tmptimeslot=st.nextToken("#");
							tmpusername=st.nextToken("#");
							String result="";
							result=addobj.bookRoom(tmproomno,tmpdate,tmptimeslot,tmpusername,"WSTSERVER");
							System.out.println("result of wst booking : "+result);
							DatagramPacket dpbook = new DatagramPacket(result.getBytes() , result.getBytes().length , incoming.getAddress() , incoming.getPort());
							sock.send(dpbook);
						}
						//cancelling room
						if(tempcancel.equals("CANCELROOMWST"))
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
							cancelresult=addobj.cancelBooking(tmpcanbookingid,tmpcanusername,tmpcanroomno,tmpcandate,tmpcantimeslot,"WSTSERVER");
							DatagramPacket dpcancel = new DatagramPacket(cancelresult.getBytes() , cancelresult.getBytes().length , incoming.getAddress() , incoming.getPort());
							sock.send(dpcancel);
						}
						String tempgetcount=s.substring(0,11);
						if(tempgetcount.equals("GETLIMITWST"))
						{
							StringTokenizer tmpcount=new StringTokenizer(s);
							String tmpcmdcount=tmpcount.nextToken("#");
							String tmpdtcount=tmpcount.nextToken("#");
							String tmpusernamecount=tmpcount.nextToken("#");
							String countlimit="";
							
							countlimit=""+addobj.countLimit(tmpdtcount,tmpusernamecount);
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
