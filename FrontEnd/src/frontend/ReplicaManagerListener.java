
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.logging.Logger;

/**
 * Listens for messages from the replica managers
 * 
 * @author mat
 *
 */
public class ReplicaManagerListener extends Thread {
;
	protected Logger logger;
	private volatile FrontEndState feState = FrontEndState.	RUNNING;
	
	/**
	 * Constructor
	 * 
	 * @param logger
	 * @return 
	 */
	public ReplicaManagerListener(Logger logger) {
		
		super();
		this.logger = logger;
	}

	public void run() {

		DatagramSocket serverSocket = null;
		InetSocketAddress localAddr = new InetSocketAddress("192.168.43.30", 9978);

		logger.info("FrontEnd: Waiting for replica manager messages on 172.30.0.1 : 7777");
		System.out.println("hello");
		try {

			serverSocket = new DatagramSocket(localAddr);
			byte[] receiveData = new byte[4096];
			System.out.println("hello2");
			while (true) {

				//
				// LISTENER
				//
				
				receiveData = new byte[4096];
				final DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

				// Wait for the packet
				serverSocket.receive(receivePacket);
				
				// Received a request. Place it in the receiving data variable and parse it
				byte[] data = new byte[receivePacket.getLength()];
		        String cmd = null;
		        System.arraycopy(receivePacket.getData(), receivePacket.getOffset(), data, 0, receivePacket.getLength());
		        
				final InetAddress remoteAddress = receivePacket.getAddress();
				final int remotePort = receivePacket.getPort();
		        
				// Unmarshall the message
		        try {
					cmd = (String) Serializer.deserialize(data);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
					System.out.println("Command received : "+cmd);
		        if (cmd.equals("STOP")) {
		        	feState = FrontEndState.STALLED;
		        	logger.info("FrontEnd: Received command to stop the FE");
		        	this.sendAckResponse(remoteAddress, remotePort, "STOPPED");
		        	
		        }
		        else if (cmd.equals("START")) {
		        	feState = FrontEndState.RUNNING;
		        	logger.info("FrontEnd: Received command to start the FE");
		        	this.sendAckResponse(remoteAddress, remotePort, "STARTED");
		        	
		        }
		        else {
		        	logger.info("FrontEnd: Received unknown command in RM Listener. Discarding it.");
		        }
			}

		} catch (final SocketException e) {


			System.out.println("Error : "+e.getMessage());		// TODO: Catch this properly
			System.exit(1);
		} catch (final IOException e) {
			// TODO: Catch this properly
			System.exit(1);
		} finally {if(serverSocket != null) serverSocket.close();}
	}
	
	/**
	 * Sends a state change ACK to the RMs
	 * 
	 * @param remoteAddress
	 * @param remotePort
	 * @param cmd
	 */
	public void sendAckResponse(InetAddress remoteAddress, int remotePort, String cmd) {
		
		// Serialize the message
		byte[] msg = new byte[4096];
		try {
			msg = Serializer.serialize(cmd);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		InetSocketAddress remoteAddr = new InetSocketAddress(remoteAddress, remotePort);
		UdpSend sender = new UdpSend(msg, remoteAddr);
		try {
			sender.call();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//
	// Getters and setters
	//
	
	public synchronized FrontEndState getFeState() {
		return feState;
	}

	public synchronized void setFeState(FrontEndState feState) {
		this.feState = feState;
	}
}
	