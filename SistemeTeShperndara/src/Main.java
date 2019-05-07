import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.JTable;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField txt_season;
	private Statement st = null;
	private ResultSet res = null;
	private JTextField txt_week;
	private JTextField txt_team;
	private JTextField txt_date;
	private JTextField txt_show_latest;

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
	
	public void addResultSetIntoComboBox(ResultSet result_set, JComboBox combo_box)
	{
		try
		{
			while (result_set.next())
			{
				combo_box.addItem(result_set.getString(1));
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

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
		lbl_notify.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_notify.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_notify.setBounds(10, 540, 966, 15);
		contentPane.add(lbl_notify);	
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 33, 330, 235);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JComboBox cmb_fetched_seasons = new JComboBox();
		cmb_fetched_seasons.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cmb_fetched_seasons.setBounds(35, 75, 100, 25);
		panel.add(cmb_fetched_seasons);
		
		txt_season = new JTextField();
		txt_season.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txt_season.setText("Season");
		txt_season.setBounds(35, 25, 100, 25);
		panel.add(txt_season);
		txt_season.setColumns(10);
		
		JButton btn_create_season = new JButton("Create Season");
		btn_create_season.setFont(new Font("Tahoma", Font.PLAIN, 12));
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
		btn_delete_season.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btn_delete_season.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				int get_txt_season = Integer.parseInt(cmb_fetched_seasons.getSelectedItem().toString());
				
				try
				{
					st = conn.createStatement();
					st.execute("delete from Seasons where number = "+get_txt_season+";");
					
					cmb_fetched_seasons.removeItem(cmb_fetched_seasons.getSelectedItem()); // duhet keshtu per arsye se parametri i pare eshte objekt, e jo integer si lart
					
					lbl_notify.setText("You have successfully deleted the season!");
				}
				catch(Exception ex)
				{
					lbl_notify.setText("An error occured while deleting season!");
				}
			}
		});
		
		txt_week = new JTextField();
		txt_week.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txt_week.setText("Week");
		txt_week.setColumns(10);
		txt_week.setBounds(195, 25, 100, 25);
		panel.add(txt_week);
		
		JComboBox cmb_fetched_weeks = new JComboBox();
		cmb_fetched_weeks.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cmb_fetched_weeks.setBounds(195, 75, 100, 25);
		panel.add(cmb_fetched_weeks);		
		
		JButton btn_create_week = new JButton("Create Season Week");
		btn_create_week.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				int get_txt_season = Integer.parseInt(cmb_fetched_seasons.getSelectedItem().toString());
				int get_txt_week = Integer.parseInt(txt_week.getText().toString());
				
				try
				{
					st = conn.createStatement();
					st.execute("insert into Weeks values ("+get_txt_week+", "+get_txt_season+");");
					
					cmb_fetched_weeks.addItem(txt_week.getText());
					
					lbl_notify.setText("You have successfully added a week!");
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					
					lbl_notify.setText("An error occured and week could not be added");
				}
			}
		});
		btn_create_week.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btn_create_week.setBounds(10, 180, 150, 35);
		panel.add(btn_create_week);
		btn_delete_season.setBounds(170, 135, 150, 35);
		panel.add(btn_delete_season);
		
		JButton btn_delete_week = new JButton("Delete Season Week");
		btn_delete_week.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				
			}
		});
		btn_delete_week.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btn_delete_week.setBounds(170, 180, 150, 35);
		panel.add(btn_delete_week);		
		
		JLabel lblNewLabel = new JLabel("Season and Week Manager");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 10, 330, 15);
		contentPane.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(10, 301, 330, 235);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JComboBox cmb_fetched_teams = new JComboBox();
		cmb_fetched_teams.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cmb_fetched_teams.setBounds(115, 75, 100, 25);
		panel_1.add(cmb_fetched_teams);
		
		JButton btn_create_team = new JButton("Create Team");
		btn_create_team.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String team_name = txt_team.getText().toString();
				
				try
				{
					st = conn.createStatement();
					st.execute("insert into Teams (name) values ('"+team_name+"');");
					
					cmb_fetched_teams.addItem(team_name);
					
					lbl_notify.setText("Team has been created successfully!");
				}
				catch (Exception ex)
				{
					lbl_notify.setText("An error occured while creating the team!");

					ex.printStackTrace();
				}
			}
		});
		btn_create_team.setBounds(90, 135, 150, 35);
		panel_1.add(btn_create_team);
		btn_create_team.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JButton btn_delete_team = new JButton("Delete Team");
		btn_delete_team.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String selected_item = cmb_fetched_teams.getSelectedItem().toString();
				
				try
				{
					st = conn.createStatement();
					st.execute("delete from Teams where name='"+selected_item+"';");
					
					lbl_notify.setText(selected_item + " has successfully been deleted!");
					
					cmb_fetched_teams.removeItem(cmb_fetched_teams.getSelectedItem());
				}
				catch(Exception ex)
				{
					lbl_notify.setText("An error occured while deleting a team!");
					
					ex.printStackTrace();
				}
			}
		});
		btn_delete_team.setBounds(90, 180, 150, 35);
		panel_1.add(btn_delete_team);
		btn_delete_team.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		txt_team = new JTextField();
		txt_team.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txt_team.setText("Team Name");
		txt_team.setBounds(115, 25, 100, 25);
		panel_1.add(txt_team);
		txt_team.setColumns(10);
		
		JLabel lblTeamManager = new JLabel("Teams Manager");
		lblTeamManager.setHorizontalAlignment(SwingConstants.CENTER);
		lblTeamManager.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTeamManager.setBounds(10, 278, 330, 15);
		contentPane.add(lblTeamManager);
		
		ResultSet fetched_seasons = Helper.fetchSeasons();
		addResultSetIntoComboBox(fetched_seasons, cmb_fetched_seasons);
		
		ResultSet fetched_weeks = Helper.fetchWeeks(Integer.parseInt(cmb_fetched_seasons.getItemAt(0).toString()));
		addResultSetIntoComboBox(fetched_weeks, cmb_fetched_weeks);
		
		addResultSetIntoComboBox(Helper.fetchTeams(), cmb_fetched_teams);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(350, 33, 626, 235);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btn_add_game = new JButton("Add Game");
		btn_add_game.setBounds(238, 152, 150, 35);
		panel_2.add(btn_add_game);
		btn_add_game.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JComboBox cmb_team_one = new JComboBox();
		cmb_team_one.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cmb_team_one.setBounds(76, 33, 150, 25);
		panel_2.add(cmb_team_one);
		addResultSetIntoComboBox(Helper.fetchTeams(), cmb_team_one);
		
		JComboBox cmb_team_two = new JComboBox();
		cmb_team_two.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cmb_team_two.setBounds(238, 33, 150, 25);
		panel_2.add(cmb_team_two);
		addResultSetIntoComboBox(Helper.fetchTeams(), cmb_team_two);
		
		txt_date = new JTextField();
		txt_date.setText("YYYY-MM-DD");
		txt_date.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txt_date.setBounds(400, 33, 150, 25);
		panel_2.add(txt_date);
		txt_date.setColumns(10);
		
		JLabel lblTeam = new JLabel("Team 1");
		lblTeam.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTeam.setBounds(76, 10, 150, 14);
		panel_2.add(lblTeam);
		lblTeam.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblTeam_1 = new JLabel("Team 2");
		lblTeam_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTeam_1.setBounds(238, 11, 150, 13);
		panel_2.add(lblTeam_1);
		lblTeam_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDate.setBounds(400, 11, 150, 13);
		panel_2.add(lblDate);
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		
		JComboBox cmb_season = new JComboBox();
		cmb_season.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cmb_season.setBounds(138, 105, 100, 25);
		panel_2.add(cmb_season);
		addResultSetIntoComboBox(Helper.fetchSeasons(), cmb_season);
		
		JComboBox cmb_week = new JComboBox();
		cmb_week.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cmb_week.setBounds(388, 105, 100, 25);
		panel_2.add(cmb_week);
		addResultSetIntoComboBox(Helper.fetchWeeks(Integer.parseInt(cmb_fetched_seasons.getItemAt(0).toString())), cmb_week);
		
		JLabel lblSeason = new JLabel("Season");
		lblSeason.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSeason.setBounds(138, 82, 100, 13);
		panel_2.add(lblSeason);
		lblSeason.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblWeek = new JLabel("Week");
		lblWeek.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblWeek.setBounds(388, 82, 100, 13);
		panel_2.add(lblWeek);
		lblWeek.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblMatchesManager = new JLabel("Matches Manager");
		lblMatchesManager.setHorizontalAlignment(SwingConstants.CENTER);
		lblMatchesManager.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMatchesManager.setBounds(350, 10, 626, 15);
		contentPane.add(lblMatchesManager);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBounds(350, 301, 626, 235);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JButton btnShowEverything = new JButton("Show Everything");
		btnShowEverything.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnShowEverything.setBounds(466, 10, 150, 35);
		panel_3.add(btnShowEverything);
		
		txt_show_latest = new JTextField();
		txt_show_latest.setText("Team Name");
		txt_show_latest.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txt_show_latest.setColumns(10);
		txt_show_latest.setBounds(10, 10, 100, 35);
		panel_3.add(txt_show_latest);
		
		JButton btn_show_latest = new JButton("Show Latest");

		btn_show_latest.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btn_show_latest.setBounds(120, 10, 150, 35);
		panel_3.add(btn_show_latest);
		
		DefaultTableModel table_model = new DefaultTableModel();
		table_model.addColumn("Team 1");
		table_model.addColumn("Team 2");
		table_model.addColumn("Date");
		table_model.addColumn("Season");
		table_model.addColumn("Week");
		
		JTable table = new JTable(table_model);
		
		JScrollPane latest_scroll = new JScrollPane(table);
		latest_scroll.setBounds(10,55,606,170);
		panel_3.add(latest_scroll);
		
		
		JLabel lblDisplayLatestAdditions = new JLabel("Display Latest Additions");
		lblDisplayLatestAdditions.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisplayLatestAdditions.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDisplayLatestAdditions.setBounds(350, 278, 626, 15);
		contentPane.add(lblDisplayLatestAdditions);
		
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
		cmb_season.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) 
			{
				Helper.clearComboBox(cmb_week);
				
				int selected_season = Integer.parseInt(cmb_season.getSelectedItem().toString());
				
				ResultSet fetched_weeks = Helper.fetchWeeks(selected_season);
				try
				{
					while(fetched_weeks.next())
					{
						cmb_week.addItem(fetched_weeks.getString(1));
					}
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		// ADD GAME button handler
		btn_add_game.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String team_one = cmb_team_one.getSelectedItem().toString();
				String team_two = cmb_team_two.getSelectedItem().toString();
				String team_one_id = null;
				String team_two_id = null;
				String date = txt_date.getText();
				int season = Integer.parseInt(cmb_season.getSelectedItem().toString());
				int week = Integer.parseInt(cmb_week.getSelectedItem().toString());
				
				try
				{
					st = conn.createStatement();
					ResultSet loop_teams = st.executeQuery("select * from teams");
					while (loop_teams.next())
					{
						String returned_team = loop_teams.getString(2);
						
						if (returned_team.equals(team_one))
							team_one_id = loop_teams.getString(1);
						
						if (returned_team.equals(team_two))
							team_two_id = loop_teams.getString(1);
					}

				    try
					{
				    	st = conn.createStatement();
						st.execute("insert into Matches (first_team, second_team, date, week_id, season_id) values ("+team_one_id+", "+team_two_id+", '"+date+"', "+week+", "+season+")");
						
						lbl_notify.setText("You have successfully added the game!");
					}
				    catch (Exception ex)
				    {
				    	ex.printStackTrace();
				    	
				    	lbl_notify.setText("Please fill the information correctly!");
				    }
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
	
		// SHOW LATEST button handler, show latest games
		btn_show_latest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				// Empty the table model before adding new content
				for (int i = 0; i < table_model.getRowCount(); i++)
				{
					table_model.removeRow(i);
				}
				
				
				
				try
				{
					int latest_input = Integer.parseInt(txt_show_latest.getText().toString());
					
					st = conn.createStatement();
					ResultSet latest = st.executeQuery("select first_team, second_team, date, week_id, season_id from Matches limit "+latest_input+";");
					
					while (latest.next())
					{
						String team_one = latest.getString(1);
						String team_two = latest.getString(2);
						String date = latest.getString(3);
						String week = latest.getString(4);
						String season = latest.getString(5);
						
						table_model.addRow(new Object[] {team_one, team_two, date, week, season});
					}
					
					lbl_notify.setText("You have successfully shown "+latest_input+" latest games!");
				}
				catch (Exception ex)
				{
					lbl_notify.setText("Please enter a valid number!");
				}
			}
		});
	}
}
