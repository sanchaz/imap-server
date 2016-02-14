package connections;

import java.net.Socket;
import java.net.ServerSocket;

import main.IMAPServer;

public class Connection {
	
	private IMAPServer _imapserver;
	
	private Client _client; //Client to whom the connection belongs to
	
	private ClientConnection _clientconnection; //Connection to client-proxy

	private ServerConnection _serverconnection = null; //Connection to proxy-IMAP server (null if no auth atempt has been made)

	private boolean _authenticated = false; //If client has been authenticated or not (false by default)
	
	private String _username;
	
	private String _pwd;
	
	public IMAPServer getIMAPServer() {
		return _imapserver;
	}
	
	public Client getClient() {
		return _client;
	}
	
	public void setClient(Client c) {
		_client = c;
	}
	
	public void createClient() {
		getIMAPServer().putClient(getClientID(), new Client(getClientID(), getClientPWD(), getIMAPServer()));
	}
	
	public void setClientConnection(ClientConnection c) { //sets the clientconnection (includes the client-proxy socket)
		_clientconnection = c;
		c.setConnection(this);
	}
	
	public ClientConnection getClientConnection() { //returns the client connection
		return _clientconnection;
	}
	
	public void setServerConnection(ServerConnection s) { //sets the clientconnection (includes the client-proxy socket)
		_serverconnection = s;
		s.setConnection(this);
	}
	
	public ServerConnection getServerConnection() { //returns the server connection
		return _serverconnection;
	}
	
	public boolean isAuthed() {
		return _authenticated;
	}
	
	public void setAuth(boolean a) {
		_authenticated = a;
	}
	
	public String getClientID() {
		return _username;
	}
	
	public void setClientID(String username) {
		_username = username;
	}
	
	public String getClientPWD() {
		return _pwd;
	}
	
	public void setClientPWD(String pwd) {
		_pwd = pwd;
	}
	
	public Connection(ClientConnection c, IMAPServer sv) {
		_clientconnection = c;
		_imapserver = sv;
		c.setConnection(this);
	}
	
}
