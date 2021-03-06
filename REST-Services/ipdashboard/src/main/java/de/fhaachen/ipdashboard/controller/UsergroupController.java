package de.fhaachen.ipdashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import de.fhaachen.ipdashboard.model.Usergroup;
import de.fhaachen.ipdashboard.repository.UsergroupRepository;


@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/usergroups") // This means URL's start with /demo (after Application path)
public class UsergroupController {
	@Autowired // This means to get the bean called UsergroupRepository
				// Which is auto-generated by Spring, we will use it to handle the data
	private UsergroupRepository usergroupRepository;

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Usergroup> getAllUsergroups() {
		// This returns a JSON or XML with the usergroups
		return usergroupRepository.findAll();
	}

}