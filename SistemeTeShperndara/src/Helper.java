import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JComboBox;

public class Helper 
{
	static java.sql.Connection conn = Connection.getConnection();
	
	public static ResultSet fetchSeasons()
	{
		try 
		{
			Statement st = conn.createStatement();	
			ResultSet res = st.executeQuery("select * from Seasons;");
			
			return res;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			
			return null;
		}
	}
	
	public static ResultSet fetchWeeks(int from_season)
	{
		try 
		{
			Statement st = conn.createStatement();	
			ResultSet res = st.executeQuery("select * from Weeks where season="+from_season+";");
			
			return res;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			
			return null;
		}
	}
	
	public static ResultSet fetchTeams()
	{
		try
		{
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("select name from Teams;");
			
			return res;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			
			return null;
		}
	}
	
	public static void clearComboBox (JComboBox combobox)
	{
		for (int i = 0; i < combobox.getItemCount(); i++)
		{
			combobox.removeItemAt(i);
		}
	}

}
