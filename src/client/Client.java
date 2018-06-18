package client;

import java.awt.IllegalComponentStateException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Client {
	
	public static void main(String[] args) {
		
		String serverAddress = JOptionPane.showInputDialog(
	            "Enter IP Address of a machine that is\n" +
	            "running the date service on port 9090:");
		
	}
	
	public static void test() {
		
	}
	
	public static void startServer() throws IOException {
		
		ServerSocket listener = new ServerSocket(9090);
		try {
			while (true) {
				Socket socket = listener.accept();
				try {
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					out.println(new Date().toString());
				} finally {
					socket.close();
				}
			}
		} finally {
			listener.close();
		}
		
	}
	
	public static void startClient() throws UnknownHostException, IOException {
		
		String serverAddress = JOptionPane.showInputDialog(
            "Enter IP Address of a machine that is\n" +
            "running the date service on port 9090:");
        Socket s = new Socket(serverAddress, 9090);
        BufferedReader input =
            new BufferedReader(new InputStreamReader(s.getInputStream()));
        String answer = input.readLine();
        JOptionPane.showMessageDialog(null, answer);
        System.exit(0);
		
	}
	
	public static void startClientConsole() throws UnknownHostException, IOException {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter IP Address of a machine that is\n" +
				"running the date service on port 9090:");
		String serverAddress = scanner.nextLine();
		Socket s = new Socket(serverAddress, 9090);
		BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String answer = input.readLine();
		System.out.println(answer);
		System.exit(0);
		
	}

}
