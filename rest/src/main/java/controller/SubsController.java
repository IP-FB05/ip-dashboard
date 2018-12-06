package controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import controller.Subs;
import controller.SubsRepository;
import controller.Process;
import database.Dashboard;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "/subs") 
public class SubsController {
	@Autowired
	private SubsRepository subsRepository;

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Subs> getAllSubs() {
		return subsRepository.findAll();
	}

	@GetMapping(path = "/detail")
	public @ResponseBody Subs getSub(@RequestParam int id) {
		return subsRepository.findById(id).get();
	}

	@PostMapping(path = "/add")
	public @ResponseBody String addNewSub(@RequestBody Subs subs) {
		subsRepository.save(subs);
		return "Saved";
	}

	@GetMapping(path = "/delete")
	public @ResponseBody String deleteSub(@RequestParam int id) {
		if (subsRepository.existsById(id)) {
			subsRepository.deleteById(id);
			return "Sub deleted.";
		} else
			return "Sub not found...";
	}

	@GetMapping(path = "/mysubs")
	public @ResponseBody Process[] getMySubbedProcesses(@RequestParam String user) throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard(); 
    	Process[] result = dash.getMySubbedProcesses(user);
    	dash.close();
    	return result;
	}


}