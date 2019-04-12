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


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Vorstellung {

	private int ID;
	private Kinosaal kinosaal;
	private Film film;
	private LocalDateTime zeit;
	private Platzreservierung platzres;

	/**
	 * Constructs a new {@link Vorstellung}.
	 *
	 * @param ID
	 * @param kinosaal
	 * @param film
	 * @param zeit
	 */
	public Vorstellung(int ID, Kinosaal kinosaal, Film film, LocalDateTime zeit) {
		this.ID       = ID;
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

	public int getSaalID() {
		return this.kinosaal.getID();
	}

	public int getID() {
		return ID;
	}

	public String getVorstellungInfo() {
		return String.format("%s\t\t%s\t\t%s",
				this.film.getName(),
				zeit.format(DateTimeFormatter.ofPattern("EEEE, dd.MMMM - HH:mm")),
				this.kinosaal.getSaalName());
	}

	public String getTimeLoc() {
		return String.format("%s : %s",
				zeit.format(DateTimeFormatter.ofPattern("EEEE, dd.MMMM - HH:mm")),
				this.kinosaal.getSaalName());
	}

}
