package ch.oconnor.backend;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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


	public Map<String, Film> getAllFilms() {

		TreeMap<String, Film> result = new TreeMap<>();

		try {

			rs = st.executeQuery("SELECT * FROM tbl_film;");

			while(rs.next()) {

				result.put(rs.getString(2),
								new Film(
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

		} catch (Exception e) {}

		return null;

	}


	public Map<String, Film> getCurrFilms(Map<String, Film> filmMap, String date) {

		Map<String, Film> reMap = new TreeMap<>();

		try {

			rs = st.executeQuery("SELECT f.`name` FROM tbl_vorstellung"
									+ " LEFT JOIN tbl_film as f"
									+ " ON tbl_Film_FK = f.tbl_Film_ID"
									+ " WHERE DATE(`zeitpunkt`) = '" + date + "'"
									+ " GROUP BY `name`;");


			while (rs.next()) {

				String name = rs.getString(1);

				reMap.put(name, filmMap.get(name));

			}



		} catch (SQLException e) {}


		if(reMap.isEmpty()) reMap.put(
				"Empty", new Film("No Movies Today ",
							"\nWe Need a Break too! :)\n",
							null)
		);


		return reMap;
	}



	public Map<String, Kinosaal> getAllKinosaal() {

		Map<String, Kinosaal> result = new TreeMap<>();

		try {

			rs = st.executeQuery("SELECT `saalName` FROM tbl_kinosaal;");

			while(rs.next()) {

				result.put(rs.getString(1), new Kinosaal(rs.getString(1)));

			}

		} catch (SQLException e) {}

		return result;

	}


	public List<Vorstellung> getAllVorstellungen(Map<String, Film> filmMap, Map<String, Kinosaal> kinosaalMap) {

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

				result.add(new Vorstellung(rs.getInt(1),
						kinosaalMap.get(rs.getString(3)),
						filmMap.get(rs.getString(2)),
						parseDate(rs.getString(4))
				));


			}

			for (Vorstellung v : result) {
				v.setPlatzres(getPlatzRes(v.getID())); // SET ID AS ATTRIBUTE OF KLASS ...
			}


		} catch (SQLException e) {}

		return result;

	}


	private LocalDateTime parseDate(String sqlDate) {
		return LocalDateTime.parse(sqlDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}



	public Platzreservierung getPlatzRes(int vorstellungID) {

		LinkedList<Platz> result = new LinkedList<>();

		try {

			rs = st.executeQuery("SELECT `Reihe`, `Num`, `BesucherTel` FROM tbl_platz"
										+ " WHERE tbl_Vorstellung_tbl_FK = " + vorstellungID
											+ " ORDER BY Reihe, Num;"
								);

			while(rs.next()) {

				result.add(new Platz(vorstellungID, rs.getString(1), rs.getInt(2), rs.getString(3)));

			}

			return new Platzreservierung(result);

		} catch (SQLException e) {}

		return new Platzreservierung(null);

	}


	public void resSeat(Platz p, String num) {


		try {

			st.executeUpdate(

					"UPDATE `tbl_platz` SET `BesucherTel` = '" + num
						+ "' WHERE (`tbl_Vorstellung_tbl_FK` = '" + p.getVorstellungID() + "') " +
							"and (`Num` = '" + p.getNum() + "') and (`Reihe` = '" + p.getReihe() + "');");

		} catch (SQLException e) {}

	}


	public void remBooking(Platz p) {


		try {

			st.executeUpdate("UPDATE `tbl_platz` SET `BesucherTel` = NULL"
								+ " WHERE (`tbl_Vorstellung_tbl_FK` = '" + p.getVorstellungID() + "') and"
								+ "(`Num` = '" + p.getNum() + "') and (`Reihe` = '" + p.getReihe() + "');");

		} catch (SQLException e) {
			e.printStackTrace();
		}


	}


}
