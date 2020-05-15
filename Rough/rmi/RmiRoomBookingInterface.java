import java.rmi.*;
import java.rmi.server.*;
public interface RmiRoomBookingInterface extends Remote
{

	public String createRoom (int room_Number,String date,String[] list_Of_Time_Slots) throws RemoteException;
	public String deleteRoom (String room_Number,String date,String[] List_Of_Time_Slots) throws RemoteException;
	public String bookRoom (String campusName,String roomNumber,String date,String timeslot) throws RemoteException;
	public String getAvailableTimeSlot (String date) throws RemoteException;
	public String cancelBooking (String bookingID)throws RemoteException;
}
