package main;

import java.io.File;
import java.io.IOException;

import checkers.Checker;
import parameters.GlobalParameters;
import parameters.GlobalParametersReader;

public class Main {

	/**
	 * Main method.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		//1. Read the config file:
		
			GlobalParametersReader.initialize("./config/parametersGlobal.xml");
		
		//2. Reads all the files in the solutions folder:
			
			File folder = new File(GlobalParameters.SOLUTIONS_FOLDER);
			
			File[] listOfFiles = folder.listFiles();
			
		//3. Checks one by one:
			
			for (int i = 0; i < listOfFiles.length; i++) { 
				
				  if (listOfFiles[i].isFile()) {
					  if(listOfFiles[i].getName().contains(".txt")) {
						  new Checker(listOfFiles[i].getName());
					  }
					 
				  }
			}
	}
	
}
