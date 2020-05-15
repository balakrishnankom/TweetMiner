
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import RoomBooking.RoomBookingInterfaceHelper;
import RoomBooking.*;
public class FrontEndServer {

	public static final boolean FILE_LOGGING = false;
	public static final String ORBD_HOST = "localhost";
	public static final String ORBD_PORT = "1050";
	
	private ORB orb = null;
	private Logger logger = null;
	public volatile QueuePool opQueuePool = null;
	private volatile HashMap<String, HashMap<String, Integer>> faultyReplicas = null;
	private ReplicaManagerListener replicaManagerListener = null;
	
	/**
	 * Constructor
	 */
	public FrontEndServer() {
		
		super();
		
		// Create a pool of blocking queues to hold the UDP messages from the
		// replicas
		this.opQueuePool = new QueuePool();

		// Set up the logger
		logger = Logger.getLogger("FrontEnd");
		if (FILE_LOGGING) {
		    FileHandler fh;  
		    try {
		        fh = new FileHandler("FrontEnd-log.txt");  
		        logger.addHandler(fh);
		        SimpleFormatter formatter = new SimpleFormatter();  
		        fh.setFormatter(formatter);
		    } catch (SecurityException e) {  
		        e.printStackTrace();
		        System.exit(1);
		    } catch (IOException e) {  
		        e.printStackTrace(); 
		        System.exit(1);
		    }
		}
		logger.info("FrontEnd: Logger started");
		
		// Pre-fill in the faulty replicas list for each machine
		faultyReplicas = new HashMap<String, HashMap<String, Integer>>();
		faultyReplicas.put(Constant.MACHINE_NAME_RICHARD, new HashMap<String, Integer>());
		faultyReplicas.put(Constant.MACHINE_NAME_AYMERIC, new HashMap<String, Integer>());
		faultyReplicas.put(Constant.MACHINE_NAME_PASCAL, new HashMap<String, Integer>());
		faultyReplicas.put(Constant.MACHINE_NAME_MATHIEU, new HashMap<String, Integer>());

		// Listen for replica manager status control messages
		replicaManagerListener = new ReplicaManagerListener(logger);
		replicaManagerListener.start();
		
		System.out.println("orb is about to initiated:");
		
		// Start the servers
		this.initiOrb();
		
		System.out.println("Server call will be initiated:");
		this.startServers();
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String args[]) {

		Env.loadSettings();
		Env.setCurrentBank(Bank.None);
		
		System.out.println("FrontEnd server is about to start:");
		new FrontEndServer();
	}
	
	/**
	 * Initialize the ORB
	 */
	private void initiOrb() {
		
		try {

		
			FrontEnd frontEnd = new FrontEnd(logger, this.opQueuePool, faultyReplicas, replicaManagerListener);
		
			String[] k ={"-ORBInitialPort", "1050","-ORBInitialHost","localhost"};
			// create and initialize the ORB //// get reference to rootpoa &amp; activate the POAManager
			orb = ORB.init(k, null);      
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
		
		
			// get object reference from the servant
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(frontEnd);
			RoomBookingInterface href = RoomBookingInterfaceHelper.narrow(ref);
		
			org.omg.CORBA.Object objRef =  orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
		
		
			NameComponent path[] = ncRef.to_name("FrontEnd");
			ncRef.rebind(path, href);
		
			System.out.println("Server ready and waiting ...");
		}
		catch (Exception e) {
			System.err.println("Error: " + e);
			e.printStackTrace(System.out);
		}
	}

	/**
	 * Starts the ORB server and UDP listener for replica messages
	 */
	private void startServers() 
	{

		System.out.println("Server start function is being invoked");
		Thread.UncaughtExceptionHandler exHandler = new Thread.UncaughtExceptionHandler() 
		{
		    public void uncaughtException(Thread th, Throwable ex) 
			{
		        System.out.println("Uncaught exception: " + ex.getClass());
		    }
		};
		
		
				// Listen for replica operation response messages
				ReplicaUdpListener1 replicaListener1 = new ReplicaUdpListener1(logger, this.opQueuePool);
				ReplicaUdpListener2 replicaListener2 = new ReplicaUdpListener2(logger, this.opQueuePool);
				ReplicaUdpListener3 replicaListener3 = new ReplicaUdpListener3(logger, this.opQueuePool);
				
				
		Thread t1=new Thread( new Runnable()
		{	 
			public void run()
			{
				replicaListener1.setUncaughtExceptionHandler(exHandler);
				
				replicaListener1.start();
			}
		});
		t1.start();

		Thread t2=new Thread( new Runnable()
		{	 
			public void run()
			{
				// Listen for replica operation response messages
				replicaListener2.setUncaughtExceptionHandler(exHandler);
				replicaListener2.start();
		
			}
		});
		t2.start();

		
		Thread t3=new Thread( new Runnable()
		{	 
			public void run()
			{
				// Listen for replica operation response messages
				replicaListener3.setUncaughtExceptionHandler(exHandler);
				replicaListener3.start();
			}
		});
		t3.start();

				
		System.out.println("Replica listener has been started");
		
		System.out.println("orb is also running:");
		try{
		for (;;)
		{
			orb.run();
		}}catch(Exception e)
		{System.out.println("error : ");	}
	}
}
