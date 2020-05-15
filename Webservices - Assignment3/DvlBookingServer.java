import java.util.StringTokenizer;  
import java.net.*; 
import java.util.Properties;
 
public class DvlBookingServer 
{
	public static void main(String args[]) 
	{
		DvlBookingServerImpl addobj = new DvlBookingServerImpl();
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
						//for timeslots
						String date_get="";
						date_get=s.substring(20);
						if(temptimeslot.equals("GETAVAILABLEROOMSDVL"))
						{	
							StringTokenizer get=new StringTokenizer(s);
							String tmpcmdtimeslot=get.nextToken("#");
							String tmpcmddate=get.nextToken("#");
							String tmpcmdusername=get.nextToken("#");
							
							String no="";
							no=addobj.getAvailableTimeSlot(tmpcmddate,tmpcmdusername,"DVLSERVER");
							String tempcount=""+no+" ";
							DatagramPacket dpget = new DatagramPacket(tempcount.getBytes() , tempcount.getBytes().length , incoming.getAddress() , incoming.getPort());
							sock.send(dpget);
						}
						String tempbook=s.substring(0,11);
						String tempcancel=s.substring(0,13);
						//booking room
						if(tempbook.equals("BOOKROOMDVL"))
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
							result=addobj.bookRoom(tmproomno,tmpdate,tmptimeslot,tmpusername,"DVLSERVER");
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
							
							String cancelresult="";
						
							cancelresult=addobj.cancelBooking(tmpcanbookingid,tmpcanusername,"DVLSERVER");			
							DatagramPacket dpcancel = new DatagramPacket(cancelresult.getBytes() , cancelresult.getBytes().length , incoming.getAddress() , incoming.getPort());
							sock.send(dpcancel);
						}
						String tempgetcount=s.substring(0,11);
						if(tempgetcount.equals("GETLIMITDVL"))
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
			}});
			t1.start();
	}
  }
