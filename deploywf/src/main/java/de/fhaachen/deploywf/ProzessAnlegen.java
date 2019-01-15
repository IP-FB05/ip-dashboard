package de.fhaachen.deploywf;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.joda.time.DateTime;

import java.sql.*;

import java.util.Properties;

import utils.*;

public class ProzessAnlegen implements JavaDelegate {

	public void execute(DelegateExecution execution) {

		String name = execution.getVariable("titel").toString();
		String beschreibung = execution.getVariable("beschreibung").toString();
		String bpmn = execution.getVariable("bpmnpath").toString();
		String verbal = execution.getVariable("verbal").toString();
		String camunda_processID = execution.getVariable("definitionId").toString();
		String datum = DateTime.now().year().getAsString() + "-" + DateTime.now().monthOfYear().getAsString() + "-"
				+ DateTime.now().dayOfMonth().getAsString();
		String ersteller = execution.getVariable("user").toString();

		execution.setVariable("datum", datum);
		try {

			Connection connect = null;

			String url = "jdbc:mysql://pruefungsamt.ckxtdfafgwid.eu-central-1.rds.amazonaws.com:3306/dashboardDB";

			Properties props = new Properties();
			props.setProperty("user", Config.getConfig(Config.DB_USER));
			props.setProperty("password", Config.getConfig(Config.DB_PASS));
			props.setProperty("useSSL", "false");
			props.setProperty("autoReconnect", "true");

			Class.forName("com.mysql.jdbc.Driver");

			connect = DriverManager.getConnection(url, props);

			PreparedStatement statement = connect.prepareStatement(
					"insert into processes (name, description, verbal, bpmn, added, camunda_processID, creator) values (?,?,?,?,?,?,?);",
					Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, name);
			statement.setString(2, beschreibung);
			statement.setString(3, verbal);
			statement.setString(4, bpmn);
			statement.setString(5, datum);
			statement.setString(6, camunda_processID);
			statement.setString(7, ersteller);

			statement.executeUpdate();
			ResultSet keys = statement.getGeneratedKeys();
			keys.next();
			int key = keys.getInt(1);
			keys.close();

			execution.setVariable("DBID", key);
			connect.close();

		} catch (SQLException e) {
			execution.setVariable("err1", e.toString());

		} catch (Exception e) {
			execution.setVariable("err2", e.getMessage());
			e.printStackTrace();
		}
	}
}