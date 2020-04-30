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

import java.util.Hashtable;

/**
 * This class represents a farm with milk weight data 
 * 
 * @author garrett rodgers and william braun
 *
 */
public class Farm {
	
  public String farmID; //unique ID of the farm
  //Hashtable that will hold a String(date) and Integer(milkWeight)
  public Hashtable<String, Integer> milkData;
  
  /**
   * Constructor that creates a new farm with a unique farm ID
   * 
   * @param farmID - the unique farmID
   */
  public Farm(String farmID) {
    this.farmID = farmID;
    //initiate Hashtable
    milkData = new Hashtable<String, Integer>();
  }
  
  /**
   * This method will insert milk weight given a specific date
   * 
   * @param date - the date in YEAR-MONTH-DAY format
   * @param milkWeight - milk weight
   * @return - true if successful insert
   */
  public boolean insertMilkForDate(String date, Integer milkWeight) {
	//make sure neither argument are null
	if(date == null || milkWeight == null) {
	  throw new NullPointerException("Cannot have a null date or milk weight for insert.");	
	}
	  
	//if this date has not yet been created, make it 
    if(!milkData.containsKey(date)) {
      milkData.put(date, milkWeight);
      return true;
    }
    //else replace pre-existing milkweight
    else
    {
      milkData.replace(date, milkWeight);
    }
    
    //confirm that the correct milkweight has been added
    if(milkData.containsValue(milkWeight)) {
      return true;
    }
    
    //return false if failed insert occurs
    return false;
  }
  
  /**
   * This method edits pre-existing milk weight given a specific date
   * 
   * @param date - the date in YEAR-MONTH-DAY format
   * @param milkWeight - milk weight to replace previous weight
   * @return true if successful insert
   */
  public boolean editMilkForDate(String date, Integer milkWeight) {
	//make sure arguments arent null
    if(date == null || milkWeight == null) {
      throw new NullPointerException("Cannot have a null date or milk weight for edit.");	
	}
    
    //replace old value with new one
    milkData.replace(date, milkWeight);
    
    //make sure the correct value is in place
    if(milkData.containsValue(milkWeight)) {
      return true;
    }
    
    //return false if failed replace occurs
    return false;
  }
  
  /**
   * This method removes milk data for a given date
   * 
   * @param date - the date to remove milk from
   * @return - old milk data
   */
  public Integer removeMilkForDate(String date) {
	//make sure the date is not null
    if(date == null) {
	  throw new NullPointerException("Cannot have a null date for remove.");	
	}
    
    //remove the data
    Integer removedData = milkData.remove(date);	  
	  
    //readd the date as a key in the Hashtable
    milkData.compute(date, null);
    
    //return the old milk weight that was removed
    return removedData;
  }
  
  /**
   * This method will clear the data in the Hashtable for this farm
   */
  public void clearData() {
    milkData.clear();
  }

}
