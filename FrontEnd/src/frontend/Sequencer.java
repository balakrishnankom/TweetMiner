import java.util.*;
import java.io.*;
import sun.net.*;
import java.net.*;
public class Sequencer
{
	long seq=0;

	HashMap<Integer,String> holdbackQueuewst=new HashMap<Integer,String>();
	HashMap<Integer,String> BufferQueuewst=new HashMap<Integer,String>();
	
	HashMap<Integer,String> holdbackQueuekkl=new HashMap<Integer,String>();
	HashMap<Integer,String> BufferQueuekkl=new HashMap<Integer,String>();
	
	HashMap<Integer,String> holdbackQueuedvl=new HashMap<Integer,String>();
	HashMap<Integer,String> BufferQueuedvl=new HashMap<Integer,String>();
	
	private static long sequencerNumberwst = 0;
	private static long sequencerNumberkkl = 0;
	private static long sequencerNumberdvl = 0;
	
	
	protected void processRequest(String udpMessage)
	{
		String request="";
		StringTokenizer s=new StringTokenizer(udpMessage);
		
		//String createmsg="CREATE#"+no+"#"+date+"#"+timeslot+"#"+username;
		String createmsg=udpMessage;
			String[] split = createmsg.split("#");
 	
			System.out.println(udpMessage);

			System.out.println(split[0]);

		String camp="";
		/*
		System.out.println(camp=split[5].substring(0,3));
		System.out.println(camp=split[5].substring(0,3));
		System.out.println(camp=split[5].substring(0,3));
		System.out.println(camp=split[3].substring(0,3));
		System.out.println(camp=split[3].substring(0,3));
		*/System.out.println(camp=split[3].substring(0,3));
		
		switch(split[1])
		{
			case "5":	camp=split[5].substring(0,3); break;
			case "6":	camp=split[5].substring(0,3); break;	
			case "1":	camp=split[5].substring(0,3); break;
			case "3":	camp=split[3].substring(0,3); break;
			case "4":	camp=split[3].substring(0,3); break;
			case "2":	camp=split[3].substring(0,3); break;
		
		}
		
		System.out.println(camp);
		
		
		if(camp.equals("WST"))
		{
			seq=incrementCountwst();
			request=seq+"#"+udpMessage;
			System.out.println(request);
			holdbackQueuewst.put((int)seq,request);
			
			System.out.println("Message in list : ");
				
			try
			{
				if(!holdbackQueuewst.isEmpty())
				{	
					Iterator it1 = holdbackQueuewst.entrySet().iterator();
					while (it1.hasNext()) 
					{
						Map.Entry pair = (Map.Entry)it1.next();
						System.out.println("UDP Message available in hashmap : "+pair.getKey());
						System.out.println("UDP sequencerNumber :"+pair.getValue());
					}
				}
			}catch(Exception e)
			{
			}
		}
		else if(camp.equals("DVL"))
	          {
        
			seq=incrementCountdvl();
			request=seq+"#"+udpMessage;
			System.out.println(request);
			holdbackQueuedvl.put((int)seq,request);
			
			System.out.println("Message in list : ");
				
			try
			{
				if(!holdbackQueuedvl.isEmpty())
				{	
					Iterator it1 = holdbackQueuedvl.entrySet().iterator();
					while (it1.hasNext()) 
					{
						Map.Entry pair = (Map.Entry)it1.next();
						System.out.println("UDP Message available in hashmap : "+pair.getKey());
						System.out.println("UDP sequencerNumber :"+pair.getValue());
					}
				}
			}catch(Exception e)
			{
			}
		}
		else if(camp.equals("KKL"))
		{
			
				System.out.println("sadfsadf");
			seq=incrementCountkkl();
			request=seq+"#"+udpMessage;
			System.out.println(request);
			holdbackQueuekkl.put((int)seq,request);
			System.out.println("Message in list : ");
			try
			{
				if(!holdbackQueuekkl.isEmpty())
				{	
					Iterator it1 = holdbackQueuekkl.entrySet().iterator();
					while (it1.hasNext()) 
					{
						Map.Entry pair = (Map.Entry)it1.next();
						System.out.println("UDP Message available in hashmap : "+pair.getKey());
						System.out.println("UDP sequencerNumber :"+pair.getValue());
					}
				}
			}catch(Exception e)
			{
			}
		}
		if(camp.equals("WST"))
		{
			try (DatagramSocket serverSocket = new DatagramSocket()) 
			{
			String INET_ADDR = "225.4.5.8";
			int PORT = 9002;
                 
		
			// Get the address that we are going to connect to.
			InetAddress addr = InetAddress.getByName(INET_ADDR);
			// Open a new DatagramSocket, which will be used to send the data.
		
		
			String msg = request;
			// Create a packet that will contain the data
		
			// (in the form of bytes) and send it.
			DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
			msg.getBytes().length, addr, PORT);
			serverSocket.send(msgPacket);
			System.out.println("Server sent packet with msg: " + msg);
			
		
			} catch (IOException ex) {
			ex.printStackTrace();
			}
		}
		else if(camp.equals("KKL"))
		{
			try (DatagramSocket serverSocket = new DatagramSocket()) 
			{
				
				
				String INET_ADDR = "225.4.5.7";
				int PORT = 9001;
				// Get the address that we are going to connect to.
				InetAddress addr = InetAddress.getByName(INET_ADDR);
				// Open a new DatagramSocket, which will be used to send the data.
				String msg = request;
				// Create a packet that will contain the data
		
				// (in the form of bytes) and send it.
				DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),msg.getBytes().length, addr, PORT);
				serverSocket.send(msgPacket);
				System.out.println("Server sent packet with msg: " + msg);
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
		else if(camp.equals("DVL"))
		{
			try (DatagramSocket serverSocket = new DatagramSocket()) 
			{
				String INET_ADDR = "225.4.5.6";
				int PORT = 9000;
			
				// Get the address that we are going to connect to.
				InetAddress addr = InetAddress.getByName(INET_ADDR);
				// Open a new DatagramSocket, which will be used to send the data.
			
				String msg = request;
				// Create a packet that will contain the data
			
				// (in the form of bytes) and send it.
				DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
				msg.getBytes().length, addr, PORT);
				serverSocket.send(msgPacket);
				System.out.println("\n\nServer sent packet with msg: " + msg);
			
			} catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
	}
	
    public static synchronized long incrementCountwst() {
    	sequencerNumberwst++;
    	return sequencerNumberwst;
    }
	
    public static synchronized long incrementCountkkl() {
    	sequencerNumberkkl++;
    	return sequencerNumberkkl;
    }
	
    public static synchronized long incrementCountdvl() {
    	sequencerNumberdvl++;
    	return sequencerNumberdvl;
    }
}