package application;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
	 * @return
	 */
	public Integer getMonthlyAverage(String month) {

		int total = 0;// total amount of milk weight between all farms
		int average = 0;// average amount of weight in a given month
		int yearCount = 0;// number of years observed

		// loops through farms in a given factory
		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {
			ArrayList<String> yearsList = new ArrayList<String>();// list of years to act as count
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
	 * Calculates the minimum weight of milk produced by farms in a given month and
	 * year.
	 * 
	 * @param month
	 * @return
	 */
	public Integer getMonthlyMin(String month) {
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
					if (month.equals(values[j + 1])) {
						// set the min value to first recorded value as it is currently 0
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
	 * Calculates the maximum weight of milk produced by farms in a given month and
	 * year.
	 * 
	 * @param month
	 * @return
	 */
	public Integer getMonthlyMax(String month) {
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
					if (month.equals(values[j + 1])) {
						// compare max value for current value
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
						if (month.equals(values[j + 1])) {
							// set the min value to first recorded value as it is currently 0
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
						if (month.equals(values[j + 1])) {
							// compare max value for current value
							if (max < factory.milkDataFromFarms.get(i).milkData.get(key)) {
								max = factory.milkDataFromFarms.get(i).milkData.get(key);
							}
						}
					}
				}
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
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 */
	public Integer getAverageInDateRange(String day1, String month1, String year1,String day2, String month2, String year2) {
		int total = 0;// total amount of milk weight between all farms
		int average = 0;// average amount of weight in a given month
//		int yearCount = 0;// number of years observed

		// loops through farms in a given factory
		for (int i = 0; i < factory.milkDataFromFarms.size(); i++) {
//			ArrayList<String> yearsList = new ArrayList<String>();// list of years to act as count
//			ArrayList<String> monthsList = new ArrayList<String>();// list of months to act as count
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
		LocalDate dateBefore = LocalDate.of(Integer.parseInt(year1), Integer.parseInt(month1), Integer.parseInt(day1));
		LocalDate dateAfter = LocalDate.of(Integer.parseInt(year2), Integer.parseInt(month2), Integer.parseInt(day2));
		long diffOfDays = ChronoUnit.DAYS.between(dateBefore, dateAfter);
		average = (int) (total / diffOfDays);
		return average;

	}

	/**
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 */
	public Integer getMinInDateRange(String day1, String month1, String year1,String day2, String month2, String year2) {
		return null;
	}

	/**
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return
	 */
	public Integer getMaxInDateRange(String day1, String month1, String year1,String day2, String month2, String year2) {
		return null;
	}
}
