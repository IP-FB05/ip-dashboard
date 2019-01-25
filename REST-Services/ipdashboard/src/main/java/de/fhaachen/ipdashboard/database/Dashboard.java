package de.fhaachen.ipdashboard.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.regex.Pattern;

import org.springframework.web.bind.annotation.RequestParam;

import de.fhaachen.ipdashboard.model.System;
import de.fhaachen.ipdashboard.model.Category;
import de.fhaachen.ipdashboard.model.Usergroup;
import de.fhaachen.ipdashboard.model.Document;
import de.fhaachen.ipdashboard.model.Process;
import de.fhaachen.ipdashboard.model.Subs;
import de.fhaachen.ipdashboard.model.ProcessInstance;
import de.fhaachen.ipdashboard.utils.Config;

public class Dashboard {
	private Connection connect = null;

	private PreparedStatement preparedStatement = null;

	private ResultSet resultSet = null;


	// CONNECTION
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


	/**
	 * 
	 * SYSTEMS
	 * 
	 * getSystems
	 * addSystems
	 * deleteSystem
	 * 
	 */

	 // GET systems
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

	// ADD System
	public boolean addSystem(System input) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("INSERT INTO systems (name, description, link) VALUES (?, ?, ?)");
		preparedStatement.setString(1, input.getName());
		preparedStatement.setString(2, input.getDescription());
		preparedStatement.setString(3, input.getLink());
		preparedStatement.execute();

		return true;
	}

	// DELETE System
	public boolean deleteSystem(int systemID) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("DELETE FROM systems WHERE systemID = ?");
		preparedStatement.setInt(1, systemID);
		preparedStatement.execute();

		return true;
	}

	/**
	 * 
	 * DOCUMENTS
	 * 
	 * getDocuments
	 * getDocumentsLimit
	 * getFilteredDocuments
	 * addDocument
	 * deleteDocument
	 * 
	 */

	// GET Documents
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

	// GET DocumentsLimit
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

	// GET Filtered Documents
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

	// ADD Document
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

	// DELETE Document
	public boolean deleteDocument(int documentID) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("DELETE FROM documents WHERE documentID = ?");
		preparedStatement.setInt(1, documentID);
		preparedStatement.execute();

		return true;
	}

	/**
	 * 
	 * PROCESSES
	 * 
	 * getProcesses
	 * getUserGroupsFromProcess
	 * getProcess
	 * getRunningProcesses
	 * addProcess
	 * deleteProcess
	 * addProcessInstance
	 * 
	 */

	 // GET Processes
	public Process[] getProcesses(String role) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("SELECT * FROM processes WHERE allowed_usergroups LIKE ?");
		preparedStatement.setString(1, "%" + role + "%");
		resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			resultSet.last();
			int rowNumber = resultSet.getRow();
			Process[] process = new Process[rowNumber];
			resultSet.first();
			for (int i = 0; i < rowNumber; i++) {
				process[i] = new Process(resultSet.getInt(1), resultSet.getString("name"),
						resultSet.getString("description"), resultSet.getString("verbal"),
						resultSet.getString("bpmn"), resultSet.getString("added"),
						resultSet.getString("camunda_processID"), resultSet.getString("allowed_usergroups"));
				resultSet.next();
			}
			return process;
		}
		return null;
	}

	// GET UserGroups from Process
	public String getUserGroupsFromProcess(int pid) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("SELECT allowed_usergroups FROM processes WHERE processID = ?");
		preparedStatement.setInt(1, pid);
		resultSet = preparedStatement.executeQuery();
		String userGroupsProcess = "";
		if (resultSet.first()) {
			userGroupsProcess = resultSet.getString("allowed_usergroups");
			return userGroupsProcess;
		}
		return null;
	}



	// GET Process with ID
	public Process getProcess(int processID) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("SELECT * FROM processes WHERE processID = ?");
		preparedStatement.setInt(1, processID);
		resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			Process process = new Process(resultSet.getInt(1), resultSet.getString("name"),
					resultSet.getString("description"), resultSet.getString("verbal"),
					resultSet.getString("bpmn"), resultSet.getString("added"),
					resultSet.getString("camunda_processID"), resultSet.getString("allowed_usergroups"));
			return process;
		}
		return null;
	}

	// GET Running Processes
	public Process[] getRunningProcesses() throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement(
				"SELECT * FROM dashboardDB.processes JOIN dashboardDB.processes_has_process_instance ON dashboardDB.processes.processID = dashboardDB.processes_has_process_instance.processes_processID JOIN dashboardDB.process_instance ON dashboardDB.process_instance.instanceID = dashboardDB.processes_has_process_instance.process_instance_instanceID");
		resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			resultSet.last();
			int rowNumber = resultSet.getRow();
			Process[] process = new Process[rowNumber];
			resultSet.first();
			for (int i = 0; i < rowNumber; i++) {
				process[i] = new Process(resultSet.getInt(1), resultSet.getString("name"),
						resultSet.getString("description"), resultSet.getString("verbal"),
						resultSet.getString("bpmn"), resultSet.getString("added"),
						resultSet.getString("camunda_processID"), resultSet.getString("allowed_usergroups"));
				resultSet.next();
			}
			return process;
		}
		return null;
	}

	// ADD Process
	public boolean addProcess(Process input) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement(
				"INSERT INTO processes (name, description, verbal, bpmn, added, camunda_processID, allowed_usergroups) VALUES (?, ?, ?, ?, CURDATE(), ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, input.getName());
		preparedStatement.setString(2, input.getDescription());
		preparedStatement.setString(3, input.getVerbal());
		preparedStatement.setString(4, input.getBpmn());
		preparedStatement.setString(5, input.getCamunda_processID());
		preparedStatement.setString(6, input.getAllowed_usergroups());
		preparedStatement.execute();

		resultSet = preparedStatement.getGeneratedKeys();
		int processes_processID=0;
		if(resultSet.next()){
			processes_processID=resultSet.getInt(1);
		}

		String[] userGroupList = input.getAllowed_usergroups().split("\\s*,\\s*");
		
		

		int usergroups_usergroup_id = 0;
		for (int i = 0; i < userGroupList.length; i++){
			preparedStatement = connect.prepareStatement(
				"SELECT usergroup_id FROM dashboardDB.usergroups WHERE usergroup_name LIKE ?");
			preparedStatement.setString(1, userGroupList[i]);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.first()) {
				usergroups_usergroup_id = resultSet.getInt(1);
			}
		

			preparedStatement = connect.prepareStatement(
				"INSERT INTO allowed_groups (processes_processID, usergroups_usergroup_id) VALUES (?,?) ");
			preparedStatement.setInt(1, processes_processID);
			preparedStatement.setInt(2, usergroups_usergroup_id);
			preparedStatement.execute();
		}
		return true;
	}

	

	// ADD ProcessInstance
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

	// DELETE Process
	public boolean deleteProcess(int processID) throws SQLException, ClassNotFoundException {

		preparedStatement = connect.prepareStatement("DELETE FROM allowed_groups WHERE processes_processID = ?");
		preparedStatement.setInt(1, processID);
		preparedStatement.execute();

		preparedStatement = connect.prepareStatement("DELETE FROM processes WHERE processID = ?");
		preparedStatement.setInt(1, processID);
		preparedStatement.execute();

		return true;
	}
	
	public int addProcessDeploy(String name, String beschreibung, String bpmn, String verbal,
			String camunda_processID, String datum, String ersteller) throws SQLException {
		preparedStatement = connect.prepareStatement(
				"insert into processes (name, description, verbal, bpmn, added, camunda_processID, creator)"
				+ " values (?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, beschreibung);
		preparedStatement.setString(3, verbal);
		preparedStatement.setString(4, bpmn);
		preparedStatement.setString(5, datum);
		preparedStatement.setString(6, camunda_processID);
		preparedStatement.setString(7, ersteller);
		preparedStatement.executeUpdate();
		
		ResultSet keys = preparedStatement.getGeneratedKeys();		
		keys.next();
		int key = keys.getInt(1);
		keys.close();
		
		return key;
	}
	
	public boolean deleteProcessDeploy(Long dbID) throws SQLException {
		
		preparedStatement = connect.prepareStatement(
				"DELETE FROM `allowed_groups` WHERE `processes_processID` = ?;");
		preparedStatement.setLong(1, dbID);
		preparedStatement.executeUpdate();
		
		preparedStatement.clearParameters();
		
		preparedStatement = connect.prepareStatement("DELETE FROM processes WHERE processID = ?;");		
		preparedStatement.setLong(1, dbID);			
		int resultInt = preparedStatement.executeUpdate();

		if(resultInt == 1) {
			return true;
		}		
		return false;
	}
	
	public boolean patchProcessDeploy(Long dbID, boolean published) throws SQLException {
		preparedStatement = connect.prepareStatement(
				"UPDATE processes SET `published` = ? WHERE processID = ?;");
		preparedStatement.setBoolean(1, published);
		preparedStatement.setLong(2, dbID);		
		int resultInt = preparedStatement.executeUpdate();

		if(resultInt == 1) {
			return true;
		}		
		return false;
	}
	
	public boolean patchProcessDeployUsergroup(Long dbID, String userGroup) throws SQLException {
		preparedStatement = connect.prepareStatement(
				"UPDATE processes SET `allowed_usergroups` = ? WHERE processID = ?;");		
		preparedStatement.setString(1, userGroup);
		preparedStatement.setLong(2, dbID);
		int resultInt = preparedStatement.executeUpdate();
	
		String[] spitUsergroups = userGroup.split(Pattern.quote(","));
		
		
		for (String group : spitUsergroups) {
			
			preparedStatement.clearParameters();
			
			//getUserGroupId
			int ugID = -1;
			preparedStatement = connect.prepareStatement(
					"SELECT usergroup_id FROM dashboardDB.usergroups WHERE usergroup_name = ?;");
			preparedStatement.setString(1, group);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.first()) {
				ugID =  resultSet.getInt(1);
			}
			
			//insert
			if(ugID != -1) {
				preparedStatement.clearParameters();
				
				preparedStatement = connect.prepareStatement(
						"INSERT INTO `allowed_groups` (`processes_processID`, `usergroups_usergroup_id`)"
						+ " VALUES (?,?);");
				preparedStatement.setLong(1, dbID);
				preparedStatement.setInt(2, ugID);
				resultInt = preparedStatement.executeUpdate();		
			}
		}
		return true;
	}

	/**
	 * 
	 * CATEGORY
	 * 
	 * getCategories
	 * getCategory
	 * addCategory
	 * deleteCategory
	 * 
	 */

	public Category[] getCategories() throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("SELECT * FROM category");
		resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			resultSet.last();
			int rowNumber = resultSet.getRow();
			Category[] cat = new Category[rowNumber];
			resultSet.first();
			for (int i = 0; i < rowNumber; i++) {
				cat[i] = new Category(resultSet.getInt(1), resultSet.getString("name"));
				resultSet.next();
			}
			return cat;
		}
		return null;
	}

	// GET Category with ID
	public Category getCategory(int categoryID) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("SELECT * FROM category WHERE categoryID = ?");
		preparedStatement.setInt(1, categoryID);
		resultSet = preparedStatement.executeQuery();
		if (resultSet.first()) {
			Category category = new Category(resultSet.getInt(1), resultSet.getString("name"));
			return category;
		}
		return null;
	}

	// ADD Category
	public boolean addCategory(Category category) throws SQLException, ClassNotFoundException {
		preparedStatement = connect.prepareStatement("INSERT INTO category (name) VALUES (?)");
		preparedStatement.setString(1, category.getName());
		preparedStatement.execute();

		return true;
	}

	// DELETE Category
	public boolean deleteCategory(int categoryID) throws SQLException, ClassNotFoundException {

		preparedStatement = connect.prepareStatement("DELETE FROM category WHERE categoryID = ?");
		preparedStatement.setInt(1, categoryID);
		preparedStatement.execute();

		return true;
	}

	/**
	 * 
	 * SUBS
	 * 
	 * getSubs
	 * getSub
	 * getMySubscribedProcesses
	 * getSubscribedProcessInstances
	 * addSubscribedProcess
	 * deleteSubscribedProcess
	 * addSubscribedRunningProcess
	 * deleteSubscribedRunningProcess
	 * AddUserToNotification
	 * DeleteUserFromNotification
	 * 
	 */

	// GET Subs
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

	// GET Sub
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

	// GET Subscribed Processes
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
						resultSet.getString("description"), resultSet.getString("verbal"),
						resultSet.getString("bpmn"), resultSet.getString("added"),
						resultSet.getString("camunda_processID"), resultSet.getString("allowed_usergroups"));
				resultSet.next();
			}
			return process;
		}
		return null;
	}

	// GET Subscribed Process Instances
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
						resultSet.getString("description"), resultSet.getString("verbal"),
						resultSet.getString("bpmn"), resultSet.getString("added"),
						resultSet.getString("camunda_processID"), resultSet.getString("allowed_usergroups"));
				resultSet.next();
			}
			return process;
		}
		return null;
	}

	// ADD Subscribed Process
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

	// DELETE Subscribed Process
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

	// ADD Subscribed Running Process
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

	// DELETE Subscribed Running Process
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

	// DELETE UserFromNotification
	public boolean deleteUserFromNotification(String username) throws SQLException, ClassNotFoundException {

		preparedStatement = connect.prepareStatement("DELETE FROM notification WHERE username LIKE (?)");
		preparedStatement.setString(1, username);
		preparedStatement.execute();

		return true;

	}

	// ADD UserToNotification
	public boolean addUserToNotification(String username) throws SQLException, ClassNotFoundException {


		preparedStatement = connect.prepareStatement("INSERT INTO notification (username) VALUES (?)");
		preparedStatement.setString(1, username);
		preparedStatement.execute();

		return true;

	
	}

	

	
	/**
	 * 
	 * USERGROUPS
	 * 
	 * getUsergroups
	 * DeleteUserFromNotification
	 * 
	 */


	 // GET Usergroups
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

	public void close() throws SQLException {
		connect.close();
	}


}
