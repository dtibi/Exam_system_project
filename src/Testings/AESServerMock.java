package Testings;


import java.io.IOException;

import logic.iMessage;
import ocsf.server.AESServer;
import ocsf.server.ConnectionToClient;
import ocsf.server.IAESServer;

public class AESServerMock implements IAESServer {

	AESClientMock clientMock;
	AESServer realServer;
	
	public AESServerMock(AESClientMock cm) {
		clientMock = cm;
		realServer = new AESServer();
	}

	@Override
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		System.out.println("Mock Server Got msg:" + msg);
		if(!(msg instanceof iMessage)) {
			System.out.println("msg from client is not of type iMessage!");
			return;
		}
		iMessage m = (iMessage) msg;
		String cmd = new String(m.getCommand());
		Object o = m.getObj();
		switch(cmd) {
		case "login":
			try {
				realServer.loginFunctionality(new ConnectionToClientMock(clientMock),new DBMainMock(), o);
			} catch (IOException e) {
				// this should never catch because we are not really sending IO to anyone.
				// this is a test.
			}
			break;
		} 
	}

}
