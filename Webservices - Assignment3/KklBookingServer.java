
import java.net.*; 
import java.util.Properties;
 import java.util.StringTokenizer;  

public class KklBookingServer 
{
	public static void main(String args[]) 
	{
	KklBookingServerImpl addobj = new KklBookingServerImpl();
 	Thread t1=new Thread( new Runnable()
	{ 
		public void run()
		{
			try
			{
				DatagramSocket sock = new DatagramSocket(9998);
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
					if(temptimeslot.equals("GETAVAILABLEROOMSKKL"))
					{	
						StringTokenizer get=new StringTokenizer(s);
						String tmpcmdtimeslot=get.nextToken("#");
						String tmpcmddate=get.nextToken("#");
						String tmpcmdusername=get.nextToken("#");
						String no="";
						no=addobj.getAvailableTimeSlot(tmpcmddate,tmpcmdusername,"KKLSERVER");					
						String tempcount=""+no+" ";
						DatagramPacket dpget = new DatagramPacket(tempcount.getBytes() , tempcount.getBytes().length , incoming.getAddress() , incoming.getPort());
						sock.send(dpget);
					}
					String tempbook=s.substring(0,11);
					String tempcancel=s.substring(0,13);
					//booking room
					if(tempbook.equals("BOOKROOMKKL"))
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
						result=addobj.bookRoom(tmproomno,tmpdate,tmptimeslot,tmpusername,"KKLSERVER");
						DatagramPacket dpbook = new DatagramPacket(result.getBytes() , result.getBytes().length , incoming.getAddress() , incoming.getPort());
						sock.send(dpbook);
					}
					//cancelling room
					if(tempcancel.equals("CANCELROOMKKL"))
					{
						//cancelling room server code - yet to edit
						StringTokenizer can = new StringTokenizer(s);  
						String tmpcancmd="",tmpcanroomno="",tmpcandate="",tmpcantimeslot="",tmpcanusername="",tmpcanbookingid="";
						tmpcancmd=can.nextToken("#");
						tmpcanbookingid=can.nextToken("#");
						tmpcanusername=can.nextToken("#");
						String cancelresult="";
						cancelresult=addobj.cancelBooking(tmpcanbookingid,tmpcanusername,"KKLSERVER");
						DatagramPacket dpcancel = new DatagramPacket(cancelresult.getBytes() , cancelresult.getBytes().length , incoming.getAddress() , incoming.getPort());
						sock.send(dpcancel);
					}
					String tempgetcount=s.substring(0,11);
					if(tempgetcount.equals("GETLIMITKKL"))
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
