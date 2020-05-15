
import java.io.Serializable;


public interface IOperationMessage extends Serializable 
{
	OperationType getOperationType();
	String getMachineName();
}
