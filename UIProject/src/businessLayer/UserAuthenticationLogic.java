package businessLayer;

import java.io.FileNotFoundException;

import org.springframework.security.crypto.bcrypt.BCrypt;

import dataLayer.UserAuthDatabaseCommands;

public class UserAuthenticationLogic {
	
	UserAuthDatabaseCommands userAuthDB = new UserAuthDatabaseCommands();
	
	public boolean signInUser(String inputUsername, String inputPassword) throws Exception, FileNotFoundException{
		
				String savedPassword = userAuthDB.getUserPassword(inputUsername);

				if(savedPassword == null) {
					throw new Exception("Invalid Sign In");
				}
		         
		        boolean matched = BCrypt.checkpw(inputPassword, savedPassword);
				
				return matched;
		
	}
	
	public void registerUser(String username, String password) {
		
		String generatedSecuredPasswordHash = BCrypt.hashpw(password, BCrypt.gensalt(12));
		
	}

}
