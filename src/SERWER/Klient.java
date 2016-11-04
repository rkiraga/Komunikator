package SERWER;

import java.io.*;
import java.net.*;
	
public class Klient {

		public static void main(String[] args) throws Exception{
		Klient klient = new Klient();
		klient.run();
		}
		       
		public void run() throws UnknownHostException, IOException{
		    	Socket socket = new Socket("89.78.245.155", 4444);
		    	PrintStream printStream = new PrintStream(socket.getOutputStream());
            	printStream.println("czesc serwer, tu klient!\n");
		        
            	InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
            	BufferedReader  bufferedReader = new BufferedReader(streamReader);
            	
            	String message = bufferedReader.readLine();
            	System.out.println(message);
            
		        socket.close();
		}
}//komentarsz
