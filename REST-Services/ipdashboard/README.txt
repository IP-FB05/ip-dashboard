Rest Server starten:
1. Install Maven --> https://maven.apache.org/install.html
2. Jar Datei erzeugen --> 	In den Rest Ordner gehen (Kommandozeile)
				mvn clean package (Kommandozeile)
3. Server starten --> java -Dserver.port=9090 -jar target/ipdashboard-0.0.1-SNAPSHOT.jar
			 ODER --> java -jar target/ipdashboard-0.0.1-SNAPSHOT.jar --server.port=9090
