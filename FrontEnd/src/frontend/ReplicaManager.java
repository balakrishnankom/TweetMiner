
import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import net.rudp.impl.*;
import net.rudp.*;

public class ReplicaManager {
	public static ReplicaManager savedInstanceOfReplicaManager;
	int sfdvl;
	int sfkkl;
	int sfwst;
	InetAddress host;
	InetAddress otherRM;
	String status;
	static HashMap<Integer, Integer> ports = new HashMap<>();

	public void restartReplica(int replicaId) {
		try {
			if (replicaId == 11) {
				DvlBookingServerImpl.savedInstanceofServerDorval.close();
				DvlBookingServerImpl s1 = new DvlBookingServerImpl(5000);
				DvlBookingServerImpl.savedInstanceofServerDorval = s1;
				sfdvl = 0;
			} else if (replicaId == 12) {
				ServerKirklandImpl.savedInstanceofServerKirkland.close();
				ServerKirklandImpl s2 = new ServerKirklandImpl(5001);
				ServerKirklandImpl.savedInstanceofServerKirkland = s2;
				sfkkl = 0;
			} else if (replicaId == 13) {
				ServerWestmountImpl.savedInstanceofServerWestmount.close();
				ServerWestmountImpl s3 = new ServerWestmountImpl(5002);
				ServerWestmountImpl.savedInstanceofServerWestmount = s3;
				sfwst = 0;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void transferHashMap(int replicaId) 
	{
		if (replicaId == 11) {
			sendMessage(5000, "TH");
		} else if (replicaId == 12) {
			sendMessage(5001, "TH");
		} else if (replicaId == 13) {
			sendMessage(5002, "TH");
		}
	}

	public void handleFailure(int replicaID) {
		if (replicaID == 11) {
			sfdvl++;
			if (sfdvl == 3) {
				ServerDorvalImpl.savedInstanceofServerDorval.close();
				ServerDorvalImpl.savedInstanceofServerDorval.close();
				ServerWestmountImpl.savedInstanceofServerWestmount.close();
				sendMessage(9979, "CNI");
			} else if (sfkkl == 3) {
				ServerDorvalImpl.savedInstanceofServerDorval.close();
				ServerDorvalImpl.savedInstanceofServerDorval.close();
				ServerWestmountImpl.savedInstanceofServerWestmount.close();
				sendMessage(9979, "CNI");
			} else if (sfwst == 3) {
				ServerDorvalImpl.savedInstanceofServerDorval.close();
				ServerDorvalImpl.savedInstanceofServerDorval.close();
				ServerWestmountImpl.savedInstanceofServerWestmount.close();
				sendMessage(9979, "CNI");
			}
		}
	}

	public void handleCrash(int replicaId) {
		restartReplica(replicaId);
		transferHashMap(replicaId);
	}

	public void createAnotherInstance() {
		try {
			
			ServerDorvalImpl s4 = new ServerDorvalImpl(6000);	
			ServerDorvalImpl.savedInstanceofServerDorval = s4;
			ServerKirklandImpl s5 = new ServerKirklandImpl(6001);
			ServerKirklandImpl.savedInstanceofServerKirkland = s5;
			ServerWestmountImpl s6 = new ServerWestmountImpl(6002);
			ServerWestmountImpl.savedInstanceofServerWestmount = s6;
			sendMessage(6000, "RH");
			sendMessage(6001, "RH");
			sendMessage(6002, "RH");
	
	} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void sendMessage(int port, String messageType) {
		try {
			otherRM = InetAddress.getByName("192.168.43.30");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		try {
			ports.put(11, 6000);
			ports.put(12, 6001);
			ports.put(13, 6002);
			ports.put(21, 5000);
			ports.put(22, 5001);
			ports.put(23, 5002);
			ServerDorvalImpl s1 = new ServerDorvalImpl(5000);
			ServerKirklandImpl s2 = new ServerKirklandImpl(5001);
			ServerWestmountImpl s3 = new ServerWestmountImpl(5002);
			ServerDorvalImpl.savedInstanceofServerDorval = s1;
			ServerKirklandImpl.savedInstanceofServerKirkland = s2;
			ServerWestmountImpl.savedInstanceofServerWestmount = s3;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
