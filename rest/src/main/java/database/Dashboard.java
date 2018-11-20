package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import controller.System;
import controller.Dokument;

public class Dashboard {
  private Connection connect = null;

  private PreparedStatement preparedStatement = null;

  private ResultSet resultSet = null;

	public Dashboard() throws SQLException, ClassNotFoundException
	{
		String url = "jdbc:mysql://pruefungsamt.ckxtdfafgwid.eu-central-1.rds.amazonaws.com:3306/dashboardDB";
		Properties props = new Properties();
		props.setProperty("user", "admin");
		props.setProperty("password", "D45hb0ard");
		props.setProperty("useSSL","false");
		props.setProperty("autoReconnect","true");
	
    Class.forName("com.mysql.jdbc.Driver");
    connect = DriverManager.getConnection(url, props);
  }
  
	public System[] getSystems() throws SQLException, ClassNotFoundException
	{
		preparedStatement = connect.prepareStatement("SELECT * FROM systeme");
	  resultSet = preparedStatement.executeQuery();
		if(resultSet.first())
		{
			resultSet.last();
			int rowNumber = resultSet.getRow();
			System[] sys = new System[rowNumber];
			resultSet.first();
			for(int i = 0; i < rowNumber; i++)
			{
				sys[i] = new System(resultSet.getInt(1), resultSet.getString("name"), resultSet.getString("beschreibung"), resultSet.getString("link"));
				resultSet.next();
			}
			return sys;
		}
	  return null;
	}
	
	public boolean addSystem(System input) throws SQLException, ClassNotFoundException
	{
		preparedStatement = connect.prepareStatement("INSERT INTO systeme (name, beschreibung, link) VALUES (?, ?, ?)");
		preparedStatement.setString(1, input.getName());
		preparedStatement.setString(2, input.getBeschreibung());
		preparedStatement.setString(3, input.getLink());
		preparedStatement.execute();
		
		return true;
	}

	public boolean deleteSystem(int systemID) throws SQLException, ClassNotFoundException
	{
		preparedStatement = connect.prepareStatement("DELETE FROM systeme WHERE systemID = ?");
		preparedStatement.setInt(1, systemID);

		preparedStatement.execute();
		
		return true;
	}

	public Dokument[] getDokumente() throws SQLException, ClassNotFoundException
	{
		preparedStatement = connect.prepareStatement("SELECT dokumentID, kategorie.name AS 'Kategoriename', dokumente.name, lastChanged, link FROM dokumente JOIN kategorie ON kategorie.kategorieID = dokumente.kategorieID");
	  resultSet = preparedStatement.executeQuery();
		if(resultSet.first())
		{
			resultSet.last();
			int rowNumber = resultSet.getRow();
			Dokument[] doc = new Dokument[rowNumber];
			resultSet.first();
			for(int i = 0; i < rowNumber; i++)
			{
				doc[i] = new Dokument(resultSet.getInt(1), resultSet.getString("Kategoriename"), resultSet.getString("name"), resultSet.getString("lastChanged"), resultSet.getString("link"));
				resultSet.next();
			}
			return doc;
		}
	  return null;
	}
	
	public boolean addDokument(Dokument input) throws SQLException, ClassNotFoundException
	{
		preparedStatement = connect.prepareStatement("SELECT kategorieID FROM kategorie WHERE name LIKE ?");
		preparedStatement.setString(1, input.getKategoriename());
		resultSet = preparedStatement.executeQuery();

		int kategorieID = 0;
		if(resultSet.first())
		{
			kategorieID = resultSet.getInt(1);
		}
		else
		{
			preparedStatement = connect.prepareStatement("INSERT INTO kategorie (name) VALUES (?)");
			preparedStatement.setString(1, input.getKategoriename());
			preparedStatement.execute();

			preparedStatement = connect.prepareStatement("SELECT kategorieID FROM kategorie WHERE kategorie.name LIKE ?");
			preparedStatement.setString(1, input.getKategoriename());
			resultSet = preparedStatement.executeQuery();
			resultSet.first();
			kategorieID = resultSet.getInt(1);
		}


		preparedStatement = connect.prepareStatement("INSERT INTO dokumente (kategorieID, name, lastChanged, link) VALUES (?, ?, CURDATE(), ?)");
		preparedStatement.setInt(1, kategorieID);
		preparedStatement.setString(2, input.getName());
		preparedStatement.setString(3, input.getLink());
		preparedStatement.execute();
		
		return true;
	}

	public boolean deleteDokument(int dokumentID) throws SQLException, ClassNotFoundException
	{
		preparedStatement = connect.prepareStatement("DELETE FROM dokumente WHERE dokumentID = ?");
		preparedStatement.setInt(1, dokumentID);

		preparedStatement.execute();
		
		return true;
	}


	public void close() throws SQLException {
		connect.close();
	}
}
