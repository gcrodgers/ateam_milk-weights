package application;

import java.util.ArrayList;

public class CheeseFactory {
 
  public String name;
  public ArrayList<Farm> milkDataFromFarms;
  
  public CheeseFactory(String name) {
    this.name = name;
    milkDataFromFarms = new ArrayList<>();
  }
  
  public boolean insertSingleData(String farmID, String date, Integer milkWeight) { 
	
	for(int i = 0; i < milkDataFromFarms.size(); i++) {
	  if(milkDataFromFarms.get(i).farmID.equals(farmID)) {
	    milkDataFromFarms.get(i).insertMilkForDate(date, milkWeight);
	    return true;
	  }
	}
	  
    return false;
  }
  
  public boolean insertData(Farm data) {
	milkDataFromFarms.add(data);
	
	if(milkDataFromFarms.contains(data)) {
      return true;
	}
	
    return false;
  }
  
  public boolean editSingleData(String farmID, String date, Integer newWeight) {
    for(int i = 0; i < milkDataFromFarms.size(); i++) {
	  if(milkDataFromFarms.get(i).farmID.equals(farmID)) {
	    milkDataFromFarms.get(i).editMilkForDate(date, newWeight);
		return true;
	  }
	}
			  
	return false;
  }
  
  public Integer removeSingleData(String farmID, String date) {  
    for(int i = 0; i < milkDataFromFarms.size(); i++) {
      if(milkDataFromFarms.get(i).farmID.equals(farmID)) {
    	Integer removedData = milkDataFromFarms.get(i).removeMilkForDate(date);
    	return removedData;
      }
   }
				  
   return null;
  }

}
