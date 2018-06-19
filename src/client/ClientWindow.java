package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ClientWindow {
	
	//Attributes
	private JPanel window;
	private JTextArea textArea;
	private JButton setDetailsButton = new JButton("Set Details");
	private JButton startGameButton = new JButton("Start Game");
	private JButton joinGameButton = new JButton("Join Game");
	private JButton returnButton = new JButton("Return");
	private JButton proceedButton = new JButton("Proceed");
	private String name;
	private int port;
	private String displayString = "Welcome to Russell Leong's Brawler Game!";
	private String[] defaultNames = {"Russell","Cool Mom227","Purepker895","Elfinlocks","1337sp34kr",
			"Qutiedoll","PKMaster0036","Grindxplox","Ironmanbtw","Cow1337killr"};
	private Random rand = new Random();
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JFrame frame;
	
	public ClientWindow(String title) {
		
		//Create and configure the window
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create the content panes
		addComponentToPane(frame.getContentPane());
		
		//Display the window
		frame.setLocation((int)(screenSize.getWidth()/2)-frame.getWidth(), (int)(screenSize.getHeight()/2)-frame.getHeight());
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public void joinServer() {
		String serverAddress = JOptionPane.showInputDialog(
	            "Enter the IP Address of your friend:");
		int serverPort;
		while(true) {
			try {
				serverPort = Integer.parseInt(JOptionPane.showInputDialog(
			            "Enter the Port of your friend:"));
				break;
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "ERROR: " + e1 + "/nTry again!");
				e1.printStackTrace();
			}
		}
		
		try {
			Socket s = new Socket(serverAddress, serverPort);
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			out.println(name);
			BufferedReader input =
		            new BufferedReader(new InputStreamReader(s.getInputStream()));
		    displayString = displayString + "\n" + "Joined Player " + input.readLine() + "'s game!";
		    refresh();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startServer() {
		
		new RunThread().start();
		System.out.println("Server has started...");
		
	}
	
	public class ServerThread extends Thread{

		Socket socket;
		
		ServerThread(Socket socket){
			
			this.socket = socket;
			
		}
		
		public void run(){
			
			try {
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println(name);
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				displayString = displayString + "\nPlayer " + bufferedReader.readLine() + " has joined the game!";
				refresh();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	// With the help of u/g051051 from Reddit
	
	public class RunThread extends Thread {
		
		public void run(){
			
			try {
				ServerSocket serverSocket = new ServerSocket(port);
				System.out.println("Server up and ready for connections...");
				
				while (true) {
					Socket socket = serverSocket.accept();
					new ServerThread(socket).start();
				}
				
			} catch (IOException ioe) {
				
				ioe.printStackTrace();
			}	
			
		}
		
	}
	
	public String getName() {
		return name;
	}
	
	public void refresh() {
		textArea.setText(displayString);
		frame.pack();
	}
	
	public int getPort() {
		return port;
	}
	
	public void setDetails() {
		name = JOptionPane.showInputDialog("Enter your name:");
		int tries = 3;
		while(tries > 0) {
			try {
				port = Integer.parseInt(JOptionPane.showInputDialog("Enter a port number:"));
				tries = 0;
			} catch (NumberFormatException e1) {
				tries --;
				JOptionPane.showMessageDialog(null, e1 + "\nThat's not a number! Try again.\nRemaining tries: " + tries);
				e1.printStackTrace();
				if(tries <= 0) {
					JOptionPane.showMessageDialog(null, "Out of tries! Exiting this loop...");
					tries = -1;
				}
			}
		}
		if(tries!=-1) {
			try {
				displayString = "Name: "+ name + "\nIP: " + InetAddress.getLocalHost() + 
						"\nPort: " + port;
			} catch (UnknownHostException e) {
				JOptionPane.showMessageDialog(null, e);
				e.printStackTrace();
			}
		} else {
			try {
				displayString = "Name: "+ name + "\nIP: " + InetAddress.getLocalHost() + 
						"\nPort: ERROR";
			} catch (UnknownHostException e) {
				JOptionPane.showMessageDialog(null, e);
				e.printStackTrace();
			}
		}
		refresh();
	}
	
	public void startGame() {
		textArea.setText("Starting game. Waiting for players...");
	}
	
	public void addComponentToPane(Container pane) {
		
		//Sets up the thing that will always show up
		JPanel comboBoxPane = new JPanel();
		textArea = new JTextArea();
		textArea.setEditable(false);
		name = defaultNames[rand.nextInt(defaultNames.length)];
		port = rand.nextInt(9999);
		try {
			displayString = "Name: "+ name + "\nIP: " + InetAddress.getLocalHost() + 
					"\nPort: " + port;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		refresh();
		comboBoxPane.add(textArea);
		
		//Menu 1
		//AKA Main Menu
		JPanel menu1 = new JPanel();
		setDetailsButton.addActionListener(setDetailsButtonPressed);
		startGameButton.addActionListener(startGameButtonPressed);
		joinGameButton.addActionListener(joinGameButtonPressed);
		menu1.add(setDetailsButton);
		menu1.add(startGameButton);
		menu1.add(joinGameButton);
		
		//Menu 2
		//AKA Starting Game Menu
		JPanel menu2 = new JPanel();
		returnButton.addActionListener(returnButtonPressed);
		menu2.add(returnButton);
		menu2.add(proceedButton);
		
		//Menu 3
		//AKA Joining Game Menu
		
		//Making these window states
		window = new JPanel(new CardLayout());
		window.add(menu1, "0");
		window.add(menu2, "1");
		
		//Finishing touches
		pane.add(textArea, BorderLayout.PAGE_START);
		pane.add(window, BorderLayout.CENTER);
	}
	
	ActionListener setDetailsButtonPressed = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			setDetails();			
		}
		
	};
	
	ActionListener startGameButtonPressed = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			displayString = displayString + "\n" + "Searching for players...";
			refresh();
			CardLayout c1 = (CardLayout)(window.getLayout());
			c1.show(window, "1");
			startServer();
		}
		
	};
	
	ActionListener returnButtonPressed = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			refresh();
			CardLayout c1 = (CardLayout)(window.getLayout());
			c1.show(window, "0");
			System.out.println("Going back to main menu...");
		}
		
	};
	
	ActionListener joinGameButtonPressed = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			joinServer();
			CardLayout c1 = (CardLayout)(window.getLayout());
			c1.show(window, "2");
		}
		
	};

}
