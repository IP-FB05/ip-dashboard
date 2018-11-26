package de.fhaachen.ipserver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "documents")
public class Document {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	@Column(name = "documentID")
    private Integer documentID;
    
	@Column(name = "categoryID")
	private Integer categoryID;

	@Column(name = "name")
	private String name;

	@Column(name = "lastChanged")
    private String lastChanged;
    
    @Column(name = "link")
    private String link;

	public Integer getDocumentId() {
		return documentID;
	}

	public void setDocumentId(Integer id) {
		this.documentID = id;
    }
    
    public Integer getCategoryId() {
		return categoryID;
	}

	public void setCategoryId(Integer id) {
		this.categoryID = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
    }
    
    public String getLastChanged() {
		return lastChanged;
	}

	public void setLastChanged(String lastChanged) {
		this.lastChanged = lastChanged;
	}

}