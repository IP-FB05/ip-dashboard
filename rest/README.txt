Rest Server starten:
1. Install Maven --> https://maven.apache.org/install.html
2. Jar Datei erzeugen --> 	In den Rest Ordner gehen (Kommandozeile)
				mvn clean package (Kommandozeile)
3. Server starten --> java -Dserver.port=9090 -jar target/rest-1.0-SNAPSHOT.jar
			 ODER --> java -jar target/rest-1.0-SNAPSHOT.jar --server.port=9090