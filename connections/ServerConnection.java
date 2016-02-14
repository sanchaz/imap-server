package connections;

import java.io.*;
import java.lang.Thread;
import java.net.Socket;
import java.net.ServerSocket;

import static main.Parsing.Parser;

public class ServerConnection extends Thread {
	
	private Connection _connection; //Pipeline between client-proxy connection and proxy-server connection

	private Socket _socket; //Connection to IMAP server
	
	private BufferedReader _socketinput = null; //read msgs sent from server
	
	private PrintWriter _socketoutput = null; //send msgs to server
	
	public Connection getConnection() {
		return _connection;
	}
	
	public void setConnection(Connection c) {
		_connection = c;	
	}
	
	public Socket getServerSocket() {
		return _socket;
	}

	public BufferedReader getServerInputStream() {
		return _socketinput;	
	}
	
	public PrintWriter getServerOutputStream() {
		return _socketoutput;
	}
	
	public void preAuthConnection(String username, String pwd) {
		try {
			getServerOutputStream().print("1 LOGIN " + username + " " + pwd + '\r' + '\n');
			String s;
			while((s = getServerInputStream().readLine()) == null) {
			}
			if(Parser.loginOK(s) == false) {
				System.out.println("Wrong username/password. System error... This shouldn't have happen. Shutting Down...");
				System.exit(-1);
			}
		} catch (IOException e) {
			System.out.println("" + e.getMessage());
		}
	}
	
	public void run() {
		while(true) {
			try {
				String temp = getServerInputStream().readLine();
				if(getConnection() != null) {
					if(temp != null) {
						System.out.println("Server Output:" + temp);
						/*if(getConnection().isAuthed() == false) {
							String[] s;
							if(Parser.loginOK(temp) == false) {
								getConnection().setClientID(null);
								getConnection().setClientPWD(null);
							} else {
								getConnection().setAuth(true);
								getConnection().createClient();
							}
						}*/
						//getConnection().getClientConnection().getClientOutputStream().flush();
						getConnection().getClientConnection().getClientOutputStream().println(temp);
					}
				}
			} catch (IOException e) {
				System.out.println("" + e.getMessage());
			}
		}
        }
        
        public ServerConnection(Socket s) {
		_socket = s;
		try {
			_socketinput = new BufferedReader(new InputStreamReader(s.getInputStream()));
			_socketoutput = new PrintWriter(s.getOutputStream(), true);
			//getServerInputStream().readLine();
		} catch (IOException e) {
			System.out.println("" + e.getMessage());
		}
	}
	
}
