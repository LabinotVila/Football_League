import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

public class Client 
{
	static ArrayList<String> connected = new ArrayList<String>(20);
	
	String nickname;
	Socket clientSocket;
	DataInputStream in;
	DataOutputStream out;
	JTextArea textPane;
	JTextField txt_text;
	JTextArea txt_area;
	DefaultTableModel table_model;
	JTable table_list;
	
	Client() throws UnknownHostException, IOException
	{
		nickname = JOptionPane.showInputDialog("What is your nickname: ");
		
		clientSocket = new Socket ("127.0.0.1", 1234);
		
		in = new DataInputStream(clientSocket.getInputStream());
		out = new DataOutputStream(clientSocket.getOutputStream());
		
		Thread t = new Thread(new Listen());
		t.start();
		
		Frame();
		OnUserJoin();
	}
	
	void OnUserJoin()
	{
		connected.add(nickname);
		SendMessage("* " + nickname + " has joined the chat!");
		SendMessage(":refreshList: :join:");
	}

	void Frame()
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener( new WindowAdapter()
		{
		    public void windowClosing(WindowEvent e)
		    {
		    	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    	
		    	OnUserLeave();
		    }
		});
			
		frame.setBounds(200, 200, 750, 500);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txt_area = new JTextArea();
		txt_area.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txt_area.setLineWrap(true);
        txt_area.setEditable(false);
        txt_area.setWrapStyleWord(true);
		JScrollPane scroll_chat = new JScrollPane(txt_area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll_chat.setBounds(10, 10, 466, 404);
		contentPane.add(scroll_chat);
		
		JButton btn_send = new JButton("Send");
		btn_send.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btn_send.setBounds(366, 424, 110, 30);
		contentPane.add(btn_send);
		
		txt_text = new JTextField();
		txt_text.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txt_text.setBounds(10, 424, 346, 30);
		contentPane.add(txt_text);
		txt_text.setColumns(10);
		
		table_model = new DefaultTableModel();
		table_list = new JTable(table_model);
		table_model.addColumn("Online:");
		
		JScrollPane scroll_list = new JScrollPane(table_list);
		scroll_list.setBounds(486, 10, 246, 444);
		contentPane.add(scroll_list);
		
		JLabel lbl_nickname_as = new JLabel("Current nickname: " + nickname);
		lbl_nickname_as.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_nickname_as.setBounds(486, 10, 240, 15);
		contentPane.add(lbl_nickname_as);
		
		btn_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				SendMessage(nickname + ": " + txt_text.getText());
				
				txt_text.setText("");
				txt_text.requestFocus();
			}
		});
		
		frame.setVisible(true);
	}
	
	void SendMessage(String msg)
	{
		try
		{
			out.writeUTF(msg);
			out.flush();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	void RemoveMeFromList()
	{
		for (int i = 0; i < connected.size(); i++)
		{
			if (connected.get(i).equals(nickname))
			{
				connected.remove(i);
			}
		}
	}
	
	void OnUserLeave()
	{
		try
		{
			RemoveMeFromList();
			SendMessage("* " + nickname + " has left the chat!");
			SendMessage(":refreshList: :left:");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	void RefreshList(String fault)
	{
		table_model.setRowCount(0);
		
		for (int i = 0; i < connected.size(); i++)
		{
			table_model.addRow(new Object[] {connected.get(i)});
		}
		
		table_list.setModel(table_model);
		
		if (fault == ":left")
		{
			try
			{
				clientSocket.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	class Listen implements Runnable
	{
		public void run()
		{
			String nickname;
			while(true)
			{
				try
				{
					String resp = in.readUTF();
					
					if (resp.contains(":refreshList:"))
					{
						String join_left = resp.split(" ")[1];
						RefreshList(join_left);
						continue;
					}
					
					txt_area.append(resp + "\n");
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}

}

