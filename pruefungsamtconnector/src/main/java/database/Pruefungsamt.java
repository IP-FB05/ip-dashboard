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
				"INSERT INTO `pruefungsamt`.`pruefungen` (`modulnr`) VALUES ('0');"
				+ "INSERT INTO `pruefungsamt`.`pruefung_student` (`student`, `pruefung`, `status`) VALUES (?, LAST_INSERT_ID(), 'angemeldet');");
		preparedStatement.setInt(1, matrikelnr);
		
		resultSet = preparedStatement.executeQuery();
		
		if(resultSet.first()) {
			return true;
		}	
		return false;
	}
	
	public boolean setNotePraxisSemester(int matrikelnr, int boolBestanden) throws SQLException {
		
		if(boolBestanden == 1) {
			
			preparedStatement = connect.prepareStatement(
					"UPDATE `pruefungsamt`.`pruefung_student` "
					+ "INNER JOIN `pruefungsamt`.`pruefungen` ON `pruefungsamt`.`pruefung_student`.pruefung = `pruefungsamt`.`pruefungen`.pruefungsnr"
					+ "SET `status` = 'bestanden'"
					+ "WHERE (`student` = ?) and (`pruefungsamt`.`pruefungen`.`modulnr` = '0')"
					+ "order by `pruefungsamt`.`pruefungen`.pruefungszeitpunkt desc"
					+ "limit 1;");
			preparedStatement.setInt(1, matrikelnr);
		}
		
		else {
			
			preparedStatement = connect.prepareStatement(
					"UPDATE `pruefungsamt`.`pruefung_student` "
					+ "INNER JOIN `pruefungsamt`.`pruefungen` ON `pruefungsamt`.`pruefung_student`.pruefung = `pruefungsamt`.`pruefungen`.pruefungsnr"
					+ "SET `status` = 'durchgefallen'"
					+ "WHERE (`student` = ?) and (`pruefungsamt`.`pruefungen`.`modulnr` = '0')"
					+ "order by `pruefungsamt`.`pruefungen`.pruefungszeitpunkt desc"
					+ "limit 1;");
			preparedStatement.setInt(1, matrikelnr);
		}
		
		if(resultSet.first()) {
			return true;
		}	
		return false;
	}
	
	//needed for tests
	public boolean getPraxisSemester(int matrikelnr) throws SQLException {
		preparedStatement = connect.prepareStatement(
				"Select * from `pruefungsamt`.`pruefung_student`" +
				"INNER JOIN `pruefungsamt`.`pruefungen` ON `pruefungsamt`.`pruefung_student`.pruefung = `pruefungsamt`.`pruefungen`.pruefungsnr"
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
					"INSERT INTO `pruefungsamt`.`module_student` (`modul`, `student`)" +
					"VALUES (?, ?);");
			preparedStatement.setInt(1, fachnr);
			preparedStatement.setInt(2, matrikelnr);
		} else {
			preparedStatement = connect.prepareStatement(
					"DELETE FROM `pruefungsamt`.`module_student`" +
					"WHERE (`modul` = ?) and (`student` = ?);");
			preparedStatement.setInt(1, fachnr);
			preparedStatement.setInt(2, matrikelnr);
		}
		 
		resultSet = preparedStatement.executeQuery();
		
		if(resultSet.first()) {
			return true;
		}
		return false;
	}
	
	//needed for tests
	public boolean getModulStudent(int matrikelnr, int fachnr) throws SQLException {
		preparedStatement = connect.prepareStatement(
				"SELECT * FROM pruefungsamt.module_student" +
				"WHERE `modul` = ? AND `student` = ?;");
		preparedStatement.setInt(1, fachnr);
		preparedStatement.setInt(2, matrikelnr);
		
		resultSet = preparedStatement.executeQuery();
		
		if(resultSet.first()) {
			return true;
		}
		return false;
	}
	
	
	public boolean getModulStudent(int matrikelnr) throws SQLException {
		preparedStatement = connect.prepareStatement(
				"SELECT modul,modultext FROM pruefungsamt.module_student" +
				"INNER JOIN `pruefungsamt`.`module`"
				+ "ON `pruefungsamt`.`module_student`.modul = `pruefungsamt`.`module`.modulnr"
				+ "WHERE `pruefungsamt`.`module_student`.`student` = ?;");
		preparedStatement.setInt(1, matrikelnr);
		
		resultSet = preparedStatement.executeQuery();
		
		if(resultSet.first()) {
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	public void close() throws SQLException {
		connect.close();
	}


}
