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
 * Kinosaal Class holding ID and SaalName.
 *
 */


public class Kinosaal {

	private int    ID;
	private String saalName;

	/**
	 * Constructs a new {@link Kinosaal}.
	 */
	public Kinosaal(int ID, String saalName) {
		this.ID       = ID;
		this.saalName = saalName;
	}

	public String getSaalName() {
		return saalName;
	}

	public int getID() {
		return ID;
	}

}
