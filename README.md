# ip-dashboard
Dashboard zur Prozesslandschaft des FB05

## Benötigte Software
* [Apache Maven](https://maven.apache.org/download.cgi?Preferred=ftp://mirror.reverse.net/pub/apache/) Empfohlene Version: 3.6.0
* [Java JDK](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) Empfohlene Version: 1.8.0
* [git](https://git-scm.com/downloads)
* [Nodejs & npm](https://nodejs.org/en/download/) Empfohlene Version Nodejs: 10.13.0 | NPM: 6.4.1
* [Camunda BPM platform](https://camunda.com/download/)

Klonen des Repository
> git clone https://github.com/IP-FB05/ip-dashboard.git


## Camunda BPM platform
Den Inhalt des Ordners `camunda-geänderte Dateien` in den entpackten Camunda BPM platform Ordner (zum Beispiel `camunda-bpm-tomcat-7.10.0\server\apache-tomcat-9.0.12`) kopieren und die Dateien ersetzen. Dadurch werden folgende Änderungen gemacht:
* Design der Tasklist, des Cockpits, der Adminansicht und der Startseite an das Design des Dashboard und der FH angepasst
* CORS Filter wird eingestellt
* Camunda-Authentication wird aktiviert

## Dashboard
Installation und starten des Dashboard. Über die Konsole in den Order `Dashboard\mydash` navigieren
> npm install

**Hinweis:** Bei der empfohlenen npm Version sollten alle dependencies korrekt installiert werden. Falls dies nicht der Fall ist, oder eine andere npm Version verwendet wird kann es ggf. nötig sein dass das mittels folgendem Befehl Sicherheitsschwachstellen behoben werden müssen.
> npm audit fix

Angular CLI installieren
> npm i @angular/cli

Im neu angelegtem Ordner `node_modules\@angular-devkit\build-angular\src\angular-cli-files\models\webpack-configs` die Datei `brower.js` öffnen und die Zeile
> node: false,

ersetzen durch
> node: { crypto: true, stream: true },

Den Server starten mittels
> npm start

Hinweis: Alle Pfade im master-Branch weisen auf localhost. Diese müssen angepasst werden, falls von außen erreichbar sein soll.

## Datenbanken
Die derzeitigen Datenbanken sind über aws gehostet. Die .sql Dateien zum erzeugen der beiden benötigten Datenbanken (Dashboard und Prüfungsamt) sowie die ER-Diagramme befinden sich im Ordner `Datenbank`.
**Hinweis:** Wird nicht die gehostete Datenbank verwendet, müssen die Connections zu der Datenbank in den REST-Service angepasst werden.

## Notificationservice
Der Notificationservice dient dazu, Abonnenten eines Prozesses bzw. einer Prozess-Instanz Emails zu schicken, wenn sie einen offenen Task haben oder ein Task beendet wurde. Er wird ist als Camunda-Plugin realisiert.
Installation und Implementierung:
1. Anlegen der `notification.conf` in `ip-dashboard\Notificationservice\src\main\resources` mit folgendem Inhalt, falls nicht vorhanden (Angaben in Klammern durch Daten ersetzen):
```
dbuser:(Username)
dbpass:(Password)
restuser:(Username)
restpass:(Password)
testkey:(Key)
mailpass:(Password)
mailadr:(Email)
```
2. Anlegen der `dashboardDB.conf` in `ip-dashboard\Notificationservice\src\main\resources` mit folgendem Inhalt, falls nicht vorhanden (Angaben in Klammern durch Daten ersetzen):
```
dbuser:(Username)
dbpass:(Password)
restuser:(Username)
restpass:(Password)
```

3. Den Notificationservice mittels folgendem Befehl im Ordner `ip-dashboard\Notificationservice` erstellen:
> mvn clean package

Die dadurch erzeugte `notificationservice.jar` befindet sich nun in `ip-dashboard\notificationservice\target`.
4. Kopieren der `notificationservice.jar` nach `camunda-bpm-tomcat-7.9.0\lib` und `camunda-bpm-tomcat-7.9.0\server\apache-tomcat-9.0.5\lib`

**Hinweis:** Ggf. kann die Versionsnummer der Ordner anders sein.

5. Herunterladen des [JDBC Connectors](https://dev.mysql.com/downloads/connector/j/). Die .jar Datei muss ebenfalls in die Ordner `camunda-bpm-tomcat-7.9.0\lib` und `camunda-bpm-tomcat-7.9.0\server\apache-tomcat-9.0.5\lib` kopiert werden.

6. Anpassen der `bpm-platform.xml` in `camunda-bpm-tomcat-7.9.0\server\apache-tomcat-9.0.5\conf`. Ergänzen sie folgende Zeilen:
```
	  <plugin>
		 <class>notificationservice.NotificationPlugin</class>
	  </plugin>
```

7. Neustarten des Servers, falls dieser bereits gestartet wurde.

## Prozesse
### Erstellen der .war Datei für automatisierte Prozesse

1. Anlegen der `pruefungsamt.conf` (falls diese nicht vorhanden ist) im Ordner `src\main\resources` des jeweilgen Prozesses mit folgendem Inhalt(Angaben in Klammern durch Daten ersetzen):
```
dbuser:(Username)
dbpass:(Password)
restuser:(Username)
restpass:(Password)
testkey:(Key)
mailpass:(Password)
mailadr:(Email)
```
2. Im Ordner des Prozesses folgenden Befehl ausführen:
> mvn clean package

Dies erstellt die .war Datei im Ordner `target`des jeweiligen Prozesses. Diese kann zum deployen über das Dashboard verwendet werden. Das jeweilige BPMN befindet sich im Ordner `src\main\resources` des jeweilgen Prozesses.

### Nicht automatisierte Prozesse
Prozesse die nicht automatisiert sind haben bisher nur eine BPMN Datei, welche zum hinzufügen des Prozesses über das Dashboard verwendet werden kann.

## REST-Service
**Hinweis: Sollten die Ports geändert werden, so müssen diese ebenfalls in den Prozessen und dem Dashboard bei den REST-Aufrufen angepasst werden**
### Dashboard
1. Anlegen der `dashboardDB.conf` in `ip-dashboard\REST-Services\Dashboard\src\main\resources` mit folgendem Inhalt, falls nicht vorhanden (Angaben in Klammern durch Daten ersetzen):
```
dbuser:(Username)
dbpass:(Password)
restuser:(Username)
restpass:(Password)
```
2. Erstellen und Starten des REST-Service. **Wichtig: Folgende Befehle im Ordner `REST-Services\Dashboard` ausführen, da dort nach dem Ordner `upload-dir` für den Fileserver gesucht wird**
```
mvn clean package
java -Dserver.port=9090 -jar target\rest-1.0-SNAPSHOT
```

### Prüfungsamt
1. Anlegen der `pruefungsamt.conf` in `ip-dashboard\REST-Services\pruefungsamt\src\main\resources` mit folgendem Inhalt, falls nicht vorhanden (Angaben in Klammern durch Daten ersetzen):
```
dbuser:(Username)
dbpass:(Password)
restuser:(Username)
restpass:(Password)
testkey:(Key)
mailpass:(Password)
mailadr:(Email)
```
2. Erstellen und Starten des REST-Service.
```
mvn clean package
java -Dserver.port=8888 -jar target\gs-rest-service-0.1.0
```

### ipdashboard
Dieser Rest-Service beinhaltet zusätzlich die Authentifizierung und Autorisierung über Rest mit einer Domainstruktur und teilweiser Nutzung des CRUD-Repositorys. Dieser ist zu finden im Branch 'new-auth'.

1. Anlegen der `dashboardDB.conf` in `ip-dashboard\REST-Services\Dashboard\src\main\resources` mit folgendem Inhalt, falls nicht vorhanden (Angaben in Klammern durch Daten ersetzen):
```
dbuser:(Username)
dbpass:(Password)
restuser:(Username)
restpass:(Password)
```
2. Erstellen und Starten des REST-Service. **Wichtig: Folgende Befehle im Ordner `REST-Services\ipdashboard` ausführen, da dort nach dem Ordner `upload-dir` für den Fileserver gesucht wird**
```
mvn clean package
java -Dserver.port=9090 -jar target\rest-1.0-SNAPSHOT
```


