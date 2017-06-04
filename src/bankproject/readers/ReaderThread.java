package bankproject.readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReaderThread extends Thread {

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
	 * method to read a text file and put the words in a list
	 * @param fileName
	 * @return allWords
	 */
	public static List<String> readInputFile(String fileName) {
		String file = getInputTxtFilePath() + System.getProperty("file.separator") +fileName + ".txt";
		List<String> allWords = new ArrayList<String>();
		try {
			FileReader input = new FileReader (file);
			BufferedReader myBuffer = new BufferedReader (input);
			String line = myBuffer.readLine();
			
			while(line != null) {
				String[] word = line.split("\t\t\t");
				if (line!=null)
					for(int i = 0; i<word.length; i ++) {
						if((word[i]!=null)&&(word[i]!="\n"))
						allWords.add(word[i]);	
					}
				line = myBuffer.readLine();			
		    
			}
			
			myBuffer.close();
			
		} catch (IOException e) {
			System.out.println("File not found");
		}
		return allWords;
		
	}
	
	/**
	 * Method to delete a file 
	 * @param fileName
	 */
	public void deleteInputFile(String fileName) {
		String pathFile = getInputTxtFilePath() + fileName + ".txt";
		File file = new File(pathFile);
		file.delete();
	}
	
}
