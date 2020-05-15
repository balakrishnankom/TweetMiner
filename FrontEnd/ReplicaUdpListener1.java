
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;


public class ReplicaUdpListener1 extends Thread {

	private static final int MAX_DATAGRAM_SIZE = 4096;

	protected Logger logger;
	private volatile QueuePool opThreadPool = null;

	public static HashMap<String>res1 = new HashMap<>();

	public ReplicaUdpListener1(Logger logger, QueuePool opThreadPool) {
		
		super();
		this.logger = logger;
		this.opThreadPool = opThreadPool;
	}
	
	 
	public void run() {

		
		DatagramSocket serverSocket = null;
		ServerInfo feInfo = Env.getFrontEndServerInfo();
		InetSocketAddress localAddr = new InetSocketAddress("192.168.43.30", 8001);

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
				res1.put(result);
				
			}

		} catch (final SocketException e) {
			// TODO: Catch this properly
			System.exit(1);
		} catch (final IOException e) {
			// TODO: Catch this properly
			System.exit(1);
		} finally {if(serverSocket != null) serverSocket.close();}
	}

}
