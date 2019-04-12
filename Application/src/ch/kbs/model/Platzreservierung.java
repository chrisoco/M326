package ch.kbs.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Platzreservierung {

	private List<Platz> platzList;

	public Platzreservierung(List<Platz> platzList) {
		this.platzList = platzList;
	}

	public List<Platz> getPlatzList() {
		return platzList;
	}

	public List<Platz> getResList() {

		ArrayList<Platz> result = new ArrayList<>();


		for (Platz p : platzList) {

			if(p.getBesucherTel() != null) result.add(p);

		}

		result.sort(Comparator.comparing(Platz::getReihe).thenComparing(Platz::getNum));

		return result;

	}

}
