package ch.oconnor.backend;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DB {

	private Connection DBCon;
	private Statement  st;
	private ResultSet  rs;


	private String jdbcUrl  = "jdbc:mysql://localhost/m326?";

	private String settings = "useSSL=false&"
							+ "useUnicode=true&"
							+ "useJDBCCompliantTimezoneShift=true&"
							+ "useLegacyDatetimeCode=false&"
							+ "serverTimezone=UTC";


	/**
	 * Custom constructor Instantiates the DB Class.
	 */
	public DB() {
		try {
			this.DBCon = DriverManager.getConnection(jdbcUrl+settings, "JavaApp", "javaapp");
			this.st    = DBCon.createStatement();

		} catch (SQLException e) { System.out.println("Connection FAILED ..."); }

	}


	public List<Film> getAllFilms() {

		ArrayList<Film> result = new ArrayList<>();

		try {

			rs = st.executeQuery("SELECT * FROM tbl_film;");

			while(rs.next()) {

				result.add(new Film(

						rs.getString(2),
						rs.getString(3),
						getImg(rs.getBinaryStream(4)))
				);

			}

		} catch (Exception e) {}

		return result;
	}


	private Image getImg(InputStream is) {

		try {

			return SwingFXUtils.toFXImage(ImageIO.read(is), null);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}



	public List<Kinosaal> getAllKinosaal() {

		ArrayList<Kinosaal> result = new ArrayList<>();

		try {

			rs = st.executeQuery("SELECT `saalName` FROM tbl_kinosaal;");

			while(rs.next()) {

				result.add(new Kinosaal(rs.getString(1)));

			}

		} catch (SQLException e) {}

		return result;

	}


	public List<Vorstellung> getAllVorstellungen(List<Film> filmList, List<Kinosaal> kinosaalList) {

		ArrayList<Vorstellung> result = new ArrayList<>();


		try {

			rs = st.executeQuery("SELECT tbl_Vorstellung_ID, f.name, k.saalName, zeitpunkt"
										+ " FROM tbl_vorstellung"

										+ " LEFT join tbl_Film AS f"
										+ " ON tbl_Film_FK = f.tbl_Film_ID"

										+ " LEFT JOIN tbl_Kinosaal AS k"
										+ " ON tbl_Kinosaal_FK = k.tbl_Kinosaal_ID"

										+ " ORDER BY zeitpunkt;"
								);

			while(rs.next()) {

				result.add(new Vorstellung(
								rs.getInt(1),
								getFilmByName(filmList, rs.getString(2)),
								getSaalByName(kinosaalList, rs.getString(3)),
								parseDate(rs.getString(4))
							));

			}

		} catch (SQLException e) {}


		return result;

	}

	private Film getFilmByName(List<Film> filmList, String name) {

		for (Film f : filmList) {
			if (f.getName().equals(name)) return f;
		}
		return null;
	}

	private Kinosaal getSaalByName(List<Kinosaal> kinosaalList, String name) {

		for (Kinosaal k : kinosaalList) {
			if (k.getSaalName().equals(name)) return k;
		}
		return null;
	}

	private LocalDateTime parseDate(String sqlDate) {
		return LocalDateTime.parse(sqlDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}



	public List<Platz> getPlaetze(int vorstellungID) {

		LinkedList<Platz> result = new LinkedList<>();

		try {

			rs = st.executeQuery("SELECT `Reihe`, `Num`, `BesucherTel` FROM tbl_platz"
										+ " WHERE tbl_Vorstellung_tbl_FK = " + vorstellungID
											+ " ORDER BY Reihe, Num;"
								);

			while(rs.next()) {

				result.add(new Platz(vorstellungID, rs.getString(1), rs.getInt(2), rs.getString(3)));

			}

		} catch (SQLException e) {}

		return result;
	}



}
