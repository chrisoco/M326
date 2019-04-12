package ch.kbs.model;


import ch.kbs.Main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Kinobuchungssystem {

	private Map<String,Film>      filmMap;
	private Map<String,Film>      curFilm;
	private Map<String, Kinosaal> kinosaalMap;
	private List<Vorstellung>     vorstellungList;


	public Kinobuchungssystem() {

		filmMap         = Main.db.getAllFilms();
		kinosaalMap     = Main.db.getAllKinosaal();
		vorstellungList = Main.db.getAllVorstellungen(filmMap, kinosaalMap);

	}

	public List<Vorstellung> getVorstellungList() {
		return vorstellungList;
	}

	public Map<String, Film> getCurFilmMap(LocalDate date) {

		curFilm = Main.db.getCurrFilms(this.filmMap, date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

		return this.curFilm;

	}

}
