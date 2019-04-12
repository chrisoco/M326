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
 *
 */


import javafx.scene.image.Image;

public class Film {

	private String name;
	private String desc;
	private Image  img ;

	/**
	 * Constructs a new {@link Film}.
	 */
	public Film(String name, String desc, Image img) {

		this.name = name;
		this.desc = desc;
		this.img  = img ;

	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public Image   getImg() {
		return img;
	}

}
