import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField txt_season;
	private Statement st = null;
	private ResultSet res = null;
	private JTextField txt_week;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() 
	{
		java.sql.Connection conn = Connection.getConnection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_notify = new JLabel("");
		lbl_notify.setBounds(10, 255, 330, 13);
		contentPane.add(lbl_notify);	
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Seasons", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 10, 330, 235);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JComboBox cmb_fetched_seasons = new JComboBox();
		cmb_fetched_seasons.setFont(new Font("Corbel", Font.BOLD, 12));
		cmb_fetched_seasons.setBounds(35, 75, 100, 25);
		panel.add(cmb_fetched_seasons);
		
		
		txt_season = new JTextField();
		txt_season.setFont(new Font("Corbel", Font.BOLD, 12));
		txt_season.setText("Season");
		txt_season.setBounds(35, 25, 100, 25);
		panel.add(txt_season);
		txt_season.setColumns(10);
		
		JButton btn_create_season = new JButton("Create Season");
		btn_create_season.setFont(new Font("Corbel", Font.BOLD, 12));
		btn_create_season.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				int get_txt_season = Integer.parseInt(txt_season.getText());
				
				try
				{
					st = conn.createStatement();
					st.execute("insert into Seasons values ("+get_txt_season+");");
					
					cmb_fetched_seasons.addItem(get_txt_season);
					
					lbl_notify.setText("You have successfully added the season!");
				}
				catch (Exception ex)
				{
					lbl_notify.setText("An error occured while creating season!");
				}
				
			}
		});
		btn_create_season.setBounds(10, 135, 150, 35);
		panel.add(btn_create_season);
		
		JButton btn_delete_season = new JButton("Delete Season");
		btn_delete_season.setFont(new Font("Corbel", Font.BOLD, 12));
		btn_delete_season.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				int get_txt_season = Integer.parseInt(cmb_fetched_seasons.getSelectedItem().toString());
				
				try
				{
					st = conn.createStatement();
					st.execute("delete from Seasons where number = "+get_txt_season+";");
					
					cmb_fetched_seasons.removeItem(cmb_fetched_seasons.getSelectedItem()); // duhet keshtu per arsye se parametri i pare eshte objekt, e jo integer si lart
					
					lbl_notify.setText("You have successfully deleted the week!");
				}
				catch(Exception ex)
				{
					lbl_notify.setText("An error occured while deleting week!");
				}
			}
		});
		
		JButton btn_create_week = new JButton("Create Season Week");
		btn_create_week.setFont(new Font("Corbel", Font.BOLD, 12));
		btn_create_week.setBounds(10, 180, 150, 35);
		panel.add(btn_create_week);
		btn_delete_season.setBounds(170, 135, 150, 35);
		panel.add(btn_delete_season);
		
		JButton btn_delete_week = new JButton("Delete Season Week");
		btn_delete_week.setFont(new Font("Corbel", Font.BOLD, 12));
		btn_delete_week.setBounds(170, 180, 150, 35);
		panel.add(btn_delete_week);
		
		txt_week = new JTextField();
		txt_week.setFont(new Font("Corbel", Font.BOLD, 12));
		txt_week.setText("Week");
		txt_week.setColumns(10);
		txt_week.setBounds(195, 25, 100, 25);
		panel.add(txt_week);
		
		JComboBox cmb_fetched_weeks = new JComboBox();
		cmb_fetched_weeks.setFont(new Font("Corbel", Font.BOLD, 12));
		cmb_fetched_weeks.setBounds(195, 75, 100, 25);
		panel.add(cmb_fetched_weeks);
		
		ResultSet fetched_seasons = Helper.fetchSeasons();
		try
		{
			while (fetched_seasons.next())
			{
				cmb_fetched_seasons.addItem(fetched_seasons.getString(1));
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		ResultSet fetched_weeks = Helper.fetchWeeks(Integer.parseInt(cmb_fetched_seasons.getItemAt(0).toString()));
		try
		{
			while(fetched_weeks.next())
			{
				cmb_fetched_weeks.addItem(fetched_weeks.getString(1));
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		cmb_fetched_seasons.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) 
			{
				Helper.clearComboBox(cmb_fetched_weeks);
				
				int selected_season = Integer.parseInt(cmb_fetched_seasons.getSelectedItem().toString());
				
				ResultSet fetched_weeks = Helper.fetchWeeks(selected_season);
				try
				{
					while(fetched_weeks.next())
					{
						cmb_fetched_weeks.addItem(fetched_weeks.getString(1));
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
	}
}
