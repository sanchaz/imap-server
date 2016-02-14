package connections;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;

import static debug.DebugMsgs.DEBUG;

public class Listen {
	
	private ServerSocket _socket;
	
	public Socket accept() {
		Socket s = null;
		try {
			System.out.println("test1");
			s = _socket.accept();
			System.out.println("test2");
		} catch (IOException e) {
			System.out.println("" + e.getMessage());
		}
		DEBUG.receivedClientConnection(_socket.getInetAddress().getHostAddress());
		return s;
	}
	
	public Listen(int port) {
		if(port >= 1 && port <= 65535) {
			try {
				_socket = new ServerSocket(port);
			} catch (IOException e) {
				System.out.println("Cannot create listen on port " + port + ": " + e.getMessage());
			}
		}else{
			//wrong
		}
	}
	
}
