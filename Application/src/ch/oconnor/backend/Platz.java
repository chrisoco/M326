package ch.oconnor.backend;

public class Platz {

	private int vorstellungID, num;
	private String reihe;
	private String besucherTel;

	public Platz(int vorstellungID, String reihe, int num, String besucherTel) {

		this.vorstellungID = vorstellungID;
		this.reihe = reihe;
		this.num = num;
		this.besucherTel = besucherTel;

	}

	public int getVorstellungID() {
		return vorstellungID;
	}

	public void setVorstellungID(int vorstellungID) {
		this.vorstellungID = vorstellungID;
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
