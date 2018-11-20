package controller;

public class Dokument {

    private int dokumentID;
    private String kategoriename;
    private String name;
    private String lastChanged;
    private String link;

    public Dokument(int dokumentID, String kategoriename, String name, String lastChanged, String link) {
        this.dokumentID = dokumentID;
        this.kategoriename = kategoriename;
        this.name = name;
        this.lastChanged = lastChanged;
		this.link = link;
    }

    public long getDokumentID() {
        return dokumentID;
    }

    public String getKategoriename() {
        return kategoriename;
    }

    public String getName() {
        return name;
    }
    
    public String getLastChanged() {
    	return lastChanged;
    }
    
    public String getLink() {
    	return link;
    }
 
}