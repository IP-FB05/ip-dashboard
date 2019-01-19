package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	// used in: Modulanmeldung, Praxissemester
	@RequestMapping(value = "/student/credits/{matrikelnr}", method = RequestMethod.GET)
	public int getCredits(@PathVariable int matrikelnr) throws ClassNotFoundException, SQLException {
		Pruefungsamt amt = new Pruefungsamt();
		int result = amt.getCredits(matrikelnr);
		amt.close();
		return result;
	}
	
	// used in: Pruefungsanmeldung
	@RequestMapping(value = "/student/praktikum/passed/{matrikelnr}/{fachnr}", method = RequestMethod.GET)
	public boolean fachBestanden(@PathVariable int matrikelnr, @PathVariable int fachnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.praktikumBestanden(matrikelnr, fachnr);
		amt.close();
		return result;
	}
	
	// used in: Praxissemester
	@RequestMapping(value = "/pruefung/praxissemester/{matrikelnr}", method = RequestMethod.POST)
	public boolean setNewPraxisSemester(@PathVariable int matrikelnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setNewPraxisSemester(matrikelnr);
		amt.close();
		return result;
	}
	
	// used in: Praxissemester
	@RequestMapping(value = "/pruefung/praxissemester/{matrikelnr}/{boolBestanden}", method = RequestMethod.PATCH)
	public boolean setNotePraxisSemester(@PathVariable int matrikelnr, @PathVariable int boolBestanden) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setNotePraxisSemester(matrikelnr,boolBestanden);
		amt.close();
		return result;
	}
	
	// used in: Modulanmeldung, Pruefungsdurchfuehrung
	@RequestMapping(value = "/modulList", method = RequestMethod.GET)
	public List<RegisteredModulesModel> getModulList() throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		List<RegisteredModulesModel> result = amt.getModulList();
		amt.close();
		return result;
	}
	
	//boolAnmeldung 1 = anmeldung (create); 0 = abmeldung (delete) // used in: Modulanmeldung,Modulabmeldung
	@RequestMapping(value = "/modul/{matrikelnr}/{fachnr}/{boolAnmeldung}", method = RequestMethod.POST)
	public boolean changeModulStudent(@PathVariable int matrikelnr, @PathVariable int fachnr, @PathVariable int boolAnmeldung) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.changeModulregister(matrikelnr,fachnr,boolAnmeldung);
		amt.close();
		return result;
	}
	
	// used in: Modulabmeldung
	@RequestMapping(value = "/student/modul/{matrikelnr}", method = RequestMethod.GET)
	public List<RegisteredModulesModel> changeModulStudent(@PathVariable int matrikelnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		List<RegisteredModulesModel> result = amt.getModulStudent(matrikelnr);
		amt.close();
		return result;
	}
	
	// used in: Modulanmeldung
	@RequestMapping(value = "/modul/semester/{fachnr}/{studiengang}", method = RequestMethod.GET)
	public int getmodulFachsemester(@PathVariable int fachnr, @PathVariable int studiengang) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		int result = amt.getModulSemester(fachnr,studiengang);
		amt.close();
		return result;
	}
	
	// used in: Pruefungsanmeldung
	@RequestMapping(value = "/pruefung/{matrikelnr}/{fachnr}", method = RequestMethod.POST)
	public boolean pruefungAnmelden(@PathVariable int matrikelnr, @PathVariable int fachnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.pruefungAnmelden(matrikelnr, fachnr);
		amt.close();
		return result;
	}
		
	// used in: Pruefungsabmeldung
	@RequestMapping(value = "/pruefung/{matrikelnr}/{fachnr}", method = RequestMethod.DELETE)
	public boolean pruefungAbmelden(@PathVariable int matrikelnr, @PathVariable int fachnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.pruefungAbmelden(matrikelnr, fachnr);
		amt.close();
		return result;
	}
	
	// used in: Pruefungsanmeldung
	@RequestMapping(value = "/pruefung/allowedRegister/{matrikelnr}/{fachnr}", method = RequestMethod.GET)
	public boolean pruefungAnmeldenVoraussetzungen(@PathVariable int matrikelnr, @PathVariable int fachnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.pruefungAnmeldenVoraussetzungen(matrikelnr, fachnr);
		amt.close();
		return result;
	}
	
	// used in: Pruefungsabmeldung
	@RequestMapping(value = "/pruefung/allowedDeregister/{matrikelnr}/{fachnr}", method = RequestMethod.GET)
	public boolean pruefungAbmeldenVoraussetzungen(@PathVariable int matrikelnr, @PathVariable int fachnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.pruefungAbmeldenVoraussetzungen(matrikelnr, fachnr);
		amt.close();
		return result;
	}
	
	// used in: Pruefungsdurchfuehrung
	@RequestMapping(value = "/pruefung/student/{matrikelnr}/{fachnr}/{note}", method = RequestMethod.PUT)
	public boolean pruefungBenotung(@PathVariable int matrikelnr, @PathVariable int fachnr, @PathVariable double note) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.pruefungBenotung(matrikelnr, fachnr, note);
		amt.close();
		return result;
	}
	
	//fachnr = modulnr; used in: Pruefungsdurchfuehrung
	@RequestMapping(value = "/pruefung/studentList/{fachnr}", method = RequestMethod.GET)
	public List<RegisteredPruefungModel> getPruefungStudentList(@PathVariable int fachnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		List<RegisteredPruefungModel> result = amt.getPruefungStudentList(fachnr);
		amt.close();
		return result;
	}
	
	// used in: Bachelorarbeit
	@RequestMapping(value = "/pruefung/ba/allowed/{matrikelnr}", method = RequestMethod.GET)
	public boolean istZugelassenBA(@PathVariable int matrikelnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.getZulassungBA(matrikelnr);
		amt.close();
		return result;
	}
	
	// used in: Bachelorarbeit
	@RequestMapping(value = "/pruefung/bakol/allowed/{matrikelnr}", method = RequestMethod.GET)
	public boolean istZugelassenBAKol(@PathVariable int matrikelnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.getZulassungBAKol(matrikelnr);
		amt.close();
		return result;
	}
	
	// used in: Bachelorarbeit
	@RequestMapping(value = "/pruefung/ba/{matrikelnr}/{betreuer}/{nameBA}/{startdatum}", method = RequestMethod.POST)
	public boolean anmeldungBA(@PathVariable int matrikelnr, @PathVariable String betreuer, @PathVariable String nameBA, @PathVariable Date startdatum) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setAnmeldungBA(matrikelnr,betreuer,nameBA,startdatum);
		amt.close();
		return result;
	}
	
	// used in: Bachelorarbeit
	@RequestMapping(value = "/pruefung/bakol/{matrikelnr}/{startdatum}", method = RequestMethod.POST)
	public boolean anmeldungBAKol(@PathVariable int matrikelnr, @PathVariable Date startdatum) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setAnmeldungBAKol(matrikelnr, startdatum);
		amt.close();
		return result;
	}
	
	// used in: Bachelorarbeit
	@RequestMapping(value = "/pruefung/ba/{matrikelnr}/{note}", method = RequestMethod.PUT)
	public boolean bachelorarbeitBenotung(@PathVariable int matrikelnr, @PathVariable double note) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setBABenoten(matrikelnr, note);
		amt.close();
		return result;
	}
	
	// used in: Bachelorarbeit
	@RequestMapping(value = "/pruefung/bakol/{matrikelnr}/{note}", method = RequestMethod.PUT)
	public boolean bachelorarbeitKolBenotung(@PathVariable int matrikelnr, @PathVariable double note) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setBAKolBenoten(matrikelnr, note);
		amt.close();
		return result;
	}
	
	// used in: Bachelorarbeit
	@RequestMapping(value = "/pruefung/ba/{matrikelnr}/{verlaengerungDate}", method = RequestMethod.PATCH)
	public boolean bachelorarbeitVerlaengern(@PathVariable int matrikelnr, @PathVariable Date verlaengerungDate) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setBAVerlaengerung(matrikelnr, verlaengerungDate);
		amt.close();
		return result;
	}
	
	// used in: Masterrarbeit
	@RequestMapping(value = "/pruefung/ma/allowed/{matrikelnr}", method = RequestMethod.GET)
	public boolean istZugelassenMA(@PathVariable int matrikelnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.getZulassungMA(matrikelnr);
		amt.close();
		return result;
	}
	
	// used in: Masterrarbeit
	@RequestMapping(value = "/pruefung/makol/allowed/{matrikelnr}", method = RequestMethod.GET)
	public boolean istZugelassenMAKol(@PathVariable int matrikelnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.getZulassungMAKol(matrikelnr);
		amt.close();
		return result;
	}
	
	// used in: Masterrarbeit
	@RequestMapping(value = "/pruefung/ma/{matrikelnr}/{betreuer}/{nameMA}/{startdatum}", method = RequestMethod.POST)
	public boolean anmeldungMA(@PathVariable int matrikelnr, @PathVariable String betreuer, @PathVariable String nameMA, @PathVariable Date startdatum) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setAnmeldungMA(matrikelnr,betreuer,nameMA,startdatum);
		amt.close();
		return result;
	}
	
	// used in: Masterrarbeit
	@RequestMapping(value = "/pruefung/makol/{matrikelnr}/{startdatum}", method = RequestMethod.POST)
	public boolean anmeldungMAKol(@PathVariable int matrikelnr, @PathVariable Date startdatum) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setAnmeldungMAKol(matrikelnr, startdatum);
		amt.close();
		return result;
	}
	
	// used in: Masterrarbeit
	@RequestMapping(value = "/pruefung/ma/{matrikelnr}/{note}", method = RequestMethod.PUT)
	public boolean masterarbeitBenotung(@PathVariable int matrikelnr, @PathVariable double note) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setMABenoten(matrikelnr, note);
		amt.close();
		return result;
	}
	
	// used in: Masterrarbeit
	@RequestMapping(value = "/pruefung/makol/{matrikelnr}/{note}", method = RequestMethod.PUT)
	public boolean masterarbeitKolBenotung(@PathVariable int matrikelnr, @PathVariable double note) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setMAKolBenoten(matrikelnr, note);
		amt.close();
		return result;
	}
	
	// used in: Masterrarbeit
	@RequestMapping(value = "/pruefung/ma/{matrikelnr}/{verlaengerungDate}", method = RequestMethod.PATCH)
	public boolean masterarbeitVerlaengern(@PathVariable int matrikelnr, @PathVariable Date verlaengerungDate) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setMAVerlaengerung(matrikelnr, verlaengerungDate);
		amt.close();
		return result;
	}
	
	
}