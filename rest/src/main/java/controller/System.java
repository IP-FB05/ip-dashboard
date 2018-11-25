package controller;

public class System {

    private int systemID;
    private String name;
    private String description;
    private String link;

    public System(int systemID, String name, String description, String link) {
        this.systemID = systemID;
        this.name = name;
        this.description = description;
		this.link = link;
    }

    public long getSystemID() {
        return systemID;
    }

    public String getName() {
        return name;
    }
    
    public String getDescription() {
    	return description;
    }
    
    public String getLink() {
    	return link;
    }
 
}