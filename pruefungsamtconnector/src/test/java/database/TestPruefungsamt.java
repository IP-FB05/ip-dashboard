package database;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
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
	public void testgetCredits() throws SQLException {
		// Student 2 hat 27 Credits
		assertEquals(27, pa.getCredits(2));
		// Student 1 hat keine Credits
		assertEquals(0, pa.getCredits(1));
	}

	@Test
	public void praktikumBestanden() throws SQLException {
		// Student 1 hat GIP nicht bestanden
		assertFalse(pa.praktikumBestanden(1, 51104));
		// Mathe hat kein Praktikum -> jeder hat bestanden
		assertTrue(pa.praktikumBestanden(1, 51101));
		// Student 2 hat GIP bestanden
		assertTrue(pa.praktikumBestanden(2, 51104));
	}

	@After
	public void tearDown() throws SQLException {
		pa.close();
	}
}
