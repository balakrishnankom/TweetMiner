import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.concurrent.Callable;

/**
 * 
 * 
 * @author mat
 *
 */
public class UdpSend implements Callable<Boolean> {

	private byte[] payload = new byte[4096];
	private InetSocketAddress remoteAddr;

	/**
	 * 
	 * @param payload
	 * @param remoteAddr
	 */
	public UdpSend(byte[] payload, InetSocketAddress remoteAddr) {
		
		super();
		this.payload = payload;
		this.remoteAddr = remoteAddr;
	}

	@Override
	public Boolean call() throws IOException, SocketException {
		
		DatagramSocket clientSocket = null;
		DatagramPacket outgoingPacket = null;
		InetSocketAddress remoteAddr = new InetSocketAddress(this.remoteAddr.getAddress(), this.remoteAddr.getPort());

		try {
			clientSocket = new DatagramSocket();
			outgoingPacket = new DatagramPacket(payload, payload.length, remoteAddr);
			clientSocket.send(outgoingPacket);
		} finally {
			if (clientSocket != null) {
				clientSocket.close();
			}
		}

		return true;
	}
}
