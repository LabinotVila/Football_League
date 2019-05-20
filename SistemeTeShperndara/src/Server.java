import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server
{
	static Vector<ClientHandler> ar = new Vector<>();
	
	public static void main (String[] args)
	{
		try
		{
			ServerSocket server = new ServerSocket(1234);
			
			Socket client;
			while (true)
			{
				client = server.accept();
				
				System.out.println("Client conneceted with port: " + client.getPort());
				
				DataInputStream in = new DataInputStream(client.getInputStream());
				DataOutputStream out = new DataOutputStream(client.getOutputStream());
				
				ClientHandler mtch = new ClientHandler(client, in, out);
				
				Thread t = new Thread(mtch);
				ar.add(mtch);
				
				t.start();
			}
	
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	static void Broadcast(String msg) throws IOException
	{
		for (int i = 0; i < ar.size(); i++)
		{
			try
			{
				DataOutputStream out = new DataOutputStream(ar.get(i).client.getOutputStream());
			
				out.writeUTF(msg);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
}

class ClientHandler implements Runnable
{
	Socket client;
	final DataInputStream in;
	final DataOutputStream out;
	
	public ClientHandler(Socket client, DataInputStream in, DataOutputStream out)
	{
		this.client = client;
		this.in = in;
		this.out = out;
	}
		
	public void run()
	{
		String received;
		while (true)
		{
			try
			{
				received = in.readUTF();
				
				Server.Broadcast(received);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
}