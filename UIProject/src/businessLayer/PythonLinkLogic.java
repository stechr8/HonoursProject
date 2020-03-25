package businessLayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PythonLinkLogic {

	public ArrayList<String> executePythonScript(String csvPath) {

		Process process;
		ArrayList<String> predictionArrayList = new ArrayList<String>();
		
		URL main = PythonLinkLogic.class.getResource("PythonLinkLogic.class");
		File javaFile = new File(main.getPath());

		String absolutePath = javaFile.getAbsolutePath();
		String javaFileFolderPath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
		
		String pythonFilePath = javaFileFolderPath + "\\ModelFolder\\Model.py";
		String csvTrainPath = javaFileFolderPath + "\\ModelFolder\\H2Clean.csv";
		
		try{
			
			ProcessBuilder pb = new ProcessBuilder("python", pythonFilePath, csvPath, csvTrainPath);
			process = pb.start();
			
			InputStream stdout = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stdout,StandardCharsets.UTF_8));
			
			String line = "";
			while((line = reader.readLine()) != null){
				System.out.println(line);
			}
		}catch(Exception e) {
			System.out.println("Exception Raised" + e.toString());
		}

		return null;
	}

}
