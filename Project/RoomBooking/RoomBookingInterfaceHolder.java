package RoomBooking;

/**
* RoomBooking/RoomBookingInterfaceHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from RoomBooking.idl
* Thursday, November 23, 2017 10:59:11 o'clock PM EST
*/

public final class RoomBookingInterfaceHolder implements org.omg.CORBA.portable.Streamable
{
  public RoomBooking.RoomBookingInterface value = null;

  public RoomBookingInterfaceHolder ()
  {
  }

  public RoomBookingInterfaceHolder (RoomBooking.RoomBookingInterface initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = RoomBooking.RoomBookingInterfaceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    RoomBooking.RoomBookingInterfaceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return RoomBooking.RoomBookingInterfaceHelper.type ();
  }

}
