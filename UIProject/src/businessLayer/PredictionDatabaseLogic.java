package businessLayer;

import java.io.IOException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import classes.PredictionClass;
import dataLayer.PredictionDatabaseCommands;

public class PredictionDatabaseLogic {
	
	PredictionDatabaseCommands pdbComms = new PredictionDatabaseCommands();
	
	public void saveToDB(int totalBookings, int cancellations) throws SQLInvalidAuthorizationSpecException, IOException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
		LocalDateTime date = LocalDateTime.now(); 
		String dateString = date.format(formatter);
		
		pdbComms.saveToDB(dateString, totalBookings, cancellations);
		
	}
	
	public PredictionClass getLatestPred() throws SQLInvalidAuthorizationSpecException, IOException {
		
		PredictionClass pred = pdbComms.getLatestPred();
		
		return pred;
		
	}

}
