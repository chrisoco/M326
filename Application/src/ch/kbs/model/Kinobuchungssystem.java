/*
 *
 * M326 LB Kinobuchungssystem
 *
 */

package ch.kbs.model;

/**
 *
 * @author Christopher O'Connor
 * @date 10/05/2019
 *
 */


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

	/**
	 * Constructs a new {@link Kinobuchungssystem} & Loads all Data from {@link DB} into Maps.
	 */
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
