package ch.oconnor.backend;

import ch.oconnor.Main;
import java.util.List;

public class Kinobuchungssystem {

	private List<Film> filmList;
	private List<Kinosaal> kinosaalList;
	private List<Vorstellung> vorstellungList;



	public Kinobuchungssystem() {

		filmList        = Main.db.getAllFilms();
		kinosaalList    = Main.db.getAllKinosaal();
		vorstellungList = Main.db.getAllVorstellungen(filmList, kinosaalList);



	}

}
