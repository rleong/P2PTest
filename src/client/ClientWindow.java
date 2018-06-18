package client;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientWindow {
	
	private JPanel window;
	private JTextArea textArea;
	private JButton setDetailsButton = new JButton("Set Details");
	private JButton startGameButton = new JButton("Start Game");
	private JButton joinGameButton = new JButton("Join Game");
	private String name;
	private int port;
	private String displayString = "Welcome to Russell Leong's Brawler Game!";
	
	public ClientWindow(String title) {
		
		//Create and configure the window
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create the content panes
		addComponentToPane(frame.getContentPane());
		
		//Display the window
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public void refresh() {
		textArea.setText(displayString);
	}
	
	public int getPort() {
		return port;
	}
	
	public void setDetails() {
		name = JOptionPane.showInputDialog("Enter your name:");
		port = Integer.parseInt(JOptionPane.showInputDialog("Enter a port number:"));
		try {
			displayString = "Name: "+ name + "\nIP: " + InetAddress.getLocalHost() + 
					"\nPort:" + port;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		refresh();
	}
	
	public void startGame() {
		textArea.setText("Starting game. Waiting for players...");
	}
	
	public void addComponentToPane(Container pane) {
		JPanel comboBoxPane = new JPanel();
		textArea = new JTextArea();
		textArea.setEditable(false);
		try {
			displayString = "Name: \nIP: " + InetAddress.getLocalHost() + "\nPort:";
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		refresh();
		comboBoxPane.add(textArea);
		
		//Menu 1
		JPanel menu1 = new JPanel();
		setDetailsButton.addActionListener(setDetailsButtonPressed);
		startGameButton.addActionListener(startGameButtonPressed);
		menu1.add(setDetailsButton);
		menu1.add(startGameButton);
		menu1.add(joinGameButton);
		
		//Menu 2
		JPanel menu2 = new JPanel();
		menu2.add(new JTextField("TextField", 20));
		
		window = new JPanel(new CardLayout());
		window.add(menu1, "0");
		window.add(menu2, "1");
		
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
			displayString = "Searching for players...";
			refresh();
			CardLayout c1 = (CardLayout)(window.getLayout());
			c1.show(window, "1");
		}
		
	};

}
