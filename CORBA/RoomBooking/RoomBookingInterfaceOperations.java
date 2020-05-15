package RoomBooking;


/**
* RoomBooking/RoomBookingInterfaceOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from RoomBooking.idl
* Wednesday, November 22, 2017 6:17:16 o'clock PM EST
*/

public interface RoomBookingInterfaceOperations 
{
  String createRoom (String no, String date, String timeslot, String username);
  String deleteRoom (String no, String date, String timeslot, String username);
  String bookRoom (String no, String date, String timeslot, String username, String campus);
  String cancelBooking (String bookingID, String username, String campus);
  String getAvailableTimeSlot (String date, String username, String campus);
  String changeReservation (String bookingID, String username, String campus, String newroomno, String newdate, String newtimeslot);
} // interface RoomBookingInterfaceOperations
