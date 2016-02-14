package connections;

import java.io.*;
import java.lang.Thread;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;

import static main.Parsing.Parser;

public class ClientConnection extends Thread {
	
	private Connection _connection; //Pipeline between client-proxy connection and proxy-server connection
	
	private Socket _socket; //Connection to Client
	
	private BufferedReader _socketinput = null; //read msgs sent from client
	
	private PrintWriter _socketoutput = null; //send msgs to client
	
	public Connection getConnection() {
		return _connection;
	}
	
	public void setConnection(Connection c) {
		_connection = c;	
	}
	
	public Socket getClientSocket() {
		return _socket;
	}
	
	public BufferedReader getClientInputStream() {
		return _socketinput;	
	}
	
	public PrintWriter getClientOutputStream() {
		return _socketoutput;
	}
	
	public void run() {
		while(true) {
			try {
				String temp = getClientInputStream().readLine();
				if(temp != null) {
					System.out.println("Client Output:" + temp);
					System.out.println("went in");
					/*String[] s;
					if(getConnection().isAuthed() == false) {
						if(Parser.login(temp) == true) {
							s = Parser.getUserPass(temp);
							getConnection().setClientID(s[0]);
							getConnection().setClientPWD(s[1]);
							if(getConnection().getIMAPServer().existClient(s[0], s[1])) {
								Client c = getConnection().getIMAPServer().getClient(s[0]);
								c.putConnection(getConnection());
								getConnection().setClient(c);
								getConnection().getIMAPServer().getACManager().rmAnonymounsConnection(getConnection().getClientID(), getConnection().getClientPWD());
							} else {
								getConnection().setServerConnection(getConnection().getIMAPServer().getStandbyConnection());
							}
						}
					}*/
					if(getConnection().getServerConnection() == null) {
						getConnection().setServerConnection(getConnection().getIMAPServer().getStandbyConnection());	
					}
					//getConnection().getServerConnection().getServerOutputStream().flush();
					getConnection().getServerConnection().getServerOutputStream().println(temp);
				}
			} catch (IOException e) {
				System.out.println("" + e.getMessage());
			}
		}
        }

	public ClientConnection(Socket s) {
		_socket = s;
		try {
			_socketinput = new BufferedReader(new InputStreamReader(s.getInputStream()));
			_socketoutput = new PrintWriter(s.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("" + e.getMessage());
		}
		getClientOutputStream().println("* OK [CAPABILITY IMAP4rev1 UIDPLUS CHILDREN NAMESPACE THREAD=ORDEREDSUBJECT THREAD=REFERENCES SORT QUOTA IDLE ACL ACL2=UNION] Courier-IMAP ready. Copyright 1998-2010 Double Precision, Inc.  See COPYING for distribution information.");
	}
	
}
