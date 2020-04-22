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
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class GUI extends Application {
	
	File singleFile;
	List<File> multiFiles;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
      Stage myWindow = primaryStage;
      myWindow.setTitle("Milk Weights File Input");
      
      Button fileButton = new Button("Choose One Farm CSV File");
      Button multiButton = new Button("Choose Multiple Farm CSV Files");
      
      FileChooser fileChooser = new FileChooser();
      fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("CSV Files", "*csv"));
      
      fileButton.setOnAction(e ->{
        singleFile = fileChooser.showOpenDialog(primaryStage);
        newWindow();
      });
      
      multiButton.setOnAction(e ->{
        multiFiles = fileChooser.showOpenMultipleDialog(primaryStage);
        newWindow();
      });
      
      VBox grid = new VBox(10);
      grid.setPadding(new Insets(20,20,20,20));
      grid.getChildren().addAll(fileButton, multiButton);
      
      Scene scene = new Scene(grid, 250, 100);
      myWindow.setScene(scene);
 	  myWindow.show();
	}
	
	public void newWindow() {
	  Stage secondWindow = new Stage();
	  
	  secondWindow.setTitle("Manage and Display Data");
	  Label labelFarm = new Label("Please enter a farm ID:");
	  Label yearLabel = new Label("Please enter a year:");
	  TextField farmText = new TextField();
	  TextField yearText = new TextField();
	  Button enterButton = new Button("Enter");
	  Button allButton = new Button("Display all data");
	 
	  enterButton.setOnAction(e -> { 
	   if(farmText.getText().equals("") || yearText.getText().equals("")) {
	     System.out.println("Please enter both a year and farm ID");
	   }
	   else {
	     String farmID = farmText.getText();
	     String year = yearText.getText();
	     System.out.println("farmID: " + farmID + " and year: " + year + " entered");
	     barChart(year);
	   }
	  });
	  
	  allButton.setOnAction(e -> {
	    System.out.println("You pressed the all button, it does not work yet!");  
	  });
	  
	  VBox root = new VBox(10);
	  root.getChildren().addAll(labelFarm, farmText, yearLabel, yearText, enterButton, allButton);
	  
	  Scene secondScene = new Scene(root, 300, 300);
	  secondWindow.setScene(secondScene);
	  secondWindow.show();
	}
	
	public void barChart(String year) {
	  Stage chartStage = new Stage();
	  chartStage.setTitle(year + " Milk Weight Information");
	  CategoryAxis x = new CategoryAxis();
	  NumberAxis y = new NumberAxis();
	  
	  x.setLabel("Month");
	  y.setLabel("Milk Weight");
	  
	  BarChart milkBarChart = new BarChart(x, y);
	  XYChart.Series data = new XYChart.Series();
	  data.setName(year);
	  
	  //NOTE: THIS IS SAMPLE DATA FOR A2, THE FINAL PRODUCT WILL RETRIEVE
	  //MILK INFO FROM FILE
	  
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
	 * @param args
	 */
	public static void main(String[] args) {
		   launch(args);
	}
}