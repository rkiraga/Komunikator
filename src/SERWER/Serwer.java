package SERWER;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Serwer {
	
	static ArrayList<Socket> connections = new ArrayList<Socket>(); 
	static ArrayList<String> users = new ArrayList<String>(); 
	
	
    public static void main(String[] args) throws IOException {

		try{
			ServerSocket serverSocket = new ServerSocket(4444);
		
	        System.out.println("Oczekiwanie na klientow");
	        
	        while (true){
	        	
	            Socket socket = serverSocket.accept();
	            connections.add(socket);
	            
	            System.out.println("Polaczono z :" + socket.getLocalAddress().getHostName());
	            
	            AddUserName(socket);
	            
	            ChatServer chat = new ChatServer(socket);
	            Thread w = new Thread(chat);
	            w.start();
	        }	
		}catch(Exception e){System.out.println(e);}
	       
		
    }
	

    public static void AddUserName(Socket x) throws IOException{
    	Scanner scanner = new Scanner(x.getInputStream());
    	String username = scanner.nextLine();
    	users.add(username);
    	
    	for(int i=1; i<=connections.size(); i++){
    		Socket tempSocket = (Socket) Serwer.connections.get(i-1);
    		PrintWriter writer = new PrintWriter(tempSocket.getOutputStream());
    		writer.println("#?!" + users);
    		writer.flush();
    	}
    }
}