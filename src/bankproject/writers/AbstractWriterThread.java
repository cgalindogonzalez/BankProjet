package bankproject.writers;

import java.io.BufferedWriter;
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
	
	/**
	 * Method to create a new file in \tmp\bank\output
	 * @param fileName
	 */
	public void createOutputFile(String fileName) {
		String pathFile = getOutputTxtFilePath() + System.getProperty("file.separator") + fileName + ".txt";
		File file = new File(pathFile);
		
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to create a file in  \tmp\bank\output with the header
	 * @param fileName
	 * @param headline
	 */
	public void writeHeaderOutputFile (String fileName, String header) {
		String file = getOutputTxtFilePath() + System.getProperty("file.separator") +fileName + ".txt"; 
		try {
			FileWriter output = new FileWriter (file);
			BufferedWriter myBuffer = new BufferedWriter (output);
			myBuffer.write(header);
			myBuffer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
	
}
