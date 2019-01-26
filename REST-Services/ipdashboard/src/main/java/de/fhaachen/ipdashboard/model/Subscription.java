package de.fhaachen.ipdashboard.model;

public class Subscription {

    private Integer processID;
    private String username;

    public Subscription(Integer pid, String uname) {
        this.processID = pid;
        this.username = uname;
    }

    public void setProcessID(Integer pid) {
        this.processID = pid;
    }

    public void setUsername(String uname) {
        this.username = uname;
    }

    public Integer getProcessID(){
        return this.processID;
    }

    public String getUsername(){
        return this.username;
    }

    public String toString() {
        return this.username + "hat den Prozess mit der PID: " + this.processID + " abonniert.";
    }
 
}