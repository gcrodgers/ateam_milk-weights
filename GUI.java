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
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class GUI extends Application {

	File singleFile; // holds the single file the user chooses
	List<File> multiFiles; // holds multiple files of the users choice

	@Override
	public void start(Stage primaryStage) throws Exception {
		Stage myWindow = primaryStage;
		myWindow.setTitle("Milk Weights File Input");

		// one button lets the user retrieve one CSV file, the other allows
		// multiple CSV files
		Button fileButton = new Button("Choose One Farm CSV File");
		Button multiButton = new Button("Choose Multiple Farm CSV Files");

		// FileChooser allows easy access to choose files
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*csv"));

		// single file button click
		fileButton.setOnAction(e -> {
			singleFile = fileChooser.showOpenDialog(primaryStage);
			manageWindow();
			myWindow.close();
		});

		// multiple files button clock
		multiButton.setOnAction(e -> {
			multiFiles = fileChooser.showOpenMultipleDialog(primaryStage);
			manageWindow();
			myWindow.close();
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
	 * This method creates a new window for information
	 */
	public void manageWindow() {
		Stage manageWindow = new Stage();
		Insets insets = new Insets(10);

		manageWindow.setTitle("Manage and Display Data");
		Label labelFarm = new Label("Please enter a farm ID:");
		Label yearLabel = new Label("Please enter a year:");
		TextField farmText = new TextField();
		TextField yearText = new TextField();
		Button enterButton = new Button("Enter");
		Button allButton = new Button("Display all data");
		Button annualButton = new Button("Do an Annual Report");
		Button monthlyButton = new Button("Do a Monthly Report");
		
		annualButton.setOnAction(e -> {
		  annualWindow();
		  manageWindow.close();
		});
		
		monthlyButton.setOnAction(e -> {
		  monthlyWindow();
		  manageWindow.close();
		});

		enterButton.setOnAction(e -> {
			if (farmText.getText().equals("") || yearText.getText().equals("")) {
				System.out.println("Please enter both a year and farm ID");
			} else {
				String farmID = farmText.getText();
				String year = yearText.getText();
				System.out.println("farmID: " + farmID + " and year: " + year + " entered");
				farmReportChart(year);
			}
		});

		allButton.setOnAction(e -> {
			System.out.println("You pressed the display all button, it does not work yet!");
		});

		Text vBoxTitle = new Text("Farm Report");
		vBoxTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));

		Label orLabel = new Label("OR");

		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(vBoxTitle, annualButton, monthlyButton, labelFarm, 
		  farmText, yearLabel, yearText, enterButton, orLabel, allButton);

		Button addData = new Button("Add Milk Data");
		Button removeData = new Button("Remove Milk Data");

		addData.setOnAction(e -> {
			addWindow();
		});

		removeData.setOnAction(e -> {
			removeWindow();
		});

		Text hBoxTitle = new Text("Change Data");
		hBoxTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));

		HBox hbox = new HBox(10);
		hbox.getChildren().addAll(hBoxTitle, addData, removeData);

		BorderPane borderPane = new BorderPane();
		borderPane.setTop(hbox);
		BorderPane.setMargin(hbox, insets);
		borderPane.setCenter(vbox);
		BorderPane.setMargin(vbox, insets);

		Scene secondScene = new Scene(borderPane, 500, 400);
		manageWindow.setScene(secondScene);
		manageWindow.show();
	}

	public void farmReportChart(String year) {
		Stage chartStage = new Stage();
		chartStage.setTitle(year + " Milk Weight Information");
		CategoryAxis x = new CategoryAxis();
		NumberAxis y = new NumberAxis();

		x.setLabel("Month");
		y.setLabel("Milk Weight");

		BarChart milkBarChart = new BarChart(x, y);
		XYChart.Series data = new XYChart.Series();
		data.setName(year);

		// NOTE: THIS IS SAMPLE DATA FOR A2, THE FINAL PRODUCT WILL RETRIEVE
		// MILK INFO FROM FILE

		data.getData().add(new XYChart.Data("1", 8500));
		data.getData().add(new XYChart.Data("2", 8300));
		data.getData().add(new XYChart.Data("3", 7000));
		data.getData().add(new XYChart.Data("4", 4000));
		data.getData().add(new XYChart.Data("5", 9000));
		data.getData().add(new XYChart.Data("6", 7500));
		data.getData().add(new XYChart.Data("7", 5000));
		data.getData().add(new XYChart.Data("8", 6000));
		data.getData().add(new XYChart.Data("9", 8020));
		data.getData().add(new XYChart.Data("10", 4500));
		data.getData().add(new XYChart.Data("11", 7600));
		data.getData().add(new XYChart.Data("12", 8900));

		milkBarChart.getData().add(data);

		VBox chartBox = new VBox(milkBarChart);

		Scene chartScene = new Scene(chartBox, 400, 400);

		chartStage.setScene(chartScene);
		chartStage.show();
	}

	/**
	 * This method creates a add window for information
	 */
	public void addWindow() {
		Stage addWindow = new Stage();
		Insets insets = new Insets(10);

		addWindow.setTitle("Add Farm Milk Yeild Data");
		Label labelFarm = new Label("Please enter a farm ID:");
		Label dateLabel = new Label("Please enter a date:");
		Label weightLabel = new Label("Please enter a weight:");
		TextField farmText = new TextField();
		TextField dateText = new TextField();
		TextField weightText = new TextField();
		Button enterButton = new Button("Enter");

		enterButton.setOnAction(e -> {
			if (farmText.getText().equals("") || dateText.getText().equals("")|| weightText.getText().equals("")) {
				System.out.println("Please enter values for all feilds");
			} else {
				String farmID = farmText.getText();
				String date = dateText.getText();
				String weight = weightText.getText();
				System.out.println("farmID: " + farmID + ", year: " + date + " entered, and" + weight + " entered.");
				addWindow.close();
			}
		});


		Text vBoxTitle = new Text("Add Data");
		vBoxTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));

		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(vBoxTitle, labelFarm, farmText, dateLabel, dateText, weightLabel, weightText, enterButton);
	
		HBox hbox = new HBox(10);

		BorderPane borderPane = new BorderPane();
		borderPane.setTop(hbox);
		BorderPane.setMargin(hbox, insets);
		borderPane.setCenter(vbox);
		BorderPane.setMargin(vbox, insets);

		Scene addScene = new Scene(borderPane, 500, 300);
		addWindow.setScene(addScene);
		addWindow.show();
	}
	
	/**
	 * This method creates a remove window for information
	 */
	public void removeWindow() {
		Stage removeWindow = new Stage();
		Insets insets = new Insets(10);

		removeWindow.setTitle("Remove Farm Milk Yeild Data");
		Label labelFarm = new Label("Please enter a farm ID:");
		Label dateLabel = new Label("Please enter a date:");
		TextField farmText = new TextField();
		TextField dateText = new TextField();
		Button enterButton = new Button("Remove Selected Data");
		Button removeAllButton = new Button("Remove All Data");

		enterButton.setOnAction(e -> {
			if (farmText.getText().equals("") || dateText.getText().equals("")) {
				System.out.println("Please enter values for all feilds");
			} else {
				String farmID = farmText.getText();
				String date = dateText.getText();
				System.out.println("farmID: " + farmID + " and year: " + date + " entered.");
			removeWindow.close();
			}
		});

		removeAllButton.setOnAction(e -> {
			System.out.println("You pressed the remove all button, it does not work yet!");
		});

		Text vBoxTitle = new Text("Remove Data");
		vBoxTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));

		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(vBoxTitle, labelFarm, farmText, dateLabel, dateText, enterButton, removeAllButton);
	
		HBox hbox = new HBox(10);

		BorderPane borderPane = new BorderPane();
		borderPane.setTop(hbox);
		BorderPane.setMargin(hbox, insets);
		borderPane.setCenter(vbox);
		BorderPane.setMargin(vbox, insets);
		
		//wont work without, will look into wont accept removeWindow();
		Scene removeScene = new Scene(borderPane, 500, 300);
		removeWindow.setScene(removeScene);
		removeWindow.show();
	}
	
	public void annualWindow() {
	  Stage annualWindow = new Stage();	
	  Insets insets = new Insets(10);
	  
	  annualWindow.setTitle("Produce an Annual Report");
	  Label yearLabel = new Label("Please enter the year:");
	  TextField yearField = new TextField();
	  Button enterButton = new Button("Enter");
	  Button returnButton = new Button("Return to Manage and Display Data");
	  
	  HBox hbox = new HBox(10);
	  VBox vbox = new VBox(10);
	  
	  hbox.getChildren().addAll(yearLabel, yearField);
	  vbox.getChildren().addAll(enterButton, returnButton);
	  
	  enterButton.setOnAction(e -> {
		  
	  });
	  
	  returnButton.setOnAction(e -> {
	    manageWindow();
	    annualWindow.close();
	  });
	  
	  BorderPane borderPane = new BorderPane();
	  borderPane.setTop(hbox);
	  BorderPane.setMargin(hbox, insets);
	  borderPane.setCenter(vbox);
	  BorderPane.setMargin(vbox, insets);

	  Scene annualScene = new Scene(borderPane, 300, 150);
	  annualWindow.setScene(annualScene);
	  annualWindow.show();
	}
	
	public void annualChart(String year) {
		
	}
	
	public void monthlyWindow() {
	  Stage monthlyWindow = new Stage();
	  
	  monthlyWindow.setTitle("Produce a Monthly Report");
	  Label monthLabel = new Label("Please enter the month:");
	  TextField monthField = new TextField();
	  Label yearLabel = new Label("Please enter the year:");
	  TextField yearField = new TextField();
	  Button enterButton = new Button("Enter");
	  Button returnButton = new Button("Return to Manage and Display Data");
	  
	  VBox vbox = new VBox(10);
	  
      enterButton.setOnAction(e -> {
		  
	  });
	  
	  returnButton.setOnAction(e -> {
	    manageWindow();
		monthlyWindow.close();
	  });
	
	  vbox.getChildren().addAll(monthLabel, monthField, yearLabel, 
	    yearField, enterButton, returnButton);
	  
	  Scene monthlyScene = new Scene(vbox, 300, 200);
	  monthlyWindow.setScene(monthlyScene);
	  monthlyWindow.show();
	}
	
	public void monthlyChart(String month, String year) {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
