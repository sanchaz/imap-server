package debug;

import main.IMAPServer;

public class DebugMsgs {
	
	private static int _debuglevel = 1;	
	
	public static void setDebugLevel(int i) {
		_debuglevel = i;
	}
	
	public static final void waiting() {
		if (_debuglevel >= 4) {
			System.out.println("Waiting for client connection to proxy");
		}
	}
	
	public static final void receivedClientConnection(String ip) {
		if (_debuglevel >= 1) {
			System.out.println("Received client connection to proxy from " + ip);
		}
	}
	
	public static final void nAConnections(int n) {
		if (_debuglevel >= 2) {
			System.out.println("Currently there are " + n + " anonymous connections to the proxy");
		}
	}
	
	public static final void creatingClientConnection() {
		if (_debuglevel >= 5) {
			System.out.println("Creating client connection to the proxy");
		}
	}
	
	
	public static/* final */DebugMsgs DEBUG = new DebugMsgs();

}
