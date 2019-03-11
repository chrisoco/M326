package ch.oconnor.backend;

import java.time.LocalDateTime;

public class Vorstellung {

	private Kinosaal kinosaal;
	private Film film;
	private Platzreservierung platzres;
	private LocalDateTime zeit;


	public Vorstellung(int vorstellungID, Film film, Kinosaal kinosaal, LocalDateTime zeit) {

		this.platzres = new Platzreservierung(vorstellungID);

		this.film     = film;
		this.kinosaal = kinosaal;
		this.zeit     = zeit;

	}

	public Vorstellung(Kinosaal kinosaal, Film film, LocalDateTime zeit) {
		this.kinosaal = kinosaal;
		this.film = film;
		this.zeit = zeit;
	}

	public LocalDateTime getZeit() {
		return zeit;
	}

	public Film getFilm() {
		return film;
	}

}
