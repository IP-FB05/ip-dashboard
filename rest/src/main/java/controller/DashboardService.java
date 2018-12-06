package controller;

import java.sql.SQLException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import database.Dashboard;

@CrossOrigin(origins = "*")
@RestController
public class DashboardService {

	@RequestMapping("/running")
	public String running() {
		return "Server is running";
	}
	
    @RequestMapping(value = "/systems", method = RequestMethod.GET)
    public System[] getSystems() throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	System[] result = dash.getSystems();
    	dash.close();
    	return result;
	}
	
	@RequestMapping(value = "/systemAdd", method = RequestMethod.POST)
	@ResponseBody
    public boolean addSystem(@RequestBody System input) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.addSystem(input);
    	dash.close();
    	return result;
	}

	@RequestMapping(value = "/systemDelete/{systemID}", method = RequestMethod.DELETE)
    public boolean deleteSystem(@PathVariable int systemID) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.deleteSystem(systemID);
    	dash.close();
    	return result;
	}


	@RequestMapping(value = "/documents", method = RequestMethod.GET)
    public Document[] getDocuments() throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	Document[] result = dash.getDocuments();
    	dash.close();
    	return result;
	}

	@RequestMapping(value = "/documentsLimit", method = RequestMethod.GET)
    public Document[] getDocumentsLimit() throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	Document[] result = dash.getDocumentsLimit();
    	dash.close();
    	return result;
	}
	
	@RequestMapping(value = "/documentAdd", method = RequestMethod.POST)
	@ResponseBody
    public boolean addDocument(@RequestBody Document input) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.addDocument(input);
    	dash.close();
    	return result;
	}

	@RequestMapping(value = "/documentDelete/{documentID}", method = RequestMethod.DELETE)
    public boolean deleteDocument(@PathVariable int documentID) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.deleteDocument(documentID);
    	dash.close();
    	return result;
	}
	
	@RequestMapping(value = "/processes", method = RequestMethod.GET)
    public Process[] getProcesses() throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	Process[] result = dash.getProcesses();
    	dash.close();
    	return result;
	}

	@RequestMapping(value = "/process/{processID}", method = RequestMethod.GET)
    public Process getProcess(@PathVariable int processID) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	Process result = dash.getProcess(processID);
    	dash.close();
    	return result;
	}
	
	@RequestMapping(value = "/processAdd", method = RequestMethod.POST)
	@ResponseBody
    public boolean addProcess(@RequestBody Process input) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.addProcess(input);
    	dash.close();
    	return result;
	}

	@RequestMapping(value = "/processInstanceAdd", method = RequestMethod.POST)
	@ResponseBody
    public boolean addProcessInstance(@RequestBody ProcessInstance input) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.addProcessInstance(input);
    	dash.close();
    	return result;
	}

	@RequestMapping(value = "/processDelete/{processID}", method = RequestMethod.DELETE)
    public boolean deleteProcess(@PathVariable int processID) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.deleteProcess(processID);
    	dash.close();
    	return result;
    }
}