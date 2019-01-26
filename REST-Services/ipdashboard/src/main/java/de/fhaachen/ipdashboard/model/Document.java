package de.fhaachen.ipdashboard.model;

import java.sql.Date;

public class Document {

    private int documentID;
    private String categoriename;
    private String name;
    private Date lastChanged;
    private String link;

    public Document(int documentID, String categoriename, String name, Date lastChanged, String link) {
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
    
    public Date getLastChanged() {
    	return lastChanged;
    }
    
    public String getLink() {
    	return link;
    }
 
}