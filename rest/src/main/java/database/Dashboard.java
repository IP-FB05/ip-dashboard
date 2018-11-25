package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import controller.System;
import controller.Document;
import controller.Process;
import utils.Config;

public class Dashboard {
	private Connection connect = null;

	private PreparedStatement preparedStatement = null;

	private ResultSet resultSet = null;

	public Dashboard() throws SQLException, ClassNotFoundException {
		String url = "jdbc:mysql://pruefungsamt.ckxtdfafgwid.eu-central-1.rds.amazonaws.com:3306/dashboardDB";
		Properties props = new Properties();
		props.setProperty("user" , Config.getConfig(Config.DB_USER));
		props.setProperty("password", Config.getConfig(Config.DB_PASS));
		props.setProperty("useSSL", "false");
		props.setProperty("autoReconnect", "true");

		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection(url, props);
	}

	public System[] getSystems() throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("SELECT * FROM systems");
		resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			resultSet.last();
			int rowNumber = resultSet.getRow();
			System[] sys = new System[rowNumber];
			resultSet.first();
			for (int i = 0; i < rowNumber; i++) {
				sys[i] = new System(resultSet.getInt(1), resultSet.getString("name"),
						resultSet.getString("description"), resultSet.getString("link"));
				resultSet.next();
			}
			return sys;
		}
		return null;
	}

	public boolean addSystem(System input) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("INSERT INTO systems (name, description, link) VALUES (?, ?, ?)");
		preparedStatement.setString(1, input.getName());
		preparedStatement.setString(2, input.getDescription());
		preparedStatement.setString(3, input.getLink());
		preparedStatement.execute();

		return true;
	}

	public boolean deleteSystem(int systemID) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("DELETE FROM systems WHERE systemID = ?");
		preparedStatement.setInt(1, systemID);

		preparedStatement.execute();

		return true;
	}

	public Document[] getDocuments() throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement(
				"SELECT documentID, category.name AS 'Categoryname', documents.name, lastChanged, link FROM documents JOIN category ON category.categoryID = documents.categoryID");
		resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			resultSet.last();
			int rowNumber = resultSet.getRow();
			Document[] doc = new Document[rowNumber];
			resultSet.first();
			for (int i = 0; i < rowNumber; i++) {
				doc[i] = new Document(resultSet.getInt(1), resultSet.getString("Categoryname"),
						resultSet.getString("name"), resultSet.getString("lastChanged"), resultSet.getString("link"));
				resultSet.next();
			}
			return doc;
		}
		return null;
	}

	public boolean addDocument(Document input) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("SELECT categoryID FROM category WHERE name LIKE ?");
		preparedStatement.setString(1, input.getCategoriename());
		resultSet = preparedStatement.executeQuery();

		int categoryID = 0;
		if (resultSet.first()) {
			categoryID = resultSet.getInt(1);
		} else {
			preparedStatement = connect.prepareStatement("INSERT INTO category (name) VALUES (?)");
			preparedStatement.setString(1, input.getCategoriename());
			preparedStatement.execute();

			preparedStatement = connect
					.prepareStatement("SELECT categoryID FROM category WHERE category.name LIKE ?");
			preparedStatement.setString(1, input.getCategoriename());
			resultSet = preparedStatement.executeQuery();
			resultSet.first();
			categoryID = resultSet.getInt(1);
		}

		preparedStatement = connect.prepareStatement(
				"INSERT INTO documents (categoryID, name, lastChanged, link) VALUES (?, ?, CURDATE(), ?)");
		preparedStatement.setInt(1, categoryID);
		preparedStatement.setString(2, input.getName());
		preparedStatement.setString(3, input.getLink());
		preparedStatement.execute();

		return true;
	}

	public boolean deleteDocument(int documentID) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("DELETE FROM documents WHERE documentID = ?");
		preparedStatement.setInt(1, documentID);

		preparedStatement.execute();

		return true;
	}

	public Process[] getProcesses() throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("SELECT * FROM processes");
		resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			resultSet.last();
			int rowNumber = resultSet.getRow();
			Process[] process = new Process[rowNumber];
			resultSet.first();
			for (int i = 0; i < rowNumber; i++) {
				process[i] = new Process(resultSet.getInt(1), resultSet.getString("name"),
						resultSet.getString("description"), resultSet.getString("pic"),
						resultSet.getString("varFile"), resultSet.getString("bpmn"), resultSet.getString("added"));
				resultSet.next();
			}
			return process;
		}
		return null;
	}

	public Process getProcess(int processID) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("SELECT * FROM processes WHERE processID = ?");
		preparedStatement.setInt(1, processID);
		resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			Process process = new Process(resultSet.getInt(1), resultSet.getString("name"),
					resultSet.getString("description"), resultSet.getString("pic"), resultSet.getString("varFile"),
					resultSet.getString("bpmn"), resultSet.getString("added"));
			return process;
		}
		return null;
	}

	public boolean addProcess(Process input) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement(
				"INSERT INTO processes (name, description, pic, varFile, bpmn, added) VALUES (?, ?, ?, ?, ?, CURDATE())");
		preparedStatement.setString(1, input.getName());
		preparedStatement.setString(2, input.getDescription());
		preparedStatement.setString(3, input.getPic());
		preparedStatement.setString(4, input.getVarFile());
		preparedStatement.setString(5, input.getBpmn());
		preparedStatement.execute();

		return true;
	}

	public boolean deleteProcess(int processID) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("DELETE FROM processes WHERE processID = ?");
		preparedStatement.setInt(1, processID);

		preparedStatement.execute();

		return true;
	}

	public void close() throws SQLException {
		connect.close();
	}
}
