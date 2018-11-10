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
	
	@RequestMapping("/praktikumBestanden/{matrikelnr}")
	public boolean setNewPraxisSemester(@PathVariable int matrikelnr) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setNewPraxisSemester(matrikelnr);
		amt.close();
		return result;
	}
	
	@RequestMapping("/praktikumBestanden/{matrikelnr}/{boolBestanden}")
	public boolean setNotePraxisSemester(@PathVariable int matrikelnr, @PathVariable int boolBestanden) throws SQLException, ClassNotFoundException{
		Pruefungsamt amt = new Pruefungsamt();
		boolean result = amt.setNotePraxisSemester(matrikelnr,boolBestanden);
		amt.close();
		return result;
	}
	
}