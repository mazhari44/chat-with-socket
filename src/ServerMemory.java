import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;


public class ServerMemory {
	public static Queue<Socket> socketQueue = new LinkedList<Socket>();
	public static Queue<String> messageQueue = new LinkedList<String>();
	public void print(){
		int i=0;
		while(i<socketQueue.size())
		{
			System.out.println("Memory print"+messageQueue.element());
			System.out.println("Memory print"+socketQueue.element());
			i++;
		}
		
	}
}
