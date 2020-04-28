//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////

//

// Title:           ateam project - Milk Weights

// Files:           GUI, FileManager, Farm, CheeseFactory, DataManager

// Course:          CS400 Spring 2020

//

// Author:          Garrett Rodgers and William Braun

// Email:           gcrodgers@wisc.edu
//                  wmbraun@wisc.edu

// Lecturer's Name: Deb Deppler

//

/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * This class manages the csv files for the Milk Weight
 * project
 * 
 * @author garrett rodgers and William Braun
 *
 */
public class FileManager {
  public String inputFile; //the file coming in
  public String outputFile; //the file going out
  
  /**
   * Constructor that takes the file path and sets it to the String
   * inputFile parameter
   * @param inputFile
   */
  public FileManager(String inputFile) {
    this.inputFile = inputFile;
  }
  
  /**
   * The method reads the content from the file and handles it accordingly
   * 
   * @return true if read correctly
   */
  public boolean readFile() {
	CheeseFactory factory = new CheeseFactory();
	BufferedReader fileReader = null;  
	String currentLine = "";
	String splitMark = ",";
	String date = "";
	String farmID = "";
	String milkWeight = "";
	
	//try to read from the provided file
	try {
	  //create a new BufferedReader from the file that was input
	  fileReader = new BufferedReader(new FileReader(inputFile));
	  //skip the first line (this contains a key)
	  fileReader.readLine();
	  
	  //go through each line of the file
	  while((currentLine = fileReader.readLine()) != null) {
	    String[] info = currentLine.split(splitMark);
	    date += info[0];
	    farmID += info[1];
	    milkWeight += info[2];
	    
	    factory.insertSingleData(farmID, date, Integer.parseInt(milkWeight));
	  }
	  
	  //close the reader
	  fileReader.close();
	  //successful extraction
	  return true;
	}
    catch(FileNotFoundException e) {
      System.out.println(e.getMessage());
    }
	catch(Exception e) {
	  System.out.println(e.getMessage());
	}
	  
    return false;
  }
  
  public boolean writeToFile() {
	return false;
  }
  
  /**
   * This method returns the file content in String form
   * 
   * @return - String representation of file content
   */
  public String getFileContenets() {
   BufferedReader fileReader = null;  
   String currentLine = "";
   String fileContent = "";
		
   //try to read content from file
   try {
	 //create a new BufferedReader to read from the file
     fileReader = new BufferedReader(new FileReader(inputFile));

     //Go through each line of the file and add it's content to String
	 while((currentLine = fileReader.readLine()) != null) {
	   fileContent += currentLine;
	   fileContent += "\n";
	 }
	 
	 //close the reader
	 fileReader.close();
   }
   catch(FileNotFoundException e) {
     System.out.println(e.getMessage());
   }
   catch(Exception e) {
     System.out.println(e.getMessage());
   }
   
   //return the content of the file in String form
   return fileContent;
  }
  
  
}
