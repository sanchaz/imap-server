package main;

import java.util.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;


import connections.*;
import static debug.DebugMsgs.DEBUG;

public class IMAPServer {
	
	private List<ServerConnection> _standbyconnections = new ArrayList<ServerConnection>(); //contains all standby and ready to use connections
	
	private Map<String, Client> _clients = new LinkedHashMap<String, Client>(); //contains all clients
	
	private AnonymousConnection _anonymousconnectionmanager = new AnonymousConnection(); //manages the unauthed and therefor not associated to any client connections
	
	private int _nconnections = 0; //total connections
	
	private int _authedconnections = 0; //number of authenticated connections
	
	private String _serverhost = null; //Imap server host
	
	private int _serverport; //Imap server port
	
	private void createStandbyConnection() { //creates new ready to use connections to the imap server
		try {
			ServerConnection s = new ServerConnection(new Socket(getServerHost(), getServerPort()));
			putStandbyConnection(s);
			s.start();
		} catch (UnknownHostException e) {
			System.out.println("Socket not binded to host. Unknown host: " + e.getMessage());
		} catch (IOException e2) {
			System.out.println("" + e2.getMessage());
		}
	}
	
	public List<ServerConnection> getStandbyConnections() { //returns a list of all ready to use connections to the imap server
		return _standbyconnections;
	}
	
	public void putStandbyConnection(ServerConnection c) { //adds a new ready to use connection to the imap server
		getStandbyConnections().add(c);	
	}
	
	public ServerConnection getStandbyConnection() { //retrieves a ready to use connection from the imap server
		System.out.println("Standby Connections list size = " + getStandbyConnections().size());
		ServerConnection c = getStandbyConnections().remove(0);
		createStandbyConnection();
		return c;
	}
	
	private void createConnection(Socket s) {
		DEBUG.creatingClientConnection();
		//Connection c = getStandbyConnection();
		ClientConnection cc = new ClientConnection(s);
		//c.setClientConnection(cc);
		getACManager().putAnonymousConnection(new Connection(cc, this));
		cc.start();
	}
	
	public Map<String, Client> getClients() {
		return _clients;
	}
	
	public Client getClient(String username) {
		return getClients().get(username);
	}
	
	public boolean containsClient(String username) {
		return getClients().containsKey(username); 
	}
	
	public void putClient(String username, Client c) {
		getClients().put(username, c);
	}
	
	public boolean existClient(String username, String pwd) {
		for(Client c : getClients().values()) {
			if((c.getClientID().compareTo(username) == 0) && (c.getClientPWD().compareTo(pwd) == 0)) {
				return true;
			}
		}
		return false;
	}
	
	public AnonymousConnection getACManager() {
		return _anonymousconnectionmanager;	
	}
	
	public int getNumberConnections() {
		return _nconnections;
	}
	
	public void setNumberConnections(int n) {
		_nconnections = n;
	}
	
	public int getNumberAuthedConnections() {
		return _authedconnections;
	}
	
	public void setNumberAuthedConnections(int n) {
		_authedconnections = n;
	}
	
	public String getServerHost() {
		return _serverhost;
	}
	
	public void setServerHost(String host) {
		_serverhost = host;
	}
	
	public int getServerPort() {
		return _serverport;
	}
	
	public void setServerPort(int port) {
		_serverport = port;
	}
	
	private void serverStart(String args[]) {
		setServerHost(args[0]);
		DEBUG.setDebugLevel(Integer.parseInt(args[3]));
		setServerPort(143);
		for(int i = 0; i < 1; i++) {
			createStandbyConnection();
		}
	}
	
	public static void main(String args[]) {
		IMAPServer imapserver = new IMAPServer();
		imapserver.serverStart(args);
		Listen server = new Listen(5000);
		while(true) {
			DEBUG.nAConnections(imapserver.getACManager().getAnonymousConnections().size());
			//waiting for connection
			DEBUG.waiting();
			imapserver.createConnection(server.accept());
		}
	}
	
}
