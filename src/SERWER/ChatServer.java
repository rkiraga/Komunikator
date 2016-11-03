package SERWER;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer implements Runnable{
	Socket socket;
	private Scanner input;
	private PrintWriter output;
	String message = "";
	
	
	public ChatServer(Socket x){
		this.socket = x;
	}
	
	public void checkConnection() throws IOException{
		
		if(!socket.isConnected()){
			
			for(int i=1; i<=Serwer.connections.size(); i++){
				if(Serwer.connections.get(i) == socket) Serwer.connections.remove(i);
			}
		
		
			for(int i=1; i<=Serwer.connections.size(); i++){
				Socket tempSocket = (Socket) Serwer.connections.get(i-1);
				PrintWriter tempWriter = new PrintWriter(tempSocket.getOutputStream());
				tempWriter.println(tempSocket.getLocalAddress().getHostName() + " disconnected");
				tempWriter.flush();
				System.out.println(tempSocket.getLocalAddress().getHostName() + " disconnected");
			}
		}
	}

	public void run(){
		try{
				try{
					input  = new Scanner(socket.getInputStream());
					output = new PrintWriter(socket.getOutputStream());
					
					while(true){
						checkConnection();
						
						if(!input.hasNext()) return ;
						
						message = input.nextLine();
						
						System.out.println("Client said: " + message);
						
						for(int i=1; i<=Serwer.connections.size(); i++){
							Socket tempSocket = (Socket) Serwer.connections.get(i-1);
							PrintWriter tempWriter = new PrintWriter(tempSocket.getOutputStream());
							tempWriter.println(message);
							tempWriter.flush();
							System.out.println("Sent to: " + tempSocket.getLocalAddress().getHostName());
						}
					}
				}
				finally{
					socket.close();
				}
		}catch(Exception e){
			System.out.println(e);
		}
	}
}

