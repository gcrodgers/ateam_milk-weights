package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	
	@Override
	public void start(Stage primaryStage) throws Exception {
      Stage myWindow = primaryStage;
      myWindow.setTitle("Milk Weights File Input");
      
      Button fileButton = new Button("Choose One CSV File");
      Button multiButton = new Button("Choose Multiple CSV Files");
      
      FileChooser fileChooser = new FileChooser();
      fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("CSV Files", "*csv"));
      
      fileButton.setOnAction(e ->{
        File singleFile = fileChooser.showOpenDialog(primaryStage);
      });
      
      multiButton.setOnAction(e ->{
        List<File> getFiles = fileChooser.showOpenMultipleDialog(primaryStage);
      });
      
      VBox grid = new VBox(10);
      grid.setPadding(new Insets(20,20,20,20));
      grid.getChildren().addAll(fileButton, multiButton);
      
      Scene scene = new Scene(grid, 200, 100);
      myWindow.setScene(scene);
 	  myWindow.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		   launch(args);
	}
}