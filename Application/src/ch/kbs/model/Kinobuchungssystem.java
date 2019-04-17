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
 * @version 1.0
 *
 * Kinobuchungssystem Class
 *
 * Contains all Information of the Cinema in different Maps
 * filmMap     = Name of the Movie, {@link ch.kbs.model.Film} - Contains all Movies
 * kinosaalMap = Name of the Kinosaal, {@link ch.kbs.model.Kinosaal} - Contains all Kinosaale
 *
 * vorstellungList = List containing all Vorstellungen
 */

import ch.kbs.Main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Kinobuchungssystem {

	private Map<String,Film>      filmMap;
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

	/**
	 *
	 * @param date User Selected Date
	 * @return Map containing all Movies of the selected Date
	 */
	public Map<String, Film> getCurFilmMap(LocalDate date) {

		return Main.db.getCurrFilms(this.filmMap, date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

	}

	public Map<String, Film> getFilmMap(){
		return filmMap;
	}

}
