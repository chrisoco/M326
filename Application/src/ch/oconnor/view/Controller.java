package ch.oconnor.view;

import ch.oconnor.backend.Film;
import ch.oconnor.backend.Kinobuchungssystem;
import ch.oconnor.backend.Vorstellung;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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

			Label title = new Label(film.getName());
			title.setStyle("-fx-padding: 10 0 0 0;");

			JFXTextArea descText = new JFXTextArea();
			descText.setEditable(false);
//			descText.setMinHeight(60);
			descText.setText(film.getDesc());





			GridPane gridPane = new GridPane();
			gridPane.setHgap(10);
			gridPane.setVgap(10);

			gridPane.add(imageView,0,0, 1,3);
			gridPane.add(title,1,0);
			gridPane.add(descText,1,1);
			gridPane.add(getFilmTimes(film), 1,2);


			movieContainer.getChildren().add(gridPane);

		}

	}

	private VBox getFilmTimes(Film film) {

		VBox timeBox = new VBox();

		for (Vorstellung v : KBS.getVorstellungList()) {

			if (dateField.getValue().isEqual(v.getZeit().toLocalDate()) && film.equals(v.getFilm())) {
				// System.out.println(v.getZeit().toLocalTime().toString());
				timeBox.getChildren().add(new Button(v.getZeit().toLocalTime().toString()));
			}

		}



		return timeBox;

	}


}
