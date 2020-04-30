package application;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
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
	 * @param month
	 * @return average
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
	 * @param month
	 * @return min
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
	 * Calculates the maximum weight of milk produced by farms in a given month and
	 * year.
	 * 
	 * @param month
	 * @return
	 */
	public Integer getMonthlyMax(String month) {

		int max = 0;// maximum weight
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
		for (String key : keys) {
			System.out.println(key + " monthkey");
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
		System.out.println(keys + "years");
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
	 * @param farm
	 * @param month
	 * @return
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
	 * @param farm
	 * @param month
	 * @return
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
	 * @param farm
	 * @param month
	 * @return
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
			System.out.println(key + " monthkey");
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
		System.out.println(keys + "years");
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
	 * @param day1   -start
	 * @param month1
	 * @param year1
	 * @param day2   -end
	 * @param month2
	 * @param year2
	 * @return
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
	 * @param day1
	 * @param month1
	 * @param year1
	 * @param day2
	 * @param month2
	 * @param year2
	 * @return
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
	 * @param day1
	 * @param month1
	 * @param year1
	 * @param day2
	 * @param month2
	 * @param year2
	 * @return
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
	 * @param farm
	 * @param year
	 * @return total
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
	 * Farm Report, returns a percentage (truncated) of a farms weight produced in a
	 * specific month compared to the year it is in.
	 * 
	 * @param farm
	 * @param year
	 * @param month
	 * @return percentS
	 */
	public String frPercent(String farm, String year, String month) {
		float percentF = 0;// percentage of a farms production relative to production in that year, float
		String percentS = null;// percentage of a farms production relative to production in that year, string
		int monthWeight = 0;

		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {
			// stores the date values of each data entry, easier to parse
			if (farm.equals(factory.milkDataFromFarms.get(i).farmID)) {
				Set<String> keys = factory.milkDataFromFarms.get(i).milkData.keySet();
				// dates of entry are matched to specified date
				for (String key : keys) {
					String[] values = key.toString().split("-");
					for (int j = 0; j < values.length; j += 3) {
						// if a date matches in month and year then it added to total
						if (month.contentEquals(values[j + 1]) && year.equals(values[j])) {
							monthWeight += factory.milkDataFromFarms.get(i).milkData.get(key);
						}
					}
				}

			}
		}

		percentF = ((float) monthWeight) * 100 / ((float) frYearWeight(farm, year));
		percentS = Float.toString(percentF);
//		percentS = percentS.substring(0, percentS.length() - 4);
		percentS += "%";

		return percentS;
	}

	/**
	 * Annual Report, returns the total weight of all farms in a year.
	 * 
	 * @param year
	 * @return
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
	 * @param farm
	 * @param year
	 * @return
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
	 * @param farm
	 * @param year
	 * @return
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
	 * Monthly Report, returns the total weight of all farms in a month and year.
	 * 
	 * @param year
	 * @param month
	 * @return
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
	 * @param farm
	 * @param month
	 * @param year
	 * @return
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
	 * @param farm
	 * @param month
	 * @param year
	 * @return
	 */
	public String mrPercent(String farm, String month, String year) {
		float percentF = 0;// percentage of a farms production relative to production in that year, float
		String percentS = null;// percentage of a farms production relative to production in that year, string

		percentF = ((float) mrFarmWeight(farm, month, year)) * 100 / ((float) mrAllWeight(month, year));

		percentS = Float.toString(percentF);
//		percentS = percentS.substring(0, percentS.length() - 4);
		percentS += "%";
		System.out.println(percentS);
		return percentS;
	}

	/**
	 * Date Range Report, weight for all farms within a given range.
	 * 
	 * @param year
	 * @param monthStart
	 * @param dayStart
	 * @param monthEnd
	 * @param dayEnd
	 * @return
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
	 * @param year
	 * @param monthStart
	 * @param dayStart
	 * @param monthEnd
	 * @param dayEnd
	 * @return
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
	 * Date Range Report, returns a percentage of a farm's weight
	 * produced in a specific range compared to all farms that year.
	 * 
	 * @param farm
	 * @param year
	 * @param monthStart
	 * @param dayStart
	 * @param monthEnd
	 * @param dayEnd
	 * @return
	 */
	public String drPercent(String farm, String year, String monthStart, String dayStart, String monthEnd,
			String dayEnd) {
		float percentF = 0;// percentage of a farms production relative to production in that year, float
		String percentS = null;// percentage of a farms production relative to production in that year, string

		percentF = ((float) drFarmWeight(farm, year, monthStart, dayStart, monthEnd, dayEnd)) * 100
				/ ((float) drAllWeight(year, monthStart, dayStart, monthEnd, dayEnd));

		percentS = Float.toString(percentF);
//		percentS = percentS.substring(0, percentS.length() - 4);
		percentS += "%";

		return percentS;
	}

}
