package bankproject.readers;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AbstractReaderThread extends Thread {

	/**
	 * method to obtain (or create and obtain if doesn't exist) the path: \tmp\bank\input 
	 * @return pathDir
	 */
	public static String getInputTxtFilePath() {
		String pathDir = System.getProperty("file.separator") + "tmp" + System.getProperty("file.separator") + "bank" + System.getProperty("file.separator") + "input";
		
		File dir = new File(pathDir); 
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		return pathDir;
	}
	
	/**
	 * method to read a file 
	 * @param fileName
	 */
	public void readInputFile(String fileName) {
		String file = getInputTxtFilePath() + fileName + ".txt";
		try {
			FileReader input = new FileReader (file);
			int c = 0;
			while(c!=-1) {
				c = input.read();
				char letter = (char)c;
				System.out.println(letter);
			}
			
			input.close();
			
		} catch (IOException e) {
			System.out.println("File not found");
		}
	}
	
	
}
