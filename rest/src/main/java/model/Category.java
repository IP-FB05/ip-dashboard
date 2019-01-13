package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "categoryID")
	private Integer categoryID;

	@Column(name = "name")
	private String name;

	public Category(int categoryID, String name) {
        this.categoryID = categoryID;
        this.name = name;
    }

	public Integer getId() {
		return categoryID;
	}

	public void setId(Integer id) {
		this.categoryID = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}