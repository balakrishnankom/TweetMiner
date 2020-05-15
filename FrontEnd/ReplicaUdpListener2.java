
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class ReplicaUdpListener2 extends Thread {

	private static final int MAX_DATAGRAM_SIZE = 4096;

	
	public static HashMap<String>res2 = new HashMap<>();

	protected Logger logger;
	private volatile QueuePool opThreadPool = null;
	public ReplicaUdpListener2(Logger logger, QueuePool opThreadPool) {
		
		super();
		this.logger = logger;
		this.opThreadPool = opThreadPool;
	}

	 
	public void run() {

		
		DatagramSocket serverSocket = null;
		InetSocketAddress localAddr = new InetSocketAddress("192.168.43.30", 8002);

		try {

			serverSocket = new DatagramSocket(localAddr);
			byte[] receiveData = new byte[MAX_DATAGRAM_SIZE];

			while (true) {

				logger.info("FrontEnd: Waiting for replica messages on 172.30.63.233 :8001 ");
				
				receiveData = new byte[MAX_DATAGRAM_SIZE];
				final DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

				// Wait for the packet
				serverSocket.receive(receivePacket);
				
				logger.info("FrontEnd: Received a UDP message from first replica");
				
				// Received a request. Place it in the receiving data variable and parse it
				byte[] data = new byte[receivePacket.getLength()];
				String result = new String(receivePacket.getData());
				result=result.trim();
				System.out.println(result);
				res2.put(result);
				
			}

		} catch (final SocketException e) {
			System.exit(1);
		} catch (final IOException e) {
			System.exit(1);
		} finally {if(serverSocket != null) serverSocket.close();}
	}

}
