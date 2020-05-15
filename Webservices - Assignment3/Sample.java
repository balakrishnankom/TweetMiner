import java.io.*;
public class Sample
{
 public static void main(String args[]) 
	{
		//Creating an object to invoke server methods
		WstBookingServerImpl addobj = new WstBookingServerImpl();
		
		Thread t1=new Thread( new Runnable()
		{	 
			public void run()
			{
				System.out.println(addobj.createRoom("RR1111","10-10-2010","1-3","WSTA1111"));
				
			
			}
		});
		t1.start();
}
}