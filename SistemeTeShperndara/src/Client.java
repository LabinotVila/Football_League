import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class Client 
{
	String nickname;
	Socket clientSocket;
	DataInputStream in;
	DataOutputStream out;
	JTextArea textPane;
	JTextField txt_text;
	JTextArea txt_area;
	
	Client() throws UnknownHostException, IOException
	{
		nickname = JOptionPane.showInputDialog("What is your nickname: ");
		
		clientSocket = new Socket ("127.0.0.1", 1234);
		
		in = new DataInputStream(clientSocket.getInputStream());
		out = new DataOutputStream(clientSocket.getOutputStream());
		
		Thread t = new Thread(new Listen());
		t.start();
		
		Frame();
	}

	void Frame()
	{
		JFrame frame = new JFrame();
		frame.setBounds(200, 200, 500, 500);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txt_area = new JTextArea();
		txt_area.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txt_area.setLineWrap(true);
        txt_area.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(txt_area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(10, 10, 466, 404);
		contentPane.add(scroll);
		
		JButton btn_send = new JButton("Send");
		btn_send.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btn_send.setBounds(366, 424, 110, 30);
		contentPane.add(btn_send);
		
		
		txt_text = new JTextField();
		txt_text.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txt_text.setBounds(10, 424, 346, 30);
		contentPane.add(txt_text);
		txt_text.setColumns(10);
		
		JLabel lbl_nickname_as = new JLabel("Current nickname: " + nickname);
		lbl_nickname_as.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_nickname_as.setBounds(486, 10, 240, 15);
		contentPane.add(lbl_nickname_as);
		
		btn_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					out.writeUTF(nickname + ": " + txt_text.getText());
					out.flush();
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				}
			}
		});
		
		frame.setVisible(true);
	}
	
	class Listen implements Runnable
	{
		public void run()
		{
			while(true)
			{
				try
				{
					String resp = in.readUTF();
					
					txt_area.append(resp);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}

}

