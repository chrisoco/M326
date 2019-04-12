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
 * @version 1.0
 *
 * DB Class : Connection + Prepared Statements.
 *
 */

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
	 * Constructs a new DB-Connection {@link DB}.
	 */
	public DB() {
		try {
			this.DBCon = DriverManager.getConnection(jdbcUrl+settings, "JavaApp", "javaapp");
			this.st    = DBCon.createStatement();

		} catch (SQLException e) { System.out.println("Connection FAILED ..."); }

	}

	/**
	 * @return Map containing all Movies from the DB
	 */
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

		} catch (Exception e) { e.printStackTrace(); }

		return result;
	}


	/**
	 * Convert InputStream from DB Blob to {@link Image}
	 *
	 * @param is InputStream
	 * @return Image from the InputStream
	 */
	private Image getImg(InputStream is) {

		try {

			return SwingFXUtils.toFXImage(ImageIO.read(is), null);

		} catch (Exception e) { e.printStackTrace(); }

		return null;

	}


	/**
	 * Get all Movies from selected Date
	 *
	 * @param filmMap Map containing all Movies
	 * @param date Selected Date to filter by
	 * @return Map containing all Movies from selected Date.
	 */
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



		} catch (SQLException e) { e.printStackTrace(); }


		if(reMap.isEmpty()) reMap.put(
				"Empty", new Film("No Movies Today ",
							"\nWe Need a Break too! :)\n",
							null)
		);


		return reMap;
	}


	/**
	 * Get all Kinosaal from DB
	 * @return Map of all Kinosaals
	 */
	public Map<String, Kinosaal> getAllKinosaal() {

		Map<String, Kinosaal> result = new TreeMap<>();

		try {

			rs = st.executeQuery("SELECT * FROM tbl_kinosaal;");

			while(rs.next()) {

				result.put(rs.getString(2), new Kinosaal(rs.getInt(1), rs.getString(2)));

			}

		} catch (SQLException e) { e.printStackTrace(); }

		return result;

	}


	/**
	 * Get all Vorstellungen from DB.
	 * @param filmMap Map with all Movies to make pointers.
	 * @param kinosaalMap Map with all Saals to make pointers.
	 * @return List containing all Vorstellungen with correct Pointers.
	 */
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

			/**
			 * Add Booked PhoneNumber to the Current Iterating Seat. If no Number is returned the Seat is free.
			 */
			for (Vorstellung v : result) {
				LinkedList<Platz> resP = getPlatzRes(v.getSaalID());
				for(Platz p : resP) {
					p.setBesucherTel(getResPlatzPhone(v.getID(), p.getID()));
				}
				v.setPlatzres(new Platzreservierung(resP));
			}

		} catch (SQLException e) { e.printStackTrace(); }

		return result;

	}


	/**
	 * Parse SQLDate to LocalDateTime
	 * @param sqlDate SQLTime
	 * @return LocalDateTime
	 */
	private LocalDateTime parseDate(String sqlDate) {
		return LocalDateTime.parse(sqlDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}


	/**
	 * Get all Seats of a Saal
	 * @param SaalID SaalID in DB
	 * @return List containing all Seats.
	 */
	public LinkedList<Platz> getPlatzRes(int SaalID) {

		LinkedList<Platz> result = new LinkedList<>();

		try {

			rs = st.executeQuery("SELECT `tbl_Platz_ID`, `Reihe`, `Num` FROM tbl_platz"
										+ " WHERE tbl_Kinosaal_FK = " + SaalID
											+ " ORDER BY Reihe, Num;"
								);

			while(rs.next()) {

				result.add(new Platz(rs.getInt(1), SaalID, rs.getString(2), rs.getInt(3)));

			}

			return result;

		} catch (SQLException e) { e.printStackTrace(); }

		return result;

	}


	/**
	 * If Platzreservation in DB exists Add Customer PhonNumber to specified seat.
	 *
	 * @param v_ID Vorstellung ID in DB
	 * @param p_ID Platz ID in DB
	 * @return PhoneNumber
	 */
	private String getResPlatzPhone(int v_ID, int p_ID) {

		try {

			rs = st.executeQuery("SELECT Tel FROM tbl_reservation"
									+ " LEFT JOIN tbl_Platz AS p"
									+ " ON p.tbl_Platz_ID = tbl_Platz_FK"
									+ " WHERE tbl_vorstellung_FK = '" + v_ID + "'"
									+ " AND p.tbl_Platz_ID = '" + p_ID + "';");

			if(rs.next()) return rs.getString(1);

		} catch (SQLException e) { e.printStackTrace(); }

		return null;

	}

	/**
	 * Make a Reservation for to selected Seat in selected Vorstellung with given PhoneNumber
	 * @param p {@link Platz}
	 * @param telNum User Entered PhoneNumber
	 * @param v_ID ID of selected Vorstellung
	 */
	public void resSeat(Platz p, String telNum, int v_ID) {

		try {

			st.executeUpdate("INSERT INTO tbl_reservation"
								+ " (`tbl_Vorstellung_FK`, `tbl_Platz_FK`, `Tel`)"
								+ " VALUE ('" + v_ID + "', '" + p.getID() + "', '" + telNum + "');");

		} catch (SQLException e) { e.printStackTrace(); }

	}


	/**
	 * Remove a Reservation for the seleccted Seat in selected Vorstellung
	 * @param p {@link Platz}
	 * @param v_ID ID of selected Vorstellung
	 */
	public void remBooking(Platz p, int v_ID) {

		try {

			st.executeUpdate("DELETE FROM tbl_reservation"
								+ " WHERE tbl_Vorstellung_FK = '" + v_ID + "'"
								+ " AND tbl_Platz_FK = '" + p.getID() + "'");

		} catch (SQLException e) { e.printStackTrace(); }

	}


}
