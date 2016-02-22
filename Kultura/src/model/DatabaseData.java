package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;




import com.microsoft.sqlserver.jdbc.SQLServerResultSet;

/**
 * Singleton klasa koja obezbjedjuje rad sa bazom podataka i konekciju na bazu.
 * 
 * @author Grupa 1
 *
 */
public class DatabaseData 
{
	public Connection conn = null;
	private String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	Statement statement;

	private String address;
	private String database;
	@SuppressWarnings("unused")
	private String database_type;
	private String password;
	@SuppressWarnings("unused")
	private String port;
	private String username;
	private String url;	

	public DatabaseData(String address, String database, String database_type, String password,
			String port, String username)
	{
		this.address = address;
		this.database = database;
		this.database_type = database_type;
		this.password = password;
		this.port = port;
		this.username = username;
		this.url = "jdbc:sqlserver://" + this.address;
		
		this.port = "1433"; //postavimo port na 1433
	}
	
	/*public void connect()
	{
		try
		{
			Class.forName(driver);
			Properties props = new Properties();
			props.put("databaseName", this.database);
			props.put("user", this.username);
			props.put("password", this.password.trim());

			this.conn = DriverManager.getConnection(url, props);
			statement = conn.createStatement(SQLServerResultSet.TYPE_SCROLL_INSENSITIVE,
					SQLServerResultSet.CONCUR_READ_ONLY);
			System.out.print("Konekcija uspostavljena: " + (!conn.isClosed()));
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}*/
	//metoda koja vrsi konekciju na bazu
	public void connect() throws SQLException
	{
		if(this.conn == null)
		{
			createConnection();
		}
		else
		{
			if(!this.conn.isValid(5))
			{
				this.conn.close();
				createConnection();
			}
		}
		
	}	
	
	public void createConnection()
	{
		try
		{
			Class.forName(driver);
			Properties props = new Properties();
			props.put("databaseName", this.database);
			props.put("user", this.username);
			props.put("password", this.password.trim());

			this.conn = DriverManager.getConnection(url, props);
			statement = conn.createStatement(SQLServerResultSet.TYPE_SCROLL_INSENSITIVE,
					SQLServerResultSet.CONCUR_READ_ONLY);
			System.out.println("Konekcija uspostavljena: " + (!conn.isClosed()));
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
