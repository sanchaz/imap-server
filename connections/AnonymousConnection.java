package connections;

import java.util.*;
import java.net.Socket;
import java.net.ServerSocket;

public class AnonymousConnection {
	
	private List<Connection> _anonymousconnections = new ArrayList<Connection>();
	
	public List<Connection> getAnonymousConnections() {
		return _anonymousconnections;
	}
	
	public void setAnonymousConnections(List<Connection> c) {
		_anonymousconnections = c;
	}
	
	public void putAnonymousConnection(Connection c) {
		_anonymousconnections.add(c);
	}
	
	public void rmAnonymounsConnection(String username, String password) {
		List<Connection> temp = new ArrayList<Connection>();
		Connection ctemp;
		for(Connection c : _anonymousconnections) {
			if((c.getClient().getClientID().compareTo(username) == 0) && (c.getClient().getClientPWD().compareTo(password) == 0)) {
				ctemp = c;
			}else{
				temp.add(c);
			}
		}
		//getAnonymousConnections().clear();
		setAnonymousConnections(temp);
	}
	
}
