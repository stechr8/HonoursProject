package businessLayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PythonLinkLogic {

	public ArrayList<String> executePythonScript(String csvPath) {

		Process process;
		ArrayList<String> predictionArrayList = new ArrayList<String>();
		
		//sort through directories by starting at the location of this class
		
		URL main = PythonLinkLogic.class.getResource("PythonLinkLogic.class");
		File javaFile = new File(main.getPath());

		String absolutePath = javaFile.getAbsolutePath();
		String javaFileFolderPath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
		String binFolderPath = javaFileFolderPath.substring(0,javaFileFolderPath.lastIndexOf(File.separator));
		String homeFileFolderPath = binFolderPath.substring(0,binFolderPath.lastIndexOf(File.separator));
		
		//append the desired directories and files
		
		String pythonFilePath = homeFileFolderPath + "\\ModelFolder\\Main.py";
		String csvTrainPath = homeFileFolderPath + "\\ModelFolder\\H2Clean.csv";
		
		try{
			
			//construct process with necessary arguments to run Python model via command line
			ProcessBuilder pb = new ProcessBuilder("python", pythonFilePath, csvPath, csvTrainPath);
			process = pb.start();
			
			//construct BufferedReader to read output from Python application
			InputStream stdout = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stdout,StandardCharsets.UTF_8));
			
			//format output to 2 decimal places
			DecimalFormat decFormat = new DecimalFormat("#.##");
			decFormat.setRoundingMode(RoundingMode.HALF_UP);
			
			String line = "";
			while((line = reader.readLine()) != null){
				
				//read in the Python output and format it
				line = line.replaceAll("[\\[\\]]", "");
				line = line.trim();
				Double dblLine = Double.parseDouble(line) * 100;
				predictionArrayList.add(decFormat.format(dblLine));
				
			}
			return predictionArrayList;
			
		}catch(Exception e) {
			System.out.println("Exception Raised" + e.toString());
		}

		return null;
	}

}
