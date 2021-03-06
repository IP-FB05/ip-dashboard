package utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Config {

	public static final String REST_USER = "restuser";
	public static final String REST_PASS = "restpass";
	public static final String DB_USER = "dbuser";
	public static final String DB_PASS = "dbpass";
	public static final String MAIL_PASS = "mailpass";
	static Map<String, String> configMap = null;

	public static String getConfig(String property) {
		if(configMap == null) {
			openConfig();
		}
		return configMap.get(property);
	}

	private static void openConfig() {
		try {
			InputStream confFile = Thread.currentThread().getContextClassLoader().getResourceAsStream("pruefungsamt.conf");
			InputStreamReader isr = new InputStreamReader(confFile);
			BufferedReader br = new BufferedReader(isr);
			if(confFile == null) {
				System.out.println("hier");
				throw new Exception();
			}
			configMap = new HashMap<String, String>();
			String line;
			while((line = br.readLine()) != null) {
				configMap.put(line.split(":")[0], line.split(":")[1]);
			}
		} catch (Exception e) {
			System.out.println("pruefungsamt.conf konnte nicht gefunden werden unter src/main/resources. Diese musst du selber anlegen!");
		}
	}
}
