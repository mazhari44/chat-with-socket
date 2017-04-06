//echo server

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

	public static boolean chatBegin = false;

	public static Socket[] idleSocketList = new Socket[10];
	public static String[] idleUsernameList = new String[10];
	public static int idleCount = 0;

	public static Socket[] busySocketList = new Socket[10];
	public static String[] busyUsernameList = new String[10];
	public static int busyCount = 0;

	public static void addIdleList(Socket socket, String username) {
		idleSocketList[idleCount] = socket;
		idleUsernameList[idleCount] = username;
		idleCount++;
	}

	public static void removeIdleList(String username) {
		int i = 0, j = 0;
		int length = idleCount;
		while (i < length) {
			if (idleUsernameList[i].equals(username)) {
				j = i;
				idleCount--;
				while (j < length - 1) {
					idleUsernameList[j] = idleUsernameList[j + 1];
					idleSocketList[j] = idleSocketList[j + 1];
					j++;
				}

				idleUsernameList[j] = null;
				idleSocketList[j] = null;

				busyUsernameList[busyCount] = idleUsernameList[i];
				busySocketList[busyCount] = idleSocketList[i];
				busyCount++;

				i = j;
			}
			i++;
		}

	}

	public static void removeBusyList(String username) {
		int i = 0, j = 0;
		int length = busyCount;
		while (i < length) {
			if (busyUsernameList[i].equals(username)) {
				j = i;
				busyCount--;
				while (j < length - 1) {
					busyUsernameList[j] = busyUsernameList[j + 1];
					busySocketList[j] = busySocketList[j + 1];
					j++;
				}

				busyUsernameList[j] = null;
				busySocketList[j] = null;

				idleUsernameList[idleCount] = busyUsernameList[i];
				idleSocketList[idleCount] = busySocketList[i];
				idleCount++;

				i = j;
			}
			i++;
		}

	}

	public static String listToString() {
		int i = 1;
		String listString = idleUsernameList[0];
		while (i < idleCount) {
			listString = listString + " " + idleUsernameList[i];
			i++;
		}
		return listString;
	}

	public static void main(String args[]) throws Exception {

		Socket clientSocket = null;
		ServerSocket serverSocket = null;
		System.out.println("Server Listening......");
		try {
			serverSocket = new ServerSocket(8090); // can also use static final
													// PORT_NUM , when defined

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ServerSocket error");

		}

		while (true) {
			try {

				clientSocket = serverSocket.accept();
				System.out.println("Server: Connection Established");
				ServerThread st = new ServerThread(clientSocket, null);
				st.start();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Server: Connection Error");
				// TODO: handle exception
			}
		}

	}

}

class ServerThread extends Thread {

	Socket mySocket = null;
	Socket hisSocket = null;

	public ServerThread(Socket mySocket, Socket hisSocket) {

		this.mySocket = mySocket;
		this.hisSocket = hisSocket;
	}

	public void run() {

		ServerWriteThread wt;
		ServerReadThread rt;
		try {
			//if (!ServerWriteThread.serverWriterIsRun) {
				wt = new ServerWriteThread();
				wt.start();
		//	}
			rt = new ServerReadThread(mySocket, hisSocket);

			rt.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
