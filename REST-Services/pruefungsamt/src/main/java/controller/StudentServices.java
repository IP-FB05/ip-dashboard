package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import database.Pruefungsamt;
import database.RegisteredModulesModel;
import database.RegisteredPruefungModel;

@RestController
public class StudentServices {

	@RequestMapping("/check")
	public String greeting() {
		return "ok";
	}
	
	/**
	 * /student/{matrikelnr}
	 * 
	 * - /credits																		GET
	 * - /praktikum/{fachnr}?get=passed													GET
	 * - /modul																			GET
	 */
	
	
	// used in: Modulanmeldung, Praxissemester
	@RequestMapping(value = "/student/{matrikelnr}/credits", method = RequestMethod.GET)
	public int getCredits(@PathVariable int matrikelnr) throws ClassNotFoundException, SQLException {
		Pruefungsamt amt = new Pruefungsamt();
		int result = amt.getCredits(matrikelnr);
		amt.close();
		return result;
	}
	
	// used in: Pruefungsanmeldung
	@RequestMapping(value = "/student/{matrikelnr}/praktikum/{fachnr}", method = RequestMethod.GET)
	public boolean fachBestanden(@PathVariable int matrikelnr, @PathVariable int fachnr, @RequestParam("get") String get)
			throws SQLException, ClassNotFoundException{		
		boolean result = false;
		if (get.equals("passed")) {
			Pruefungsamt amt = new Pruefungsamt();
			result = amt.praktikumBestanden(matrikelnr, fachnr);
			amt.close();
		}		
		return result;
	}
	
	// used in: Modulabmeldung
	@RequestMapping(value = "/student/{matrikelnr}/modul", method = RequestMethod.GET)
	public List<RegisteredModulesModel> changeModulStudent(@PathVariable int matrikelnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		List<RegisteredModulesModel> result = amt.getModulStudent(matrikelnr);
		amt.close();
		return result;
	}
	
	/**
	 * /modul
	 * 
	 * -																				GET
	 * - /{fachnr}?get=semester&studiengang={studiengang}								GET
	 * - /{fachnr}/student/{matrikelnr} 												POST
	 * - /{fachnr}/student/{matrikelnr}													DELETE
	 */
	
	// used in: Modulanmeldung, Pruefungsdurchfuehrung
	@RequestMapping(value = "/modul", method = RequestMethod.GET)
	public List<RegisteredModulesModel> getModulList() throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		List<RegisteredModulesModel> result = amt.getModulList();
		amt.close();
		return result;
	}
	
	// used in: Modulanmeldung
	@RequestMapping(value = "/modul/{fachnr}", method = RequestMethod.GET)
	public int getmodulFachsemester(@PathVariable int fachnr, 
			@RequestParam("get") String get, @RequestParam("studiengang") int studiengang) throws SQLException, ClassNotFoundException{
		int result = -1;
		if(get.equals("semester")) {
			Pruefungsamt amt = new Pruefungsamt();
			result = amt.getModulSemester(fachnr,studiengang);
			amt.close();
		}
		return result;
	}
	
	// used in: Modulanmeldung
	@RequestMapping(value = "/modul/{fachnr}/student/{matrikelnr}", method = RequestMethod.POST)
	public boolean registerModulStudent(@PathVariable int matrikelnr, @PathVariable int fachnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.registerModul(matrikelnr,fachnr);
		amt.close();
		return result;
	}
	
	//used in: Modulabmeldung
	@RequestMapping(value = "/modul/{fachnr}/student/{matrikelnr}", method = RequestMethod.DELETE)
	public boolean changeModulStudent(@PathVariable int matrikelnr, @PathVariable int fachnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.deregisterModulregister(matrikelnr,fachnr);
		amt.close();
		return result;
	}

	
	/**
	 * /pruefung/{fachnr}/student
	 * 
	 * - 																				GET
	 * - /{matrikelnr}?allowed=register/deregister 										GET
	 * - /{matrikelnr} 																	POST
	 * - /{matrikelnr}?note={note} 														PUT
	 * - /{matrikelnr}																	DELET
	 * 
	 * /pruefung/praxissemester/student/{matrikelnr}
	 * 
	 * - 																				POST
	 * - ?bestanden=0/1 																PATCH
	 */
	

	//fachnr = modulnr; used in: Pruefungsdurchfuehrung
	@RequestMapping(value = "/pruefung/{fachnr}/student", method = RequestMethod.GET)
	public List<RegisteredPruefungModel> getPruefungStudentList(@PathVariable int fachnr) throws SQLException, ClassNotFoundException {
		Pruefungsamt amt = new Pruefungsamt();
		List<RegisteredPruefungModel> result = amt.getPruefungStudentList(fachnr);
		amt.close();
		return result;
	}

	// used in: Pruefungsanmeldung, Pruefungsabmeldung
	@RequestMapping(value = "/pruefung/{fachnr}/student/{matrikelnr}", method = RequestMethod.GET)
	public boolean pruefungAnmeldenVoraussetzungen(@PathVariable int fachnr, @PathVariable int matrikelnr, 
			@RequestParam("allowed") String allowed) throws SQLException, ClassNotFoundException{
		boolean result = false;
		Pruefungsamt amt = new Pruefungsamt();
		if(allowed.equals("register")) {
			result = amt.pruefungAnmeldenVoraussetzungen(matrikelnr, fachnr);
		}
		else if (allowed.equals("deregister")) {
			result = amt.pruefungAbmeldenVoraussetzungen(matrikelnr, fachnr);
		}
		amt.close();
		return result;
	}
	
	// used in: Pruefungsanmeldung
	@RequestMapping(value = "/pruefung/{fachnr}/student/{matrikelnr}", method = RequestMethod.POST)
	public boolean pruefungAnmelden(@PathVariable int fachnr, @PathVariable int matrikelnr)
			throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.pruefungAnmelden(matrikelnr, fachnr);
		amt.close();
		return result;
	}
	
	// used in: Pruefungsdurchfuehrung
	@RequestMapping(value = "/pruefung/{fachnr}/student/{matrikelnr}", method = RequestMethod.PUT)
	public boolean pruefungBenotung(@PathVariable int fachnr, @PathVariable int matrikelnr,  
			@RequestParam("note") double note) throws SQLException, ClassNotFoundException {
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.pruefungBenotung(matrikelnr, fachnr, note);
		amt.close();
		return result;
	}
		
	// used in: Pruefungsabmeldung
	@RequestMapping(value = "/pruefung/{fachnr}/student/{matrikelnr}", method = RequestMethod.DELETE)
	public boolean pruefungAbmelden(@PathVariable int fachnr, @PathVariable int matrikelnr)
			throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.pruefungAbmelden(matrikelnr, fachnr);
		amt.close();
		return result;
	}

	// used in: Praxissemester
	@RequestMapping(value = "/pruefung/praxissemester/student/{matrikelnr}", method = RequestMethod.POST)
	public boolean setNewPraxisSemester(@PathVariable int matrikelnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setNewPraxisSemester(matrikelnr);
		amt.close();
		return result;
	}
	
	// used in: Praxissemester
	@RequestMapping(value = "/pruefung/praxissemester/student/{matrikelnr}", method = RequestMethod.PATCH)
	public boolean setNotePraxisSemester(@PathVariable int matrikelnr, @RequestParam("bestanden") int bestanden) 
			throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setNotePraxisSemester(matrikelnr,bestanden);
		amt.close();
		return result;
	}
	
	/**
	 * /pruefung/bachelorarbeit/student/{matrikelnr}
	 * 
	 * - ?get=allowed																	GET
	 * - ?betreuer={betreuer}&name-ba={nameBA}&startdatum={startdatum} 					POST
	 * - ?note={note} 																	PUT
	 * - ?verlaengerungsdatum={verlaengerungsdatum} 									PATCH
	 * 
	 * /pruefung/bachelorarbeit-kolloquium/student/{matrikelnr}
	 * 
	 * - ?get=allowed																	GET
	 * - ?startdatum={startdatum}														POST
	 * - ?note={note}																	PUT
	 */
	
	// used in: Bachelorarbeit
	@RequestMapping(value = "/pruefung/bachelorarbeit/student/{matrikelnr}", method = RequestMethod.GET)
	public boolean istZugelassenBA(@PathVariable int matrikelnr, @RequestParam("get") String get)
			throws SQLException, ClassNotFoundException {
		boolean result = false;
		if(get.equals("allowed")) {
			Pruefungsamt amt = new Pruefungsamt();
			result = amt.getZulassungBA(matrikelnr);
			amt.close();
		}
		return result;
	}
	
	// used in: Bachelorarbeit
	@RequestMapping(value = "/pruefung/bachelorarbeit/student/{matrikelnr}", method = RequestMethod.POST)
	public boolean anmeldungBA(@PathVariable int matrikelnr, @RequestParam("betreuer") String betreuer,
			@RequestParam("name-ba") String nameBA, @RequestParam("startdatum") Date startdatum) throws SQLException, ClassNotFoundException {
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setAnmeldungBA(matrikelnr,betreuer,nameBA,startdatum);
		amt.close();
		return result;
	}
	
	// used in: Bachelorarbeit
	@RequestMapping(value = "/pruefung/bachelorarbeit/student/{matrikelnr}", method = RequestMethod.PUT)
	public boolean bachelorarbeitBenotung(@PathVariable int matrikelnr, @RequestParam("note") double note) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setBABenoten(matrikelnr, note);
		amt.close();
		return result;
	}
	
	// used in: Bachelorarbeit
	@RequestMapping(value = "/pruefung/bachelorarbeit/student/{matrikelnr}", method = RequestMethod.PATCH)
	public boolean bachelorarbeitVerlaengern(@PathVariable int matrikelnr, @RequestParam("verlaengerungsdatum") Date verlaengerungsdatum) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setBAVerlaengerung(matrikelnr, verlaengerungsdatum);
		amt.close();
		return result;
	}
	
	// used in: Bachelorarbeit
	@RequestMapping(value = "/pruefung/bachelorarbeit-kolloquium/student/{matrikelnr}", method = RequestMethod.GET)
	public boolean istZugelassenBAKol(@PathVariable int matrikelnr, @RequestParam("get") String get)
			throws SQLException, ClassNotFoundException {
		boolean result = false;
		if(get.equals("allowed")) {
			Pruefungsamt amt = new Pruefungsamt();
			result = amt.getZulassungBAKol(matrikelnr);
			amt.close();
		}
		return result;
	}

	// used in: Bachelorarbeit
	@RequestMapping(value = "/pruefung/bachelorarbeit-kolloquium/student/{matrikelnr}", method = RequestMethod.POST)
	public boolean anmeldungBAKol(@PathVariable int matrikelnr, @RequestParam("startdatum") Date startdatum)
			throws SQLException, ClassNotFoundException {
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setAnmeldungBAKol(matrikelnr, startdatum);
		amt.close();
		return result;
	}

	// used in: Bachelorarbeit
	@RequestMapping(value = "/pruefung/bachelorarbeit-kolloquium/student/{matrikelnr}", method = RequestMethod.PUT)
	public boolean bachelorarbeitKolBenotung(@PathVariable int matrikelnr, @RequestParam("note") double note) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setBAKolBenoten(matrikelnr, note);
		amt.close();
		return result;
	}
	
	
	/**
	 * /pruefung/masterarbeit/student/{matrikelnr}
	 * 
	 * - ?get=allowed																	GET
	 * - ?betreuer={betreuer}&name-ma={nameMA}&startdatum={startdatum} 					POST
	 * - ?note={note} 																	PUT
	 * - ?verlaengerungsdatum={verlaengerungsdatum} 									PATCH
	 * 
	 * /pruefung/masterarbeit-kolloquium/student/{matrikelnr}
	 * 
	 * - ?get=allowed																	GET
	 * - ?startdatum={startdatum}														POST
	 * - ?note={note}																	PUT
	 */

	// used in: Masterrarbeit
	@RequestMapping(value = "/pruefung/masterarbeit/student/{matrikelnr}", method = RequestMethod.GET)
	public boolean istZugelassenMA(@PathVariable int matrikelnr, @RequestParam("get") String get) throws SQLException, ClassNotFoundException{		
		boolean result = false;
		if(get.equals("allowed")) {
			Pruefungsamt amt = new Pruefungsamt();
			result = amt.getZulassungMA(matrikelnr);
			amt.close();
		}
		return result;
	}
	
	// used in: Masterrarbeit
	@RequestMapping(value = "/pruefung/masterarbeit/student/{matrikelnr}", method = RequestMethod.POST)
	public boolean anmeldungMA(@PathVariable int matrikelnr, @RequestParam("betreuer") String betreuer,
			@RequestParam("name-ma") String nameMA, @RequestParam("startdatum") Date startdatum) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setAnmeldungMA(matrikelnr,betreuer,nameMA,startdatum);
		amt.close();
		return result;
	}
	
	// used in: Masterrarbeit
	@RequestMapping(value = "/pruefung/masterarbeit/student/{matrikelnr}", method = RequestMethod.PUT)
	public boolean masterarbeitBenotung(@PathVariable int matrikelnr, @RequestParam("note") double note) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setMABenoten(matrikelnr, note);
		amt.close();
		return result;
	}
	
	// used in: Masterrarbeit
	@RequestMapping(value = "/pruefung/masterarbeit/student/{matrikelnr}", method = RequestMethod.PATCH)
	public boolean masterarbeitVerlaengern(@PathVariable int matrikelnr, @RequestParam("verlaengerungsdatum") Date verlaengerungsdatum) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setMAVerlaengerung(matrikelnr, verlaengerungsdatum);
		amt.close();
		return result;
	}
	
	// used in: Masterrarbeit
	@RequestMapping(value = "/pruefung/masterarbeit-kolloquium/student/{matrikelnr}", method = RequestMethod.GET)
	public boolean istZugelassenMAKol(@PathVariable int matrikelnr, @RequestParam("get") String get) throws SQLException, ClassNotFoundException{
		boolean result = false;
		if(get.equals("allowed")) {
			Pruefungsamt amt = new Pruefungsamt();
			result = amt.getZulassungMAKol(matrikelnr);
			amt.close();
		}
		return result;
	}

	// used in: Masterrarbeit
	@RequestMapping(value = "/pruefung/masterarbeit-kolloquium/student/{matrikelnr}", method = RequestMethod.POST)
	public boolean anmeldungMAKol(@PathVariable int matrikelnr, @RequestParam("startdatum") Date startdatum) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setAnmeldungMAKol(matrikelnr, startdatum);
		amt.close();
		return result;
	}

	// used in: Masterrarbeit
	@RequestMapping(value = "/pruefung/masterarbeit-kolloquium/student/{matrikelnr}", method = RequestMethod.PUT)
	public boolean masterarbeitKolBenotung(@PathVariable int matrikelnr, @RequestParam("note") double note) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setMAKolBenoten(matrikelnr, note);
		amt.close();
		return result;
	}

}