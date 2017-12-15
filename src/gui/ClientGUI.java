package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.*;
import common.*;

public class ClientGUI extends JFrame implements ChatIF, ActionListener {

	final public static int DEFAULT_PORT = 5555;
	private static int id;
	
	ChatClient client;

	private JButton[] boutons = new JButton[10];
	JTextArea chat;


	public ClientGUI(int id, String host, int port) {
		
		setTitle("ClientConsole");
		setSize(500, 600);
		int windowWidth = (int) this.getSize().getWidth();
		int windowHeight = (int) this.getSize().getHeight();
		
		// SetUp
		Container contentPane = getContentPane();
			
		// Display messages
		chat = new JTextArea();
		JScrollPane scroll = new JScrollPane(chat, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//scroll.setMinimumSize(new Dimension(windowWidth, windowHeight/2));
		chat.setSize(new Dimension(windowWidth, windowHeight/2));
		contentPane.add(scroll,BorderLayout.NORTH);
		
		//message
		JPanel sendMsge = new JPanel();
		sendMsge.setLayout(new GridLayout(1,2));
		sendMsge.setMaximumSize(new Dimension(windowWidth, windowHeight/8));

		JTextField message = new JTextField();
		int size = sendMsge.getWidth();
		System.out.println("Size : " + 3*size/4);
		message.setMaximumSize(new Dimension((int) 3*sendMsge.getWidth()/4, sendMsge.getHeight()));
		sendMsge.add(message, BorderLayout.WEST);
		JButton buttonSend = new JButton("Send");
		buttonSend.setActionCommand("send");

		buttonSend.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				chat.append("> "+message.getText()+"\n");
				Object source = e.getSource();
				client.handleMessageFromClientUI(message.getText());
			}
		});
		buttonSend.setMaximumSize(new Dimension(sendMsge.getWidth()/4, sendMsge.getHeight()));
		sendMsge.add(buttonSend, BorderLayout.EAST);
		contentPane.add(sendMsge,BorderLayout.CENTER);
		//TODO effacer le jtextfield
		//TODO appened
		
		
		JPanel panelButtons = new JPanel();
		int n = 5;
		panelButtons.setLayout(new GridLayout(0,n));
		panelButtons.setMaximumSize(new Dimension(windowWidth, windowHeight/8));

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
		contentPane.add(panelButtons,BorderLayout.SOUTH);
		
		// Connection
		try {
			client = new ChatClient(id, host, port, this);
		} 
		catch(IOException exception) {
			System.out.println("Error: Can't setup connection!"
					+ " Terminating client.");
			System.exit(1);
		}

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void display(String message) {
		if (!message.contains("Client " + id)) {
			chat.append(message + "\n");
		}
	}	

	public static void main(String[] args) {
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
				//chat.append();
			}
		}
	}
}
