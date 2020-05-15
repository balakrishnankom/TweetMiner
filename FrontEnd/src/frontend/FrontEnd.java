import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.io.Serializable;
import java.util.Date;

import RoomBooking.*;
/**  * CORBA front end */
public class FrontEnd extends RoomBooking.RoomBookingInterfacePOA 
{

	// These should be coming from a config
	public static final int MAX_DATAGRAM_SIZE = 4096;
	//private static long assumedSequenceNbr = 1;
	private Logger logger = null;
	private ServerInfo sqInfo = null;
	private volatile QueuePool opQueuePool = null;
	private volatile HashMap<String, HashMap<String, Integer>> faultyReplicas = null;
	private volatile ReplicaManagerListener replicaManagerListener = null;
	protected static String logfile ="";
	Sequencer seq=new Sequencer();
	/**
	 * Constructor
	 */
	public FrontEnd(Logger logger, QueuePool opQueuePool , HashMap<String, HashMap<String, Integer>> faultyReplicas, ReplicaManagerListener replicaManagerListener) 
	{
		
		super();
		this.logger = logger;
		this.opQueuePool = opQueuePool;
		this.faultyReplicas = faultyReplicas;
		this.replicaManagerListener = replicaManagerListener;
		this.sqInfo = Env.getSequencerServerInfo();
	}

	/**
	 * Polls the state of the FE to see if a operation can go ahead.
	 * If it times out, an AppException is thrown
	 * 
	 * @return
	 */
	public boolean goAhead() {

		long waitIteration = 500;
		for (long totalTimeToWait = 5000; totalTimeToWait > 0; totalTimeToWait -= waitIteration) {
			if (replicaManagerListener.getFeState() == FrontEndState.RUNNING) {
				return true;
			}
		}
		
		logger.info("FrontEnd: Client timed out while Front End was not in running state)");
		return true;
	}
	
	public boolean createRoom(String no,String date,String timeslot,String username)
	{
		logger.info("FrontEnd: Client invoked Create Room(" + no + ", " + date + ", " + timeslot + ", " + username + ")");
		this.goAhead();
				
		BlockingQueue<Integer> resultQueue = new ArrayBlockingQueue<Integer>(1);
	
		String createmsg="CL#6#"+no+"#"+date+"#"+timeslot+"#"+username;
		seq.processRequest(createmsg);
	
		ResultSetListener<Integer> resultsListener = null;
	

	
		long opSequenceNbr = 0;
		Integer result = null;
		
		logger.info("FrontEnd: TotalOrderMulticastWithSequencerreplied to Create Room operation with sequence number " + opSequenceNbr);

		try {
			result = resultQueue.poll(5000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
		}

		// Timeout!
		if (result == null) {}
		String res=""+opSequenceNbr;
		return true;
	}

	
	public String getAvailableTimeSlot(String date,String username)
	{
		logger.info("FrontEnd: Client invoked Get available time slot (" + date + ", " + username + ")");
		this.goAhead();
		
		
		BlockingQueue<Integer> resultQueue = new ArrayBlockingQueue<Integer>(1);
		String getmsg="CL#2#"+date+"#"+username;
		seq.processRequest(getmsg);

		ResultSetListener<Integer> resultsListener = null;
		long opSequenceNbr = 0;
		Integer result = null;
		
		logger.info("FrontEnd: TotalOrderMulticastWithSequencerreplied to Get available time slot operation with sequence number " + opSequenceNbr);

		try{ 
			result = resultQueue.poll(5000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
		}

		// Timeout!
		if (result == null) {}
		String res="";
		return res;
	}	


	public int bookRoom(String no,String date,String timeslot,String username,String campus,int flag)
	{
		logger.info("FrontEnd: Client invoked Book Room(" + no + ", " + date + ", " + timeslot + ", " + username + ")");
		this.goAhead();
		
		
		BlockingQueue<Integer> resultQueue = new ArrayBlockingQueue<Integer>(1);
		String bookmsg="CL#1#"+no+"#"+date+"#"+timeslot+"#"+username+"#"+campus+"#"+flag;
		seq.processRequest(bookmsg);

			
		ResultSetListener<Integer> resultsListener = null;
		long opSequenceNbr = 0;
		Integer result = null;

		
				
		DatagramSocket serverSocket = null;
		ServerInfo feInfo = Env.getFrontEndServerInfo();
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
				String result1 = new String(receivePacket.getData());
				result1=result1.trim();
				System.out.println(result1);
				
				
			}

		} catch (final SocketException e) {
			System.exit(1);
		} catch (final IOException e) {
			System.exit(1);
		} finally {if(serverSocket != null) serverSocket.close();}
	
		
		logger.info("FrontEnd: TotalOrderMulticastWithSequencerreplied to Book Room operation with sequence number " + opSequenceNbr);

		try {
			result = resultQueue.poll(5000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
		}

		// Timeout!
		if (result == null) {}
		return 1;


	}

  
	public boolean cancelBooking (String booking_id,String username)
	{
		
		logger.info("FrontEnd: Client invoked Cancel booking (" + booking_id + ", " + username + ")");
		this.goAhead();
		
		
		BlockingQueue<Integer> resultQueue = new ArrayBlockingQueue<Integer>(1);
		String cancelmsg="CL#3#"+booking_id+"#"+username;
		seq.processRequest(cancelmsg);

		ResultSetListener<Integer> resultsListener = null;
		long opSequenceNbr = 0;
		Integer result = null;
		
		logger.info("FrontEnd: TotalOrderMulticastWithSequencerreplied to cancel booking operation with sequence number " + opSequenceNbr);

		try {
			result = resultQueue.poll(5000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
		}

		// Timeout!
		if (result == null) {}
		return true;
		
	}
  
	public int changeReservation(String booking_id,String username,String campus,String newroomno,String newdate,String newtimeslot)
	{
		
		logger.info("FrontEnd: Client invoked change Room(" + booking_id + ", " + username + ", " + campus + ", " + newroomno + ", "+newdate + ", " + newtimeslot + ")");
		this.goAhead();
		
		
		BlockingQueue<Integer> resultQueue = new ArrayBlockingQueue<Integer>(1);
		String changemsg="CL#4#"+booking_id+"#"+username+"#"+campus+"#"+newroomno+"#"+newdate+"#"+newtimeslot;
		seq.processRequest(changemsg);

		ResultSetListener<Integer> resultsListener = null;
		long opSequenceNbr = 0;
		Integer result = null;
		
		logger.info("FrontEnd: TotalOrderMulticastWithSequencerreplied to Change Room operation with sequence number " + opSequenceNbr);

		try {
			result = resultQueue.poll(5000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
		}

		// Timeout!
		if (result == null) {}
			String 	res="";
		
		return 1;
		
		
		
	}

	public boolean deleteRoom(String no,String date,String timeslot,String username)
	{
		logger.info("FrontEnd: Client invoked Delete Room(" + no + ", " + date + ", " + timeslot + ", " + username + ")");
		this.goAhead();
		
		
		BlockingQueue<Integer> resultQueue = new ArrayBlockingQueue<Integer>(1);
		String deletemsg="CL#6#"+no+"#"+date+"#"+timeslot+"#"+username;
		seq.processRequest(deletemsg);
		
		ResultSetListener<Integer> resultsListener = null;
		long opSequenceNbr = 0;
		Integer result = null;
		
		logger.info("FrontEnd: TotalOrderMulticastWithSequencerreplied to Delete Room operation with sequence number " + opSequenceNbr);

		try {
			result = resultQueue.poll(5000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
		}

		// Timeout!
		if (result == null) {}
		String res=""+opSequenceNbr;
	return true;


	}
	
	
	
}