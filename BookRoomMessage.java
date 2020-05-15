import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class BookRoomMessage implements Serializable, IOperationMessage, IOperationResult<Boolean>
{

	String no,date,timeslot,username;
	private Exception exception;
	private String machineName;
	private boolean isBookSuccessful = false;

	public BookRoomMessage(String no,String date,String timeslot,String username)
	{
		this.no = no;
		this.date = date;
		this.timeslot = timeslot;
		this.username = username;
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

		return isBookSuccessful == altMessage.getResult();
	}
	
	public OperationType getOperationType()
	{
		return OperationType.CreateRoom;
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
	public boolean isBookSuccessful() {
		return isBookSuccessful;
	}
	public void setBookSuccessful(boolean isBookSuccessful) {
		this.isBookSuccessful = isBookSuccessful;
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
		return isBookSuccessful;
	}
}
