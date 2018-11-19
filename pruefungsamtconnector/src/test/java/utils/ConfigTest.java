package utils;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class ConfigTest {
	@Test
	public void testGetConfig() throws SQLException {
		assertEquals(Config.getConfig("testkey"), "testvalue");
		assertNotNull(Config.getConfig(Config.REST_PASS));
		assertNotNull(Config.getConfig(Config.REST_USER));
	}
}
