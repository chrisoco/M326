package ch.oconnor;

import ch.oconnor.backend.DB;
import ch.oconnor.backend.Kinobuchungssystem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {

	public Stage primaryStage;
	public BorderPane rootPane;

	public static DB db = new DB();
	public static Kinobuchungssystem KBS = new Kinobuchungssystem();


	@Override
	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("M326 Kinobuchungssystem");


		initFXML();

	}


	private void initFXML() {

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Layout.fxml"));
			rootPane = (BorderPane) loader.load();

			primaryStage.setScene(new Scene(rootPane));
			primaryStage.setResizable(false);
			primaryStage.show();

		} catch (Exception e) {}

	}



	public static void main(String[] args) {
		launch(args);
	}

}
