package controller;

public class Subscription {

    private int processID;
    private String username;

    public Subscription(int pid, String uname) {
        this.processID = pid;
        this.username = uname;
    }

    public void setProcessID(int pid) {
        this.processID = pid;
    }

    public void setUsername(String uname) {
        this.username = uname;
    }

    public int getProcessID(){
        return this.processID;
    }

    public String getUsername(){
        return this.username;
    }

    public String toString() {
        return this.username + "hat den Prozess mit der PID: " + this.processID + " abonniert.";
    }
 
}