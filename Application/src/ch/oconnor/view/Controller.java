package ch.oconnor.view;

import ch.oconnor.backend.Film;
import ch.oconnor.backend.Kinobuchungssystem;
import ch.oconnor.backend.Platz;
import ch.oconnor.backend.Vorstellung;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.time.LocalDate;

public class Controller {

	@FXML
	private JFXDatePicker dateField;

	@FXML
	private VBox movieContainer;

	@FXML
	private VBox saalSitz;

	private GridPane seatGrid;


	private static Kinobuchungssystem KBS;


	@FXML
	public void initialize() {

		KBS = new Kinobuchungssystem();

		dateField.setValue(LocalDate.of(2019, 3, 1));

		movieContainer.setSpacing(20);


		initSaal();
		initFilm();

	}


	private void initFilm(){

		movieContainer.getChildren().removeAll();

		KBS.getFilmMap().forEach((key, film) -> {

			ImageView imageView = new ImageView();
			imageView.setFitHeight(100);
			imageView.setFitWidth(80);
			imageView.setImage(film.getImg());

//			Label title = new Label(film.getName());
//			title.setAlignment(Pos.BOTTOM_CENTER);
//			title.setStyle("-fx-font-weight: bold;");


			JFXTextArea descText = new JFXTextArea();
			descText.setEditable(false);
			descText.setText(film.getName() + ":\n" + film.getDesc());


			GridPane gridPane = new GridPane();
			gridPane.setHgap(10);
			gridPane.setVgap(10);

			gridPane.add(imageView,0,0, 1,3);
			//gridPane.add(title,1,0);
			gridPane.add(descText,1,1);
			gridPane.add(getFilmTimes(film), 1,2);


			movieContainer.getChildren().add(gridPane);

		});

	}


	private void initSaal() {

		seatGrid = new GridPane();

		seatGrid.setHgap(10);
		seatGrid.setVgap(10);

		seatGrid.setAlignment(Pos.CENTER);
		saalSitz.setSpacing(30);

		saalSitz.getChildren().add(seatGrid);


	}


	private HBox getFilmTimes(Film film) {

		HBox timeBox = new HBox(5);

		for (Vorstellung v : KBS.getVorstellungList()) {

			if (film.equals(v.getFilm()) && dateField.getValue().isEqual(v.getZeit().toLocalDate())) {

				JFXButton b = new JFXButton(v.getZeit().toLocalTime().toString());
				b.setUserData(v);
				b.setStyle("-fx-border-color: black;");

				b.setOnAction(event -> showVorstellung((Vorstellung) b.getUserData()));

				timeBox.getChildren().add(b);
			}

		}

		return timeBox;

	}

	private void showVorstellung(Vorstellung vorstellung) {

		seatGrid.getChildren().removeAll();

		for(Platz p : vorstellung.getPlatzres().getPlatzList()) {

			JFXCheckBox seat = new JFXCheckBox();
			seat.setUserData(p);

			if(p.getBesucherTel() != null) {
				seat.setSelected(true);
				seat.setDisable(true);
				seat.setCheckedColor(Paint.valueOf("#b71010"));
			}

			seat.setOnAction( event -> {
				Platz pl = (Platz) seat.getUserData();
				System.out.println(pl.getReihe() + pl.getNum());
			});

			seatGrid.add(seat, p.getNum(), p.getReihe().charAt(0) - 64);

		}

	}


}
