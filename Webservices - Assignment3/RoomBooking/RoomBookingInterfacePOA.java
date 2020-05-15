package RoomBooking;


/**
* RoomBooking/RoomBookingInterfacePOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from RoomBooking.idl
* Saturday, December 9, 2017 2:00:24 AM EST
*/

public abstract class RoomBookingInterfacePOA extends org.omg.PortableServer.Servant
 implements RoomBooking.RoomBookingInterfaceOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("createRoom", new java.lang.Integer (0));
    _methods.put ("deleteRoom", new java.lang.Integer (1));
    _methods.put ("bookRoom", new java.lang.Integer (2));
    _methods.put ("cancelBooking", new java.lang.Integer (3));
    _methods.put ("getAvailableTimeSlot", new java.lang.Integer (4));
    _methods.put ("changeReservation", new java.lang.Integer (5));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // RoomBooking/RoomBookingInterface/createRoom
       {
         String no = in.read_string ();
         String date = in.read_string ();
         String timeslot = in.read_string ();
         String username = in.read_string ();
         boolean $result = false;
         $result = this.createRoom (no, date, timeslot, username);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 1:  // RoomBooking/RoomBookingInterface/deleteRoom
       {
         String no = in.read_string ();
         String date = in.read_string ();
         String timeslot = in.read_string ();
         String username = in.read_string ();
         boolean $result = false;
         $result = this.deleteRoom (no, date, timeslot, username);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 2:  // RoomBooking/RoomBookingInterface/bookRoom
       {
         String no = in.read_string ();
         String date = in.read_string ();
         String timeslot = in.read_string ();
         String username = in.read_string ();
         String campus = in.read_string ();
         int flag = in.read_long ();
         int $result = (int)0;
         $result = this.bookRoom (no, date, timeslot, username, campus, flag);
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       case 3:  // RoomBooking/RoomBookingInterface/cancelBooking
       {
         String bookingID = in.read_string ();
         String username = in.read_string ();
         boolean $result = false;
         $result = this.cancelBooking (bookingID, username);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 4:  // RoomBooking/RoomBookingInterface/getAvailableTimeSlot
       {
         String date = in.read_string ();
         String $result = null;
         $result = this.getAvailableTimeSlot (date);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 5:  // RoomBooking/RoomBookingInterface/changeReservation
       {
         String bookingID = in.read_string ();
         String username = in.read_string ();
         String campus = in.read_string ();
         String newroomno = in.read_string ();
         String newdate = in.read_string ();
         String newtimeslot = in.read_string ();
         int $result = (int)0;
         $result = this.changeReservation (bookingID, username, campus, newroomno, newdate, newtimeslot);
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:RoomBooking/RoomBookingInterface:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public RoomBookingInterface _this() 
  {
    return RoomBookingInterfaceHelper.narrow(
    super._this_object());
  }

  public RoomBookingInterface _this(org.omg.CORBA.ORB orb) 
  {
    return RoomBookingInterfaceHelper.narrow(
    super._this_object(orb));
  }


} // class RoomBookingInterfacePOA