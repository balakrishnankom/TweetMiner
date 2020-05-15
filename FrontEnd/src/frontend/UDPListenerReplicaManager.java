package com.drrs.serverside;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPListenerReplicaManager implements Runnable {
	DatagramSocket socket;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			socket = new DatagramSocket(7000);
			while (true) {
				byte buffer[] = new byte[1024];
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				socket.receive(request);
				new RMConnection(socket, request);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}

class RMConnection extends Thread {
	DatagramPacket request;
	DatagramSocket socket;
	String result;
	String dataStore;
	String data[];

	public RMConnection(DatagramSocket socket, DatagramPacket request) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		this.request = request;
		this.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			dataStore = new String(request.getData()).trim();
			data = dataStore.split("#");
			if (data[0].equals("SF")) {
				ReplicaManager.savedInstanceOfReplicaManager.handleFailure(Integer.parseInt(data[1]));
			} else if (data[0].equals("CR")) {
				ReplicaManager.savedInstanceOfReplicaManager.handleCrash(Integer.parseInt(data[1]));
			} else if (data[0].equals("CNI")) {
				ReplicaManager.savedInstanceOfReplicaManager.createAnotherInstance();
			} else if (data[0].equals("SV")) {
				int replicaId = Integer.parseInt(data[1]);
				switch (replicaId) {
				case 5000:
				case 5001:
				case 5002:
					int dvlPort = ReplicaManager.savedInstanceOfReplicaManager.ports.get(21);
					int kklPort = ReplicaManager.savedInstanceOfReplicaManager.ports.get(22);
					int wstPort = ReplicaManager.savedInstanceOfReplicaManager.ports.get(23);
					result = Integer.toString(dvlPort) + Integer.toString(kklPort) + Integer.toString(wstPort);
					this.start();
					break;
				case 6000:
				case 6001:
				case 6002:
					dvlPort = ReplicaManager.savedInstanceOfReplicaManager.ports.get(21);
					kklPort = ReplicaManager.savedInstanceOfReplicaManager.ports.get(22);
					wstPort = ReplicaManager.savedInstanceOfReplicaManager.ports.get(23);
					result = Integer.toString(dvlPort) + Integer.toString(kklPort) + Integer.toString(wstPort);
					this.start();
					DatagramPacket reply = new DatagramPacket(result.getBytes(), result.getBytes().length,
							request.getAddress(), request.getPort());
					socket.send(reply);
					break;

				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
