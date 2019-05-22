import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class Server implements Runnable
{
	static public Vector<ClientHandler> ar = new Vector<>();
	static public boolean on = false;
	static ServerSocket server;
	
	public void run ()
	{
		try
		{
			server = new ServerSocket(1234);
			Server.on = true;
			
			Socket client;
			while (true)
			{
				try
				{
					client = server.accept();
					
					DataInputStream in = new DataInputStream(client.getInputStream());
					DataOutputStream out = new DataOutputStream(client.getOutputStream());
					
					ClientHandler mtch = new ClientHandler(client, in, out);
					
					Thread t = new Thread(mtch);
					ar.add(mtch);
					
					t.start();
				}
				catch (Exception ex)
				{
					
				}
			}
	
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	static void StopServer()
	{
		try 
		{
			server.close();
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
				DataOutputStream out = ar.get(i).out;
			
				out.writeUTF(msg);
				out.flush();
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