package database;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestPruefungsamt {
	Pruefungsamt pa;

	@Before
	public void setup() throws ClassNotFoundException, SQLException {
		pa = new Pruefungsamt();
	}

	@Test
	public void testFachHasPraktikum() throws SQLException {
		// Mathe hat kein Praktikum
		assertFalse(pa.fachHasPraktikum(51101));
		// Fachnr 0 existiert nicht
		assertFalse(pa.fachHasPraktikum(0));
		// GIP hat Praktikum
		assertTrue(pa.fachHasPraktikum(51104));
	}

	@Test
	@Ignore
	public void testgetCredits() throws SQLException {
		// Student 2 hat 27 Credits
		assertEquals(171, pa.getCredits(2));
		// Student 1 hat keine Credits
		assertEquals(139, pa.getCredits(1));
	}

	@Test
	public void praktikumBestanden() throws SQLException {
		// Student 1 hat GIP nicht bestanden
		assertTrue(pa.praktikumBestanden(1, 51104));
		// Mathe hat kein Praktikum -> jeder hat bestanden
		assertTrue(pa.praktikumBestanden(1, 51101));
		// Student 2 hat GIP bestanden
		assertTrue(pa.praktikumBestanden(2, 51104));
	}
	
	@Test
	public void pruefungAnmeldenVoraussetzungen() throws SQLException {
		// Student 1 hat alle 3 Versuche aufgebraucht
		assertFalse(pa.pruefungAnmeldenVoraussetzungen(1, 55603));
		// Student 1 hat die Prﾃｼfung bereits angemeldet
		assertFalse(pa.pruefungAnmeldenVoraussetzungen(1, 55606));
		// Student 1 Prﾃｼfung kann angemeldet werden
		assertTrue(pa.pruefungAnmeldenVoraussetzungen(1, 55607));
	}

	@Test
	public void pruefungAbmeldenVoraussetzungen() throws SQLException {
		// Student 1 hat die Prﾃｼfung nicht angemeldet
		assertFalse(pa.pruefungAbmeldenVoraussetzungen(1, 55607));
		// Student 1 kann die Prﾃｼfung anmelden
		assertTrue(pa.praktikumBestanden(1, 55606));
	}
	
	
	@Test
	public void testGetModulSemester() throws SQLException {
		// Modul 52106 in Studiengang 1 ist im 2. Semester
		assertEquals(2,pa.getModulSemester(52106,1));
		// Es gibt kein Modul 333 in Studiengang 2
		assertEquals(0,pa.getModulSemester(333,2));
	}
	
	@Test
	public void testSetModulRegister() throws SQLException {
		// Modul 55623 von Student 1 wird angemeldet
		assertTrue(pa.changeModulregister(1, 55623,1));
		assertTrue(pa.getModulStudent(1, 55623));
		// Modul 55623 von Student 1 wird geloescht
		assertTrue(pa.changeModulregister(1, 55623,0)); //true falls vorhanden
		assertFalse(pa.getModulStudent(1, 55623));
		
		//Test fuer ueberpruefung von bereits geloechten Daten
		assertFalse(pa.changeModulregister(1, 55623,0));
		
	}
	
	
	@Test
	public void testSetNewPraxisSemester() throws SQLException {
		// Fuer Student 1 wird ein neues Praxissemester angelegt (mehrere moeglich)
		assertTrue(pa.setNewPraxisSemester(1));
		
		// ueberprueft ob mindestens ein Praxissemester für student 1 angelegt ist
		assertTrue(pa.getPraxisSemester(1));
		assertFalse(pa.getPraxisSemester(4));
		// Fuer Student 1 wird das letzte Praxissemester benotet
		assertTrue(pa.setNotePraxisSemester(1,1)); //bestanden
		assertTrue(pa.setNotePraxisSemester(1,0)); //nicht bestanden
		//Student 4 hat kein Praxissemester angemeldet
		assertFalse(pa.setNotePraxisSemester(4,0));
	}
	
	@Test
	public void testGetModuleStudentList() throws SQLException {
		// Fuer Student 1 sollte das erste Modul 8998 mit Name Bachelorarbeit sein
		
		int modNr = 0;
		String modName = "";
		
		List<RegisteredModulesModel> testList = pa.getModulStudent(1);
		
		RegisteredModulesModel firstModule = testList.get(0);
		modNr = firstModule.getId();
		modName = firstModule.getModule();
		
		
		assertEquals(8998,modNr);
		assertEquals("Bachelorarbeit",modName);
	}
	
	@Test
	public void testGetModuleListAll() throws SQLException {
		// Fuer Student 1 sollte das erste Modul 8998 mit Name Bachelorarbeit sein
		
		int modNr = 0;
		String modName = "";
		
		List<RegisteredModulesModel> testList = pa.getModulList();
		
		RegisteredModulesModel firstModule = testList.get(0);
		modNr = firstModule.getId();
		modName = firstModule.getModule();
		
		
		assertEquals(52106,modNr);
		assertEquals("Algorithmen und Datenstrukturen",modName);
	}
	
	
	
	
	@Test
	public void testPruefungBenotung() throws SQLException {
		// Student 1 bekommt seine letzte Pruefung in Prüfung 552011 mit 2.0 benotet
		pa.pruefungAnmelden(1, 55606);	
		assertTrue(pa.pruefungBenotung(1, 55606, 5.0));
		pa.pruefungAbmelden(1, 55606);
		
		// Fehler da Student 1 nicht zur Pruefung in Modul 552011 angemeldet ist
		assertFalse(pa.pruefungBenotung(1, 552011, 2.0));
	}
	
	@Test
	public void testGetPruefungStudentList() throws SQLException {
		// Fuer Modul 51101 sollen alle angemeldeten Studenten ausgegeben werden
		
		int matrNr = 0;
		String studName = "";
		
		List<RegisteredPruefungModel> testList = pa.getPruefungStudentList(51104);
		
		RegisteredPruefungModel firstStudent = testList.get(0);
		matrNr = firstStudent.getId();
		studName = firstStudent.getName();
		
		
		assertEquals(1,matrNr);
		assertEquals("Test1 Student1",studName);
	}
	
	@Test
	public void testBachelorarbeit() throws SQLException {

		
		//Student 1 erfuellt zulassungsvoraussetztung
		assertTrue(pa.getZulassungBA(1));
		
		// Fuer Student 1 soll eine BA angelegt werden
		assertTrue(pa.setAnmeldungBA
				(1, "Testbetreuer", "TestBA", new java.sql.Date(new java.util.Date().getTime()))
				);
		
		// Fuer Student 1 soll die letzte BA benotet werden
		assertTrue(pa.setBABenoten(1, 2.7));
	}
	
	@Test
	public void testBAKolloquium() throws SQLException {

		
		//Student 1 erfuellt zulassungsvoraussetztung
		assertTrue(pa.getZulassungBAKol(1));
		
		// Fuer Student 1 soll eine BAKol angelegt werden
		assertTrue(pa.setAnmeldungBAKol(1, new java.sql.Date(new java.util.Date().getTime())));
		
		// Fuer Student 1 soll die letzte BAKol benotet werden
		assertTrue(pa.setBAKolBenoten(1, 3.3));
	}
	
	@Test
	public void testLetzteBAverlaengern() throws SQLException {

		// Fuer Student 1 soll letzte BA um 14 Tage verlaengert werden
		// schlaegt fehl wenn noch keine BA angelegt wurde
		assertTrue(pa.setBAVerlaengerung(1, new java.sql.Date(new java.util.Date().getTime())));
	}
	
	@Test
	public void testMasterarbeit() throws SQLException {

		
		//Student 1 erfuellt zulassungsvoraussetztung
		assertTrue(pa.getZulassungMA(1));
		
		// Fuer Student 1 soll eine BA angelegt werden
		assertTrue(pa.setAnmeldungMA
				(1, "Testbetreuer", "TestBA", new java.sql.Date(new java.util.Date().getTime()))
				);
		
		// Fuer Student 1 soll die letzte BA benotet werden
		assertTrue(pa.setMABenoten(1, 2.7));
	}
	
	@Test
	public void testMAKolloquium() throws SQLException {

		
		//Student 1 erfuellt zulassungsvoraussetztung
		assertTrue(pa.getZulassungMAKol(1));
		
		// Fuer Student 1 soll eine BAKol angelegt werden
		assertTrue(pa.setAnmeldungMAKol(1, new java.sql.Date(new java.util.Date().getTime())));
		
		// Fuer Student 1 soll die letzte BAKol benotet werden
		assertTrue(pa.setMAKolBenoten(1, 3.3));
	}
	
	@Test
	public void testLetzteMAverlaengern() throws SQLException {

		// Fuer Student 1 soll letzte BA um 14 Tage verlaengert werden
		// schlaegt fehl wenn noch keine BA angelegt wurde
		assertTrue(pa.setMAVerlaengerung(1, new java.sql.Date(new java.util.Date().getTime())));
	}
	

	@After
	public void tearDown() throws SQLException {
		pa.close();
	}
}
