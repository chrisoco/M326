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
 * Platz Class to Hold Values of a Seat
 * ID          = ID of Seat in DB.
 * SaalID      = In witch Saal the Seat is.
 * reihe       = Row of the Seat (Char A-Z)
 * num         = Number of the Seat in its row.
 * besucherTel = TelNumber of the Booker
 *
 */


public class Platz {

	private int SaalID, ID, num;
	private String reihe, besucherTel;


	/**
	 * Constructs a new {@link Platz}.
	 */
	public Platz(int ID, int SaalID, String reihe, int num) {

		this.ID          = ID;
		this.SaalID      = SaalID;
		this.reihe       = reihe;
		this.num         = num;
		this.besucherTel = besucherTel;

	}

	public int getID() {
		return ID;
	}

	public String getReihe() {
		return reihe;
	}

	public int getNum() {
		return num;
	}

	public String getBesucherTel() {
		return besucherTel;
	}

	public String getResInfo() {
		return String.format("R: %s\t| P: %3d\t%s\t", this.reihe, this.num, this.besucherTel);
	}

	public void setBesucherTel(String besucherTel) {
		this.besucherTel = besucherTel;
	}

}
