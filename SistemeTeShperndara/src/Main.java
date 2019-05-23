import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.SwingConstants;
import javax.swing.JTable;

public class Main extends JFrame {

	private JPanel contentPane;
	private Statement st = null;
	private ResultSet res = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try 
				{
					Main frame = new Main();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Main() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 341, 140);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_notify = new JLabel("");
		lbl_notify.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_notify.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_notify.setBounds(10, 76, 310, 15);
		contentPane.add(lbl_notify);
		
		DefaultTableModel table_model = new DefaultTableModel();
		table_model.addColumn("Team 1");
		table_model.addColumn("Team 2");
		table_model.addColumn("Date");
		table_model.addColumn("Season");
		table_model.addColumn("Week");
		
		JButton btn_server = new JButton("Server");
		btn_server.setBounds(10, 10, 150, 35);
		contentPane.add(btn_server);
		btn_server.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Server.on = !Server.on;
				
				if (Server.on == true)
				{
					Thread start = new Thread(new Server());
					start.start();
					
					lbl_notify.setText("Server started.");
				}
				else
				{
					Server.StopServer();
					lbl_notify.setText("Server stopped.");
				}

			}
		});
		btn_server.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JButton btnShowEverything = new JButton("Show Chat");
		btnShowEverything.setBounds(170, 10, 150, 35);
		contentPane.add(btnShowEverything);
		btnShowEverything.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					if (Server.on == false)
					{
						lbl_notify.setText("Please start the server first, it seems to be stopped!");
					}
					else
					{
						new Client();
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		btnShowEverything.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
	}
}
