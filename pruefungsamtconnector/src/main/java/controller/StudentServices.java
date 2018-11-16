package controller;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import database.Pruefungsamt;

@RestController
public class StudentServices {

	@RequestMapping("/check")
	public String greeting() {
		return "ok";
	}

	@RequestMapping("/credits/{matrikelnr}")
	public int getCredits(@PathVariable int matrikelnr) throws ClassNotFoundException, SQLException {
		Pruefungsamt amt = new Pruefungsamt();
		int result = amt.getCredits(matrikelnr);
		amt.close();
		return result;
	}
	
	@RequestMapping("/praktikumBestanden/{matrikelnr}/{fachnr}")
	public boolean fachBestanden(@PathVariable int matrikelnr, @PathVariable int fachnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.praktikumBestanden(matrikelnr, fachnr);
		amt.close();
		return result;
	}
	
	@RequestMapping("/neuesPraxisSemester/{matrikelnr}")
	public boolean setNewPraxisSemester(@PathVariable int matrikelnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setNewPraxisSemester(matrikelnr);
		amt.close();
		return result;
	}
	
	@RequestMapping("/noteLetztesPraxisSemester/{matrikelnr}/{boolBestanden}")
	public boolean setNotePraxisSemester(@PathVariable int matrikelnr, @PathVariable int boolBestanden) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setNotePraxisSemester(matrikelnr,boolBestanden);
		amt.close();
		return result;
	}
	
	//boolAnmeldung 1 = anmeldung (erzeugen); 0 = abmeldung (loeschen)
	@RequestMapping("/modulAnAbmeldung/{matrikelnr}/{fachnr}/{boolAnmeldung}")
	public boolean changeModulStudent(@PathVariable int matrikelnr, @PathVariable int fachnr, @PathVariable int boolAnmeldung) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.changeModulregister(matrikelnr,fachnr,boolAnmeldung);
		amt.close();
		return result;
	}
	
	@RequestMapping("/modulStudent/{matrikelnr}")
	public boolean changeModulStudent(@PathVariable int matrikelnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.getModulStudent(matrikelnr);
		amt.close();
		return result;
	}
	
	@RequestMapping("/pruefungAnmelden/{matrikelnr}/{fachnr}")
	public boolean pruefungAnmelden(@PathVariable int matrikelnr, @PathVariable int fachnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.pruefungAnmelden(matrikelnr, fachnr);
		amt.close();
		return result;
	}

	@RequestMapping("/pruefungAbmelden/{matrikelnr}/{fachnr}")
	public boolean pruefungAbmelden(@PathVariable int matrikelnr, @PathVariable int fachnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.pruefungAbmelden(matrikelnr, fachnr);
		amt.close();
		return result;
	}

	@RequestMapping("/pruefungAnmeldenVoraussetzungen/{matrikelnr}/{fachnr}")
	public boolean pruefungAnmeldenVoraussetzungen(@PathVariable int matrikelnr, @PathVariable int fachnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.pruefungAnmeldenVoraussetzungen(matrikelnr, fachnr);
		amt.close();
		return result;
	}

	@RequestMapping("/pruefungAbmeldenVoraussetzungen/{matrikelnr}/{fachnr}")
	public boolean pruefungAbmeldenVoraussetzungen(@PathVariable int matrikelnr, @PathVariable int fachnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.pruefungAbmeldenVoraussetzungen(matrikelnr, fachnr);
		amt.close();
		return result;
	}
	
}