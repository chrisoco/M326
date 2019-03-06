package ch.oconnor.backend;

import ch.oconnor.Main;

import java.util.List;

public class Platzreservierung {

	private List<Platz> platzList;

	public Platzreservierung(int vorstellungID) {

		this.platzList = Main.db.getPlaetze(vorstellungID);

	}





}
