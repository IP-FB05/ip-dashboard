package de.fhaachen.ipdashboard.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usergroups")
public class Usergroup {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "usergroup_id")
    private Integer usergroup_id;

    @Column(name = "usergroup_name")
    private String usergroup_name;


    public Usergroup() {}
    public Usergroup(int usergroup_id, String usergroup_name) {
        this.usergroup_id = usergroup_id;
        this.usergroup_name = usergroup_name;
    }


    public Integer getUsergroupID() {
        return usergroup_id;
    }

    public void setID(Integer id) {
        this.usergroup_id = id;
    }

    public String getUsergroupName() {
        return usergroup_name;
    }

    public void setUsergroupName(String name) {
        this.usergroup_name = name;
    }
}