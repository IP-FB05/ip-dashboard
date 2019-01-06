package controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.System;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import controller.Usergroup;
import controller.UsergroupRepository;
import database.Dashboard;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/usergroups") 
public class UsergroupController {
	@Autowired
	private UsergroupRepository usergroupRepository;

	/*
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Usergroup> getAllUsergroups() {
		return usergroupRepository.findAll();
	}
	*/

	@GetMapping(path = "/detail")
	public @ResponseBody Usergroup getSub(@RequestParam int id) {
		return usergroupRepository.findById(id).get();
	}


	@GetMapping(path = "/delete")
	public @ResponseBody String deleteUsergroup(@RequestParam int id) {
		if (usergroupRepository.existsById(id)) {
			usergroupRepository.deleteById(id);
			return "Usergroup deleted.";
		} else
			return "Usergroup not found...";
	}

	
	@GetMapping(path = "/all")
	public @ResponseBody Usergroup[] getUsergroups() throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard(); 
		Usergroup[] result = dash.getUsergroups();
    	dash.close();
    	return result;
	}
	



}