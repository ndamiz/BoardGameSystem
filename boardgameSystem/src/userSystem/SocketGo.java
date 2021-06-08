package userSystem;

import java.net.InetAddress;
import java.net.Socket;

public class SocketGo {

	public SocketGo() {
		try {
			InetAddress ia = InetAddress.getByName("54.180.213.64");
			
			Socket s = new Socket(ia, 10214);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
