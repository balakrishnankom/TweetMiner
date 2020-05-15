import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class CancelBookingMessage implements Serializable, IOperationMessage, IOperationResult<Boolean>
{

	String no,date,timeslot,username;
	private Exception exception;
	private String machineName;
	private boolean isCancelSuccessful = false;
	String booking_id;
	public CancelBookingMessage(String booking_id,String username)
	{
		this.booking_id = booking_id;
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

		return isCancelSuccessful == altMessage.getResult();
	}
	
	public OperationType getOperationType()
	{
		return OperationType.CancelRoom;
	}
	
	public String getBookingID() {
		return no;
	}
	public void setRoomNo(String booking_id) {
		this.booking_id = booking_id;
	}
	public boolean isCancelSuccessful() {
		return isCancelSuccessful;
	}
	public void setCancelSuccessful(boolean isCancelSuccessful) {
		this.isCancelSuccessful = isCancelSuccessful;
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
		return isCancelSuccessful;
	}
}
