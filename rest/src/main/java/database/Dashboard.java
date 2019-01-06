package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import controller.System;
import controller.Usergroup;
import controller.Document;
import controller.Process;
import controller.Subs;
import controller.ProcessInstance;
import utils.Config;

public class Dashboard {
	private Connection connect = null;

	private PreparedStatement preparedStatement = null;

	private ResultSet resultSet = null;

	public Dashboard() throws SQLException, ClassNotFoundException {
		String url = "jdbc:mysql://pruefungsamt.ckxtdfafgwid.eu-central-1.rds.amazonaws.com:3306/dashboardDB";
		Properties props = new Properties();
		props.setProperty("user", Config.getConfig(Config.DB_USER));
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
				"SELECT documentID, category.name AS 'Categoriename', documents.name, lastChanged, link FROM documents JOIN category ON category.categoryID = documents.categoryID");
		resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			resultSet.last();
			int rowNumber = resultSet.getRow();
			Document[] doc = new Document[rowNumber];
			resultSet.first();
			for (int i = 0; i < rowNumber; i++) {
				doc[i] = new Document(resultSet.getInt(1), resultSet.getString("Categoriename"),
						resultSet.getString("name"), resultSet.getDate("lastChanged"), resultSet.getString("link"));
				resultSet.next();
			}
			return doc;
		}
		return null;
	}

	public Document[] getDocumentsLimit() throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement(
				"SELECT documentID, category.name AS 'Categoriename', documents.name, lastChanged, link FROM documents JOIN category ON category.categoryID = documents.categoryID ORDER BY RAND() limit 3");
		resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			resultSet.last();
			int rowNumber = resultSet.getRow();
			Document[] doc = new Document[rowNumber];
			resultSet.first();
			for (int i = 0; i < rowNumber; i++) {
				doc[i] = new Document(resultSet.getInt(1), resultSet.getString("Categoriename"),
						resultSet.getString("name"), resultSet.getDate("lastChanged"), resultSet.getString("link"));
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

			preparedStatement = connect.prepareStatement("SELECT categoryID FROM category WHERE category.name LIKE ?");
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
						resultSet.getString("description"), resultSet.getString("pic"), resultSet.getString("warFile"),
						resultSet.getString("bpmn"), resultSet.getString("added"),
						resultSet.getString("camunda_processID"));
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
					resultSet.getString("description"), resultSet.getString("pic"), resultSet.getString("warFile"),
					resultSet.getString("bpmn"), resultSet.getString("added"),
					resultSet.getString("camunda_processID"));
			return process;
		}
		return null;
	}

	public boolean addProcess(Process input) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement(
				"INSERT INTO processes (name, description, pic, warFile, bpmn, added, camunda_processID) VALUES (?, ?, ?, ?, ?, CURDATE(), ?)");
		preparedStatement.setString(1, input.getName());
		preparedStatement.setString(2, input.getDescription());
		preparedStatement.setString(3, input.getPic());
		preparedStatement.setString(4, input.getwarFile());
		preparedStatement.setString(5, input.getBpmn());
		preparedStatement.setString(6, input.getCamunda_processID());
		preparedStatement.execute();
		
		return true;
	}

	public boolean addProcessWithUG(Process input, int[] userGroups) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement(
				"INSERT INTO processes (name, description, pic, warFile, bpmn, added, camunda_processID) VALUES (?, ?, ?, ?, ?, CURDATE(), ?)");
		preparedStatement.setString(1, input.getName());
		preparedStatement.setString(2, input.getDescription());
		preparedStatement.setString(3, input.getPic());
		preparedStatement.setString(4, input.getwarFile());
		preparedStatement.setString(5, input.getBpmn());
		preparedStatement.setString(6, input.getCamunda_processID());
		preparedStatement.execute();
		ResultSet resultSet = preparedStatement.getGeneratedKeys();
		int id=0;
		if(resultSet.next()){
			id=resultSet.getInt(1);
		}
		this.addAllowedUserGroups(userGroups,id);		

		return true;
	}

	public boolean addProcessInstance(ProcessInstance input) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("INSERT INTO process_instance (camunda_instanceID) VALUES (?)");
		preparedStatement.setString(1, input.getId());
		preparedStatement.execute();

		int instanceID = 0;
		int processID = 0;
		preparedStatement = connect.prepareStatement("SELECT LAST_INSERT_ID()");
		resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			instanceID = resultSet.getInt(1);
		} else {
			return false;
		}

		preparedStatement = connect
				.prepareStatement("SELECT processID FROM dashboardDB.processes WHERE camunda_processID LIKE ?");
		preparedStatement.setString(1, input.getDefinitionId());
		resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			processID = resultSet.getInt(1);
		} else {
			return false;
		}

		preparedStatement = connect.prepareStatement(
				"INSERT INTO processes_has_process_instance (processes_processID, process_instance_instanceID) VALUES (?, ?)");
		preparedStatement.setInt(1, processID);
		preparedStatement.setInt(2, instanceID);
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

	// FILTER SECTION
	public Document[] getFilteredDocuments(String name) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement(
				"SELECT documentID, category.name AS 'Categoriename', documents.name, lastChanged, link FROM documents JOIN category ON category.categoryID = documents.categoryID WHERE category.name = ?");
		preparedStatement.setString(1, name);
		resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			resultSet.last();
			int rowNumber = resultSet.getRow();
			Document[] doc = new Document[rowNumber];
			resultSet.first();
			for (int i = 0; i < rowNumber; i++) {
				doc[i] = new Document(resultSet.getInt(1), resultSet.getString("Categoriename"),
						resultSet.getString("name"), resultSet.getDate("lastChanged"), resultSet.getString("link"));
				resultSet.next();
			}
			return doc;
		}
		return null;
	}

	// SUBS
	public Subs[] getSubs() throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("SELECT * FROM subs");
		resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			resultSet.last();
			int rowNumber = resultSet.getRow();
			Subs[] subs = new Subs[rowNumber];
			resultSet.first();
			for (int i = 0; i < rowNumber; i++) {
				subs[i] = new Subs(resultSet.getInt(1), resultSet.getString("username"));
				resultSet.next();
			}
			return subs;
		}
		return null;
	}

	public Subs getSub(int subID) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("SELECT * FROM subs WHERE subID = ?");
		preparedStatement.setInt(1, subID);
		resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			Subs subs = new Subs(resultSet.getInt(1), resultSet.getString("username"));
			return subs;
		}
		return null;
	}

	// Subscribed Processes
	public Process[] getMySubscribedProcesses(String username) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement(
				"SELECT * FROM dashboardDB.processes JOIN dashboardDB.processes_has_subs ON dashboardDB.processes.processID = dashboardDB.processes_has_subs.processes_processID JOIN dashboardDB.subs ON dashboardDB.subs.subID = dashboardDB.processes_has_subs.subs_subID WHERE subs.username = ?");
		preparedStatement.setString(1, username);
		resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			resultSet.last();
			int rowNumber = resultSet.getRow();
			Process[] process = new Process[rowNumber];
			resultSet.first();
			for (int i = 0; i < rowNumber; i++) {
				process[i] = new Process(resultSet.getInt(1), resultSet.getString("name"),
						resultSet.getString("description"), resultSet.getString("pic"), resultSet.getString("warFile"),
						resultSet.getString("bpmn"), resultSet.getString("added"),
						resultSet.getString("camunda_processID"));
				resultSet.next();
			}
			return process;
		}
		return null;
	}

	// Subscribed Processes with ProcessInstance
	public Process[] getMySubscribedProcessInstances(String username) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement(
				"SELECT * FROM dashboardDB.processes JOIN dashboardDB.processes_has_process_instance ON dashboardDB.processes.processID = dashboardDB.processes_has_process_instance.processes_processID JOIN dashboardDB.process_instance ON dashboardDB.process_instance.instanceID = dashboardDB.processes_has_process_instance.process_instance_instanceID JOIN dashboardDB.process_instance_has_subs ON dashboardDB.process_instance.instanceID = dashboardDB.process_instance_has_subs.process_instance_instanceID JOIN dashboardDB.subs ON dashboardDB.subs.subID = dashboardDB.process_instance_has_subs.subs_subID WHERE subs.username = ?");
		preparedStatement.setString(1, username);
		resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			resultSet.last();
			int rowNumber = resultSet.getRow();
			Process[] process = new Process[rowNumber];
			resultSet.first();
			for (int i = 0; i < rowNumber; i++) {
				process[i] = new Process(resultSet.getInt(1), resultSet.getString("name"),
						resultSet.getString("description"), resultSet.getString("pic"), resultSet.getString("warFile"),
						resultSet.getString("bpmn"), resultSet.getString("added"),
						resultSet.getString("camunda_processID"));
				resultSet.next();
			}
			return process;
		}
		return null;
	}

	// Running Processes
	public Process[] getRunningProcesses() throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement(
				"SELECT * FROM dashboardDB.processes JOIN dashboardDB.processes_has_process_instance ON dashboardDB.processes.processID = dashboardDB.processes_has_process_instance.processes_processID JOIN dashboardDB.process_instance ON dashboardDB.process_instance.instanceID = dashboardDB.processes_has_process_instance.process_instance_instanceID");
		// preparedStatement.setString(1, username);
		resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			resultSet.last();
			int rowNumber = resultSet.getRow();
			Process[] process = new Process[rowNumber];
			resultSet.first();
			for (int i = 0; i < rowNumber; i++) {
				process[i] = new Process(resultSet.getInt(1), resultSet.getString("name"),
						resultSet.getString("description"), resultSet.getString("pic"), resultSet.getString("warFile"),
						resultSet.getString("bpmn"), resultSet.getString("added"),
						resultSet.getString("camunda_processID"));
				resultSet.next();
			}
			return process;
		}
		return null;
	}

	public boolean addSubscribedProcess(int processId, String username) throws SQLException, ClassNotFoundException {

		preparedStatement = connect.prepareStatement("SELECT subID FROM subs WHERE username LIKE ?");
		preparedStatement.setString(1, username);
		resultSet = preparedStatement.executeQuery();

		int subID = 0;
		if (resultSet.first()) {
			subID = resultSet.getInt(1);
		} else {
			preparedStatement = connect.prepareStatement("INSERT INTO subs (username) VALUES (?)");
			preparedStatement.setString(1, username);
			preparedStatement.execute();

			preparedStatement = connect.prepareStatement("SELECT subID FROM subs WHERE username LIKE ?");
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			resultSet.first();
			subID = resultSet.getInt(1);
		}

		preparedStatement = connect
				.prepareStatement("INSERT INTO processes_has_subs (processes_processID, subs_subID) VALUES (?, ?)");
		preparedStatement.setInt(1, processId);
		preparedStatement.setInt(2, subID);
		preparedStatement.execute();

		return true;
	}

	public boolean addSubscribedRunningProcess(int processId, String username)
			throws SQLException, ClassNotFoundException {

		preparedStatement = connect.prepareStatement("SELECT subID FROM subs WHERE username LIKE ?");
		preparedStatement.setString(1, username);
		resultSet = preparedStatement.executeQuery();

		int subID = 0;
		int instanceID = 0;
		if (resultSet.first()) {
			subID = resultSet.getInt(1);
		} else {
			preparedStatement = connect.prepareStatement("INSERT INTO subs (username) VALUES (?)");
			preparedStatement.setString(1, username);
			preparedStatement.execute();

			preparedStatement = connect.prepareStatement("SELECT subID FROM subs WHERE username LIKE ?");
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			resultSet.first();
			subID = resultSet.getInt(1);
		}

		preparedStatement = connect.prepareStatement(
				"SELECT process_instance_instanceID FROM processes_has_process_instance WHERE processes_processID = ?");
		preparedStatement.setInt(1, processId);
		resultSet = preparedStatement.executeQuery();
		resultSet.first();
		instanceID = resultSet.getInt(1);

		preparedStatement = connect.prepareStatement(
				"INSERT INTO process_instance_has_subs (process_instance_instanceID, subs_subID) VALUES (?, ?)");
		preparedStatement.setInt(1, instanceID);
		preparedStatement.setInt(2, subID);
		preparedStatement.execute();

		return true;
	}

	public boolean deleteUserFromNotification(String username) throws SQLException, ClassNotFoundException {

		preparedStatement = connect.prepareStatement("DELETE FROM notification WHERE username LIKE (?)");
		preparedStatement.setString(1, username);
		preparedStatement.execute();

		return true;

		/*
		 * else { preparedStatement =
		 * connect.prepareStatement("INSERT INTO notification (username) VALUES (?)");
		 * preparedStatement.setString(1, username); preparedStatement.execute();
		 * 
		 * return false; } } else { preparedStatement = connect.
		 * prepareStatement("SELECT username FROM notification WHERE username LIKE ?");
		 * preparedStatement.setString(1, username); resultSet =
		 * preparedStatement.executeQuery();
		 * 
		 * if (resultSet.first()) { preparedStatement =
		 * connect.prepareStatement("DELETE FROM notification WHERE username = ?");
		 * preparedStatement.setString(1, username); preparedStatement.execute();
		 * 
		 * return true; } else { return true; } }
		 */
	}

	public boolean addUserToNotification(String username) throws SQLException, ClassNotFoundException {

		/*
		 * preparedStatement =
		 * connect.prepareStatement("SELECT * FROM notification WHERE username LIKE ?");
		 * preparedStatement.setString(1, username); resultSet =
		 * preparedStatement.executeQuery();
		 * 
		 * if (resultSet.first()) { preparedStatement =
		 * connect.prepareStatement("DELETE FROM notification WHERE username = ?");
		 * preparedStatement.setString(1, username); preparedStatement.execute(); } else
		 * {
		 */
		preparedStatement = connect.prepareStatement("INSERT INTO notification (username) VALUES (?)");
		preparedStatement.setString(1, username);
		preparedStatement.execute();

		return true;

		/*
		 * else { preparedStatement =
		 * connect.prepareStatement("INSERT INTO notification (username) VALUES (?)");
		 * preparedStatement.setString(1, username); preparedStatement.execute();
		 * 
		 * return false; } } else { preparedStatement = connect.
		 * prepareStatement("SELECT username FROM notification WHERE username LIKE ?");
		 * preparedStatement.setString(1, username); resultSet =
		 * preparedStatement.executeQuery();
		 * 
		 * if (resultSet.first()) { preparedStatement =
		 * connect.prepareStatement("DELETE FROM notification WHERE username = ?");
		 * preparedStatement.setString(1, username); preparedStatement.execute();
		 * 
		 * return true; } else { return true; } }
		 * 
		 * }
		 */
	}

	public boolean deleteSubscribedProcess(int processId, String username) throws SQLException, ClassNotFoundException {

		int subID = 0;
		preparedStatement = connect.prepareStatement("SELECT subID FROM subs WHERE username LIKE ?");
		preparedStatement.setString(1, username);
		resultSet = preparedStatement.executeQuery();
		resultSet.first();
		subID = resultSet.getInt(1);

		preparedStatement = connect.prepareStatement(
				"DELETE FROM processes_has_subs WHERE processes_processID LIKE ? AND subs_subID LIKE ?");
		preparedStatement.setInt(1, processId);
		preparedStatement.setInt(2, subID);
		preparedStatement.execute();

		preparedStatement = connect
				.prepareStatement("SELECT subs_subID FROM processes_has_subs WHERE subs_subID LIKE ?");
		preparedStatement.setInt(1, subID);
		resultSet = preparedStatement.executeQuery();

		if (resultSet.first()) {
			return true;
		} else {
			preparedStatement = connect
					.prepareStatement("SELECT subs_subID FROM process_instance_has_subs WHERE subs_subID LIKE ?");
			preparedStatement.setInt(1, subID);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.first()) {
				return true;
			} else {
				preparedStatement = connect.prepareStatement("DELETE FROM subs WHERE subID = ?");
				preparedStatement.setInt(1, subID);
				preparedStatement.execute();
			}
		}

		return true;
	}

	public boolean deleteSubscribedRunningProcess(int processId, String username)
			throws SQLException, ClassNotFoundException {

		preparedStatement = connect.prepareStatement("SELECT subID FROM subs WHERE username LIKE ?");
		preparedStatement.setString(1, username);
		resultSet = preparedStatement.executeQuery();
		resultSet.first();
		int subID = resultSet.getInt(1);

		preparedStatement = connect.prepareStatement(
				"SELECT process_instance_instanceID FROM processes_has_process_instance WHERE processes_processID LIKE ?");
		preparedStatement.setInt(1, processId);
		resultSet = preparedStatement.executeQuery();
		resultSet.first();
		int instanceID = resultSet.getInt(1);

		preparedStatement = connect.prepareStatement(
				"DELETE FROM process_instance_has_subs WHERE process_instance_instanceID = ? AND subs_subID = ?");
		preparedStatement.setInt(1, instanceID);
		preparedStatement.setInt(2, subID);
		preparedStatement.execute();

		preparedStatement = connect
				.prepareStatement("SELECT subs_subID FROM process_instance_has_subs WHERE subs_subID = ?");
		preparedStatement.setInt(1, subID);
		resultSet = preparedStatement.executeQuery();

		if (resultSet.first()) {
			return true;
		} else {
			preparedStatement = connect
					.prepareStatement("SELECT subs_subID FROM processes_has_subs WHERE subs_subID = ?");
			preparedStatement.setInt(1, subID);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.first()) {
				return true;
			} else {
				preparedStatement = connect.prepareStatement("DELETE FROM subs WHERE subID = ?");
				preparedStatement.setInt(1, subID);
				preparedStatement.execute();
			}
		}

		return true;
	}



	public Usergroup[] getUsergroups() throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("SELECT * FROM usergroups");
		resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			resultSet.last();
			int rowNumber = resultSet.getRow();
			Usergroup[] usergroups = new Usergroup[rowNumber];
			resultSet.first();
			for (int i = 0; i < rowNumber; i++) {
				usergroups[i] = new Usergroup(resultSet.getInt(1), resultSet.getString("usergroup_name"));
				resultSet.next();
			}
			return usergroups;
		}
		return null;
	}

	public boolean addAllowedUserGroups(int[] userGroups, int id) throws SQLException, ClassNotFoundException {
		for (int i = 0; i < userGroups.length; i++){
			preparedStatement = connect.prepareStatement(
				"INSERT INTO allowed_groups (processes_processID, usergroups_usergroup_id) VALUES (?, ?)");
			preparedStatement.setInt(1, id);
			preparedStatement.setInt(2, userGroups[i]);
			preparedStatement.execute();
		}
		return true;
	}

}
