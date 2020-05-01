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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DataManager {

	// initializing fields
	CheeseFactory factory;

	/**
	 * Default constructor initialize a CheeseFactory.
	 * 
	 */
	public DataManager(CheeseFactory factory) {
		this.factory = factory;
	}

	/**
	 * Calculates the average weight of milk produced by farms in a given month.
	 * 
	 * @param month	- String of month to be specified
	 * @return average - Integer of average Weight in a specified month
	 */
	public Integer getMonthlyAverage(String month) {

		int total = 0;// total amount of milk weight between all farms
		int average = 0;// average amount of weight in a given month
		int yearCount = 0;// number of years observed

		// loops through farms in a given factory
		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {
			ArrayList<String> yearsList = new ArrayList<String>();// list of years to act as count for each farm
			// stores the date values of each data entry, easier to parse
			Set<String> keys = factory.milkDataFromFarms.get(i).milkData.keySet();
			// dates of entry are matched to specified date
			for (String key : keys) {
				String[] values = key.toString().split("-");
				for (int j = 0; j < values.length; j += 3) {
					// if a date matches in month and year then it added to total
					if (month.equals(values[j + 1])) {
						total += factory.milkDataFromFarms.get(i).milkData.get(key);
						if (!yearsList.contains(values[j])) {
							yearsList.add(values[j]);
						}
					}
				}

			}
			yearCount += yearsList.size();
		}
		// average is the total divided by the number of years included in the data
		average = total / yearCount;
		return average;
	}

	/**
	 * Calculates the minimum weight of milk produced by farms in a given month.
	 * 
	 * @param month - month specified
	 * @return min - minimum weight in month, for all same months for all farms
	 */
	public Integer getMonthlyMin(String month) {
		int min = 0;// monthly minimum value
		int initialMin = 0;// used to set to a correct starting value
		Set<String> keys = new HashSet<String>();// holds keys for each set used in search
		Hashtable<String, Integer> monthKeys = new Hashtable<String, Integer>();// keys that have the correct month
		Hashtable<String, Integer> yearSum = new Hashtable<String, Integer>();// sums the total yield for the month in a
																				// given year
		// loops through farms in a given factory
		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {
			// stores the date values of each data entry, easier to parse
			keys = factory.milkDataFromFarms.get(i).milkData.keySet();
			// dates of entry are matched to specified date
			for (String key : keys) {
				String[] values = key.toString().split("-");
				for (int j = 0; j < values.length; j += 3) {
					// if a date matches in month
					if (month.equals(values[j + 1])) {
						monthKeys.put(key, factory.milkDataFromFarms.get(i).milkData.get(key));
					}
				}
			}
		}
		keys = monthKeys.keySet();
		System.out.println(keys);
		for (String key : keys) {
			String[] values = key.toString().split("-");
			for (int j = 0; j < values.length; j += 3) {
				// if year is not in list
				if (!yearSum.containsKey(values[j])) {
					yearSum.put(values[j], monthKeys.get(key));
				}
				// if year is in table replace with updated total
				else {
					int sum = monthKeys.get(key) + yearSum.get(values[j]);
					yearSum.put(values[j], sum);
				}
			}
		}

		keys = yearSum.keySet();
		// dates of entry are matched to specified date
		for (String key : keys) {
			// set the min value to first recorded value as it is currently 0
			if (0 == initialMin) {
				min = yearSum.get(key);
				initialMin++;
			}
			// compare current value to min value
			if (min > yearSum.get(key)) {
				min = yearSum.get(key);
				
			}
			System.out.println(min);
			System.out.println(yearSum.get(key));
		}
//		System.out.println(min);
		return min;
	}
	
	public Hashtable<String, Integer> helper(String month){
		Hashtable<String, Integer> monthKeys = new Hashtable<String, Integer>();// keys that have the correct month
		Set<String> keys = new HashSet<String>();// holds keys for each set used in search
		// loops through farms in a given factory
				for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {
					// stores the date values of each data entry, easier to parse
					keys = factory.milkDataFromFarms.get(i).milkData.keySet();
					// dates of entry are matched to specified date
					for (String key : keys) {
						String[] values = key.toString().split("-");
						for (int j = 0; j < values.length; j += 3) {
							// if a date matches in month
							if (month.equals(values[j + 1])) {
								monthKeys.put(key, factory.milkDataFromFarms.get(i).milkData.get(key));
							}
						}
					}
				}
		return monthKeys;
	}
	/**
	 * Calculates the maximum weight of milk produced by farms in a given month.
	 * 
	 * @param month - month specified
	 * @return max - maximum weight in month, for all same months for all farms
	 */
	public Integer getMonthlyMax(String month) {

		int max = 0;// maximum weight
		Set<String> keys = new HashSet<String>();// holds keys for each set used in search
		Hashtable<String, Integer> monthKeys = new Hashtable<String, Integer>();// keys that have the correct month
		Hashtable<String, Integer> yearSum = new Hashtable<String, Integer>();// sums the total yield for the month in a
																				// given year

		monthKeys = helper(month);
		keys = monthKeys.keySet();
		for (String key : keys) {
		
			String[] values = key.toString().split("-");
			for (int j = 0; j < values.length; j += 3) {
				// if year is not in list
				if (!yearSum.containsKey(values[j])) {
					yearSum.put(values[j], monthKeys.get(key));
				}
				// if year is in table replace with updated total
				else {
					int sum = monthKeys.get(key) + yearSum.get(values[j]);
					yearSum.put(values[j], sum);
				}
			}
		}
		keys = yearSum.keySet();

		// dates of entry are matched to specified date
		for (String key : keys) {
			if (max < yearSum.get(key)) {
				max = yearSum.get(key);
			}
		}
		return max;

	}

	/**
	 * Calculates the average weight of milk produced by a specified farm in a given
	 * month.
	 * 
	 * @param farm - farmID specified
	 * @param month - month specified
	 * @return average - average weight of farm in a month compared to all other recoded same months
	 */
	public Integer getMonthlyAverageForFarm(String farm, String month) {

		int total = 0;// total amount of milk weight between all farms
		int average = 0;// average amount of weight in a given month
		int yearCount = 0;// number of years observed

		// loops through farms in a given factory
		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {
			ArrayList<String> yearsList = new ArrayList<String>();// list of years to act as count
			// stores the date values of each data entry, easier to parse
			if (farm.equals(factory.milkDataFromFarms.get(i).farmID)) {
				Set<String> keys = factory.milkDataFromFarms.get(i).milkData.keySet();
				// dates of entry are matched to specified date
				for (String key : keys) {
					String[] values = key.toString().split("-");
					for (int j = 0; j < values.length; j += 3) {
						// if a date matches in month and year then it added to total
						if (month.equals(values[j + 1])) {
							total += factory.milkDataFromFarms.get(i).milkData.get(key);
							if (!yearsList.contains(values[j])) {
								yearsList.add(values[j]);
							}
						}
					}
				}

			}
			yearCount += yearsList.size();
		}
		// average is the total divided by the number of years included in the data
		average = total / yearCount;
		return average;
	}

	/**
	 * Calculates the minimum weight of milk produced by a specified farm in a given
	 * month.
	 * 
	 * Unfinished.
	 * 
	 * @param farm - farmID specified
	 * @param month - month specified
	 * @return min - minimum weight of farm in month
	 */
	public Integer getMonthlyMinForFarm(String farm, String month) {
		int min = 0;// monthly minimum value
		int initialMin = 0;// used to set to a correct starting value
		Set<String> keys = new HashSet<String>();// holds keys for each set used in search
		Hashtable<String, Integer> monthKeys = new Hashtable<String, Integer>();// keys that have the correct month
		Hashtable<String, Integer> yearSum = new Hashtable<String, Integer>();// sums the total yield for the month in a
																				// given year
		// loops through farms in a given factory
		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {
			if (farm.equals(factory.milkDataFromFarms.get(i).farmID)) {
				// stores the date values of each data entry, easier to parse
				keys = factory.milkDataFromFarms.get(i).milkData.keySet();
				// dates of entry are matched to specified date
				for (String key : keys) {
					String[] values = key.toString().split("-");
					for (int j = 0; j < values.length; j += 3) {
						// if a date matches in month
						if (month.equals(values[j + 1])) {
							monthKeys.put(key, factory.milkDataFromFarms.get(i).milkData.get(key));
						}
					}
				}
			}
		}
		keys = monthKeys.keySet();
		for (String key : keys) {
			String[] values = key.toString().split("-");
			for (int j = 0; j < values.length; j += 3) {
				// if year is not in list
				if (!yearSum.containsKey(values[j])) {
					yearSum.put(values[j], monthKeys.get(key));
				}
				// if year is in table replace with updated total
				else {
					int sum = monthKeys.get(key) + yearSum.get(values[j]);
					yearSum.put(values[j], sum);
				}
			}
		}

		keys = yearSum.keySet();
		// dates of entry are matched to specified date
		for (String key : keys) {
			// set the min value to first recorded value as it is currently 0
			if (0 == initialMin) {
				min = yearSum.get(key);
				initialMin++;
			}
			// compare current value to min value
			if (min > yearSum.get(key)) {
				min = yearSum.get(key);
			}
		}
		return min;
	}

	/**
	 * Calculates the maximum weight of milk produced by a specified farm in a given
	 * month.
	 * 
	 * Unfinished.
	 * 
	 * @param farm - farmID specified
	 * @param month - month specified
	 * @return max - max weight of farm in month
	 */
	public Integer getMonthlyMaxForFarm(String farm, String month) {

		int max = 0;// maximum weight
		Set<String> keys = new HashSet<String>();// holds keys for each set used in search
		Hashtable<String, Integer> monthKeys = new Hashtable<String, Integer>();// keys that have the correct month
		Hashtable<String, Integer> yearSum = new Hashtable<String, Integer>();// sums the total yield for the month in a
																				// given year

		// loops through farms in a given factory
		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {
			if (farm.equals(factory.milkDataFromFarms.get(i).farmID)) {
				// stores the date values of each data entry, easier to parse
				keys = factory.milkDataFromFarms.get(i).milkData.keySet();
				// dates of entry are matched to specified date
				for (String key : keys) {
					String[] values = key.toString().split("-");
					for (int j = 0; j < values.length; j += 3) {
						// if a date matches in month
						if (month.equals(values[j + 1])) {
							monthKeys.put(key, factory.milkDataFromFarms.get(i).milkData.get(key));
						}
					}
				}
			}
		}
		keys = monthKeys.keySet();
		for (String key : keys) {
		
			String[] values = key.toString().split("-");
			for (int j = 0; j < values.length; j += 3) {
				// if year is not in list
				if (!yearSum.containsKey(values[j])) {
					yearSum.put(values[j], monthKeys.get(key));
				}
				// if year is in table replace with updated total
				else {
					int sum = monthKeys.get(key) + yearSum.get(values[j]);
					yearSum.put(values[j], sum);
				}
			}
		}
		keys = yearSum.keySet();
	
		// dates of entry are matched to specified date
		for (String key : keys) {
			if (max < yearSum.get(key)) {
				max = yearSum.get(key);
			}
		}
		return max;

	}

	public Integer getDataSortedByField() {

		return null;
	}

	/**
	 * Average weight within a specified date range, average weight by day
	 * 
	 * @param day1 -day start
	 * @param month1 - month start
	 * @param year1 - year start
	 * @param day2 - day end
	 * @param month2 - month end
	 * @param year2 - year end
	 * @return average - average weight in range per day
	 */
	public Integer getAverageInDateRange(String day1, String month1, String year1, String day2, String month2,
			String year2) {
		int total = 0;// total amount of milk weight between all farms
		int average = 0;// average amount of weight in a given month

		// loops through farms in a given factory
		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {

			// stores the date values of each data entry, easier to parse
			Set<String> keys = factory.milkDataFromFarms.get(i).milkData.keySet();
			// dates of entry are matched to specified date
			for (String key : keys) {
				String[] values = key.toString().split("-");
				for (int j = 0; j < values.length; j += 3) {
					// if a date matches in month and year then it added to total
					if ((Integer.parseInt(year1) <= Integer.parseInt(values[j])
							&& Integer.parseInt(month1) <= Integer.parseInt(values[j + 1])
							&& Integer.parseInt(day1) <= Integer.parseInt(values[j + 2]))
							&& (Integer.parseInt(year2) >= Integer.parseInt(values[j])
									&& Integer.parseInt(month2) >= Integer.parseInt(values[j + 1])
									&& Integer.parseInt(day2) >= Integer.parseInt(values[j + 2]))) {
						total += factory.milkDataFromFarms.get(i).milkData.get(key);
					}
				}

			}

		}
		// calculation of the difference between two dates
		LocalDate dateBefore = LocalDate.of(Integer.parseInt(year1), Integer.parseInt(month1), Integer.parseInt(day1));
		LocalDate dateAfter = LocalDate.of(Integer.parseInt(year2), Integer.parseInt(month2), Integer.parseInt(day2));
		long diffOfDays = ChronoUnit.DAYS.between(dateBefore, dateAfter);
		if (diffOfDays != 0) {
			average = (int) (total / diffOfDays);
		} else {
			average = total;
		}
		return average;

	}

	/**
	 * Calculates the maximum weight of milk produced on a given day in range of 2
	 * dates
	 * 
	 * @param day1 -day start
	 * @param month1 - month start
	 * @param year1 - year start
	 * @param day2 - day end
	 * @param month2 - month end
	 * @param year2 - year end
	 * @return min - min weight in range
	 */
	public Integer getMinInDateRange(String day1, String month1, String year1, String day2, String month2,
			String year2) {
		int min = 0;// monthly minimum value
		int initialMin = 0;// used to set to a correct starting value

		// loops through farms in a given factory
		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {

			// stores the date values of each data entry, easier to parse
			Set<String> keys = factory.milkDataFromFarms.get(i).milkData.keySet();
			// dates of entry are matched to specified date
			for (String key : keys) {
				String[] values = key.toString().split("-");
				for (int j = 0; j < values.length; j += 3) {
					// if a date matches in month and year then it added to total
					if ((Integer.parseInt(year1) <= Integer.parseInt(values[j])
							&& Integer.parseInt(month1) <= Integer.parseInt(values[j + 1])
							&& Integer.parseInt(day1) <= Integer.parseInt(values[j + 2]))
							&& (Integer.parseInt(year2) >= Integer.parseInt(values[j])
									&& Integer.parseInt(month2) >= Integer.parseInt(values[j + 1])
									&& Integer.parseInt(day2) >= Integer.parseInt(values[j + 2]))) {
						if (0 == initialMin) {
							min = factory.milkDataFromFarms.get(i).milkData.get(key);
							initialMin++;
						}
						// compare current value to min value
						if (min > factory.milkDataFromFarms.get(i).milkData.get(key)) {
							min = factory.milkDataFromFarms.get(i).milkData.get(key);
						}
					}
				}

			}

		}
		return min;
	}

	/**
	 * Calculates the minimum weight of milk produced on a given day in range of 2
	 * dates
	 * 
	 * Unfinished.
	 * 
	 * @param day1 -day start
	 * @param month1 - month start
	 * @param year1 - year start
	 * @param day2 - day end
	 * @param month2 - month end
	 * @param year2 - year end
	 * @return max - max weight in range
	 */
	public Integer getMaxInDateRange(String day1, String month1, String year1, String day2, String month2,
			String year2) {
		int max = 0;// maximum weight

		// loops through farms in a given factory
		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {

			// stores the date values of each data entry, easier to parse
			Set<String> keys = factory.milkDataFromFarms.get(i).milkData.keySet();
			// dates of entry are matched to specified date
			for (String key : keys) {
				String[] values = key.toString().split("-");
				for (int j = 0; j < values.length; j += 3) {
					// if a date matches in month and year then it added to total
					if ((Integer.parseInt(year1) <= Integer.parseInt(values[j])
							&& Integer.parseInt(month1) <= Integer.parseInt(values[j + 1])
							&& Integer.parseInt(day1) <= Integer.parseInt(values[j + 2]))
							&& (Integer.parseInt(year2) >= Integer.parseInt(values[j])
									&& Integer.parseInt(month2) >= Integer.parseInt(values[j + 1])
									&& Integer.parseInt(day2) >= Integer.parseInt(values[j + 2]))) {
						if (max < factory.milkDataFromFarms.get(i).milkData.get(key)) {
							max = factory.milkDataFromFarms.get(i).milkData.get(key);
						}
					}
				}

			}

		}
		return max;
	}

	/**
	 * Farm Report, calculates total amount of weight for specific farm in a
	 * specific year
	 * 
	 * @param farm - farmID specified
	 * @param year - year specified
	 * @return total - weight of farm in year 
	 */
	public Integer frYearWeight(String farm, String year) {
		int total = 0;// total amount of milk weight

		// loops through farms in a given factory
		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {
			// stores the date values of each data entry, easier to parse
			if (farm.equals(factory.milkDataFromFarms.get(i).farmID)) {
				Set<String> keys = factory.milkDataFromFarms.get(i).milkData.keySet();
				// dates of entry are matched to specified date
				for (String key : keys) {
					String[] values = key.toString().split("-");
					for (int j = 0; j < values.length; j += 3) {
						// if a date matches in month and year then it added to total
						if (year.equals(values[j])) {
							total += factory.milkDataFromFarms.get(i).milkData.get(key);
						}
					}
				}

			}
		}
		return total;
	}

	/**
	 * Farm Report, calculates total amount of weight for specific farm in a
	 * specific month and year.
	 * 
	 * @param farm - farmID specified
	 * @param year - year specified
	 * @param month - month specified
	 * @return total - weight of farm in year and month
	 */
	public Integer frMonthWeight(String farm, String year, String month) {
		int total = 0;// total amount of milk weight

		// loops through farms in a given factory
		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {
			// stores the date values of each data entry, easier to parse
			if (farm.equals(factory.milkDataFromFarms.get(i).farmID)) {
				Set<String> keys = factory.milkDataFromFarms.get(i).milkData.keySet();
				// dates of entry are matched to specified date
				for (String key : keys) {
					String[] values = key.toString().split("-");
					for (int j = 0; j < values.length; j += 3) {
						// if a date matches in month and year then it added to total
						if (month.equals(values[j + 1]) && year.equals(values[j])) {
							total += factory.milkDataFromFarms.get(i).milkData.get(key);
						}
					}
				}

			}
		}

		return total;
	}

	/**
	 * Farm Report, returns a percentage (truncated) of a farms weight produced in a
	 * specific month compared to the year it is in.
	 * 
	 * @param farm - farmID specified
	 * @param year - year specified
	 * @param month - month specified
	 * @return percentS - String of what percent the farm specified makes up of yield in year and month
	 */
	public String frPercent(String farm, String year, String month) {
		float percentF = 0;// percentage of a farms production relative to production in that year, float
		String percentS = null;// percentage of a farms production relative to production in that year, string

		percentF = ((float) frMonthWeight(farm, year, month)) * 100 / ((float) frYearWeight(farm, year));
		percentS = Float.toString(percentF);
//		percentS = percentS.substring(0, percentS.length() - 4);
		percentS += "%";

		return percentS;
	}

	/**
	 * Farm Report,returns a list of farm's keys in a sorted order by date.
	 * 
	 * @param farm - farmID specified
	 * @return keys - list of farm keys sorted by ascending date
	 */
	public List<String> frList(String farm) {

		List<String> keys = new ArrayList<String>();

		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {
			if (farm.equals(factory.milkDataFromFarms.get(i).farmID)) {
				keys = new ArrayList<String>(factory.milkDataFromFarms.get(i).milkData.keySet());
			}
		}
		Collections.sort(keys);
		return keys;
	}

	/**
	 * Annual Report, returns the total weight of all farms in a year.
	 * 
	 * @param year - year specified for search
	 * @return total - weight of all farms in year
	 */
	public Integer arYearWeight(String year) {
		int total = 0;// total amount of milk weight

		// loops through farms in a given factory
		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {
			// stores the date values of each data entry, easier to parse
			Set<String> keys = factory.milkDataFromFarms.get(i).milkData.keySet();
			// dates of entry are matched to specified date
			for (String key : keys) {
				String[] values = key.toString().split("-");
				for (int j = 0; j < values.length; j += 3) {
					// if a date matches in month and year then it added to total
					if (year.equals(values[j])) {
						total += factory.milkDataFromFarms.get(i).milkData.get(key);
					}
				}
			}
		}
		return total;
	}

	/**
	 * Annual Report, returns the total weight of specified farm in year.
	 * 
	 * @param farm - farmID specified
	 * @param year - year specified for search
	 * @return total - weight of farm in year
	 */
	public Integer arFarmWeight(String farm, String year) {
		int total = 0;// total amount of milk weight

		// loops through farms in a given factory
		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {
			if (farm.equals(factory.milkDataFromFarms.get(i).farmID)) {
				// stores the date values of each data entry, easier to parse
				Set<String> keys = factory.milkDataFromFarms.get(i).milkData.keySet();
				// dates of entry are matched to specified date
				for (String key : keys) {
					String[] values = key.toString().split("-");
					for (int j = 0; j < values.length; j += 3) {
						// if a date matches in month and year then it added to total
						if (year.equals(values[j])) {
							total += factory.milkDataFromFarms.get(i).milkData.get(key);
						}
					}
				}
			}
		}
		return total;
	}

	/**
	 * Annual Report, returns a percentage (truncated) of a farms weight produced in
	 * a specific year compared to all farms that year.
	 * 
	 * @param farm - farmID specified
	 * @param year - year specified for search
	 * @return percentS - String of what percent the farm specified makes up of yield in year
	 */
	public String arPercent(String farm, String year) {
		float percentF = 0;// percentage of a farms production relative to production in that year, float
		String percentS = null;// percentage of a farms production relative to production in that year, string

		percentF = ((float) arFarmWeight(farm, year)) * 100 / ((float) arYearWeight(year));

		percentS = Float.toString(percentF);
//		percentS = percentS.substring(0, percentS.length() - 4);
		percentS += "%";

		return percentS;
	}

	/**
	 * Annual Report,returns a list of farm IDs for a specified year
	 * 
	 * 
	 * @param year - year specified for search
	 * @return farmID - list of farms in year
	 */
	private List<String> arList(String year) {
		List<String> farmID = new ArrayList<String>();// farm ids list

		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {
			Set<String> keys = factory.milkDataFromFarms.get(i).milkData.keySet();
			// dates of entry are matched to specified date
			for (String key : keys) {
				String[] values = key.toString().split("-");
				for (int j = 0; j < values.length; j += 3) {
					// if a date matches in month and year then it added to total
					if (year.equals(values[j])) {
						if (!farmID.contains(factory.milkDataFromFarms.get(i).farmID)) {
							farmID.add(factory.milkDataFromFarms.get(i).farmID);
						}
					}
				}
			}
		}
		return farmID;
	}

	/**
	 * Annual Report,returns a sorted list of farm IDs in ascending id order.
	 * 
	 * 
	 * @param year - year specified for search
	 * @return farmID - list of farms in descending order by id
	 */
	public List<String> arListAscending(String year) {

		List<String> farmID = new ArrayList<String>();// farm ids list

		farmID = arList(year);

		// sort in correct order of farm
		Collections.sort(farmID);

		return farmID;
	}

	/**
	 * Annual Report,returns a sorted list of farm IDs in descending id order.
	 * 
	 * 
	 * @param year - year specified for search
	 * @return farmID - list of farms in descending order by id
	 */
	public List<String> arListDescending(String year) {

		List<String> farmID = new ArrayList<String>();// farm ids list

		farmID = arList(year);

		// sort in correct order of farm
		Collections.sort(farmID, Collections.reverseOrder());

		return farmID;
	}
	
	/**
	 * Annual Report,returns a sorted list of farm IDs in ascending id order by weight.
	 * 
	 * 
	 * @param year - year specified for search
	 * @return sortedList - list of farms in ascending order by weight
	 */
	public List<String> arAscendingWeight(String year) {
		List<String> farmList = arList(year);// farm ids list
		List<String> sortedList = new ArrayList<String>();//list that will hold sorted farms id list
		Hashtable<String, Integer> farmTable = new Hashtable<String, Integer>();// table of farms with monthly weight
		// add each farms monthly weight to table
		for (int i = 0; i < farmList.size(); i++) {
			farmTable.put(farmList.get(i), arFarmWeight(farmList.get(i), year));
		}
		List<Entry<?, Integer>> sort = sortValue(farmTable);

		for (Entry<?, Integer> key : sort) {
			String[] values = key.toString().split("=");
			for (int j = 0; j < values.length; j += 2) {
				sortedList.add(values[j]);
			}
		}
		Collections.reverse(sortedList);
		return sortedList;
	}
	
	/**
	 * Annual Report,returns a sorted list of farm IDs in descending id order by weight.
	 * 
	 * @param year - year specified for search
	 * @return sortedList - list of farms in descending order by weight
	 */
	public List<String> arDescendingWeight(String year) {
		List<String> sortedList = arAscendingWeight(year);//list that will hold sorted farms id list
		//reverse list 
		Collections.reverse(sortedList);
		return sortedList;
	}

	/**
	 * Monthly Report, returns the total weight of all farms in a month and year.
	 * 
	 * @param month - specified month for search
	 * @param year	- specified year for search
	 * @return total - weight all farms in year and month
	 */
	public Integer mrAllWeight(String month, String year) {
		int total = 0;// total amount of milk weight

		// loops through farms in a given factory
		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {
			// stores the date values of each data entry, easier to parse
			Set<String> keys = factory.milkDataFromFarms.get(i).milkData.keySet();
			// dates of entry are matched to specified date
			for (String key : keys) {
				String[] values = key.toString().split("-");
				for (int j = 0; j < values.length; j += 3) {
					// if a date matches in month and year then it added to total
					if (month.contentEquals(values[j + 1]) && year.equals(values[j])) {
						total += factory.milkDataFromFarms.get(i).milkData.get(key);
					}
				}
			}
		}
		return total;
	}

	/**
	 * Monthly Report, returns the total weight of specified farm in a month and
	 * year.
	 * 
	 * @param farm - specified farmID 
	 * @param month - specified month for search
	 * @param year	- specified year for search
	 * @return total - weight the farm specified makes up of yield in year and month
	 */
	public Integer mrFarmWeight(String farm, String month, String year) {
		int total = 0;// total amount of milk weight

		// loops through farms in a given factory
		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {
			if (farm.equals(factory.milkDataFromFarms.get(i).farmID)) {
				// stores the date values of each data entry, easier to parse
				Set<String> keys = factory.milkDataFromFarms.get(i).milkData.keySet();
				// dates of entry are matched to specified date
				for (String key : keys) {
					String[] values = key.toString().split("-");
					for (int j = 0; j < values.length; j += 3) {
						// if a date matches in month and year then it added to total
						if (month.contentEquals(values[j + 1]) && year.equals(values[j])) {
							total += factory.milkDataFromFarms.get(i).milkData.get(key);
						}
					}
				}
			}
		}
		return total;
	}

	/**
	 * Monthly Report, returns a percentage (truncated) of a farms weight produced
	 * in a specific year compared to all farms that year.
	 * 
	 * @param farm - specified farmID 
	 * @param month - specified month for search
	 * @param year	- specified year for search
	 * @return percentS - Sting of what percent the farm specified makes up of yield in year and month
	 */
	public String mrPercent(String farm, String month, String year) {
		float percentF = 0;// percentage of a farms production relative to production in that year, float
		String percentS = null;// percentage of a farms production relative to production in that year, string

		percentF = ((float) mrFarmWeight(farm, month, year)) * 100 / ((float) mrAllWeight(month, year));
		//convert float to final string form
		percentS = Float.toString(percentF);
		percentS += "%";

		return percentS;
	}

	/**
	 * Monthly Report,returns a list of farm's keys in a sorted order by date.
	 * 
	 * @param year - year specified for search
	 * @param month - month specified for search
	 * @return list of farmIDs in specified month and year by farmID 
	 */
	private List<String> mrList(String year, String month) {

		List<String> farmID = new ArrayList<String>();// farm ids list

		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {
			Set<String> keys = factory.milkDataFromFarms.get(i).milkData.keySet();
			// dates of entry are matched to specified date
			for (String key : keys) {
				String[] values = key.toString().split("-");
				for (int j = 0; j < values.length; j += 3) {
					// if a date matches in month and year then it added to total
					if (month.equals(values[j + 1]) && year.equals(values[j])) {
						if (!farmID.contains(factory.milkDataFromFarms.get(i).farmID)) {
							farmID.add(factory.milkDataFromFarms.get(i).farmID);
						}
					}
				}
			}
		}
		return farmID;
	}

	/**
	 * Monthly Report,returns a sorted list of farm IDs in ascending id order.
	 * 
	 * 
	 * @param year - year specified for search
	 * @param month - month specified for search
	 * @return list of farmIDs in specified month and year by farmID ascending order
	 */
	public List<String> mrListAscendingID(String year, String month) {

		List<String> farmID = new ArrayList<String>();// farm ids list

		farmID = mrList(year, month);

		// sort in correct order of farm
		Collections.sort(farmID);

		return farmID;
	}

	/**
	 * Monthly Report,returns a sorted list of farm IDs in descending id order.
	 * 
	 * 
	 * @param year - year specified for search
	 * @param month - month specified for search
	 * @return list of farmIDs in specified month and year by farmID descending order
	 */
	public List<String> mrListDescendingID(String year, String month) {

		List<String> farmID = new ArrayList<String>();// farm ids list

		farmID = mrList(year, month);

		// sort in correct order of farm
		Collections.sort(farmID, Collections.reverseOrder());

		return farmID;
	}

	/**
	 * Monthly Report,returns a sorted list of farm IDs in ascending id order by weight.
	 * 
	 * 
	 * @param year - year specified for search
	 * @param month - month specified for search
	 * @return sortedList - list of farms in ascending order by weight
	 */
	public List<String> mrAscendingWeight(String year, String month) {
		List<String> farmList = mrList(year, month);// farm ids list
		List<String> sortedList = new ArrayList<String>();//list that will hold sorted farms id list
		Hashtable<String, Integer> farmTable = new Hashtable<String, Integer>();// table of farms with monthly weight
		// add each farms monthly weight to table
		for (int i = 0; i < farmList.size(); i++) {
			farmTable.put(farmList.get(i), mrFarmWeight(farmList.get(i), month, year));
		}

		List<Entry<?, Integer>> sort = sortValue(farmTable);
		//add each farm to list in correct order
		for (Entry<?, Integer> key : sort) {
			String[] values = key.toString().split("=");
			for (int j = 0; j < values.length; j += 2) {
				sortedList.add(values[j]);
			}
		}
	
		return sortedList;
	}
	/**
	 * Monthly Report,returns a sorted list of farm IDs in descending id order by weight.
	 * 
	 * 
	 * @param year - year specified for search
	 * @param month - month specified for search
	 * @return sortedList - list of farms in descending order by weight
	 */
	public List<String> mrDescendingWeight(String year, String month) {
		List<String> sortedList = mrAscendingWeight(year, month);//list that will hold sorted farms id list
		//reverse list 
		Collections.reverse(sortedList);
		return sortedList;
	}



	/**
	 * Date Range Report, weight for all farms within a given range.
	 * 
	 * @param year - year to be searched
	 * @param monthStart - month starting point
	 * @param dayStart - day starting point
	 * @param monthEnd - month ending point
	 * @param dayEnd - day ending point
	 * @return total - total weight of milk in date range 
	 */
	public Integer drAllWeight(String year, String monthStart, String dayStart, String monthEnd, String dayEnd) {

		int total = 0;// total amount of milk weight between all farms

		// loops through farms in a given factory
		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {

			// stores the date values of each data entry, easier to parse
			Set<String> keys = factory.milkDataFromFarms.get(i).milkData.keySet();
			// dates of entry are matched to specified date
			for (String key : keys) {
				String[] values = key.toString().split("-");
				for (int j = 0; j < values.length; j += 3) {
					// if a date matches in month and year then it added to total
					if ((Integer.parseInt(year) <= Integer.parseInt(values[j])
							&& Integer.parseInt(monthStart) <= Integer.parseInt(values[j + 1])
							&& Integer.parseInt(dayStart) <= Integer.parseInt(values[j + 2]))
							&& (Integer.parseInt(year) >= Integer.parseInt(values[j])
									&& Integer.parseInt(monthEnd) >= Integer.parseInt(values[j + 1])
									&& Integer.parseInt(dayEnd) >= Integer.parseInt(values[j + 2]))) {
						total += factory.milkDataFromFarms.get(i).milkData.get(key);
					}
				}
			}
		}
		return total;
	}

	/**
	 * Date Range Report, weight for a specific farm within a given range.
	 * 
	 * @param year - year to be searched
	 * @param monthStart - month starting point
	 * @param dayStart - day starting point
	 * @param monthEnd - month ending point
	 * @param dayEnd - day ending point
	 * @return total - total weight of milk in date range for specified farm
	 */
	public Integer drFarmWeight(String farm, String year, String monthStart, String dayStart, String monthEnd,
			String dayEnd) {

		int total = 0;// total amount of milk weight between all farms

		// loops through farms in a given factory
		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {
			if (farm.equals(factory.milkDataFromFarms.get(i).farmID)) {
				// stores the date values of each data entry, easier to parse
				Set<String> keys = factory.milkDataFromFarms.get(i).milkData.keySet();
				// dates of entry are matched to specified date
				for (String key : keys) {
					String[] values = key.toString().split("-");
					for (int j = 0; j < values.length; j += 3) {
						// if a date matches in month and year then it added to total
						if ((Integer.parseInt(year) <= Integer.parseInt(values[j])
								&& Integer.parseInt(monthStart) <= Integer.parseInt(values[j + 1])
								&& Integer.parseInt(dayStart) <= Integer.parseInt(values[j + 2]))
								&& (Integer.parseInt(year) >= Integer.parseInt(values[j])
										&& Integer.parseInt(monthEnd) >= Integer.parseInt(values[j + 1])
										&& Integer.parseInt(dayEnd) >= Integer.parseInt(values[j + 2]))) {
							total += factory.milkDataFromFarms.get(i).milkData.get(key);
						}
					}
				}
			}
		}
		return total;
	}

	/**
	 * Date Range Report, returns a percentage of a farm's weight produced in a
	 * specific range compared to all farms that year.
	 * 
	 * @param farm - farmID specified
	 * @param year - year to be searched
	 * @param monthStart - month starting point
	 * @param dayStart - day starting point
	 * @param monthEnd - month ending point
	 * @param dayEnd - day ending point
	 * @return percentS - String of what percent the farm specified makes up of yield in a date range
	 */
	public String drPercent(String farm, String year, String monthStart, String dayStart, String monthEnd,
			String dayEnd) {
		float percentF = 0;// percentage of a farms production relative to production in that year, float
		String percentS = null;// percentage of a farms production relative to production in that year, string
		//calculate percent as float
		percentF = ((float) drFarmWeight(farm, year, monthStart, dayStart, monthEnd, dayEnd)) * 100
				/ ((float) drAllWeight(year, monthStart, dayStart, monthEnd, dayEnd));
		//convert float to final string form
		percentS = Float.toString(percentF);
		percentS += "%";

		return percentS;
	}
	
	/**
	 * Helper method, sorts HashTable by value. Method is slightly modified to fit
	 * needs of this project
	 * 
	 * @source https://stackoverflow.com/questions/5176771/sort-hashtable-by-values
	 * @author Shengyuan Lu
	 */
	private ArrayList<Map.Entry<?, Integer>> sortValue(Hashtable<?, Integer> t) {
		// Transfer as List and sort it
		ArrayList<Map.Entry<?, Integer>> l = new ArrayList(t.entrySet());
		Collections.sort(l, new Comparator<Map.Entry<?, Integer>>() {

			public int compare(Map.Entry<?, Integer> o1, Map.Entry<?, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		return l;
	}

}
