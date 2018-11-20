package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import utils.Config;

public class Pruefungsamt {
  private Connection connect = null;

  private PreparedStatement preparedStatement = null;

  private ResultSet resultSet = null;

  public Pruefungsamt() throws SQLException, ClassNotFoundException {
	String url = "jdbc:mysql://pruefungsamt.ckxtdfafgwid.eu-central-1.rds.amazonaws.com/pruefungsamt";
	Properties props = new Properties();
	props.setProperty("user",Config.getConfig(Config.DB_USER));
	props.setProperty("password",Config.getConfig(Config.DB_PASS));
	props.setProperty("useSSL","false");
	props.setProperty("autoReconnect","true");
	
    Class.forName("com.mysql.jdbc.Driver");
    /*connect = DriverManager.getConnection(
        "jdbc:mysql://pruefungsamt.ckxtdfafgwid.eu-central-1.rds.amazonaws.com/pruefungsamt?"
            + "user=admin&password=D45hb0ard");*/
    connect = DriverManager.getConnection(url, props);
  }

  public int getCredits(int matrikelnr) throws SQLException {

    preparedStatement = connect.prepareStatement(
        "select sum(leistungspunkte) from pruefung_student " +
        "join student on pruefung_student.student = student.matrikelnr " +
        "join pruefungen on pruefungen.pruefungsnr = pruefung_student.pruefung " +
        "join module on pruefungen.modulnr = module.modulnr " +
        "where matrikelnr = ? and status = 'bestanden' " +
        "group by matrikelnr");
    preparedStatement.setInt(1, matrikelnr);
    resultSet = preparedStatement.executeQuery();
    if(resultSet.first()) {
      return resultSet.getInt(1);
    }
    return 0;
    
  }
  
  public boolean praktikumBestanden(int matrikelnr, int fachnr) throws SQLException {
	if(!fachHasPraktikum(fachnr)) {
		return true;
	}
	preparedStatement = connect.prepareStatement(
		"select count(student) from praktikum_student " +
		"join praktikum on  praktikum.idpraktikum = praktikum_student.praktikum " +
		"where student = ? and modul = ? and status = 'bestanden' " +
		"group by student");
	preparedStatement.setInt(1, matrikelnr);
    preparedStatement.setInt(2, fachnr);
    resultSet = preparedStatement.executeQuery();
    if(resultSet.first()) {
        return resultSet.getInt(1) > 0;
    }
    return false;
  }

	public boolean fachHasPraktikum(int fachnr) throws SQLException {
		preparedStatement = connect.prepareStatement(
				"select * from praktikum " +
				"where modul = ?");
		preparedStatement.setInt(1, fachnr);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.first()) {
			return true;
		}
		return false;
	}
	
	public boolean setNewPraxisSemester(int matrikelnr) throws SQLException  {
		preparedStatement = connect.prepareStatement(
				"INSERT INTO `pruefungsamt`.`pruefungen` (`modulnr`) VALUES ('0');");
		int resultSet = preparedStatement.executeUpdate();
		
		preparedStatement.clearParameters();
		
		preparedStatement = connect.prepareStatement(
				"INSERT INTO `pruefungsamt`.`pruefung_student` (`student`, `pruefung`, `status`)"
				+ "VALUES (?, LAST_INSERT_ID(), 'angemeldet');");
		preparedStatement.setInt(1, matrikelnr);
		
		resultSet = preparedStatement.executeUpdate();
		
		if(resultSet == 1) {
			return true;
		}
		return false;
	}
	
	public boolean setNotePraxisSemester(int matrikelnr, int boolBestanden) throws SQLException {
		
		if(boolBestanden == 1) {
			preparedStatement = connect.prepareStatement(
					"UPDATE `pruefungsamt`.`pruefung_student`  AS prstd "
					+ "JOIN "
					+ "( SELECT pruefung FROM `pruefungsamt`.`pruefung_student` AS t1 "
					+ "INNER JOIN `pruefungsamt`.`pruefungen` AS t2 ON t2.pruefungsnr = t1.pruefung "
					+ "WHERE (`student` = ?) and (t2.`modulnr` = '0') "
					+ "order by t2.pruefungszeitpunkt desc "
					+ "LIMIT 1 "
					+ ") AS sel "
					+ "ON sel.pruefung = prstd.pruefung "
					+ "SET prstd.`status` = 'bestanden'; ");				
		}		
		else {		
			preparedStatement = connect.prepareStatement(
					"UPDATE `pruefungsamt`.`pruefung_student`  AS prstd "
					+ "JOIN "
					+ "( SELECT pruefung FROM `pruefungsamt`.`pruefung_student` AS t1 "
					+ "INNER JOIN `pruefungsamt`.`pruefungen` AS t2 ON t2.pruefungsnr = t1.pruefung "
					+ "WHERE (`student` = ?) and (t2.`modulnr` = '0') "
					+ "order by t2.pruefungszeitpunkt desc "
					+ "LIMIT 1 "
					+ ") AS sel "
					+ "ON sel.pruefung = prstd.pruefung "
					+ "SET prstd.`status` = 'durchgefallen'; ");	
		}
		
		preparedStatement.setInt(1, matrikelnr);
		int resultSet = preparedStatement.executeUpdate();
		
		if(resultSet == 1) {
			return true;
		}	
		return false;
	}
	//needed for tests
	public boolean getPraxisSemester(int matrikelnr) throws SQLException {
		preparedStatement = connect.prepareStatement(
				"Select * from `pruefungsamt`.`pruefung_student` " +
				"INNER JOIN `pruefungsamt`.`pruefungen` ON `pruefungsamt`.`pruefung_student`.pruefung = `pruefungsamt`.`pruefungen`.pruefungsnr "
				+ "WHERE (`student` = ?) and (`pruefungsamt`.`pruefungen`.`modulnr` = '0');");
		preparedStatement.setInt(1, matrikelnr);
		resultSet = preparedStatement.executeQuery();
		if(resultSet.first()) {
			return true;
		}
		return false;
	}
	
	public boolean changeModulregister(int matrikelnr, int fachnr, int boolAnmeldung) throws SQLException {
		if(boolAnmeldung == 1) {
			preparedStatement = connect.prepareStatement(
					"INSERT INTO `pruefungsamt`.`module_student` (`modul`, `student`) " +
					"VALUES (?, ?) ON DUPLICATE KEY UPDATE modul=modul;");
			preparedStatement.setInt(1, fachnr);
			preparedStatement.setInt(2, matrikelnr);
		} else {
			preparedStatement = connect.prepareStatement(
					"DELETE FROM `pruefungsamt`.`module_student` " +
					"WHERE (`modul` = ?) and (`student` = ?);");
			preparedStatement.setInt(1, fachnr);
			preparedStatement.setInt(2, matrikelnr);
		}
		 
		int resultInt = preparedStatement.executeUpdate();
		
		if(resultInt == 1) {
			return true;
		}
		return false;
	}
	//Output: Semester in welchem ein Modul angeboten wird in welchem Studiengang
	public int getModulSemester(int fachnr, int studiengang) throws SQLException {
		preparedStatement = connect.prepareStatement(
		        "select semester from module_studiengang " + 
		        "where modul = ? and studiengang = ?");
		
	    preparedStatement.setInt(1, fachnr);
	    preparedStatement.setInt(2, studiengang);
	    
	    resultSet = preparedStatement.executeQuery();
	    if(resultSet.first()) {
	      return resultSet.getInt(1);
	    }
	    return 0;
	    
	}
	//needed for tests Output: Bool ob Student in diesem Modul angemeldet ist
	public boolean getModulStudent(int matrikelnr, int fachnr) throws SQLException {
		preparedStatement = connect.prepareStatement(
				"SELECT * FROM pruefungsamt.module_student " +
				"WHERE `modul` = ? AND `student` = ?;");
		preparedStatement.setInt(1, fachnr);
		preparedStatement.setInt(2, matrikelnr);
		
		resultSet = preparedStatement.executeQuery();
		
		if(resultSet.first()) {
			return true;
		}
		return false;
	}
	// output: alle angemeldeten module eines Studenten
	public boolean getModulStudent(int matrikelnr) throws SQLException {
		/*preparedStatement = connect.prepareStatement(
				"SELECT modul,modultext FROM pruefungsamt.module_student" +
				"INNER JOIN `pruefungsamt`.`module`"
				+ "ON `pruefungsamt`.`module_student`.modul = `pruefungsamt`.`module`.modulnr"
				+ "WHERE `pruefungsamt`.`module_student`.`student` = ?;");
		preparedStatement.setInt(1, matrikelnr);
		
		resultSet = preparedStatement.executeQuery();
		
		if(resultSet.first()) {
			return true;
		}
		return false;*/
		
		//TODO Muss mit json ueberarbeitet werden
		
		return false;
		
		
	}
	
	public boolean pruefungAnmelden(int matrikelnr, int fachnr) throws SQLException {
  		//erfrage die anzahl der bereits durchgeführten Versuche
		preparedStatement = connect.prepareStatement("select ps.status from student s \n" +
				"\tjoin pruefung_student ps on s.matrikelnr = ps.student\n" +
				"    join pruefungen p on ps.pruefung = p.pruefungsnr\n" +
				"    where s.matrikelnr = ? and p.modulnr = ?");
		preparedStatement.setInt(1, matrikelnr);
		preparedStatement.setInt(2, fachnr);
		resultSet = preparedStatement.executeQuery();
		int anzversuche = 0;
		while (resultSet.next()) {
			anzversuche++;
		}
		if(anzversuche >= 3){
			return false;
		}
		preparedStatement.clearParameters();
		String pruefungsnr = fachnr + "" + (anzversuche + 1);
		preparedStatement = connect.prepareStatement("insert into pruefung_student (student, pruefung, status) values (?,?,\"angemeldet\");");
		preparedStatement.setInt(1, matrikelnr);
		preparedStatement.setInt(2, Integer.parseInt(pruefungsnr));
		preparedStatement.execute();

		return true;
	}

	public boolean pruefungAbmelden(int matrikelnr, int fachnr) throws SQLException {
		//erfrage die anzahl der bereits durchgeführten Versuche
		preparedStatement = connect.prepareStatement("select ps.status from student s \n" +
				"\tjoin pruefung_student ps on s.matrikelnr = ps.student\n" +
				"    join pruefungen p on ps.pruefung = p.pruefungsnr\n" +
				"    where s.matrikelnr = ? and p.modulnr = ?");
		preparedStatement.setInt(1, matrikelnr);
		preparedStatement.setInt(2, fachnr);
		resultSet = preparedStatement.executeQuery();
		int anzversuche = 0;
		while (resultSet.next()) {
			anzversuche++;
		}

		preparedStatement.clearParameters();
		String pruefungsnr = fachnr + "" + (anzversuche);
		preparedStatement = connect.prepareStatement("delete from pruefung_student where student = ? and pruefung = ?");
		preparedStatement.setInt(1, matrikelnr);
		preparedStatement.setInt(2, Integer.parseInt(pruefungsnr));
		preparedStatement.execute();

		return true;
	}

	public boolean pruefungAnmeldenVoraussetzungen(int matrikelnr, int fachnr) throws SQLException {
		preparedStatement = connect.prepareStatement("select ps.status from student s \n" +
				"\tjoin pruefung_student ps on s.matrikelnr = ps.student\n" +
				"    join pruefungen p on ps.pruefung = p.pruefungsnr\n" +
				"    where s.matrikelnr = ? and p.modulnr = ?");
		preparedStatement.setInt(1, matrikelnr);
		preparedStatement.setInt(2, fachnr);
		resultSet = preparedStatement.executeQuery();

		boolean zulassung = true;
		int anzversuche = 0;
		while (resultSet.next()) {
			anzversuche++;
			String status = resultSet.getString("status");
			if (status.equals("bestanden")){
				zulassung = false;
			}
			if (status.equals("angemeldet")){
				zulassung = false;
			}

		}
		if(anzversuche >= 3){
			zulassung = false;
		}

		return zulassung;
	}

	public boolean pruefungAbmeldenVoraussetzungen(int matrikelnr, int fachnr) throws SQLException {
		preparedStatement = connect.prepareStatement("select ps.status from student s \n" +
				"\tjoin pruefung_student ps on s.matrikelnr = ps.student\n" +
				"    join pruefungen p on ps.pruefung = p.pruefungsnr\n" +
				"    where s.matrikelnr = ? and p.modulnr = ?");
		preparedStatement.setInt(1, matrikelnr);
		preparedStatement.setInt(2, fachnr);
		resultSet = preparedStatement.executeQuery();

		boolean zulassung = false;

		while (resultSet.next()) {
			String status = resultSet.getString("status");
			if (status.equals("angemeldet")){
				zulassung = true;
			}
			else{
				zulassung = false;
			}

		}


		return zulassung;
	}

	
	
	
	
	
	public void close() throws SQLException {
		connect.close();
	}

	


}
