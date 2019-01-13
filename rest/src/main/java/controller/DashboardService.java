package controller;

import java.sql.SQLException;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import database.Dashboard;

import model.System;
import model.Category;
import model.Document;
import model.Notification;
import model.Process;
import model.ProcessInstance;
import model.Subscription;
import model.Usergroup;




@CrossOrigin(origins = "*")
@RestController
public class DashboardService {

	@RequestMapping("/running")
	public String running() {
		return "Server is running";
	}

	/**
	 * 
	 * SYSTEMS
	 * 
	 * GET
	 * ADD
	 * DELETE
	 * 
	 */

	 // GET
    @RequestMapping(value = "/systems", method = RequestMethod.GET)
    public System[] getSystems() throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	System[] result = dash.getSystems();
    	dash.close();
    	return result;
	}
	
	// ADD
	@RequestMapping(value = "/systemAdd", method = RequestMethod.POST)
	@ResponseBody
    public boolean addSystem(@RequestBody System input) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.addSystem(input);
    	dash.close();
    	return result;
	}

	// DELETE
	@RequestMapping(value = "/systemDelete/{systemID}", method = RequestMethod.DELETE)
    public boolean deleteSystem(@PathVariable int systemID) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.deleteSystem(systemID);
    	dash.close();
    	return result;
	}



	/**
	 * 
	 * DOCUMENTS
	 * 
	 * GET
	 * GET DocumentsLimit
	 * GET FilteredDocuments
	 * ADD
	 * DELETE
	 * 
	 */


	 // GET
	@RequestMapping(value = "/documents", method = RequestMethod.GET)
    public Document[] getDocuments() throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	Document[] result = dash.getDocuments();
    	dash.close();
    	return result;
	}

	// GET DocumentsLimit
	@RequestMapping(value = "/documentsLimit", method = RequestMethod.GET)
    public Document[] getDocumentsLimit() throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	Document[] result = dash.getDocumentsLimit();
    	dash.close();
    	return result;
	}

	//GET Filtered Documents
	@RequestMapping(value = "/filter/documents", method = RequestMethod.GET)
	public @ResponseBody Document[] getDocuments (@RequestParam String name) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	Document[] result = dash.getFilteredDocuments(name);
    	dash.close();
    	return result;
	}
	
	// ADD
	@RequestMapping(value = "/documentAdd", method = RequestMethod.POST)
	@ResponseBody
    public boolean addDocument(@RequestBody Document input) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.addDocument(input);
    	dash.close();
    	return result;
	}

	// DELETE
	@RequestMapping(value = "/documentDelete/{documentID}", method = RequestMethod.DELETE)
    public boolean deleteDocument(@PathVariable int documentID) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.deleteDocument(documentID);
    	dash.close();
    	return result;
	}
	

	/**
	 * 
	 * PROCESSES
	 * 
	 * GET
	 * GET with ID
	 * ADD
	 * ADD ProcessInstance
	 * DELETE
	 * 
	 */

	// GET
	@RequestMapping(value = "/processes", method = RequestMethod.GET)
    public Process[] getProcesses() throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	Process[] result = dash.getProcesses();
    	dash.close();
    	return result;
	}

	 // GET with ID
	@RequestMapping(value = "/process/{processID}", method = RequestMethod.GET)
    public Process getProcess(@PathVariable int processID) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	Process result = dash.getProcess(processID);
    	dash.close();
    	return result;
	}
	
	// ADD
	@RequestMapping(value = "/processAdd", method = RequestMethod.POST)
	@ResponseBody
    public boolean addProcess(@RequestBody Process input) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.addProcess(input);
    	dash.close();
    	return result;
	}

	/*
	@RequestMapping(value = "/processAddwithUG", method = RequestMethod.POST)
	@ResponseBody
    public boolean addProcesswithUG(@RequestBody Process process, @RequestParam() int[] selectedUserGroups) throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard(); 
    	boolean result = dash.addProcessWithUG(process, selectedUserGroups);
    	dash.close();
    	return result;
	}
	*/

	// ADD ProcessInstance
	@RequestMapping(value = "/processInstanceAdd", method = RequestMethod.POST)
	@ResponseBody
    public boolean addProcessInstance(@RequestBody ProcessInstance input) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.addProcessInstance(input);
    	dash.close();
    	return result;
	}

	// DELETE
	@RequestMapping(value = "/processDelete/{processID}", method = RequestMethod.DELETE)
    public boolean deleteProcess(@PathVariable int processID) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.deleteProcess(processID);
    	dash.close();
    	return result;
	}
	
	/**
	 * 
	 * CATEGORY
	 * 
	 * GET
	 * ADD
	 * ADD ProcessInstance
	 * DELETE
	 * 
	 */

	// GET
	@RequestMapping(value = "/category/all", method = RequestMethod.GET)
	public Category[] getCategories() throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard(); 
    	Category[] result = dash.getCategories();
    	dash.close();
    	return result;
	}

	// GET with ID
	@RequestMapping(value = "/category/{categoryID}", method = RequestMethod.GET)
    public Category getCategory(@PathVariable int categoryID) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	Category result = dash.getCategory(categoryID);
    	dash.close();
    	return result;
	}

	// ADD
	@RequestMapping(value = "/category/add", method = RequestMethod.POST)
	@ResponseBody
    public boolean addCategory(@RequestBody Category category) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.addCategory(category);
    	dash.close();
    	return result;
	}

	// DELETE
	@RequestMapping(value = "/category/{categoryID}", method = RequestMethod.DELETE)
    public boolean deleteCategory(@PathVariable int categoryID) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.deleteCategory(categoryID);
    	dash.close();
    	return result;
	}

	/**
	 * 
	 * NOTIFICATION
	 * 
	 * ADD User to Notification
	 * DELETE User from Notification
	 * 
	 */

	 // ADD Notification
	@RequestMapping(value = "/notification/add", method = RequestMethod.POST)
	@ResponseBody
	public boolean addUserToNotification(@RequestBody Notification notification) throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard();
		boolean result = dash.addUserToNotification(notification.getUsername());
		dash.close();
		return result;
	}

	// DELETE Notification
	@RequestMapping(value = "/notification/delete", method = RequestMethod.DELETE)
	public boolean deleteUserFromNotification(@RequestParam String username) throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard();
		boolean result = dash.deleteUserFromNotification(username);
		dash.close();
		return result;
	}

	/**
	 * 
	 * SUBS
	 * 
	 * GET subscribedProcess
	 * GET subscribedRunningProcess
	 * GET runningProcesses
	 * ADD SubscribedProcess
	 * ADD SubscribedRunningProcess
	 * DELETE SubscribedProcess
	 * DELETE SubscribedRunningProcess
	 * 
	 */


	 // GET subscribedProcess
	@RequestMapping(value = "/subs/mysubscribedProcesses", method = RequestMethod.GET)
	public @ResponseBody Process[] getMySubscribedProcesses(@RequestParam String user) throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard(); 
		Process[] result = dash.getMySubscribedProcesses(user);
    	dash.close();
    	return result;
	}

	// GET subscribedRunningProcess
	@RequestMapping(value = "/subs/mysubscribedProcessInstances", method = RequestMethod.GET)
	public @ResponseBody Process[] getMySubscribedProcessInstances(@RequestParam String user) throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard(); 
		Process[] result = dash.getMySubscribedProcessInstances(user);
    	dash.close();
    	return result;
	}

	// GET runningProcesses
	@RequestMapping(value = "/subs/runningProcesses", method = RequestMethod.GET)
	public @ResponseBody Process[] getRunningProcesses() throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard(); 
		Process[] result = dash.getRunningProcesses();
    	dash.close();
    	return result;
	}

	// ADD SubscribedProcess
	@RequestMapping(path = "/subs/addSub", method = RequestMethod.POST)
	@ResponseBody
	public boolean addSubscribedProcess(@RequestBody Subscription subscription) throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard(); 
		boolean result = dash.addSubscribedProcess(subscription.getProcessID(),subscription.getUsername());
    	dash.close();
    	return result;
	}

	// ADD SubscribedRunningProcess
	@RequestMapping(value = "/subs/addRunningSub", method = RequestMethod.POST)
	@ResponseBody
	public boolean addSubscribedRunningProcess(@RequestBody Subscription subscription) throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard(); 
		boolean result = dash.addSubscribedRunningProcess(subscription.getProcessID(), subscription.getUsername());
    	dash.close();
    	return result;
	}
	
	// DELETE SubscribedProcess
	@RequestMapping(value = "/subs/deleteSubscribedProcess/{processId}", method = RequestMethod.DELETE)
	@ResponseBody
	public boolean deleteSubscribedProcess(@PathVariable int processId, @RequestParam String username) throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard();
		boolean result = dash.deleteSubscribedProcess(processId, username);
		dash.close();
		return result;
	}

	// DELETE SubscribedRunningProcess
	@RequestMapping(value = "/subs/deleteSubscribedRunningProcess/{processId}", method = RequestMethod.DELETE)
	@ResponseBody
	public boolean deleteSubscribedRunningProcess(@PathVariable int processId, @RequestParam String username) throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard();
		boolean result = dash.deleteSubscribedRunningProcess(processId, username);
		dash.close();
		return result;
	}

	/**
	 * 
	 * USERGROUPS
	 * 
	 * GET Usergroups
	 * 
	 */
	
	@RequestMapping(value = "/usergroups/all", method = RequestMethod.GET)
	public @ResponseBody Usergroup[] getUsergroups() throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard(); 
		Usergroup[] result = dash.getUsergroups();
    	dash.close();
    	return result;
	}
}