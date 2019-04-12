package ch.kbs.model;

public class Kinosaal {

	private int    ID;
	private String saalName;

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
