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
	
	public static ResultSet fetchWeeks(int week)
	{
		try 
		{
			Statement st = conn.createStatement();	
			ResultSet res = st.executeQuery("select * from Weeks where season="+week+";");
			
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
