import java.net.*;
import java.io.*;

public class Server {
	private ServerSocket serverSocket;
	private Socket clientSocket;
	public void start (int port) throws IOException
	{
		serverSocket = new ServerSocket(port);
		System.out.println("Server started");
		
		while (true)
			new HandleClient(serverSocket.accept()).start();
	}
	
	public void stop() throws IOException
	{
		serverSocket.close();
	}
	
	public static class HandleClient extends Thread
	{
		private Socket clientSocket;
		private BufferedReader in;
		private PrintWriter out;
		
		public HandleClient(Socket socket)
		{
			this.clientSocket = socket;
		}
		
		public void run()
		{
			try
			{
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));	
				
				out.println(in.readLine());
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	
	public static void main (String[] args) throws IOException
	{
		Server server = new Server();
		server.start(6666);
	}
}