import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class MyClient {

	public static String address = null;
	public static int port = 0;
public static boolean endFlag=false;
	public static void main(String[] args) {

		Socket clientSocket = null;
		LoginFrame frame = null;
		try {
			frame = new LoginFrame();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			while (address == null || port == 0) {
				Thread.sleep(100);
			}
			
			clientSocket = new Socket(address, port);
		} catch (IOException | NullPointerException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			WriteThread wt = new WriteThread(clientSocket);
			ReadThread rt = new ReadThread(clientSocket, frame);

			wt.start();
			rt.start();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Socket read Error");
		}

	}

}
