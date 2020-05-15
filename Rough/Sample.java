import java.io.*;
import java.util.*;
public class Sample
{
	public static void main(String args[])throws IOException
	{

		String s="Roombooking Successful    Room No : RR1115        Booking Date : 10-10-2010   Timeslot : 1-3  Booked by : WSTS1111    Booking ID : WSTS11111510075619";
		//System.out.println(s.substring(0,10));
		StringTokenizer st=new StringTokenizer(s);
		String temp1,temp2,temp3,temp4,temp5,temp6,temp7,temp8,temp9,temp10,temp11,temp12;
		temp1=st.nextToken(":");
		temp2=st.nextToken(":");
temp3=st.nextToken(":");
temp4=st.nextToken(":");
temp5=st.nextToken(":");
temp6=st.nextToken(":");

System.out.println(" Booking id : "+temp6);	
System.out.println(" Booking id after substring : "+temp6.substring(0));


	}
}