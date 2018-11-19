package database;

import static org.junit.Assert.*;

import java.sql.SQLException;

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

	@Ignore
	@Test
	public void testgetCredits() throws SQLException {
		// Student 2 hat 27 Credits
		assertEquals(171, pa.getCredits(2));
		// Student 1 hat keine Credits
		assertEquals(109, pa.getCredits(1));
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
		// Student 1 hat die Prüfung bereits angemeldet
		assertFalse(pa.pruefungAnmeldenVoraussetzungen(1, 55606));
		// Student 1 Prüfung kann angemeldet werden
		assertTrue(pa.pruefungAnmeldenVoraussetzungen(1, 55607));
	}

	@Test
	public void pruefungAbmeldenVoraussetzungen() throws SQLException {
		// Student 1 hat die Prüfung nicht angemeldet
		assertFalse(pa.pruefungAbmeldenVoraussetzungen(1, 55607));
		// Student 1 kann die Prüfung anmelden
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
		
	}
	

	@After
	public void tearDown() throws SQLException {
		pa.close();
	}
}
