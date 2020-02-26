package dataLayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import businessLayer.PropertiesLogic;

public class UserAuthDatabaseCommands{

	PropertiesLogic pLogic = new PropertiesLogic();

	public String getUserPassword(String suppliedUsername) throws FileNotFoundException{
		
		String password = null;

		try {

			Properties prop = new Properties();

			InputStream inputStream = new FileInputStream(pLogic.getPropertiesFilePath());

			// load a properties file
			prop.load(inputStream);

			// get value by key
			prop.getProperty("db.url");
			prop.getProperty("db.user");
			prop.getProperty("db.password");

			// Load the driver
			Class.forName("com.mysql.jdbc.Driver");

			//Establish connection to the database
			Connection conn = DriverManager
					.getConnection(prop.getProperty("db.url") + "?user=" + prop.getProperty("db.user") + "&password=" + prop.getProperty("db.password"));

			// Create query to get the product from the database matching the product id
			String query = "SELECT password FROM userstable WHERE username = ?";
			
			// Connect to DB
			PreparedStatement statement = conn.prepareStatement(query);
			
			statement.setString(1, suppliedUsername);

			// And then get the results from executing the query
			ResultSet results = statement.executeQuery();

			//take the first row
			results.first();

			password = results.getString("password");

		}
		catch (ClassNotFoundException cnf)
		{
			System.err.println("Could not load driver");
			System.err.println(cnf.getMessage());
		}
		catch (SQLException sqe)
		{
			System.out.println("Error performing SQL Query");
			System.out.println(sqe.getMessage());
		}
		catch(IOException e) {

			System.out.println("IOException raised");
			System.err.println(e.getMessage());

		}

		return password;
	}

}
