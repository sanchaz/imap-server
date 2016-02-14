package connections;

import java.util.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;

import main.IMAPServer;

public class Client {
	
	private IMAPServer _imapserver;

	private String _username;
	
	private String _pwd;
	
	private List<Connection> _clientconnections = new ArrayList<Connection>();
	
	private List<ServerConnection> _preauthedconnections = new ArrayList<ServerConnection>();
	
	public IMAPServer getIMAPServer() {
		return _imapserver;
	}
	
	public String getClientID() {
		return _username;
	}
	
	public String getClientPWD() {
		return _pwd;
	}
	
	public List<Connection> getClientConnections() {
		return _clientconnections;
	}
	
	public void putConnection(Connection c) {
		getClientConnections().add(c);
	}
	
	public List<ServerConnection> getPreAuthedConnections() {
		return _preauthedconnections;
	}
	
	public void putPreAuthedConnection(ServerConnection c) {
		getPreAuthedConnections().add(c);
	}

	public ServerConnection getPreAuthedConnection() {
		ServerConnection c = getPreAuthedConnections().remove(0);
		try {
			ServerConnection sc = new ServerConnection(new Socket(getIMAPServer().getServerHost(), getIMAPServer().getServerPort()));
			putPreAuthedConnection(sc);
			sc.start();
		} catch (UnknownHostException e) {
			System.out.println("Socket not binded to host. Unknown host: " + e.getMessage());
		} catch (IOException e2) {
			System.out.println("" + e2.getMessage());
		}
		return c;
	}
	
	public Client(String username, String pwd, IMAPServer is) {
		_username = username;
		_pwd = pwd;
		_imapserver = is;
		try {
			ServerConnection sc = new ServerConnection(new Socket(getIMAPServer().getServerHost(), getIMAPServer().getServerPort()));
			putPreAuthedConnection(sc);
		sc.preAuthConnection(getClientID(), getClientPWD());
		sc.start();
		} catch (UnknownHostException e) {
			System.out.println("Socket not binded to host. Unknown host: " + e.getMessage());
		} catch (IOException e2) {
			System.out.println("" + e2.getMessage());
		}
	} 
	
}
