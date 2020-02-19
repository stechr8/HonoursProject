package businessLayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

import applicationLayer.SignInFrame;

public class PropertiesLogic {
	
	public void writePropertiesFile(String url, String username, String password) {

		try {
			
			EncryptionLogic encLogic = new EncryptionLogic();
			
			//String encUrl = encLogic.encrypt("jdbc:mysql://" + url);
			//String encUsername = encLogic.encrypt(username);
			//String encPassword = encLogic.encrypt(password);

			Properties prop = new Properties();

			// set the properties value
			
			//TODO set up encryption
			prop.setProperty("db.url", url);
			prop.setProperty("db.user", username);
			prop.setProperty("db.password", password);

			OutputStream output = new FileOutputStream(getPropertiesFilePath());

			// save properties to project root folder
			prop.store(output, null);

			System.out.println(prop);

		} catch (IOException io) {
			io.printStackTrace();
		}

	}
	
	//locates and returns the path of the properties file
	public String getPropertiesFilePath() {
		
		URL main = SignInFrame.class.getResource("SignInFrame.class");
		File javaFile = new File(main.getPath());

		String absolutePath = javaFile.getAbsolutePath();
		String javaFileFolderPath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
		String dbFilePath = javaFileFolderPath+"\\db.properties";
		
		return dbFilePath;
	}

}
