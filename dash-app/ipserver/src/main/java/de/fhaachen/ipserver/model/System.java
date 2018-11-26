package de.fhaachen.ipserver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "systems")
public class System {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	@Column(name = "systemID")
	private Integer systemID;

	@Column(name = "description")
	private String description;

	@Column(name = "link")
	private String link;

	@Column(name = "name")
	private String name;

	public Integer getId() {
		return systemID;
	}

	public void setId(Integer id) {
		this.systemID = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

}