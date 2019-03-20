package ch.oconnor.backend;

public class Platz {

	private int SaalID, ID, num;
	private String reihe;
	private String besucherTel;

	public Platz(int ID, int SaalID, String reihe, int num) {

		this.ID = ID;
		this.SaalID = SaalID;
		this.reihe = reihe;
		this.num = num;
		this.besucherTel = besucherTel;

	}

	public int getID() {
		return ID;
	}

	public int getSaalID() {
		return SaalID;
	}

	public void setSaalID(int saalID) {
		SaalID = saalID;
	}

	public String getReihe() {
		return reihe;
	}

	public void setReihe(String reihe) {
		this.reihe = reihe;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
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
