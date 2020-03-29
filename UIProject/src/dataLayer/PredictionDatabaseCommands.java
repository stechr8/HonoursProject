package dataLayer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.Properties;

import businessLayer.PropertiesLogic;

public class PredictionDatabaseCommands {
	
	PropertiesLogic pLogic = new PropertiesLogic();
	
	public void saveToDB(String dateOfRun, int totalBookings, int cancellations) throws IOException, SQLInvalidAuthorizationSpecException {
		
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
					.getConnection(prop.getProperty("db.url") + "?user=" + prop.getProperty("db.user") + "&password=" + prop.getProperty("db.password") + "&serverTimezone=UTC");
			
			//create insert query
			String query = "INSERT INTO predictionstable VALUES (NULL, ?, ?, ?)";
						
			// Connect to DB
			PreparedStatement statement = conn.prepareStatement(query);
						
			statement.setString(1, dateOfRun);
			statement.setInt(2, totalBookings);
			statement.setInt(3, cancellations);
			
			statement.executeUpdate();
			
		}
		catch (ClassNotFoundException cnf)
		{
			System.err.println("Could not load driver");
			System.err.println(cnf.getMessage());
		}
		catch (SQLException sqe)
		{
			System.out.println("Error performing SQL Query");
			System.out.println(sqe.getMessage() + "\n" + sqe.getErrorCode());
			
			if(sqe.getErrorCode() == 1045 || sqe.getErrorCode() == 1049) {
				throw new SQLInvalidAuthorizationSpecException("Connection could not be made to the database");
			}
		}
	}

}
