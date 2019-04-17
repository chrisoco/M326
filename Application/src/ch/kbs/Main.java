/*
 *
 * M326 LB Kinobuchungssystem
 *
 */

package ch.kbs;

/**
 *
 * @author Christopher O'Connor
 * @date 10/05/2019
 * @version 1.0
 *
 * Main Klass
 *
 * Initializes FXML - GUI and starts the Application.
 *
 */

import ch.kbs.controll.Controller;
import ch.kbs.model.DB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {

	public Stage primaryStage;
	public BorderPane rootPane;

	public static DB db = new DB();


	@Override
	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("M326 Kinobuchungssystem");

		initFXML();

	}

	/**
	 * Initialize FXML File and start Application.
	 */
	private void initFXML() {

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/Layout.fxml"));
			loader.setController(new Controller());
			rootPane = (BorderPane) loader.load();

			primaryStage.centerOnScreen();
			primaryStage.setScene(new Scene(rootPane));
			primaryStage.setResizable(false);
			primaryStage.show();

		} catch (Exception e) { e.printStackTrace(); }

	}

	public static void main(String[] args) {
		launch(args);
	}

}
