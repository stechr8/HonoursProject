package businessLayer;

import java.io.IOException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import dataLayer.PredictionDatabaseCommands;

public class PredictionDatabaseLogic {
	
	public void saveToDB(int totalBookings, int cancellations) throws SQLInvalidAuthorizationSpecException, IOException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
		LocalDateTime date = LocalDateTime.now(); 
		String dateString = date.format(formatter);
		
		PredictionDatabaseCommands pdbComms = new PredictionDatabaseCommands();
		
		pdbComms.saveToDB(dateString, totalBookings, cancellations);
		
	}

}
