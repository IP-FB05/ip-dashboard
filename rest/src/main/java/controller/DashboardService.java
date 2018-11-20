package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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


	@RequestMapping(value = "/dokumente", method = RequestMethod.GET)
    public Dokument[] getDokumente() throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	Dokument[] result = dash.getDokumente();
    	dash.close();
    	return result;
	}
	
	@RequestMapping(value = "/dokumentAdd", method = RequestMethod.POST)
	@ResponseBody
    public boolean addDokument(@RequestBody Dokument input) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.addDokument(input);
    	dash.close();
    	return result;
	}

	@RequestMapping(value = "/dokumentDelete/{dokumentID}", method = RequestMethod.DELETE)
    public boolean deleteDokument(@PathVariable int dokumentID) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.deleteDokument(dokumentID);
    	dash.close();
    	return result;
	}
	
	@RequestMapping(value = "/prozesse", method = RequestMethod.GET)
    public Prozess[] getProzesse() throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	Prozess[] result = dash.getProzesse();
    	dash.close();
    	return result;
	}

	@RequestMapping(value = "/prozess/{prozessID}", method = RequestMethod.GET)
    public Prozess getProzess(@PathVariable int prozessID) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	Prozess result = dash.getProzess(prozessID);
    	dash.close();
    	return result;
	}
	
	@RequestMapping(value = "/prozessAdd", method = RequestMethod.POST)
	@ResponseBody
    public boolean addProzess(@RequestBody Prozess input) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.addProzess(input);
    	dash.close();
    	return result;
	}

	@RequestMapping(value = "/prozessDelete/{prozessID}", method = RequestMethod.DELETE)
    public boolean deleteProzess(@PathVariable int prozessID) throws SQLException, ClassNotFoundException {
    	Dashboard dash = new Dashboard(); 
    	boolean result = dash.deleteProzess(prozessID);
    	dash.close();
    	return result;
    }
}