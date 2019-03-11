package ch.oconnor.view;

import ch.oconnor.backend.Film;
import ch.oconnor.backend.Kinobuchungssystem;
import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class Controller {

	@FXML
	private JFXDatePicker dateField;

	@FXML
	private VBox movieContainer;





	private static Kinobuchungssystem KBS = new Kinobuchungssystem();


	@FXML
	public void initialize() {

		dateField.setValue(LocalDate.of(2019, 3, 1));

		initFilm();

	}


	private void initFilm(){

		for(Film film : KBS.getFilmList()) {

			ImageView imageView = new ImageView();
			imageView.setFitHeight(100);
			imageView.setFitWidth(80);
			imageView.setImage(film.getImg());

			GridPane gridPane = new GridPane();
			gridPane.add(imageView,0,0);
			gridPane.add(new Label(film.getName()),1,0);
			gridPane.add(new Label(film.getDesc()),1,1);


			movieContainer.getChildren().add(gridPane);

		}

	}




}
