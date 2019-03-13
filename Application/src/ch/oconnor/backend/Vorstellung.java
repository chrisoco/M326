package ch.oconnor.backend;

import java.time.LocalDateTime;

public class Vorstellung {

	private int ID;
	private Kinosaal kinosaal;
	private Film film;
	private LocalDateTime zeit;
	private Platzreservierung platzres;


	public Vorstellung(int ID, Kinosaal kinosaal, Film film, LocalDateTime zeit) {
		this.ID = ID;
		this.kinosaal = kinosaal;
		this.film     = film;
		this.zeit     = zeit;
	}

	public LocalDateTime getZeit() {
		return zeit;
	}

	public Film getFilm() {
		return film;
	}

	public Platzreservierung getPlatzres() {
		return platzres;
	}

	public void setPlatzres(Platzreservierung platzres) {
		this.platzres = platzres;
	}

	public int getID() {
		return this.ID;
	}

}
