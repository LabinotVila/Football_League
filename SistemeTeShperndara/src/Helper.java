import java.sql.ResultSet;
import java.sql.Statement;

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
}
