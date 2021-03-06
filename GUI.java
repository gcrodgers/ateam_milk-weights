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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * This class represents a GUI used for Milk Weight information
 * @author garre
 *
 */
public class GUI extends Application {

	File singleFile; // holds the single file the user chooses
	List<File> multiFiles; // holds multiple files of the users choice
	FileManager manageFiles;
	DataManager manageData;

	/**
	 * This method is the beginning of the GUI
	 * 
	 * @param primaryStage - the primary stage
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
	  Stage myWindow = primaryStage;
      myWindow.setTitle("Milk Weights File Input");
      
      manageFiles = new FileManager();

	  // one button lets the user retrieve one CSV file, the other allows
	  // multiple CSV files
	  Button fileButton = new Button("Choose One Farm CSV File");
	  Button multiButton = new Button("Choose Multiple Farm CSV Files");

	  // FileChooser allows easy access to choose files
	  FileChooser fileChooser = new FileChooser();
	  // only allow users to choose files of csv type
	  fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*csv"));
	  
	  // single file button click
	  fileButton.setOnAction(e -> {
	    singleFile = fileChooser.showOpenDialog(primaryStage);
	    if(singleFile != null) {
	      manageFiles.inputFile = singleFile.toString();
	      manageFiles.readFile();
	      manageWindow();
		  myWindow.close();
	    }
      });

	  // multiple files button clock
	  multiButton.setOnAction(e -> {
        multiFiles = fileChooser.showOpenMultipleDialog(primaryStage);
        if(multiFiles != null) {
          for(int i = 0; i < multiFiles.size(); i++) {
            manageFiles.inputFile = multiFiles.get(i).toString();
            manageFiles.readFile();
          }
        
		  manageWindow();
		  myWindow.close();
        }
	  });

	  // add nodes
	  VBox grid = new VBox(10);
	  grid.setPadding(new Insets(20, 20, 20, 20));
	  grid.getChildren().addAll(fileButton, multiButton);

	  // create scene
	  Scene scene = new Scene(grid, 250, 100);
	  myWindow.setScene(scene);
	  myWindow.show();
	}

	/**
	 * This method creates a main menu type of window that
	 * gives the user multiple choices of what they want to do 
	 * with a file or files.
	 */
	public void manageWindow() {
	  Stage manageWindow = new Stage();
	  Insets insets = new Insets(10);
	  
	  manageData = new DataManager(manageFiles.factory);

	  manageWindow.setTitle("Manage and Display Data");
		
	  //create the necessary labels, text fields, and buttons
	  Label labelFarm = new Label("Please enter a farm ID:");
	  Label yearLabel = new Label("Please enter a year:");
	  TextField farmText = new TextField();
	  TextField yearText = new TextField();
	  Button enterButton = new Button("Enter");
	  Button allButton = new Button("Display all data for year");
	  Button annualButton = new Button("Do an Annual Report");
	  Button monthlyButton = new Button("Do a Monthly Report");
	  Button dateRangeButton = new Button("Do a Date Range Report");
	  Button moreFiles = new Button("Add more files");
	  Button exitProgram = new Button("Exit program");
		
	  //this button will take the user to an annual report window
	  annualButton.setOnAction(e -> {
	    annualWindow();
	    manageWindow.close();
	  });
		
	  //this button will take the user to a monthly report window
	  monthlyButton.setOnAction(e -> {
	    monthlyWindow();
		manageWindow.close();
	  });
	  
	  dateRangeButton.setOnAction(e -> {
	    dateRangeWindow();
	    manageWindow.close();
	  });
	  
	  //allows more files to be chosen 
	  moreFiles.setOnAction(e -> {
	    Stage temp = new Stage(); 
	    try {
			start(temp);
			manageWindow.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	  });
	  
	  exitProgram.setOnAction(e -> {
	    System.exit(0);
	  });
	  
	  //this button is when the user enters a year and farm ID
	  enterButton.setOnAction(e -> {
	    String farmID = farmText.getText();
		String year = yearText.getText();
	    if (farmText.getText().equals("") || yearText.getText().equals("")) {
	      System.out.println("Please enter both a year and farm ID");
	    }
	    else if(!(year.matches("[0-9]+"))) {
	      System.out.println("Please enter a valid, numerical year.");
	    }
	    else {
		  System.out.println("farmID: " + farmID + " and year: " + year + " entered");
		  farmReportChart(farmID, year);
	    }
	  });

	  //this button uses all availible data
	  allButton.setOnAction(e -> {
	    String year = yearText.getText();
	    if(year.equals("") || !(year.matches("[0-9]+"))) {
	      System.out.println("Please enter a valid year for Display All");
	    }
	    else {
	      displayAllChart(year);
	      manageWindow.close();
	    }
	  });

	  
	  Text farmReport = new Text("Farm Report");
	  farmReport.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));

	  Label orLabel = new Label("OR");
	  
	  Text options = new Text("More Options");
	  options.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
	  
	  //create a vbox and add nodes
	  VBox vbox = new VBox(10);
	  vbox.getChildren().addAll(farmReport, labelFarm, farmText, yearLabel,
	    yearText, enterButton, orLabel, allButton, options, annualButton, monthlyButton,
	    dateRangeButton, moreFiles, exitProgram);

	  //add some buttons for modifying file data
	  Button addData = new Button("Add Milk Data");
	  Button removeData = new Button("Remove Milk Data");

	  //this button will take a user to a window to add or edit data
	  addData.setOnAction(e -> {
	    addWindow();
	  });

	  //this button will take a user to a window to remove data
	  removeData.setOnAction(e -> {
	    removeWindow();
	  });

	  Text hBoxTitle = new Text("Change Data");
	  hBoxTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));

	  //create an hbox and add nodes
	  HBox hbox = new HBox(10);
	  hbox.getChildren().addAll(hBoxTitle, addData, removeData);
	  
	  //create a BorderPane to make it look nice
	  BorderPane borderPane = new BorderPane();
	  borderPane.setTop(hbox);
	  BorderPane.setMargin(hbox, insets);
	  borderPane.setCenter(vbox);
	  BorderPane.setMargin(vbox, insets);

	  //create the scene
	  Scene secondScene = new Scene(borderPane, 500, 500);
	  manageWindow.setScene(secondScene);
	  manageWindow.show();
	}

	/**
	 * This method produces a farm report based off of the farm ID and year
	 * given by the user
	 * 
	 * @param year - the year given by the user
	 */
	public void farmReportChart(String farmID, String year) {
	  Stage chartStage = new Stage();
	  chartStage.setTitle(year + " Milk Weight Information");
	
	  //create the x and y axis for the bar chart
	  CategoryAxis x = new CategoryAxis();
	  NumberAxis y = new NumberAxis();

	  //set the labels for the chart
	  x.setLabel("Month");
	  y.setLabel("Milk Weight");

	  //create the bar chart
	  BarChart milkBarChart = new BarChart(x, y);
	  XYChart.Series data = new XYChart.Series();
	  data.setName(farmID + " for " + year);
	  

	  data.getData().add(new XYChart.Data("Janruary\n" + "Percent of total:" + manageData.frPercent(farmID, year, "1"), 
			                             manageData.frMonthWeight(farmID, year, "1")));
	  data.getData().add(new XYChart.Data("February\n", manageData.frMonthWeight(farmID, year, "2")));
	  data.getData().add(new XYChart.Data("March\n", manageData.frMonthWeight(farmID, year, "3")));
	  data.getData().add(new XYChart.Data("April\n", manageData.frMonthWeight(farmID, year, "4")));
	  data.getData().add(new XYChart.Data("May\n", manageData.frMonthWeight(farmID, year, "5")));
	  data.getData().add(new XYChart.Data("June\n", manageData.frMonthWeight(farmID, year, "6")));
	  data.getData().add(new XYChart.Data("July\n", manageData.frMonthWeight(farmID, year, "7")));
	  data.getData().add(new XYChart.Data("August\n", manageData.frMonthWeight(farmID, year, "8")));
	  data.getData().add(new XYChart.Data("September\n", manageData.frMonthWeight(farmID, year, "9")));
	  data.getData().add(new XYChart.Data("October\n", manageData.frMonthWeight(farmID, year, "10")));
	  data.getData().add(new XYChart.Data("November\n", manageData.frMonthWeight(farmID, year, "11")));
	  data.getData().add(new XYChart.Data("December\n", manageData.frMonthWeight(farmID, year, "12")));

	  //add the data to the chart
	  milkBarChart.getData().add(data);

	  //create a VBox for the chart
	  VBox chartBox = new VBox(milkBarChart);

	  //create the scene
	  Scene chartScene = new Scene(chartBox, 1000, 400);

	  //set the scene
	  chartStage.setScene(chartScene);
	  chartStage.show();
	}

	/**
	 * This method creates window that will allow the user to add
	 * or edit information
	 */
	public void addWindow() {
	  Stage addWindow = new Stage();
	  
      //create the nodes for the add window
	  addWindow.setTitle("Add Farm Milk Yeild Data");
	  Label labelFarm = new Label("Please enter a farm ID:");
	  Label dateLabel = new Label("Please enter a date in YEAR-MONTH-DAY numeric format:");
	  Label weightLabel = new Label("Please enter a weight:");
	  TextField farmText = new TextField();
	  TextField dateText = new TextField();
	  TextField weightText = new TextField();
	  Button enterButton = new Button("Enter");

	  //the enter button responds to what the user input
	  enterButton.setOnAction(e -> {
	    if (farmText.getText().equals("") || dateText.getText().equals("")|| weightText.getText().equals("")) {
	      System.out.println("Please enter values for all feilds");
		} 
	    else {
		  String farmID = farmText.getText();
		  String date = dateText.getText();
		  String weight = weightText.getText();
		  String[] dateParts = date.split("-");
		  
		  if(!(weight.matches("[0-9]+"))) {
		    System.out.println("Please enter a valid weight");
		  }
		  else if (dateParts.length != 3 || !(dateParts[0].matches("[0-9]+")) ||
		    !(dateParts[1].matches("[0-9]+")) || !(dateParts[2].matches("[0-9]+")) ||
		    dateParts[1].length() > 2 || dateParts[2].length() > 2) {
		    System.out.println("Please enter a valid date.");
		  }
		  else {
 		    if (manageData.factory.insertSingleData(farmID, date, Integer.parseInt(weight))) {
		      System.out.println(weight + " successfully added to " + farmID + " for " + 
		      date);
		      addWindow.close();
		    }
		    else {
		      System.out.println(weight + " not successfully added");
		    }
		  }
		}
	  });


	  Text vBoxTitle = new Text("Add Data");
	  vBoxTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));

	  //create a vbox and add all of the nodes
	  VBox vbox = new VBox(10);
	  vbox.getChildren().addAll(vBoxTitle, labelFarm, farmText, dateLabel,
	    dateText, weightLabel, weightText, enterButton);

	  //create and set the scene
	  Scene addScene = new Scene(vbox, 500, 300);
	  addWindow.setScene(addScene);
	  addWindow.show();
	}
	
	/**
	 * This method allows the user to remove data from the file
	 */
	public void removeWindow() {
	  Stage removeWindow = new Stage();
	  Insets insets = new Insets(10);

	  //create the nodes for the remove window
	  removeWindow.setTitle("Remove Farm Milk Yeild Data");
	  Label labelFarm = new Label("Please enter a farm ID:");
	  Label dateLabel = new Label("Please enter a date in YEAR-MONTH-DAY numeric format:");
	  TextField farmText = new TextField();
	  TextField dateText = new TextField();
	  Button enterButton = new Button("Remove Selected Data");
	  Button removeAllButton = new Button("Remove All Data");

	  //the enter button allows the user to remove data given what they 
	  //input into the text fields
	  enterButton.setOnAction(e -> {
	    if (farmText.getText().equals("") || dateText.getText().equals("")) {
		  System.out.println("Please enter values for all feilds");
		} 
	    else {
		  String farmID = farmText.getText();
		  String date = dateText.getText();
		  String[] dateParts = date.split("-");
		  System.out.println("farmID: " + farmID + " and year: " + date + " entered.");
		  if (dateParts.length != 3 || !(dateParts[0].matches("[0-9]+")) ||
			  !(dateParts[1].matches("[0-9]+")) || !(dateParts[2].matches("[0-9]+")) ||
			  dateParts[1].length() > 2 || dateParts[2].length() > 2) {
	        System.out.println("Please enter a valid date.");
		  }
		  else {
		    Integer removed = manageData.factory.removeSingleData(farmID, date);
		    if(removed != null) {
		      System.out.println(removed + " successfully removed from " + date);
		      removeWindow.close();
		    }
		    else {
			  System.out.println("Data not successfully removed");
		    }
		  }
		}
	  });

	  //this button will remove all data from the given year/file
	  removeAllButton.setOnAction(e -> {
	    String farmID = farmText.getText();
	    for(int i = 0; i < manageData.factory.milkDataFromFarms.size(); i++) {
	      if(manageData.factory.milkDataFromFarms.get(i).farmID.equals(farmID)) {
	        manageData.factory.milkDataFromFarms.get(i).clearData();;
	      }
	    }
	  });

	  
	  Text vBoxTitle = new Text("Remove Data");
	  vBoxTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));

	  //create the VBox and add the nodes
	  VBox vbox = new VBox(10);
	  vbox.getChildren().addAll(vBoxTitle, labelFarm, farmText, dateLabel,
	    dateText, enterButton, removeAllButton);

	  //create and set the scene
	  Scene removeScene = new Scene(vbox, 500, 300);
	  removeWindow.setScene(removeScene);
	  removeWindow.show();
	}
	
	/**
	 * This method creates a window that is responsible for showing the user
	 * an annual report based on what they input
	 */
	public void annualWindow() {
	  Stage annualWindow = new Stage();	
	  Insets insets = new Insets(10);
	  
	  //create the nodes for the annual winodw
	  annualWindow.setTitle("Produce an Annual Report");
	  Label yearLabel = new Label("Please enter the year:");
	  TextField yearField = new TextField();
	  Button enterButton = new Button("Enter");
	  Button returnButton = new Button("Return to Manage and Display Data");
	  
	  //create the hbox and the vbox
	  HBox hbox = new HBox(10);
	  VBox vbox = new VBox(10);
	  
	  //add the nodes to the window
	  hbox.getChildren().addAll(yearLabel, yearField);
	  vbox.getChildren().addAll(enterButton, returnButton);
	  
	  //this button will create the annual report
	  enterButton.setOnAction(e -> {
	    String year = yearField.getText();
	    
	    if(!(year.matches("[0-9]+")) || yearField.getText().equals("")) {
		  System.out.println("Please enter a valid, numerical year.");
		}
	    else {
	      annualChart(year);
	    }
	  });
	  
	  //this button returns the user to the main menu
	  returnButton.setOnAction(e -> {
	    manageWindow();
	    annualWindow.close();
	  });
	  
	  //create the BorderPane for neatness
	  BorderPane borderPane = new BorderPane();
	  borderPane.setTop(hbox);
	  BorderPane.setMargin(hbox, insets);
	  borderPane.setCenter(vbox);
	  BorderPane.setMargin(vbox, insets);

	  //create the scene and set it
	  Scene annualScene = new Scene(borderPane, 300, 150);
	  annualWindow.setScene(annualScene);
	  annualWindow.show();
	}
	
	/**
	 * This method creates a bar chart with annual report information
	 * 
	 * @param year - the year the user wants the annual report for
	 */
	public void annualChart(String year) {
	  Stage chartStage = new Stage();
	  chartStage.setTitle(year + " Annual report");
	  Insets insets = new Insets(10);
	  
	  //create the x and y axis
	  CategoryAxis x = new CategoryAxis();
	  NumberAxis y = new NumberAxis();
	  
	  int noOfFarms = manageData.factory.farmIDList.size();
	  int annualWeight = 0;
	  ArrayList<String> farms = manageData.factory.farmIDList;

	  //label the x and y axis
	  x.setLabel("Farms");
	  y.setLabel("Milk Weight");

	  //create the bar chart
	  BarChart milkBarChart = new BarChart(x, y);
	  XYChart.Series data = new XYChart.Series();
	  data.setName(year);
	  
	  VBox percentBox = new VBox(10);
	  
	  for(int i = 0; i < noOfFarms; i++) {
	    annualWeight = manageData.arFarmWeight(farms.get(i), year);
	    data.getData().add(new XYChart.Data(farms.get(i), annualWeight));
	    percentBox.getChildren().add(new Label("Percent of weight for the year of " + farms.get(i) + 
	    		             " is " + manageData.arPercent(farms.get(i), year)));
	  }
	  
	  //add the data to the chart
	  milkBarChart.getData().add(data);

	  //create a VBox for the chart
	  VBox chartBox = new VBox(milkBarChart);
	  
	  //create a BorderPane to make it look nice
	  BorderPane borderPane = new BorderPane();
	  ScrollPane perc = new ScrollPane();
	  
	  perc.setPrefSize(400, 200);
	  perc.setContent(percentBox);
	  
	  borderPane.setTop(perc);
	 // BorderPane.setMargin(chartBox, insets);
	  borderPane.setCenter(chartBox);
	 // BorderPane.setMargin(percentBox, insets);

	  //create the scene
	  Scene chartScene = new Scene(borderPane, 1000, 625);

	  //set the scene
	  chartStage.setScene(chartScene);
	  chartStage.show();
	}
	
	/**
	 * This method creates a window to display a monthly report
	 */
	public void monthlyWindow() {
	  Stage monthlyWindow = new Stage();
	  
	  //create the nodes for the monthly report window
	  monthlyWindow.setTitle("Produce a Monthly Report");
	  Label monthLabel = new Label("Please enter the month in numeric format:");
	  TextField monthField = new TextField();
	  Label yearLabel = new Label("Please enter the year:");
	  TextField yearField = new TextField();
	  Button enterButton = new Button("Enter");
	  Button returnButton = new Button("Return to Manage and Display Data");
	  
	  //create a new vbox
	  VBox vbox = new VBox(10);
	  
	  //this button will create a monthly report bar chart
      enterButton.setOnAction(e -> {
    	String month = monthField.getText();
        String year = yearField.getText();
  	    
  	    if(!(year.matches("[0-9]+")) || yearField.getText().equals("") ||
  	    	!(month.matches("[0-9]+")) || monthField.getText().equals("")) {
  		  System.out.println("Please enter a valid, numerical year and month.");
  		}
  	    else {
  	      monthlyChart(month, year);
  	    }
	  });
	  
      //this button will return the user to the main menu
	  returnButton.setOnAction(e -> {
	    manageWindow();
		monthlyWindow.close();
	  });
	
	  //add the nodes to the vbox
	  vbox.getChildren().addAll(monthLabel, monthField, yearLabel, 
	    yearField, enterButton, returnButton);
	  
	  //create and set the scene
	  Scene monthlyScene = new Scene(vbox, 300, 200);
	  monthlyWindow.setScene(monthlyScene);
	  monthlyWindow.show();
	}
	
	/**
	 * This method will create a monthly report bar chart for the
	 * given month and year
	 * 
	 * @param month - the month that the user input
	 * @param year - the year that the user input
	 */
	public void monthlyChart(String month, String year) {
	  Stage chartStage = new Stage();
	  chartStage.setTitle(month + " Monthly Report");
	  
	  //create the x and y axis
	  CategoryAxis x = new CategoryAxis();
	  NumberAxis y = new NumberAxis();

	  //label the x and y axis
	  x.setLabel("Farms");
	  y.setLabel("Milk Weight");

	  //create the bar chart
	  BarChart milkBarChart = new BarChart(x, y);
	  XYChart.Series data = new XYChart.Series();
	  data.setName(month);
	  
	  int noOfFarms = manageData.factory.farmIDList.size();
	  int monthlyWeight = 0;
	  ArrayList<String> farms = manageData.factory.farmIDList;
	  
	  VBox percentBox = new VBox(10);
	  
	  for(int i = 0; i < noOfFarms; i++) {
	    monthlyWeight = manageData.mrFarmWeight(farms.get(i), month, year);
		data.getData().add(new XYChart.Data(farms.get(i), monthlyWeight));
		percentBox.getChildren().add(new Label("Farm " + farms.get(i) + " contributed " + 
		                             manageData.mrPercent(farms.get(i), month, year) + 
		                             "for month: " + month));
	  }
	  
	  //add the data to the chart
	  milkBarChart.getData().add(data);

	  //create a VBox for the chart
	  VBox chartBox = new VBox(milkBarChart);
	  
	  //create a BorderPane to make it look nice
	  BorderPane borderPane = new BorderPane();
	  ScrollPane perc = new ScrollPane();
	  
	  perc.setPrefSize(400, 200);
	  perc.setContent(percentBox);
	  
	  borderPane.setTop(perc);
	  borderPane.setCenter(chartBox);

	  //create the scene
	  Scene chartScene = new Scene(borderPane, 1000, 625);

	  //set the scene
	  chartStage.setScene(chartScene);
	  chartStage.show();
	}
	
	public void dateRangeWindow() {
	  Stage dateRangeWindow = new Stage();
		  
	  //create the nodes for the date range report window
	  dateRangeWindow.setTitle("Produce a Monthly Report");
	  Label startDateLabel = new Label("Please enter the start date in YEAR-" +
	                                   "MONTH-DAY format:");
	  TextField startDateField = new TextField();
	  Label endDateLabel = new Label("Please enter the end date in MONTH-DAY format:");
	  TextField endDateField = new TextField();
	  Button enterButton = new Button("Enter");
	  Button returnButton = new Button("Return to Manage and Display Data");
		  
	  //create a new vbox
	  VBox vbox = new VBox(10);
	  
	  enterButton.setOnAction(e -> {
	    String startDate = startDateField.getText();
		String[] dateParts = startDate.split("-");
		String endDate = endDateField.getText();
		String[] endParts = endDate.split("-");
		if (dateParts.length != 3 || !(dateParts[0].matches("[0-9]+")) ||
	      !(dateParts[1].matches("[0-9]+")) || !(dateParts[2].matches("[0-9]+")) ||
		  dateParts[1].length() > 2 || dateParts[2].length() > 2) {
	      System.out.println("Please enter a valid start date.");
		}
		else if(endParts.length != 2 || !(endParts[0].matches("[0-9]+")) || 
				!(endParts[1].matches("[0-9]+")) || endParts[0].length() > 2 ||
				endParts[1].length() > 2) {
		  System.out.println("Please enter a valid end date.");
		}
		else {
		  dateRangeChart(startDate, endDate, dateParts, endParts);
		}
	  });
	  
	  //this button will return the user to the main menu
	  returnButton.setOnAction(e -> {
	    manageWindow();
	    dateRangeWindow.close();
	  });
	
	  //add the nodes to the vbox
	  vbox.getChildren().addAll(startDateLabel, startDateField, endDateLabel, 
	    endDateField, enterButton, returnButton);
	  
	  //create and set the scene
	  Scene monthlyScene = new Scene(vbox, 300, 200);
	  dateRangeWindow.setScene(monthlyScene);
	  dateRangeWindow.show();
	}

	public void dateRangeChart(String startDate, String endDate, 
			                   String[] dateParts, String[] endParts) {
		Stage chartStage = new Stage();
		chartStage.setTitle(startDate + " to " + endDate + " range report.");
		//create the x and y axis
		CategoryAxis x = new CategoryAxis();
		NumberAxis y = new NumberAxis();
		  
		int noOfFarms = manageData.factory.farmIDList.size();
		int weight = 0;
		ArrayList<String> farms = manageData.factory.farmIDList;

		//label the x and y axis
		x.setLabel("Farms");
		y.setLabel("Milk Weight");

		//create the bar chart
		BarChart milkBarChart = new BarChart(x, y);
		XYChart.Series data = new XYChart.Series();
		data.setName(startDate + " to " + endDate);
		
		VBox percentBox = new VBox(10);
		
		for(int i = 0; i < noOfFarms; i++) {
	      weight = manageData.drFarmWeight(farms.get(i), dateParts[0], dateParts[1], 
	    		                           dateParts[2], endParts[0], endParts[1]);
		  data.getData().add(new XYChart.Data(farms.get(i), weight));
		  percentBox.getChildren().add(new Label("Farm " + farms.get(i) + " contribued " +
		                               manageData.drPercent(farms.get(i), dateParts[0], dateParts[1], 
		                               dateParts[2], endParts[0], endParts[1]) + " to milk in date " +
		                               "range: " + startDate + " to " + endDate));
		}
		
		//add the data to the chart
		milkBarChart.getData().add(data);

		//create a VBox for the chart
		VBox chartBox = new VBox(milkBarChart);
		  
		//create a BorderPane to make it look nice
		BorderPane borderPane = new BorderPane();
		ScrollPane perc = new ScrollPane();
		  
		perc.setPrefSize(400, 200);
		perc.setContent(percentBox);
		  
		borderPane.setTop(perc);
		borderPane.setCenter(chartBox);

		//create the scene
		Scene chartScene = new Scene(borderPane, 1000, 625);

		//set the scene
		chartStage.setScene(chartScene);
		chartStage.show();
	}
	
	
	public void displayAllChart(String year) {
	  Stage chartStage = new Stage();
	  chartStage.setTitle("All Milk Weight Information for" + year);
		
	  //create the x and y axis for the bar chart
	  CategoryAxis x = new CategoryAxis();
	  NumberAxis y = new NumberAxis();

      //set the labels for the chart
	  x.setLabel("Month");
	  y.setLabel("Milk Weight");

      //create the bar chart
	  BarChart milkBarChart = new BarChart(x, y);
	  XYChart.Series data = new XYChart.Series();
	  data.setName("All data for " + year);
	  
	  int totalForMonth = 0;
	  ArrayList<String> farms = manageData.factory.farmIDList;
	  
	  VBox percentBox = new VBox(10);
	  
	  for(int i = 0; i < 12; i++) {
	    totalForMonth = 0;
	    for(int j = 0; j < farms.size(); j++) {
	      totalForMonth += manageData.mrFarmWeight(farms.get(j), Integer.toString(i + 1), year);
	    }
	    data.getData().add(new XYChart.Data("Month " + Integer.toString(i + 1), totalForMonth));
	    percentBox.getChildren().add(new Label("Farm " + farms.get(i) + " contributed " + 
	                                 manageData.frPercent(farms.get(i), year, Integer.toString(i + 1)) + 
	                                 " milk for month: " + (i+1) + " in year " + year ));
	  }
	  
	  //add the data to the chart
	  milkBarChart.getData().add(data);

      //create a VBox for the chart
	  VBox chartBox = new VBox(milkBarChart);
			  
	  //create a BorderPane to make it look nice
	  BorderPane borderPane = new BorderPane();
	  ScrollPane perc = new ScrollPane();
			  
	  perc.setPrefSize(400, 200);
	  perc.setContent(percentBox);
			  
	  borderPane.setTop(perc);
	  borderPane.setCenter(chartBox);

	  //create the scene
	  Scene chartScene = new Scene(borderPane, 1000, 625);

	  //set the scene
	  chartStage.setScene(chartScene);
	  chartStage.show();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
