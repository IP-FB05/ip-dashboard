package controller;

public class Prozess {

    private int prozessID;
    private String name;
    private String beschreibung;
    private String bild;
    private String varDatei;
    private String bpmn;

    public Prozess(int prozessID, String name, String beschreibung, String bild, String varDatei, String bpmn) {
        this.prozessID = prozessID;
        this.name = name;
        this.beschreibung = beschreibung;
        this.bild = bild;
        this.varDatei = varDatei;
        this.bpmn = bpmn;
    }

    public long getProzessID() {
        return prozessID;
    }

    public String getName() {
        return name;
    }
    
    public String getBeschreibung() {
    	return beschreibung;
    }
    
    public String getBild() {
    	return bild;
    }

    public String getVarDatei() {
    	return varDatei;
    }

    public String getBpmn() {
    	return bpmn;
    }
 
}