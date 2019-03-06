package ch.oconnor.backend;

public class Platz {

	private String reihe;
	private int num;
	private Besucher besucher;

	public Platz(String reihe, int num, String besucherTel) {

		this.reihe = reihe;
		this.num = num;

		if (besucherTel != null) this.besucher = new Besucher(besucherTel);

	}

	public Besucher getBesucher() {
		return besucher;
	}

	public void setBesucher(Besucher besucher) {
		this.besucher = besucher;
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

}
