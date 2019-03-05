package ch.oconnor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

	public Stage primaryStage;
	public BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("M326 Kinobuchungssystem");


	}


	public void initRootLayout() {

		try {
			
			// Load root layout from FXML file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(""));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();


		} catch (IOException e) {
			e.printStackTrace();
		}


	}



	public static void main(String[] args) {
		launch(args);
	}

}
