/*
 *
 * M326 LB Kinobuchungssystem
 *
 */

package ch.junit;

import ch.kbs.Main;
import ch.kbs.controll.Controller;
import ch.kbs.model.Film;
import ch.kbs.model.Vorstellung;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Christopher O'Connor
 * @date 10/05/2019
 * @version 1.0
 *
 * UnitTests
 *
 *
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UnitTests extends Application {

	static Controller controller;
	static Thread t;

	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/Layout.fxml"));
		loader.setController(controller);

		BorderPane rootPane = (BorderPane) loader.load();

		primaryStage.centerOnScreen();
		primaryStage.setScene(new Scene(rootPane));
		primaryStage.setResizable(false);
		primaryStage.show();

	}


	@BeforeAll
	public static void beforeClass() throws Exception {

		System.out.println("\n/> Starting UnitTests\n/> Starting JFX-GUI\n");

		controller = new Controller();

		t   = new Thread(() -> launch());

		t.start();
		t.sleep(7000);

	}


	@Test
	@Order(1)
	public void defaultDate() {

		System.out.println("\t/> Testing Default DateValue");
		assertEquals(controller.dateField.getValue(), LocalDate.of(2019,3,1));

	}


	@Test
	@Order(2)
	public void defaultMovies() {

		System.out.println("\t/> Testing Default Movies Correctly Added from DB");
		assertFalse(controller.KBS.getFilmMap().isEmpty());

	}


	@Test
	@Order(3)
	public void todayMovies() {

		System.out.println("\t/> Testing Today's Movies");

		Map<String, Film> map = controller.KBS.getCurFilmMap(controller.dateField.getValue());

		assertTrue(map.containsKey("Alita Battle Angel"));
		assertTrue(map.containsKey("Captain Marvel"));
		assertTrue(map.containsKey("Chaos im Netz"));
		assertTrue(map.containsKey("Hard Powder"));

	}


	@Test
	@Order(4)
	public void tomorrowMovies() {

		System.out.println("\t/> Testing Movies from 2019/03/02");

		Map<String, Film> map = controller.KBS.getCurFilmMap(LocalDate.of(2019, 3, 2));

		assertTrue(map.containsKey("Empty"));
		assertTrue(map.size() == 1);

	}


	@Test
	@Order(5)
	public void showVorstellung() throws Exception {

		System.out.println("\t/> Testing Show Vorstellung -> Alita Battle Angel 18:15");

		GridPane gp   = (GridPane) controller.movieContainer.getChildren().get(0);
		HBox timeBox  = (HBox) gp.getChildren().get(2);
		JFXButton btn = (JFXButton) timeBox.getChildren().get(1);

		controller.currVorstellung = (Vorstellung) btn.getUserData();

		Platform.runLater(() -> controller.showVorstellung());
		t.sleep(5000);

		assertEquals(controller.currVorstellung.getTimeLoc(), controller.selSaalLabel.getText());

	}


	@Test
	@Order(6)
	public void selectSeat() throws Exception {

		System.out.println("\t/> Selecting Seat A6");

		JFXCheckBox cb = (JFXCheckBox) controller.seatGrid.getChildren().get(5);
		Platform.runLater(() -> cb.fire());
		t.sleep(6000);

		assertTrue(cb.isSelected());

		System.out.println("\t/> Reserving Seat A6");

	}


	@Test
	@Order(7)
	public void resSeat() throws Exception {

		controller.userPhoneField.setText("0765801234");
		controller.resButton.setDisable(false);
		t.sleep(6000);

		Platform.runLater(() -> controller.resButton.fire());
		t.sleep(6000);
		Platform.runLater(() -> controller.jfxDialog.close());
		t.sleep(6000);

		assertTrue(controller.resList.isEmpty());
	}


	@Test
	@Order(8)
	void removeRes() throws Exception {

		System.out.println("\t/> Removing Reservation of Seat A6");

		Platform.runLater(() -> controller.showRes());
		t.sleep(6000);

		HBox box = (HBox) ((VBox)((ScrollPane)((JFXDialogLayout) controller.jfxDialog.getContent()).getBody().get(0))
																   .getContent()).getChildren().get(0);

		JFXButton button = (JFXButton) box.getChildren().get(1);

		fireButton(button);
		fireButton(button);
		fireButton(button);

		JFXButton apply = (JFXButton) ((JFXDialogLayout) controller.jfxDialog.getContent()).getActions().get(0);
		Platform.runLater(() -> apply.fire());
		t.sleep(6000);

		assertTrue(true);

	}


	@Test
	@Order(9)
	public void showVorstellung02() throws Exception {

		System.out.println("\t/> Testing Show Vorstellung -> Captain Marvel 20:30");

		GridPane gp   = (GridPane) controller.movieContainer.getChildren().get(1);
		HBox timeBox  = (HBox) gp.getChildren().get(2);
		JFXButton btn = (JFXButton) timeBox.getChildren().get(2);

		controller.currVorstellung = (Vorstellung) btn.getUserData();

		Platform.runLater(() -> controller.showVorstellung());
		t.sleep(10000);

		assertEquals(controller.currVorstellung.getTimeLoc(), controller.selSaalLabel.getText());

	}


	@Test
	@Order(10)
	public void resMultipleSeats() throws Exception {


		System.out.println("\t/> Selecting Seats A1, A2");

		for(int i = 0; i < 10; i++) {
			JFXCheckBox cb = (JFXCheckBox) controller.seatGrid.getChildren().get(i);
			Platform.runLater(() -> cb.fire());
		}


		t.sleep(3000);

		assertTrue(((JFXCheckBox) controller.seatGrid.getChildren().get(0)).isSelected());

		resSeat();

	}


	@Test
	@Order(11)
	void removeMultipleRes() throws Exception {

		System.out.println("\t/> Removing Reservation of Seat A1, A2");

		Platform.runLater(() -> controller.showRes());
		t.sleep(6000);

		VBox box = ((VBox)((ScrollPane)((JFXDialogLayout) controller.jfxDialog.getContent()).getBody().get(0))
				.getContent());

		JFXButton b1 = (JFXButton) ((HBox) box.getChildren().get(0)).getChildren().get(1);
		JFXButton b2 = (JFXButton) ((HBox) box.getChildren().get(1)).getChildren().get(1);

		for(int i = 2; i < 10; i++) {
			final int j = i;
			Platform.runLater(() -> ((JFXButton) ((HBox) box.getChildren().get(j)).getChildren().get(1)).fire());

		}

		fireButton(b1);
		fireButton(b2);
		fireButton(b2);

		fireButton((JFXButton) ((JFXDialogLayout) controller.jfxDialog.getContent()).getActions().get(0));

		assertTrue(true);

		t.sleep(15000);

	}


	private void fireButton(JFXButton button) throws Exception {
		Platform.runLater(() -> button.fire());
		t.sleep(6000);
	}


}