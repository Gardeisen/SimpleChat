package gui;

import javax.swing.JFrame;

public class ClientGUI extends JFrame {

	public ClientGUI() {
		setTitle("ClientConsole");
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		JFrame gui = new ClientGUI();
	}

}
