import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection 
{
	public static java.sql.Connection getConnection()
	{
		try
		{
			java.sql.Connection conn = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/League?useSSL=false", "", "");
			
			return conn;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			
			return null;
		}
	}
}
	