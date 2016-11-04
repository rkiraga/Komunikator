package SERWER;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import static java.lang.Thread.sleep;

public class A_Chat_Client_GUI {
    
    private static A_Chat_Client ChatClient;
    public static String UserName = "Anonymous";
    
    private static boolean ifConnected = false;
    
    public  static JFrame  MainWindow = new JFrame();
    private static JButton B_ABOUT = new JButton();
    private static JButton B_CONNECT = new JButton();
    private static JButton B_DISCONNECT = new JButton();
    private static JButton B_HELP = new JButton();
    private static JButton B_SEND = new JButton();
    private static JLabel  L_Message = new JLabel("Message: ");
    public  static JTextField TF_Message = new JTextField(20);
    private static JLabel L_Conversation = new JLabel();
    public  static JTextArea TA_CONVERSATION = new JTextArea();
    private static JScrollPane SP_CONVERSATION = new JScrollPane();
    private static JLabel L_ONLINE = new JLabel();
    public  static JList JL_ONLINE = new JList();
    private static JScrollPane SP_ONLINE = new JScrollPane();
    private static JLabel L_LoggedInAs = new JLabel();
    private static JLabel L_LoggedInAsBox = new JLabel();
    
    public  static JFrame LogInWindow = new JFrame();
    public  static JTextField TF_UserNameBox = new JTextField(20);
    private static JButton B_ENTER = new JButton("ENTER");
    private static JLabel L_EnterUserName = new JLabel("Enter username: ");
    private static JPanel P_LogIn = new JPanel();
    
    public  static JFrame ExitWindow = new JFrame();
    private static JPanel P_Exit = new JPanel();
    //public  static JTextField TF_UserNameBox = new JTextField(20);
    private static JLabel  L_Exit = new JLabel("Are you sure?");
    private static JButton B_EXIT_Yes = new JButton("Jestem siur");
    private static JButton B_EXIT_No  = new JButton("NEIN");
    
    protected final static int MW_width = 510, MW_height = 320;
    
    public static void main(String[] args) 
    {
        BuildMainWindow();
        Initialize();
        
        while (true) {

            try{
                sleep(250);
                if(MainWindow.getWidth() != MW_width || MainWindow.getHeight() != MW_height) {
                    changeWindowSize();
                }
            }
            catch(Exception e) {
                System.out.println("BLAD Zmiana rozmiaru okna");
            }
        }
    }
    
    public static void Connect()
    {
        try
        {
 
            final int PORT = 4444;
            final String HOST = "localhost";
            Socket SOCK = new Socket(HOST, PORT);
            System.out.println("You connected to: "+ HOST);
            
            ChatClient = new A_Chat_Client(SOCK);
            ifConnected = true;
            
            PrintWriter OUT = new PrintWriter(SOCK.getOutputStream());
            OUT.println(UserName);
            OUT.flush();
           
            Thread X = new Thread(ChatClient);
            X.start();
            
            
            
        }
        catch(Exception e)
        {
            System.out.print(e);
            JOptionPane.showMessageDialog(null, "Server not responding.");
            ifConnected = false;
            System.exit(0);
        }
    }
    
    public static void Initialize()
    {
        B_SEND.setEnabled(false);
        B_DISCONNECT.setEnabled(false);
        B_CONNECT.setEnabled(true);
    }
  
    //funkcja losujaca numer bany
    public static int randIntforUserName(int bound1, int bound2) {
        //przedzia³y losowania
        int min = Math.min(bound1, bound2); //wybiera wartosc min z przedzialu
        int max = Math.max(bound1, bound2);
        //math.random zwraca wynik od 0 do 1
        return (int) (min + (Math.random() * (max - min)));
    }
    
    public static void BuildLogInWindow()
    {
        LogInWindow.setTitle("What's your name?");
        LogInWindow.setSize(400, 100);
        LogInWindow.setLocation(250, 200);
        LogInWindow.setResizable(true);
        P_LogIn = new JPanel();
        P_LogIn.add(L_EnterUserName);
        //ustawienie domyœlnie nazwy uzytkownika na bana + kolejne 
        //numery polaczonych 
        //TF_UserNameBox.setText("bana_"+ String.valueOf(Serwer.connections.size() + 1)); 
        TF_UserNameBox.setText("bana_ " + randIntforUserName(1,1000));
        P_LogIn.add(TF_UserNameBox);
        P_LogIn.add(B_ENTER);
        LogInWindow.add(P_LogIn);
        
        Login_Action();
        LogInWindow.setVisible(true);
    }
    
    
    public static void changeWindowSize() {

        B_DISCONNECT.setBounds(MainWindow.getWidth()/50, MainWindow.getHeight()/8, (int)(MainWindow.getWidth()/4.5), (int)(MainWindow.getHeight()/12.8));
        B_CONNECT.setBounds(MainWindow.getWidth()/4, MainWindow.getHeight()/8, (int)(MainWindow.getWidth()/4.5), (int)(MainWindow.getHeight()/12.8));
        B_SEND.setBounds((int)(MainWindow.getWidth()/2.1), MainWindow.getHeight()/8, (int)(MainWindow.getWidth()/6), (int)(MainWindow.getHeight()/12.8));
        B_ABOUT.setBounds((int)(MainWindow.getWidth()/1.5), MainWindow.getHeight()/8, (int)(MainWindow.getWidth()/7), (int)(MainWindow.getHeight()/12.8));
        B_HELP.setBounds((int)(MainWindow.getWidth()/1.2), MainWindow.getHeight()/8, (int)(MainWindow.getWidth()/7), (int)(MainWindow.getHeight()/12.8));

        L_Message.setBounds(MainWindow.getWidth()/50, MainWindow.getHeight()/32, (int)(MainWindow.getWidth()/8.3), (int)(MainWindow.getHeight()/16));
        L_LoggedInAs.setBounds((int)(MainWindow.getWidth()/1.5), 0, (int)(MainWindow.getWidth()/3.5), (int)(MainWindow.getHeight()/21));
        L_LoggedInAsBox.setBounds((int)(MainWindow.getWidth()/1.5), (int)(MainWindow.getHeight()/18.8), (int)(MainWindow.getWidth()/3.3), (int)(MainWindow.getHeight()/16));
        L_Conversation.setBounds(MainWindow.getWidth()/5, (int)(MainWindow.getHeight()/4.6), (int)(MainWindow.getWidth()/3.3), (int)(MainWindow.getHeight()/20));
        L_ONLINE.setBounds((int)(MainWindow.getWidth()/1.5), (int)(MainWindow.getHeight()/4.6), (int)(MainWindow.getWidth()/3.5), (int)(MainWindow.getHeight()/20));

        SP_CONVERSATION.setBounds(MainWindow.getWidth()/50, (int)(MainWindow.getHeight()/3.6), (int)(MainWindow.getWidth()/1.6), (int)(MainWindow.getHeight()/1.8));
        SP_ONLINE.setBounds((int)(MainWindow.getWidth()/1.45), (int)(MainWindow.getHeight()/3.6), (int)(MainWindow.getWidth()/3.8), (int)(MainWindow.getHeight()/1.8));

        TF_Message.setBounds((int)(MainWindow.getWidth()/8), MainWindow.getHeight()/50, (int)(MainWindow.getWidth()/1.92), (int)(MainWindow.getHeight()/10.7));
    }
    
    public static void BuildMainWindow()
    {
        MainWindow.setTitle(UserName + "'s Chat Box");
        MainWindow.setSize(450, 500);
        MainWindow.setLocation(220, 180);
        MainWindow.setResizable(true);
        ConfigureMainWindow();
        MainWindow_Action();
        MainWindow.setVisible(true);
        //MainWindow.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        MainWindow.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        //MainWindow.setDefaultCloseOperation(Exit_Action());
		//dispose().
		
    }
    
    public static void ConfigureMainWindow()
    {
        MainWindow.setBackground(new java.awt.Color(255, 255, 255));
        MainWindow.setSize(500, 320);
        MainWindow.getContentPane().setLayout(null);
        
        B_SEND.setBackground(new java.awt.Color(0, 0, 255));
        B_SEND.setForeground(new java.awt.Color(255, 255, 255));
        B_SEND.setText("SEND");
        MainWindow.getContentPane().add(B_SEND);
        B_SEND.setBounds(250, 40, 81, 25);
        
        B_DISCONNECT.setBackground(new java.awt.Color(0, 0, 255));
        B_DISCONNECT.setForeground(new java.awt.Color(255, 255, 255));
        B_DISCONNECT.setText("DISCONNECT");
        MainWindow.getContentPane().add(B_DISCONNECT);
        B_DISCONNECT.setBounds(10, 40, 110, 25);
        
        B_CONNECT.setBackground(new java.awt.Color(0, 0, 255));
        B_CONNECT.setForeground(new java.awt.Color(255, 255, 255));
        B_CONNECT.setText("CONNECT");
        B_CONNECT.setToolTipText("");
        MainWindow.getContentPane().add(B_CONNECT);
        B_CONNECT.setBounds(130, 40, 110, 25);
        
        B_HELP.setBackground(new java.awt.Color(0, 0, 255));
        B_HELP.setForeground(new java.awt.Color(255, 255, 255));
        B_HELP.setText("HELP");
        MainWindow.getContentPane().add(B_HELP);
        B_HELP.setBounds(420, 40, 70, 25);
        
        B_ABOUT.setBackground(new java.awt.Color(0, 0, 255));
        B_ABOUT.setForeground(new java.awt.Color(255, 255, 255));
        B_ABOUT.setText("ABOUT");
        MainWindow.getContentPane().add(B_ABOUT);
        B_ABOUT.setBounds(340, 40, 75, 25);
        
        L_Message.setText("Message:");
        MainWindow.getContentPane().add(L_Message);
        L_Message.setBounds(10, 10, 60, 20);
        
        TF_Message.setForeground(new java.awt.Color(0, 0, 255));
        TF_Message.requestFocus();
        MainWindow.getContentPane().add(TF_Message);
        TF_Message.setBounds(70, 4, 260, 30);
        
        L_Conversation.setHorizontalAlignment(SwingConstants.CENTER);
        L_Conversation.setText("Conversation");
        MainWindow.getContentPane().add(L_Conversation);
        L_Conversation.setBounds(100, 70, 140, 16);
        
        TA_CONVERSATION.setColumns(20);
        TA_CONVERSATION.setFont(new java.awt.Font("Tahoma", 0, 12));
        TA_CONVERSATION.setForeground(new java.awt.Color(0, 0, 255));
        TA_CONVERSATION.setLineWrap(true);
        TA_CONVERSATION.setRows(5);
        TA_CONVERSATION.setEditable(false);
        
        SP_CONVERSATION.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SP_CONVERSATION.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        SP_CONVERSATION.setViewportView(TA_CONVERSATION);
        MainWindow.getContentPane().add(SP_CONVERSATION);
        SP_CONVERSATION.setBounds(10, 90, 330, 180);
        
        L_ONLINE.setHorizontalAlignment(SwingConstants.CENTER);
        L_ONLINE.setText("Currently Online");
        L_ONLINE.setToolTipText("");
        MainWindow.getContentPane().add(L_ONLINE);
        L_ONLINE.setBounds(350, 70, 130, 16);
        
        //String [] TestNames = {"Bob", "Sue", "Jenny", "Anna"};
        JL_ONLINE.setForeground(new java.awt.Color(0, 0, 255));
        //JL_ONLINE.setListData(TestNames);
        
        SP_ONLINE.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SP_ONLINE.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        SP_ONLINE.setViewportView(JL_ONLINE);
        MainWindow.getContentPane().add(SP_ONLINE);
        SP_ONLINE.setBounds(350, 90, 130, 180);
        
        L_LoggedInAs.setFont(new java.awt.Font("Tahoma", 0, 12));
        L_LoggedInAs.setText("Currently Logged In As");
        MainWindow.getContentPane().add(L_LoggedInAs);
        L_LoggedInAs.setBounds(348, 0, 140, 15);
        
        L_LoggedInAsBox.setHorizontalAlignment(SwingConstants.CENTER);
        L_LoggedInAsBox.setFont(new java.awt.Font("Tahoma", 0, 12));
        L_LoggedInAsBox.setForeground(new java.awt.Color(255, 0, 0));
        L_LoggedInAsBox.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        MainWindow.getContentPane().add(L_LoggedInAsBox);
        L_LoggedInAsBox.setBounds(340, 17, 150, 20);
    }
    
    public static void Login_Action()
    {
        B_ENTER.addActionListener(
                new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ACTION_B_ENTER();
            }
        }
        );
    }
    
    public static void Exit_Action(){
    	
    	ExitWindow.setTitle("Wyjœcie?");
    	ExitWindow.setSize(400, 100);
    	ExitWindow.setLocation(250, 200);
    	ExitWindow.setResizable(false);
        
    	P_Exit = new JPanel();
        P_Exit.add(L_Exit);
        P_Exit.add(B_EXIT_Yes);
        P_Exit.add(B_EXIT_No);

        B_EXIT_Yes.setVisible(true);
        B_EXIT_No.setVisible(true);

        LogInWindow.add(P_LogIn);
        ExitWindow.add(P_Exit);
        ExitWindow.setVisible(true);
        
        B_EXIT_Yes.addActionListener(
        		 new java.awt.event.ActionListener() {
        	            @Override
        	            public void actionPerformed(ActionEvent e) {
        	                ExitWindow.dispose();
        	            	MainWindow.dispose();
	        	            	try {
									if(ifConnected){
										ChatClient.DISCONNECT();
										System.exit(0);
									}
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
	        	                }
        		 	}
        		);
        B_EXIT_No.addActionListener(
       		 new java.awt.event.ActionListener() {
       	            @Override
       	            public void actionPerformed(ActionEvent e) {
       	                ExitWindow.dispose();
       	                }
       		 	}
       		);
    }
    
    public static void ACTION_B_ENTER()
    {
        if(!TF_UserNameBox.getText().equals(""))
        {
            UserName = TF_UserNameBox.getText().trim();
            L_LoggedInAsBox.setText(UserName);
            Serwer.users.add(UserName);
            MainWindow.setTitle(UserName+"'s Chat Box");
            LogInWindow.setVisible(false);
            B_SEND.setEnabled(true);
            B_DISCONNECT.setEnabled(true);
            B_CONNECT.setEnabled(false);
            Connect();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please enter a name!");
        }
    }
    
    public static void MainWindow_Action()
    {
        MainWindow.addWindowListener( 
        		new WindowListener(){

					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub	
					}
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub	
					}
					@Override
					public void windowClosing(WindowEvent e) {
						Exit_Action();	
					}
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub	
					}
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
					}
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
					}
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
					}
        			
        		}
        		
        		);

    	B_SEND.addActionListener(
                new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ACTION_B_SEND();
            }
        }
        );
        
        TF_Message.addKeyListener(
                new java.awt.event.KeyListener() {

          
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER ) {
					ACTION_B_SEND();  
					System.out.println("KUTAS");};
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
        }
        );
        
        TF_UserNameBox.addKeyListener(new java.awt.event.KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					ACTION_B_ENTER();
				};
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		}
        );
        
        B_DISCONNECT.addActionListener(
                new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ACTION_B_DISCONNECT();
            }
        }
        );
        
        B_CONNECT.addActionListener(
                new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	LogInWindow.setVisible(true);
            	BuildLogInWindow();
            }
        }
        );
        
        B_HELP.addActionListener(
                new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ACTION_B_HELP();
            }
        }
        );
        
        B_ABOUT.addActionListener(
                new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ACTION_B_HELP();
            }
        }
        );
    }
    
    public static void ACTION_B_SEND()
    {
        if(!TF_Message.getText().equals(""))
        {
            ChatClient.SEND(TF_Message.getText());
            TF_Message.requestFocus();
        }
    }
    
    public static void ACTION_B_DISCONNECT()
    {
        try
        {
            ChatClient.DISCONNECT();
            B_CONNECT.setEnabled(true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void ACTION_B_HELP()
    {
        JOptionPane.showMessageDialog(null, "Multi-Client CHAT Program");
    }
}
