package ch.oconnor.backend;

import javafx.scene.image.Image;

public class Film {

	private String name;
	private String desc;
	private Image img;


	public Film(String name, String desc, Image img) {
		this.name = name;
		this.desc = desc;
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public Image getImg() {
		return img;
	}

}
