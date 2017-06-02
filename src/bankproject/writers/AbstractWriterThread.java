package bankproject.writers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AbstractWriterThread extends Thread {

	/**
	 * method to obtain (or create and obtain if doesn't exist) the path: \tmp\bank\output 
	 * @return pathDir
	 */
	public static String getOutputTxtFilePath() {
		String pathDir = System.getProperty("file.separator") + "tmp" + System.getProperty("file.separator") + "bank" + System.getProperty("file.separator") + "output";
		
		File dir = new File(pathDir); 
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		return pathDir;
	}
	
	public void writeOutputFile (String fileName) {
		String sentence = "prueba";
		String file = getOutputTxtFilePath() + fileName + ".txt"; 
		try {
			FileWriter output = new FileWriter (file);
			for(int i=0; i<sentence.length(); i++) {
				output.write(sentence.charAt(i));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
	
}
