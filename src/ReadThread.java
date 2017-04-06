import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ReadThread extends Thread {

	private ChatFrame chatFrame=null;
	private IdleFrame idleFrame=null;
	public Socket socket = null;
	DataInputStream is = null;
	LoginFrame loginFrame = null;

	ReadThread(Socket socket, LoginFrame loginFrame) throws IOException {
		this.socket = socket;
		this.loginFrame = loginFrame;
		try {
			is = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out
					.println("Client Read Thread: IO error, can not get Input Stream");
		}
	}

	@SuppressWarnings("deprecation")
	public void run() {

		String line;
		String[] token;
		try {
			Thread.sleep(0);
			while (true) {

				line = is.readLine();
				if (line != null) {

					token = line.split(" ");
					if (token[0].equals("USER")) {
						if (token[1].equals("TRUE")) {
							loginFrame.dispose();
							 idleFrame = new IdleFrame();
							idleFrame.setVisible(true);

						} else {
							JOptionPane.showMessageDialog(loginFrame,
									"please try again", token[1] + " ERROR",
									JOptionPane.ERROR_MESSAGE);
						}
						continue;

					}
					if (token[0].equals("LIST")) {
						int length = token.length;
						if (token[1] == null)
							length = 1;
						IdleFrame.table.setModel(new DefaultTableModel(
								length - 1, 1));
						for (int i = 1; i < length; i++) {
							IdleFrame.table.setValueAt(token[i], i - 1, 0);
						}

						continue;
					}

					if (token[0].equals("CNNECTED")) {

						IdleFrame.connectFlag = true;
						IdleFrame.user.setUsername(token[1]);

						chatFrame = new ChatFrame();
						chatFrame.me.setText("me");
						chatFrame.he.setText(token[1]);
						chatFrame.setTitle("Chating with "+token[1]+"...");
						chatFrame.setVisible(true);

						continue;
					}
					if (line.equals("CLOSE")) {
						MyClient.endFlag=true;
						chatFrame.dispose();
						idleFrame.dispose();
						break;
					}
					ChatFrame.textPane0.append(line + "\n");
				}

			}
		} catch (IOException e) {
			line = this.getName(); // reused String line for getting thread name
			System.out.println("Client Read Thread: IO Error/ this " + line
					+ " terminated abruptly");
		} catch (NullPointerException e) {
			line = this.getName(); // reused String line for getting thread name
			System.out.println("Client Read Thread: this " + line + " Closed");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				System.out.println("Client Read Thread: Connection Closing..");
				if (is != null) {
					is.close();
					System.out
							.println("Client Read Thread: Socket Input Stream Closed");
				}
				if (socket != null) {
					socket.close();
					System.out.println("Client Read Thread: Socket Closed");
				}

			} catch (IOException ie) {
				System.out.println("Client Read Thread: Socket Close Error");
			}
		}// end finally

	}
}
