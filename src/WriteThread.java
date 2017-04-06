import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class WriteThread extends Thread {
	public Socket socket = null;
	public static boolean writeFlag = true;
	PrintWriter os = null;

	WriteThread(Socket socket) throws IOException {
		this.socket = socket;

		try {
			os = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			System.out
					.println("Client Write Thread: IO error, can not get Output Stream");
		}
	}

	public void run() {

		String line = null;

		try {
			while (!MyClient.endFlag) {
				Thread.sleep(10);
				if (LoginFrame.loginFlag) {
					line = "LOGIN " + LoginFrame.user.getUsername() + " "
							+ LoginFrame.user.getPassword();
					LoginFrame.loginFlag = false;

				}

				if (IdleFrame.listFlag) {
					line = "LIST";
					IdleFrame.listFlag = false;

				}
				if (IdleFrame.connectFlag) {
					line = "CONNECT " + IdleFrame.user.getUsername();
					IdleFrame.connectFlag = false;

				}

				if (ChatFrame.sendFlag) {
					line = ChatFrame.textPane2.getText();
					ChatFrame.textPane1.append(line + "\n");
					ChatFrame.textPane2.setText("");
					ChatFrame.sendFlag = false;

				}
				if (ChatFrame.closeFlag) {
					line = "CLOSE";
					ChatFrame.closeFlag = false;

				}

				if (line != null) {
					if (line.equals("CLOSE"))
					{
						MyClient.endFlag=true;
					}
					os.write(line + "\n");
					os.flush();
					line = null;

				}

			}

		} catch (NullPointerException | InterruptedException e) {
			line = this.getName(); // reused String line for getting thread name
			System.out.println("this " + line + " Closed");
		}

		finally {
			try {
				System.out.println("Client Write Thread: Connection Closing..");
				if (os != null) {
					os.close();
					System.out
							.println("Client Write Thread: Socket output Stream Closed");
				}
				if (socket != null) {
					socket.close();
					System.out.println("Client Write Thread: Socket Closed");
				}

			} catch (IOException ie) {
				System.out.println("Client Write Thread: Socket Close Error");
			}
		}// end finally

	}
}
