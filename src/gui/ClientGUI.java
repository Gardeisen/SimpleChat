package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.*;
import common.*;

public class ClientGUI extends JFrame implements ChatIF, ActionListener{

	final public static int DEFAULT_PORT = 5555;

	ChatClient client;

	private JButton[] boutons = new JButton[10];


	public ClientGUI(int id, String host, int port) {
		// Connection
		try {
			client = new ChatClient(id, host, port, this);
		} 
		catch(IOException exception) {
			System.out.println("Error: Can't setup connection!"
					+ " Terminating client.");
			System.exit(1);
		}

		// SetUp
		Container contentPane = getContentPane();
		JPanel panelButtons = new JPanel();
		int n = 5;
		panelButtons.setLayout(new GridLayout(0,n));

		//Settings
		JButton buttonSettings = new JButton("SETTINGS");
		boutons[0]=buttonSettings;
		buttonSettings.setActionCommand("settings");
		buttonSettings.addActionListener(this);
		panelButtons.add(buttonSettings);

		//GetPort
		JButton buttonPort = new JButton("GETPORT");
		boutons[1]=buttonPort;
		buttonPort.setActionCommand("#getport");
		buttonPort.addActionListener(this);
		panelButtons.add(buttonPort);

		//GetHost
		JButton buttonHost = new JButton("GETHOST");
		boutons[2]=buttonPort;
		buttonHost.setActionCommand("#gethost");
		buttonHost.addActionListener(this);
		panelButtons.add(buttonHost);

		//LogOff
		JButton buttonLogOff = new JButton("LOGOFF");
		boutons[3]=buttonLogOff;
		buttonLogOff.setActionCommand("#logoff");
		buttonLogOff.addActionListener(this);
		panelButtons.add(buttonLogOff);

		//Quit
		JButton buttonQuit = new JButton("QUIT");
		boutons[4]=buttonQuit;
		buttonQuit.setActionCommand("#quit");
		buttonQuit.addActionListener(this);
		panelButtons.add(buttonQuit);
		
		//message
		JPanel sendMsge = new JPanel();
		JTextField message = new JTextField();
		sendMsge.setLayout(new GridLayout(0,2));
		sendMsge.add(message);
		JButton buttonSend = new JButton("SEND");
		buttonSend.setActionCommand("send");

		JTextArea chat = new JTextArea();
		contentPane.add(chat,BorderLayout.NORTH);
		buttonSend.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				chat.setText("> "+message.getText()+"\n");
				Object source = e.getSource();
				client.handleMessageFromClientUI(((JButton)source).getActionCommand());
			}
		}
		);
		sendMsge.add(buttonSend);
		//TODO effacer le jtextfield
		//TODO appened
		
		contentPane.add(panelButtons,BorderLayout.SOUTH);
		contentPane.add(sendMsge,BorderLayout.CENTER);

		setTitle("ClientConsole");
		setSize(400, 400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void display(String message) {
		System.out.println("> " + message);
	}

	public static void main(String[] args) {
		int id = 0;
		String host = "";
		int port =0;  //The port number

		try {
			id = Integer.parseInt(args[0]);
			host = args[1];
			port =  Integer.parseInt(args[2]);
		}
		catch(ArrayIndexOutOfBoundsException e) {
			host = "localhost";
			port = DEFAULT_PORT;
		}
		JFrame gui = new ClientGUI(id, host, port);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source.getClass().equals(JButton.class)){
			if(((JButton)source).getActionCommand().equals("settings")){
				//TODO ouvrir fenetre de settings
			}
			else{
				client.handleMessageFromClientUI(((JButton)source).getActionCommand());
			}
		}
	}

}
