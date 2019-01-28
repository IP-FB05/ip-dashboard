package de.fhaachen.ipdashboard.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.fhaachen.ipdashboard.model.Subs;
import de.fhaachen.ipdashboard.repository.SubsRepository;
import de.fhaachen.ipdashboard.model.Subscription;
import de.fhaachen.ipdashboard.model.Process;
import de.fhaachen.ipdashboard.database.Dashboard;

@RestController
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

	@GetMapping(path = "/mysubscribedProcesses")
	public @ResponseBody Process[] getMySubscribedProcesses(@RequestParam String user) throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard(); 
		Process[] result = dash.getMySubscribedProcesses(user);
    	dash.close();
    	return result;
	}

	@GetMapping(path = "/mysubscribedProcessInstances")
	public @ResponseBody Process[] getMySubscribedProcessInstances(@RequestParam String user) throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard(); 
		Process[] result = dash.getMySubscribedProcessInstances(user);
    	dash.close();
    	return result;
	}

	@GetMapping(path = "/runningProcesses")
	public @ResponseBody Process[] getRunningProcesses() throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard(); 
		Process[] result = dash.getRunningProcesses();
    	dash.close();
    	return result;
	}

	@PostMapping(path = "/addSub")
	@ResponseBody
	public boolean addSubscribedProcess(@RequestBody Subscription subscription) throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard(); 
		boolean result = dash.addSubscribedProcess(subscription.getProcessID(),subscription.getUsername());
    	dash.close();
    	return result;
	}

	
	@PostMapping(path = "/addRunningSub")
	@ResponseBody
	public boolean addSubscribedRunningProcess(@RequestBody Subscription subscription) throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard(); 
		boolean result = dash.addSubscribedRunningProcess(subscription.getProcessID(), subscription.getUsername());
    	dash.close();
    	return result;
	}
	
	@DeleteMapping(path = "deleteSubscribedProcess/{processId}")
	@ResponseBody
	public boolean deleteSubscribedProcess(@PathVariable int processId, @RequestParam String username) throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard();
		boolean result = dash.deleteSubscribedProcess(processId, username);
		dash.close();
		return result;
	}

	@DeleteMapping(path = "deleteSubscribedRunningProcess/{processId}")
	@ResponseBody
	public boolean deleteSubscribedRunningProcess(@PathVariable int processId, @RequestParam String username) throws SQLException, ClassNotFoundException {
		Dashboard dash = new Dashboard();
		boolean result = dash.deleteSubscribedRunningProcess(processId, username);
		dash.close();
		return result;
	}

}