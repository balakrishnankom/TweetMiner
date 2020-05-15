import java.rmi.*;
import java.rmi.server.*;
public interface RoomBookingInterface extends Remote
{
  public String createRoom(String no,String date,String timeslot,String username)throws RemoteException;
public String deleteRoom(String no,String date,String timeslot,String username)throws RemoteException;
  public String bookRoom(String no,String date,String timeslot,String username)throws RemoteException;
 public String cancelBooking (String bookingID,String username,String no,String date,String timeslot)throws RemoteException;
}

