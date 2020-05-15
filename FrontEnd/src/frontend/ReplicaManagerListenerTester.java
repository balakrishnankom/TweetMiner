
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.logging.Logger;
public class ReplicaManagerListenerTester 
{

	public static void main(String[] args) 
	{
		
		
		byte[] msg = new byte[4096];
		try 
		{
			msg = Serializer.serialize("START");
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		InetSocketAddress remoteAddr = new InetSocketAddress("192.168.43.30",9978);
		UdpSend sender = new UdpSend(msg, remoteAddr);
		try {
			sender.call();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
