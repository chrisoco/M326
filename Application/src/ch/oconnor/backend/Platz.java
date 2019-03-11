package ch.oconnor.backend;

public class Platz {

	private int vorstellungID, num;
	private String reihe;
	private Besucher besucher;

	public Platz(int vorstellungID, String reihe, int num, String besucherTel) {

		this.vorstellungID = vorstellungID;
		this.reihe = reihe;
		this.num = num;

		if (besucherTel != null) this.besucher = new Besucher(besucherTel);

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

	public Besucher getBesucher() {
		return besucher;
	}

	public void setBesucher(Besucher besucher) {
		this.besucher = besucher;
	}

}
