import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class GetAvailableTimeSlotMessage implements Serializable, IOperationMessage, IOperationResult<Boolean>
{

	String date,username;
	private Exception exception;
	private String machineName;
	private boolean isGetSuccessful = false;

	public GetAvailableTimeSlotMessage(String date,String username)
	{
		this.date = date;
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

		return isGetSuccessful == altMessage.getResult();
	}
	
	public OperationType getOperationType()
	{
		return OperationType.GetAvailableTimeSlot;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public boolean isGetSuccessful() {
		return isGetSuccessful;
	}
	public void setGetSuccessful(boolean isGetSuccessful) {
		this.isGetSuccessful = isGetSuccessful;
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
		return isGetSuccessful;
	}
}
