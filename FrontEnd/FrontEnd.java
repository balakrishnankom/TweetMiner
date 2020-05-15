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
	
	public String createRoom(String no,String date,String timeslot,String username)
	{
		logger.info("FrontEnd: Client invoked Create Room(" + no + ", " + date + ", " + timeslot + ", " + username + ")");
		this.goAhead();
				
		BlockingQueue<Integer> resultQueue = new ArrayBlockingQueue<Integer>(1);
	
		String createmsg="CREATE#"+no+"#"+date+"#"+timeslot+"#"+username;
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
		return res;
	}

	
	public String getAvailableTimeSlot(String date,String username)
	{
		logger.info("FrontEnd: Client invoked Get available time slot (" + date + ", " + username + ")");
		this.goAhead();
		
		
		BlockingQueue<Integer> resultQueue = new ArrayBlockingQueue<Integer>(1);
		String getmsg="GET#"+date+"#"+username;
		seq.processRequest(getmsg);

		ResultSetListener<Integer> resultsListener = null;
		long opSequenceNbr = 0;
		Integer result = null;
		
		logger.info("FrontEnd: TotalOrderMulticastWithSequencerreplied to Get available time slot operation with sequence number " + opSequenceNbr);

		try {
			result = resultQueue.poll(5000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
		}

		// Timeout!
		if (result == null) {}
		String res="";
		return res;
	}	


	public String bookRoom(String no,String date,String timeslot,String username)
	{
		logger.info("FrontEnd: Client invoked Book Room(" + no + ", " + date + ", " + timeslot + ", " + username + ")");
		this.goAhead();
		
		
		BlockingQueue<Integer> resultQueue = new ArrayBlockingQueue<Integer>(1);
		String bookmsg="BOOK#"+no+"#"+date+"#"+timeslot+"#"+username;
		seq.processRequest(bookmsg);

			
		ResultSetListener<Integer> resultsListener = null;
		long opSequenceNbr = 0;
		Integer result = null;
		
		
		logger.info("FrontEnd: TotalOrderMulticastWithSequencerreplied to Book Room operation with sequence number " + opSequenceNbr);

		try {
			result = resultQueue.poll(5000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
		}

		// Timeout!
		if (result == null) {}
		String res=""+opSequenceNbr;
		return res;


	}

  
	public String cancelBooking (String booking_id,String username)
	{
		
		logger.info("FrontEnd: Client invoked Cancel booking (" + booking_id + ", " + username + ")");
		this.goAhead();
		
		
		BlockingQueue<Integer> resultQueue = new ArrayBlockingQueue<Integer>(1);
		String cancelmsg="CANCEL#"+booking_id+"#"+username;
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
		String res="";
		return res;
		
	}
  
	public String changeReservation(String booking_id,String username,String campus,String newroomno,String newdate,String newtimeslot)
	{
		
		logger.info("FrontEnd: Client invoked change Room(" + booking_id + ", " + username + ", " + campus + ", " + newroomno + ", "+newdate + ", " + newtimeslot + ")");
		this.goAhead();
		
		
		BlockingQueue<Integer> resultQueue = new ArrayBlockingQueue<Integer>(1);
		String changemsg="CHANGE#"+booking_id+"#"+username+"#"+campus+"#"+newroomno+"#"+newdate+"#"+newtimeslot;
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
		
		return res;
		
		
		
	}

	public String deleteRoom(String no,String date,String timeslot,String username)
	{
		logger.info("FrontEnd: Client invoked Delete Room(" + no + ", " + date + ", " + timeslot + ", " + username + ")");
		this.goAhead();
		
		
		BlockingQueue<Integer> resultQueue = new ArrayBlockingQueue<Integer>(1);
		String deletemsg="DELETE#"+no+"#"+date+"#"+timeslot+"#"+username;
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
	


	return res;


	}
	
	
	
}