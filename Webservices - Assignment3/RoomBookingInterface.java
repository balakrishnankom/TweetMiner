package webserv;

import javax.jws.WebService;

import javax.jws.WebMethod;

import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style=Style.RPC)
public interface RoomBookingInterface
{
	@WebMethod String createRoom(String no,String date,String timeslot,String username);
	@WebMethod String deleteRoom(String no,String date,String timeslot,String username);
	@WebMethod String bookRoom(String no,String date,String timeslot,String username);
	@WebMethod String cancelBooking (String bookingID,String username);
	@WebMethod String getAvailableTimeSlot(String date,String username);
	@WebMethod String changeReservation(String bookingID,String username,String campus,String newroomno,String newdate,String newtimeslot); 

}

