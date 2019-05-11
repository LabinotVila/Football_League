import java.net.*;
import java.io.*;

public class Server {
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	public void start (int port)
	{
		try
		{
			serverSocket = new ServerSocket(port);
			clientSocket = serverSocket.accept();
		
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
			
			String greeting = in.readLine();
			System.out.println(greeting);
			out.println("Hello from server to client");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void stop ()
	{
		try
		{
			in.close();
			out.close();
			clientSocket.close();
			serverSocket.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static void main (String[] args)
	{
		Server server = new Server();
		server.start(6666);
	}
}
