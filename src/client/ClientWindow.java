package client;

import javax.swing.JFrame;

public class ClientWindow {
	
	//Attributes
	private JFrame frame;
	
	public ClientWindow(String title) {
		
		//Create and configure the window
		frame = new JFrame(title);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
	}

}
