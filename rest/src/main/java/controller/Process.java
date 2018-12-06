package controller;

public class Process {

    private int processID;
    private String name;
    private String description;
    private String pic;
    private String warFile;
    private String bpmn;
    private String added;
    private String camunda_processID;

    public Process(int processID, String name, String description, String pic, String warFile, String bpmn, String added, String camunda_processID) {
        this.processID = processID;
        this.name = name;
        this.description = description;
        this.pic = pic;
        this.warFile = warFile;
        this.bpmn = bpmn;
        this.added = added;
        this.camunda_processID = camunda_processID;
    }

    public long getProcessID() {
        return processID;
    }

    public String getName() {
        return name;
    }
    
    public String getDescription() {
    	return description;
    }
    
    public String getPic() {
    	return pic;
    }

    public String getwarFile() {
    	return warFile;
    }

    public String getBpmn() {
    	return bpmn;
    }

    public String getAdded() {
    	return added;
    }

    public String getCamunda_processID() {
        return camunda_processID;
    }
}