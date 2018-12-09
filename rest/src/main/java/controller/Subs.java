package controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subs")
public class Subs {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "subID")
    private Integer subID;

    @Column(name = "username")
    private String username;


    public Subs() {}

    public Subs(int subID, String username) {
        this.subID = subID;
        this.username = username;
    }

    public Integer getSubID() {
        return subID;
    }

    public void setID(Integer id) {
        this.subID = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }
 
}