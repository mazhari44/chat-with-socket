
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;



public class ServerWriteThread extends Thread {
	
	public static boolean serverWriterIsRun=false;
	private Socket hisSocket = null;
	private PrintWriter os = null;
	private String line;

	ServerWriteThread(){
		serverWriterIsRun=true;
	}
	

	private synchronized void popMessage() {
		
		if(!ServerMemory.socketQueue.isEmpty())
		{
			hisSocket=ServerMemory.socketQueue.poll();
			line=ServerMemory.messageQueue.poll();
		}
	}
	public void run() {
			
		try {
			while (true) {

				popMessage();
				
				try {
					if(hisSocket!=null){
					os = new PrintWriter(hisSocket.getOutputStream());}
				} catch (IOException e) {
					System.out.println("Server Write Thread: IO error , can not get Output Stream");
				}
								
				if(line!=null)
				{
					os.write(line+"\n");
					os.flush();
					if(line.compareTo("CLOSE")==0)
					{
						serverWriterIsRun=false;
						break;
					}
					line=null;
				}
			}
			
		} catch (NullPointerException e) {
			line = this.getName(); // reused String line for getting thread name
			System.out.println("Server Write Thread:  this " + line + " Closed");
		}

		finally {
			try {
				System.out.println("Server Write Thread:  Connection Closing..");
				if (os!= null) {
					os.close();
					System.out.println("Server Write Thread:  Socket output Stream Closed");
				}
				if (hisSocket != null) {
					hisSocket.close();
					System.out.println("Server Write Thread:  Socket Closed");
				}

			} catch (IOException ie) {
				System.out.println("Server Write Thread:  Socket Close Error");
			}
		}// end finally

	}
}
