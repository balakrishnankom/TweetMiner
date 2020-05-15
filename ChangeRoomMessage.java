import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ChangeRoomMessage implements Serializable, IOperationMessage, IOperationResult<Boolean>
{

	String no,date,timeslot,username;
	private Exception exception;
	private String machineName;
	private boolean isChangeSuccessful = false;
	String booking_id,campus;
	public ChangeRoomMessage(String booking_id,String username,String campus,String newroomno,String newdate,String newtimeslot)
	{
	
		this.booking_id=booking_id;
		this.username=username;
		this.campus=campus;
		this.no=newroomno;
		this.date=newdate;
		this.timeslot=newtimeslot;
	}
	
	/**
	 * Used to compare this object's result with another, for use in the front
	 * end so that it doesn't have to worry about types
	 * 
	 * @param msg
	 * @return
	 */
	@Override
	public boolean isResultEqual(IOperationResult<Boolean> altMessage) {

		if (altMessage == null) {
			return false;
		}

		return isChangeSuccessful == altMessage.getResult();
	}
	
	public OperationType getOperationType()
	{
		return OperationType.ChangeRoom;
	}

	
	public String getBookingID() {
		return booking_id;
	}

	public void setBookingID(String booking_id) {
		this.booking_id=booking_id;
	}

	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username=username;
	}
		
	

	public String getCampus()
	{
		return campus;
	}
	public void setCampus(String campus)
	{
		this.campus=campus;
	}
	

	public String getRoomNo() {
		return no;
	}
	public void setRoomNo(String roomno) {
		this.no = roomno;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTimeSlot() {
		return timeslot;
	}
	public void setTimeSlot(String timeslot) {
		this.timeslot = timeslot;
	}
	public boolean isChangeSuccessful() {
		return isChangeSuccessful;
	}
	public void setChangeSuccessful(boolean isChangeSuccessful) {
		this.isChangeSuccessful = isChangeSuccessful;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	@Override
	public String getMachineName() {
		return machineName;
	}
	@Override
	public Boolean getResult() {
		return isChangeSuccessful;
	}
}
