 
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

/**
 * This is the UDP listener class/thread which receives the operation results
 * from the replicas and makes it available to the thread corresponding to the
 * original request from the client
 * 
 * @author mat
 * 
 */
public class ReplicaUdpListener extends Thread {

	InetAddress address1,address2,address3;
	int portno1,portno2,portno3;
	

	public ReplicaUdpListener(InetAddress add1,int port1,InetAddress add2,int port2,InetAddress add3,int port3) 
	{
		super();
		this.address1=add1;
		this.address2=add2;
		this.address3=add3;
		this.portno1=port1;
		this.portno2=port2;
		this.portno3=port3;
	}
	
	
	@Override
	public void run() 
	{
		System.out.println("Address 1 : "+this.address1);
		System.out.println("Address 2 : "+this.address2);
		System.out.println("Address 3 : "+this.address3);
		System.out.println("Port no 1 : "+this.portno1);
		System.out.println("Port no 2 : "+this.portno2);
		System.out.println("Port no 3 : "+this.portno3);
		
	}
	
/*	

	@Override
	public void run() 
	{
		
		DatagramSocket serverSocket = null;
		InetSocketAddress localAddr = new InetSocketAddress(feInfo.getIpAddress(), feInfo.getPort());

		try {

			serverSocket = new DatagramSocket(localAddr);
			byte[] receiveData = new byte[1050];
			
			
			while (true) {

				//
				// LISTENER
				//
				
				
				receiveData = new byte[MAX_DATAGRAM_SIZE];
				final DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length,);

				// Wait for the packet
				serverSocket.receive(receivePacket);
				
				logger.info("FrontEnd: Received a UDP message from a replica");
				
				// Received a request. Place it in the receiving data variable and parse it
				byte[] data = new byte[receivePacket.getLength()];
		        System.arraycopy(receivePacket.getData(), receivePacket.getOffset(), data, 0, receivePacket.getLength());

		        UDPMessage message = null;
				try {
					message = (UDPMessage) Serializer.deserialize(data);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		        // Get the sequence number for this message and add it to the appropriate queue
		        long sequenceNbr = message.getSequenceNumber();
		        BlockingQueue<UDPMessage> queue = opThreadPool.get(sequenceNbr);
		        // If the queue doesn't exist, then this packet is probably a duplicate and late. Just discard it.
				if (queue == null) {
					logger.info("FrontEnd: Received a UDP message from a replica with sequence number " + sequenceNbr
							+ " but no queue exists");
					continue;
				}

				// Add the message to the queue and move on
				try {
					queue.put(message);
				} catch (InterruptedException e) {
					// TODO: Catch this properly
					e.printStackTrace();
				}
			}

		} catch (final SocketException e) {
			// TODO: Catch this properly
			System.exit(1);
		} catch (final IOException e) {
			// TODO: Catch this properly
			System.exit(1);
		} finally {if(serverSocket != null) serverSocket.close();}
	}
*/

}



