package ch.oconnor.backend;

import java.util.List;

public class Platzreservierung {

	private List<Platz> platzList;

	public Platzreservierung(List<Platz> platzList) {
		this.platzList = platzList;
	}

	public List<Platz> getPlatzList() {
		return platzList;
	}

}
