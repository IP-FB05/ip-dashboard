package de.fhaachen.ipdashboard.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fhaachen.ipdashboard.database.Dashboard;
import de.fhaachen.ipdashboard.model.System;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/system")
public class SystemController {

	// GET
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public System[] getSystems() throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	System[] result = dash.getSystems();
    	dash.close();
    	return result;
	}
	
	// ADD
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
    public boolean addSystem(@RequestBody System input) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.addSystem(input);
    	dash.close();
    	return result;
	}

	// DELETE
	@RequestMapping(value = "/delete/{systemID}", method = RequestMethod.DELETE)
    public boolean deleteSystem(@PathVariable int systemID) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.deleteSystem(systemID);
    	dash.close();
    	return result;
	}
}