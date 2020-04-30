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

import java.util.ArrayList;

public class CheeseFactory {
 
  public ArrayList<Farm> milkDataFromFarms; //stores Farms for this factory
  public ArrayList<String> farmIDList; //contains farmIDs
  
  /**
   * Default constructor that initializes the two ArrayList
   * parameters, milkDataFromFarms and farmIDList
   */
  public CheeseFactory() {
    milkDataFromFarms = new ArrayList<>();
    farmIDList = new ArrayList<>();
  }
  
  /**
   * This method will insert data into a Farm, or if no Farm exists it will
   * create a Farm and then insert the data
   * 
   * @param farmID - the unique ID of the farm
   * @param date - the date of the data
   * @param milkWeight - the milk weight data
   * @return true if inserting the data worked
   */
  public boolean insertSingleData(String farmID, String date, Integer milkWeight) { 
	if(farmID == null) {
	  return false;
	}
	  
	//check if this farm has been added to the list of Farms for the 
	//Cheese Factory
	if(!farmIDList.contains(farmID)) {
	  //add the farm to the list of Farm IDs
	  farmIDList.add(farmID);
	  //create a new Farm with this new id
	  Farm newFarm = new Farm(farmID);
	  //add this to the list of Farms
	  milkDataFromFarms.add(newFarm);
	}
	
	//add the data info to the farm if the date and milkWeight are not null
	for(int i = 0; i < milkDataFromFarms.size(); i++) {
	  if(milkDataFromFarms.get(i).farmID.equals(farmID)) {
		if(date != null && milkWeight != null) {
	      milkDataFromFarms.get(i).insertMilkForDate(date, milkWeight);
	      return true;
		}
	  }
	}
	  
	//return false if insert of data fails
    return false;
  }
  
  /**
   * This method will edit the milk weight for a given date in a Farm
   * 
   * @param farmID - the unique ID of the farm
   * @param date - the date to be edited
   * @param newWeight - the new weight
   * @return true if the edit was successful
   */
  public boolean editSingleData(String farmID, String date, Integer newWeight) {
	if (farmID == null) {
	  return false;
	}
	
	//find the right Farm and then edit the data for that date
    for(int i = 0; i < milkDataFromFarms.size(); i++) {
	  if(milkDataFromFarms.get(i).farmID.equals(farmID)) {
		if(date != null && newWeight != null) {
	      milkDataFromFarms.get(i).editMilkForDate(date, newWeight);
		  return true;
		}
	  }
	}
		
    //return false if the edit is not successful 
	return false;
  }
  
  /**
   * This method will remove the milk weight for a date given from a
   * Farm
   * 
   * @param farmID - the unique ID of the farm
   * @param date - the date for the data to be removed
   * @return true if the removal was a success
   */
  public Integer removeSingleData(String farmID, String date) {  
	if(farmID == null) {
	  return null;
	}
	
	//find the correct Farm using the ID and remove the data
    for(int i = 0; i < milkDataFromFarms.size(); i++) {
      if(milkDataFromFarms.get(i).farmID.equals(farmID)) {
    	if (date != null) {
    	  Integer removedData = milkDataFromFarms.get(i).removeMilkForDate(date);
    	  return removedData;
    	}
      }
   }
   
   //if the value was never returned in the for loop, return null
   return null;
  }

}
