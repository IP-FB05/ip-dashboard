package de.fhaachen.ip.pruefungabmelden;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Config {
	
	public static final String REST_USER = "restuser";
	public static final String REST_PASS = "restpass";
	public static final String DB_USER = "dbuser";
	public static final String DB_PASS = "dbpass";
	public static final String MAIL_PASS = "mailpass";
	public static final String MAIL_ADR = "mailadr";
	static Map<String, String> configMap = null;
	
	public static String getConfig(String property) {
		if(configMap == null) {
			openConfig();
		}
		return configMap.get(property);
	}
	
	private static void openConfig() {
		File file = null;
		Scanner scanner = null;
		try {
			file = new File(ClassLoader.getSystemResource("pruefungsamt.conf").getFile());
			scanner = new Scanner(file);
			configMap = new HashMap<String, String>();
			String line;
			while(scanner.hasNextLine()) {
				line = scanner.nextLine();
				configMap.put(line.split(":")[0], line.split(":")[1]);
			}
		} catch (FileNotFoundException e) {
			System.out.println("pruefungsamt.conf konnte nicht gefunden werden unter src/main/resources. Diese musst du selber anlegen!");
		}finally {
			if(scanner != null) {
				scanner.close();
			}
		}
	}
}
