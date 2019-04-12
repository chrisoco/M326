package ch.kbs.controll;

import ch.kbs.*;
import ch.kbs.model.Film;
import ch.kbs.model.Kinobuchungssystem;
import ch.kbs.model.Platz;
import ch.kbs.model.Vorstellung;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller {

	@FXML
	private JFXDatePicker dateField;
	@FXML
	private VBox movieContainer;
	@FXML
	private VBox saalSitz;
	@FXML
	private JFXTextField userPhoneField;
	@FXML
	private Label selMovieLabel;
	@FXML
	private Label selSaalLabel;
	@FXML
	private Label selSeat;
	@FXML
	private JFXButton resButton;
	@FXML
	private JFXButton showRes;
	@FXML
	private StackPane dialogPane;


	private GridPane seatGrid;
	private Vorstellung currVorstellung;


	private static Kinobuchungssystem KBS;
	private static List<Platz> resList;
	private static List<Platz> remList;


	@FXML
	public void initialize() {

		KBS = new Kinobuchungssystem();
		resList = new ArrayList<>();
		remList = new ArrayList<>();

		dateField.setValue(LocalDate.of(2019, 3, 1));

		initSaal();
		initFilm();

	}


	public void initFilm(){

		movieContainer.getChildren().clear();
		movieContainer.setSpacing(20);

		KBS.getCurFilmMap(dateField.getValue()).forEach((key, film) -> {

			ImageView imageView = new ImageView();
			imageView.setFitHeight(100);
			imageView.setFitWidth(80);
			imageView.setImage(film.getImg());

			JFXTextArea descText = new JFXTextArea();
			descText.setEditable(false);
			descText.setPrefHeight(80);
			descText.setText(film.getName() + ":\n" + film.getDesc());


			GridPane gridPane = new GridPane();
			gridPane.setHgap(10);
			gridPane.setVgap(10);

			gridPane.add(imageView,0,0, 1,3);
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

				b.setOnAction(event -> {
					currVorstellung = (Vorstellung) b.getUserData();
					showVorstellung();
					resList.clear();
				});

				timeBox.getChildren().add(b);
			}

		}

		return timeBox;

	}

	private void showVorstellung() {

		seatGrid.getChildren().clear();

		selMovieLabel.setText(currVorstellung.getFilm().getName());
		selSaalLabel .setText(currVorstellung.getTimeLoc());
		selSeat.setText("");
		showRes.setVisible(true);

		for(Platz p : currVorstellung.getPlatzres().getPlatzList()) {

			JFXCheckBox seat = new JFXCheckBox();
			seat.setUserData(p);

			if(p.getBesucherTel() != null) seatStatusBooked(seat);

			seat.setOnAction( event -> {

				if(seat.isSelected()) resList.add((Platz) seat.getUserData());
				else resList.remove(seat.getUserData());
				selSeat.setText(selectedPlaetze());

			});

			seatGrid.add(seat, p.getNum(), p.getReihe().charAt(0) - 64);

		}

	}

	@FXML
	private void placeBooking() {

		for (Platz p : resList) {

			Main.db.resSeat (p, getPhoneNumFormatted(), currVorstellung.getID());
			p.setBesucherTel(   getPhoneNumFormatted());

		}

		userPhoneField.clear();
		resButton.setDisable(true);
		showVorstellung();
		orderComplete();
		resList.clear();

	}

	private String getPhoneNumFormatted() {

		StringBuilder raw = new StringBuilder(userPhoneField.getText().replaceAll("\\s|[-]", ""));

		raw.insert( 3, " ");
		raw.insert( 7, " ");
		raw.insert(10, " ");


		return raw.toString();
	}

	private void seatStatusBooked(JFXCheckBox cb) {

		cb.setSelected(true);
		cb. setDisable(true);

		cb.setCheckedColor(Paint.valueOf("#b71010"));
		cb.setTooltip(new Tooltip(((Platz) cb.getUserData()).getBesucherTel()));


	}

	private String selectedPlaetze() {

		if (resList.isEmpty()) return "";

		String s = "P: ";

		for (Platz p : resList) {
			s += "[" + p.getReihe() + " " + p.getNum() + "] ";
		}

		return s;
	}


	@FXML
	private void phoneValidator() {

		String input = userPhoneField.getText().replaceAll("\\s|[-]", "");

		if(!resList.isEmpty()) resButton.setDisable(!input.matches("07\\d{8}"));
		else resButton.setDisable(true);

	}

	@FXML
	private void showRes() {

		remList.clear();

		JFXDialogLayout content = new JFXDialogLayout();
		content.setHeading(new Text(currVorstellung.getVorstellungInfo()));

		ScrollPane sc = new ScrollPane();

		VBox resSeats = new VBox(2);

		for(Platz p : currVorstellung.getPlatzres().getResList()) {

			HBox seatRow = new HBox(120);
			seatRow.setStyle("-fx-background-color: #e5fffc");
			seatRow.setPadding(new Insets(10,5,10,5));

			Text text = new Text(p.getResInfo());

			JFXButton btn = new JFXButton("Delete");
			btn.setButtonType(JFXButton.ButtonType.RAISED);
			btn.setStyle("-fx-background-color: #bf3f3f");

			btn.setOnAction(e -> {

				if(btn.getText().equals("Delete")) {
					seatRow.setStyle("-fx-background-color: #bf3f3f");
					btn.setText("Cancel");
					btn.setStyle("-fx-background-color: #e5fffc");
					remList.add(p);
				} else {
					seatRow.setStyle("-fx-background-color: #e5fffc");
					btn.setText("Delete");
					btn.setStyle("-fx-background-color: #bf3f3f");
					remList.remove(p);
				}

			});


			seatRow .getChildren().add(text   );
			seatRow .getChildren().add(btn    );
			resSeats.getChildren().add(seatRow);

		}

		sc.setContent(resSeats);

		content.setBody(sc);

		JFXDialog jfxDialog = new JFXDialog(dialogPane, content, JFXDialog.DialogTransition.TOP);

		JFXButton apply = new JFXButton(" APPLY ");
		apply.setButtonType(JFXButton.ButtonType.RAISED);
		apply.setStyle("-fx-background-color: #b3ffb1");
		apply.setPadding(new Insets(10,10,10,10));

		apply.setOnAction(e -> {

			resList.clear();

			if(remList.isEmpty()) {
				jfxDialog.close();
				return;
			}

			for (Platz p : remList) {
				p.setBesucherTel(null);
				Main.db.remBooking(p, currVorstellung.getID());
			}

			showVorstellung();
			jfxDialog.close();

		});


		JFXButton cancel = new JFXButton(" CANCEL ");
		cancel.setButtonType(JFXButton.ButtonType.RAISED);
		cancel.setStyle("-fx-background-color: #ff9f54");
		cancel.setPadding(new Insets(10,10,10,10));
		cancel.setOnAction(e -> jfxDialog.close());

		content.setActions(apply, cancel);

		jfxDialog.show();

	}

	private void orderComplete() {

		JFXDialogLayout content = new JFXDialogLayout();
		content.setHeading(new Text("Order Accepted!"));


		VBox box = new VBox();

		box.getChildren().add(new Text(currVorstellung.getVorstellungInfo() + "\n"));

		for (Platz p : resList) {
			box.getChildren().add(new Text(p.getResInfo()));
		}


		content.setBody(box);
		JFXButton btn = new JFXButton("OK");

		btn.setButtonType(JFXButton.ButtonType.RAISED);
		btn.setStyle("-fx-background-color: #b3ffb1");
		btn.setPadding(new Insets(10,10,10,10));

		JFXDialog jfxDialog = new JFXDialog(dialogPane, content, JFXDialog.DialogTransition.TOP);

		btn.setOnAction(e -> jfxDialog.close());

		content.setActions(btn);

		jfxDialog.show();

	}

}
