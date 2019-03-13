package ch.oconnor.backend;


import ch.oconnor.Main;

import java.util.List;
import java.util.Map;

public class Kinobuchungssystem {

	private Map<String,Film> filmMap;
	private Map<String, Kinosaal> kinosaalMap;
	private List<Vorstellung> vorstellungList;



	public Kinobuchungssystem() {

		filmMap        = Main.db.getAllFilms();

		kinosaalMap    = Main.db.getAllKinosaal();

		vorstellungList = Main.db.getAllVorstellungen(filmMap, kinosaalMap);

		for(Vorstellung v : vorstellungList) {
			System.out.println(v.getFilm().getName() + " -> " + v.getZeit());
		}


	}

	public Map<String, Film> getFilmMap() {
		return filmMap;
	}


	public List<Vorstellung> getVorstellungList() {
		return vorstellungList;
	}

}
