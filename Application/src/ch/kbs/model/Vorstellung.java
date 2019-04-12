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
 * Vorstellung Class holding all Information of 1 specific Vorstellung.
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
	 * @param ID ID of Vorstellung in DB.
	 * @param kinosaal Kinosaal in which the Movie is showed in.
	 * @param film Film that is beeing shown.
	 * @param zeit Time and Date when the Vorstellung is shown.
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

	/**
	 *
	 * @return Formatted String with Information about {@link Film#getName()}, {@link #zeit}, {@link Kinosaal#getSaalName()}
	 * (Alita Battle Angle  Friday, 01.March - 20:30    UG, 01)
	 */
	public String getVorstellungInfo() {
		return String.format("%s\t\t%s\t\t%s",
				this.film.getName(),
				zeit.format(DateTimeFormatter.ofPattern("EEEE, dd.MMMM - HH:mm")),
				this.kinosaal.getSaalName());
	}

	/**
	 *
	 * @return Formatted Time (Friday, 01.March - 20:30)
	 */
	public String getTimeLoc() {
		return String.format("%s : %s",
				zeit.format(DateTimeFormatter.ofPattern("EEEE, dd.MMMM - HH:mm")),
				this.kinosaal.getSaalName());
	}

}
