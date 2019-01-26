package de.fhaachen.ipdashboard.controller;

import java.sql.SQLException;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


import de.fhaachen.ipdashboard.database.Dashboard;
import de.fhaachen.ipdashboard.model.Document;



@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/") // This means URL's start with /demo (after Application path)
public class DocumentController {
    
     // GET
	@RequestMapping(value = "/documents", method = RequestMethod.GET)
    public Document[] getDocuments() throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	Document[] result = dash.getDocuments();
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
    
	// GET DocumentsLimit
	@RequestMapping(value = "/documentsLimit", method = RequestMethod.GET)
    public Document[] getDocumentsLimit() throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	Document[] result = dash.getDocumentsLimit();
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
}


