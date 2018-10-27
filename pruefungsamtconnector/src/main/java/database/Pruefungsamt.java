package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Pruefungsamt {
  private Connection connect = null;

  private PreparedStatement preparedStatement = null;

  private ResultSet resultSet = null;

  public Pruefungsamt() throws SQLException, ClassNotFoundException {
    Class.forName("com.mysql.jdbc.Driver");
    connect = DriverManager.getConnection(
        "jdbc:mysql://pruefungsamt.ckxtdfafgwid.eu-central-1.rds.amazonaws.com/pruefungsamtconnector?"
            + "user=admin&password=D45hb0ard");
  }

  public int getCredits(int matrikelnr) throws SQLException {

    preparedStatement = connect.prepareStatement(
        "select sum(*) from pruefung_student " +
        "join student on pruefung_student.student = student.matrikelnr " +
        "join pruefung on pruefung.idpruefung = pruefung_student.pruefung " +
        "where matrikelnr = ? and status = 'bestanden'");
    preparedStatement.setInt(0, matrikelnr);
    resultSet = preparedStatement.executeQuery();
    if(resultSet.first()) {
      return resultSet.getInt(1);
    }
    return 0;
    
  }

}
