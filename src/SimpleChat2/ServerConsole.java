import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import server.EchoServer;
import common.ChatIF;


public class ServerConsole implements ChatIF {

	EchoServer server;

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	ServerConsole(int port) throws IOException {
		server = new EchoServer(port, this);
		server.listen();
	}

	/**
	 * This method waits for input from the console.  Once it is 
	 * received, it sends it to the client's message handler.
	 */
	public void accept() {
		try {
			BufferedReader fromConsole = 
					new BufferedReader(new InputStreamReader(System.in));
			String message;

			while (true) 
			{
				message = fromConsole.readLine();
				server.handleMessageFromServerUI(message);
			}
		} 
		catch (Exception ex) 
		{
			display("Unexpected error while reading from console!");
		}
	}

	@Override
	public void display(String message) {
		System.out.println("> " + message);
	}

	public static void main(String[] args) {
		int port =0;  //The port number

		try {
			port =  Integer.parseInt(args[0]);
		}
		catch(ArrayIndexOutOfBoundsException e) {
			port = DEFAULT_PORT;
		}
		ServerConsole console;
		try {
			console = new ServerConsole(port);
			console.accept();  //Wait for console data
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
