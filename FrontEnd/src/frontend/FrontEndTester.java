import java.util.Properties;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import RoomBooking.RoomBookingInterfaceHelper;
import RoomBooking.*;
// Testfile for Front end
public class FrontEndTester 
{
	private ORB orb;
	protected FrontEnd server = null;
	public FrontEndTester() 
	{
		super();
		try{
	
			// create and initialize the ORB
			String[] k ={"-ORBInitialPort", "1050","-ORBInitialHost","192.168.43.30"};
			ORB orb = ORB.init(k, null);
			// get the root naming context
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContext ncRef = NamingContextHelper.narrow(objRef);
			// resolve the Object Reference in Naming
            NameComponent nc = new NameComponent("FrontEnd", "");
            NameComponent path[] = {nc};
            RoomBookingInterface helloRef = RoomBookingInterfaceHelper.narrow(ncRef.resolve(path));
			//System.out.println(helloRef.createRoom("RR1113","10-10-2010","1-3","KKLA1111"));
			
			System.out.println(helloRef.bookRoom("RR1113","10-10-2010","1-3","KKLA1111","KKL",1));
			//
			//String s=helloRef.cancelBooking("wsts11111","WSTA1111");
			String s=helloRef.getAvailableTimeSlot("10-10-2010","KKLA1111");
			//String s=helloRef.changeReservation("WSTS11223","username","username.substring(0,3)","newroomno","newdate","newtimeslot");
			//System.out.println("Result : "+s);
			
		}
		catch (Exception e) {
			System.out.println("Error : " + e);
			e.printStackTrace(System.out);
			System.exit(1);
		}
		
	}
	public static void main(String args[])
	{
		new FrontEndTester();
	}
}