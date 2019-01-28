# ip-dashboard
Dashboard zur Prozesslandschaft des FB05


### ipdashboard
Dieser Rest-Service beinhaltet zusätzlich die Authentifizierung und Autorisierung über Rest mit einer Domainstruktur und teilweiser Nutzung des CRUD-Repositorys. Dieser ist zu finden im Branch 'new-auth'.

1. Anlegen der `dashboardDB.conf` in `ip-dashboard\REST-Services\ipdashbaord\src\main\resources` mit folgendem Inhalt, falls nicht vorhanden (Angaben in Klammern durch Daten ersetzen):
```
dbuser:(Username)
dbpass:(Password)
restuser:(Username)
restpass:(Password)
```
2. Erstellen und Starten des REST-Service. **Wichtig: Folgende Befehle im Ordner `REST-Services\ipdashboard` ausführen, da dort nach dem Ordner `upload-dir` für den Fileserver gesucht wird**
```
mvn clean package
java -Dserver.port=9090 -jar target\ipdashboard-0.0.1-SNAPSHOT.jar
```

Falls dies nicht funktioniert, folgenden Befehl ausführen:

```
java -jar target/ipdashboard-0.0.1-SNAPSHOT.jar --server.port=9090
```


