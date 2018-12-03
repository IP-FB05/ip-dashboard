package controller;

public class Document {

    private int documentID;
    private String categoriename;
    private String name;
    private String lastChanged;
    private String link;

    public Document(int documentID, String categoriename, String name, String lastChanged, String link) {
        this.documentID = documentID;
        this.categoriename = categoriename;
        this.name = name;
        this.lastChanged = lastChanged;
		this.link = link;
    }

    public long getDocumentID() {
        return documentID;
    }

    public String getCategoriename() {
        return categoriename;
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