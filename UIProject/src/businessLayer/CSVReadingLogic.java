package businessLayer;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;

public class CSVReadingLogic {
	
	public ArrayList<String[]> readCsvForTable(String path) throws IOException {

			FileReader fReader = new FileReader(new File(path));
            BufferedReader buffer = new BufferedReader(fReader);
            ArrayList<String[]> elements = new ArrayList<String[]>();
            String line = null;
            buffer.readLine();
            while((line = buffer.readLine())!=null) {
                String[] splitValues = line.split(",");
                elements.add(splitValues);
            }
            buffer.close();
            
            return elements;
		
	}
	
	

}
