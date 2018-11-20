package controller;

public class System {

    private int systemID;
    private String name;
    private String beschreibung;
    private String link;

    public System(int systemID, String name, String beschreibung, String link) {
        this.systemID = systemID;
        this.name = name;
        this.beschreibung = beschreibung;
		this.link = link;
    }

    public long getSystemID() {
        return systemID;
    }

    public String getName() {
        return name;
    }
    
    public String getBeschreibung() {
    	return beschreibung;
    }
    
    public String getLink() {
    	return link;
    }
 
}