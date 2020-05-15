import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class DeleteRoomMessage implements Serializable, IOperationMessage, IOperationResult<Boolean>
{

	String no,date,timeslot,username;
	private Exception exception;
	private String machineName;
	private boolean isDeleteSuccessful = false;

	public DeleteRoomMessage(String no,String date,String timeslot,String username)
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

		return isDeleteSuccessful == altMessage.getResult();
	}
	
	public OperationType getOperationType()
	{
		return OperationType.DeleteRoom;
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
	public boolean isDeleteSuccessful() {
		return isDeleteSuccessful;
	}
	public void setDeleteSuccessful(boolean isDeleteSuccessful) {
		this.isDeleteSuccessful = isDeleteSuccessful;
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
		return isDeleteSuccessful;
	}
}
