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
		
		URL main = PythonLinkLogic.class.getResource("PythonLinkLogic.class");
		File javaFile = new File(main.getPath());

		String absolutePath = javaFile.getAbsolutePath();
		String javaFileFolderPath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
		String binFolderPath = javaFileFolderPath.substring(0,javaFileFolderPath.lastIndexOf(File.separator));
		String homeFileFolderPath = binFolderPath.substring(0,binFolderPath.lastIndexOf(File.separator));
		
		String pythonFilePath = homeFileFolderPath + "\\ModelFolder\\Main.py";
		String csvTrainPath = homeFileFolderPath + "\\ModelFolder\\H2Clean.csv";
		
		try{
			
			ProcessBuilder pb = new ProcessBuilder("python", pythonFilePath, csvPath, csvTrainPath);
			process = pb.start();
			
			InputStream stdout = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stdout,StandardCharsets.UTF_8));
			
			DecimalFormat decFormat = new DecimalFormat("#.##");
			decFormat.setRoundingMode(RoundingMode.HALF_UP);
			
			String line = "";
			while((line = reader.readLine()) != null){
				
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
