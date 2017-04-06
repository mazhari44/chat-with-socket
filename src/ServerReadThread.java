import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerReadThread extends Thread {

	private  EntityManagment em = new EntityManagment();
	private  User user = new User();

	private Socket mySocket = null;
	private Socket hisSocket = null;
	private Socket goalSocket = null;
	private String line;
	private BufferedReader is = null;

	ServerReadThread(Socket mySocket, Socket hisSocket) throws IOException {
		this.mySocket = mySocket;
		this.hisSocket = hisSocket;
		try {
			is = new BufferedReader(new InputStreamReader(
					mySocket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Server Read Thread: IO error, can not get Input Stream");
		}
	}

	private synchronized void readMessage() {

		if (line != null) {
			ServerMemory.socketQueue.offer(goalSocket);
			ServerMemory.messageQueue.offer(line);
		}
	}

	public void run() {

		try {
			while (true) {

				line = is.readLine();
				goalSocket = hisSocket;
				
				String[] token = null;
				if (line != null) {
					token = line.split(" ");

					if (token[0].compareTo("LOGIN") == 0 && token.length == 3) {
						
						user.setUsername(token[1]);
						user.setPassword(token[2]);
						String loginResult = em.userExist(user);

						if (loginResult == "USER TRUE")
							MyServer.addIdleList(mySocket, token[1]);

						line = loginResult;
						goalSocket = mySocket;

					}
	
					if (token[0].compareTo("LIST") == 0) {
			
						line = "LIST " + MyServer.listToString();
						goalSocket = mySocket;
					}
					

					if (token[0].compareTo("CONNECT") == 0) {

						String man = user.getUsername();
						line = null;
						int i = 0;

						while (i < MyServer.idleCount) {
							if (MyServer.idleUsernameList[i]
									.compareTo(token[1]) == 0) {
								hisSocket = MyServer.idleSocketList[i];
								MyServer.removeIdleList(token[1]);
								// MyServer.removeIdleList(man);
								System.out.println(hisSocket);
								goalSocket = hisSocket;
				
							}
							if (MyServer.idleUsernameList[i] != null
									&& MyServer.idleUsernameList[i]
											.compareTo(man) == 0) {
								line = "CNNECTED " + man;
							}
							
							System.out.println("connected:"+man);
							System.out.println("connect:"+ token[1]);
					
							i++;
						}

					}
				}

				if (line != null) {
					readMessage();
					if(line.compareTo("CLOSE")==0)
					{
						//MyServer.removeBusyList(user.getUsername());
						break;
					}
					
				}

			}
		} catch (IOException e) {

			line = this.getName(); // reused String line for getting thread name
			System.out.println("Server Read Thread: IO Error/ this " + line
					+ " terminated abruptly");
		} catch (NullPointerException e) {
			line = this.getName(); // reused String line for getting thread name
			System.out.println("Server Read Thread: this " + line + " Closed");
		}

		finally {
			try {
				System.out.println("Server Read Thread: Connection Closing..");
				if (is != null) {
					is.close();
					System.out.println("Server Read Thread:  Socket Input Stream Closed");
				}
				if (mySocket != null) {
					mySocket.close();
					System.out.println("Server Read Thread: Socket Closed");
				}

			} catch (IOException ie) {
				System.out.println("Server Read Thread: Socket Close Error");
			}
		}// end finally

	}
}
