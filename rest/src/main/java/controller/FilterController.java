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
import org.springframework.web.bind.annotation.GetMapping;


import database.Dashboard;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/filter")
public class FilterController {
	
    @RequestMapping(value = "/filterSystems", method = RequestMethod.GET)
    public System[] getSystems() throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	System[] result = dash.getSystems();
    	dash.close();
    	return result;
	}
	
	@GetMapping(path = "/documents")
	public @ResponseBody Document[] getDocuments (@RequestParam String name) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	Document[] result = dash.getFilteredDocuments(name);
    	dash.close();
    	return result;
	}
	
	@RequestMapping(value = "/filterProcesses", method = RequestMethod.GET)
    public Process[] getProcesses() throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	Process[] result = dash.getProcesses();
    	dash.close();
    	return result;
	}
}